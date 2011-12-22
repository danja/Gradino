// get rid, use RevertDB instead @@TODO

package com.dannyayers.users

import com.hp.hpl.jena.rdf.model._
import com.dannyayers.core._

import com.hp.hpl.jena.util.FileManager
import java.io._
import com.hp.hpl.jena.query._
import scala.xml._
import com.dannyayers.rdf._
import com.dannyayers.http._

object RevertUserDB {
    
  def main(args : Array[String]) : Unit = {
revert
Main.shutdown()
}
	
	  def revert() : Unit = {	
	Main.userModel.removeAll()
	RdfUtils.readModel(Main.userModel, Main.userBackupFilename)
}
}
