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

object ItemFormRenderer {
	
  def renderItem(item : Map[String, String]) : Elem = {
     // System.out.println(item("content"))
   //  System.out.println("rendering item content...")
     var rawContent = item("content").replace("&nbsp;", " ") // temporary, to work around inadequate input sanitization
     // what's numeric for a nbsp?
     
   val content = XML.loadString("<p class=\"entry-content\">"+rawContent+"</p>")
  //  val content = scala.xml.Utility.unescape(item("content"))
    val tagMap = TagsFinder.getTags(item("uri"))
    
    var tagString =""  // neater way?
    for ((key, value) <- tagMap){
    	tagString = tagString +value+" "
    }
		  
    val related = item("related")
    val comment = item("comment")

    <html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Post</title>

<!-- TinyMCE -->
<script type="text/javascript" src="tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript">
	<!-- CDATA? tinyMCE.init({ mode : "textareas", theme : "advanced" }); -->
</script>
<!-- /TinyMCE -->

</head>
<body>

<h3>Post</h3>

<form method="post" action="/addpost"><!-- http://localhost/test2.php -->

  <label>Author </label><input type="text" name="user" value={item("makerName")} /> 

  <p><label>Password </label><input type="password" name="password" value="******" /></p>

  <label>URI </label><input type="text" name="uri" value={item("uri")} /> 

<label>Delete Entry?</label> <input type="text" name="delete" value="no" />

  <p><label>Title</label> <input type="text" name="title" value= {item("title")} /></p>

  <p><label>Content</label></p>
	<!-- Gets replaced with TinyMCE, remember HTML in a textarea should be encoded -->
	<textarea id="content" name="content" rows="15" cols="80" style="width: 80%">
 {content}
	</textarea>
	<br />
	<p>
	 <label>Tags</label> <input type="text" name="tags" value={ tagString } />
	  </p>
	  <p>
	  <label>Date</label><input type="text" name="date" value= {item("date")} />
</p>
	<input type="submit" value="Submit" />
</form>

</body>
</html>
  }
}
