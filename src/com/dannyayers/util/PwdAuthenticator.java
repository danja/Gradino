package com.dannyayers.util;

import javax.mail.*;


public class PwdAuthenticator extends Authenticator {
	 
	private PasswordAuthentication passwordAuthentication;
 
	public PwdAuthenticator(String user, String pwd) {
		this.passwordAuthentication = new PasswordAuthentication(user, pwd);
	}
 
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return passwordAuthentication;
	}
 

}
