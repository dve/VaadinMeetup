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

import de.sebastianrothbucher.vaadin.meetup.service.BreakoutServiceEx;
import de.sebastianrothbucher.vaadin.meetup.service.TalkService;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.SubviewCapablePresenter;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.ViewFactoryEx;
import de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication;

public class PresenterFactoryEx extends PresenterFactory {

	public PresenterFactoryEx(Map<String, Object> context,
			ViewFactoryEx viewFactory, BreakoutServiceEx breakoutService,
			TalkService talkService, UserAuthentication userAuthentication) {
		super(context, viewFactory, breakoutService, talkService);
		this.context = context;
		this.viewFactory = viewFactory;
		this.breakoutService = breakoutService;
		this.talkService = talkService;
		this.userAuthentication = userAuthentication;
	}

	private Map<String, Object> context;
	private ViewFactoryEx viewFactory;
	private BreakoutServiceEx breakoutService;
	private TalkService talkService;
	private UserAuthentication userAuthentication;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.PresenterFactory
	 * #createFirstPagePresenter()
	 */
	@Override
	public FirstPagePresenter createFirstPagePresenter() {
		return new FirstPagePresenterImplEx(context,
				viewFactory.createFirstPageView(), this, userAuthentication);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.PresenterFactory
	 * #createBreakoutListPresenter
	 * (de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter,
	 * de.sebastianrothbucher
	 * .vaadin.meetup.ui.presenter.SubviewCapablePresenter)
	 */
	@Override
	public BreakoutListPresenter createBreakoutListPresenter(
			Presenter returnPresenter,
			SubviewCapablePresenter subviewCapablePresenter) {
		return new BreakoutListPresenterImplEx(context,
				viewFactory.createBreakoutListView(), this, breakoutService,
				subviewCapablePresenter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.PresenterFactory
	 * #createBreakoutChangePresenter
	 * (de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter)
	 */
	@Override
	public BreakoutChangePresenter createBreakoutChangePresenter(
			Presenter returnPresenter) {
		return new BreakoutChangePresenterImplEx(context,
				viewFactory.createBreakoutChangeView(), returnPresenter,
				breakoutService);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.PresenterFactory
	 * #createBreakoutAddPresenter
	 * (de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter)
	 */
	@Override
	public BreakoutAddPresenter createBreakoutAddPresenter(
			Presenter returnPresenter) {
		return new BreakoutAddPresenterImplEx(context,
				viewFactory.createBreakoutAddView(), returnPresenter,
				breakoutService);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.PresenterFactory
	 * #createTalkListPresenter
	 * (de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter,
	 * de.sebastianrothbucher
	 * .vaadin.meetup.ui.presenter.SubviewCapablePresenter)
	 */
	@Override
	public TalkListPresenter createTalkListPresenter(Presenter returnPresenter,
			SubviewCapablePresenter subviewCapablePresenter) {
		return new TalkListPresenterImplEx(context,
				viewFactory.createTalkListView(), this, talkService,
				subviewCapablePresenter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.PresenterFactory
	 * #createTalkChangePresenter
	 * (de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter)
	 */
	@Override
	public TalkChangePresenter createTalkChangePresenter(
			Presenter returnPresenter) {
		return new TalkChangePresenterImplEx(context,
				viewFactory.createTalkChangeView(), returnPresenter,
				talkService);
	}

}
