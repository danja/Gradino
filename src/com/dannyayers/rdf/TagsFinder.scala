package com.dannyayers.rdf

/* finds tags on a specified item */

import com.hp.hpl.jena.rdf.model._
import scala.collection.mutable.HashMap
import com.hp.hpl.jena.util.FileManager
import java.io._
import com.hp.hpl.jena.query._
import scala.xml._
import com.dannyayers.core._
import com.dannyayers.rdf._

object TagsFinder {
     def getTags(uri : String) : HashMap[String, String] = {     
 
      var queryTemplate = """
      		  prefix dc: <http://purl.org/dc/elements/1.1/>
              prefix foaf:  <http://xmlns.com/foaf/0.1/> 
              prefix rss:  <http://purl.org/rss/1.0/> 
              prefix content:  <http://purl.org/rss/1.0/modules/content/> 
              prefix planet: <http://planetrdf.com/ns/>
      		  prefix knobot: <http://wymiwyg.org/ontologies/knobot#>
      		  prefix tag: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>

              SELECT ?tagUri ?tagName WHERE {
    	  		%uri% tag:taggedWithTag ?tagUri .
                ?tagUri tag:tagName ?tagName .
              }
      """ 

    	   val queryString = queryTemplate.replaceAll("%uri%", "<"+uri+">")
    	    
      System.out.println("running tag query...")
      
  var qexec = QueryExecutionFactory.create(queryString, Main.mainModel)  
   var results = qexec.execSelect()
var i = 0
var tags = new HashMap[String, String] 
   while(results.hasNext()) {
	   var soln = results.nextSolution() 

          var tagName = ""
    if(soln.get("?tagName") != null)
      tagName = soln.get("?tagName").toString() 
      
        var tagUri = ""
    if(soln.get("?tagUri") != null) {
      tagUri = soln.get("?tagUri").toString().substring(21) // relativise for local testing
      tags.update(tagUri, tagName)
    }
      i = i + 1
      // System.out.println(i)  
      
      }
qexec.close()
 // System.out.println("query run.")
return tags
     }
}
