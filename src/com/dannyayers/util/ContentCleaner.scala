package com.dannyayers.util

import com.hp.hpl.jena.rdf.model._
import com.hp.hpl.jena.util.FileManager
import java.io._
import com.hp.hpl.jena.query._
import com.dannyayers.rdf._
import org.w3c.tidy._

object ContentCleaner {
    def main(args : Array[String]) : Unit = {
          var inputFileName  = "/home/danny/workspace/gradino/data/2008-04-20.rdf"
          var outputFileName  = "/home/danny/workspace/gradino/data/2008-04-20_clean.rdf"
          
          System.out.println("****** READING INPUT ******")
          
          var model = RdfUtils.getModel(inputFileName)
        
          model = scan(model)
          
        System.out.println("****** WRITING OUTPUT ******")
        
          RdfUtils.writeModel(model, outputFileName)
    }
    
		def scan(rawmodel : Model) : Model = {
		  var model = rawmodel
    
		  val ENCODED = model.createProperty("http://purl.org/rss/1.0/modules/content/","encoded")
		  val CONTENT = model.createProperty("http://planetrdf.com/ns/","content")
    
    // <planet:content xmlns="http://www.w3.org/1999/xhtml" rdf:parseType="Literal">

    var iter = model.listResourcesWithProperty(ENCODED)
    
    var count = 0
    while (iter.hasNext()){ 
    	var subject = iter.nextResource()
     var encoded = model.getProperty(subject, ENCODED).getObject()
     val content = clean(encoded.toString())
     val literal = model.createLiteral(content, true)
     
     model.remove(subject, ENCODED, encoded)
    
     model.add(subject, CONTENT, literal)
     
     count = count + 1
     System.out.println(count)
     }
			return model
        }
  
    def clean(encoded : String) : String = {
      var in = new ByteArrayInputStream(encoded.getBytes());
      var out = new ByteArrayOutputStream();
      var tidy = new Tidy()
      
      tidy.setXHTML(true)
      tidy.setXmlOut(true)
      tidy.setDocType("omit"); // can't check for strict and omit :(
      tidy.setMakeClean(true);
      tidy.setQuiet(false);
      tidy.setIndentContent(true);
      tidy.setPrintBodyOnly(true)
      tidy.setTidyMark(false) 

      tidy.parse(in, out)
      val clean = "<div>"+out.toString()+"</div>"
      return clean
    }
}
