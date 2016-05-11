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

import java.util.ResourceBundle;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;

import de.sebastianrothbucher.vaadin.meetup.ui.std.view.util.BundleUtil;
import de.sebastianrothbucher.vaadin.meetup.ui.view.ExceptionMappingStrategy;

public class BreakoutListViewImplEx extends BreakoutListViewImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BreakoutListViewImplEx(ExceptionMappingStrategy exceptionMappingStrategy) {
		super(exceptionMappingStrategy);
	}

	private BreakoutListView.Observer observer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutListViewImpl
	 * #initializeUi()
	 */
	@Override
	public void initializeUi() {
		super.initializeUi();
		// avoid double-tap
		breakoutTable.addValueChangeListener(new ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					observer.onBreakoutChosen();
				}
			}
		});
	}

	private ResourceBundle bundle = BundleUtil.createCommonBundle();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutListViewImpl
	 * #obtainBundle()
	 */
	@Override
	protected ResourceBundle obtainBundle() {
		return bundle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutListViewImpl
	 * #setObserver
	 * (de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutListView
	 * .Observer)
	 */
	@Override
	public void setObserver(Observer observer) {
		this.observer = observer;
		super.setObserver(observer);
	}

}
