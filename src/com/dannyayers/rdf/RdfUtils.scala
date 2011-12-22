package com.dannyayers.rdf

import com.hp.hpl.jena.rdf.model._
import com.hp.hpl.jena.util._
import com.hp.hpl.jena.tdb._
// import com.hp.hpl.jena.util.FileManager
 import java.io._
import com.hp.hpl.jena.query._

object RdfUtils {
  
    def getModel(directory : String) : Model = {  
    System.out.println("db = "+directory)
    var model = TDBFactory.createModel(directory)
    return model
    }
    
    def readModel(model : Model, filename : String) = {
  var in = FileManager.get().open(filename) 
  model.read(in, "", "N3")            
    }
    
    def readModelString(model : Model, text : String) = {
    	var in = new ByteArrayInputStream(text.getBytes("UTF-8"));
    	model.read(in, null, "N3")
    	in.close
    }
    
    def writeModel(model : Model, filename : String) = {
      var out = new FileOutputStream(filename) 
      model.write(out,"N3");
      out.close()
    }
    
    def revertModel(model : Model, filename : String) : Unit = {	
    	writeModel(model, filename+"_previous") // just in case
    	model.removeAll()
    	RdfUtils.readModel(model, filename)
    }
    
    def modelToString(model: Model) : String = {   
		var stringOut = new StringWriter();
			model.write(
				stringOut, "N3");
			// http://base
			stringOut.flush();
			stringOut.close();
		return stringOut.toString();
    }
		
 }