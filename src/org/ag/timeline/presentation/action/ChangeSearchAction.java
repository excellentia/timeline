package org.ag.timeline.presentation.action;

import java.util.Date;

import org.ag.timeline.business.model.audit.AuditRecord;
import org.ag.timeline.common.TextHelper;
import org.ag.timeline.common.TimelineConstants;
import org.ag.timeline.presentation.transferobject.reply.audit.AuditDataReply;
import org.ag.timeline.presentation.transferobject.search.AuditDataSearchParameters;

/**
 * Searches {@link AuditRecord} objects.
 * 
 * @author Abhishek Gaurav
 */
public class ChangeSearchAction extends SecureBaseAction {

	private int entityType = 0;
	private int userId = 0;
	private int operationType = 0;

	private String fromDate = null;
	private String toDate = null;
	private boolean search = false;

	private AuditDataReply reply = null;

	/**
	 * Getter for entityType.
	 * 
	 * @return the entityType.
	 */
	public int getEntityType() {
		return this.entityType;
	}

	/**
	 * Setter for entityType.
	 * 
	 * @param entityType the entityType to set.
	 */
	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}

	/**
	 * Getter for userId.
	 * 
	 * @return the userId.
	 */
	public int getUserId() {
		return this.userId;
	}

	/**
	 * Setter for userId.
	 * 
	 * @param userId the userId to set.
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Getter for operationType.
	 * 
	 * @return the operationType.
	 */
	public int getOperationType() {
		return this.operationType;
	}

	/**
	 * Setter for operationType.
	 * 
	 * @param operationType the operationType to set.
	 */
	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}

	/**
	 * Getter for fromDate.
	 * 
	 * @return the fromDate.
	 */
	public String getFromDate() {
		return this.fromDate;
	}

	/**
	 * Setter for fromDate.
	 * 
	 * @param fromDate the fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * Getter for toDate.
	 * 
	 * @return the toDate.
	 */
	public String getToDate() {
		return this.toDate;
	}

	/**
	 * Setter for toDate.
	 * 
	 * @param toDate the toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * Getter for reply.
	 * 
	 * @return the reply.
	 */
	public AuditDataReply getReply() {
		return this.reply;
	}

	/**
	 * Setter for reply.
	 * 
	 * @param reply the reply to set.
	 */
	public void setReply(AuditDataReply reply) {
		this.reply = reply;
	}

	/**
	 * Getter for search.
	 * 
	 * @return the search.
	 */
	public boolean isSearch() {
		return this.search;
	}

	/**
	 * Setter for search.
	 * 
	 * @param search the search to set.
	 */
	public void setSearch(boolean search) {
		this.search = search;
	}

	@Override
	public String secureExecute() throws Exception {

		if (search) {

			Date fromDat = TextHelper.getValidDate(fromDate, TextHelper.WEEK_DAY_FORMAT);
			Date toDat = TextHelper.getValidDate(toDate, TextHelper.WEEK_DAY_FORMAT);

			AuditDataSearchParameters parameters = new AuditDataSearchParameters();
			parameters.setFromDate(fromDat);
			parameters.setToDate(toDat);
			parameters.setOperationType(TimelineConstants.EntityOperation.getOperation(operationType));
			parameters.setType(TimelineConstants.AuditDataType.getAuditDataType(entityType));

			if (!super.isSessionUserAdmin()) {
				parameters.setUserId(super.getSessionUserId());
			} else {
				parameters.setUserId(userId);
			}

			reply = service.searchAuditData(parameters);
		}

		return SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChangeSearchAction [entityType=");
		builder.append(entityType);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", operationType=");
		builder.append(operationType);
		builder.append(", fromDate=");
		builder.append(fromDate);
		builder.append(", toDate=");
		builder.append(toDate);
		builder.append(", search=");
		builder.append(search);
		builder.append(", reply=");
		builder.append(reply);
		builder.append("]");
		return builder.toString();
	}

}
