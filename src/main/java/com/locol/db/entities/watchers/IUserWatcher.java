package com.locol.db.entities.watchers;

import java.util.Date;

import org.mongodb.morphia.annotations.PrePersist;

import com.locol.db.entities.IUser;

public interface IUserWatcher<T extends IUser> {

	@PrePersist
	public default void prePersist(T t1) {
		t1.setLastModifiedTs(new Date());
	}
}
