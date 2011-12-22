package com.dannyayers.client

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol._
import org.apache.http.client.protocol._
import org.apache.http.auth._

/**
 */
object TestAuth {

      def main(args : Array[String]) : Unit = {

       val httpclient = new DefaultHttpClient()

       var localContext = new BasicHttpContext()
val httpget = new HttpGet("http://localhost/auth"); 
val response = httpclient.execute(httpget, localContext)

val targetAuthState = localContext.getAttribute(
    ClientContext.TARGET_AUTH_STATE).asInstanceOf[AuthState]
System.out.println("Target auth scope: " + targetAuthState.getAuthScope());
System.out.println("Target auth scheme: " + targetAuthState.getAuthScheme());
System.out.println("Target auth credentials: " + targetAuthState.getCredentials());


        httpclient.getConnectionManager().shutdown();        
    }
}