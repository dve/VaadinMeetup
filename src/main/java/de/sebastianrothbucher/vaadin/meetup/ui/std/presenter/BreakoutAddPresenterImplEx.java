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

import de.sebastianrothbucher.vaadin.meetup.model.Breakout;
import de.sebastianrothbucher.vaadin.meetup.model.User;
import de.sebastianrothbucher.vaadin.meetup.service.BreakoutService;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.SubviewCapablePresenter;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutAddView;
import de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication;

public class BreakoutAddPresenterImplEx extends BreakoutAddPresenterImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BreakoutAddPresenterImplEx(Map<String, Object> context,
			BreakoutAddView view, Presenter returnPresenter,
			BreakoutService service) {
		super(context, view, returnPresenter, service);
		this.context = context;
		this.view = view;
	}

	public BreakoutAddPresenterImplEx(Map<String, Object> context,
			BreakoutAddView view, Presenter returnPresenter,
			SubviewCapablePresenter capablePresenter, BreakoutService service) {
		super(context, view, returnPresenter, capablePresenter, service);
		this.context = context;
		this.view = view;
	}

	private Map<String, Object> context;
	private BreakoutAddView view;

	@Override
	public void startPresenting() {
		// we need an authenticated user!
		User user = (User) context
				.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY);
		if (user == null || (!user.isGroupMember())) {
			view.showErrorMessage("Not logged on!");
			return;
		}
		// also add the user as submitted user 
		setBreakout(new Breakout());
		getBreakout().setSubmittedByUser(user);
		view.setObserver(this);
		view.initializeUi();	
		loadFromModel();
	}

}
