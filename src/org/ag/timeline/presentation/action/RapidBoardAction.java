package org.ag.timeline.presentation.action;

import org.ag.timeline.presentation.transferobject.reply.agile.RapidBoardReply;
import org.ag.timeline.presentation.transferobject.search.ProjectSearchParameter;

/**
 * Rapid Board related functionality.
 * 
 * @author Abhishek Gaurav
 */
public class RapidBoardAction extends SecureBaseAction {

	private long projectId = 0;

	private RapidBoardReply reply = null;

	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	public String secureExecute() throws Exception {

		String forward = SUCCESS;

		if (projectId > 0) {
			ProjectSearchParameter searchParameters = new ProjectSearchParameter();
			searchParameters.setProjectId(projectId);
			this.reply = service.getRapidBoardData(searchParameters);
		}

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
	 * Getter for reply.
	 * 
	 * @return the reply.
	 */
	public RapidBoardReply getReply() {
		return this.reply;
	}

	/**
	 * Setter for reply.
	 * 
	 * @param reply the reply to set.
	 */
	public void setReply(RapidBoardReply reply) {
		this.reply = reply;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RapidBoardAction [projectId=");
		builder.append(projectId);
		builder.append(", reply=");
		builder.append(reply);
		builder.append("]");
		return builder.toString();
	}

}