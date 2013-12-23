/**
 * 
 */
package org.ag.timeline.application.exception;

/**
 * Timeline specific exception.
 * 
 * @author Abhishek Gaurav
 */
public class TimelineException extends Exception {

	/**
	 * Serial version U-Id.
	 */
	private static final long serialVersionUID = 5155281030548336427L;

	/**
	 * Error message.
	 */
	private String errorMessage = null;

	/**
	 * Default Constructor.
	 */
	public TimelineException() {
	}

	/**
	 * Constructor that takes in a error message.
	 * 
	 * @param message String error message to be associated with this
	 *            {@link TimelineException}.
	 */
	public TimelineException(String message) {
		super(message);
		this.setErrorMessage(message);
	}

	/**
	 * Getter for errorMessage.
	 * 
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Setter for errorMessage.
	 * 
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TimelineException [errorMessage=");
		builder.append(errorMessage);
		builder.append("]");
		return builder.toString();
	}

}
