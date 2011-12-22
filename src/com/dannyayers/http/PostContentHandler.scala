package com.dannyayers.http

import java.util.Date
import com.dannyayers.core._
import java.text.SimpleDateFormat
import javax.ws.rs._
import javax.ws.rs.core._
import com.hp.hpl.jena.rdf.model._
import com.hp.hpl.jena.tdb._
import com.dannyayers.core._
import com.dannyayers.rdf._
import com.dannyayers.render._
import com.dannyayers.users._
import com.dannyayers.util._

class PostContentHandler {
	def handle(user : String, password : String,
			   title: String,  uri: String, delete: String, content: String, tags : String, date : String) = {
		System.out.println("handling POST")
		val role = RoleFinder.getRole(user,password)
		System.out.println("Role = "+role)
			System.out.println("delete= "+delete)
		if (role == "") {
			throw new WebApplicationException(Response.Status.UNAUTHORIZED)
			<em>Unauthorized</em>.toString 
		} else {
		  if((uri != null) && (uri !="")) {
		    if( delete == "yes") {
		              var item = Main.mainModel.createResource(uri) 
		              Main.mainModel.removeAll(item, null, null)
		    } else {
		    insertNamedItem(uri, title, content, user, tags, date)
		    }
		  } else {
		    insertItem(title, content, user, tags, date)
		  }
			
//			val outputFileName  = Main.baseDir+"/data/2008-04-20_clean.rdf" // 2008-04-20_clean.rdf
//			RdfUtils.writeModel(model, outputFileName)
			TDB.sync(Main.mainModel)
		//	val outputFileName2  = Main.baseDir+"/data/backup.rdf" // 2008-04-20_clean.rdf
		//	RdfUtils.writeModel(Main.mainModel, Main.mainBackupFilename)
			Main.indexCacheDirty = true
			Main.rssCacheDirty = true
			<em>post accepted</em>.toString 
		}
	}

	private def insertItem(title: String, content: String, makerName : String, tags : String, existingDate : String) = {
	          val uri = buildUri(title)
	          insertNamedItem(uri, title, content, makerName, tags, existingDate)
	}
	

	private def insertNamedItem(uri: String, title: String, content: String, makerName : String, tags : String, existingDate : String) = {
        // System.out.println("inserting item")
		// ?title ?content ?date ?makerName ?related ?comment
		val date =
			if( existingDate=="" || existingDate == null ){
				getDate()
			} else existingDate
		

        var item = Main.mainModel.createResource(uri) 
        Main.mainModel.removeAll(item, null, null)
        
        var itemType = Main.mainModel.createResource("http://purl.org/rss/1.0/item")
        var maker = Main.mainModel.createResource()
               
        Main.mainModel.add(item, Main.mainModel.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), itemType)
        Main.mainModel.add(item, Main.mainModel.createProperty("http://purl.org/rss/1.0/title"), title)
        Main.mainModel.add(item, Main.mainModel.createProperty("http://planetrdf.com/ns/content"), content)
        Main.mainModel.add(item, Main.mainModel.createProperty("http://purl.org/dc/elements/1.1/date"), date)
        Main.mainModel.add(item, Main.mainModel.createProperty("http://xmlns.com/foaf/0.1/maker"), maker)
        Main.mainModel.add(maker, Main.mainModel.createProperty("http://xmlns.com/foaf/0.1/name"), makerName)
        
        doTags(item, tags)
        System.out.println("item inserted")
	}
	
	private def doTags(item : Resource, tags : String) = {
		val tagNS = "http://www.holygoat.co.uk/owl/redwood/0.1/tags/"
		val tagArray = tags.split(" ")
		
		for(tag <- tagArray) {
			val tagResource = Main.mainModel.createResource(Main.BASE_URI+"/tag/"+tag)
			val tagType = Main.mainModel.createResource(tagNS+"Tag")
			// create the Tag itself
			Main.mainModel.add(tagResource, Main.mainModel.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), tagType)
			Main.mainModel.add(tagResource, Main.mainModel.createProperty(tagNS+"tagName"), tag)
			// associate the tag with the post
			Main.mainModel.add(item, Main.mainModel.createProperty(tagNS+"taggedWithTag"), tagResource)
		}
		
	}
	
		private def buildUri(title : String) : String = {
        val now = new Date();
        val sdf = new SimpleDateFormat("yyyy/MM/dd/");
         
		var uri = Main.BASE_URI+"/"+ sdf.format( now )
		var stub = title.replaceAll(" ","-")
		uri = uri+stub
		// System.out.println("URI for POSTed item = "+uri)
		return uri
	}
      
	private def getDate() : String = {
        val now = new Date();
        val sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+01:00");
        return sdf.format( now );
	}
}
