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

object ItemRenderer {
	
  def renderItem(item : Map[String, String]) : String = {
     // System.out.println(item("content"))
     System.out.println("rendering item content...")
     var rawContent = item("content").replace("&nbsp;", " ") // @TODO temporary, to work around inadequate input sanitization
  // val content = XML.loadString("<p class=\"entry-content\">"+rawContent+"</p>")
  //  val content = scala.xml.Utility.unescape(item("content"))
    val tagMap = TagsFinder.getTags(item("uri"))
    val related = item("related")
    val editLink = "/edit"+item("uri").substring(21) // @TODO nasty - need to pull out hostname elsewhere
    		
    val comment = item("comment")
    
//     var xml = <div class="hentry"><h2 class="entry-title"><a href={item("uri")}>{item("title")}</a><a href={editLink}><img src="/icons/edit.png" alt="Edit" /></a></h2>
//                  {content}
//                  <p class="author">{item("makerName")}</p>
//                  <p class="published">{item("date")}</p>
//                  <p class="tag">{ renderTags(tagMap) }</p>
//                  <p><a href={ related }>Related</a></p>
//                  <p><a href={ comment }>Comments</a></p>
//               </div>
         var html = "<div class=\"hentry\">" +
         "<h2 class=\"entry-title\"><a href=\""+item("uri")+"\">"+ item("title")+"</a></h2>" +
                  "<div class=\"entry-content\">"+rawContent+"</div><br/>"+
                  "<span class=\"author\">"+item("makerName")+"</span><br/>"+
                  "<span class=\"published\">"+item("date")+"</span><br/>"+
                  "<span class=\"tag\">"+renderTags(tagMap)+"</span><br/>"+
                  "<span><a href=\""+ related + "\">Related</a></span><br/>" +
                  "<span><a href=\""+ comment + "\">Comments</a></span><br/>" +
                  "<a href=\""+editLink+"\"><img src=\"/icons/edit.png\" alt=\"Edit\" /></a>"+
               "</div>"
    return html
  }
  

  private def renderTags(tagMap : HashMap[String, String]) : String = { 
	  var tags = " "
	    // new NodeBuffer()
		  for ((key, value) <- tagMap){
		    tags = tags + "<a href=\""+ key +"\">"+ value +"</a>" + " "
	//	 	  nb.append(<a href={ key }>{ value }</a> )
		// 	  nb.append(<br/> )
		  }
	  return tags
  }

}
