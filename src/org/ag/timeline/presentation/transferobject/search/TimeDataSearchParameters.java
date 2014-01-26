/**
 * 
 */
package org.ag.timeline.presentation.transferobject.search;

import java.util.Date;

/**
 * Wraps the search parameters.
 * 
 * @author Abhishek Gaurav
 */
public class TimeDataSearchParameters extends BasicSearchParameter {

	private long timeDataId = 0;

	private UserSearchParameter user = null;

	private ActivitySearchParameter activity = null;

	private WeekSearchParameter startWeek = null;

	private WeekSearchParameter endWeek = null;

	private TaskSearchParameter task = null;

	/**
	 * Constructor.
	 */
	public TimeDataSearchParameters() {
		this.user = new UserSearchParameter();
		this.activity = new ActivitySearchParameter();
		this.startWeek = new WeekSearchParameter();
		this.endWeek = new WeekSearchParameter();
		this.task = new TaskSearchParameter();
	}

	/**
	 * Getter for timeDataId.
	 * 
	 * @return the timeDataId
	 */
	public long getTimeDataId() {
		return timeDataId;
	}

	/**
	 * Setter for timeDataId.
	 * 
	 * @param timeDataId the timeDataId to set
	 */
	public void setTimeDataId(long timeDataId) {
		this.timeDataId = timeDataId;
	}

	public long getUserId() {
		return this.user.getId();
	}

	public void setUserId(long userId) {
		this.user.setId(userId);
	}

	public long getProjectId() {
		return this.activity.getProjectId();
	}

	public void setProjectId(long projectId) {
		this.activity.setProjectId(projectId);
	}

	public long getActivityid() {
		return this.activity.getActivityId();
	}

	public void setActivityid(long activityId) {
		this.activity.setActivityId(activityId);
	}

	public Date getStartDate() {
		return this.startWeek.getStartDate();
	}

	public void setStartDate(Date startDate) {
		this.startWeek.setStartDate(startDate);
	}

	public Date getEndDate() {
		return this.endWeek.getEndDate();
	}

	public void setEndDate(Date endDate) {
		this.endWeek.setEndDate(endDate);
	}

	public long getStartWeekNum() {
		return this.startWeek.getWeekNumber();
	}

	public void setStartWeekNum(long weekNum) {
		this.startWeek.setWeekNumber(weekNum);
	}

	public long getStartYear() {
		return this.startWeek.getYear();
	}

	public void setStartYear(long year) {
		this.startWeek.setYear(year);
	}

	public long getStartWeekId() {
		return this.startWeek.getWeekId();
	}

	public void setStartWeekId(long weekId) {
		this.startWeek.setweekId(weekId);
	}

	public long getEndWeekNum() {
		return this.endWeek.getWeekNumber();
	}

	public void setEndWeekNum(long weekNum) {
		this.endWeek.setWeekNumber(weekNum);
	}

	public long getEndYear() {
		return this.endWeek.getYear();
	}

	public void setEndYear(long year) {
		this.endWeek.setYear(year);
	}

	public long getEndWeekId() {
		return this.endWeek.getWeekId();
	}

	public void setEndWeekId(long weekId) {
		this.endWeek.setweekId(weekId);
	}

	/**
	 * Getter for taskId.
	 * 
	 * @return the taskId.
	 */
	public long getTaskId() {
		return this.task.getTaskId();
	}

	/**
	 * Setter for taskId.
	 * 
	 * @param taskId the taskId to set.
	 */
	public void setTaskId(long taskId) {
		this.task.setTaskId(taskId);
	}

}
