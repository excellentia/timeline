package org.ag.timeline.presentation.action;

import org.ag.timeline.common.TextHelper;
import org.ag.timeline.presentation.transferobject.input.AuthenticationInput;
import org.ag.timeline.presentation.transferobject.reply.UserReply;

/**
 * Login related functionality.
 * 
 * @author Abhishek Gaurav.
 */
public class LoginAction extends BaseAction {

	private String userId = null;
	private String password = null;

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
	 * Getter for password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for password.
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String execute() throws Exception {

		long time = System.nanoTime();
		String result = ERROR;

		{
			String userId = TextHelper.trimToNull(getUserId());
			String password = TextHelper.trimToNull(getPassword());

			if ((userId != null) && (password != null)) {
				
				//populate input data
				AuthenticationInput input = new AuthenticationInput();
				input.setPassword(password);
				input.setUserId(userId);
				
				//call service
				UserReply userReply = service.autheticateUser(input);

				if (userReply != null) {

					// set data in session
					super.setSessionUser(userReply.getUser());

					result = SUCCESS;
				}
			}
		}
		
		TextHelper.logMessage("LoginAction.execute() > Time taken : " + ((System.nanoTime() - time) / 1000000));

		return result;
	}
}