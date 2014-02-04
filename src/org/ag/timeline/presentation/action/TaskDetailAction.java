package org.ag.timeline.presentation.action;

import org.ag.timeline.presentation.transferobject.reply.agile.TaskDetailReply;
import org.ag.timeline.presentation.transferobject.search.TaskSearchParameter;

/**
 * Task Detail related functionality.
 * 
 * @author Abhishek Gaurav
 */
public class TaskDetailAction extends SecureBaseAction {

	private long taskId = 0;

	private TaskDetailReply reply = null;

	public String secureExecute() throws Exception {

		TaskSearchParameter searchParameters = new TaskSearchParameter();
		searchParameters.setTaskId(taskId);
		reply = service.getTaskDetails(searchParameters);

		return SUCCESS;
	}

	/**
	 * Getter for reply.
	 * 
	 * @return the reply.
	 */
	public TaskDetailReply getReply() {
		return this.reply;
	}

	/**
	 * Setter for reply.
	 * 
	 * @param reply the reply to set.
	 */
	public void setReply(TaskDetailReply reply) {
		this.reply = reply;
	}

	/**
	 * Getter for taskId.
	 * 
	 * @return the taskId.
	 */
	public long getTaskId() {
		return this.taskId;
	}

	/**
	 * Setter for taskId.
	 * 
	 * @param taskId the taskId to set.
	 */
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

}
