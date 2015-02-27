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
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.vaadin.data.Property;

import de.sebastianrothbucher.vaadin.meetup.model.Breakout;
import de.sebastianrothbucher.vaadin.meetup.model.User;
import de.sebastianrothbucher.vaadin.meetup.service.BreakoutServiceEx;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutAddView;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutChangeView;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutChangeViewEx;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutListView;
import de.sebastianrothbucher.vaadin.meetup.userauth.UserAuthentication;

public class BreakoutListPresenterImplExTest {

	Map<String, Object> context = new HashMap<String, Object>();
	User testUser = new User(22, "Test U.", true);
	BreakoutListView breakoutListView;
	BreakoutServiceEx breakoutServiceEx;
	PresenterFactoryEx presenterFactoryEx;
	BreakoutListPresenterImplEx presenter;

	@Before
	public void setUp() {
		breakoutListView = mock(BreakoutListView.class);
		breakoutServiceEx = mock(BreakoutServiceEx.class);
		presenterFactoryEx = mock(PresenterFactoryEx.class);
		presenter = new BreakoutListPresenterImplEx(context, breakoutListView,
				presenterFactoryEx, breakoutServiceEx, null);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testStartPresenting() {
		when(breakoutServiceEx.listAllBreakout(anyMap())).thenReturn(
				Collections.singletonList(new Breakout("Test-Topic")));
		presenter.startPresenting();
		verify(breakoutServiceEx).listAllBreakout(anyMap());
		verify(breakoutListView).setObserver(presenter);
		verify(breakoutListView).initializeUi();
		verify(breakoutListView).setOrRefreshData(anyList());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testOnBreakoutChosen() {
		Property breakoutProp = mock(Property.class);
		when(breakoutProp.getValue()).thenReturn(new Breakout("Test-Topic"));
		when(breakoutListView.getBreakoutSelection()).thenReturn(breakoutProp);
		BreakoutChangePresenter breakoutChangePresenter = mock(BreakoutChangePresenter.class);
		when(breakoutChangePresenter.getView()).thenReturn(
				mock(BreakoutChangeViewEx.class));
		when(
				presenterFactoryEx
						.createBreakoutChangePresenter(any(Presenter.class)))
				.thenReturn(breakoutChangePresenter);
		presenter.onBreakoutChosen();
		verify(breakoutListView).getBreakoutSelection();
		verify(presenterFactoryEx).createBreakoutChangePresenter(
				any(Presenter.class));
		verify(breakoutListView).openSubView(any(BreakoutChangeView.class));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testOnBreakoutChosenNone() {
		Property breakoutProp = mock(Property.class);
		when(breakoutProp.getValue()).thenReturn(null);
		when(breakoutListView.getBreakoutSelection()).thenReturn(breakoutProp);
		presenter.onBreakoutChosen();
		verify(breakoutListView).getBreakoutSelection();
		verify(presenterFactoryEx, never()).createBreakoutChangePresenter(
				any(Presenter.class));
		verify(breakoutListView, never()).openSubView(
				any(BreakoutChangeView.class));
	}

	@Test
	public void testOnAddBreakout() {
		context.put(UserAuthentication.CURRENT_USER_CONTEXT_KEY, testUser);
		BreakoutAddPresenter breakoutAddPresenter = mock(BreakoutAddPresenter.class);
		when(breakoutAddPresenter.getView()).thenReturn(
				mock(BreakoutAddView.class));
		when(
				presenterFactoryEx
						.createBreakoutAddPresenter(any(Presenter.class)))
				.thenReturn(breakoutAddPresenter);
		presenter.onAddBreakout();
		verify(presenterFactoryEx).createBreakoutAddPresenter(presenter);
		verify(breakoutAddPresenter).startPresenting();
		verify(breakoutListView).openSubView(any(BreakoutAddView.class));
	}

	@Test
	public void testOnAddBreakoutNoUser() {
		when(
				presenterFactoryEx
						.createBreakoutAddPresenter(any(Presenter.class)))
				.thenReturn(mock(BreakoutAddPresenter.class));
		presenter.onAddBreakout();
		verify(breakoutListView).showErrorMessage(anyString());
		verify(presenterFactoryEx, never()).createBreakoutAddPresenter(
				any(Presenter.class));
		verify(breakoutListView, never()).openSubView(
				any(BreakoutAddView.class));
	}

}
