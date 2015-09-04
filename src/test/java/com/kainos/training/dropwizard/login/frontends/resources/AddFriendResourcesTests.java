package com.kainos.training.dropwizard.login.frontends.resources;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.kainos.training.blackbox.client.FriendClient;
import com.kainos.training.blackboxinterface.model.person.Person;

public class AddFriendResourcesTests {
	
	private final static String EMPTY_FRIEND_NAME = "";
	private final static String STANDARD_FRIEND_NAME = "test name";
	private FriendClient mockedFriendClient;
	private ViewsResource resource;
	private Person friend;
	Response okResponse = Response.ok().build();
	
	@Before
	public void setup() {
		mockedFriendClient = mock(FriendClient.class);
		friend = new Person();
		friend.setName(STANDARD_FRIEND_NAME);
		when(mockedFriendClient.addFriend(friend)).thenReturn(okResponse);
				
		resource = new ViewsResource(mockedFriendClient);
	}
	
	@Test
	public void testNameCapturedWhenEnteredToAddAFriend() {	
	}
	
	@Test
	public void testEmptyNameWhenSubmitting() {
		//	
	}
	
	@Test
	public void testAddingDuplicatesToFriendList() {
		
	}
	
	@Test
	public void testNameIsStoredWhenAddingFriend() {
		
	}


}
