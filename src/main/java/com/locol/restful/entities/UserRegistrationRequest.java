package com.locol.restful.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.locol.constants.Constants;

@XmlRootElement(name = Constants.USER_REGESTRATION_FORM)
public class UserRegistrationRequest {

	@XmlElement(name = Constants.USERNAME)
	private String username;

	@XmlElement(name = Constants.PASSWORD)
	private String password;

	@XmlElement(name = Constants.FULL_NAME)
	private String fullName;

	@XmlElement(name = Constants.DOB)
	private Date dob;

	@XmlElement(name = Constants.EMAIL_ID)
	private String emailId;

	@XmlElement(name = Constants.MOBILE_NO)
	private String mobileNumber;

	@XmlElement(name = Constants.GENDER)
	private String gender;

	@XmlElement(name = Constants.REGISTRATION_RANDOM)
	private String randomString;

	private Date createdTs;

	public UserRegistrationRequest() {
		// Empty Constructor
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

	public String getRandomString() {
		return randomString;
	}

	public void setRandomString(String randomString) {
		this.randomString = randomString;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

}
