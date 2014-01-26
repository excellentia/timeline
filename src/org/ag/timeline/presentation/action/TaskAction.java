package org.ag.timeline.presentation.action;

import org.ag.timeline.presentation.transferobject.reply.TaskReply;
import org.ag.timeline.presentation.transferobject.search.TaskSearchParameter;

/**
 * Task related functionality.
 * 
 * @author Abhishek Gaurav
 */
public class TaskAction extends SecureBaseAction {

	private TaskReply taskReply = null;

	public String secureExecute() throws Exception {

		TaskSearchParameter searchParameters = new TaskSearchParameter();
		searchParameters.setSearchAllTasks(Boolean.TRUE);

//		if (!super.isSessionUserAdmin()) {
//			searchParameters.setUserDbId(super.getSessionUserId());
//		}

		taskReply = service.searchTasks(searchParameters);

		return SUCCESS;
	}

	/**
	 * Getter for reply.
	 * 
	 * @return the reply
	 */
	public TaskReply getTaskReply() {
		return taskReply;
	}

	/**
	 * Setter for reply.
	 * 
	 * @param reply the reply to set
	 */
	public void setTaskReply(TaskReply reply) {
		this.taskReply = reply;
	}

}
