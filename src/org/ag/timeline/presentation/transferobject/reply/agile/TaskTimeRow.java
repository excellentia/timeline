/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply.agile;

import java.util.Date;

import org.ag.timeline.common.TextHelper;
import org.ag.timeline.common.TimelineConstants;

/**
 * @author Abhishek Gaurav
 */
public class TaskTimeRow implements Comparable<TaskTimeRow> {

	private Date startDate = null;

	private Date endDate = null;

	private double time = 0d;

	private String user = null;

	/**
	 * Getter for startDate.
	 * 
	 * @return the startDate.
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * Setter for startDate.
	 * 
	 * @param startDate the startDate to set.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Getter for endDate.
	 * 
	 * @return the endDate.
	 */
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * Setter for endDate.
	 * 
	 * @param endDate the endDate to set.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Getter for time.
	 * 
	 * @return the time.
	 */
	public double getTime() {
		return this.time;
	}

	/**
	 * Setter for time.
	 * 
	 * @param time the time to set.
	 */
	public void setTime(double time) {
		this.time = time;
	}

	/**
	 * Getter for user.
	 * 
	 * @return the user.
	 */
	public String getUser() {
		return this.user;
	}

	/**
	 * Setter for user.
	 * 
	 * @param user the user to set.
	 */
	public void setUser(String user) {
		this.user = user;
	}

	public String getDisplayWeek() {
		String val = TimelineConstants.EMPTY;

		if ((this.startDate != null) && (this.endDate != null)) {
			val = TextHelper.getDisplayWeek(this.startDate, this.endDate);
		}

		return val;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskTimeRow [startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", time=");
		builder.append(time);
		builder.append(", user=");
		builder.append(user);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(TaskTimeRow other) {

		int retVal = 0;

		if (other == null) {
			retVal = 1;
		} else {

			if (this.startDate == null) {
				retVal = -1;
			} else if (other.startDate == null) {
				retVal = 1;
			} else if (this.startDate.before(other.getStartDate())) {
				retVal = 1;
			} else {
				retVal = -1;
			}

		}

		return retVal;
	}
}
