package com.dannyayers.render

import scala.collection.mutable.HashMap
import scala.xml.Elem
import scala.xml.NodeBuffer

import com.dannyayers.rdf.TagsFinder

object ArchiveItemRenderer {
  def renderItem(item : Map[String, String]) : Elem = {

     var xml =      
  <li>
    {item("date")} <a href={item("uri")}>{item("title")}</a>
  </li>
    return xml
  }
}
