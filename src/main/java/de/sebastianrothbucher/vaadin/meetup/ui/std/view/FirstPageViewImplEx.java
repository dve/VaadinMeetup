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

import java.util.ResourceBundle;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickEvent;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickListener;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.sebastianrothbucher.vaadin.meetup.ui.std.view.util.BundleUtil;
import de.sebastianrothbucher.vaadin.meetup.ui.view.ExceptionMappingStrategy;

public class FirstPageViewImplEx extends FirstPageViewImpl implements
		FirstPageViewEx {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FirstPageViewImplEx(ExceptionMappingStrategy exceptionMappingStrategy) {
		super(exceptionMappingStrategy);
	}

	protected Label userNameLabel = new Label();
	protected Label logonLabel = new Label();
	protected Label memberLabel = new Label();
	protected NavigationButton logonButton = new NavigationButton();
	protected Button imprintButton = new Button();

	private Popover popover = null;
	
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
		listTalkButton.setCaption(obtainBundle().getString("entity.Talk.all"));
		listBreakoutButton.setCaption(obtainBundle().getString(
				"entity.Breakout.all"));
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
		logonLabel.setValue(obtainBundle().getString("meetupMember"));
		logonLabel.setContentMode(ContentMode.HTML);
		logonLabel.addStyleName("styleid-FirstPageViewImplEx-logonLabel");
		logonGroup.addComponent(logonLabel);
		memberLabel.setVisible(false);
		memberLabel.setValue(obtainBundle().getString("vaadinMember"));
		memberLabel.setContentMode(ContentMode.HTML);
		memberLabel.addStyleName("styleid-FirstPageViewImplEx-memberLabel");
		logonGroup.addComponent(memberLabel);
		logonButton.setVisible(false);
		logonButton.setCaption(obtainBundle().getString("logon"));
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
		imprintButton.setCaption(obtainBundle().getString("imprint"));
		imprintButton.addStyleName("styleid-FirstPageViewImplEx-imprintButton");
		imprintButton.addClickListener(new ClickListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				observer.onImprint();
			}
		});
		firstPageLayout.addComponent(imprintButton);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageViewEx#showImprint
	 * (java.lang.String)
	 */
	@Override
	public void showImprint(String imprintHtml) {
		popover = new Popover();
		popover.setSizeFull();
		popover.setModal(false);
		VerticalLayout imprintLayout = new VerticalLayout();
		Label imprintLabel = new Label(imprintHtml, ContentMode.HTML);
		imprintLabel.addStyleName("styleid-FirstPageViewImplEx-imprintLabel");
		imprintLayout.addComponent(imprintLabel);
		Button closeButton = new Button("Schließen");
		closeButton.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				popoverSchliessen();
			}
		});
		imprintLayout.addComponent(closeButton);
		popover.setContent(imprintLayout);
		UI.getCurrent().addWindow(popover);
	}

	private void popoverSchliessen() {
		if (popover != null) {
			popover.close();
			popover = null;
		}
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
			userNameLabel.setValue(obtainBundle().getString("welcome")
					+ userName);
			userNameLabel.setVisible(true);
		}
	}

	private ResourceBundle bundle = BundleUtil.createCommonBundle();

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageViewImpl#
	 * obtainBundle()
	 */
	@Override
	protected ResourceBundle obtainBundle() {
		return bundle;
	}

}
