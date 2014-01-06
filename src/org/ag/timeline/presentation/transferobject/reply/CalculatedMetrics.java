/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

import org.ag.timeline.application.exception.TimelineException;

/**
 * Calculate Project Metrics.
 * 
 * @author Abhishek Gaurav
 */
public class CalculatedMetrics {

	private double expectedToComplete = 0;

	private double schedulePerformanceIndex = 0;

	private double costPerformanceIndex = 0;

	private double defectRatio = 0;

	public CalculatedMetrics(BasicProjectMetrics basicMetrics, ProjectEstimateData estimateData)
			throws TimelineException {
		if ((basicMetrics != null) && (estimateData != null)) {

			double bac = estimateData.getBudgetAtCompletion();

			double plannedValue = basicMetrics.getPlannedValue();
			double earnedValue = basicMetrics.getEarnedValue();
			double actualCost = basicMetrics.getActualCost();
			double actualsToDate = basicMetrics.getActualsToDate();
			double softwareProgrammingEffort = basicMetrics.getSoftwareProgrammingEffort();
			long defects = basicMetrics.getDefects();

			// calculate the values
			this.expectedToComplete = bac - earnedValue;

			this.costPerformanceIndex = earnedValue / actualCost;
			this.schedulePerformanceIndex = earnedValue / plannedValue;

			this.defectRatio = defects / softwareProgrammingEffort;

		} else {
			throw new TimelineException();
		}
	}

	/**
	 * Getter for expectedToComplete.
	 * 
	 * @return the expectedToComplete.
	 */
	public double getExpectedToComplete() {
		return this.expectedToComplete;
	}

	/**
	 * Getter for schedulePerformanceIndex.
	 * 
	 * @return the schedulePerformanceIndex.
	 */
	public double getSchedulePerformanceIndex() {
		return this.schedulePerformanceIndex;
	}

	/**
	 * Getter for costPerformanceIndex.
	 * 
	 * @return the costPerformanceIndex.
	 */
	public double getCostPerformanceIndex() {
		return this.costPerformanceIndex;
	}

	/**
	 * Getter for defectRatio.
	 * 
	 * @return the defectRatio.
	 */
	public double getDefectRatio() {
		return this.defectRatio;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CalculatedMetrics [expectedToComplete=");
		builder.append(expectedToComplete);
		builder.append(", schedulePerformanceIndex=");
		builder.append(schedulePerformanceIndex);
		builder.append(", costPerformanceIndex=");
		builder.append(costPerformanceIndex);
		builder.append(", defectRatio=");
		builder.append(defectRatio);
		builder.append("]");
		return builder.toString();
	}

}
