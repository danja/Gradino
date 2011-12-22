package com.dannyayers.core

import com.dannyayers.rdf._
import com.dannyayers.http._
import javax.ws.rs.core._
import org.wymiwyg.wrhapi._
import org.trialox.triaxrs.JaxRsHandler
import scala.collection.mutable.HashMap

object Main {

	val BASE_URI = "http://dannyayers.com"
   val MAX_ITEMS = 10
   var seedMap = new HashMap[String, String]

   	  val baseDir = "/home/danny/workspace/gradino"
val wwwDir = "/home/danny/workspace/gradino/www/"
  
  val sitemapFilename = wwwDir + "sitemap.xml"
	  
  val mainDBDir = baseDir + "/data/tdb"
  val mainBackupFilename = baseDir+"/data/main_backup.n3"
   var mainModel = RdfUtils.getModel(mainDBDir)
   
  val userDBDir  = baseDir+"/data/users"
  val userBackupFilename = baseDir+"/data/user_backup.n3"
   var userModel = RdfUtils.getModel(userDBDir)  
   
	  val foafDBDir  = baseDir+"/data/foaf"
  val foafBackupFilename = baseDir+"/data/foaf_backup.n3"
   var foafModel = RdfUtils.getModel(foafDBDir)
   
   var indexCache = ""
   var indexCacheDirty = true
   var rssCache = ""
   var rssCacheDirty = true
      var sitemapCache = ""
   var sitemapCacheDirty = true
   
  def main(args : Array[String]) : Unit = {
        // val inputFileName  = baseDir+"/data/2008-04-20_clean.rdf"
	   
        System.out.println("****** Loading Model ******")
       
        System.out.println("****** Serving ******")
        val serverFactory = WebServerFactory.newInstance();
        val rootResource = new RootResource(mainModel)
        val handler = new JaxRsHandler() {
          registerComponent(rootResource, "")
          // registerComponent(new HelloWorld(), "")
        }
        serverFactory.startNewWebServer(handler, myBindings)
  }
  
  def shutdown() = {
	  mainModel.close()
	  userModel.close()
	  System.out.println("DBs closed, bye bye!")
	  System.exit(0)
  }
  
  
  object myBindings extends ServerBinding() {
	  	override def getInetAddress() = null
		override def getPort() = 8080;
  }

}
