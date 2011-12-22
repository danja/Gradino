package com.dannyayers.http

import com.dannyayers.core._
import com.dannyayers.templates._
import com.dannyayers.render._ 
import com.dannyayers.rdf._
import com.dannyayers.rdf.Queries

class FeedHandler {
	def renderFeed(query : String) = {
		// Main.foafModel
		if(Main.rssCacheDirty){
			val items = ItemsFinder.getItems(Main.mainModel, query)
			val xml = FeedRenderer.renderItems(items)
			Main.rssCache = xml.toString()
			Main.rssCacheDirty = false
		}
		Main.rssCache
	}
	
	def renderFeed(query : String, uri : String) = {
				val queryString = TemplatingEngine.applyTemplate(Queries.taggedItems, "tagUri",uri)  
				// System.out.println("query ="+queryString)
			val items = ItemsFinder.getItems(Main.mainModel, queryString)
			val xml = FeedRenderer.renderItems(items)
			xml.toString()

	}
	
	
}
