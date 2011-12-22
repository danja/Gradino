package com.dannyayers.rdf

import com.hp.hpl.jena.rdf.model._
import com.hp.hpl.jena.util.FileManager
import java.io._
import com.hp.hpl.jena.query._
import scala.xml._

object ItemFinder {

    def getItem(model: Model, uri : String) : Map[String, String] = {     
      
      var queryTemplate = """
      		  prefix dc: <http://purl.org/dc/elements/1.1/>
              prefix foaf:  <http://xmlns.com/foaf/0.1/> 
              prefix rss:  <http://purl.org/rss/1.0/> 
              prefix content:  <http://purl.org/rss/1.0/modules/content/> 
              prefix planet: <http://planetrdf.com/ns/>
      		  prefix knobot: <http://wymiwyg.org/ontologies/knobot#>
      		  prefix tag: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>

              SELECT ?title ?content ?date ?makerName ?related ?comment ?tagUri ?tagName WHERE {

              	%uri%	rss:title ?title .
              	OPTIONAL { %uri%	planet:content ?content }
              	OPTIONAL { %uri%	dc:date ?date }
                OPTIONAL { %uri% foaf:maker ?maker . 
                           ?maker foaf:name ?makerName .
                }
                OPTIONAL { ?s1 knobot:target %uri% . ?s1 knobot:source ?related . }
                OPTIONAL { ?s2 knobot:source %uri% . ?s2 knobot:target ?comment . }
              }
              LIMIT 1
      """ 
      
      var queryString = queryTemplate.replaceAll("%uri%", "<"+uri+">")
                                                 
  var qexec = QueryExecutionFactory.create(queryString, model) 
   var results = qexec.execSelect()
var i = 0
var item = Map.empty[String, String]

   while(results.hasNext()) {
	   var soln = results.nextSolution() 

    var title = ""
    if(soln.get("?title") != null)
      title = soln.get("?title").toString()
    else
      title = "Untitled"
    
    var content = ""
      if(soln.get("?content") != null)
        
        content =  soln.get("?content").asInstanceOf[Literal].getValue().toString()
      // content =  "<p>"+soln.get("?content").asInstanceOf[Literal].getValue().toString()+"</p>"
      else 
         content = "<em>no content found - maybe an error</em>"
      
     // System.out.println("CONTENT="+content)
      
    var date = ""
    if(soln.get("?date") != null)
      date = soln.get("?date").asInstanceOf[Literal].getValue().toString() 
      
    var makerName = ""
    if(soln.get("?makerName") != null)
      makerName = soln.get("?makerName").toString()    
    
    var related = ""
    if(soln.get("?related") != null)
      related = soln.get("?related").toString().substring(21) // relativise for local testing

        var comment = ""
    if(soln.get("?comment") != null)
      comment = soln.get("?comment").toString().substring(21) // relativise for local testing

      item = Map(
         "uri" -> uri,
         "title" -> title,
         "content" -> content,
         "date" -> date,
         "makerName" -> makerName,
         "related" -> related,
         "comment" -> comment
      )
    //  System.out.println(item)
       System.out.println(uri)
      i = i + 1
      // System.out.println(i)  
      }
qexec.close()
return item
}
}
