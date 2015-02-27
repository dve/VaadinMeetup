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
package de.sebastianrothbucher.vaadin.meetup.service;

import javax.persistence.EntityManagerFactory;

import de.sebastianrothbucher.vaadin.meetup.dao.BreakoutDao;

public class BreakoutServicePlainEx extends BreakoutServicePlain implements
		BreakoutServiceEx {

	public BreakoutServicePlainEx() {
		super();
	}

	public BreakoutServicePlainEx(EntityManagerFactory entityManagerFactory,
			BreakoutDao breakoutDao) {
		super(entityManagerFactory, breakoutDao);
		this.breakoutDao = breakoutDao;
	}

	private BreakoutDao breakoutDao = null;
	BreakoutServiceEx backend = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.service.BreakoutServicePlain#
	 * obtainBackend()
	 */
	@Override
	protected BreakoutServiceEx obtainBackend() {
		if (backend == null) {
			backend = new BreakoutServiceImplEx(breakoutDao);
		}
		return backend;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sebastianrothbucher.vaadin.meetup.service.BreakoutServicePlain#
	 * setBreakoutDao(de.sebastianrothbucher.vaadin.meetup.dao.BreakoutDao)
	 */
	@Override
	public void setBreakoutDao(BreakoutDao breakoutDao) {
		super.setBreakoutDao(breakoutDao);
		this.breakoutDao = breakoutDao;
	}

}
