package com.dannyayers.render

import scala.collection.mutable.ListBuffer
import scala.xml.Elem

object ArchiveRenderer {
  def renderItems(items : ListBuffer[Map[String, String]]) : String = {
    var xml = """<!doctype html>
<html>
<head>
<title>%title%</title>
</head>
<body>
<h1>%title%</h1>
<p><a href="http://dannyayers.com/">Raw Blog</a></p>
<h4>Other Archives</h4>
<ul>
    <li><a href="/archive/older.html" itemprop="url">pre-2004</a><em>(dates messed up!)</em></li>
    <li><a href="/archive/2004.html" itemprop="url">2004</a></li>
    <li><a href="/archive/2005.html" itemprop="url">2005</a></li>
    <li><a href="/archive/2006.html" itemprop="url">2006</a></li>
    <li><a href="/archive/2007.html" itemprop="url">2007</a></li>
    <li><a href="/archive/2008.html" itemprop="url">2008</a></li>
    <li><a href="/archive/2009.html" itemprop="url">2009</a></li>
    <li><a href="/archive/2010.html" itemprop="url">2010</a></li>
    <li><a href="/archive/2011.html" itemprop="url">2011</a></li>
</ul>
"""
      
    items.foreach(  (item : Map[String, String]) =>
      xml = xml + ArchiveItemRenderer.renderItem(item)
   )

   xml = xml + """
</body>
</html>
"""
    return xml
  }

  
}
