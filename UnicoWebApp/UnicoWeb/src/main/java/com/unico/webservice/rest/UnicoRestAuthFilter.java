package com.unico.webservice.rest;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.unico.webservice.auth.Authentication;

@PreMatching
@Provider
@Priority(Priorities.AUTHENTICATION)
public class UnicoRestAuthFilter implements ContainerRequestFilter  {

	@Inject
	private Authentication auth;

	
	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {

		String authorizationHeader =
				requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		if(authorizationHeader == null) {
			requestContext.abortWith(
					Response.status(Response.Status.UNAUTHORIZED).build());
		}
		
		try {
			auth.authenticate(authorizationHeader);
		} catch (Exception e) {
			requestContext.abortWith(
					Response.status(Response.Status.UNAUTHORIZED).build());
		}
		
	}
}
