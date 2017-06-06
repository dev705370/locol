package com.locol.user.register.rest;

import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.openejb.util.Strings;

import com.locol.auth.AuthenticatedUser;
import com.locol.auth.Secured;
import com.locol.db.entities.UserProfile;
import com.locol.exceptions.BusinessException;
import com.locol.exceptions.PasswordAlgorithmException;
import com.locol.exceptions.UserAlreadyExistException;
import com.locol.restful.entities.LoginRequest;
import com.locol.restful.entities.UserRegistrationRequest;
import com.locol.user.services.UserService;
import com.locol.validation.ValidationHelper;

@Path("/v1")
public class UserResources {

	@Inject
	private UserService userService;

	@Inject
	@AuthenticatedUser
	UserProfile authenticatedUser;

	@Path("/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(LoginRequest loginRequest) {
		try {
			validateLoginRequest(loginRequest);
			String token;
			token = userService.authenticate(loginRequest);
			return Response.ok(token).build();
		} catch (BusinessException e) {
			return Response.status(Status.UNAUTHORIZED).entity("Username or password is incorrect").build();
		} catch (PasswordAlgorithmException e) {
			return Response.serverError().build();
		}
	}

	@Path("/user")
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@QueryParam("username") String username) {
		// System.out.println(authenticatedUser);
		if (Strings.checkNullBlankString(username)) {
			return Response.status(Status.BAD_REQUEST).entity("Please provide a valid username").build();
		}
		UserProfile userProfile = userService.getUser(username);
		if (userProfile == null) {
			return Response.noContent().entity(username + " user does not exist").build();
		}
		return Response.ok(userProfile).build();
	}

	private void validateLoginRequest(LoginRequest loginRequest) throws BusinessException {
		ValidationHelper validationHelper = new ValidationHelper();
		validationHelper.errorIfNullorEmpty(loginRequest.getUsername(), "Username can't be Empty");
		validationHelper.errorIfNullorEmpty(loginRequest.getPassword(), "Password can't be Empty");
		validationHelper.logIfErrors();
		validationHelper.throwAllErrors();
	}

	@Path("/registor")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response registor(UserRegistrationRequest registrationRequest) {
		validateRegistrationRequest(registrationRequest);
		registrationRequest.setCreatedTs(new Date());
		String username = null;
		try {
			username = userService.register(registrationRequest);
		} catch (UserAlreadyExistException e) {
			// TODO Log
			return Response.status(Status.CONFLICT).entity("{message : user already exist}").build();
		} catch (PasswordAlgorithmException e) {
			return Response.serverError().build();
		}
		return Response.ok("successfully registerred user {}", username).build();
	}

	private void validateRegistrationRequest(UserRegistrationRequest registrationRequest) {
		ValidationHelper validationHelper = new ValidationHelper();
		validationHelper.errorIfNullorEmpty(registrationRequest.getUsername(), "Missing username during regstration");
		validationHelper.errorIfNullorEmpty(registrationRequest.getEmailId(), "Missing emailId during registration");
		validationHelper.errorIfNullorEmpty(registrationRequest.getFullName(), "Missing full name during registration");
		validationHelper.errorIfNullorEmpty(registrationRequest.getGender(), "Missing gender during registration");
		validationHelper.errorIfNullorEmpty(registrationRequest.getMobileNumber(),
				"Missing mobile number during registration");
		validationHelper.errorIfNullorEmpty(registrationRequest.getPassword(), "Missing password during registration");

		// TODO validate mobile number and email id format
		// TODO validate other fields also

		validationHelper.logIfErrors();
	}
}
