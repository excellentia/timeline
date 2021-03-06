/**
 * 
 */
package org.ag.timeline.presentation.transferobject.search;

import org.ag.timeline.business.model.Task;

/**
 * Task Search params.
 * 
 * @author Abhishek Gaurav
 */
public class TaskSearchParameter extends ActivitySearchParameter {

	private Task task = null;

	/**
	 * Constructor.
	 */
	public TaskSearchParameter() {
		this.task = new Task();
	}

	/**
	 * Getter for taskId.
	 * 
	 * @return the taskId.
	 */
	public long getTaskId() {
		return this.task.getId();
	}

	/**
	 * Setter for taskId.
	 * 
	 * @param taskId the taskId to set.
	 */
	public void setTaskId(long taskId) {
		this.task.setId(taskId);
	}

	/**
	 * Getter for taskName.
	 * 
	 * @return the taskName.
	 */
	public String getTaskName() {
		return this.task.getText();
	}

	/**
	 * Setter for taskName.
	 * 
	 * @param taskName the taskName to set.
	 */
	public void setTaskName(String taskName) {
		this.task.setText(taskName);
	}

}
