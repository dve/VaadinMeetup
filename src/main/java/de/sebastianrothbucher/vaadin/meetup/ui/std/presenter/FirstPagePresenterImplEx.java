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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.sebastianrothbucher.vaadin.meetup.model.Imprint;
import de.sebastianrothbucher.vaadin.meetup.model.User;
import de.sebastianrothbucher.vaadin.meetup.service.ImprintService;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageViewEx;
import de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication;

public class FirstPagePresenterImplEx extends FirstPagePresenterImpl implements
		FirstPageViewEx.Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FirstPagePresenterImplEx(Map<String, Object> context,
			FirstPageViewEx view, PresenterFactoryEx presenterFactory,
			ImprintService imprintService, UserAuthentication userAuthentication) {
		super(context, view, presenterFactory);
		this.context = context;
		this.view = view;
		this.imprintService = imprintService;
		this.userAuthentication = userAuthentication;
	}

	private Map<String, Object> context;
	private FirstPageViewEx view;
	private ImprintService imprintService;
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
		super.startPresenting();
		// we need an authenticated user!
		User user = (User) context.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY);
		if (user == null) {
			// no user - need to logon
			view.setLogonHintVisible(true);
			view.setMembershipHintVisible(false);
			view.setLogonButtonVisible(true);
			view.setTalksVisible(false);
			view.setBreakoutsVisible(false);
			view.setUserName(null);
		} else if (!user.isGroupMember()) {
			// user is no member - too bad
			view.setLogonHintVisible(false);
			view.setMembershipHintVisible(true);
			view.setLogonButtonVisible(true);
			view.setTalksVisible(false);
			view.setBreakoutsVisible(false);
			view.setUserName(user.getMeetupShort());
		} else {
			// yeah - we can go
			view.setLogonHintVisible(false);
			view.setMembershipHintVisible(false);
			view.setLogonButtonVisible(false);
			view.setTalksVisible(true);
			view.setBreakoutsVisible(true);
			view.setUserName(user.getMeetupShort());
		}
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
		User user = (User) context.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY);
		if (user == null || (!user.isGroupMember())) {
			view.showErrorMessage("Not logged on!");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.FirstPagePresenterImpl
	 * #onListBreakout()
	 */
	@Override
	public void onListBreakout() {
		// we need an authenticated user!
		User user = (User) context
				.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY);
		if (user == null || (!user.isGroupMember())) {
			view.showErrorMessage("Not logged on!");
			return;
		}
		super.onListBreakout();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.FirstPagePresenterImpl
	 * #onAddBreakout()
	 */
	@Override
	public void onAddBreakout() {
		// we need an authenticated user!
		User user = (User) context
				.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY);
		if (user == null || (!user.isGroupMember())) {
			view.showErrorMessage("Not logged on!");
			return;
		}
		super.onAddBreakout();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.FirstPagePresenterImpl
	 * #getView()
	 */
	@Override
	public FirstPageViewEx getView() {
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageViewEx.Observer
	 * #onLogon()
	 */
	@Override
	public void onLogon() {
		userAuthentication.requireUser(this, 0, context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageViewEx.Observer
	 * #onImprint()
	 */
	@Override
	public void onImprint() {
		try {
			List<Imprint> imprints = imprintService
					.listAllImprint(new HashMap<String, Object>(context));
			String imprintContent = null;
			if (imprints.size() > 0) {
				imprintContent = imprints.get(0).getContent();
			}
			view.showImprint(imprintContent == null ? "--" : imprintContent);
		} catch (RuntimeException exc) {
			view.showErrorMessage(exc.toString());
			throw exc;
		}
	}

}
