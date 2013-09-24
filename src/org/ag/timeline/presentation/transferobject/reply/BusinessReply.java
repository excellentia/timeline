/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

/**
 * Wrapper for business service response.
 * 
 * @author Abhishek Gaurav
 */
public class BusinessReply {

	private String message = null;
	private boolean msgError = false;

	/**
	 * Constructor.
	 */
	public BusinessReply() {

	}

	/**
	 * Parameterized constructor.
	 * 
	 * @param message Message to be set.
	 * @param isMsgError boolean flag denoting the message type as error (true)
	 *            or success (false).
	 */
	public BusinessReply(String message, boolean isMsgError) {
		this.message = message;
		this.msgError = isMsgError;
	}

	public void setSuccessMessage(final String message) {
		this.message = message;
		this.msgError = false;
	}

	public void setErrorMessage(final String message) {
		this.message = message;
		this.msgError = true;
	}

	/**
	 * Getter for message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for message.
	 * 
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter for msgError.
	 * 
	 * @return the msgError
	 */
	public boolean isMsgError() {
		return msgError;
	}

	/**
	 * Setter for msgError.
	 * 
	 * @param msgError the msgError to set
	 */
	public void setMsgError(boolean msgError) {
		this.msgError = msgError;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BusinessReply [message=");
		builder.append(message);
		builder.append(", msgError=");
		builder.append(msgError);
		builder.append("]");
		return builder.toString();
	}

}
