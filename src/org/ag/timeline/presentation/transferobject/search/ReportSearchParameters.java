package org.ag.timeline.presentation.transferobject.search;

/**
 * Wrapper for report search.
 * 
 * @author Abhishek Gaurav
 */
public class ReportSearchParameters {

	private long userDbId = 0;
	private long projectDbId = 0;
	private long activityDbId = 0;
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
	 * Getter for activityDbId.
	 * 
	 * @return the activityDb
	 */
	public long getActivityDbId() {
		return activityDbId;
	}

	/**
	 * Setter for activityDbId.
	 * 
	 * @param activityDbId the activityDbId to set
	 */
	public void setActivityDbId(long activityDbId) {
		this.activityDbId = activityDbId;
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
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportSearchParameters [userDbId=").append(userDbId).append(", projectDbId=")
				.append(projectDbId).append(", activityDbId=").append(activityDbId).append(", searchAllData=")
				.append(searchAllData).append("]");
		return builder.toString();
	}
}
