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
package de.sebastianrothbucher.vaadin.meetup.model;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}

	public User(String meetupShort, boolean groupMember) {
		super();
		this.meetupShort = meetupShort;
		this.groupMember = groupMember;
	}

	private String meetupShort = "";
	private boolean groupMember = false;

	public String getMeetupShort() {
		return meetupShort;
	}

	public void setMeetupShort(String meetupShort) {
		this.meetupShort = meetupShort;
	}

	public boolean isGroupMember() {
		return groupMember;
	}

	public void setGroupMember(boolean groupMember) {
		this.groupMember = groupMember;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (groupMember ? 1231 : 1237);
		result = prime * result
				+ ((meetupShort == null) ? 0 : meetupShort.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (groupMember != other.groupMember)
			return false;
		if (meetupShort == null) {
			if (other.meetupShort != null)
				return false;
		} else if (!meetupShort.equals(other.meetupShort))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [meetupShort=" + meetupShort + ", groupMember="
				+ groupMember + "]";
	}

}
