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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.sebastianrothbucher.vaadin.meetup.model.Breakout;
import de.sebastianrothbucher.vaadin.meetup.model.User;
import de.sebastianrothbucher.vaadin.meetup.service.BreakoutServiceEx;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutAddView;
import de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication;

public class BreakoutAddPresenterImplExTest {

	Map<String, Object> context = new HashMap<String, Object>();
	User testUser = new User("Test U.", true);
	BreakoutAddView breakoutAddView;
	BreakoutServiceEx breakoutServiceEx;
	Presenter returnPresenter;
	BreakoutAddPresenterImplEx presenter;

	@Before
	public void setUp() {
		breakoutAddView = mock(BreakoutAddView.class);
		breakoutServiceEx = mock(BreakoutServiceEx.class);
		returnPresenter = mock(Presenter.class);
		presenter = new BreakoutAddPresenterImplEx(context, breakoutAddView,
				returnPresenter, breakoutServiceEx);
	}

	@Test
	public void testStartPresenting() {
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		presenter.setBreakout(new Breakout("Wrong one"));
		presenter.startPresenting();
		verify(breakoutAddView).setObserver(presenter);
		verify(breakoutAddView).initializeUi();
		verify(breakoutAddView).setTopic(null);
		verify(breakoutAddView).setSubmittedByUserName("Test U.");
		verify(breakoutAddView).setLikedCount(0);
	}

	@Test
	public void testStartPresentingNoMember() {
		testUser.setGroupMember(false);
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		presenter.startPresenting();
		verify(breakoutAddView).showErrorMessage(anyString());
		verify(breakoutAddView, never()).setTopic(anyString());
		verify(breakoutAddView, never()).setSubmittedByUserName(anyString());
		verify(breakoutAddView, never()).setLikedCount(anyInt());
	}

	@Test
	public void testStartPresentingNoUser() {
		presenter.startPresenting();
		verify(breakoutAddView).showErrorMessage(anyString());
		verify(breakoutAddView, never()).setTopic(anyString());
		verify(breakoutAddView, never()).setSubmittedByUserName(anyString());
		verify(breakoutAddView, never()).setLikedCount(anyInt());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testOnSave() {
		when(breakoutAddView.checkAllFieldsValid()).thenReturn(true);
		presenter.onSave();
		verify(breakoutAddView).checkAllFieldsValid();
		verify(breakoutServiceEx).addNewBreakout(any(Breakout.class), anyMap());
		verify(returnPresenter).returnToThisPresener(presenter);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testOnSaveInvalid() {
		when(breakoutAddView.checkAllFieldsValid()).thenReturn(false);
		presenter.onSave();
		verify(breakoutAddView).checkAllFieldsValid();
		verify(breakoutServiceEx, never()).addNewBreakout(any(Breakout.class),
				anyMap());
		verify(returnPresenter, never()).returnToThisPresener(
				any(Presenter.class));
	}

	@Test
	public void testOnCancel() {
		presenter.onCancel();
		verify(returnPresenter).returnToThisPresener(presenter);
	}

}
