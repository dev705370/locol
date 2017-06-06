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
import com.locol.db.entities.watchers.UserTokenWatcher;

@Entity("user_token")
@EntityListeners(UserTokenWatcher.class)
public class UserToken implements IUser {

	@Id
	private ObjectId _id;

	@Property(value = DBConstants.USERNAME)
	@Indexed(options = @IndexOptions(unique = true))
	private String username;

	@Property(value = DBConstants.TOKEN)
	private String token;

	@Property(value = DBConstants.TOKEN_EXPIRES)
	private Date expires;

	@Property(value = "last_modified_ts")
	private Date lastModifiedTs;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLastModifiedTs() {
		return lastModifiedTs;
	}

	@Override
	public void setLastModifiedTs(Date lastModifiedTs) {
		this.lastModifiedTs = lastModifiedTs;
	}

	public ObjectId get_id() {
		return _id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

}
