package org.ag.timeline.presentation.action;

import org.ag.timeline.business.model.UserPreferences;
import org.ag.timeline.presentation.transferobject.reply.UserPreferenceSearchReply;
import org.ag.timeline.presentation.transferobject.reply.UserSearchReply;
import org.ag.timeline.presentation.transferobject.search.UserPreferenceSearchParameter;
import org.ag.timeline.presentation.transferobject.search.UserSearchParameter;

/**
 * Account Settings related funtionality.
 * 
 * @author Abhishek Gaurav
 */
public class AccountSettingsAction extends SecureBaseAction {

	UserPreferences preferences = null;

	/**
	 * Getter for preferences.
	 * 
	 * @return the preferences
	 */
	public UserPreferences getPreferences() {
		return preferences;
	}

	/**
	 * Setter for preferences.
	 * 
	 * @param preferences the preferences to set
	 */
	public void setPreferences(UserPreferences preferences) {
		this.preferences = preferences;
	}

	public String secureExecute() throws Exception {

		final long sessionUserId = getSessionUserId();

		// get the updated user
		{
			UserSearchParameter parameter = new UserSearchParameter();
			parameter.setId(sessionUserId);
			UserSearchReply reply = service.searchUsers(parameter);

			if (reply != null) {
				super.setSessionUser(reply.getUser(sessionUserId));
			}
		}

		// get the updated preferences
		{
			UserPreferenceSearchParameter searchParameters = new UserPreferenceSearchParameter();
			searchParameters.setId(sessionUserId);
			UserPreferenceSearchReply reply = service.searchUserPreferences(searchParameters);

			if (reply != null) {
				preferences = reply.getPreference(sessionUserId);
			}
		}

		return SUCCESS;
	}

}
