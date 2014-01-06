package org.ag.timeline.presentation.transferobject.reply;

import java.util.Date;


/**
 * Project Detail Metrics data.
 * 
 * @author Abhishek Gaurav
 */
public class ProjectDetailMetrics {

	private long metricId = 0;

	private BasicProjectMetrics weeklyMetrics = null;

	private String metricWeek = null;

	private Date lastSaved = null;

	/**
	 * Getter for metricId.
	 * 
	 * @return the metricId.
	 */
	public long getMetricId() {
		return this.metricId;
	}

	/**
	 * Setter for metricId.
	 * 
	 * @param metricId the metricId to set.
	 */
	public void setMetricId(long metricId) {
		this.metricId = metricId;
	}

	/**
	 * Getter for weeklyMetrics.
	 * 
	 * @return the weeklyMetrics.
	 */
	public BasicProjectMetrics getWeeklyMetrics() {
		return this.weeklyMetrics;
	}

	/**
	 * Setter for weeklyMetrics.
	 * 
	 * @param weeklyMetrics the weeklyMetrics to set.
	 */
	public void setWeeklyMetrics(BasicProjectMetrics weeklyMetrics) {
		this.weeklyMetrics = weeklyMetrics;
	}

	/**
	 * Getter for metricWeek.
	 * 
	 * @return the metricWeek.
	 */
	public String getMetricWeek() {
		return this.metricWeek;
	}

	/**
	 * Setter for metricWeek.
	 * 
	 * @param metricWeek the metricWeek to set.
	 */
	public void setMetricWeek(String week) {
		this.metricWeek = week;
	}

	/**
	 * Getter for lastSaved.
	 * 
	 * @return the lastSaved.
	 */
	public Date getLastSaved() {
		return this.lastSaved;
	}

	/**
	 * Setter for lastSaved.
	 * 
	 * @param lastSaved the lastSaved to set.
	 */
	public void setLastSaved(Date lastSaved) {
		this.lastSaved = lastSaved;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectDetailMetrics [metricId=");
		builder.append(metricId);
		builder.append(", weeklyMetrics=");
		builder.append(weeklyMetrics);
		builder.append(", metricWeek=");
		builder.append(metricWeek);
		builder.append(", lastSaved=");
		builder.append(lastSaved);
		builder.append("]");
		return builder.toString();
	}
}
