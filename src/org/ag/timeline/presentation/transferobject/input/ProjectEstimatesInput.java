package org.ag.timeline.presentation.transferobject.input;

import org.ag.timeline.presentation.transferobject.reply.ProjectEstimateData;

public class ProjectEstimatesInput extends AbstractTimelineInput {

	/**
	 * Serial Version U-Id.
	 */
	private static final long serialVersionUID = 5367634177449957185L;

	private ProjectEstimateData estimateData = null;

	/**
	 * Getter for estimateData.
	 * 
	 * @return the estimateData.
	 */
	public ProjectEstimateData getEstimateData() {
		return this.estimateData;
	}

	/**
	 * Setter for estimateData.
	 * 
	 * @param estimateData the estimateData to set.
	 */
	public void setEstimateData(ProjectEstimateData estimateData) {
		this.estimateData = estimateData;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectEstimatesInput [estimateData=");
		builder.append(estimateData);
		builder.append("]");
		return builder.toString();
	}

}