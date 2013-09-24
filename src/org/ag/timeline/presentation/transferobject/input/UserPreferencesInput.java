package org.ag.timeline.presentation.transferobject.input;

import org.ag.timeline.common.TimelineConstants;

public class UserPreferencesInput extends AbstractTimelineInput {

	private long userDbId = 0;
	private String userId = null;
	private String question = null;
	private String answer = null;
	private TimelineConstants.UserPrefDataFieldType type = null;

	/**
	 * Getter for userDbId.
	 * 
	 * @return the userDbId.
	 */
	public long getUserDbId() {
		return this.userDbId;
	}

	/**
	 * Setter for userDbId.
	 * 
	 * @param userDbId the userDbId to set.
	 */
	public void setUserDbId(long userDbId) {
		this.userDbId = userDbId;
	}

	/**
	 * Getter for userId.
	 * 
	 * @return the userId.
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * Setter for userId.
	 * 
	 * @param userId the userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Getter for question.
	 * 
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * Setter for question.
	 * 
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * Getter for answer.
	 * 
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * Setter for answer.
	 * 
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
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
		builder.append("UserPreferencesInput [userDbId=");
		builder.append(userDbId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", question=");
		builder.append(question);
		builder.append(", answer=");
		builder.append(answer);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}
