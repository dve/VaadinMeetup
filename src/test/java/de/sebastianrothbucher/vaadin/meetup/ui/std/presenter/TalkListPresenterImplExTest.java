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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.vaadin.data.Property;

import de.sebastianrothbucher.vaadin.meetup.model.Talk;
import de.sebastianrothbucher.vaadin.meetup.service.TalkService;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.TalkChangeView;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.TalkListView;
import de.sebastianrothbucher.vaadin.meetup.ui.view.View;

public class TalkListPresenterImplExTest {

	TalkListView talkListView;
	TalkService talkService;
	PresenterFactoryEx presenterFactoryEx;
	TalkListPresenterImplEx presenter;

	@Before
	public void setUp() {
		talkListView = mock(TalkListView.class);
		talkService = mock(TalkService.class);
		presenterFactoryEx = mock(PresenterFactoryEx.class);
		presenter = new TalkListPresenterImplEx(new HashMap<String, Object>(),
				talkListView, presenterFactoryEx, talkService, null);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testStartPresenting() {
		when(talkService.listAllTalk(anyMap())).thenReturn(
				Collections.singletonList(new Talk("Test-Talk")));
		presenter.startPresenting();
		verify(talkService).listAllTalk(anyMap());
		verify(talkListView).setObserver(presenter);
		verify(talkListView).initializeUi();
		verify(talkListView).setOrRefreshData(anyList());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testOnTalkChosen() {
		Property talkProp = mock(Property.class);
		when(talkProp.getValue()).thenReturn(new Talk("Test-Talk"));
		when(talkListView.getTalkSelection()).thenReturn(talkProp);
		TalkChangePresenter talkChangePresenter = mock(TalkChangePresenter.class);
		when(talkChangePresenter.getView()).thenReturn(
				mock(TalkChangeView.class));
		when(presenterFactoryEx.createTalkChangePresenter(any(Presenter.class)))
				.thenReturn(talkChangePresenter);
		presenter.onTalkChosen();
		verify(talkListView).getTalkSelection();
		verify(presenterFactoryEx).createTalkChangePresenter(
				any(Presenter.class));
		verify(talkListView).openSubView(any(TalkChangeView.class));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testOnTalkChosenNone() {
		Property talkProp = mock(Property.class);
		when(talkProp.getValue()).thenReturn(null);
		when(talkListView.getTalkSelection()).thenReturn(talkProp);
		presenter.onTalkChosen();
		verify(talkListView).getTalkSelection();
		verify(presenterFactoryEx, never()).createTalkChangePresenter(
				any(Presenter.class));
		verify(talkListView, never()).openSubView(any(View.class));
	}

	@Test(expected = Exception.class)
	public void testOnAddTalk() {
		presenter.onAddTalk();
	}

}
