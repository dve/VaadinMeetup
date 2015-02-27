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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.sebastianrothbucher.vaadin.meetup.model.User;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.SubviewCapablePresenter;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutAddView;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutListView;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.FirstPageViewEx;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.TalkListView;
import de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication;

public class FirstPagePresenterImplExTest {

	Map<String, Object> context = new HashMap<String, Object>();
	User testUser = new User(22, "Test U.", true);
	FirstPageViewEx firstPageViewEx;
	UserAuthentication userAuthentication;
	PresenterFactoryEx presenterFactoryEx;
	FirstPagePresenterImplEx presenter;

	@Before
	public void setUp() {
		firstPageViewEx = mock(FirstPageViewEx.class);
		userAuthentication = mock(UserAuthentication.class);
		presenterFactoryEx = mock(PresenterFactoryEx.class);
		presenter = new FirstPagePresenterImplEx(context, firstPageViewEx,
				presenterFactoryEx, userAuthentication);
	}

	@Test
	public void testStartPresenting() {
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		presenter.startPresenting();
		verify(firstPageViewEx).setObserver(presenter);
		verify(firstPageViewEx).initializeUi();
		verify(firstPageViewEx).setBreakoutsVisible(true);
		verify(firstPageViewEx).setTalksVisible(true);
		verify(firstPageViewEx).setLogonButtonVisible(false);
		verify(firstPageViewEx).setLogonHintVisible(false);
		verify(firstPageViewEx).setMembershipHintVisible(false);
		verify(firstPageViewEx).setUserName("Test U.");
	}

	@Test
	public void testStartPresentingNoUser() {
		presenter.startPresenting();
		verify(firstPageViewEx).setObserver(presenter);
		verify(firstPageViewEx).initializeUi();
		verify(firstPageViewEx).setBreakoutsVisible(false);
		verify(firstPageViewEx).setTalksVisible(false);
		verify(firstPageViewEx).setLogonButtonVisible(true);
		verify(firstPageViewEx).setLogonHintVisible(true);
		verify(firstPageViewEx).setMembershipHintVisible(false);
		verify(firstPageViewEx).setUserName(null);
	}

	@Test
	public void testStartPresentingNoMember() {
		testUser.setGroupMember(false);
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		presenter.startPresenting();
		verify(firstPageViewEx).setObserver(presenter);
		verify(firstPageViewEx).initializeUi();
		verify(firstPageViewEx).setBreakoutsVisible(false);
		verify(firstPageViewEx).setTalksVisible(false);
		verify(firstPageViewEx).setLogonButtonVisible(true);
		verify(firstPageViewEx).setLogonHintVisible(false);
		verify(firstPageViewEx).setMembershipHintVisible(true);
		verify(firstPageViewEx).setUserName("Test U.");
	}

	@Test
	public void testOnListTalk() {
		TalkListPresenter talkListPresenter = mock(TalkListPresenter.class);
		when(talkListPresenter.getView()).thenReturn(mock(TalkListView.class));
		when(
				presenterFactoryEx.createTalkListPresenter(
						any(Presenter.class),
						any(SubviewCapablePresenter.class))).thenReturn(
				talkListPresenter);
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		presenter.onListTalk();
		verify(presenterFactoryEx).createTalkListPresenter(
				any(Presenter.class), any(SubviewCapablePresenter.class));
		verify(talkListPresenter).startPresenting();
		verify(firstPageViewEx).openSubView(any(TalkListView.class));
	}

	@Test
	public void testOnListTalkNoUser() {
		presenter.onListTalk();
		verify(firstPageViewEx).showErrorMessage(anyString());
		verify(presenterFactoryEx, never()).createTalkListPresenter(
				any(Presenter.class), any(SubviewCapablePresenter.class));
		verify(firstPageViewEx, never()).openSubView(any(TalkListView.class));
	}

	@Test
	public void testOnListTalkNoMember() {
		testUser.setGroupMember(false);
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		presenter.onListTalk();
		verify(firstPageViewEx).showErrorMessage(anyString());
		verify(presenterFactoryEx, never()).createTalkListPresenter(
				any(Presenter.class), any(SubviewCapablePresenter.class));
		verify(firstPageViewEx, never()).openSubView(any(TalkListView.class));
	}

	@Test(expected = Exception.class)
	public void testOnAddTalk() {
		presenter.onAddTalk();
	}

	@Test
	public void testOnListBreakout() {
		BreakoutListPresenter breakoutListPresenter = mock(BreakoutListPresenter.class);
		when(breakoutListPresenter.getView()).thenReturn(
				mock(BreakoutListView.class));
		when(
				presenterFactoryEx.createBreakoutListPresenter(
						any(Presenter.class),
						any(SubviewCapablePresenter.class))).thenReturn(
				breakoutListPresenter);
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		presenter.onListBreakout();
		verify(presenterFactoryEx).createBreakoutListPresenter(
				any(Presenter.class), any(SubviewCapablePresenter.class));
		verify(breakoutListPresenter).startPresenting();
		verify(firstPageViewEx).openSubView(any(BreakoutListView.class));
	}

	@Test
	public void testOnListBreakoutNoUser() {
		presenter.onListBreakout();
		verify(firstPageViewEx).showErrorMessage(anyString());
		verify(presenterFactoryEx, never()).createBreakoutListPresenter(
				any(Presenter.class), any(SubviewCapablePresenter.class));
		verify(firstPageViewEx, never()).openSubView(
				any(BreakoutListView.class));
	}

	@Test
	public void testOnListBreakoutNoMember() {
		testUser.setGroupMember(false);
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		presenter.onListBreakout();
		verify(firstPageViewEx).showErrorMessage(anyString());
		verify(presenterFactoryEx, never()).createBreakoutListPresenter(
				any(Presenter.class), any(SubviewCapablePresenter.class));
		verify(firstPageViewEx, never()).openSubView(
				any(BreakoutListView.class));
	}

	@Test
	public void testOnAddBreakout() {
		BreakoutAddPresenter breakoutAddPresenter = mock(BreakoutAddPresenter.class);
		when(breakoutAddPresenter.getView()).thenReturn(
				mock(BreakoutAddView.class));
		when(
				presenterFactoryEx
						.createBreakoutAddPresenter(any(Presenter.class)))
				.thenReturn(breakoutAddPresenter);
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		presenter.onAddBreakout();
		verify(presenterFactoryEx).createBreakoutAddPresenter(
				any(Presenter.class));
		verify(breakoutAddPresenter).startPresenting();
		verify(firstPageViewEx).openSubView(any(BreakoutAddView.class));
	}

	@Test
	public void testOnAddBreakoutNoUser() {
		presenter.onAddBreakout();
		verify(firstPageViewEx).showErrorMessage(anyString());
		verify(presenterFactoryEx, never()).createBreakoutAddPresenter(
				any(Presenter.class));
		verify(firstPageViewEx, never())
				.openSubView(any(BreakoutAddView.class));
	}

	@Test
	public void testOnAddBreakoutNoMember() {
		testUser.setGroupMember(false);
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		presenter.onAddBreakout();
		verify(firstPageViewEx).showErrorMessage(anyString());
		verify(presenterFactoryEx, never()).createBreakoutAddPresenter(
				any(Presenter.class));
		verify(firstPageViewEx, never())
				.openSubView(any(BreakoutAddView.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testOnLogon() {
		presenter.onLogon();
		verify(userAuthentication).requireUser(eq(presenter), anyInt(),
				anyMap());
	}

}
