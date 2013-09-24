/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.ag.timeline.business.model.TimeData;
import org.ag.timeline.common.TextHelper;
import org.ag.timeline.common.TimelineConstants;

/**
 * Contains a collection of results of a search operation.
 * 
 * @author Abhishek Gaurav
 */
public class TimeDataReply extends BusinessReply {

	private Set<TimeDataRow> dataSet = null;
	private boolean entryPresent = false;

	private Map<Long, List<TimeDataRow>> projTimeMap = null;
	private Map<Long, List<TimeDataRow>> actTimeMap = null;
	private Map<Long, List<TimeDataRow>> userTimeMap = null;
	private Map<Long, List<TimeDataRow>> weekTimeMap = null;
	private Map<Long, List<TimeDataRow>> yearTimeMap = null;

	private Set<Long> userIdList = null;
	private Set<Long> weekIdList = null;

	private Map<Long, String> projectMap = null;
	private Map<Long, String> activityMap = null;

	private Map<Long, List<String>> weekDayLabelMap = null;
	private Map<Long, String> weekLabelMap = null;
	private Map<Long, Map<Long, Double>> reportAggregateMap = null;

	private String getDayLabel(final Date startDate, int offsetDays) {
		String label = TimelineConstants.EMPTY;

		if (startDate != null) {
			label = TextHelper.getDisplayDate(startDate, offsetDays);
		}

		return label;
	}

	private String getWeekLabel(final Date startDate, final Date endDate) {
		String label = TimelineConstants.EMPTY;

		if ((startDate != null) && (endDate != null)) {
			label = TextHelper.getDisplayWeek(startDate, endDate);
		}

		return label;
	}

	/**
	 * Constructor.
	 */
	public TimeDataReply() {
		this.dataSet = new HashSet<TimeDataRow>();
		this.projTimeMap = new HashMap<Long, List<TimeDataRow>>();
		this.actTimeMap = new HashMap<Long, List<TimeDataRow>>();
		this.userTimeMap = new HashMap<Long, List<TimeDataRow>>();
		this.weekTimeMap = new HashMap<Long, List<TimeDataRow>>();
		this.yearTimeMap = new HashMap<Long, List<TimeDataRow>>();

		this.userIdList = new TreeSet<Long>();
		this.weekIdList = new TreeSet<Long>();
		this.weekDayLabelMap = new HashMap<Long, List<String>>();
		this.weekLabelMap = new HashMap<Long, String>();

		this.projectMap = new HashMap<Long, String>();
		this.activityMap = new HashMap<Long, String>();

		this.reportAggregateMap = new HashMap<Long, Map<Long, Double>>();
	}

	public void addTimeData(TimeData data) {

		TimeDataRow row = new TimeDataRow();
		entryPresent = true;

		// populate row
		final long userId = data.getUser().getId();
		final long projectId = data.getProject().getId();
		final String projectName = data.getProject().getName();
		final String leadName = data.getProject().getLead().getUserName();
		final long activityId = data.getActivity().getId();
		final String activityName = data.getActivity().getName();
		final long weekId = data.getWeek().getId();
		final long year = data.getWeek().getYear();

		row.setId(data.getId());

		row.setProjectId(projectId);
		row.setProjectName(projectName);
		row.setLeadName(leadName);

		row.setActivityId(activityId);
		row.setActivityName(activityName);

		row.setUserId(userId);
		row.setUserFirstName(data.getUser().getFirstName());
		row.setUserLastName(data.getUser().getLastName());
		row.setUserFullName(data.getUser().getUserName());

		row.setWeekId(data.getWeek().getId());
		row.setWeekNum(data.getWeek().getWeekNumber());
		row.setYear(data.getWeek().getYear());
		row.setEndDate(data.getWeek().getEndDate());
		row.setStartDate(data.getWeek().getStartDate());

		row.setDay_1_time(TextHelper.getScaledDouble(data.getData_weekday_1()));
		row.setDay_2_time(TextHelper.getScaledDouble(data.getData_weekday_2()));
		row.setDay_3_time(TextHelper.getScaledDouble(data.getData_weekday_3()));
		row.setDay_4_time(TextHelper.getScaledDouble(data.getData_weekday_4()));
		row.setDay_5_time(TextHelper.getScaledDouble(data.getData_weekday_5()));
		row.setDay_6_time(TextHelper.getScaledDouble(data.getData_weekday_6()));
		row.setDay_7_time(TextHelper.getScaledDouble(data.getData_weekday_7()));
		row.setWeeklySum(TextHelper.getScaledDouble(data.calculateTotalTime()));

		// add to set
		dataSet.add(row);
		projectMap.put(projectId, projectName);
		activityMap.put(activityId, activityName);

		// update maps & sets
		{
			if (projTimeMap.get(projectId) == null) {
				projTimeMap.put(projectId, new ArrayList<TimeDataRow>());
			}

			if (actTimeMap.get(activityId) == null) {
				actTimeMap.put(activityId, new ArrayList<TimeDataRow>());
			}

			if (userTimeMap.get(userId) == null) {
				userTimeMap.put(userId, new ArrayList<TimeDataRow>());
			}

			if (weekTimeMap.get(weekId) == null) {
				weekTimeMap.put(weekId, new ArrayList<TimeDataRow>());
			}

			if (yearTimeMap.get(year) == null) {
				yearTimeMap.put(year, new ArrayList<TimeDataRow>());
			}

			projTimeMap.get(projectId).add(row);
			actTimeMap.get(activityId).add(row);
			userTimeMap.get(userId).add(row);
			weekTimeMap.get(weekId).add(row);
			yearTimeMap.get(year).add(row);

			userIdList.add(userId);
			weekIdList.add(weekId);

			if (this.weekDayLabelMap.get(weekId) == null) {

				List<String> labels = new ArrayList<String>();

				for (int i = 0; i < 7; i++) {
					labels.add(getDayLabel(row.getStartDate(), i));
				}

				this.weekDayLabelMap.put(weekId, labels);
			}

			if (this.weekLabelMap.get(weekId) == null) {
				this.weekLabelMap.put(weekId, getWeekLabel(row.getStartDate(), row.getEndDate()));
			}
		}
	}

	public void populateReportData(final long project, final long activity, final BigDecimal sum) {
		// update aggregates
		Map<Long, Double> actAggMap = reportAggregateMap.get(project);

		if (actAggMap == null) {
			actAggMap = new HashMap<Long, Double>();
		}

		actAggMap.put(activity, TextHelper.getScaledDouble(sum));
		reportAggregateMap.put(project, actAggMap);

	}

	public List<TimeDataRow> getEntriesForProject(final long projectId) {
		List<TimeDataRow> list = null;

		if ((this.projTimeMap != null) && (this.projTimeMap.containsKey(projectId))) {
			list = this.projTimeMap.get(projectId);
		}

		return list;
	}

	public List<TimeDataRow> getEntriesForActivity(final long activityId) {
		List<TimeDataRow> list = null;

		if ((this.actTimeMap != null) && (this.actTimeMap.containsKey(activityId))) {
			list = this.actTimeMap.get(activityId);
		}

		return list;
	}

	public List<TimeDataRow> getEntriesForUser(final long userId) {
		List<TimeDataRow> list = null;

		if ((this.userTimeMap != null) && (this.userTimeMap.containsKey(userId))) {
			list = this.userTimeMap.get(userId);
		}

		return list;
	}

	public List<TimeDataRow> getEntriesForWeek(final long weekId) {
		List<TimeDataRow> list = null;

		if ((this.weekTimeMap != null) && (this.weekTimeMap.containsKey(weekId))) {
			list = this.weekTimeMap.get(weekId);
		}

		return list;
	}

	public List<TimeDataRow> getEntriesForYear(final long year) {
		List<TimeDataRow> list = null;

		if ((this.yearTimeMap != null) && (this.yearTimeMap.containsKey(year))) {
			list = this.yearTimeMap.get(year);
		}

		return list;
	}

	/**
	 * Returns a collection of all the user Ids whose data is present in this
	 * reply object.
	 * 
	 * @return List<Long>;
	 */
	public List<Long> getUserIds() {
		List<Long> list = new ArrayList<Long>();
		list.addAll(this.userIdList);
		return list;
	}

	/**
	 * Returns a collection of all the week Ids whose data is present in this
	 * reply object.
	 * 
	 * @return ArrayList<Long>;
	 */
	public List<Long> getWeekIds() {
		List<Long> list = new ArrayList<Long>();
		list.addAll(this.weekIdList);
		return list;
	}

	/**
	 * Returns a list of labels for days in given week id.
	 * 
	 * @param weekId id of week to be used.
	 * @return List<String>
	 */
	public List<String> getDayLabels(final long weekId) {
		return this.weekDayLabelMap.get(weekId);
	}

	/**
	 * Returns the week label for given week id.
	 * 
	 * @param weekId id of week to be used.
	 * @return String.
	 */
	public String getWeekLabel(final long weekId) {
		return this.weekLabelMap.get(weekId);
	}

	public List<TimeDataRow> getAllData() {
		List<TimeDataRow> list = new ArrayList<TimeDataRow>();
		list.addAll(this.dataSet);
		return list;
	}

	/**
	 * Getter for projectsIdSet.
	 * 
	 * @return the projectsIdSet
	 */
	public Set<Long> getProjectsIdSet() {
		Set<Long> set = new HashSet<Long>();
		set.addAll(projectMap.keySet());
		return set;
	}

	/**
	 * Getter for activityIdSet.
	 * 
	 * @return the activityIdSet
	 */
	public Set<Long> getActivityIdSet() {
		Set<Long> set = new HashSet<Long>();
		set.addAll(activityMap.keySet());
		return set;
	}

	/**
	 * Getter for reportAggregateMap.
	 * 
	 * @return the reportAggregateMap
	 */
	public Map<String, Map<String, Double>> getReportAggregateMap() {
		Map<String, Map<String, Double>> map = new HashMap<String, Map<String, Double>>();

		if (reportAggregateMap != null) {

			String projectName = null;
			String activityName = null;
			Map<Long, Double> actMap = null;
			Map<String, Double> innerMap = null;

			for (long projectId : reportAggregateMap.keySet()) {

				projectName = projectMap.get(projectId);
				actMap = reportAggregateMap.get(projectId);

				innerMap = new HashMap<String, Double>();

				for (long activityId : actMap.keySet()) {

					activityName = activityMap.get(activityId);
					innerMap.put(activityName, actMap.get(activityId));
				}

				map.put(projectName, innerMap);

			}
		}
		return map;
	}

	/**
	 * Getter for entryPresent.
	 * 
	 * @return the entryPresent
	 */
	public final boolean isEntryPresent() {
		return entryPresent;
	}

}
