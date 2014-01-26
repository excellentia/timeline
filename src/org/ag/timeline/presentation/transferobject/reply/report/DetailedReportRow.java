package org.ag.timeline.presentation.transferobject.reply.report;

import java.util.Date;

/**
 * Contains TimeData aggregated as detailed report.
 * 
 * @author Abhishek Gaurav
 */
public class DetailedReportRow {

	private long year = 0;

	private long weekNumber = 0;

	private Date weekStartDate = null;

	private Date weekEndDate = null;

	private double weeklySum = 0.0d;

	private long activityId = 0;

	private String activityName = null;

	/**
	 * Getter for year.
	 * 
	 * @return the year.
	 */
	public long getYear() {
		return this.year;
	}

	/**
	 * Setter for year.
	 * 
	 * @param year the year to set.
	 */
	public void setYear(long year) {
		this.year = year;
	}

	/**
	 * Getter for weekId.
	 * 
	 * @return the weekId.
	 */
	public long getWeekNumber() {
		return this.weekNumber;
	}

	/**
	 * Setter for weekId.
	 * 
	 * @param weekId the weekId to set.
	 */
	public void setWeekNumber(long weekId) {
		this.weekNumber = weekId;
	}

	/**
	 * Getter for weekStartDate.
	 * 
	 * @return the weekStartDate.
	 */
	public Date getWeekStartDate() {
		return this.weekStartDate;
	}

	/**
	 * Setter for weekStartDate.
	 * 
	 * @param weekStartDate the weekStartDate to set.
	 */
	public void setWeekStartDate(Date weekStartDate) {
		this.weekStartDate = weekStartDate;
	}

	/**
	 * Getter for weekEndDate.
	 * 
	 * @return the weekEndDate.
	 */
	public Date getWeekEndDate() {
		return this.weekEndDate;
	}

	/**
	 * Setter for weekEndDate.
	 * 
	 * @param weekEndDate the weekEndDate to set.
	 */
	public void setWeekEndDate(Date weekEndDate) {
		this.weekEndDate = weekEndDate;
	}

	/**
	 * Getter for weeklySum.
	 * 
	 * @return the weeklySum.
	 */
	public double getWeeklySum() {
		return this.weeklySum;
	}

	/**
	 * Setter for weeklySum.
	 * 
	 * @param weeklySum the weeklySum to set.
	 */
	public void setWeeklySum(double weeklySum) {
		this.weeklySum = weeklySum;
	}

	/**
	 * Getter for activityId.
	 * 
	 * @return the activityId.
	 */
	public long getActivityId() {
		return this.activityId;
	}

	/**
	 * Setter for activityId.
	 * 
	 * @param activityId the activityId to set.
	 */
	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	/**
	 * Getter for activityName.
	 * 
	 * @return the activityName.
	 */
	public String getActivityName() {
		return this.activityName;
	}

	/**
	 * Setter for activityName.
	 * 
	 * @param activityName the activityName to set.
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DetailedReportRow [year=");
		builder.append(year);
		builder.append(", weekNumber=");
		builder.append(weekNumber);
		builder.append(", weekStartDate=");
		builder.append(weekStartDate);
		builder.append(", weekEndDate=");
		builder.append(weekEndDate);
		builder.append(", weeklySum=");
		builder.append(weeklySum);
		builder.append(", activityId=");
		builder.append(activityId);
		builder.append(", activityName=");
		builder.append(activityName);
		builder.append("]");
		return builder.toString();
	}

}
