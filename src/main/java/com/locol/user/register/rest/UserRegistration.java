package com.locol.user.register.rest;

import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.locol.db.entities.User;
import com.locol.db.services.DBService;

@Path("")
public class UserRegistration {

	@Inject
	private DBService dbService;

	@Path("/registor")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response registor(@QueryParam("name") String name, @QueryParam("dob") long dobEpochs) {
		User user = new User();
		user.setName(name);
		user.setDateOfBirth(new Date(dobEpochs));
		return Response.ok(dbService.save(user)).build();
	}

	@Path("/")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response ok() {
		return Response.ok("App is up").build();
	}

	@Path("/users")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		return Response.ok().entity(dbService.getAll(User.class)).build();
	}
}
