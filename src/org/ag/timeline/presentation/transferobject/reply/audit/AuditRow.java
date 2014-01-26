package org.ag.timeline.presentation.transferobject.reply.audit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ag.timeline.common.TextHelper;
import org.ag.timeline.common.TimelineConstants;

/**
 * Contains audit data.
 * 
 * @author Abhishek Gaurav
 */
public class AuditRow {

	private long id = 0;

	/**
	 * Operation performed.
	 */
	private TimelineConstants.EntityOperation operation = null;

	/**
	 * Date on which operation was performed.
	 */
	private Date operationDate = null;

	/**
	 * Time on which operation was performed.
	 */
	private Date operationTime = null;

	/**
	 * Type of Business Data.
	 */
	private TimelineConstants.AuditDataType dataType = null;

	/**
	 * Entity name.
	 */
	private String entityName = null;

	/**
	 * Audit details releted to this {@link AuditRow}.
	 */
	private List<AuditDetailRow> details = null;

	/**
	 * User who created / modified the entry.
	 */
	private String userName = null;

	/**
	 * Getter for id.
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for id.
	 * 
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for operation code.
	 * 
	 * @return the operation
	 */
	public long getOperationCode() {

		long code = 0;

		if (this.operation != null) {
			code = this.operation.getOpCode();
		}

		return code;
	}

	/**
	 * Getter for operation text.
	 * 
	 * @return the operation
	 */
	public String getOperationText() {

		String text = null;

		if (this.operation != null) {
			text = this.operation.getOpText();
		}

		return text;
	}

	/**
	 * Setter for operation.
	 * 
	 * @param operation the operation to set
	 */
	public void setOperation(TimelineConstants.EntityOperation operation) {
		this.operation = operation;
	}

	/**
	 * Getter for operationDate.
	 * 
	 * @return the operationDate
	 */
	public Date getOperationDate() {
		return operationDate;
	}

	/**
	 * Setter for operationDate.
	 * 
	 * @param operationDate the operationDate to set
	 */
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	/**
	 * Getter for dataType code.
	 * 
	 * @return the dataType
	 */
	public long getDataTypeCode() {
		long code = 0;

		if (this.dataType != null) {
			code = this.dataType.getTypeId();
		}

		return code;
	}

	/**
	 * Getter for dataType text.
	 * 
	 * @return the dataType
	 */
	public String getDataTypeText() {
		String text = null;

		if (this.dataType != null) {
			text = this.dataType.getTypeText();
		}

		return text;
	}

	/**
	 * Setter for dataType.
	 * 
	 * @param dataType the dataType to set
	 */
	public void setDataType(TimelineConstants.AuditDataType dataType) {
		this.dataType = dataType;
	}

	/**
	 * Getter for details.
	 * 
	 * @return the details.
	 */
	public List<AuditDetailRow> getDetails() {
		return this.details;
	}

	/**
	 * Setter for details.
	 * 
	 * @param details the details to set.
	 */
	public void setDetails(List<AuditDetailRow> details) {
		this.details = details;
	}

	/**
	 * Getter for user.
	 * 
	 * @return the user
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Setter for user.
	 * 
	 * @param user the user to set
	 */
	public void setUserName(String user) {
		this.userName = user;
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
	 * Retruns a formatted timestamp.
	 * 
	 * @return String formatted timestamp.
	 */
	public String getFormattedDate() {
		return TextHelper.getDisplayWeekDay(this.operationTime);
	}
	
	/**
	 * Retruns a formatted timestamp.
	 * 
	 * @return String formatted timestamp.
	 */
	public String getFormattedTime() {
		return TextHelper.getTimeAsString(this.operationTime);
	}

	/**
	 * Adds a {@link AuditDetailRow} object to this {@link AuditRow} object.
	 * 
	 * @param detailRow {@link AuditDetailRow} to be added.
	 */
	public void addDetail(AuditDetailRow detailRow) {
		if (this.details == null) {
			this.details = new ArrayList<AuditDetailRow>();
		}
		this.details.add(detailRow);
	}
}
