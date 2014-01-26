package org.ag.timeline.presentation.transferobject.reply.report;

import org.ag.timeline.business.model.TimeData;

/**
 * Contains {@link TimeData} grouped as report.
 * 
 * @author Abhishek Gaurav
 */
public class ReportData {

	private long projectId = 0;
	private String projectName = null;

	private long userId = 0;
	private String userName = null;

	private long activityId = 0;
	private String activityName = null;

	private double activityTime = 0.0d;

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
	 * Getter for userName.
	 * 
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Setter for userName.
	 * 
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * Getter for activityName.
	 * 
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * Setter for activityName.
	 * 
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
	 * Getter for activityTime.
	 * 
	 * @return the activityTime
	 */
	public double getActivityTime() {
		return activityTime;
	}

	/**
	 * Setter for activityTime.
	 * 
	 * @param activityTime the activityTime to set
	 */
	public void setActivityTime(double activityTime) {
		this.activityTime = activityTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportData [projectId=").append(projectId).append(", projectName=").append(projectName)
				.append(", userId=").append(userId).append(", userName=").append(userName).append(", activityId=")
				.append(activityId).append(", activityName=").append(activityName).append(", activityTime=")
				.append(activityTime).append("]");
		return builder.toString();
	}

}
