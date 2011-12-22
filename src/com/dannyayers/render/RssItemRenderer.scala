package com.dannyayers.render

import org.eclipse.jetty.server.handler.AbstractHandler
import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.Request
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import scala.xml._
import com.hp.hpl.jena.rdf.model._
import com.dannyayers.rdf._

object RssItemRenderer {
  def renderItem(item : Map[String, String]) : Elem = {
    // System.out.println(item("content"))
  //  var encoded = scala.xml.Utility.escape(item("content"))
var encoded = item("content")
// System.out.println("CONTENT="+encoded)
     var xml = 
  <item rdf:about={item("uri")}>
    <title>{item("title")}</title> 
    <link>{item("uri")}</link>
    <content:encoded>{encoded}</content:encoded>
    <dc:date>{item("date")}</dc:date>
    <dc:creator>{item("makerName")}</dc:creator>
  </item>       

    return xml
  }
  
    def renderSeqItem(item : Map[String, String]) : Elem = {
      
      var xml = <rdf:li rdf:resource={item("uri")} />
      return xml
    }
}
