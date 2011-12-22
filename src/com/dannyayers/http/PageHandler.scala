package com.dannyayers.http

import com.dannyayers.templates._
import com.dannyayers.core._

import javax.ws.rs._
import javax.ws.rs.core._
import com.dannyayers.render._
import com.dannyayers.rdf._

class PageHandler {
	def renderIndexPage = {
    	if(Main.indexCacheDirty) {
    		val items = ItemsFinder.getItems(Main.mainModel, Queries.recentItems)
    		val html = SeqPageRenderer.renderItems(items) 
    		Main.indexCache = html.toString()
    		Main.indexCacheDirty = false
    	} 
    	Main.indexCache
	}	
	
	def renderTagPage(tagUri : String) = {
		//  val queryString = Queries.taggedItems.replaceAll("%tagUri%", "<"+tagUri+">")
		
		val queryString = TemplatingEngine.applyTemplate(Queries.taggedItems, "tagUri", tagUri)
		
    		val items = ItemsFinder.getItems(Main.mainModel, queryString)
    		SeqPageRenderer.renderItems(items) 
	}
	

	

	def renderItem(uriInfo : UriInfo) = {
// new WebApplicationException(Response.Status.UNAUTHORIZED)
    	try{
		val requestUri = uriInfo.getAbsolutePath()
		val uri = Main.BASE_URI+requestUri.getPath
		// System.out.println("uri = "+uri)
		val item = ItemFinder.getItem(Main.mainModel, uri)
		val html = ItemPageRenderer.renderItemPage(item)
		html.toString()
    	} catch {
    		  case e => {
    		 	  System.out.println("should be 404")
    		 	  throw new WebApplicationException(Response.Status.NOT_FOUND)
    		  }
    		   "404"
    	}
	}

	def renderItemForm(uri: String) = {
    	try{
		// val uri = Main.BASE_URI+path
		val item = ItemFinder.getItem(Main.mainModel, uri)
		val html = ItemFormRenderer.renderItem(item)
		html.toString()
    	} catch {
    		  case e => {
    		 	  println("404")
    		 	//  var response =  Response.ResponseBuilder.create(Response.Status.NOT_FOUND)
    		 	  throw new WebApplicationException(Response.Status.NOT_FOUND)
    		  }
    		   "404"
    	}
	}
		
	def renderComment(path: String) = {
    	try{
		val uri = Main.BASE_URI+path
		val item = ItemFinder.getItem(Main.mainModel, uri)
		val html = CommentRenderer.renderItem(item)
		html.toString()
    	} catch {
    		  case e => {
    		 	  println("404")
    		 	  throw new WebApplicationException(Response.Status.NOT_FOUND)
    		  }
    		   "404"
    	}
	}
	/*
		def renderTemplate(uriInfo : UriInfo) = {
// new WebApplicationException(Response.Status.UNAUTHORIZED)
    	try{
		val requestUri = uriInfo.getAbsolutePath()
		val uri = Main.BASE_URI+requestUri.getPath
		// System.out.println("uri = "+uri)
		val item = ItemFinder.getItem(Main.mainModel, uri)
		val html = ItemPageRenderer.renderItemPage(item)
		html.toString()
    	} catch {
    		  case e => {
    		 	  System.out.println("should be 404")
    		 	  throw new WebApplicationException(Response.Status.NOT_FOUND)
    		  }
    		   "404"
    	}
	}
	*/
}
