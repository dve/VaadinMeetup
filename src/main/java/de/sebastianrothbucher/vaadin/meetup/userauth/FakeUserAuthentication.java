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

import java.util.Map;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.JavaScript;

import de.sebastianrothbucher.vaadin.meetup.model.User;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter;

public class FakeUserAuthentication implements UserAuthentication {

	public FakeUserAuthentication() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication#
	 * processCurrentRequest(com.vaadin.server.VaadinRequest, java.util.Map)
	 */
	@Override
	public void processCurrentRequest(VaadinRequest request,
			Map<String, Object> context) {
		if (request.getParameter("fakeuser") != null
				&& request.getParameter("fakemember") != null) {
			context.put(
					UserAuthentication.CURRENT_USER_CONTEXT_KEY,
					new User(request.getParameter("fakeuser"), Boolean
							.parseBoolean(request.getParameter("fakemember"))));
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
		// (no presenter to be called EVER as we re-init the application
		// afterwards => pointless to remember it)
		JavaScript.getCurrent().execute(
				"setTimeout(\"location.href='?fakeuser=Sebastian%20R.&fakemember=true';\", "
						+ delay + ");");
	}

}
