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

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;

import de.sebastianrothbucher.vaadin.meetup.ui.std.view.util.BundleUtil;
import de.sebastianrothbucher.vaadin.meetup.ui.view.ExceptionMappingStrategy;

public class BreakoutChangeViewImplEx extends BreakoutChangeViewImpl implements
		BreakoutChangeViewEx {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Label topicLabel = new Label();
	protected Label notLikedLabel = new Label();
	protected Label likedLabel = new Label();
	protected Button likeButton = new Button();
	protected Button unLikeButton = new Button();

	public BreakoutChangeViewImplEx(ExceptionMappingStrategy exceptionMappingStrategy) {
		super(exceptionMappingStrategy);
	}

	private BreakoutChangeViewEx.Observer observer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutChangeViewImpl
	 * #initializeUi()
	 */
	@Override
	public void initializeUi() {
		super.initializeUi();
		topicLabel.setCaption(obtainBundle().getString(
				"entity.Breakout.property.topic"));
		topicLabel.addStyleName("styleid-BreakoutEditViewImpl-topicLabel");
		notLikedLabel.setValue(obtainBundle().getString("notLiked"));
		notLikedLabel
				.addStyleName("styleid-BreakoutEditViewImpl-notLikedLabel");
		notLikedLabel.setVisible(false);
		layout.addComponent(notLikedLabel);
		likedLabel.setValue(obtainBundle().getString("liked"));
		likedLabel.addStyleName("styleid-BreakoutEditViewImpl-likedLabel");
		likedLabel.setVisible(false);
		layout.addComponent(likedLabel);
		likeButton.setCaption(obtainBundle().getString("like"));
		likeButton.addStyleName("styleid-BreakoutEditViewImpl-likeButton");
		likeButton.setVisible(false);
		likeButton.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				observer.onLike();
			}
		});
		layout.addComponent(likeButton);
		unLikeButton.setCaption(obtainBundle().getString("unlike"));
		unLikeButton.addStyleName("styleid-BreakoutEditViewImpl-unLikeButton");
		unLikeButton.setVisible(false);
		unLikeButton.addClickListener(new ClickListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				observer.onUnlike();
			}
		});
		layout.addComponent(unLikeButton);
		// and rename "back"
		cancel.setCaption(obtainBundle().getString("back"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutChangeViewImpl
	 * #setObserver
	 * (de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutChangeView
	 * .Observer)
	 */
	@Override
	public void setObserver(
			de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutChangeView.Observer observer) {
		if (!(observer instanceof BreakoutChangeViewEx.Observer)) {
			throw new IllegalArgumentException("Need "
					+ BreakoutChangeViewEx.Observer.class.getName());
		}
		super.setObserver(observer);
		this.observer = (BreakoutChangeViewEx.Observer) observer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutEditViewImpl
	 * #setTopic(java.lang.String)
	 */
	@Override
	public void setTopic(String topic) {
		super.setTopic(topic);
		this.topicLabel.setValue(topic);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutChangeViewEx
	 * #setReadOnlyMode(boolean)
	 */
	@Override
	public void setReadOnlyMode(boolean readOnlyMode) {
		if (readOnlyMode && topic.isAttached()) {
			sectionBasisdaten.replaceComponent(topic, topicLabel);
		} else if ((!readOnlyMode) && topicLabel.isAttached()) {
			sectionBasisdaten.replaceComponent(topicLabel, topic);
		}
		// also hide save button for r-o
		save.setVisible(!readOnlyMode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutChangeViewEx
	 * #setLiked(boolean)
	 */
	@Override
	public void setLiked(boolean isLiked) {
		notLikedLabel.setVisible(!isLiked);
		likedLabel.setVisible(isLiked);
		likeButton.setVisible(!isLiked);
		unLikeButton.setVisible(isLiked);
	}

	private ResourceBundle bundle = BundleUtil.createCommonBundle();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.sebastianrothbucher.vaadin.meetup.ui.std.view.BreakoutEditViewImpl
	 * #obtainBundle()
	 */
	@Override
	protected ResourceBundle obtainBundle() {
		return bundle;
	}

}
