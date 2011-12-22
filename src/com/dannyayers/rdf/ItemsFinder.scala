package com.dannyayers.rdf

import scala.collection.mutable.ListBuffer
import com.dannyayers.core._
import com.hp.hpl.jena.rdf.model._

import com.hp.hpl.jena.util.FileManager
import java.io._
import com.hp.hpl.jena.query._
import scala.xml._

object ItemsFinder {

      def getItems(model: Model, queryString: String) : ListBuffer[Map[String, String]] = {     
 
      /* 
      
      var queryString = """
      		  prefix dc: <http://purl.org/dc/elements/1.1/>
              prefix foaf:  <http://xmlns.com/foaf/0.1/> 
              prefix rss:  <http://purl.org/rss/1.0/> 
              prefix content:  <http://purl.org/rss/1.0/modules/content/> 
              prefix planet: <http://planetrdf.com/ns/>

              SELECT ?item ?title ?content ?date ?makerName WHERE {

              	?item a rss:item ;
              		rss:title ?title ;
              		planet:content ?content ;
              		dc:date ?date ;
                    foaf:maker ?maker . 
                    ?maker foaf:name ?makerName .
              }
              ORDER BY desc(?date)
              LIMIT 10
      """ 
      */
   /*
    * ?item ?title ?content ?date ?makerName ?related ?comment
                OPTIONAL { ?item foaf:maker ?maker . 
                           ?maker foaf:name ?makerName .
                }
                OPTIONAL { ?s1 knobot:target ?item . ?s1 knobot:source ?related . }
                OPTIONAL { ?s2 knobot:source ?item . ?s2 knobot:target ?comment . }
    */
      System.out.println("running query...")
      
  var qexec = QueryExecutionFactory.create(queryString, model) 
   var results = qexec.execSelect()
var i = 0
var items = new ListBuffer[Map[String, String]]
      // (Main.MAX_ITEMS)
   while(results.hasNext()) {
	   var soln = results.nextSolution() 

    var uri = ""
    if(soln.get("?item") != null)
      uri = soln.get("?item").asInstanceOf[Resource].getURI()
    else
      uri = Main.BASE_URI+"/"
    // System.out.println(uri)
    var title = ""
    if(soln.get("?title") != null)
      title = soln.get("?title").toString()
    else
      title = "Untitled"
    
    // System.out.println(title)
    
    var content = ""
      if(soln.get("?content") != null)
        
        content =  soln.get("?content").asInstanceOf[Literal].getValue().toString()
   //     content =  "<p>"+soln.get("?content").asInstanceOf[Literal].getValue().toString()+"</p>"
      else 
         content = "<em>no content found - probably an error</em>"
      
     // System.out.println("CONTENT="+content)
      
    var date = ""
    if(soln.get("?date") != null)
      date = soln.get("?date").asInstanceOf[Literal].getValue().toString()     
      
    var makerName = ""
    if(soln.get("?makerName") != null) {
      makerName = soln.get("?makerName").toString()    
     System.out.println("MAKER NAME="+makerName)
    }
    
    var related = ""
    if(soln.get("?related") != null)
      related = soln.get("?related").toString().substring(21) // relativised for local testing

        var comment = ""
    if(soln.get("?comment") != null)
      comment = soln.get("?comment").toString().substring(21) // relativised for local testing
 
      
     var item = Map(
         "uri" -> uri,
         "title" -> title,
         "content" -> content,
         "date" -> date,
         "makerName" -> makerName,
         "related" -> related,
         "comment" -> comment
      )
     // items.update(i, item)
     // i = i + 1
      items += item
      
      // System.out.println(i)  
      
      }
qexec.close()
 // System.out.println("query run.")
return items

}
}