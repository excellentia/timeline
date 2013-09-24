/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

import org.ag.timeline.business.model.User;

/**
 * Wrapper for {@link User}.
 * 
 * @author Abhishek Gaurav
 */
public class UserReply extends BusinessReply {

	private User user = null;

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

}
