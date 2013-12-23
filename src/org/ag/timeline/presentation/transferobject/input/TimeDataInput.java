/**
 * 
 */
package org.ag.timeline.presentation.transferobject.input;

import java.util.Date;

/**
 * Encapsulates user time data.
 * 
 * @author Abhishek Gaurav
 */
public class TimeDataInput extends AbstractTimelineInput {

	private long id = 0;

	private long userId = 0;

	private long activityId = 0;

	// private long projectId = 0;
	private long proxiedUserDbId = 0;

	private Date date = null;

	private double day_1_time = 0;

	private double day_2_time = 0;

	private double day_3_time = 0;

	private double day_4_time = 0;

	private double day_5_time = 0;

	private double day_6_time = 0;

	private double day_7_time = 0;

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

	// /**
	// * Getter for projectId.
	// *
	// * @return the projectId
	// */
	// public long getProjectId() {
	// return projectId;
	// }
	//
	// /**
	// * Setter for projectId.
	// *
	// * @param projectId the projectId to set
	// */
	// public void setProjectId(long projectId) {
	// this.projectId = projectId;
	// }

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
	 * Getter for date.
	 * 
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Setter for date.
	 * 
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Getter for proxiedUserDbId.
	 * 
	 * @return The proxiedUserDbId.
	 */
	public long getProxiedUserDbId() {
		return proxiedUserDbId;
	}

	/**
	 * Setter for proxiedUserDbId.
	 * 
	 * @param proxiedUserDbId The proxiedUserDbId to set.
	 */
	public void setProxiedUserDbId(long proxiedUserDbId) {
		this.proxiedUserDbId = proxiedUserDbId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TimeDataInput [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", proxiedUserDbId=");
		builder.append(proxiedUserDbId);
		builder.append(", activityId=");
		builder.append(activityId);
		// builder.append(", projectId=");
		// builder.append(projectId);
		builder.append(", date=");
		builder.append(date);
		builder.append(", day_1_time=");
		builder.append(day_1_time);
		builder.append(", day_2_time=");
		builder.append(day_2_time);
		builder.append(", day_3_time=");
		builder.append(day_3_time);
		builder.append(", day_4_time=");
		builder.append(day_4_time);
		builder.append(", day_5_time=");
		builder.append(day_5_time);
		builder.append(", day_6_time=");
		builder.append(day_6_time);
		builder.append(", day_7_time=");
		builder.append(day_7_time);
		builder.append("]");
		return builder.toString();
	}

}
