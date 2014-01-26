package org.ag.timeline.presentation.transferobject.reply;

import java.util.ArrayList;
import java.util.List;

public class ProjectDetailMetricsReply extends BusinessReply {

	private ProjectEstimateData basicData = null;

	private List<ProjectDetailMetrics> weeklyMetrics = null;

	/**
	 * Constructor.
	 */
	public ProjectDetailMetricsReply() {
		this.weeklyMetrics = new ArrayList<ProjectDetailMetrics>();
	}

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
	 * Getter for weeklyMetrics.
	 * 
	 * @return the weeklyMetrics.
	 */
	public List<ProjectDetailMetrics> getWeeklyMetrics() {
		return this.weeklyMetrics;
	}

	public void addProjectDetailMetrics(ProjectDetailMetrics metrics) {
		this.weeklyMetrics.add(metrics);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectDetailMetricsReply [basicData=");
		builder.append(basicData);
		builder.append(", weeklyMetrics=");
		builder.append(weeklyMetrics);
		builder.append("]");
		return builder.toString();
	}
	
	/*
	 * public void addProjectDetailMetrics(ProjectMetrics row) {

		if (row != null) {

			Project project = row.getProject();
			final long key = project.getId();

			// handle the project base data
			{
				// create project base data
				ProjectData baseData = new ProjectData();
				baseData.setCode(key);
				baseData.setValue(project.getName());
				baseData.setLeadName(TextHelper.trimToEmpty(project.getLeadName()));
				
				
				
				ProjectDetailMetrics detailMetrics = new ProjectDetailMetrics();
				
				detailMetrics.set
				baseData.setBudgetAtCompletion(TextHelper.getScaledDouble(project.getBudgetAtCompletion()));
				baseData.setStartDate(TextHelper.getDateAsString(project.getStartDate()));
				baseData.setEndDate(TextHelper.getDateAsString(project.getEndDate()));

				// update the project map
				projectBaseDataMap.put(key, baseData);

			}

			// handle the project metrics data
			{
				ProjectMetricsReportRow reportRow = new ProjectMetricsReportRow();
				{
					reportRow.setProjectId(project.getId());
					reportRow.setProjectName(project.getName());
					reportRow.setLeadName(project.getLeadName());

					Week week = row.getWeek();
					reportRow.setWeekId(week.getId());
					reportRow.setWeekNum(week.getWeekNumber());
					reportRow.setYear(week.getYear());
					reportRow.setDisplayWeek(TextHelper.getDisplayWeek(week.getStartDate(), week.getEndDate()));

					reportRow.setPlannedValue(TextHelper.getScaledDouble(row.getPlannedValue()));
					reportRow.setEarnedValue(TextHelper.getScaledDouble(row.getEarnedValue()));
					reportRow.setActualCost(TextHelper.getScaledDouble(row.getActualCost()));
					reportRow.setActualsToDate(TextHelper.getScaledDouble(row.getActualsToDate()));
					reportRow.setExpectedToComplete(TextHelper.getScaledDouble(row.getExpectedToComplete()));
					reportRow.setSoftwareProgrammingEffort(TextHelper.getScaledDouble(row
							.getSoftwareProgrammingEffort()));

					reportRow.setDefectRatio(TextHelper.getScaledDouble(row.getDefectRatio()));
					reportRow.setDefects(row.getDefects());

					reportRow
							.setSchedulePerformanceIndex(TextHelper.getScaledDouble(row.getSchedulePerformanceIndex()));
					reportRow.setCostPerformanceIndex(TextHelper.getScaledDouble(row.getCostPerformanceIndex()));
				}

				List<ProjectMetricsReportRow> metricsList = projectMetricsMap.get(key);

				if (metricsList == null) {
					metricsList = new ArrayList<ProjectMetricsReportRow>();
				}

				metricsList.add(reportRow);
				projectMetricsMap.put(key, metricsList);
			}

			projectIdSet.add(key);
		}
	}
	 */

}