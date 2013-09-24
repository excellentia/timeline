/**
 * 
 */
package org.ag.timeline.business.model;


/**
 * User Preferences.
 * 
 * @author Abhishek Gaurav
 */
public class UserPreferences extends AbstractModel {

	private User user = null;

	private String question = null;

	private String answer = null;

	/**
	 * Getter for user.
	 * 
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setter for user.
	 * 
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
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

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserPreferences [user=").append(user).append(", question=").append(question)
				.append(", answer=").append(answer).append("]");
		return builder.toString();
	}

	@Override
	public String getDescription() {
		return this.getUser().getUserName();
	}

}
