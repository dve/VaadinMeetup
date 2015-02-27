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
import com.vaadin.shared.ui.label.ContentMode;
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
	protected Label memberLabel = new Label();
	protected NavigationButton logonButton = new NavigationButton();

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
		listTalkButton.setCaption("Alle Talks...");
		// TODO: move to bundle
		listBreakoutButton.setCaption("Alle Breakouts...");
		// TODO: move to bundle
		newBreakoutButton.setCaption("Breakout vorschlagen");
		// hide "standard buttons"
		listTalkButton.setVisible(false);
		newTalkButton.setVisible(false);
		listBreakoutButton.setVisible(false);
		newBreakoutButton.setVisible(false);
		// add elements for logon
		VerticalComponentGroup logonGroup = new VerticalComponentGroup();
		userNameLabel.setVisible(false);
		userNameLabel.addStyleName("styleid-FirstPageViewImplEx-userNameLabel");
		logonGroup.addComponent(userNameLabel);
		logonLabel.setVisible(false);
		// TODO: move to bundle
		logonLabel
				.setValue("Sie müssen über Meetup als Mitglied von Vaadin-Germany angemeldet sein.");
		logonLabel.addStyleName("styleid-FirstPageViewImplEx-logonLabel");
		logonGroup.addComponent(logonLabel);
		memberLabel.setVisible(false);
		// TODO: move to bundle
		memberLabel
				.setValue("Treten Sie <a href=\"http://www.meetup.com/Vaadin-Germany/\" target=\"_blank\">Vaadin-Germany</a> bei und rufen Sie die App erneut auf...");
		memberLabel.setContentMode(ContentMode.HTML);
		memberLabel.addStyleName("styleid-FirstPageViewImplEx-memberLabel");
		logonGroup.addComponent(memberLabel);
		logonButton.setVisible(false);
		// TODO: move to bundle
		logonButton.setCaption("Jetzt via Meetup.com anmelden...");
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
	public void setLogonHintVisible(boolean visible) {
		logonLabel.setVisible(visible);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageViewEx#
	 * setMembershipHintVisible(boolean)
	 */
	@Override
	public void setMembershipHintVisible(boolean visible) {
		memberLabel.setVisible(visible);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageViewEx#
	 * setLogonButtonVisible(boolean)
	 */
	@Override
	public void setLogonButtonVisible(boolean visible) {
		logonButton.setVisible(visible);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageViewEx#
	 * setTalksListVisible(boolean)
	 */
	@Override
	public void setTalksVisible(boolean visible) {
		listTalkButton.getParent().setVisible(visible);
		listTalkButton.setVisible(visible);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageViewEx#
	 * setBreakoutsVisible(boolean)
	 */
	@Override
	public void setBreakoutsVisible(boolean visible) {
		listBreakoutButton.setVisible(visible);
		newBreakoutButton.setVisible(visible);
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
			userNameLabel.setValue("Willkommen, " + userName);
			userNameLabel.setVisible(true);
		}
	}

}
