/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply.agile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.ag.timeline.business.model.Task;
import org.ag.timeline.business.model.TimeData;
import org.ag.timeline.common.TextHelper;
import org.ag.timeline.presentation.transferobject.reply.BusinessReply;

/**
 * Wrapper for Task Details.
 * 
 * @author Abhishek Gaurav
 */
public class TaskDetailReply extends BusinessReply {

	private TaskDetailRow detailRow = null;

	private List<TaskStageRow> stageRows = null;

	private List<TaskTimeRow> timeRows = null;

	/**
	 * Constructor.
	 */
	public TaskDetailReply() {
		this.stageRows = new ArrayList<TaskStageRow>();
		this.timeRows = new ArrayList<TaskTimeRow>();
	}

	/**
	 * Getter for detailRow.
	 * 
	 * @return the detailRow.
	 */
	public TaskDetailRow getDetailRow() {
		return this.detailRow;
	}

	/**
	 * Getter for stageRows.
	 * 
	 * @return the stageRows.
	 */
	public List<TaskStageRow> getStageRows() {

		if ((this.stageRows != null) && (this.stageRows.size() > 0)) {
			Collections.sort(this.stageRows);
		}

		return this.stageRows;
	}

	/**
	 * Getter for timeRows.
	 * 
	 * @return the timeRows.
	 */
	public List<TaskTimeRow> getTimeRows() {

		if ((this.timeRows != null) && (this.timeRows.size() > 0)) {
			Collections.sort(this.timeRows);
		}

		return this.timeRows;
	}

	public void addTaskDetailRow(Task task, String createUserName) {

		if (task != null) {
			this.detailRow = new TaskDetailRow();
			this.detailRow.setTaskName(task.getText());
			this.detailRow.setActiveStageName(task.getActivity().getName());
			this.detailRow.setProjectName(task.getActivity().getProject().getName());
			this.detailRow.setTaskDescription(task.getDetails());
			this.detailRow.setTaskCreateDate(task.getCreateDate());
			this.detailRow.setActive(task.isActive());
			this.detailRow.setCreateUserName(createUserName);
		}
	}

	public void addTaskTimeRow(TimeData data) {

		if (data != null) {
			TaskTimeRow row = new TaskTimeRow();
			row.setTime(TextHelper.getScaledDouble(data.calculateTotalTime()));
			row.setUser(data.getUser().getUserName());
			row.setStartDate(data.getWeek().getStartDate());
			row.setEndDate(data.getWeek().getEndDate());
			this.timeRows.add(row);
		}
	}

	public void addTaskStageRow(String stageName, Date changeDate, String userName) {
		TaskStageRow row = new TaskStageRow();
		row.setStageName(stageName);
		 row.setChangeDate(changeDate);
		// row.setStartDate(startDate);
		// row.setEndDate(endDate);
		row.setModifierUser(userName);
		this.stageRows.add(row);
	}

}
