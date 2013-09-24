package org.ag.timeline.presentation.action;

import org.ag.timeline.presentation.transferobject.reply.ReportDataReply;
import org.ag.timeline.presentation.transferobject.search.ReportSearchParameters;

/**
 * Report related functionality.
 * 
 * @author Abhishek Gaurav
 */
public class ReportAction extends SecureBaseAction {

	private long projectId = 0;
	private long activityId = 0;
	private long userId = 0;
	private boolean exportToFile = false;
	private ReportDataReply reply = null;

	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	public String secureExecute() throws Exception {

		String forward = null;
		ReportSearchParameters searchParameters = new ReportSearchParameters();

		if (!exportToFile) {

			searchParameters.setProjectDbId(projectId);
			searchParameters.setActivityDbId(activityId);
			searchParameters.setUserDbId(userId);

			if ((projectId <= 0) && (activityId <= 0) && (userId <= 0)) {
				searchParameters.setSearchAllData(Boolean.TRUE);
			}

			forward = SUCCESS;
		} else {
			searchParameters.setSearchAllData(Boolean.TRUE);
			forward = null;
		}

		this.reply = service.getReport(searchParameters);
		return forward;
	}

	/**
	 * Getter for projectId.
	 * 
	 * @return the projectId
	 */
	public long getProjectId() {
		return projectId;
	}

	/**
	 * Setter for projectId.
	 * 
	 * @param projectId the projectId to set
	 */
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	/**
	 * Getter for activityId.
	 * 
	 * @return the activityId
	 */
	public long getActivityId() {
		return activityId;
	}

	/**
	 * Setter for activityId.
	 * 
	 * @param activityId the activityId to set
	 */
	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	/**
	 * Getter for userId.
	 * 
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * Setter for userId.
	 * 
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * Getter for reply.
	 * 
	 * @return the reply
	 */
	public ReportDataReply getReply() {
		return reply;
	}

	/**
	 * Setter for reply.
	 * 
	 * @param reply the reply to set
	 */
	public void setReply(ReportDataReply reply) {
		this.reply = reply;
	}

	/**
	 * Getter for exportToFile.
	 * 
	 * @return the exportToFile
	 */
	public boolean isExportToFile() {
		return exportToFile;
	}

	/**
	 * Setter for exportToFile.
	 * 
	 * @param exportToFile the exportToFile to set
	 */
	public void setExportToFile(boolean exportToFile) {
		this.exportToFile = exportToFile;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportAction [projectId=");
		builder.append(projectId);
		builder.append(", activityId=");
		builder.append(activityId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", exportToFile=");
		builder.append(exportToFile);
		builder.append(", reply=");
		builder.append(reply);
		builder.append("]");
		return builder.toString();
	}

}