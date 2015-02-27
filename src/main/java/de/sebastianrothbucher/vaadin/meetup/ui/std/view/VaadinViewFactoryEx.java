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
package de.sebastianrothbucher.vaadin.meetup.ui.std.view;

public class VaadinViewFactoryEx extends VaadinViewFactory implements
		ViewFactoryEx {

	public VaadinViewFactoryEx() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.view.VaadinViewFactory#
	 * createBreakoutListView()
	 */
	@Override
	public BreakoutListView createBreakoutListView() {
		return new BreakoutListViewImplEx();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.view.VaadinViewFactory#
	 * createBreakoutAddView()
	 */
	@Override
	public BreakoutAddView createBreakoutAddView() {
		return new BreakoutAddViewImplEx();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.view.VaadinViewFactory#
	 * createBreakoutChangeView()
	 */
	@Override
	public BreakoutChangeViewEx createBreakoutChangeView() {
		return new BreakoutChangeViewImplEx();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.view.VaadinViewFactory#
	 * createFirstPageView()
	 */
	@Override
	public FirstPageViewEx createFirstPageView() {
		return new FirstPageViewImplEx();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.view.VaadinViewFactory#
	 * createTalkListView()
	 */
	@Override
	public TalkListView createTalkListView() {
		return new TalkListViewImplEx();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.ui.std.view.VaadinViewFactory#
	 * createTalkChangeView()
	 */
	@Override
	public TalkChangeView createTalkChangeView() {
		return new TalkChangeViewImplEx();
	}

}
