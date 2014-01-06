package org.ag.timeline.presentation.transferobject.input;

import java.util.Date;

/**
 * Wrapper for project metrics entered by user.
 * 
 * @author Abhishek Gaurav
 */
public class ProjectMetricsInput extends AbstractTimelineInput {

	/**
	 * Serial Version U-Id.
	 */
	private static final long serialVersionUID = 1314966721737178897L;

	private long projectId = 0;

	private long metricId = 0;

	private double plannedValue = 0;

	private double earnedValue = 0;

	private double actualCost = 0;

	private double actualsToDate = 0;

	private double softwareProgrammingEffort = 0;

	private long defects = 0;

	private Date date = null;

	/**
	 * Getter for projectId.
	 * 
	 * @return the projectId.
	 */
	public long getProjectId() {
		return this.projectId;
	}

	/**
	 * Setter for projectId.
	 * 
	 * @param projectId the projectId to set.
	 */
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	/**
	 * Getter for plannedValue.
	 * 
	 * @return the plannedValue.
	 */
	public double getPlannedValue() {
		return this.plannedValue;
	}

	/**
	 * Setter for plannedValue.
	 * 
	 * @param plannedValue the plannedValue to set.
	 */
	public void setPlannedValue(double plannedValue) {
		this.plannedValue = plannedValue;
	}

	/**
	 * Getter for earnedValue.
	 * 
	 * @return the earnedValue.
	 */
	public double getEarnedValue() {
		return this.earnedValue;
	}

	/**
	 * Setter for earnedValue.
	 * 
	 * @param earnedValue the earnedValue to set.
	 */
	public void setEarnedValue(double earnedValue) {
		this.earnedValue = earnedValue;
	}

	/**
	 * Getter for actualCost.
	 * 
	 * @return the actualCost.
	 */
	public double getActualCost() {
		return this.actualCost;
	}

	/**
	 * Setter for actualCost.
	 * 
	 * @param actualCost the actualCost to set.
	 */
	public void setActualCost(double actualCost) {
		this.actualCost = actualCost;
	}

	/**
	 * Getter for actualsToDate.
	 * 
	 * @return the actualsToDate.
	 */
	public double getActualsToDate() {
		return this.actualsToDate;
	}

	/**
	 * Setter for actualsToDate.
	 * 
	 * @param actualsToDate the actualsToDate to set.
	 */
	public void setActualsToDate(double actualsToDate) {
		this.actualsToDate = actualsToDate;
	}

	/**
	 * Getter for softwareProgrammingEffort.
	 * 
	 * @return the softwareProgrammingEffort.
	 */
	public double getSoftwareProgrammingEffort() {
		return this.softwareProgrammingEffort;
	}

	/**
	 * Setter for softwareProgrammingEffort.
	 * 
	 * @param softwareProgrammingEffort the softwareProgrammingEffort to set.
	 */
	public void setSoftwareProgrammingEffort(double softwareProgrammingEffort) {
		this.softwareProgrammingEffort = softwareProgrammingEffort;
	}

	/**
	 * Getter for defects.
	 * 
	 * @return the defects.
	 */
	public long getDefects() {
		return this.defects;
	}

	/**
	 * Setter for defects.
	 * 
	 * @param defects the defects to set.
	 */
	public void setDefects(long defects) {
		this.defects = defects;
	}

	/**
	 * Getter for date.
	 * 
	 * @return the date.
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * Setter for date.
	 * 
	 * @param date the date to set.
	 */
	public void setDate(Date date) {
		this.date = date;
	}

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

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectMetricsInput [projectId=");
		builder.append(projectId);
		builder.append(", metricId=");
		builder.append(metricId);
		builder.append(", plannedValue=");
		builder.append(plannedValue);
		builder.append(", earnedValue=");
		builder.append(earnedValue);
		builder.append(", actualCost=");
		builder.append(actualCost);
		builder.append(", actualsToDate=");
		builder.append(actualsToDate);
		builder.append(", softwareProgrammingEffort=");
		builder.append(softwareProgrammingEffort);
		builder.append(", defects=");
		builder.append(defects);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
	}

}
