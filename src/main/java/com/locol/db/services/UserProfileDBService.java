package com.locol.db.services;

import javax.inject.Inject;

import org.mongodb.morphia.query.Query;

import com.locol.constants.DBConstants;
import com.locol.db.entities.UserProfile;

public class UserProfileDBService {

	@Inject
	private DBConnectionService abstractDBService;

	public void save(UserProfile userProfile) {
		abstractDBService.getDatastore().save(userProfile);
	}

	public boolean isExistingUser(String username, String emailId, String phoneNumber) {
		Query<UserProfile> query = abstractDBService.getDatastore().createQuery(UserProfile.class);
		query.or(query.criteria(DBConstants.USERNAME).equalIgnoreCase(username),
				query.criteria(DBConstants.EMAIL_ID).equalIgnoreCase(emailId),
				query.criteria(DBConstants.PHONE_NUMBER).equalIgnoreCase(phoneNumber));
		long count = query.count();
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}

	public UserProfile getByUsername(String username) {
		return abstractDBService.getDatastore().createQuery(UserProfile.class).field(DBConstants.USERNAME)
				.equalIgnoreCase(username).get();
	}
}
