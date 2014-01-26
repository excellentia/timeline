/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ag.timeline.common.TextHelper;
import org.ag.timeline.presentation.transferobject.common.CodeValueStatus;

/**
 * 
 * 
 * @author Abhishek Gaurav
 */
public class ActivityReply extends BusinessReply {

	private long activityCount = 0;
	private Map<Long, String> projectNameMap = null;
	private Map<Long, List<CodeValueStatus>> projectActivitiesMap = null;
	private List<Long> projectIdSet = null;
	private List<Long> activityIdSet = null;

	/**
	 * Getter for activityCount.
	 * 
	 * @return the activityCount.
	 */
	public long getActivityCount() {
		return activityCount;
	}

	public void addActivity(final long projectId, final String projectName, final long activityId,
			final String activityText, final boolean isDefaultActivity) {

		if (this.projectActivitiesMap == null) {
			this.projectActivitiesMap = new HashMap<Long, List<CodeValueStatus>>();
		}

		if (this.projectNameMap == null) {
			this.projectNameMap = new HashMap<Long, String>();
		}

		if (this.projectIdSet == null) {
			this.projectIdSet = new ArrayList<Long>();
		}

		if (this.activityIdSet == null) {
			this.activityIdSet = new ArrayList<Long>();
		}

		if (!this.projectIdSet.contains(projectId)) {
			this.projectNameMap.put(projectId, projectName);
			this.projectIdSet.add(projectId);
		}

		if (!this.projectActivitiesMap.containsKey(projectId)) {
			this.projectActivitiesMap.put(projectId, new ArrayList<CodeValueStatus>());
		}

		if (!this.activityIdSet.contains(activityId)) {
			this.projectActivitiesMap.get(projectId).add(new CodeValueStatus(activityId, activityText, isDefaultActivity));
			this.activityIdSet.add(activityId);
			activityCount++;
		}
	}

	public List<CodeValueStatus> getProjectActivitiesById(final long projectId) {

		List<CodeValueStatus> activities = null;

		if (this.projectActivitiesMap != null) {
			activities = this.projectActivitiesMap.get(projectId);
		}

		return activities;
	}

	public List<CodeValueStatus> getProjectActivitiesByName(final String projectName) {

		List<CodeValueStatus> activities = null;
		String projName = TextHelper.trimToNull(projectName);

		if ((this.projectNameMap != null) && (projName != null) && (this.projectNameMap.containsValue(projName))) {

			long projId = 0;

			for (long id : this.projectNameMap.keySet()) {
				if (this.projectNameMap.get(id).equals(projName)) {
					projId = id;
					break;
				}
			}

			if (this.projectActivitiesMap != null) {
				activities = this.projectActivitiesMap.get(projId);
			}
		}

		return activities;
	}

	public String getProjectName(final long projectId) {
		String name = null;

		if (this.projectNameMap != null) {
			name = this.projectNameMap.get(projectId);
		}

		return name;
	}

	public List<Long> getProjectIds() {
		return this.projectIdSet;
	}

	public List<Long> getActivityIds() {
		return this.activityIdSet;
	}
}
