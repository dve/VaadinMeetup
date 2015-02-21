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
package de.sebastianrothbucher.vaadin.meetup;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import de.sebastianrothbucher.vaadin.meetup.dao.TalkDaoPlain;
import de.sebastianrothbucher.vaadin.meetup.service.TalkService;
import de.sebastianrothbucher.vaadin.meetup.service.TalkServicePlain;
import de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.FirstPagePresenter;
import de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.PresenterFactoryEx;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageView;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.VaadinViewFactory;
import de.sebastianrothbucher.vaadin.meetup.userauth.MeetupUserAuthentication;

/**
 * Main UI class
 */
@Title("VaadinMeetup")
@Theme("touchkitex")
@Widgetset("de.sebastianrothbucher.vaadin.meetup.MobileWidgetset")
public class VaadinMeetupUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void init(VaadinRequest request) {
		System.out.println("init");
		NavigationManager m = new NavigationManager();
		m.setMaintainBreadcrumb(true);
		FirstPagePresenter fpres;
		fpres = obtainPresenterFactory(request).createFirstPagePresenter();
		FirstPageView fview = fpres.getView();
		m.setCurrentComponent((Component) fview.getComponent());
		setContent(m);
		// and go
		fpres.startPresenting();
	}

	PresenterFactoryEx presenterFactory = null;

	protected PresenterFactoryEx obtainPresenterFactory(VaadinRequest request) {
		if (presenterFactory == null) {
			Map<String, Object> context = new HashMap<String, Object>();
			// simple, overwrite method for e.g. Spring / CDI / ...
			// Entity-Manager NUR Thread-Safe, wenn er injected wird wie hier
			TalkService talkService;
			EntityManagerFactory entityManagerFactory = Persistence
					.createEntityManagerFactory("VaadinMeetup");
			TalkDaoPlain talkDaoPlain = new TalkDaoPlain(entityManagerFactory);
			talkService = new TalkServicePlain(entityManagerFactory,
					talkDaoPlain);
			MeetupUserAuthentication userAuthentication = new MeetupUserAuthentication();
			userAuthentication.processCurrentRequest(request, getSession(),
					context);
			presenterFactory = new PresenterFactoryEx(context,
					new VaadinViewFactory(), talkService, userAuthentication);
		}
		return presenterFactory;
	}

}