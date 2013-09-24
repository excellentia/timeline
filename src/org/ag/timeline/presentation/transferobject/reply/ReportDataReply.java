package org.ag.timeline.presentation.transferobject.reply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReportDataReply extends BusinessReply {

	private Map<Long, List<ReportRow>> activityTimeMap = null;
	private Map<Long, List<ReportRow>> userTimeMap = null;
	private Set<Long> projectIdSet = null;
	private int rowCount = 0;

	/**
	 * Constructor.
	 */
	public ReportDataReply() {
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
