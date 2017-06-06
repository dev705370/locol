package com.locol.auth;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.locol.db.entities.UserToken;
import com.locol.db.services.TokenDBService;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	@Inject
	private TokenDBService tokenDBService;

	@Inject
	@AuthenticatedUser
	Event<String> userAuthenticatedEvent;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// Get the HTTP Authorization header from the request
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// Check if the HTTP Authorization header is present and formatted
		// correctly
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new NotAuthorizedException("Authorization header must be provided");
		}

		// Extract the token from the HTTP Authorization header
		String token = authorizationHeader.substring("Bearer".length()).trim();

		try {

			// Validate the token
			validateToken(token);

		} catch (Exception e) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

	private void validateToken(String token) {
		UserToken userToken = tokenDBService.getUserTokenByToken(token);
		if (userToken == null || isTokenExpired(userToken)) {
			throw new NotAuthorizedException("Unauthorize exception");
		}
		 userAuthenticatedEvent.fire(userToken.getUsername());
	}

	private boolean isTokenExpired(UserToken userToken) {
		return userToken.getExpires().toInstant().isBefore(new Date().toInstant());
	}

}
