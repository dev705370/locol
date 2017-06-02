package com.locol.db.entities.watchers;

import java.util.Date;

import org.mongodb.morphia.annotations.PrePersist;

import com.locol.db.entities.User;

public class UserWatcher {
	@PrePersist
	void prePersist(User user) {
		user.setLastUpdated(new Date());
	}
}
