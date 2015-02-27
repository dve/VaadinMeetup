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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import de.sebastianrothbucher.vaadin.meetup.model.Talk;
import de.sebastianrothbucher.vaadin.meetup.service.TalkService;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.TalkChangeView;

public class TalkChangePresenterImplExTest {

	TalkChangeView talkChangeView;
	TalkService talkService;
	Presenter returnPresenter;
	TalkChangePresenterImplEx presenter;

	@Before
	public void setUp() {
		talkChangeView = mock(TalkChangeView.class);
		talkService = mock(TalkService.class);
		returnPresenter = mock(Presenter.class);
		presenter = new TalkChangePresenterImplEx(
				new HashMap<String, Object>(), talkChangeView, returnPresenter,
				talkService);
	}

	@Test
	public void testStartPresenting() {
		presenter.setTalk(new Talk("Test-Talk"));
		presenter.startPresenting();
		verify(talkChangeView).setObserver(presenter);
		verify(talkChangeView).initializeUi();
		verify(talkChangeView).setTitle("Test-Talk");
	}

	@Test(expected = Exception.class)
	public void testOnSave() {
		presenter.onSave();
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
