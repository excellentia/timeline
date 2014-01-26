/**
 * 
 */
package org.ag.timeline.business.model.metrics;

import java.math.BigDecimal;

import org.ag.timeline.business.model.AbstractModel;
import org.ag.timeline.business.model.Project;
import org.ag.timeline.business.model.Week;
import org.ag.timeline.common.TimelineConstants;

/**
 * @author Abhishek Gaurav
 */
public class ProjectMetrics extends AbstractModel {

	/**
	 * Parent project.
	 */
	private Project project = null;

	/**
	 * Week applicable.
	 */
	private Week week = null;

	private BigDecimal plannedValue = null;

	private BigDecimal earnedValue = null;

	private BigDecimal actualCost = null;

	private BigDecimal actualsToDate = null;

	private BigDecimal softwareProgrammingEffort = null;

	private long defects = 0;

	/**
	 * Getter for project.
	 * 
	 * @return the project.
	 */
	public Project getProject() {
		return this.project;
	}

	/**
	 * Setter for project.
	 * 
	 * @param project the project to set.
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * Getter for week.
	 * 
	 * @return the week.
	 */
	public Week getWeek() {
		return this.week;
	}

	/**
	 * Setter for week.
	 * 
	 * @param week the week to set.
	 */
	public void setWeek(Week week) {
		this.week = week;
	}

	/**
	 * Getter for plannedValue.
	 * 
	 * @return the plannedValue.
	 */
	public BigDecimal getPlannedValue() {
		return this.plannedValue;
	}

	/**
	 * Setter for plannedValue.
	 * 
	 * @param plannedValue the plannedValue to set.
	 */
	public void setPlannedValue(BigDecimal plannedValue) {
		this.plannedValue = plannedValue;
	}

	/**
	 * Getter for earnedValue.
	 * 
	 * @return the earnedValue.
	 */
	public BigDecimal getEarnedValue() {
		return this.earnedValue;
	}

	/**
	 * Setter for earnedValue.
	 * 
	 * @param earnedValue the earnedValue to set.
	 */
	public void setEarnedValue(BigDecimal earnedValue) {
		this.earnedValue = earnedValue;
	}

	/**
	 * Getter for actualCost.
	 * 
	 * @return the actualCost.
	 */
	public BigDecimal getActualCost() {
		return this.actualCost;
	}

	/**
	 * Setter for actualCost.
	 * 
	 * @param actualCost the actualCost to set.
	 */
	public void setActualCost(BigDecimal actualCost) {
		this.actualCost = actualCost;
	}

	/**
	 * Getter for actualsToDate.
	 * 
	 * @return the actualsToDate.
	 */
	public BigDecimal getActualsToDate() {
		return this.actualsToDate;
	}

	/**
	 * Setter for actualsToDate.
	 * 
	 * @param actualsToDate the actualsToDate to set.
	 */
	public void setActualsToDate(BigDecimal actualsToDate) {
		this.actualsToDate = actualsToDate;
	}

	/**
	 * Getter for softwareProgrammingEffort.
	 * 
	 * @return the softwareProgrammingEffort.
	 */
	public BigDecimal getSoftwareProgrammingEffort() {
		return this.softwareProgrammingEffort;
	}

	/**
	 * Setter for softwareProgrammingEffort.
	 * 
	 * @param softwareProgrammingEffort the softwareProgrammingEffort to set.
	 */
	public void setSoftwareProgrammingEffort(BigDecimal softwareProgrammingEffort) {
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

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectMetrics [id=");
		builder.append(super.getId());
		builder.append(", project=");
		builder.append(project.getDescription());
		builder.append(", Week =");
		builder.append(week.getDescription());
		builder.append(", PV =");
		builder.append(plannedValue);
		builder.append(", EV =");
		builder.append(earnedValue);
		builder.append(", AC =");
		builder.append(actualCost);
		builder.append(", ATD =");
		builder.append(actualsToDate);
		builder.append(", SPE =");
		builder.append(softwareProgrammingEffort);
		builder.append(", Defects =");
		builder.append(defects);
		builder.append("]");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see org.ag.timeline.business.model.AbstractModel#getDescription()
	 */
	@Override
	public String getDescription() {
		StringBuilder builder = new StringBuilder(this.project.getDescription()).append(TimelineConstants.COMMA).append(week.getDescription());
		return builder.toString();
	}

}
