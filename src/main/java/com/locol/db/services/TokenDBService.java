package com.locol.db.services;

import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.locol.constants.DBConstants;
import com.locol.db.entities.UserToken;

public class TokenDBService {

	@Inject
	private DBConnectionService abstractDBService;

	public void update(Map<String, Object> searchFields, Map<String, Object> updateFields) {
		Query<UserToken> searchQuery = abstractDBService.getDatastore().createQuery(UserToken.class);
		for (Entry<String, Object> entry : searchFields.entrySet()) {
			searchQuery = searchQuery.field(entry.getKey()).equalIgnoreCase(entry.getValue());
		}
		UpdateOperations<UserToken> updateOperations = abstractDBService.getDatastore()
				.createUpdateOperations(UserToken.class);
		for (Entry<String, Object> entry : updateFields.entrySet()) {
			updateOperations = updateOperations.set(entry.getKey(), entry.getValue());
		}
		for (Entry<String, Object> entry : searchFields.entrySet()) {
			updateOperations = updateOperations.setOnInsert(entry.getKey(), entry.getValue());
		}
		abstractDBService.getDatastore().update(searchQuery, updateOperations, true);
	}

	public UserToken getUserTokenByToken(String token) {
		return abstractDBService.getDatastore().createQuery(UserToken.class).field(DBConstants.TOKEN)
				.equalIgnoreCase(token).get();
	}

}
