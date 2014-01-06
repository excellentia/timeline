/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

/**
 * Basic Project Metrics.
 * 
 * @author Abhishek Gaurav
 */
public class BasicProjectMetrics {

	private double plannedValue = 0;

	private double earnedValue = 0;

	private double actualCost = 0;

	private double actualsToDate = 0;

	private double softwareProgrammingEffort = 0;

	private long defects = 0;

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

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BasicProjectMetrics [plannedValue=");
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
		builder.append("]");
		return builder.toString();
	}

}
