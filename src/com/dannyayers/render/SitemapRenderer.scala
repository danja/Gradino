package com.dannyayers.render

import scala.collection.mutable.ListBuffer
import scala.xml.Elem

object SitemapRenderer {
  def renderItems(items : ListBuffer[Map[String, String]]) : String = {
    var xml = """<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9"
        xmlns:news="http://www.google.com/schemas/sitemap-news/0.9">
 <url>
      <loc>http://dannyayers.com/</loc>
      <changefreq>daily</changefreq>
      <priority>0.9</priority>
 </url>
 <url>
      <loc>http://dannyayers.com/archive/older.html</loc>
      <changefreq>monthly</changefreq>
      <priority>0.5</priority>
 </url>
 <url>
      <loc>http://dannyayers.com/archive/2004.html</loc>
      <changefreq>monthly</changefreq>
      <priority>0.5</priority>
 </url>
 <url>
      <loc>http://dannyayers.com/archive/2005.html</loc>
      <changefreq>monthly</changefreq>
      <priority>0.5</priority>
 </url>
 <url>
      <loc>http://dannyayers.com/archive/2006.html</loc>
      <changefreq>monthly</changefreq>
      <priority>0.5</priority>
 </url>
 <url>
      <loc>http://dannyayers.com/archive/2007.html</loc>
      <changefreq>monthly</changefreq>
      <priority>0.5</priority>
 </url>
 <url>
      <loc>http://dannyayers.com/archive/2008.html</loc>
      <changefreq>monthly</changefreq>
      <priority>0.5</priority>
 </url>
 <url>
      <loc>http://dannyayers.com/archive/2009.html</loc>
      <changefreq>monthly</changefreq>
      <priority>0.5</priority>
 </url>
 <url>
      <loc>http://dannyayers.com/archive/2010.html</loc>
      <changefreq>monthly</changefreq>
      <priority>0.5</priority>
 </url>
 <url>
      <loc>http://dannyayers.com/archive/2011.html</loc>
      <changefreq>monthly</changefreq>
      <priority>0.5</priority>
 </url>
"""
      
    items.foreach(  (item : Map[String, String]) =>
     if( item("uri").startsWith("http://dannyayers.com")){
      xml = xml + SitemapItemRenderer.renderItem(item)
  } )

   xml = xml + """

</urlset>
"""
    return xml
  }

  
}
