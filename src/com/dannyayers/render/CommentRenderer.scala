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
import scala.collection.mutable.HashMap

object CommentRenderer {
	
  def renderItem(item : Map[String, String]) : Elem = {
     // System.out.println(item("content"))
     System.out.println("rendering item content...")
     var rawContent = item("content").replace("&nbsp;", " ") // temporary, to work around inadequate input sanitization
   val content = XML.loadString("<p class=\"entry-content\">"+rawContent+"</p>")
  //  val content = scala.xml.Utility.unescape(item("content"))
    val tagMap = TagsFinder.getTags(item("uri"))
    val related = item("related")
    val editLink = "edit/"+item("uri").substring(21) // nasty - need to pull out hostname elsewhere
    		
    val comment = item("comment")
     var xml = <div class="hentry"><h2 class="entry-title"><a href={item("uri")}>{item("title")}</a><a href={editLink}><img src="/icons/edit.png" /></a></h2>
                  {content}
                  <p class="author">{item("makerName")}</p>
                  <p class="published">{item("date")}</p>
                  <p class="tag">{ renderTags(tagMap) }</p>
                  <p><a href={ related }>Related</a></p>
                  <p><a href={ comment }>Comments</a></p>
               </div>
    return xml
  }
  

  private def renderTags(tagMap : HashMap[String, String]) : NodeBuffer = { 
	  var nb = new NodeBuffer()
		  for ((key, value) <- tagMap){
		 	  nb.append(<a href={ key }>{ value }</a> )
		  }
	  return nb
  }

}
