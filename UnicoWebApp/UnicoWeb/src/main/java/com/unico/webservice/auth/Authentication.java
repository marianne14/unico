package com.unico.webservice.auth;

import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.unico.webservice.dao.UsersDAO;
import com.unico.webservice.model.User;

@Stateless
public class Authentication {

	@Inject
	private UsersDAO usersDAO;
	
	
	public void authenticate(String authentication) throws Exception {
		final String encodedUserPassword = authentication.replaceFirst("Basic" + " ", "");
		String usernameAndPassword = null;
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword);
			usernameAndPassword = new String(decodedBytes, "UTF-8");
		} catch (IOException e) {
			throw new Exception("Decoding failure.");
		}

		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		User user = usersDAO.getUserInfo(username, password);	
		if(user == null) {
			throw new Exception("Authentication failure");
		}
	}
}
