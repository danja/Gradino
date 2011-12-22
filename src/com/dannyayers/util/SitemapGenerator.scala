package com.dannyayers.util

import scala.actors.Actor
import com.dannyayers.core._
import com.dannyayers.core._
import com.dannyayers.templates._
import com.dannyayers.render._
import com.dannyayers.rdf._
import com.dannyayers.rdf.Queries
import com.dannyayers.users._
import com.dannyayers.rdf._
import javax.ws.rs._
import javax.ws.rs.core._
import java.io._
import scala.actors.Actor._

import com.hp.hpl.jena.rdf.model._
import com.hp.hpl.jena.util.FileManager
import java.io._
import com.hp.hpl.jena.query._
import com.dannyayers.rdf._
import org.w3c.tidy._

class SitemapGenerator extends Actor {
  def act() {
    var done = false
    while (!done) {
      react {
        case _ =>

          val items = ItemsFinder.getItems(Main.mainModel, Queries.allItems)
          val xml = SitemapRenderer.renderItems(items)

          try {
            val fstream = new FileWriter(Main.sitemapFilename);
            val out = new BufferedWriter(fstream);
            out.write(xml)
            out.close()
          } catch {
            case e => e.printStackTrace()
          }
          done =true
      }
    
    }
System.out.println("*** SITEMAP GENERATED OK ***")
  }
}