/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

import java.util.ArrayList;
import java.util.List;

import org.ag.timeline.business.model.User;

/**
 * Wrapper for search results for {@link User}.
 * 
 * @author Abhishek Gaurav
 */
public class UserSearchReply extends BusinessReply {

	private List<User> users = null;

	public UserSearchReply() {
		users = new ArrayList<User>();
	}

	/**
	 * Getter for users.
	 * 
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * Setter for users.
	 * 
	 * @param users the users to set
	 */
	public void addUsers(User users) {
		this.users.add(users);
	}

	public User getUser(final long id) {
		User user = null;

		if (users != null) {
			for (User u : users) {
				if ((u != null) && (u.getId() == id)) {
					user = u;
					break;
				}
			}
		}

		return user;
	}
}
