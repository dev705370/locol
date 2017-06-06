package com.locol.restful.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.locol.constants.Constants;

@XmlRootElement(name = Constants.USER_LOGIN_FORM)
public class LoginRequest {

	@XmlElement(name = Constants.USERNAME)
	private String username;

	@XmlElement(name = Constants.PASSWORD)
	private String password;

	@XmlElement(name = Constants.REGISTRATION_RANDOM)
	private String random;

	public LoginRequest() {
		// Default cons
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

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}
}
