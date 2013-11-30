package org.ag.timeline.presentation.action;

/**
 * Detailed report related functionality.
 * 
 * @author Abhishek Gaurav
 */
public class ReportDetailAction extends SecureBaseAction {

	/**
	 * Unique database id for the project for which the details report is to be
	 * created.
	 */
	private long projectId;

	/**
	 * Getter for projectId.
	 * 
	 * @return the projectId.
	 */
	public long getProjectId() {
		return this.projectId;
	}

	/**
	 * Setter for projectId.
	 * 
	 * @param projectId the projectId to set.
	 */
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	@Override
	public String secureExecute() throws Exception {
		
		
		
		return SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportDetailAction [projectId=");
		builder.append(projectId);
		builder.append("]");
		return builder.toString();
	}

}