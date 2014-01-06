package org.ag.timeline.presentation.action;

import org.ag.timeline.presentation.transferobject.reply.ProjectLevelMetricsReply;
import org.ag.timeline.presentation.transferobject.search.ProjectMetricsSearchParameters;

/**
 * Project Metrics related functionality.
 * 
 * @author Abhishek Gaurav
 */
public class MetricsAction extends SecureBaseAction {

	private long projectId = 0;

	private ProjectLevelMetricsReply projectLevelMetricReply = null;

	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	public String secureExecute() throws Exception {

		String forward = SUCCESS;

		ProjectMetricsSearchParameters searchParameters = new ProjectMetricsSearchParameters();

		if (this.projectId > 0) {

			// set report criteria for selected project
			searchParameters.setProjectDbId(this.projectId);

		} else {

			// set report criteria for all data
			searchParameters.setSearchAllData(true);
		}

		// call service
		this.projectLevelMetricReply = service.getProjectLevelMetricsReport(searchParameters);

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
	 * Getter for projectLevelMetricReply.
	 * 
	 * @return the projectLevelMetricReply.
	 */
	public ProjectLevelMetricsReply getProjectLevelMetricReply() {
		return this.projectLevelMetricReply;
	}

	/**
	 * Setter for projectLevelMetricReply.
	 * 
	 * @param projectLevelMetricReply the projectLevelMetricReply to set.
	 */
	public void setProjectLevelMetricReply(ProjectLevelMetricsReply projectLevelMetricReply) {
		this.projectLevelMetricReply = projectLevelMetricReply;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MetricsAction [projectId=");
		builder.append(projectId);
		builder.append(", projectLevelMetricReply=");
		builder.append(projectLevelMetricReply);
		builder.append("]");
		return builder.toString();
	}

}