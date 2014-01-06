package org.ag.timeline.presentation.transferobject.search;

/**
 * Wrapper for Project Metrics search.
 * 
 * @author Abhishek Gaurav
 */
public class ProjectDetailMetricsSearchParameters extends BasicSearchParameter {

	/**
	 * Serial Version U-Id.
	 */
	private static final long serialVersionUID = 4948625048298703905L;

	private long userDbId = 0;

	private long projectDbId = 0;

	private boolean searchAllData = false;

	/**
	 * Getter for userDbId.
	 * 
	 * @return the userDbId
	 */
	public long getUserDbId() {
		return userDbId;
	}

	/**
	 * Setter for userDbId.
	 * 
	 * @param userDbId the userDbId to set
	 */
	public void setUserDbId(long userDbId) {
		this.userDbId = userDbId;
	}

	/**
	 * Getter for projectDbId.
	 * 
	 * @return the projectDbId
	 */
	public long getProjectDbId() {
		return projectDbId;
	}

	/**
	 * Setter for projectDbId.
	 * 
	 * @param projectDbId the projectDbId to set
	 */
	public void setProjectDbId(long projectDbId) {
		this.projectDbId = projectDbId;
	}

	/**
	 * Getter for searchAllData.
	 * 
	 * @return the searchAllData
	 */
	public boolean isSearchAllData() {
		return searchAllData;
	}

	/**
	 * Setter for searchAllData.
	 * 
	 * @param searchAllData the searchAllData to set
	 */
	public void setSearchAllData(boolean searchAllData) {
		this.searchAllData = searchAllData;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportSearchParameters [userDbId=").append(userDbId).append(", projectDbId=")
				.append(projectDbId).append(", searchAllData=").append(searchAllData).append("]");
		return builder.toString();
	}
}
