/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ag.timeline.common.TextHelper;
import org.ag.timeline.presentation.transferobject.common.CodeValue;

/**
 * 
 * 
 * @author Abhishek Gaurav
 */
public class ActivityReply extends BusinessReply {

	private long activityCount = 0;
	private Map<Long, String> projectNameMap = null;
	private Map<Long, List<CodeValue>> projectActivitiesMap = null;
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
			final String activityText) {

		if (this.projectActivitiesMap == null) {
			this.projectActivitiesMap = new HashMap<Long, List<CodeValue>>();
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
			this.projectActivitiesMap.put(projectId, new ArrayList<CodeValue>());
		}

		if (!this.activityIdSet.contains(activityId)) {
			this.projectActivitiesMap.get(projectId).add(new CodeValue(activityId, activityText));
			this.activityIdSet.add(activityId);
			activityCount++;
		}
	}

	public List<CodeValue> getProjectActivities(final long projectId) {

		List<CodeValue> activities = null;

		if (this.projectActivitiesMap != null) {
			activities = this.projectActivitiesMap.get(projectId);
		}

		return activities;
	}

	public List<CodeValue> getProjectActivities(final String projectName) {

		List<CodeValue> activities = null;
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
