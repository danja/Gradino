package com.dannyayers.users

import com.hp.hpl.jena.rdf.model._
import com.dannyayers.core._

import com.hp.hpl.jena.util.FileManager
import java.io._
import com.hp.hpl.jena.query._
import scala.xml._
import com.dannyayers.rdf._
import com.dannyayers.http._

object ListUsers {
    
  def main(args : Array[String]) : Unit = {

     var query = """
     prefix : <http://rdfs.org/sioc/ns#> 
     prefix foaf: <http://xmlns.com/foaf/0.1/> 
     prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> 
     prefix x: <http://purl.org/stuff/access#> 

     SELECT ?username ?password ?roleName WHERE {
    [     a foaf:Person;
         foaf:holdsAccount  [
             a :User;
             x:password ?password ;
             x:username ?username ;
             :function_of  [
                  a :Role;
                       x:roleName ?roleName ;
                       :has_scope <http://dannyayers.com> ] ] ].
      }
      """ 
      
  var qexec = QueryExecutionFactory.create(query, Main.userModel) 
   var results = qexec.execSelect()

  while(results.hasNext()) {
	   var soln = results.nextSolution() 

    val nameNode = soln.get("?username") 
    System.out.print(nameNode.asInstanceOf[Literal].getValue().toString()+"  ")  
    val passwordNode = soln.get("?password") 
    System.out.print(passwordNode.asInstanceOf[Literal].getValue().toString()+"  ")  
    val roleNameNode = soln.get("?roleName") 
    System.out.print(roleNameNode.asInstanceOf[Literal].getValue().toString())    
      }
qexec.close()
Main.userModel.close()

}
}
  
