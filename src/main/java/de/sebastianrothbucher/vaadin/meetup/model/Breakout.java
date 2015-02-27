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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import de.akquinet.engineering.vaadinator.annotations.DisplayBean;
import de.akquinet.engineering.vaadinator.annotations.DisplayProperty;
import de.akquinet.engineering.vaadinator.annotations.DisplayPropertySetting;
import de.akquinet.engineering.vaadinator.annotations.FieldType;

@DisplayBean
@Entity
public class Breakout implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Breakout() {
		super();
	}

	public Breakout(String topic) {
		super();
		this.topic = topic;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@DisplayProperty(profileSettings = { @DisplayPropertySetting(required = true, showInTable = true) })
	private String topic;
	@OneToOne(cascade = CascadeType.PERSIST)
	private User submittedByUser = null;
	@OneToMany(cascade = CascadeType.PERSIST)
	private Set<User> likedByUsers = new HashSet<User>();

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public User getSubmittedByUser() {
		return submittedByUser;
	}

	public void setSubmittedByUser(User submittedByUser) {
		this.submittedByUser = submittedByUser;
	}

	@DisplayProperty(captionText = "By", profileSettings = { @DisplayPropertySetting(readOnly = true, fieldType = FieldType.LABEL) })
	public String getSubmittedByUserName() {
		if (getSubmittedByUser() == null) {
			return null;
		}
		return getSubmittedByUser().getMeetupShort();
	}

	public Set<User> getLikedByUsers() {
		return likedByUsers;
	}

	public void setLikedByUsers(Set<User> likedByUsers) {
		this.likedByUsers = likedByUsers;
	}

	public void like(User user) {
		likedByUsers.add(user);
	}

	public void unlike(User user) {
		likedByUsers.remove(user);
	}

	public boolean isLiked(User user) {
		return likedByUsers.contains(user);
	}

	@DisplayProperty(profileSettings = { @DisplayPropertySetting(readOnly = true, fieldType = FieldType.LABEL) })
	public int getLikedCount() {
		return likedByUsers.size();
	}

	@Override
	public int hashCode() {
		if (id != 0) {
			return (int) id;
		}
		final int prime = 31;
		int result = 1;
		result = prime * result + ((topic == null) ? 0 : topic.hashCode());
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
		Breakout other = (Breakout) obj;
		if (id != 0) {
			return id == other.id;
		}
		if (topic == null) {
			if (other.topic != null)
				return false;
		} else if (!topic.equals(other.topic))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Breakout [id=" + id + ", topic=" + topic + ", submittedByUser="
				+ submittedByUser + ", likedByUsers=" + likedByUsers
				+ ", getSubmittedByUserName()=" + getSubmittedByUserName()
				+ ", getLikedCount()=" + getLikedCount() + "]";
	}

}
