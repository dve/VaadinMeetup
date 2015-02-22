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

import de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter;

/**
 * Bean that delivers the current user
 * 
 * @author srothbucher
 * 
 */
public interface UserAuthentication {

	public static final String CURRENT_USER_CONTEXT_KEY = "current_user";

	/**
	 * Process the current request to check whether there is a user for us
	 * 
	 * @param request
	 *            vaadin request
	 * @param context
	 *            context of current UI
	 */
	public void processCurrentRequest(VaadinRequest request,
			Map<String, Object> context);

	/**
	 * Require a user authentication to be performed and the bespoke presenter
	 * be called when we have a user now
	 * 
	 * @param returnPresenter
	 *            presenter to call
	 * @param delay
	 *            millis to wait before redirecting
	 * @param context
	 *            context of current UI
	 */
	public void requireUser(Presenter returnPresenter, int delay,
			Map<String, Object> context);

	/**
	 * Helper to hide away the session (which is no IF)
	 * 
	 * @author srothbucher
	 *
	 */
	public static interface SessionAccess {

		public Object getAttribute(String name);

		public void setAttribute(String name, Object value);

	}

}
