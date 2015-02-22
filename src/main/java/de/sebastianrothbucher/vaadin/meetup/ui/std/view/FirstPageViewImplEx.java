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
package de.sebastianrothbucher.vaadin.meetup.ui.std.view;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickEvent;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickListener;
import com.vaadin.ui.Label;

public class FirstPageViewImplEx extends FirstPageViewImpl implements
		FirstPageViewEx {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FirstPageViewImplEx() {
		super();
	}

	protected Label userNameLabel = new Label();
	protected Label logonLabel = new Label();
	protected NavigationButton logonButton = new NavigationButton();
	protected Label memberLabel = new Label();

	private FirstPageViewEx.Observer observer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageViewImpl#
	 * initializeUi()
	 */
	@Override
	public void initializeUi() {
		super.initializeUi();
		// TODO: move to bundle
		listTalkButton.setCaption("See all talks...");
		// hide "standard buttons"
		listTalkButton.setVisible(false);
		newTalkButton.setVisible(false);
		// add elements for logon
		VerticalComponentGroup logonGroup = new VerticalComponentGroup();
		userNameLabel.setVisible(false);
		userNameLabel.addStyleName("styleid-FirstPageViewImplEx-userNameLabel");
		logonGroup.addComponent(userNameLabel);
		logonLabel.setVisible(false);
		// TODO: move to bundle
		logonLabel
				.setValue("You have to authenticate via Meetup as member of Vaadin-Germany");
		logonLabel.addStyleName("styleid-FirstPageViewImplEx-logonLabel");
		logonGroup.addComponent(logonLabel);
		logonButton.setVisible(false);
		// TODO: move to bundle
		logonButton.setCaption("Logon via Meetup.com now...");
		logonButton.addStyleName("styleid-FirstPageViewImplEx-logonButton");
		logonButton.addClickListener(new NavigationButtonClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(NavigationButtonClickEvent event) {
				observer.onLogon();
			}

		});
		logonGroup.addComponent(logonButton);
		memberLabel.setVisible(false);
		// TODO: move to bundle
		memberLabel
				.setValue("Please join Vaadin-Germany and revisit the app then...");
		memberLabel.addStyleName("styleid-FirstPageViewImplEx-memberLabel");
		logonGroup.addComponent(memberLabel);
		firstPageLayout.addComponent(logonGroup, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageViewImpl#
	 * setObserver
	 * (de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageView.Observer)
	 */
	@Override
	public void setObserver(FirstPageView.Observer observer) {
		if (!(observer instanceof FirstPageViewEx.Observer)) {
			throw new IllegalArgumentException("Need "
					+ FirstPageViewEx.Observer.class.getName());
		}
		super.setObserver(observer);
		this.observer = (FirstPageViewEx.Observer) observer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageViewEx#
	 * setLogonVisible(boolean)
	 */
	@Override
	public void setLogonVisible(boolean visible) {
		logonLabel.setVisible(visible);
		logonButton.setVisible(visible);
	}

	@Override
	public void setMembershipVisible(boolean visible) {
		memberLabel.setVisible(visible);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageViewEx#
	 * setTalksListVisible(boolean)
	 */
	@Override
	public void setTalksListVisible(boolean visible) {
		listTalkButton.getParent().setVisible(visible);
		listTalkButton.setVisible(visible);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageViewEx#setUserName
	 * (java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		if (userName == null) {
			userNameLabel.setValue("--");
			userNameLabel.setVisible(false);
		} else {
			// TODO: move to bundle
			userNameLabel.setValue("Welcome, " + userName);
			userNameLabel.setVisible(true);
		}
	}

}
