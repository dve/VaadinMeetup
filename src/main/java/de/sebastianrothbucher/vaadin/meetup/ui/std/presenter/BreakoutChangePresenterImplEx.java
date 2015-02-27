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
import java.util.Map;

import de.sebastianrothbucher.vaadin.meetup.model.User;
import de.sebastianrothbucher.vaadin.meetup.service.BreakoutService;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.SubviewCapablePresenter;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutChangeViewEx;
import de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication;

public class BreakoutChangePresenterImplEx extends BreakoutChangePresenterImpl
		implements BreakoutChangeViewEx.Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BreakoutChangePresenterImplEx(Map<String, Object> context,
			BreakoutChangeViewEx view, Presenter returnPresenter,
			BreakoutService service) {
		super(context, view, returnPresenter, service);
		this.context = context;
		this.view = view;
		this.service = service;
	}

	public BreakoutChangePresenterImplEx(Map<String, Object> context,
			BreakoutChangeViewEx view, Presenter returnPresenter,
			SubviewCapablePresenter capablePresenter, BreakoutService service) {
		super(context, view, returnPresenter, capablePresenter, service);
		this.context = context;
		this.view = view;
		this.service = service;
	}

	private Map<String, Object> context;
	private BreakoutChangeViewEx view;
	private BreakoutService service;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.
	 * BreakoutChangePresenterImpl#startPresenting()
	 */
	@Override
	public void startPresenting() {
		super.startPresenting();
		User user = (User) context
				.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY);
		if (!isUnmodified(user)) {
			view.setReadOnlyMode(true);
		}
		// like or unlike...
		if (user != null) {
			view.setLiked(getBreakout().isLiked(user));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.
	 * BreakoutChangePresenterImpl#onSave()
	 */
	@Override
	public void onSave() {
		// only if we're not modified
		User user = (User) context
				.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY);
		if (isUnmodified(user)) {
			super.onSave();
		}
	}

	private boolean isUnmodified(User user) {
		// we can only edit when we have an authenticated user equal to the
		// creator and no likes yet
		return user != null && user.isGroupMember()
				&& user.equals(getBreakout().getSubmittedByUser())
				&& getBreakout().getLikedCount() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.
	 * BreakoutChangePresenterImpl#onRemove()
	 */
	@Override
	public void onRemove() {
		// not possible here (no button yet either)
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutChangeViewEx
	 * .Observer#onLike()
	 */
	@Override
	public void onLike() {
		User user = (User) context
				.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY);
		// does not work without authentication
		if (user == null || (!user.isGroupMember())) {
			view.showErrorMessage("Not logged on!");
			return;
		}
		// now do the like
		getBreakout().like(user);
		// do not save anything else to the model yet, but save the like!
		try {
			this.service.updateExistingBreakout(getBreakout(),
					new HashMap<String, Object>(context));
		} catch (RuntimeException exc) {
			view.showErrorMessage(exc.toString());
			throw exc;
		}
		// finally update the view
		view.setLiked(getBreakout().isLiked(user));
		view.setLikedCount(getBreakout().getLikedCount());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutChangeViewEx
	 * .Observer#onUnlike()
	 */
	@Override
	public void onUnlike() {
		User user = (User) context
				.get(UserAuthentication.CURRENT_USER_CONTEXT_KEY);
		// does not work without authentication
		if (user == null || (!user.isGroupMember())) {
			view.showErrorMessage("Not logged on!");
			return;
		}
		// now do the un-like
		getBreakout().unlike(user);
		// do not save anything else to the model yet, but save the like!
		try {
			this.service.updateExistingBreakout(getBreakout(),
					new HashMap<String, Object>(context));
		} catch (RuntimeException exc) {
			view.showErrorMessage(exc.toString());
			throw exc;
		}
		// finally update the view
		view.setLiked(getBreakout().isLiked(user));
		view.setLikedCount(getBreakout().getLikedCount());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.
	 * BreakoutChangePresenterImpl#getView()
	 */
	@Override
	public BreakoutChangeViewEx getView() {
		return (BreakoutChangeViewEx) super.getView();
	}

}
