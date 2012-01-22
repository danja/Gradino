package com.dannyayers.http

import com.dannyayers.core._
import com.dannyayers.core._
import com.dannyayers.templates._
import com.dannyayers.render._ 
import com.dannyayers.rdf._
import com.dannyayers.rdf.Queries
import com.dannyayers.users._
import com.dannyayers.rdf._
import javax.ws.rs._
import javax.ws.rs.core._
import java.io._
import com.dannyayers.util._

class AdminHandler {
	def shutdown(user : String, password : String) = {
		System.out.println("Shutting down...")
		val role = RoleFinder.getRole(user,password)
		System.out.println("Role = "+role)
		if (role == "") {
			throw new WebApplicationException(Response.Status.UNAUTHORIZED)
			<em>Unauthorized</em>.toString 
		} else {
			Main.shutdown
		}
	}
	
	def backupUserDB() = {
		System.out.println("Writing user db...")
		RdfUtils.writeModel(Main.userModel, Main.userBackupFilename)
		"ok"
	}
	
	def revertUserDB() = {
		System.out.println("Reverting user db...")
		RdfUtils.revertModel(Main.userModel, Main.userBackupFilename)
		"ok"
	}

	def backupContentDB() = {
		System.out.println("Writing content db...")
		RdfUtils.writeModel(Main.mainModel, Main.mainBackupFilename)
		"ok"
	}

	def revertContentDB() = {
		System.out.println("Reverting content db...")
		RdfUtils.revertModel(Main.mainModel, Main.mainBackupFilename)
		"ok"
	}
	
	def generateSitemap() = {
	  	System.out.println("Generating Sitemap...")
	  val sitemapGenerator = new SitemapGenerator

  sitemapGenerator.start
  sitemapGenerator ! "go"
  
	  
	  	"ok."
	}
	
	def generateArchives() = {
	  	System.out.println("Generating Archives...")
	  val archiveGenerator = new ArchiveGenerator

  archiveGenerator.start
  archiveGenerator ! "go"
  
	  
	  	"ok."
	}
}
