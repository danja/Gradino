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

class ArchiveGenerator extends Actor {
  def act() {
    var done = false
    while (!done) {
      react {
        case _ =>
// -------------------
            var query1970 = Queries.dateRangeItems.replaceAll("%year%", "1970")
            System.out.println(query1970)
          var items = ItemsFinder.getItems(Main.mainModel, query1970)
          var xml = ArchiveRenderer.renderItems(items)
          xml = xml.replaceAll("%title%", "Raw Archives pre-2004")

          try {
           var fstream = new FileWriter(Main.wwwDir+"archive/older.html");
           var out = new BufferedWriter(fstream);
            out.write(xml)
            out.close()
          } catch {
            case e => e.printStackTrace()
          }
          System.out.println("*** ARCHIVE older.html GENERATED OK ***")
          // -------------------
          for (year <- 2004 until 2013) {

            var query = Queries.dateRangeItems.replaceAll("%year%", ""+year)
            System.out.println(query)
          items = ItemsFinder.getItems(Main.mainModel, query)
          xml = ArchiveRenderer.renderItems(items)
 xml = xml.replaceAll("%title%", "Raw Archives "+year)
          try {
            var fstream = new FileWriter(Main.wwwDir+"archive/"+year+".html");
            var out = new BufferedWriter(fstream);
            out.write(xml)
            out.close()
          } catch {
            case e => e.printStackTrace()
          }
          System.out.println("*** ARCHIVE "+year+".html GENERATED OK ***")
          }
    
          done =true
      }
    
    }
System.out.println("*** ARCHIVES GENERATED OK ***")
  }
}