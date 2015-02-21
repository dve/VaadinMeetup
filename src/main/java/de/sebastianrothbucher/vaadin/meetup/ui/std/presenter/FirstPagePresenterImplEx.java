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
package de.sebastianrothbucher.vaadin.meetup.ui.std.presenter;

import java.util.Map;

import de.sebastianrothbucher.vaadin.meetup.model.User;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageView;
import de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication;

public class FirstPagePresenterImplEx extends FirstPagePresenterImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FirstPagePresenterImplEx(Map<String, Object> context,
			FirstPageView view, PresenterFactoryEx presenterFactory,
			UserAuthentication userAuthentication) {
		super(context, view, presenterFactory);
		this.context = context;
		this.userAuthentication = userAuthentication;
	}

	private Map<String, Object> context;
	private UserAuthentication userAuthentication;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.FirstPagePresenterImpl
	 * #startPresenting()
	 */
	@Override
	public void startPresenting() {
		// we need an authenticated user!
		User user = userAuthentication.getCurrentUser(context);
		if (user == null || (!user.isGroupMember())) {
			userAuthentication.requireUser(this, 2000, context);
			return;
		}
		System.out.println("Welcome, " + user);
		super.startPresenting();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.FirstPagePresenterImpl
	 * #onListTalk()
	 */
	@Override
	public void onListTalk() {
		// we need an authenticated user!
		User user = userAuthentication.getCurrentUser(context);
		if (user == null || (!user.isGroupMember())) {
			userAuthentication.requireUser(this, 2000, context);
			return;
		}
		super.onListTalk();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.FirstPagePresenterImpl
	 * #onAddTalk()
	 */
	@Override
	public void onAddTalk() {
		// the view is supposed to hide this one!
		throw new UnsupportedOperationException();
	}

}
