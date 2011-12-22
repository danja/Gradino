package com.dannyayers.http

import javax.ws.rs.core._

import com.hp.hpl.jena.tdb._
import java.text.SimpleDateFormat
import scala.xml.Elem
import com.hp.hpl.jena.rdf.model._
import com.dannyayers.rdf._
import com.dannyayers.core._

class QueryHandler {
	def sparql(uriInfo : UriInfo) = {
		val paramMap = uriInfo.getQueryParameters(true)
		val queryString = paramMap.getFirst("query")
		System.out.println("-------------------")
				System.out.println(queryString)
				System.out.println("-------------------")
		val xml = SparqlRunner.runQuery(Main.mainModel, queryString)
		System.out.println(xml)
		xml.toString()
	}
}
