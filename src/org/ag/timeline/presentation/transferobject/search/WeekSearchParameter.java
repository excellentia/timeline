/**
 * 
 */
package org.ag.timeline.presentation.transferobject.search;

import java.util.Date;

import org.ag.timeline.business.model.Week;

public class WeekSearchParameter extends BasicSearchParameter {

	private Week week = null;

	/**
	 * Constructor.
	 */
	public WeekSearchParameter() {
		this.week = new Week();
	}

	/**
	 * Getter for id.
	 * 
	 * @return the id
	 */
	public long getWeekId() {
		return this.week.getId();
	}

	/**
	 * Setter for id.
	 * 
	 * @param id the id to set
	 */
	public void setweekId(long id) {
		this.week.setId(id);
	}

	/**
	 * Getter for year.
	 * 
	 * @return the year
	 */
	public long getYear() {
		return this.week.getYear();
	}

	/**
	 * Setter for year.
	 * 
	 * @param year the year to set
	 */
	public void setYear(long year) {
		this.week.setYear(year);
	}

	/**
	 * Getter for weekNumber.
	 * 
	 * @return the weekNumber
	 */
	public long getWeekNumber() {
		return this.week.getWeekNumber();
	}

	/**
	 * Setter for weekNumber.
	 * 
	 * @param weekNumber the weekNumber to set
	 */
	public void setWeekNumber(long weekNumber) {
		this.week.setWeekNumber(weekNumber);
	}

	/**
	 * Getter for startDate.
	 * 
	 * @return the startDate
	 */
	public Date getStartDate() {
		return this.week.getStartDate();
	}

	/**
	 * Setter for startDate.
	 * 
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.week.setStartDate(startDate);
	}

	/**
	 * Getter for endDate.
	 * 
	 * @return the endDate
	 */
	public Date getEndDate() {
		return this.week.getEndDate();
	}

	/**
	 * Setter for endDate.
	 * 
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.week.setEndDate(endDate);
	}
}
