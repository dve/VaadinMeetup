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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "USERTAB")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}

	public User(int meetupId, String meetupShort, boolean groupMember) {
		super();
		this.meetupId = meetupId;
		this.meetupShort = meetupShort;
		this.groupMember = groupMember;
	}

	@Id
	private int meetupId;	
	private String meetupShort = "";
	@Transient
	private boolean groupMember = false;

	public int getMeetupId() {
		return meetupId;
	}

	public void setMeetupId(int meetupId) {
		this.meetupId = meetupId;
	}

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
		result = prime * result + meetupId;
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
		if (meetupId != other.meetupId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [meetupId=" + meetupId + ", meetupShort=" + meetupShort
				+ ", groupMember=" + groupMember + "]";
	}

}
