/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply.agile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ag.timeline.business.model.Activity;
import org.ag.timeline.business.model.Project;
import org.ag.timeline.business.model.Task;
import org.ag.timeline.common.CodeValueComparator;
import org.ag.timeline.presentation.transferobject.common.CodeValue;
import org.ag.timeline.presentation.transferobject.reply.BusinessReply;
import org.ag.timeline.presentation.transferobject.reply.ProjectData;

/**
 * Contains Rapid Board related data.
 * 
 * @author Abhishek Gaurav
 */
public class RapidBoardReply extends BusinessReply {

	private ProjectData projectData = null;

	private Map<Long, String> activityMap = null;

	private Map<Long, Long> activitySizeMap = null;

	private Map<Long, Long> taskSizeMap = null;

	private Map<Long, List<CodeValue>> activityTaskMap = null;

	/**
	 * Default Constructor
	 */
	public RapidBoardReply() {

		this.projectData = new ProjectData();
		this.activityMap = new HashMap<Long, String>();
		this.activityTaskMap = new HashMap<Long, List<CodeValue>>();
		this.activitySizeMap = new HashMap<Long, Long>();
		this.taskSizeMap = new HashMap<Long, Long>();
	}

	/**
	 * Getter for projectData.
	 * 
	 * @return the projectData.
	 */
	public ProjectData getProjectData() {
		return this.projectData;
	}

	/**
	 * Returns a list of {@link Activity} ids.
	 * 
	 * @return List<Long>.
	 */
	public List<Long> getActivityIdList() {
		List<Long> list = null;

		if ((this.activityMap != null) && (this.activityMap.keySet() != null)) {
			list = new ArrayList<Long>();
			list.addAll(this.activityMap.keySet());
			Collections.sort(list);
		}

		return list;
	}

	public List<CodeValue> getActivityTasks(long activityId) {
		List<CodeValue> list = null;

		if ((this.activityTaskMap != null) && (this.activityTaskMap.keySet() != null)) {
			list = this.activityTaskMap.get(activityId);

			if ((list != null) && (list.size() > 0)) {
				Collections.sort(list, new CodeValueComparator());
			}
		}

		return list;
	}

	public long getTaskSize(final long taskId) {
		long size = 0;
		
		if ((this.taskSizeMap != null) && (this.taskSizeMap.containsKey(taskId))) {
			size = this.taskSizeMap.get(taskId);
		}
		
		return size;
	}

	public String getActivityName(long activityId) {
		String name = null;

		if (this.activityMap != null) {
			name = this.activityMap.get(activityId);
		}

		return name;
	}

	/**
	 * Adds a {@link Project} to this reply.
	 * 
	 * @param project {@link Project} owing the current
	 *            {@link ProjectStageTaskReply} object.
	 */
	public void addProject(Project project) {
		this.projectData.setCode(project.getId());
		this.projectData.setValue(project.getName());
	}

	public void addActivity(Activity activity) {
		this.activityMap.put(activity.getId(), activity.getName());
	}

	public boolean hasTasks() {

		boolean retVal = false;

		if ((this.activityTaskMap != null) && (this.activityTaskMap.values() != null)
				&& (this.activityTaskMap.values().size() > 0)) {
			retVal = true;
		}

		return retVal;
	}

	public boolean hasProjectStages() {

		boolean retVal = false;

		if ((this.activityMap != null) && (this.activityMap.values() != null) && (this.activityMap.values().size() > 0)) {
			retVal = true;
		}

		return retVal;
	}

	public void addActivityTask(Task task) {

		if (task != null) {

			Activity activity = task.getActivity();

			if (activity != null) {

				final long activityId = activity.getId();
				List<CodeValue> taskList = this.activityTaskMap.get(activityId);

				if (taskList == null) {
					taskList = new ArrayList<CodeValue>();
				}

				taskList.add(new CodeValue(task.getId(), task.getText()));
				this.activityTaskMap.put(activityId, taskList);
				this.activityMap.put(task.getActivity().getId(), task.getActivity().getName());

				long size = 0;

				if (activitySizeMap.containsKey(activityId)) {
					size = activitySizeMap.get(activityId);
				}

				size = size + task.getStoryPoints();
				activitySizeMap.put(activityId, size);

				this.taskSizeMap.put(task.getId(), task.getStoryPoints());

			}
		}
	}

	public long getActivitySize(long activityId) {
		long size = 0;

		if ((this.activitySizeMap != null) && (this.activitySizeMap.containsKey(activityId))) {
			size = this.activitySizeMap.get(activityId);
		}

		return size;
	}
}
