package org.ag.timeline.presentation.transferobject.reply.audit;


/**
 * Contains audit detail.
 * 
 * @author Abhishek Gaurav
 */
public class AuditDetailRow {

	private long id = 0;

	/**
	 * Field on which operation was performed.
	 */
	private String fieldName = null;

	/**
	 * Old value (value before operation).
	 */
	private String oldValue = null;

	/**
	 * New value (value after operation).
	 */
	private String newValue = null;

	/**
	 * Getter for id.
	 * 
	 * @return the id.
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * Setter for id.
	 * 
	 * @param id the id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for fieldName.
	 * 
	 * @return the fieldName.
	 */
	public String getFieldName() {
		return this.fieldName;
	}

	/**
	 * Setter for fieldName.
	 * 
	 * @param fieldName the fieldName to set.
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Getter for oldValue.
	 * 
	 * @return the oldValue.
	 */
	public String getOldValue() {
		return this.oldValue;
	}

	/**
	 * Setter for oldValue.
	 * 
	 * @param oldValue the oldValue to set.
	 */
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	/**
	 * Getter for newValue.
	 * 
	 * @return the newValue.
	 */
	public String getNewValue() {
		return this.newValue;
	}

	/**
	 * Setter for newValue.
	 * 
	 * @param newValue the newValue to set.
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AuditDetailRow [id=");
		builder.append(id);
		builder.append(", fieldName=");
		builder.append(fieldName);
		builder.append(", oldValue=");
		builder.append(oldValue);
		builder.append(", newValue=");
		builder.append(newValue);
		builder.append("]");
		return builder.toString();
	}

}
