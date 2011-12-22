package com.dannyayers.render

import scala.collection.mutable.ListBuffer

object FeedRenderer {
  def renderItems(items : ListBuffer[Map[String, String]]) : String = {
    var xml = """<?xml version="1.0" encoding="utf-8"?> 
<rdf:RDF 
  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
  xmlns:dc="http://purl.org/dc/elements/1.1/"
  xmlns="http://purl.org/rss/1.0/"
  xmlns:content="http://purl.org/rss/1.0/modules/content/"
> 
  <channel rdf:about="http://dannyayers.com/feed">
    <title>Danny Ayers : Raw Blog</title>
    <link>http://dannyayers.com</link>
    <description>Semantic Web, Linked Data, cute animal stuff</description>
    <dc:creator>Danny Ayers</dc:creator>

    <items>
      <rdf:Seq>
    """
    
    items.foreach((item : Map[String, String]) =>
      xml = xml + RssItemRenderer.renderSeqItem(item))  
   
    xml = xml + """
      </rdf:Seq>
    </items>
  </channel>
"""
    
    items.foreach((item : Map[String, String]) =>
      xml = xml + RssItemRenderer.renderItem(item))

   xml = xml + """

</rdf:RDF>
"""
    return xml
  }
}
