package com.locol.user.services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.locol.constants.DBConstants;
import com.locol.db.entities.UserAuthenticationProfile;
import com.locol.db.entities.UserProfile;
import com.locol.db.services.TokenDBService;
import com.locol.db.services.UserAuthDBService;
import com.locol.db.services.UserProfileDBService;
import com.locol.exceptions.BusinessException;
import com.locol.exceptions.PasswordAlgorithmException;
import com.locol.exceptions.UserAlreadyExistException;
import com.locol.restful.entities.LoginRequest;
import com.locol.restful.entities.UserRegistrationRequest;
import com.locol.utility.PasswordHelper;
import com.locol.utility.TokenHelper;

public class UserService {

	@Inject
	private UserProfileDBService profileDBService;

	@Inject
	private UserAuthDBService authDBService;

	@Inject
	private TokenDBService tokenDBService;

	public String authenticate(LoginRequest loginRequest) throws BusinessException, PasswordAlgorithmException {
		String username = getBase64Decoded(loginRequest.getUsername());
		UserAuthenticationProfile authenticationProfile = authDBService.getUserByUsername(username);
		if (authenticationProfile == null) {
			throw new BusinessException("User does not exist");
		}
		String password = getBase64Decoded(loginRequest.getPassword());
		try {
			if (PasswordHelper.validatePassword(password, authenticationProfile.getSalt(),
					authenticationProfile.getPassword())) {
				return generateAndStoreToken(username);
			} else {
				throw new BusinessException("Username and/or password is incorrect");
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new PasswordAlgorithmException(e);
		}
	}

	private String generateAndStoreToken(String username) {
		String token = TokenHelper.generateToken();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		Date expireTime = cal.getTime();

		Map<String, Object> searchFields = new HashMap<>();
		searchFields.put(DBConstants.USERNAME, username);
		Map<String, Object> updateFields = new HashMap<>();
		updateFields.put(DBConstants.TOKEN, token);
		updateFields.put(DBConstants.TOKEN_EXPIRES, expireTime);

		tokenDBService.update(searchFields, updateFields);

		return token;
	}

	public String register(UserRegistrationRequest registrationRequest)
			throws UserAlreadyExistException, PasswordAlgorithmException {
		UserProfile userProfile = createUserProfile(registrationRequest);
		UserAuthenticationProfile authenticationProfile = null;
		try {
			authenticationProfile = createAuthProfile(registrationRequest);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO log
			throw new PasswordAlgorithmException(e);
		}
		if (profileDBService.isExistingUser(userProfile.getUsername(), userProfile.getEmailId(),
				userProfile.getMobileNumber()) || authDBService.isExistingUser(authenticationProfile.getUsername())) {
			throw new UserAlreadyExistException();
		}
		profileDBService.save(userProfile);
		authDBService.save(authenticationProfile);
		return registrationRequest.getUsername();
	}

	private UserAuthenticationProfile createAuthProfile(UserRegistrationRequest registrationRequest)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		UserAuthenticationProfile userAuthenticationProfile = new UserAuthenticationProfile();
		userAuthenticationProfile.setUsername(getBase64Decoded(registrationRequest.getUsername()));
		userAuthenticationProfile.setCreatedTs(registrationRequest.getCreatedTs());
		userAuthenticationProfile.setSalt(PasswordHelper.toHex(PasswordHelper.getSalt()));
		userAuthenticationProfile.setPassword(PasswordHelper.generateStorngPasswordHash(
				getBase64Decoded(registrationRequest.getPassword()), userAuthenticationProfile.getSalt()));
		return userAuthenticationProfile;
	}

	private UserProfile createUserProfile(UserRegistrationRequest registrationRequest) {
		UserProfile userProfile = new UserProfile();
		userProfile.setCreatedTs(registrationRequest.getCreatedTs());
		userProfile.setDob(registrationRequest.getDob());
		userProfile.setEmailId(registrationRequest.getEmailId());
		userProfile.setFullName(registrationRequest.getFullName());
		userProfile.setGender(registrationRequest.getGender());
		userProfile.setMobileNumber(registrationRequest.getMobileNumber());
		userProfile.setUsername(getBase64Decoded(registrationRequest.getUsername()));
		return userProfile;
	}

	private String getBase64Decoded(String encodedUsername) {
		return new String(Base64.getMimeDecoder().decode(encodedUsername));
	}

	public UserProfile getUser(String username) {
		return profileDBService.getByUsername(username);
	}

}
