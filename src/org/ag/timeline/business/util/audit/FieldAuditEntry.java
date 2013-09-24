package org.ag.timeline.business.util.audit;

/**
 * Class encapsulating the data for a single field's audit (Package private).
 * 
 * @author Abhishek Gaurav
 */
class FieldAuditEntry {

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
	 * Field type.
	 */
	private int fieldType = 0;

	/**
	 * Getter for fieldName.
	 * 
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Setter for fieldName.
	 * 
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Getter for oldValue.
	 * 
	 * @return the oldValue
	 */
	public String getOldValue() {
		return oldValue;
	}

	/**
	 * Setter for oldValue.
	 * 
	 * @param oldValue the oldValue to set
	 */
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	/**
	 * Getter for newValue.
	 * 
	 * @return the newValue
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * Setter for newValue.
	 * 
	 * @param newValue the newValue to set
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	/**
	 * Getter for fieldType.
	 * 
	 * @return the fieldType.
	 */
	public int getFieldType() {
		return this.fieldType;
	}

	/**
	 * Setter for fieldType.
	 * 
	 * @param fieldType the fieldType to set.
	 */
	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FieldAuditEntry [fieldName=");
		builder.append(fieldName);
		builder.append(", oldValue=");
		builder.append(oldValue);
		builder.append(", newValue=");
		builder.append(newValue);
		builder.append(", fieldType=");
		builder.append(fieldType);
		builder.append("]");
		return builder.toString();
	}

}
