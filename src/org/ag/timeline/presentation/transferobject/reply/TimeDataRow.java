package org.ag.timeline.presentation.transferobject.reply;

import java.util.Date;

import org.ag.timeline.common.TextHelper;

/**
 * Denotes a single flattened time data row.
 * 
 * @author Abhishek Gaurav
 */
public class TimeDataRow {

	private long id = 0;

	private long projectId = 0;

	private String projectName = null;

	private String leadName = null;

	private long activityId = 0;

	private String activityName = null;

	private long taskId = 0;

	private String taskName = null;

	private long userId = 0;

	private String userFirstName = null;

	private String userLastName = null;

	private String userFullName = null;

	private String userAbbrvName = null;

	private long weekId = 0;

	private long year = 0;

	private long weekNum = 0;

	private Date startDate = null;

	private Date endDate = null;

	private double day_1_time = 0;

	private double day_2_time = 0;

	private double day_3_time = 0;

	private double day_4_time = 0;

	private double day_5_time = 0;

	private double day_6_time = 0;

	private double day_7_time = 0;

	private double weeklySum = 0;

	private boolean proxyRow = false;

	/**
	 * Getter for id.
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for id.
	 * 
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
	 * Getter for weekId.
	 * 
	 * @return the weekId
	 */
	public long getWeekId() {
		return weekId;
	}

	/**
	 * Setter for weekId.
	 * 
	 * @param weekId the weekId to set
	 */
	public void setWeekId(long weekId) {
		this.weekId = weekId;
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
	 * Getter for year.
	 * 
	 * @return the year
	 */
	public long getYear() {
		return year;
	}

	/**
	 * Setter for year.
	 * 
	 * @param year the year to set
	 */
	public void setYear(long year) {
		this.year = year;
	}

	/**
	 * Getter for weekNum.
	 * 
	 * @return the weekNum
	 */
	public long getWeekNum() {
		return weekNum;
	}

	/**
	 * Setter for weekNum.
	 * 
	 * @param weekNum the weekNum to set
	 */
	public void setWeekNum(long weekNum) {
		this.weekNum = weekNum;
	}

	/**
	 * Getter for startDate.
	 * 
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Setter for startDate.
	 * 
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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
	 * Getter for userFirstName.
	 * 
	 * @return the userFirstName
	 */
	public String getUserFirstName() {
		return userFirstName;
	}

	/**
	 * Setter for userFirstName.
	 * 
	 * @param userFirstName the userFirstName to set
	 */
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	/**
	 * Getter for userLastName.
	 * 
	 * @return the userLastName
	 */
	public String getUserLastName() {
		return userLastName;
	}

	/**
	 * Setter for userLastName.
	 * 
	 * @param userLastName the userLastName to set
	 */
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	/**
	 * Getter for day_1_time.
	 * 
	 * @return the day_1_time
	 */
	public double getDay_1_time() {
		return day_1_time;
	}

	/**
	 * Setter for day_1_time.
	 * 
	 * @param day_1_time the day_1_time to set
	 */
	public void setDay_1_time(double day_1_time) {
		this.day_1_time = day_1_time;
	}

	/**
	 * Getter for day_2_time.
	 * 
	 * @return the day_2_time
	 */
	public double getDay_2_time() {
		return day_2_time;
	}

	/**
	 * Setter for day_2_time.
	 * 
	 * @param day_2_time the day_2_time to set
	 */
	public void setDay_2_time(double day_2_time) {
		this.day_2_time = day_2_time;
	}

	/**
	 * Getter for day_3_time.
	 * 
	 * @return the day_3_time
	 */
	public double getDay_3_time() {
		return day_3_time;
	}

	/**
	 * Setter for day_3_time.
	 * 
	 * @param day_3_time the day_3_time to set
	 */
	public void setDay_3_time(double day_3_time) {
		this.day_3_time = day_3_time;
	}

	/**
	 * Getter for day_4_time.
	 * 
	 * @return the day_4_time
	 */
	public double getDay_4_time() {
		return day_4_time;
	}

	/**
	 * Setter for day_4_time.
	 * 
	 * @param day_4_time the day_4_time to set
	 */
	public void setDay_4_time(double day_4_time) {
		this.day_4_time = day_4_time;
	}

	/**
	 * Getter for day_5_time.
	 * 
	 * @return the day_5_time
	 */
	public double getDay_5_time() {
		return day_5_time;
	}

	/**
	 * Setter for day_5_time.
	 * 
	 * @param day_5_time the day_5_time to set
	 */
	public void setDay_5_time(double day_5_time) {
		this.day_5_time = day_5_time;
	}

	/**
	 * Getter for day_6_time.
	 * 
	 * @return the day_6_time
	 */
	public double getDay_6_time() {
		return day_6_time;
	}

	/**
	 * Setter for day_6_time.
	 * 
	 * @param day_6_time the day_6_time to set
	 */
	public void setDay_6_time(double day_6_time) {
		this.day_6_time = day_6_time;
	}

	/**
	 * Getter for day_7_time.
	 * 
	 * @return the day_7_time
	 */
	public double getDay_7_time() {
		return day_7_time;
	}

	/**
	 * Setter for day_7_time.
	 * 
	 * @param day_7_time the day_7_time to set
	 */
	public void setDay_7_time(double day_7_time) {
		this.day_7_time = day_7_time;
	}

	/**
	 * Getter for leadName.
	 * 
	 * @return the leadName
	 */
	public String getLeadName() {
		return leadName;
	}

	/**
	 * Setter for leadName.
	 * 
	 * @param leadName the leadName to set
	 */
	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

	/**
	 * Getter for endDate.
	 * 
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Setter for endDate.
	 * 
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Getter for userFullName.
	 * 
	 * @return the userFullName
	 */
	public String getUserFullName() {
		return userFullName;
	}

	/**
	 * Setter for userFullName.
	 * 
	 * @param userFullName the userFullName to set
	 */
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	/**
	 * Getter for userAbbrvName.
	 * 
	 * @return The userAbbrvName.
	 */
	public String getUserAbbrvName() {
		return userAbbrvName;
	}

	/**
	 * Setter for userAbbrvName.
	 * 
	 * @param userAbbrvName The userAbbrvName to set.
	 */
	public void setUserAbbrvName(String userAbbrvName) {
		this.userAbbrvName = userAbbrvName;
	}

	/**
	 * Getter for weeklySum.
	 * 
	 * @return the weeklySum
	 */
	public double getWeeklySum() {
		return weeklySum;
	}

	/**
	 * Getter for proxyRow.
	 * 
	 * @return The proxyRow.
	 */
	public boolean isProxyRow() {
		return proxyRow;
	}

	/**
	 * Setter for proxyRow.
	 * 
	 * @param proxyRow The proxyRow to set.
	 */
	public void setProxyRow(boolean proxyRow) {
		this.proxyRow = proxyRow;
	}

	/**
	 * Setter for weeklySum.
	 * 
	 * @param weeklySum the weeklySum to set
	 */
	public void setWeeklySum(double weeklySum) {
		this.weeklySum = weeklySum;
	}

	public String getFormattedStartDate() {
		return TextHelper.getDateAsString(this.startDate);
	}

	/**
	 * Getter for taskId.
	 * 
	 * @return the taskId.
	 */
	public long getTaskId() {
		return this.taskId;
	}

	/**
	 * Setter for taskId.
	 * 
	 * @param taskId the taskId to set.
	 */
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	/**
	 * Getter for taskName.
	 * 
	 * @return the taskName.
	 */
	public String getTaskName() {
		return this.taskName;
	}

	/**
	 * Setter for taskName.
	 * 
	 * @param taskName the taskName to set.
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}