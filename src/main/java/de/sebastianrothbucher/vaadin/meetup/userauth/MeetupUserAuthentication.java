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

import java.io.Serializable;
import java.util.Map;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.JavaScript;

import de.sebastianrothbucher.vaadin.meetup.model.User;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter;

/**
 * Bean (stateless) to handle authentication requests against meetup.com (via
 * OAuth 2). Needs System properties meetup.oauth2.key and meetup.oauth2.secret
 * in place to work
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

	public MeetupUserAuthentication() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication#
	 * processCurrentRequest(com.vaadin.server.VaadinRequest,
	 * com.vaadin.server.VaadinSession, java.util.Map)
	 */
	@Override
	public void processCurrentRequest(VaadinRequest request,
			VaadinSession session, Map<String, Object> context) {
		if (context.containsKey(UserAuthentication.CURRENT_USER_CONTEXT_KEY)) {
			// already a user in the context - no further action
			return;
		}
		String code = request.getParameter(CODE_REQUEST_PARAM);
		System.out.println("doing for code=" + code);
		if (code != null) {
			String lastCode = (String) session
					.getAttribute(LAST_CODE_SESSION_ATTR);
			if (lastCode != null && code.equals(lastCode)) {
				// we have inited with this code already - no further action
				return;
			}
			// TODO: Arie gegen meetup abfeuern
			System.out.println(System.getProperty("meetup.oauth2.key"));
			System.out.println(System.getProperty("meetup.oauth2.secret"));
			context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, new User(
					"Sebastian R.", true));
			session.setAttribute(LAST_CODE_SESSION_ATTR, code);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication#
	 * getCurrentUser(java.util.Map)
	 */
	@Override
	public User getCurrentUser(Map<String, Object> context) {
		return (User) context.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY);
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
		System.out.println(System.getProperty("meetup.oauth2.key"));
		// (no presenter to be called EVER as we re-init the application
		// afterwards => pointless to remember it)
		JavaScript.getCurrent().execute(
				"setTimeout(\"location.href='http://localhost:8880/fake_meetup.htm';\", "
						+ delay + ");");
	}

}
