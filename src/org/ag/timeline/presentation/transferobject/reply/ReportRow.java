package org.ag.timeline.presentation.transferobject.reply;


/**
 * Contains {@link TimeData} aggregated as report.
 * 
 * @author Abhishek Gaurav
 */
public class ReportRow {

	private long projectId = 0;
	private long rowId = 0;

	private String projectName = null;
	private String rowName = null;

	private double rowTime = 0;

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
	 * Getter for rowId.
	 * 
	 * @return the rowId
	 */
	public long getRowId() {
		return rowId;
	}

	/**
	 * Setter for rowId.
	 * 
	 * @param rowId the rowId to set
	 */
	public void setRowId(long rowId) {
		this.rowId = rowId;
	}

	/**
	 * Getter for projectName.
	 * 
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Setter for projectName.
	 * 
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Getter for rowName.
	 * 
	 * @return the rowName
	 */
	public String getRowName() {
		return rowName;
	}

	/**
	 * Setter for rowName.
	 * 
	 * @param rowName the rowName to set
	 */
	public void setRowName(String rowName) {
		this.rowName = rowName;
	}

	/**
	 * Getter for rowTime.
	 * 
	 * @return the rowTime
	 */
	public double getRowTime() {
		return rowTime;
	}

	/**
	 * Setter for rowTime.
	 * 
	 * @param rowTime the rowTime to set
	 */
	public void setRowTime(double rowTime) {
		this.rowTime = rowTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportRow [projectId=").append(projectId).append(", rowId=").append(rowId)
				.append(", projectName=").append(projectName).append(", rowName=").append(rowName).append(", rowTime=")
				.append(rowTime).append("]");
		return builder.toString();
	}

}
