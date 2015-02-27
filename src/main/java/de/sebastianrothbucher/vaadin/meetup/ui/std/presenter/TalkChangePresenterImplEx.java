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

import java.util.Map;

import de.sebastianrothbucher.vaadin.meetup.service.TalkService;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.Presenter;
import de.sebastianrothbucher.vaadin.meetup.ui.presenter.SubviewCapablePresenter;
import de.sebastianrothbucher.vaadin.meetup.ui.std.view.TalkChangeView;

public class TalkChangePresenterImplEx extends TalkChangePresenterImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TalkChangePresenterImplEx(Map<String, Object> context,
			TalkChangeView view, Presenter returnPresenter, TalkService service) {
		super(context, view, returnPresenter, service);
	}

	public TalkChangePresenterImplEx(Map<String, Object> context,
			TalkChangeView view, Presenter returnPresenter,
			SubviewCapablePresenter capablePresenter, TalkService service) {
		super(context, view, returnPresenter, capablePresenter, service);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.TalkChangePresenterImpl
	 * #onSave()
	 */
	@Override
	public void onSave() {
		// save does not make sense
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.presenter.TalkChangePresenterImpl
	 * #onRemove()
	 */
	@Override
	public void onRemove() {
		// not possible here (no button yet either)
		throw new UnsupportedOperationException();
	}

}
