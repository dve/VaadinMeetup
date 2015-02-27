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
import de.sebastianrothbucher.vaadin.meetup.service.BreakoutServiceEx;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.SubviewCapablePresenter;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutListView;
import de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication;

public class BreakoutListPresenterImplEx extends BreakoutListPresenterImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BreakoutListPresenterImplEx(Map<String, Object> context,
			BreakoutListView view, PresenterFactory presenterFactory,
			BreakoutServiceEx service,
			SubviewCapablePresenter subviewCapablePresenter) {
		super(context, view, presenterFactory, service, subviewCapablePresenter);
		this.context = context;
		this.view = view;
	}

	private Map<String, Object> context;
	private BreakoutListView view;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.
	 * BreakoutListPresenterImpl#onAddBreakout()
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

}
