package com.dannyayers.render

import scala.collection.mutable.HashMap
import scala.xml.Elem
import scala.xml.NodeBuffer

import com.dannyayers.rdf.TagsFinder

object SitemapItemRenderer {
  def renderItem(item : Map[String, String]) : Elem = {
  
// val tagMap = TagsFinder.getTags(item("uri"))

     var xml =      
  <url>
    <loc>{item("uri")}</loc>
      <lastmod>{item("date").substring(0,10)}</lastmod>
      <changefreq>monthly</changefreq>
  </url>
    return xml
  }
}
