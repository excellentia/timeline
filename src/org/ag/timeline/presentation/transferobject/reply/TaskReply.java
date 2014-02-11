/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.ag.timeline.business.model.Activity;
import org.ag.timeline.business.model.Project;
import org.ag.timeline.business.model.Task;
import org.ag.timeline.common.CodeValueComparator;
import org.ag.timeline.presentation.transferobject.common.CodeValue;
import org.ag.timeline.presentation.transferobject.common.CodeValueStatus;

/**
 * Contains Tasks fetched from backend.
 * 
 * @author Abhishek Gaurav
 */
public class TaskReply extends BusinessReply {

	// TODO:AG complete javadocs

	private Map<Long, String> projectNameMap = null;
	
	private Map<Long, String> activityNameMap = null;

	private Map<Long, CodeValue> taskDetailMap = null;
	
	private Map<Long, Set<Long>> projectActivityIdMap = null;

	private Map<Long, Set<CodeValueStatus>> activityTasksMap = null;

	public TaskReply() {
		this.projectNameMap = new HashMap<Long, String>();
		this.activityNameMap = new HashMap<Long, String>();
		this.taskDetailMap = new HashMap<Long, CodeValue>();
		this.projectActivityIdMap = new HashMap<Long, Set<Long>>();
		this.activityTasksMap = new HashMap<Long, Set<CodeValueStatus>>();
	}

	/**
	 * Checks if there are any tasks.
	 * 
	 * @return boolean true if tasks exist.
	 */
	public boolean hasTasks() {

		boolean retVal = false;

		if ((this.taskDetailMap != null) && (this.taskDetailMap.keySet() != null) && (this.taskDetailMap.keySet().size() > 0)) {
			retVal = true;
		}

		return retVal;
	}

	/**
	 * Add a {@link Task} instance to this reply object.
	 * 
	 * @param task {@link Task} to be added.
	 */
	public void addTask(Task task) {

		if (task != null) {

			final Activity activity = task.getActivity();
			final Project project = activity.getProject();

			final long taskId = task.getId();
			final long actId = activity.getId();
			final long projId = project.getId();

			// populate names
			this.projectNameMap.put(projId, project.getName());
			
			this.activityNameMap.put(actId, activity.getName());

			// populate activity Set
			Set<Long> actSet = projectActivityIdMap.get(projId);

			if (actSet == null) {
				actSet = new HashSet<Long>();
			}

			actSet.add(actId);
			this.projectActivityIdMap.put(projId, actSet);

			// populate task
			Set<CodeValueStatus> taskSet = this.activityTasksMap.get(actId);

			if (taskSet == null) {
				taskSet = new HashSet<CodeValueStatus>();
			}

			taskSet.add(new CodeValueStatus(taskId, task.getText(), task.isActive()));
			this.activityTasksMap.put(actId, taskSet);
			this.taskDetailMap.put(taskId, new CodeValue(task.getStoryPoints(), task.getDetails()));
		}
	}

	/**
	 * Returns a list of Activity Ids for given project id.
	 * 
	 * @param projectId d of the project.
	 * @return List<Long> containing activity ids.
	 */
	public List<Long> getProjectActivityIds(final long projectId) {

		List<Long> activities = new ArrayList<Long>();

		if ((this.projectActivityIdMap != null) && (this.projectActivityIdMap.containsKey(projectId))) {
			activities.addAll(this.projectActivityIdMap.get(projectId));
			Collections.sort(activities);
		}

		return activities;
	}

	public List<CodeValueStatus> getActivityTasks(final long activityId) {
		List<CodeValueStatus> list = new ArrayList<CodeValueStatus>();

		if ((this.activityTasksMap != null) & (this.activityTasksMap.containsKey(activityId))) {
			list.addAll(this.activityTasksMap.get(activityId));
			Collections.sort(list, new CodeValueComparator());
		}

		return list;
	}

	public String getProjectName(final long projectId) {
		String name = null;

		if (this.projectNameMap != null) {
			name = this.projectNameMap.get(projectId);
		}

		return name;
	}

	public String getActivityName(final long activityyId) {
		String name = null;

		if (this.activityNameMap != null) {
			name = this.activityNameMap.get(activityyId);
		}

		return name;
	}

	public String getTaskDetail(final long taskId) {
		String name = null;

		if ((this.taskDetailMap != null) & (this.taskDetailMap.containsKey(taskId))) {
			name = this.taskDetailMap.get(taskId).getValue();
		}

		return name;
	}
	
	public long getTaskStoryPoints(final long taskId) {
		long size = 0;

		if ((this.taskDetailMap != null) & (this.taskDetailMap.containsKey(taskId))) {
			size = this.taskDetailMap.get(taskId).getCode();
		}

		return size;
	}
	
	public List<Long> getProjectIds() {
		List<Long> list = new ArrayList<Long>();

		if (this.projectNameMap != null) {
			
			Map<String, Long> projectNameIdMap = new TreeMap<String, Long>();
			
			for (Long id : this.projectNameMap.keySet()) {
				projectNameIdMap.put(this.projectNameMap.get(id), id);
			}
			
			list.addAll(projectNameIdMap.values());
		}

		return list;
	}

}
