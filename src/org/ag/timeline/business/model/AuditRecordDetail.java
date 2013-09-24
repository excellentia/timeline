package org.ag.timeline.business.model;

/**
 * Contains details related to on {@link AuditRecord}.
 * 
 * @author Abhishek Gaurav
 */
public class AuditRecordDetail {

	/**
	 * Unique ID.
	 */
	private long id = 0;

	/**
	 * Parent {@link AuditRecord} object.
	 */
	private AuditRecord auditRecord = null;

	/**
	 * Field on which operation was performed.
	 */
	private String fieldName = null;

	/**
	 * Field type.
	 */
	private int fieldType = 0;

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

	/**
	 * Getter for auditRecord.
	 * 
	 * @return the auditRecord.
	 */
	public AuditRecord getAuditRecord() {
		return this.auditRecord;
	}

	/**
	 * Setter for auditRecord.
	 * 
	 * @param auditRecord the auditRecord to set.
	 */
	public void setAuditRecord(AuditRecord auditRecord) {
		this.auditRecord = auditRecord;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AuditRecordDetail [id=");
		builder.append(id);
		builder.append(", auditRecord=");
		builder.append(auditRecord);
		builder.append(", fieldName=");
		builder.append(fieldName);
		builder.append(", fieldType=");
		builder.append(fieldType);
		builder.append(", oldValue=");
		builder.append(oldValue);
		builder.append(", newValue=");
		builder.append(newValue);
		builder.append("]");
		return builder.toString();
	}

}