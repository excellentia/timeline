/**
 * 
 */
package org.ag.timeline.business.model;

import org.ag.timeline.common.TimelineConstants;

/**
 * Denotes a Task mapped to {@link ProjectStage}.
 * 
 * @author Abhishek Gaurav
 */
public class ProjectStageTask extends AbstractModel {

	private Task task = null;

	private ProjectStage projectStage = null;

	private long position = 0;

	/**
	 * Getter for task.
	 * 
	 * @return the task.
	 */
	public Task getTask() {
		return this.task;
	}

	/**
	 * Setter for task.
	 * 
	 * @param task the task to set.
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * Getter for projectStage.
	 * 
	 * @return the projectStage.
	 */
	public ProjectStage getProjectStage() {
		return this.projectStage;
	}

	/**
	 * Setter for projectStage.
	 * 
	 * @param projectStage the projectStage to set.
	 */
	public void setProjectStage(ProjectStage projectStage) {
		this.projectStage = projectStage;
	}

	/**
	 * Getter for position.
	 * 
	 * @return the position.
	 */
	public long getPosition() {
		return this.position;
	}

	/**
	 * Setter for position.
	 * 
	 * @param position the position to set.
	 */
	public void setPosition(long position) {
		this.position = position;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectStageTask [task=");
		builder.append(task);
		builder.append(", projectStage=");
		builder.append(projectStage);
		builder.append(", position=");
		builder.append(position);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String getDescription() {
		StringBuilder builder = new StringBuilder(this.getProjectStage().getDescription());
		builder.append(TimelineConstants.COMMA);
		builder.append(this.getTask().getTaskText());
		return builder.toString();
	}

}
