package com.dannyayers.users

import com.hp.hpl.jena.rdf.model._
import com.dannyayers.core._

import com.hp.hpl.jena.util.FileManager
import java.io._
import com.hp.hpl.jena.query._
import scala.xml._
import com.dannyayers.rdf._
import com.dannyayers.http._

object ShowUserRdf {
    
  def main(args : Array[String]) : Unit = {
 		System.out.println(RdfUtils.modelToString(Main.userModel))

}
	
}
  
