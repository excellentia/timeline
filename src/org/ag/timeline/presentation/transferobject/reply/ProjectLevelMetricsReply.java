package org.ag.timeline.presentation.transferobject.reply;

import java.util.ArrayList;
import java.util.List;

public class ProjectLevelMetricsReply extends BusinessReply {

	private List<ProjectLevelMetrics> projectLevelMetrics = null;

	/**
	 * Constructor.
	 */
	public ProjectLevelMetricsReply() {
		this.projectLevelMetrics = new ArrayList<ProjectLevelMetrics>();
	}

	// public void addProjectMetrics(ProjectLevelMetrics row) {

	// if (row != null) {
	//
	// Project project = row.getProject();
	// final long key = project.getId();
	//
	// // handle the project base data
	// {
	// // create project base data
	// ProjectBaseData baseData = new ProjectBaseData();
	// baseData.setCode(key);
	// baseData.setValue(project.getName());
	// baseData.setLeadName(TextHelper.trimToEmpty(project.getLeadName()));
	// baseData.setBudgetAtCompletion(TextHelper.getScaledDouble(project.getBudgetAtCompletion()));
	// baseData.setStartDate(TextHelper.getDateAsString(project.getStartDate()));
	// baseData.setEndDate(TextHelper.getDateAsString(project.getEndDate()));
	//
	// // update the project map
	// projectBaseDataMap.put(key, baseData);
	//
	// }
	//
	// // handle the project metrics data
	// {
	// ProjectMetricsReportRow reportRow = new ProjectMetricsReportRow();
	// {
	// reportRow.setProjectId(project.getId());
	// reportRow.setProjectName(project.getName());
	// reportRow.setLeadName(project.getLeadName());
	//
	// Week week = row.getWeek();
	// reportRow.setWeekId(week.getId());
	// reportRow.setWeekNum(week.getWeekNumber());
	// reportRow.setYear(week.getYear());
	// reportRow.setDisplayWeek(TextHelper.getDisplayWeek(week.getStartDate(),
	// week.getEndDate()));
	//
	// reportRow.setPlannedValue(TextHelper.getScaledDouble(row.getPlannedValue()));
	// reportRow.setEarnedValue(TextHelper.getScaledDouble(row.getEarnedValue()));
	// reportRow.setActualCost(TextHelper.getScaledDouble(row.getActualCost()));
	// reportRow.setActualsToDate(TextHelper.getScaledDouble(row.getActualsToDate()));
	// reportRow.setExpectedToComplete(TextHelper.getScaledDouble(row.getExpectedToComplete()));
	// reportRow.setSoftwareProgrammingEffort(TextHelper.getScaledDouble(row
	// .getSoftwareProgrammingEffort()));
	//
	// reportRow.setDefectRatio(TextHelper.getScaledDouble(row.getDefectRatio()));
	// reportRow.setDefects(row.getDefects());
	//
	// reportRow
	// .setSchedulePerformanceIndex(TextHelper.getScaledDouble(row.getSchedulePerformanceIndex()));
	// reportRow.setCostPerformanceIndex(TextHelper.getScaledDouble(row.getCostPerformanceIndex()));
	// }
	//
	// List<ProjectMetricsReportRow> metricsList = projectMetricsMap.get(key);
	//
	// if (metricsList == null) {
	// metricsList = new ArrayList<ProjectMetricsReportRow>();
	// }
	//
	// metricsList.add(reportRow);
	// projectMetricsMap.put(key, metricsList);
	// }
	//
	// projectIdSet.add(key);
	// }

	// this.projectMetricsList.add(row);
	// }

	/**
	 * Getter for projectMetrics.
	 * 
	 * @return the projectMetrics.
	 */
	public List<ProjectLevelMetrics> getProjectLevelMetrics() {
		return this.projectLevelMetrics;
	}

	public void addProjectLevelMetrics(ProjectLevelMetrics metrics) {
		this.projectLevelMetrics.add(metrics);
	}
}
