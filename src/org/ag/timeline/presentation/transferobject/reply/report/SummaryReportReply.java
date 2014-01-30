package org.ag.timeline.presentation.transferobject.reply.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ag.timeline.common.TimelineConstants;
import org.ag.timeline.presentation.transferobject.reply.BusinessReply;

public class SummaryReportReply extends BusinessReply {

	private Map<Long, List<ReportRow>> activityTimeMap = null;
	private Map<Long, List<ReportRow>> userTimeMap = null;
	private Set<Long> projectIdSet = null;
	private int rowCount = 0;

	/**
	 * Constructor.
	 */
	public SummaryReportReply() {
		this.activityTimeMap = new HashMap<Long, List<ReportRow>>();
		this.userTimeMap = new HashMap<Long, List<ReportRow>>();
		this.projectIdSet = new HashSet<Long>();
	}

	public void addActivityRow(ReportRow row) {

		if (row != null) {
			final long key = row.getProjectId();
			List<ReportRow> list = activityTimeMap.get(key);

			if (list == null) {
				list = new ArrayList<ReportRow>();
			}

			list.add(row);

			rowCount++;
			projectIdSet.add(key);
			activityTimeMap.put(key, list);
		}
	}

	public void addUserRow(ReportRow row) {

		if (row != null) {
			final long key = row.getProjectId();
			List<ReportRow> list = userTimeMap.get(key);

			if (list == null) {
				list = new ArrayList<ReportRow>();
			}

			list.add(row);

			rowCount++;
			projectIdSet.add(key);
			userTimeMap.put(key, list);
		}
	}

	public List<ReportRow> getActivityRowList(final long projectId) {
		return this.activityTimeMap.get(projectId);
	}

	public List<ReportRow> getUserRowList(final long projectId) {
		return this.userTimeMap.get(projectId);
	}
	
	public double getProjectTime(final long projectId) {
		double time = 0d;
		
		List<ReportRow> list = activityTimeMap.get(projectId);
		
		for (ReportRow reportRow : list) {
			time = time + reportRow.getRowTime();
		} 
		
		return time;
	}
	
	public String getProjectName(final long projectId) {
		String name = TimelineConstants.EMPTY;
		
		if (activityTimeMap.containsKey(projectId)) {
			ReportRow first = activityTimeMap.get(projectId).get(0);
			
			if (first != null) {
				name = first.getProjectName();
			}
		}
		
		return name;
	}

	public Set<Long> getProjectIds() {
		return this.projectIdSet;
	}

	/**
	 * Getter for rowCount.
	 * 
	 * @return the rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}
}
