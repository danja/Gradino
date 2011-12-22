package com.dannyayers.users

import com.dannyayers.http._
import com.dannyayers.core._
import com.dannyayers.rdf._

object UserMaker {
	
	def makeUser(user : String, password : String, 
			email : String, id : String, rolename : String) : Unit = {
		
     var userTemplate = """
     prefix : <http://rdfs.org/sioc/ns#> 
     prefix foaf: <http://xmlns.com/foaf/0.1/> 
     prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> 
     prefix x: <http://purl.org/stuff/access#> 

    [     a foaf:Person;
         foaf:holdsAccount  [
             a :User;              
            x:username "%user%" ;
            x:password "%password%" ; 
            x:id "%id%";
             :function_of  [
                  a :Role;
                       x:roleName "%rolename%" ;
                       :has_scope <http://dannyayers.com> ] ] ].
      """ 
    var userString = userTemplate.replaceAll("%user%", user)
    userString = userString.replaceAll("%password%", password)
    userString = userString.replaceAll("%id%", id)
    userString = userString.replaceAll("%rolename%", rolename)
    
    System.out.println(RdfUtils.modelToString(Main.userModel))
    RdfUtils.readModelString(Main.userModel, userString)
    System.out.println(RdfUtils.modelToString(Main.userModel))
}
}
