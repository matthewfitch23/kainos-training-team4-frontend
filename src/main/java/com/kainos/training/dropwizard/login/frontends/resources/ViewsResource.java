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
import com.kainos.training.blackbox.client.FriendClient;
import com.kainos.training.blackboxinterface.model.person.Person;
import com.kainos.training.dropwizard.login.frontends.views.AddFriendFailureView;
import com.kainos.training.dropwizard.login.frontends.views.AddFriendSuccessView;
import com.kainos.training.dropwizard.login.frontends.views.AddFriendView;
import com.kainos.training.dropwizard.login.frontends.views.Index;
import com.kainos.training.dropwizard.login.frontends.views.LoginFailureView;
import com.kainos.training.dropwizard.login.frontends.views.LoginSuccessView;
import com.kainos.training.jersey.client.BaseClient;

@Path("/")
public class ViewsResource {

	private BaseClient loginClient;
	private FriendClient friendClient;

	public ViewsResource(BaseClient loginClient) {
		this.loginClient = loginClient;
	}
	
	public ViewsResource(FriendClient friendClient) {
		this.friendClient = friendClient;
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

	@POST
	@Timed
	@Path("add-friend")
	@Produces(MediaType.TEXT_HTML)
	public Response addFriend(@FormParam("name") String name) {
		Person newFriend = new Person();
		newFriend.setName(name);
		Response response = friendClient.addFriend(newFriend);

		if (response.getStatus() == 200) {
			return Response.seeOther(
					UriBuilder.fromUri("add-friend-success").build()).build();
		} else {
			return Response.seeOther(
					UriBuilder.fromUri("add-friend-failure").build()).build();
		}

	}

	@GET
	@Timed
	@Path("/add-friend")
	@Produces(MediaType.TEXT_HTML)
	public View addFriend() {
		return new AddFriendView();
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

	@GET
	@Timed
	@Path("add-friend-success")
	@Produces(MediaType.TEXT_HTML)
	public View addFriendSuccess() {
		return new AddFriendSuccessView();
	}

	@GET
	@Timed
	@Path("add-friend-failure")
	@Produces(MediaType.TEXT_HTML)
	public View addFriendFailure() {
		return new AddFriendFailureView();
	}
}
