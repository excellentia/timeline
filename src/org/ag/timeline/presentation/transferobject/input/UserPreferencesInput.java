package org.ag.timeline.presentation.transferobject.input;

import org.ag.timeline.business.model.User;
import org.ag.timeline.business.model.UserPreferences;
import org.ag.timeline.common.TimelineConstants;

public class UserPreferencesInput extends AbstractTimelineInput {

	private UserPreferences preferences;

	private TimelineConstants.UserPrefDataFieldType type = null;

	/**
	 * Default Constructor.
	 */
	public UserPreferencesInput() {
		this.preferences = new UserPreferences();
		this.preferences.setUser(new User());
	}

	/**
	 * Getter for userDbId.
	 * 
	 * @return the userDbId.
	 */
	public long getUserDbId() {
		return this.preferences.getUser().getId();
	}

	/**
	 * Setter for userDbId.
	 * 
	 * @param userDbId the userDbId to set.
	 */
	public void setUserDbId(long userDbId) {
		this.preferences.getUser().setId(userDbId);
	}

	/**
	 * Getter for userId.
	 * 
	 * @return the userId.
	 */
	public String getUserId() {
		return this.preferences.getUser().getUserId();
	}

	/**
	 * Setter for userId.
	 * 
	 * @param userId the userId to set.
	 */
	public void setUserId(String userId) {
		this.preferences.getUser().setUserId(userId);
	}

	/**
	 * Getter for question.
	 * 
	 * @return the question
	 */
	public String getQuestion() {
		return this.preferences.getQuestion();
	}

	/**
	 * Setter for question.
	 * 
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.preferences.setQuestion(question);
	}

	/**
	 * Getter for answer.
	 * 
	 * @return the answer
	 */
	public String getAnswer() {
		return this.preferences.getAnswer();
	}

	/**
	 * Setter for answer.
	 * 
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.preferences.setAnswer(answer);
	}

	/**
	 * Getter for Email.
	 * 
	 * @return the Email
	 */
	public String getEmail() {
		return this.preferences.getEmail();
	}

	/**
	 * Setter for Email.
	 * 
	 * @param Email the Email to set
	 */
	public void setEmail(String email) {
		this.preferences.setEmail(email);
	}

	/**
	 * Getter for type.
	 * 
	 * @return the type
	 */
	public TimelineConstants.UserPrefDataFieldType getType() {
		return type;
	}

	/**
	 * Setter for type.
	 * 
	 * @param type the type to set
	 */
	public void setType(TimelineConstants.UserPrefDataFieldType type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserPreferencesInput [");
		builder.append(preferences);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}
