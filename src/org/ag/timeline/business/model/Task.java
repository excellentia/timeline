/**
 * 
 */
package org.ag.timeline.business.model;

import org.ag.timeline.common.TimelineConstants;

/**
 * Contains Task details related to user's time logged against an
 * {@link Activity}.
 * 
 * @author Abhishek Gaurav
 */
public class Task extends AbstractModel {

	// Task does not contain Activity as same Task may be target of different
	// activities for a single Project

	private Project project = null;

	private String taskText = null;

	/**
	 * Getter for project.
	 * 
	 * @return the project.
	 */
	public Project getProject() {
		return this.project;
	}

	/**
	 * Setter for project.
	 * 
	 * @param project the project to set.
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * Getter for taskText.
	 * 
	 * @return the taskText.
	 */
	public String getTaskText() {
		return this.taskText;
	}

	/**
	 * Setter for taskText.
	 * 
	 * @param taskText the taskText to set.
	 */
	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Task [id=");
		builder.append(super.getId());
		builder.append(", project=");
		builder.append(project);
		builder.append(", taskText=");
		builder.append(taskText);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String getDescription() {
		StringBuilder builder = new StringBuilder(this.getProject().getName());
		builder.append(TimelineConstants.COMMA);
		builder.append(this.getTaskText());
		return builder.toString();
	}

}
