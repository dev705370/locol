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
import com.locol.db.entities.watchers.UserProfileWatcher;

@Entity("user_profile")
@EntityListeners(UserProfileWatcher.class)
public class UserProfile implements IUser {
	@Id
	private ObjectId _id;

	@Property(value = DBConstants.USERNAME)
	@Indexed(options = @IndexOptions(unique = true))
	private String username;

	@Property(value = "full_name")
	private String fullName;

	@Property(value = "date_of_birth")
	private Date dob;

	@Property(value = DBConstants.EMAIL_ID)
	private String emailId;

	@Property(value = DBConstants.PHONE_NUMBER)
	private String mobileNumber;

	private String gender;

	@Property(value = "created_ts")
	private Date createdTs;

	@Property(value = "last_modified_ts")
	private Date lastModifiedTs;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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

	public ObjectId get_id() {
		return _id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
