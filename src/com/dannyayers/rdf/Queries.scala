/*
 * SPARQL queries as strings
 * some have %variables% to replace
 */

package com.dannyayers.rdf

object Queries {
	var recentItems = """
      		  prefix dc: <http://purl.org/dc/elements/1.1/>
              prefix foaf:  <http://xmlns.com/foaf/0.1/> 
              prefix rss:  <http://purl.org/rss/1.0/> 
              prefix content:  <http://purl.org/rss/1.0/modules/content/> 
              prefix planet: <http://planetrdf.com/ns/>

              SELECT DISTINCT ?item ?title ?content ?date ?makerName WHERE {

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
	  
	  	var allItems = """
      		  prefix dc: <http://purl.org/dc/elements/1.1/>
              prefix foaf:  <http://xmlns.com/foaf/0.1/> 
              prefix rss:  <http://purl.org/rss/1.0/> 

              SELECT DISTINCT ?item ?title ?date WHERE {
	  			
              	?item a rss:item ;
              		rss:title ?title ;
              		dc:date ?date .
	  				FILTER (!regex(str(?item), "comment", "i")) 
              }
      """ 
		
	  var taggedItems = """
      		  prefix dc: <http://purl.org/dc/elements/1.1/>
              prefix foaf:  <http://xmlns.com/foaf/0.1/> 
              prefix rss:  <http://purl.org/rss/1.0/> 
              prefix content:  <http://purl.org/rss/1.0/modules/content/> 
              prefix planet: <http://planetrdf.com/ns/>
      		  prefix knobot: <http://wymiwyg.org/ontologies/knobot#>
      		  prefix tag: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>

              SELECT DISTINCT ?item ?title ?content ?date ?makerName WHERE {

              	?item a rss:item ;
              		rss:title ?title ;
              		planet:content ?content ;
		  			tag:taggedWithTag %tagUri% ;
              		dc:date ?date ;
                    foaf:maker ?maker . 
                    ?maker foaf:name ?makerName .
              }
              ORDER BY desc(?date) # maybe asc() would be better here?
      """ 
	    
	  	var dateRangeItems = """
      		  prefix dc: <http://purl.org/dc/elements/1.1/>
              prefix rss:  <http://purl.org/rss/1.0/> 

              SELECT DISTINCT ?item ?title ?date WHERE {
	  			
              	?item a rss:item ;
              		rss:title ?title ;
              		dc:date ?date .
FILTER ( (regex(str(?date), "^%year%")) 
&&       (!regex(str(?title), "^Comment") )
      && (!regex(str(?title), "^Re:")  ) )              }
ORDER BY ASC(?date)
      """ 
	 	  /*
	 	   *               SELECT ?tagUri ?tagName WHERE {
    	  		%uri% tag:taggedWithTag ?tagUri .
                ?tagUri tag:tagName ?tagName .
              }
              */
	 
}