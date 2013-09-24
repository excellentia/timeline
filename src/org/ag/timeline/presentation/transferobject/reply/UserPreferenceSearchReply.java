/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

import java.util.HashMap;
import java.util.Map;

import org.ag.timeline.business.model.UserPreferences;

/**
 * Wrapper for search results for {@link UserPreferences}.
 * 
 * @author Abhishek Gaurav
 */
public class UserPreferenceSearchReply extends BusinessReply {

	/**
	 * Map containing user preferences.
	 */
	private Map<Long, UserPreferences> idMap = null;
	private Map<String, UserPreferences> userIdMap = null;

	/**
	 * Constructor.
	 */
	public UserPreferenceSearchReply() {
		this.idMap = new HashMap<Long, UserPreferences>();
		this.userIdMap = new HashMap<String, UserPreferences>();
	}

	/**
	 * Adds a {@link UserPreferences} object to existing collection.
	 * 
	 * @param preferences {@link UserPreferences} to be saved.
	 */
	public void addPreference(UserPreferences preferences) {
		if (preferences != null) {
			this.idMap.put(preferences.getUser().getId(), preferences);
			this.userIdMap.put(preferences.getUser().getUserId(), preferences);
		}
	}

	/**
	 * Getter for {@link UserPreferences} for given user.
	 * 
	 * @param id Unique database identifier for the user for which the
	 *            {@link UserPreferences} is to be fecthed.
	 * @return {@link UserPreferences}
	 */
	public UserPreferences getPreference(final long id) {
		return this.idMap.get(id);
	}

	/**
	 * Getter for {@link UserPreferences} for given user.
	 * 
	 * @param userId User Id for which the {@link UserPreferences} is to be
	 *            fecthed.
	 * @return {@link UserPreferences}
	 */
	public UserPreferences getPreference(final String userId) {
		return this.userIdMap.get(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserPreferenceReply [idMap=").append(idMap).append(", userIdMap=").append(userIdMap)
				.append("]");
		return builder.toString();
	}

}
