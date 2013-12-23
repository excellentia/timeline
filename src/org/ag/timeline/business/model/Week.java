/**
 * 
 */
package org.ag.timeline.business.model;

import java.util.Date;

import org.ag.timeline.common.TextHelper;

/**
 * Represents one week in year.
 * 
 * @author Abhishek Gaurav
 */
public class Week extends AbstractModel {

	private long year = 0;

	private long weekNumber = 0;

	private Date startDate = null;

	private Date endDate = null;

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
	 * Getter for weekNumber.
	 * 
	 * @return the weekNumber
	 */
	public long getWeekNumber() {
		return weekNumber;
	}

	/**
	 * Setter for weekNumber.
	 * 
	 * @param weekNumber the weekNumber to set
	 */
	public void setWeekNumber(long weekNumber) {
		this.weekNumber = weekNumber;
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
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Week [id=");
		builder.append(super.getId());
		builder.append(", year=");
		builder.append(year);
		builder.append(", weekNumber=");
		builder.append(weekNumber);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public String getDescription() {
		return TextHelper.getDisplayWeek(this.getStartDate(), this.getEndDate());
	}

}
