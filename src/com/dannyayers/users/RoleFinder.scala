package com.dannyayers.users

import com.hp.hpl.jena.rdf.model._
import com.dannyayers.core._

import com.hp.hpl.jena.util.FileManager
import java.io._
import com.hp.hpl.jena.query._
import scala.xml._
import com.dannyayers.http._

object RoleFinder {
  
  def getRole(username : String, password : String) : String = {
		  var roleName =""
	 System.out.println("Looking up user = "+username+" password = "+password)
	 
     var queryTemplate = """
     prefix : <http://rdfs.org/sioc/ns#> 
     prefix foaf: <http://xmlns.com/foaf/0.1/> 
     prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> 
     prefix x: <http://purl.org/stuff/access#> 

     SELECT ?roleName WHERE {
    [     a foaf:Person;
      #   foaf:name ?name ;
         foaf:holdsAccount  [
             a :User;
             x:password "%password%" ;
             x:username "%username%" ;
             :function_of  [
                  a :Role;
                       x:roleName ?roleName ;
                       :has_scope <http://dannyayers.com> ] ] ].
      }
      # LIMIT 1
      """ 
      
      var queryString = queryTemplate.replaceAll("%username%", username)
      queryString = queryString.replaceAll("%password%", password)
      // System.out.println(queryString)
      
  var qexec = QueryExecutionFactory.create(queryString, Main.userModel) 
   var results = qexec.execSelect()

  while(results.hasNext()) {
	   var soln = results.nextSolution() 
    val roleNameNode = soln.get("?roleName") 
    
    if(roleNameNode != null) {
    	roleName = roleNameNode.asInstanceOf[Literal].getValue().toString()    
    	System.out.println("roleName = "+roleName)
     }
  }
qexec.close()
roleName
}
}
  
