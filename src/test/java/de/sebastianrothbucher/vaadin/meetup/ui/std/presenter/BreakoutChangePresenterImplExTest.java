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
package de.sebastianrothbucher.vaadin.meetup.ui.std.presenter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import de.sebastianrothbucher.vaadin.meetup.model.Breakout;
import de.sebastianrothbucher.vaadin.meetup.model.User;
import de.sebastianrothbucher.vaadin.meetup.service.BreakoutServiceEx;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutChangeViewEx;
import de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication;

public class BreakoutChangePresenterImplExTest {

	Map<String, Object> context = new HashMap<String, Object>();
	User testUser = new User("Test U.", true);
	BreakoutChangeViewEx breakoutChangeViewEx;
	BreakoutServiceEx breakoutServiceEx;
	Presenter returnPresenter;
	BreakoutChangePresenterImplEx presenter;

	@Before
	public void setUp() {
		breakoutChangeViewEx = mock(BreakoutChangeViewEx.class);
		breakoutServiceEx = mock(BreakoutServiceEx.class);
		returnPresenter = mock(Presenter.class);
		presenter = new BreakoutChangePresenterImplEx(context,
				breakoutChangeViewEx, returnPresenter, breakoutServiceEx);
	}

	@Test
	public void testStartPresenting() {
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		Breakout breakout = new Breakout("Old Topic");
		breakout.setSubmittedByUser(testUser);
		presenter.setBreakout(breakout);
		presenter.startPresenting();
		verify(breakoutChangeViewEx).setObserver(presenter);
		verify(breakoutChangeViewEx).initializeUi();
		verify(breakoutChangeViewEx).setTopic("Old Topic");
		verify(breakoutChangeViewEx).setLiked(false);
		verify(breakoutChangeViewEx).setLikedCount(0);
		verify(breakoutChangeViewEx).setSubmittedByUserName("Test U.");
		verify(breakoutChangeViewEx, never()).setReadOnlyMode(true);
	}

	@Test
	public void testStartPresentingNoUser() {
		Breakout breakout = new Breakout("Old Topic");
		breakout.setSubmittedByUser(testUser);
		presenter.setBreakout(breakout);
		presenter.startPresenting();
		verify(breakoutChangeViewEx).setObserver(presenter);
		verify(breakoutChangeViewEx).initializeUi();
		verify(breakoutChangeViewEx).setTopic("Old Topic");
		verify(breakoutChangeViewEx, never()).setLiked(true);
		verify(breakoutChangeViewEx).setLikedCount(0);
		verify(breakoutChangeViewEx).setSubmittedByUserName("Test U.");
		verify(breakoutChangeViewEx).setReadOnlyMode(true);
	}

	@Test
	public void testStartPresentingOtherUser() {
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		Breakout breakout = new Breakout("Old Topic");
		breakout.setSubmittedByUser(new User("Other U.", true));
		presenter.setBreakout(breakout);
		presenter.startPresenting();
		verify(breakoutChangeViewEx).setObserver(presenter);
		verify(breakoutChangeViewEx).initializeUi();
		verify(breakoutChangeViewEx).setTopic("Old Topic");
		verify(breakoutChangeViewEx).setLiked(false);
		verify(breakoutChangeViewEx).setLikedCount(0);
		verify(breakoutChangeViewEx).setSubmittedByUserName("Other U.");
		verify(breakoutChangeViewEx).setReadOnlyMode(true);
	}

	@Test
	public void testStartPresentingLiked() {
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		Breakout breakout = new Breakout("Old Topic");
		breakout.setSubmittedByUser(testUser);
		breakout.setLikedByUsers(Collections.singleton(new User("Other U.",
				true)));
		presenter.setBreakout(breakout);
		presenter.startPresenting();
		verify(breakoutChangeViewEx).setObserver(presenter);
		verify(breakoutChangeViewEx).initializeUi();
		verify(breakoutChangeViewEx).setTopic("Old Topic");
		verify(breakoutChangeViewEx).setLiked(false);
		verify(breakoutChangeViewEx).setLikedCount(1);
		verify(breakoutChangeViewEx).setSubmittedByUserName("Test U.");
		verify(breakoutChangeViewEx).setReadOnlyMode(true);
	}

	@Test
	public void testStartPresentingLikedMultiSelf() {
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		Breakout breakout = new Breakout("Old Topic");
		breakout.setSubmittedByUser(testUser);
		Set<User> likes = new HashSet<User>();
		likes.add(testUser);
		likes.add(new User("Other U.", true));
		breakout.setLikedByUsers(likes);
		presenter.setBreakout(breakout);
		presenter.startPresenting();
		verify(breakoutChangeViewEx).setObserver(presenter);
		verify(breakoutChangeViewEx).initializeUi();
		verify(breakoutChangeViewEx).setTopic("Old Topic");
		verify(breakoutChangeViewEx).setLiked(true);
		verify(breakoutChangeViewEx).setLikedCount(2);
		verify(breakoutChangeViewEx).setSubmittedByUserName("Test U.");
		verify(breakoutChangeViewEx).setReadOnlyMode(true);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testOnSave() {
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		Breakout breakout = new Breakout("Old Topic");
		breakout.setSubmittedByUser(testUser);
		presenter.setBreakout(breakout);
		when(breakoutChangeViewEx.checkAllFieldsValid()).thenReturn(true);
		when(breakoutChangeViewEx.getTopic()).thenReturn("New Topic");
		presenter.onSave();
		verify(breakoutServiceEx)
				.updateExistingBreakout(eq(breakout), anyMap());
		assertEquals("New Topic", breakout.getTopic());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testOnSaveNoUser() {
		Breakout breakout = new Breakout("Old Topic");
		breakout.setSubmittedByUser(testUser);
		presenter.setBreakout(breakout);
		when(breakoutChangeViewEx.checkAllFieldsValid()).thenReturn(true);
		when(breakoutChangeViewEx.getTopic()).thenReturn("New Topic");
		presenter.onSave();
		verify(breakoutServiceEx, never()).updateExistingBreakout(
				any(Breakout.class), anyMap());
		assertEquals("Old Topic", breakout.getTopic());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testOnSaveOtherUser() {
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		Breakout breakout = new Breakout("Old Topic");
		breakout.setSubmittedByUser(new User("Other U.", true));
		presenter.setBreakout(breakout);
		when(breakoutChangeViewEx.checkAllFieldsValid()).thenReturn(true);
		when(breakoutChangeViewEx.getTopic()).thenReturn("New Topic");
		presenter.onSave();
		verify(breakoutServiceEx, never()).updateExistingBreakout(
				any(Breakout.class), anyMap());
		assertEquals("Old Topic", breakout.getTopic());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testOnSaveLikes() {
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		Breakout breakout = new Breakout("Old Topic");
		breakout.setSubmittedByUser(testUser);
		breakout.setLikedByUsers(Collections.singleton(new User("Other U.",
				true)));
		presenter.setBreakout(breakout);
		when(breakoutChangeViewEx.checkAllFieldsValid()).thenReturn(true);
		when(breakoutChangeViewEx.getTopic()).thenReturn("New Topic");
		presenter.onSave();
		verify(breakoutServiceEx, never()).updateExistingBreakout(
				any(Breakout.class), anyMap());
		assertEquals("Old Topic", breakout.getTopic());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testOnLike() {
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		Breakout breakout = new Breakout("Old Topic");
		breakout.setSubmittedByUser(testUser);
		presenter.setBreakout(breakout);
		when(breakoutChangeViewEx.getTopic()).thenReturn("New Topic");
		presenter.onLike();
		assertEquals(1, breakout.getLikedByUsers().size());
		assertEquals(testUser, breakout.getLikedByUsers().iterator().next());
		verify(breakoutChangeViewEx, never()).checkAllFieldsValid();
		verify(breakoutChangeViewEx).setLikedCount(1);
		verify(breakoutChangeViewEx).setLiked(true);
		verify(breakoutServiceEx)
				.updateExistingBreakout(eq(breakout), anyMap());
		assertEquals("Old Topic", breakout.getTopic());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testOnLikeNoUser() {
		Breakout breakout = new Breakout("Old Topic");
		breakout.setSubmittedByUser(testUser);
		presenter.setBreakout(breakout);
		presenter.onLike();
		assertEquals(0, breakout.getLikedByUsers().size());
		verify(breakoutChangeViewEx).showErrorMessage(anyString());
		verify(breakoutChangeViewEx, never()).checkAllFieldsValid();
		verify(breakoutChangeViewEx, never()).setLikedCount(anyInt());
		verify(breakoutChangeViewEx, never()).setLiked(anyBoolean());
		verify(breakoutServiceEx, never()).updateExistingBreakout(
				any(Breakout.class), anyMap());
		assertEquals("Old Topic", breakout.getTopic());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testOnUnlike() {
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		Breakout breakout = new Breakout("Old Topic");
		breakout.setSubmittedByUser(testUser);
		breakout.setLikedByUsers(new HashSet<User>(Collections
				.singleton(testUser)));
		presenter.setBreakout(breakout);
		when(breakoutChangeViewEx.getTopic()).thenReturn("New Topic");
		presenter.onUnlike();
		assertEquals(0, breakout.getLikedByUsers().size());
		verify(breakoutChangeViewEx, never()).checkAllFieldsValid();
		verify(breakoutChangeViewEx).setLikedCount(0);
		verify(breakoutChangeViewEx).setLiked(false);
		verify(breakoutServiceEx)
				.updateExistingBreakout(eq(breakout), anyMap());
		assertEquals("Old Topic", breakout.getTopic());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testOnUnlikeNoUser() {
		Breakout breakout = new Breakout("Old Topic");
		breakout.setSubmittedByUser(testUser);
		breakout.setLikedByUsers(Collections.singleton(testUser));
		presenter.setBreakout(breakout);
		presenter.onUnlike();
		assertEquals(1, breakout.getLikedByUsers().size());
		verify(breakoutChangeViewEx).showErrorMessage(anyString());
		verify(breakoutChangeViewEx, never()).checkAllFieldsValid();
		verify(breakoutChangeViewEx, never()).setLikedCount(anyInt());
		verify(breakoutChangeViewEx, never()).setLiked(anyBoolean());
		verify(breakoutServiceEx, never()).updateExistingBreakout(
				any(Breakout.class), anyMap());
		assertEquals("Old Topic", breakout.getTopic());
	}

	@Test(expected = Exception.class)
	public void testOnRemove() {
		presenter.onRemove();
	}

	@Test
	public void testOnCancel() {
		presenter.onCancel();
		verify(returnPresenter).returnToThisPresener(presenter);
	}

}
