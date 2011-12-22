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
import org.apache.http.protocol.HTTP;

/**
 */
object Shutdown {

      def main(args : Array[String]) : Unit = {

       val httpclient = new DefaultHttpClient();

        var httpost = new HttpPost("http://localhost/shutdown")

        var nvps = new ArrayList[BasicNameValuePair]
        nvps.add(new BasicNameValuePair("user", "danja"));
        nvps.add(new BasicNameValuePair("password", "sashapooch"));

        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        System.out.println("Calling shutdown...")
        val response = httpclient.execute(httpost);
        val entity = response.getEntity();

        System.out.println("Status: " + response.getStatusLine());
        if (entity != null) {
            entity.consumeContent();
        }
        httpclient.getConnectionManager().shutdown();        
    }
}