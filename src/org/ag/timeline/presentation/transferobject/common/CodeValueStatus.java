/**
 * 
 */
package org.ag.timeline.presentation.transferobject.common;

/**
 * Code value status wrapper.
 * 
 * @author Abhishek Gaurav
 */
public class CodeValueStatus extends CodeValue {

	private boolean status = false;

	/**
	 * Constructor.
	 * 
	 * @param code Code to be set
	 * @param value Value to be set
	 * @param status boolean status to be set.
	 */
	public CodeValueStatus(long code, String value, boolean status) {
		super(code, value);
		this.status = status;
	}

	/**
	 * Getter for status.
	 * 
	 * @return the status.
	 */
	public boolean isStatus() {
		return this.status;
	}

	/**
	 * Setter for status.
	 * 
	 * @param status the status to set.
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CodeValueStatus [");
		builder.append(super.toString());
		builder.append(", status = ");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
