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

public interface BreakoutChangeViewEx extends BreakoutChangeView {

	public void setReadOnlyMode(boolean readOnlyMode);
	
	public void setLiked(boolean isLiked);
	
	public static interface Observer extends BreakoutChangeView.Observer {
		
		public void onLike();
		
		public void onUnlike();
		
	}
	
}
