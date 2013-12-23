/**
 * 
 */
package org.ag.timeline.common;

import java.util.Comparator;

import org.ag.timeline.business.model.User;

/**
 * Comparator for {@link User}.
 * 
 * @author Abhishek Gaurav
 */
public class UserComparator implements Comparator<User> {

	public int compare(User user1, User user2) {
		int retVal = 0;

		if ((user1 != null) || (user2 != null)) {

			if (user1 == null) {
				retVal = 1;
			} else if (user2 == null) {
				retVal = -1;
			} else {
				retVal = user1.getUserName().compareTo(user2.getUserName());
			}
		}

		return retVal;
	}

}
