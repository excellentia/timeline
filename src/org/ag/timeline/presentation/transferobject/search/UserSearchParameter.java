package org.ag.timeline.presentation.transferobject.search;

import org.ag.timeline.business.model.User;

public class UserSearchParameter extends BasicSearchParameter {

	private User user = null;

	/**
	 * Constructor.
	 */
	public UserSearchParameter() {
		user = new User();
	}

	public long getId() {
		return this.user.getId();
	}

	public void setId(long id) {
		this.user.setId(id);
	}

	public String getUserId() {
		return this.user.getUserId();
	}

	public void setUserId(String userId) {
		this.user.setUserId(userId);
	}

	public String getFirstName() {
		return this.user.getFirstName();
	}

	public void setFirstName(String text) {
		this.user.setFirstName(text);
	}

	public String getLastName() {
		return this.user.getLastName();
	}

	public void setLastName(String text) {
		this.user.setLastName(text);
	}

	public boolean getOnlyAdmin() {
		return this.user.isAdmin();
	}

	public void setOnlyAdmin(boolean admin) {
		this.user.setAdmin(admin);
	}

	public boolean getOnlyActive() {
		return this.user.isActive();
	}

	public void setOnlyActive(boolean active) {
		this.user.setActive(active);
	}

}
