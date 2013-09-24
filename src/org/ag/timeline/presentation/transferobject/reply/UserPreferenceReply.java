/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

import org.ag.timeline.business.model.UserPreferences;

/**
 * Wrapper for for {@link UserPreferences}.
 * 
 * @author Abhishek Gaurav
 */
public class UserPreferenceReply extends BusinessReply {

	UserPreferences preference = null;

	/**
	 * Getter for preference.
	 * 
	 * @return the preference
	 */
	public UserPreferences getPreference() {
		return preference;
	}

	/**
	 * Setter for preference.
	 * 
	 * @param preference the preference to set
	 */
	public void setPreference(UserPreferences preference) {
		this.preference = preference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserPreferenceReply [preference=").append(preference).append("]");
		return builder.toString();
	}

}
