package com.locol.db.entities;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.EntityListeners;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;

import com.locol.constants.DBConstants;
import com.locol.db.entities.watchers.UserAuthenticationProfileWatcher;

@Entity("user_authentication")
@EntityListeners(UserAuthenticationProfileWatcher.class)
public class UserAuthenticationProfile implements IUser {

	@Id
	private ObjectId _id;

	@Property(value = DBConstants.USERNAME)
	@Indexed(options = @IndexOptions(unique = true))
	private String username;

	@Property(value = "password")
	private String password;

	@Property(value = "salt")
	private String salt;

	@Property(value = "created_ts")
	private Date createdTs;

	@Property(value = "last_modified_ts")
	private Date lastModifiedTs;

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public Date getLastModifiedTs() {
		return lastModifiedTs;
	}

	@Override
	public void setLastModifiedTs(Date lastModifiedTs) {
		this.lastModifiedTs = lastModifiedTs;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public ObjectId get_id() {
		return _id;
	}
}
