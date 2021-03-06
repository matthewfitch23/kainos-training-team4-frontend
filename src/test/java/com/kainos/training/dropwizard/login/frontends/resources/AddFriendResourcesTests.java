package com.kainos.training.dropwizard.login.frontends.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.junit.Before;
import org.junit.Test;

import com.kainos.training.blackbox.client.FriendClient;
import com.kainos.training.blackboxinterface.model.person.Person;
import com.kainos.training.jersey.client.BaseClient;

public class AddFriendResourcesTests {
	
	private final static String EMPTY_FRIEND_NAME = "";
	private final static String STANDARD_FRIEND_NAME = "test name";
	private FriendClient mockedFriendClient;
	private ViewsResource resource;
	private Person friend;
	Response okResponse = Response.ok().build();
	Response successResponse = Response.seeOther(
			UriBuilder.fromUri("add-friend-success").build()).build();
	Response notOkResponse = Response.seeOther(
			UriBuilder.fromUri("add-friend-failure").build()).build();
	
	@Before
	public void setup() {
		mockedFriendClient = mock(FriendClient.class);
		friend = new Person();
		friend.setName(STANDARD_FRIEND_NAME);
		// when(mockedFriendClient.addFriend(friend).thenReturn(successResponse));
		
				
		resource = new ViewsResource(new BaseClient(), mockedFriendClient);
	}
	
	@Test
	public void testAddFriendCallsSuccessfully() {
		when(mockedFriendClient.addFriend(any(Person.class))).thenReturn(okResponse);
		Response addFriendResponse = resource.addFriend(friend.getName());
		assertEquals(addFriendResponse.getStatus(), successResponse.getStatus());
		assertEquals(addFriendResponse.getLocation(), successResponse.getLocation());
		

		verify(mockedFriendClient).addFriend(any(Person.class));
	}
	
	@Test
	public void testEmptyNameWhenSubmitting() {
		Response addFriendEmptyResponse = resource.addFriend("");
	
		assertEquals(addFriendEmptyResponse.getStatus(), notOkResponse.getStatus());
		assertEquals(addFriendEmptyResponse.getLocation(), notOkResponse.getLocation());
		verify(mockedFriendClient, never()).addFriend(any(Person.class));
	}
	
	@Test
	public void testAddingDuplicatesToFriendList() {
		
	}
	
	@Test
	public void testNameIsStoredWhenAddingFriend() {
		
	}


}
