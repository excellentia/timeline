/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply.agile;

import java.util.Date;

/**
 * @author Abhishek Gaurav
 */
public class TaskDetailRow {

	private String taskName = null;

	private String taskDescription = null;

	private String activeStageName = null;

	private String projectName = null;

	private String createUserName = null;

	private Date taskCreateDate = null;

	private boolean active = false;

	/**
	 * Getter for taskName.
	 * 
	 * @return the taskName.
	 */
	public String getTaskName() {
		return this.taskName;
	}

	/**
	 * Setter for taskName.
	 * 
	 * @param taskName the taskName to set.
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * Getter for taskDescription.
	 * 
	 * @return the taskDescription.
	 */
	public String getTaskDescription() {
		return this.taskDescription;
	}

	/**
	 * Setter for taskDescription.
	 * 
	 * @param taskDescription the taskDescription to set.
	 */
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	/**
	 * Getter for activeStageName.
	 * 
	 * @return the activeStageName.
	 */
	public String getActiveStageName() {
		return this.activeStageName;
	}

	/**
	 * Setter for activeStageName.
	 * 
	 * @param activeStageName the activeStageName to set.
	 */
	public void setActiveStageName(String activeStageName) {
		this.activeStageName = activeStageName;
	}

	/**
	 * Getter for projectName.
	 * 
	 * @return the projectName.
	 */
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * Setter for projectName.
	 * 
	 * @param projectName the projectName to set.
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Getter for taskCreateDate.
	 * 
	 * @return the taskCreateDate.
	 */
	public Date getTaskCreateDate() {
		return this.taskCreateDate;
	}

	/**
	 * Setter for taskCreateDate.
	 * 
	 * @param taskCreateDate the taskCreateDate to set.
	 */
	public void setTaskCreateDate(Date taskCreateDate) {
		this.taskCreateDate = taskCreateDate;
	}

	/**
	 * Getter for active.
	 * 
	 * @return the active.
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * Setter for active.
	 * 
	 * @param active the active to set.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Getter for createUserName.
	 * 
	 * @return the createUserName.
	 */
	public String getCreateUserName() {
		return this.createUserName;
	}

	/**
	 * Setter for createUserName.
	 * 
	 * @param createUserName the createUserName to set.
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskDetailRow [taskName=");
		builder.append(taskName);
		builder.append(", taskDescription=");
		builder.append(taskDescription);
		builder.append(", activeStageName=");
		builder.append(activeStageName);
		builder.append(", projectName=");
		builder.append(projectName);
		builder.append(", createUserName=");
		builder.append(createUserName);
		builder.append(", taskCreateDate=");
		builder.append(taskCreateDate);
		builder.append(", active=");
		builder.append(active);
		builder.append("]");
		return builder.toString();
	}

}
