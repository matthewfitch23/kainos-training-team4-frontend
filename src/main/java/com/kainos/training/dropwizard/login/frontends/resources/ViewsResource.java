package com.kainos.training.dropwizard.login.frontends.resources;

import io.dropwizard.views.View;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.codahale.metrics.annotation.Timed;
import com.kainos.training.dropwizard.login.frontends.views.Index;
import com.kainos.training.dropwizard.login.frontends.views.LoginFailureView;
import com.kainos.training.dropwizard.login.frontends.views.LoginSuccessView;
import com.kainos.training.jersey.client.BaseClient;

@Path("/")
public class ViewsResource {

	private BaseClient loginClient;

	public ViewsResource(BaseClient loginClient) {
		this.loginClient = loginClient;
	}

	@GET
	@Timed
	@Path("/login")
	@Produces(MediaType.TEXT_HTML)
	public View login() {
		// code goes here to return index.ftl via index.java.....
		// Index.java class extends View superclass
		return new Index();
	}

	@POST
	@Timed
	@Path("login-details")
	@Produces(MediaType.TEXT_HTML)
	public Response loginDetails(@FormParam("username") String username,
			@FormParam("password") String password) {

		Response response = loginClient.getLogin(username, password);

		if (response.getStatus() == 200) {
			return Response.seeOther(
					UriBuilder.fromUri("login-success").build()).build();
			// The other simpler but bad practice way. Would crash if refreshed
			// This is because would not leave login-details
			// return new LoginSuccessView
		} else {
			return Response.seeOther(
					UriBuilder.fromUri("login-failure").build()).build();
		}

	}

	@GET
	@Timed
	@Path("login-success")
	@Produces(MediaType.TEXT_HTML)
	public View loginSuccess() {
		return new LoginSuccessView();
	}

	@GET
	@Timed
	@Path("login-failure")
	@Produces(MediaType.TEXT_HTML)
	public View loginFailure() {
		return new LoginFailureView();
	}
}
