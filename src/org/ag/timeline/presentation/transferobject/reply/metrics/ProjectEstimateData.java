package org.ag.timeline.presentation.transferobject.reply.metrics;

import java.util.Date;

import org.ag.timeline.presentation.transferobject.reply.ProjectData;

/**
 * Project Estimate data.
 * 
 * @author Abhishek Gaurav
 */
public class ProjectEstimateData {

	private ProjectData projectData = null;

	private double budgetAtCompletion = 0;

	private Date startDate = null;

	private Date endDate = null;

	private String lastSubmittedPeriod = null;

	/**
	 * Getter for projectData.
	 * 
	 * @return the projectData.
	 */
	public ProjectData getProjectData() {
		return this.projectData;
	}

	/**
	 * Setter for projectData.
	 * 
	 * @param projectData the projectData to set.
	 */
	public void setProjectData(ProjectData projectData) {
		this.projectData = projectData;
	}

	/**
	 * Getter for budgetAtCompletion.
	 * 
	 * @return the budgetAtCompletion.
	 */
	public double getBudgetAtCompletion() {
		return this.budgetAtCompletion;
	}

	/**
	 * Setter for budgetAtCompletion.
	 * 
	 * @param budgetAtCompletion the budgetAtCompletion to set.
	 */
	public void setBudgetAtCompletion(double budgetAtCompletion) {
		this.budgetAtCompletion = budgetAtCompletion;
	}

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
	 * Getter for lastSubmittedPeriod.
	 * 
	 * @return the lastSubmittedPeriod.
	 */
	public String getLastSubmittedPeriod() {
		return this.lastSubmittedPeriod;
	}

	/**
	 * Setter for lastSubmittedPeriod.
	 * 
	 * @param lastSubmittedPeriod the lastSubmittedPeriod to set.
	 */
	public void setLastSubmittedPeriod(String lastSubmittedPeriod) {
		this.lastSubmittedPeriod = lastSubmittedPeriod;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectEstimateData [projectData=");
		builder.append(projectData);
		builder.append(", budgetAtCompletion=");
		builder.append(budgetAtCompletion);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", lastSubmittedPeriod=");
		builder.append(lastSubmittedPeriod);
		builder.append("]");
		return builder.toString();
	}

}
