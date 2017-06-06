package com.locol.auth;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.locol.db.entities.UserProfile;
import com.locol.db.services.UserProfileDBService;

@RequestScoped
public class AuthenticatedUserProducer {

	@Produces
	@Dependent
	@AuthenticatedUser
	private UserProfile authenticatedUser;

	@Inject
	private UserProfileDBService userProfileDBService;

	public void handleAuthenticationEvent(@Observes @AuthenticatedUser String username) {
		this.authenticatedUser = findUser(username);
	}

	private UserProfile findUser(String username) {
		return userProfileDBService.getByUsername(username);
	}
}
