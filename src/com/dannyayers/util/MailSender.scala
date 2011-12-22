package com.dannyayers.util

import javax.mail._
import javax.mail.internet._
import java.util.Properties._


object MailSender {

val properties = System.getProperties
properties.put("mail.smtp.host", "mail.hyperdata.org")
properties.put("mail.smtp.auth", "true")

val authenticator = new PwdAuthenticator("danja", "nemesis");
var session = Session.getDefaultInstance(properties, authenticator);
// session.setDebug(true);

 val message = new MimeMessage(session)

def send(from : String, to : String, subject : String, body : String) = {
	message.setFrom(new InternetAddress(from))
	message.setRecipients(Message.RecipientType.TO, to)
	message.setSubject(subject)
	message.setText(body)

	Transport.send(message)
}

  def main(args : Array[String]) : Unit = {
	  send("danja@hyperdata.org", "danny.ayers@gmail.com", "test from gradino", "hello danny")
  }
}
