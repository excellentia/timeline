package org.ag.timeline.presentation.transferobject.input;

/**
 * Input object containing authetication related data.
 * 
 * @author Abhishek Gaurav
 */
public class AuthenticationInput extends UserPreferencesInput {

	private String password = null;

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

}
