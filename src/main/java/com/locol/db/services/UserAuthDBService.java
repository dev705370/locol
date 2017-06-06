package com.locol.db.services;

import javax.inject.Inject;

import com.locol.constants.DBConstants;
import com.locol.db.entities.UserAuthenticationProfile;

public class UserAuthDBService {

	@Inject
	private DBConnectionService abstractDBService;

	public void save(UserAuthenticationProfile userAuthenticationProfile) {
		abstractDBService.getDatastore().save(userAuthenticationProfile);
	}

	public boolean isExistingUser(String username) {
		long count = abstractDBService.getDatastore().createQuery(UserAuthenticationProfile.class)
				.field(DBConstants.USERNAME).equalIgnoreCase(username).count();
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}

	public UserAuthenticationProfile getUserByUsername(String username) {
		UserAuthenticationProfile authenticationProfile = abstractDBService.getDatastore()
				.createQuery(UserAuthenticationProfile.class).field(DBConstants.USERNAME).equalIgnoreCase(username)
				.get();
		return authenticationProfile;
	}

}
