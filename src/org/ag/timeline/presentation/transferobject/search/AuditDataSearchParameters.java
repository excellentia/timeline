package org.ag.timeline.presentation.transferobject.search;

import java.util.Date;

import org.ag.timeline.common.TimelineConstants;

public class AuditDataSearchParameters extends BasicSearchParameter {

	private Date fromDate = null;
	private Date toDate = null;
	private TimelineConstants.AuditDataType type = null;
	private TimelineConstants.EntityOperation operationType = null;
	private long userId = 0;

	/**
	 * Getter for userId.
	 * 
	 * @return the userId.
	 */
	public long getUserId() {
		return this.userId;
	}

	/**
	 * Setter for userId.
	 * 
	 * @param userId the userId to set.
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * Getter for type.
	 * 
	 * @return the type
	 */
	public TimelineConstants.AuditDataType getType() {
		return type;
	}

	/**
	 * Setter for type.
	 * 
	 * @param type the type to set
	 */
	public void setType(TimelineConstants.AuditDataType type) {
		this.type = type;
	}

	/**
	 * Getter for fromDate.
	 * 
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * Setter for fromDate.
	 * 
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * Getter for toDate.
	 * 
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * Setter for toDate.
	 * 
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * Getter for operationtype.
	 * 
	 * @return the operationtype
	 */
	public TimelineConstants.EntityOperation getOperationType() {
		return operationType;
	}

	/**
	 * Setter for operationtype.
	 * 
	 * @param operationtype the operationtype to set
	 */
	public void setOperationType(TimelineConstants.EntityOperation operationType) {
		this.operationType = operationType;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AuditDataSearchParameters [fromDate=");
		builder.append(fromDate);
		builder.append(", toDate=");
		builder.append(toDate);
		builder.append(", type=");
		builder.append(type);
		builder.append(", operationType=");
		builder.append(operationType);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}

}
