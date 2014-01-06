package org.ag.timeline.presentation.action;

import org.ag.timeline.presentation.transferobject.reply.ProjectDetailMetricsReply;
import org.ag.timeline.presentation.transferobject.search.ProjectDetailMetricsSearchParameters;

/**
 * Project Metrics Details related functionality.
 * 
 * @author Abhishek Gaurav
 */
public class MetricsDetailsAction extends SecureBaseAction {

	private long projectId = 0;

	private ProjectDetailMetricsReply detailReply = null;

	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	public String secureExecute() throws Exception {

		String forward = SUCCESS;

		ProjectDetailMetricsSearchParameters searchParameters = new ProjectDetailMetricsSearchParameters();

		if (this.projectId > 0) {

			// set report criteria for selected project
			searchParameters.setProjectDbId(this.projectId);

		} else {

			// set report criteria for all data
			searchParameters.setSearchAllData(true);
		}

		// call service
		this.detailReply = service.getProjectDetailMetricsReport(searchParameters);

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
	 * Getter for detailReply.
	 * 
	 * @return the detailReply.
	 */
	public ProjectDetailMetricsReply getDetailReply() {
		return this.detailReply;
	}

	/**
	 * Setter for detailReply.
	 * 
	 * @param detailReply the detailReply to set.
	 */
	public void setDetailReply(ProjectDetailMetricsReply detailReply) {
		this.detailReply = detailReply;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MetricsDetailsAction [projectId=");
		builder.append(projectId);
		builder.append(", detailReply=");
		builder.append(detailReply);
		builder.append("]");
		return builder.toString();
	}

}