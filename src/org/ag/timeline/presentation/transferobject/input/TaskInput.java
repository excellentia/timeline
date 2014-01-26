package org.ag.timeline.presentation.transferobject.input;

import org.ag.timeline.business.model.Activity;
import org.ag.timeline.business.model.Project;
import org.ag.timeline.business.model.Task;

/**
 * Wrapper for Project Task data entered by user.
 * 
 * @author Abhishek Gaurav
 */
public class TaskInput extends AbstractTimelineInput {

	private Task task = null;

	/**
	 * Default constructor.
	 */
	public TaskInput() {
		this.task = new Task();
		Activity activity = new Activity();
		activity.setProject(new Project());
		this.task.setActivity(activity);
	}

	/**
	 * Getter for Project Id.
	 * 
	 * @return the projectId.
	 */
	public long getProjectId() {
		return this.task.getActivity().getProject().getId();
	}

	/**
	 * Getter for Activity Id.
	 * 
	 * @return the Activity Id.
	 */
	public long getActivityId() {
		return this.task.getActivity().getId();
	}

	/**
	 * Getter for Task Id.
	 * 
	 * @return the taskId.
	 */
	public long getTaskId() {
		return this.task.getId();
	}

	/**
	 * Getter for Task Text.
	 * 
	 * @return the task text.
	 */
	public String getTaskText() {
		return this.task.getText();
	}

	/**
	 * Getter for Task Description.
	 * 
	 * @return the task text.
	 */
	public String getTaskDescription() {
		return this.task.getDetails();
	}

	/**
	 * Setter for Project Id..
	 * 
	 * @param projectId the projectId to set.
	 */
	public void setProjectId(long projectId) {
		Project project = new Project();
		project.setId(projectId);
		this.task.getActivity().setProject(project);
	}

	/**
	 * Setter for Activity Id.
	 * 
	 * @param activityId the activityId to set.
	 */
	public void setActivityId(long activityId) {
		this.task.getActivity().setId(activityId);
	}

	/**
	 * Setter for Task Id..
	 * 
	 * @param stageId the taskId to set.
	 */
	public void setTaskId(long taskId) {
		this.task.setId(taskId);
	}

	/**
	 * Setter for Task Text.
	 */
	public void setTaskText(String text) {
		this.task.setText(text);
	}

	/**
	 * Setter for Task Description.
	 */
	public void setTaskDescription(String text) {
		this.task.setDetails(text);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskInput [task=");
		builder.append(task);
		builder.append("]");
		return builder.toString();
	}

}
