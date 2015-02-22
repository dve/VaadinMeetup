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

import java.io.Serializable;

import com.vaadin.ui.UI;

import de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication;

public class VaadinSessionAccess implements UserAuthentication.SessionAccess,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication.
	 * SessionAccess#getAttribute(java.lang.String)
	 */
	@Override
	public Object getAttribute(String name) {
		return UI.getCurrent().getSession().getAttribute(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication.
	 * SessionAccess#setAttribute(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setAttribute(String name, Object value) {
		UI.getCurrent().getSession().setAttribute(name, value);
	}

}