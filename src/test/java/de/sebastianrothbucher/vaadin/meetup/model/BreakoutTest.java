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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class BreakoutTest {

	Breakout breakout = new Breakout("Test-Topic");

	@Test
	public void testSubmittedByUserNameInitial() {
		assertNull(breakout.getSubmittedByUserName());
	}

	@Test
	public void testSubmittedByUserName() {
		breakout.setSubmittedByUser(new User("Wile E. C.", true));
		assertEquals("Wile E. C.", breakout.getSubmittedByUserName());
	}

	@Test
	public void testGetLikedCountInitial() {
		assertEquals(0, breakout.getLikedCount());
	}

	@Test
	public void testGetLikedCount() {
		breakout.setLikedByUsers(Collections.singleton(new User("Wile E. C.",
				true)));
		assertEquals(1, breakout.getLikedCount());
	}

	@Test
	public void testLike() {
		User user = new User("Wile E. C.", true);
		// must count only once
		breakout.like(user);
		breakout.like(user);
		assertEquals(1, breakout.getLikedByUsers().size());
		assertEquals(user, breakout.getLikedByUsers().iterator().next());
	}

	@Test
	public void testUnlike() {
		Set<User> users = new HashSet<User>();
		User user1 = new User("Road R.", true);
		users.add(user1);
		User user = new User("Wile E. C.", true);
		users.add(user);
		breakout.setLikedByUsers(users);
		// must count only once
		breakout.unlike(user);
		breakout.unlike(user);
		assertEquals(1, breakout.getLikedByUsers().size());
		assertEquals(user1, breakout.getLikedByUsers().iterator().next());
	}

}
