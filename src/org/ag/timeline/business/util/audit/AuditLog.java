package org.ag.timeline.business.util.audit;

import java.util.Date;
import java.util.Set;

import org.ag.timeline.common.TimelineConstants;

/**
 * Wrapper for data related to audit (Package private).
 * 
 * @author Abhishek Gaurav
 */
class AuditLog {

	/**
	 * Set of audit date per field.
	 */
	private Set<FieldAuditEntry> fieldAuditData = null;

	/**
	 * User who performed the operation.
	 */
	private Long userId = null;

	/**
	 * Operation performed.
	 */
	private int operation = 0;

	/**
	 * Timestap of operation.
	 */
	private Date operationTimestamp = null;

	/**
	 * Entity on which operation was performed.
	 */
	private String entityName = null;

	/**
	 * Id of the record being audited.
	 */
	private long entityId = 0;

	/**
	 * Business data type.
	 */
	private TimelineConstants.AuditDataType dataType = null;

	/**
	 * Getter for fieldAuditData.
	 * 
	 * @return the fieldAuditData
	 */
	public Set<FieldAuditEntry> getFieldAuditData() {
		return fieldAuditData;
	}

	/**
	 * Setter for fieldAuditData.
	 * 
	 * @param fieldAuditData the fieldAuditData to set
	 */
	public void setFieldAuditData(Set<FieldAuditEntry> fieldAuditData) {
		this.fieldAuditData = fieldAuditData;
	}

	/**
	 * Getter for userId.
	 * 
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Setter for userId.
	 * 
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Getter for operation.
	 * 
	 * @return the operation
	 */
	public int getOperation() {
		return operation;
	}

	/**
	 * Setter for operation.
	 * 
	 * @param operation the operation to set
	 */
	public void setOperation(int operation) {
		this.operation = operation;
	}

	/**
	 * Getter for operationTimestamp.
	 * 
	 * @return the operationTimestamp
	 */
	public Date getOperationTimestamp() {
		return operationTimestamp;
	}

	/**
	 * Setter for operationTimestamp.
	 * 
	 * @param operationTimestamp the operationTimestamp to set
	 */
	public void setOperationTimestamp(Date operationTimestamp) {
		this.operationTimestamp = operationTimestamp;
	}

	/**
	 * Getter for entityName.
	 * 
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * Setter for entityName.
	 * 
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * Getter for entityId.
	 * 
	 * @return the entityId
	 */
	public long getEntityId() {
		return entityId;
	}

	/**
	 * Setter for entityId.
	 * 
	 * @param entityId the entityId to set
	 */
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Getter for dataType.
	 * 
	 * @return the dataType.
	 */
	public TimelineConstants.AuditDataType getDataType() {
		return this.dataType;
	}

	/**
	 * Setter for dataType.
	 * 
	 * @param dataType the dataType to set.
	 */
	public void setDataType(TimelineConstants.AuditDataType dataType) {
		this.dataType = dataType;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AuditLog [fieldAuditData=");
		builder.append(fieldAuditData);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", operation=");
		builder.append(operation);
		builder.append(", operationTimestamp=");
		builder.append(operationTimestamp);
		builder.append(", entityName=");
		builder.append(entityName);
		builder.append(", entityId=");
		builder.append(entityId);
		builder.append(", dataType=");
		builder.append(dataType);
		builder.append("]");
		return builder.toString();
	}

}