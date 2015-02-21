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

import de.sebastianrothbucher.vaadin.meetup.service.TalkService;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.ViewFactory;
import de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication;

public class PresenterFactoryEx extends PresenterFactory {

	public PresenterFactoryEx(Map<String, Object> context,
			ViewFactory viewFactory, TalkService talkService,
			UserAuthentication userAuthentication) {
		super(context, viewFactory, talkService);
		this.context = context;
		this.viewFactory = viewFactory;
		this.userAuthentication = userAuthentication;
	}

	private Map<String, Object> context;
	private ViewFactory viewFactory;
	private UserAuthentication userAuthentication;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.PresenterFactory
	 * #createFirstPagePresenter()
	 */
	@Override
	public FirstPagePresenterImplEx createFirstPagePresenter() {
		return new FirstPagePresenterImplEx(context,
				viewFactory.createFirstPageView(), this, userAuthentication);
	}

}
