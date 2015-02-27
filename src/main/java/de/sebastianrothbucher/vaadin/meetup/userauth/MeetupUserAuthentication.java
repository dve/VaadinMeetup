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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.JavaScript;

import de.sebastianrothbucher.vaadin.meetup.TechnicalException;
import de.sebastianrothbucher.vaadin.meetup.model.User;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter;

/**
 * Bean (stateless) to handle authentication requests against meetup.com (via
 * OAuth 2). Needs System properties meetup.oauth2.key, meetup.oauth2.secret,
 * meetup.groupid in place to work
 * 
 * @author srothbucher
 * 
 */
public class MeetupUserAuthentication implements UserAuthentication,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CODE_REQUEST_PARAM = "code";

	private static final String LAST_CODE_SESSION_ATTR = "last_code";
	private static final String LAST_USER_SESSION_ATTR = "last_user";

	// TODO: move (e.g. to property file)
	private static final String APPLICATION_ADDRESS = "http://meetup-vaadinhh.rhcloud.com/";

	private static final int VAADIN_GERMANY_GROUPID_PROP = Integer
			.parseInt(System.getProperty("meetup.groupid"));

	private static final String OAUTH_KEY_PROP = System
			.getProperty("meetup.oauth2.key");

	private static final String OAUTH_SECRET_PROP = System
			.getProperty("meetup.oauth2.secret");
	
	private SessionAccess session;

	public MeetupUserAuthentication(SessionAccess session) {
		super();
		this.session = session;
	}

	@Override
	public void processCurrentRequest(VaadinRequest request,
			Map<String, Object> context) {
		User lastUser = (User) session.getAttribute(LAST_USER_SESSION_ATTR);
		if (lastUser != null) {
			// already a user in the session - put it back & no further action
			context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, lastUser);
			return;
		}
		String code = request.getParameter(CODE_REQUEST_PARAM);
		// System.out.println("doing for code=" + code);
		if (code != null) {
			String lastCode = (String) session
					.getAttribute(LAST_CODE_SESSION_ATTR);
			if (lastCode != null && code.equals(lastCode)) {
				// we have inited with this code already - no further action
				return;
			}
			session.setAttribute(LAST_CODE_SESSION_ATTR, code);
			// System.out.println(OAUTH_KEY_PROP);
			// System.out.println(OAUTH_SECRET_PROP);
			// System.out.println(VAADIN_GERMANY_GROUPID_PROP);
			try {
				Map<String, String> tokenInfoHeaders = new HashMap<String, String>();
				tokenInfoHeaders.put("Content-type",
						"application/x-www-form-urlencoded");
				JSONObject tokenInfo = curl(
						"POST",
						"https://secure.meetup.com/oauth2/access",
						tokenInfoHeaders,
						"client_id="
								+ OAUTH_KEY_PROP
								+ "&client_secret="
								+ OAUTH_SECRET_PROP
								+ "&grant_type=authorization_code&redirect_uri="
								+ APPLICATION_ADDRESS + "&code=" + code);
				Map<String, String> bearerHeaders = new HashMap<String, String>();
				bearerHeaders.put("Authorization",
						"Bearer " + tokenInfo.getString("access_token"));
				JSONObject userInfo = curl("GET",
						"https://api.meetup.com/2/member/self/", bearerHeaders);
				JSONObject groupsInfo = curl(
						"GET",
						"https://api.meetup.com/2/groups?member_id="
								+ userInfo.getInt("id"), bearerHeaders);
				boolean isMember = false;
				JSONArray groupsArray = groupsInfo.getJSONArray("results");
				for (int i = 0; i < groupsArray.length(); i++) {
					if (VAADIN_GERMANY_GROUPID_PROP == groupsArray
							.getJSONObject(i).getInt("id")) {
						isMember = true;
						break;
					}
				}
				User res = new User(abbreviateName(userInfo.getString("name")),
						isMember);
				session.setAttribute(LAST_USER_SESSION_ATTR, res);
				context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, res);
			} catch (JSONException e) {
				throw new TechnicalException(e);
			}
		}
	}

	private String abbreviateName(String name) {
		String[] nameParts = name.split(" ");
		String res = "";
		for (int i = 0; i < nameParts.length; i++) {
			if (i < (nameParts.length - 1)) {
				res += (nameParts[i] + " ");
			} else {
				res += (nameParts[i].substring(0, 1) + ".");
			}
		}
		return res;
	}

	private JSONObject curl(String method, String url,
			Map<String, String> headers) throws TechnicalException {
		return curl(method, url, headers, null);
	}

	// (Package protected 4 unit testing)
	JSONObject curl(String method, String url, Map<String, String> headers,
			String data) throws TechnicalException {
		// very simple, low load only ;-)
		HttpURLConnection connection = null;
		OutputStream connectionOut = null;
		InputStream connectionIn = null;
		try {
			connection = (HttpURLConnection) ((new URL(url)).openConnection());
			connection.setDoInput(true);
			connection.setDoOutput(data != null);
			connection.setRequestMethod(method);
			for (String header : headers.keySet()) {
				connection.addRequestProperty(header, headers.get(header));
			}
			connection.connect();
			if (data != null) {
				connectionOut = connection.getOutputStream();
				connectionOut.write(data.getBytes("UTF-8"));
				connectionOut.flush();
			}
			connectionIn = connection.getInputStream();
			BufferedReader buffRead = new BufferedReader(new InputStreamReader(
					connectionIn));
			String jsonString = "";
			String line = buffRead.readLine();
			while (line != null) {
				jsonString += line;
				line = buffRead.readLine();
			}
			JSONObject jsonObj = new JSONObject(jsonString);
			return jsonObj;
		} catch (ProtocolException e) {
			throw new TechnicalException(e);
		} catch (MalformedURLException e) {
			throw new TechnicalException(e);
		} catch (IOException e) {
			throw new TechnicalException(e);
		} catch (JSONException e) {
			throw new TechnicalException(e);
		} finally {
			if (connectionOut != null) {
				try {
					connectionOut.close();
				} catch (Exception exc) {
					// best effort
				}
			}
			if (connectionIn != null) {
				try {
					connectionIn.close();
				} catch (Exception exc) {
					// best effort
				}
			}
			if (connection != null) {
				try {
					connection.disconnect();
				} catch (Exception exc) {
					// best effort
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication#requireUser
	 * (de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter, int,
	 * java.util.Map)
	 */
	@Override
	public void requireUser(Presenter returnPresenter, int delay,
			Map<String, Object> context) {
		// System.out.println(OAUTH_KEY_PROP);
		// kill the current user - so we can have a new one
		session.setAttribute(LAST_USER_SESSION_ATTR, null);
		// (no presenter to be called EVER as we re-init the application
		// afterwards => pointless to remember it)
		JavaScript
				.getCurrent()
				.execute(
						"setTimeout(\"location.href='https://secure.meetup.com/oauth2/authorize?client_id="
								+ OAUTH_KEY_PROP
								+ "&response_type=code&redirect_uri="
								+ APPLICATION_ADDRESS + "';\", " + delay + ");");
	}

}
