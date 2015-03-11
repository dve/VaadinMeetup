/*
 * Copyright 2015 Sebastian Rothbucher
 *  
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.sebastianrothbucher.vaadin.meetup.userauth;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.vaadin.server.VaadinRequest;

import de.sebastianrothbucher.vaadin.meetup.TechnicalException;
import de.sebastianrothbucher.vaadin.meetup.model.User;
import de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication.SessionAccess;

public class MeetupUserAuthenticationTest {

	public static interface CurlInterface {
		JSONObject curl(String method, String url, Map<String, String> headers,
				String data) throws TechnicalException;
	}

	VaadinRequest vaadinRequest;
	SessionAccess sessionAccess;
	CurlInterface curlInterface;
	MeetupUserAuthentication authentication;

	@SuppressWarnings("serial")
	@Before
	public void setUp() {
		vaadinRequest = mock(VaadinRequest.class);
		sessionAccess = mock(SessionAccess.class);
		curlInterface = mock(CurlInterface.class);
		// also set configs
		System.setProperty("meetup.groupid", "99");
		System.setProperty("meetup.oauth2.key", "key77");
		System.setProperty("meetup.oauth2.secret", "topsecret77");
		authentication = new MeetupUserAuthentication(sessionAccess) {

			@Override
			JSONObject curl(String method, String url,
					Map<String, String> headers, String data)
					throws TechnicalException {
				return curlInterface.curl(method, url, headers, data);
			}
		};
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testProcessCurrentRequest() throws TechnicalException,
			JSONException {
		when(vaadinRequest.getParameter("code")).thenReturn("0815");
		when(
				curlInterface.curl(anyString(),
						eq("https://secure.meetup.com/oauth2/access"),
						anyMap(), anyString())).thenReturn(
				new JSONObject("{\"access_token\": \"0816\"}"));
		when(
				curlInterface.curl(anyString(),
						eq("https://api.meetup.com/2/member/self/"), anyMap(),
						anyString())).thenReturn(
				new JSONObject("{\"id\": 88, \"name\": \"Homer Simpson\"}"));
		when(
				curlInterface.curl(anyString(),
						eq("https://api.meetup.com/2/groups?member_id=88"),
						anyMap(), anyString())).thenReturn(
				new JSONObject("{\"results\": [{\"id\": 99}]}"));
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

	@SuppressWarnings("unchecked")
	@Test
	public void testProcessCurrentRequestNoMember() throws TechnicalException,
			JSONException {
		when(vaadinRequest.getParameter("code")).thenReturn("0815");
		when(
				curlInterface.curl(anyString(),
						eq("https://secure.meetup.com/oauth2/access"),
						anyMap(), anyString())).thenReturn(
				new JSONObject("{\"access_token\": \"0816\"}"));
		when(
				curlInterface.curl(anyString(),
						eq("https://api.meetup.com/2/member/self/"), anyMap(),
						anyString())).thenReturn(
				new JSONObject("{\"id\": 88, \"name\": \"Homer Simpson\"}"));
		when(
				curlInterface.curl(anyString(),
						eq("https://api.meetup.com/2/groups?member_id=88"),
						anyMap(), anyString())).thenReturn(
				new JSONObject("{\"results\": []}"));
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
		assertFalse(((User) context
				.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY))
				.isGroupMember());
	}

}
