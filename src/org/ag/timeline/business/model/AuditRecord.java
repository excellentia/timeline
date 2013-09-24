package org.ag.timeline.business.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Abhishek Gaurav
 */
public class AuditRecord {

	/**
	 * Unique record ID.
	 */
	private long id = 0;

	/**
	 * Operation performed.
	 */
	private int operation = 0;

	/**
	 * Date on which operation was performed.
	 */
	private Date operationDate = null;

	/**
	 * Time on which operation was performed.
	 */
	private Date operationTime = null;

	/**
	 * Entity on which operation was performed.
	 */
	private String entityName = null;

	/**
	 * Id of the record being audited.
	 */
	private long entityId = 0;

	/**
	 * Type of Business Data.
	 */
	private int dataType = 0;

	/**
	 * User who performed the operation.
	 */
	private Long userId = null;

	/**
	 * Audit details.
	 */
	private Set<AuditRecordDetail> details = null;

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
	 * Getter for operation.
	 * 
	 * @return the operation.
	 */
	public int getOperation() {
		return this.operation;
	}

	/**
	 * Setter for operation.
	 * 
	 * @param operation the operation to set.
	 */
	public void setOperation(int operation) {
		this.operation = operation;
	}

	/**
	 * Getter for operationDate.
	 * 
	 * @return the operationDate.
	 */
	public Date getOperationDate() {
		return this.operationDate;
	}

	/**
	 * Setter for operationDate.
	 * 
	 * @param operationDate the operationDate to set.
	 */
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	/**
	 * Getter for operationTime.
	 * 
	 * @return the operationTime.
	 */
	public Date getOperationTime() {
		return this.operationTime;
	}

	/**
	 * Setter for operationTime.
	 * 
	 * @param operationTime the operationTime to set.
	 */
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	/**
	 * Getter for entityName.
	 * 
	 * @return the entityName.
	 */
	public String getEntityName() {
		return this.entityName;
	}

	/**
	 * Setter for entityName.
	 * 
	 * @param entityName the entityName to set.
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * Getter for entityId.
	 * 
	 * @return the entityId.
	 */
	public long getEntityId() {
		return this.entityId;
	}

	/**
	 * Setter for entityId.
	 * 
	 * @param entityId the entityId to set.
	 */
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Getter for dataType.
	 * 
	 * @return the dataType.
	 */
	public int getDataType() {
		return this.dataType;
	}

	/**
	 * Setter for dataType.
	 * 
	 * @param dataType the dataType to set.
	 */
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	/**
	 * Getter for userId.
	 * 
	 * @return the userId.
	 */
	public Long getUserId() {
		return this.userId;
	}

	/**
	 * Setter for userId.
	 * 
	 * @param userId the userId to set.
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Getter for details.
	 * 
	 * @return the details.
	 */
	public Set<AuditRecordDetail> getDetails() {
		return this.details;
	}

	/**
	 * Setter for details.
	 * 
	 * @param details the details to set.
	 */
	public void setDetails(Set<AuditRecordDetail> details) {
		this.details = details;
	}

	/**
	 * Add single {@link AuditRecordDetail}.
	 * 
	 * @param detail to be added.
	 */
	public void addDetail(AuditRecordDetail detail) {
		if (this.details == null) {
			this.details = new HashSet<AuditRecordDetail>();
		}

		this.details.add(detail);
		detail.setAuditRecord(this);
	}

	/**
	 * Remove single {@link AuditRecordDetail}.
	 * 
	 * @param detail to be removed.
	 */
	public void removeDetail(AuditRecordDetail detail) {
		if ((this.details != null) && (this.details.size() > 0)) {
			this.details.remove(detail);
			detail.setAuditRecord(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AuditRecord [id=");
		builder.append(id);
		builder.append(", operation=");
		builder.append(operation);
		builder.append(", operationDate=");
		builder.append(operationDate);
		builder.append(", operationTime=");
		builder.append(operationTime);
		builder.append(", entityName=");
		builder.append(entityName);
		builder.append(", entityId=");
		builder.append(entityId);
		builder.append(", dataType=");
		builder.append(dataType);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", details=");
		builder.append(details);
		builder.append("]");
		return builder.toString();
	}

}