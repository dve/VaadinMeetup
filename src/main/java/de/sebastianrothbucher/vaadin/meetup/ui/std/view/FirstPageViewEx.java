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

public interface FirstPageViewEx extends FirstPageView {

	/**
	 * Show a "you have to auth with meetup first" hint + button to do so
	 * 
	 * @param visible
	 *            or not
	 */
	public void setLogonVisible(boolean visible);

	/**
	 * Show a "you have to become member of Vaadin-Germany first" message
	 * 
	 * @param visible
	 *            or not
	 */
	public void setMembershipVisible(boolean visible);

	/**
	 * Show the button to jump to the talks list
	 * 
	 * @param visible
	 *            or not
	 */
	public void setTalksListVisible(boolean visible);

	/**
	 * Show the user name à la "Welcome, Sebastian"
	 * 
	 * @param userName
	 *            user (null to hide the message)
	 */
	public void setUserName(String userName);

	public static interface Observer extends FirstPageView.Observer {

		/**
		 * Callback for pressing the "login with meetup"
		 */
		public void onLogon();

	}

}