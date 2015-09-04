package com.kainos.training.dropwizard.login.frontends.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.junit.Before;
import org.junit.Test;

import com.kainos.training.blackbox.client.FriendClient;
import com.kainos.training.jersey.client.BaseClient;

public class ViewsResourceTest {

	private final static String CORRECT_USERNAME = "admin";
	private final static String CORRECT_PASSWORD = "password";
	private final static String INCORRECT_USERNAME = "incorrect";
	
	private BaseClient mockedClient;
	Response okResponse = Response.ok().build();
	Response notOKResponse = Response.status(Status.UNAUTHORIZED).build();
	private ViewsResource resource;

	@Before
	public void setup() {
		mockedClient = mock(BaseClient.class);
		when(mockedClient.getLogin(CORRECT_USERNAME, CORRECT_PASSWORD))
				.thenReturn(okResponse);
		resource = new ViewsResource(mockedClient, new FriendClient());
	}

	@Test
	public void testLoginCallsLoginClientSuccessfully() {
		resource.loginDetails(CORRECT_USERNAME, CORRECT_PASSWORD);

		verify(mockedClient).getLogin(CORRECT_USERNAME, CORRECT_PASSWORD);
	}

	@Test
	public void testLoginRedirectToSuccessWhenOKReturned() {
		Response actualResponse = resource.loginDetails(CORRECT_USERNAME,
				CORRECT_PASSWORD);
		Response expectedResponse = Response.seeOther(
				UriBuilder.fromUri("login-success").build()).build();

		assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
		assertEquals(expectedResponse.getLocation(),
				actualResponse.getLocation());

	}
	
	@Test
	public void testLoginRedirectToFailure() {
		when(mockedClient.getLogin(INCORRECT_USERNAME , CORRECT_PASSWORD)).thenReturn(notOKResponse);
		
		Response actualResponse = resource.loginDetails(INCORRECT_USERNAME,
				CORRECT_PASSWORD);
		Response expectedResponse = Response.seeOther(
				UriBuilder.fromUri("login-failure").build()).build();

		assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
		assertEquals(expectedResponse.getLocation(),
				actualResponse.getLocation());

	}
	
}
