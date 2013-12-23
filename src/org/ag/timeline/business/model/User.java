/**
 * 
 */
package org.ag.timeline.business.model;

import org.ag.timeline.common.TextHelper;
import org.ag.timeline.common.TimelineConstants;

/**
 * Denotes data for a user.
 * 
 * @author Abhishek Gaurav
 */
public class User extends AbstractModel {

	private String firstName = null;

	private String lastName = null;

	private String userId = null;

	private String password = "default";

	private boolean admin = false;

	private boolean active = true;

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
	 * Getter for admin.
	 * 
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * Setter for admin.
	 * 
	 * @param admin the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
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

	/**
	 * Getter for firstName.
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for firstName.
	 * 
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for lastName.
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for lastName.
	 * 
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for active.
	 * 
	 * @return the active.
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * Setter for active.
	 * 
	 * @param active the active to set.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Business method creating simple representation if user name.
	 * 
	 * @return User name
	 */
	public String getUserName() {
		StringBuilder builder = new StringBuilder();

		String firstName = TextHelper.trimToNull(this.firstName);
		String lastName = TextHelper.trimToNull(this.lastName);

		if (firstName != null) {
			builder.append(firstName);
		}

		if ((firstName != null) && (lastName != null)) {
			builder.append(TimelineConstants.SPACE).append(lastName);
		} else if (lastName != null) {
			builder.append(lastName);
		}

		return builder.toString();

	}

	/**
	 * Business method creating simple representation if user name with
	 * abbreviated lastname.
	 * 
	 * @return User name
	 */
	public String getAbbrvUserName() {
		StringBuilder builder = new StringBuilder();

		String firstName = TextHelper.trimToNull(this.firstName);
		String lastName = TextHelper.trimToNull(this.lastName);

		if (firstName != null) {
			builder.append(firstName);
		}

		if ((firstName != null) && (lastName != null)) {
			builder.append(TimelineConstants.SPACE).append(lastName.substring(0, 1).toUpperCase())
					.append(TimelineConstants.DOT);
		} else if (lastName != null) {
			builder.append(lastName);
		}

		return builder.toString();

	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(super.getId());
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", admin=");
		builder.append(admin);
		builder.append(", active=");
		builder.append(active);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String getDescription() {
		return this.getUserName();
	}

}
