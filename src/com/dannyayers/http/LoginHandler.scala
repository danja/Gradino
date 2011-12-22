package com.dannyayers.http

import scala.util._
import com.dannyayers.core._


import javax.ws.rs.core._
import com.dannyayers.util._
import com.dannyayers.users._
import org.apache.http.protocol._
import org.apache.http.client.protocol._
import org.apache.http.auth._
import org.apache.http.impl.auth._
import org.apache.http.impl.client._
import org.apache.http.client.methods._
import org.apache.http.util._
import org.apache.http._
import org.apache.http.impl.client._
import org.apache.http.client._
import org.apache.http.impl.client.BasicAuthCache._

class LoginHandler {
	
	def register(user : String, password : String, 
			email : String, foaf : String) = {
		val id = Random.nextInt(1000000).toString	
		val uri = Main.BASE_URI+"/confirm/"+id
		MailSender.send("noreply@hyperdata.org", email, "dannyayers.com registration", uri)
		UserMaker.makeUser(user, password, email, id, "PENDING")
	}
		
	def login(uriInfo : UriInfo) : String = {
		
		var params = uriInfo.getQueryParameters

		var seed = ""
		var seedId = ""
		if(params.getFirst("task").equals("getseed")){
			seedId = Random.nextInt(1000).toString
			seed = Random.nextInt(1000).toString
			 Main.seedMap += seedId -> seed
System.out.println("first params = "+params)
			System.out.println("seedId = "+seedId +" seed = "+seed)
			seedId+"|"+seed
		} else {
			var seedId = params.getFirst("id")
			System.out.println(params)
			val stuff = Main.seedMap(seedId)
			System.out.println(stuff)
		//	System.out.println("sha1 = "+SHA1.hex_sha1(stuff))
		params.toString
		}
	}
	
	def auth(auth : String) : String = {
		System.out.println("Auth="+auth)
		"heyho!"+auth
	}
	
def authProxy(user : String, password : String) = {
		
	val targetHost = new HttpHost("localhost", 80, "http"); 

val httpclient = new DefaultHttpClient();

httpclient.getCredentialsProvider().setCredentials(
        new AuthScope(targetHost.getHostName(), targetHost.getPort()), 
        new UsernamePasswordCredentials(user, password));
 
var authCache = new BasicAuthCache()
var basicAuth = new BasicScheme();
authCache.put(targetHost, basicAuth);

// Add AuthCache to the execution context
var localcontext = new BasicHttpContext();
localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);        

val httpget = new HttpGet("/auth");
val response = httpclient.execute(targetHost, httpget, localcontext);
   val entity = response.getEntity();
    if (entity != null) {
        entity.consumeContent();
         EntityUtils.toString(entity)
    } else {
    	"waah!"
    }

	}
}
