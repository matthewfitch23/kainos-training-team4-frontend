package com.kainos.training.dropwizard.login.frontends.resources;

import io.dropwizard.views.View;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.kainos.training.dropwizard.login.frontends.views.Index;

@Path("/test")
public class ViewsResource {
	
	
	public ViewsResource() {
	}

	@GET
	@Timed
	@Path("/login")
	@Produces(MediaType.TEXT_HTML)	
	public void login() {
		//code goes here.....
	}
	
	@POST
	@Timed
	@Path("login-details")
	@Produces(MediaType.TEXT_HTML)
	public void loginDetails(@FormParam("username") String username,
			 			     @FormParam("password") String password){
		//Code goes here....		
	}
}
