package org.ag.timeline.presentation.transferobject.input;

import org.ag.timeline.business.model.Project;
import org.ag.timeline.business.model.ProjectStage;
import org.ag.timeline.business.model.ProjectStageTask;
import org.ag.timeline.business.model.Stage;
import org.ag.timeline.business.model.Task;

/**
 * Wrapper for Project Stage Task mapping entered by user.
 * 
 * @author Abhishek Gaurav
 */
public class ProjectStageTaskInput extends AbstractTimelineInput {

	private ProjectStageTask projectStageTask = null;

	/**
	 * Default constructor.
	 */
	public ProjectStageTaskInput() {
		this.projectStageTask = new ProjectStageTask();
		this.projectStageTask.setTask(new Task());

		ProjectStage projectStage = new ProjectStage();
		projectStage.setProject(new Project());
		projectStage.setStage(new Stage());

		this.projectStageTask.setProjectStage(projectStage);
	}

	/**
	 * Getter for Project Id.
	 * 
	 * @return the projectId.
	 */
	public long getProjectId() {
		return this.projectStageTask.getProjectStage().getProject().getId();
	}

	/**
	 * Getter for Stage Id.
	 * 
	 * @return the stageId.
	 */
	public long getStageId() {
		return this.projectStageTask.getProjectStage().getStage().getId();
	}

	/**
	 * Getter for Task Id.
	 * 
	 * @return the taskId.
	 */
	public long getTaskId() {
		return this.projectStageTask.getTask().getId();
	}

	/**
	 * Getter for position.
	 * 
	 * @return the position.
	 */
	public long getPosition() {
		return this.projectStageTask.getPosition();
	}

	/**
	 * Setter for Project Id..
	 * 
	 * @param projectId the projectId to set.
	 */
	public void setProjectId(long projectId) {
		this.projectStageTask.getProjectStage().getProject().setId(projectId);
	}

	/**
	 * Setter for Stage Id..
	 * 
	 * @param stageId the stageId to set.
	 */
	public void setStageId(long stageId) {
		this.projectStageTask.getProjectStage().getStage().setId(stageId);
	}

	/**
	 * Setter for Task Id..
	 * 
	 * @param stageId the taskId to set.
	 */
	public void setTaskId(long taskId) {
		this.projectStageTask.getTask().setId(taskId);
	}

	/**
	 * Setter for position.
	 * 
	 * @param position the position to set.
	 */
	public void setPosition(long position) {
		this.projectStageTask.setPosition(position);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectStageTaskInput [projectStageTask=");
		builder.append(projectStageTask);
		builder.append("]");
		return builder.toString();
	}

}
