package com.dannyayers.render

import scala.collection.mutable.ListBuffer

import org.eclipse.jetty.server.handler.AbstractHandler
import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.Request
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import scala.xml._
import com.hp.hpl.jena.rdf.model._
import com.dannyayers.rdf._
import com.dannyayers.templates._

/*
need to check markup around http://dannyayers.com/2009/09/19/Another-test
 * was giving unclosed <p> tag
 */
object SeqPageRenderer {
  def renderItems(items : ListBuffer[Map[String, String]]) : String = {
	     items.foreach(item => println(item)) 
    var html = """<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta name="description" content="Danny Ayers' blog, mostly featuring things like Linked Data and the Semantic Web. The Danny from Tideswell, UK now living in Mozzanella, Italy." />
<meta name="keywords" content="Danny, Danny Ayers, Raw Blog, Linked Data, Semantic Web, RDF" />
<link rel="profile" type="application/rdf+xml" title="FOAF" href="http://danny.ayers.name/index.rdf" />
<link rel="alternate" type="application/rdf+xml" title="RSS" href="http://dannyayers.com/feed" />
 <title>Danny</title>
<link rel="stylesheet" href="/css/style.css" type="text/css" media="Screen" />
<!-- link rel="stylesheet" href="http://hyperdata.org/css/tabs.css" type="text/css" media="Screen" / -->
<link rel="stylesheet" href="/css/handheld.css" type="text/css" media="handheld" />
<meta name="viewport" content="width=device-width" /> <!-- Tell mobiles not to do the fake-a-wide-screen thing -->  
<link rel="openid.server" href="http://www.myopenid.com/server" />
<link rel="openid.delegate" href="http://dannyayers.myopenid.com/" />
<meta name="geo.position" content="44.150000;10.3889074" />
<meta name="geo.placename" content="Mozzanella, 55033, Italy" />
<meta name="ICBM" content="44.150000, 10.3889074" />
</head>

<body id="tab1">

<ul id="tabnav">
    <li class="tab1"><a href="/" title="Danny Ayers' Blog">Raw Blog</a></li>
    <li class="tab2"><a href="http://hyperdata.org/danja/" title="my updates on various social sites">Planet Danja</a></li>
    <li class="tab3"><a href="http://hyperdata.org/planet/" title="stuff I'm watching">Planet Raw</a></li>
    <li class="tab4"><a href="http://hyperdata.org/" title="Linked Data related material">hyperdata.org</a></li>
    <li class="tab5"><a href="http://hyperdata.org/wiki/" title="on my personal wiki">Projects</a></li>
    <li class="tab6"><a href="http://hyperdata.org/xmlns/" title="various namespaces/ontologies">Vocabs</a></li>
    <li class="tab7"><a href="http://dannyayers.com/about.html" title="About">About</a></li></ul>
<div id="header">
<h1 id="blog-title">Raw</h1>
<p id="description">being the blog of <a href="http://danny.ayers.name">Danny Ayers</a></p>
</div>

<div id="content"><div id="main">
    """
    items.foreach((item : Map[String, String]) => 
      html = html + ItemRenderer.renderItem(item)) 
html = html +  """
</div>

<div id="sidebar">
""" + 
PlusInclusion.content + ProfileInclusion.content + """
<p><a href="/editor-sparql.html">Search</a> (figure it out - and please only do lightweight queries for now, big ones will kill the server)</p><p>
Powered by <a href="http://hyperdata.org/wiki/wiki/Gradino">Gradino</a>
</p>
<span><a rel="author" href="https://plus.google.com/112609322932428633493?rel=author">Danny Ayers</a> on G+</span>
</div>
""" + 
AdvertInclusion.content + 
ScriptsInclusion.content + """
<a rel="me" href="https://plus.google.com/112609322932428633493"> <img src="//www.google.com/images/icons/ui/gprofile_button-16.png"> </a>
</body>
</html>
"""
    return html
  }
}
