package org.ag.timeline.presentation.transferobject.reply.metrics;

import org.ag.timeline.application.exception.TimelineException;

/**
 * Project Level Metrics data.
 * 
 * @author Abhishek Gaurav
 */
public class ProjectLevelMetrics {

	private ProjectEstimateData basicData = null;

	private BasicProjectMetrics cumulativeMetrics = null;

	private CalculatedMetrics calculatedMetrics = null;

	/**
	 * Getter for basicData.
	 * 
	 * @return the basicData.
	 */
	public ProjectEstimateData getBasicData() {
		return this.basicData;
	}

	/**
	 * Setter for basicData.
	 * 
	 * @param basicData the basicData to set.
	 */
	public void setBasicData(ProjectEstimateData basicData) {
		this.basicData = basicData;
	}

	/**
	 * Getter for cumulativeMetrics.
	 * 
	 * @return the cumulativeMetrics.
	 */
	public BasicProjectMetrics getCumulativeMetrics() {
		return this.cumulativeMetrics;
	}

	/**
	 * Setter for cumulativeMetrics.
	 * 
	 * @param cumulativeMetrics the cumulativeMetrics to set.
	 */
	public void setCumulativeMetrics(BasicProjectMetrics cumulativeMetrics) {
		this.cumulativeMetrics = cumulativeMetrics;
	}

	/**
	 * Getter for calculatedMetrics.
	 * 
	 * @return the calculatedMetrics.
	 */
	public CalculatedMetrics getCalculatedMetrics() {
		return this.calculatedMetrics;
	}
	
	/**
	 * Business method. Populates the calculated metrics. 
	 */
	public void populateCalculatedMetrics() {
		try {
			this.calculatedMetrics = new CalculatedMetrics(cumulativeMetrics, basicData);
		} catch (TimelineException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectLevelMetrics [basicData=");
		builder.append(basicData);
		builder.append(", cumulativeMetrics=");
		builder.append(cumulativeMetrics);
		builder.append("]");
		return builder.toString();
	}

}
