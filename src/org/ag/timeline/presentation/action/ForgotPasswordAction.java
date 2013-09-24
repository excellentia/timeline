package org.ag.timeline.presentation.action;

import org.ag.timeline.business.model.UserPreferences;
import org.ag.timeline.common.TextHelper;
import org.ag.timeline.presentation.transferobject.input.AuthenticationInput;
import org.ag.timeline.presentation.transferobject.reply.UserPreferenceSearchReply;
import org.ag.timeline.presentation.transferobject.reply.UserReply;
import org.ag.timeline.presentation.transferobject.search.UserPreferenceSearchParameter;

public class ForgotPasswordAction extends BaseAction {

	private String userId = null;
	private String question = null;
	private String answer = null;
	private static final String STEP_TWO_FORWARD = "stepTwo";

	/**
	 * Getter for userId.
	 * 
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Setter for userId.
	 * 
	 * @param userId the userId to set
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

	public String execute() throws Exception {

		String forward = ERROR;
		final String userId = TextHelper.trimToNull(this.getUserId());
		final String answer = TextHelper.trimToNull(this.getAnswer());

		if (userId != null) {

			// fetch question
			UserPreferenceSearchParameter searchParameter = new UserPreferenceSearchParameter();
			searchParameter.setUserId(userId);
			UserPreferenceSearchReply reply = service.searchUserPreferences(searchParameter);

			if (reply != null) {

				UserPreferences preferences = reply.getPreference(userId);

				if (preferences != null) {

					String question = TextHelper.trimToNull(preferences.getQuestion());

					if (question != null) {
						this.setQuestion(question);
						forward = STEP_TWO_FORWARD;

						if (answer != null) {

							forward = ERROR;

							// authenticate with fresh call
							AuthenticationInput input = new AuthenticationInput();
							input.setUserId(userId);
							input.setQuestion(question);
							input.setAnswer(answer);

							// call service
							UserReply userReply = service.autheticateUser(input);

							if (userReply != null) {

								// set data in session
								super.setSessionUser(userReply.getUser());
								forward = SUCCESS;
							}
						}
					}
				}
			}
		}

		return forward;
	}

}
