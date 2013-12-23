package org.ag.timeline.presentation.transferobject.input;

import org.ag.timeline.business.model.User;
import org.ag.timeline.common.TimelineConstants;

/**
 * Wrapper for User related data entered by presentation layer.
 * 
 * @author Abhishek Gaurav
 */
public class UserInput extends AbstractTimelineInput {

	private User user = null;

	private TimelineConstants.UserDataFieldType type = null;

	public UserInput() {
		user = new User();
	}

	/**
	 * Getter for id.
	 * 
	 * @return the id
	 */
	public long getId() {
		return user.getId();
	}

	/**
	 * Setter for id.
	 * 
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.user.setId(id);
	}

	/**
	 * Getter for userId.
	 * 
	 * @return the userId
	 */
	public String getUserId() {
		return this.user.getUserId();
	}

	/**
	 * Setter for userId.
	 * 
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.user.setUserId(userId);
	}

	/**
	 * Getter for admin.
	 * 
	 * @return the admin
	 */
	public boolean isAdmin() {
		return this.user.isAdmin();
	}

	/**
	 * Setter for admin.
	 * 
	 * @param admin the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.user.setAdmin(admin);
	}

	/**
	 * Getter for password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return this.user.getPassword();
	}

	/**
	 * Setter for password.
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.user.setPassword(password);
	}

	/**
	 * Getter for firstName.
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return this.user.getFirstName();
	}

	/**
	 * Setter for firstName.
	 * 
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.user.setFirstName(firstName);
	}

	/**
	 * Getter for lastName.
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return this.user.getLastName();
	}

	/**
	 * Setter for lastName.
	 * 
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.user.setLastName(lastName);
	}

	/**
	 * Getter for type.
	 * 
	 * @return the type
	 */
	public TimelineConstants.UserDataFieldType getType() {
		return type;
	}

	/**
	 * Setter for type.
	 * 
	 * @param type the type to set
	 */
	public void setType(TimelineConstants.UserDataFieldType type) {
		this.type = type;
	}
	
	/**
	 * Getter for active.
	 * 
	 * @return the active.
	 */
	public boolean isActive() {
		return this.user.isActive();
	}

	/**
	 * Setter for active.
	 * 
	 * @param active the active to set.
	 */
	public void setActive(boolean active) {
		this.user.setActive(active);
	}

}
