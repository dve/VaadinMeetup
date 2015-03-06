package de.sebastianrothbucher.vaadin.meetup.userauth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.vaadin.server.VaadinRequest;

import de.sebastianrothbucher.vaadin.meetup.model.User;

public class FakeUserAuthenticationTest {

	VaadinRequest vaadinRequest;
	FakeUserAuthentication authentication;

	@Before
	public void setUp() {
		vaadinRequest = mock(VaadinRequest.class);
		authentication = new FakeUserAuthentication();
	}

	@Test
	public void testProcessCurrentRequest() {
		when(vaadinRequest.getParameter("fakeid")).thenReturn("88");
		when(vaadinRequest.getParameter("fakeuser")).thenReturn("Homer S.");
		when(vaadinRequest.getParameter("fakemember")).thenReturn("true");
		Map<String, Object> context = new HashMap<String, Object>();
		authentication.processCurrentRequest(vaadinRequest, context);
		assertNotNull(context.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY));
		assertEquals(88,
				((User) context
						.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY))
						.getMeetupId());
		assertEquals("Homer S.",
				((User) context
						.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY))
						.getMeetupShort());
		assertTrue(((User) context
				.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY))
				.isGroupMember());
	}

	@Test
	public void testProcessCurrentRequestIncoplete() {
		when(vaadinRequest.getParameter("fakeuser")).thenReturn("Homer%20S.");
		when(vaadinRequest.getParameter("fakemember")).thenReturn("true");
		Map<String, Object> context = new HashMap<String, Object>();
		authentication.processCurrentRequest(vaadinRequest, context);
		assertNull(context.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY));
	}

}
