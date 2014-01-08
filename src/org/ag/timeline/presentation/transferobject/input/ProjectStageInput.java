package org.ag.timeline.presentation.transferobject.input;

import org.ag.timeline.business.model.Project;
import org.ag.timeline.business.model.ProjectStage;
import org.ag.timeline.business.model.Stage;

/**
 * Wrapper for Project Stage entered by user.
 * 
 * @author Abhishek Gaurav
 */
public class ProjectStageInput extends AbstractTimelineInput {

	private ProjectStage projectStage = null;

	/**
	 * Default constructor.
	 */
	public ProjectStageInput() {
		this.projectStage = new ProjectStage();
		this.projectStage.setProject(new Project());
		this.projectStage.setStage(new Stage());
	}

	/**
	 * Getter for Project Id.
	 * 
	 * @return the projectId.
	 */
	public long getProjectId() {
		return this.projectStage.getProject().getId();
	}

	/**
	 * Getter for Stage Id.
	 * 
	 * @return the stageId.
	 */
	public long getStageId() {
		return this.projectStage.getStage().getId();
	}

	/**
	 * Getter for position.
	 * 
	 * @return the position.
	 */
	public long getPosition() {
		return this.projectStage.getPosition();
	}

	/**
	 * Setter for Project Id..
	 * 
	 * @param projectId the projectId to set.
	 */
	public void setProjectId(long projectId) {
		this.projectStage.getProject().setId(projectId);
	}

	/**
	 * Setter for Stage Id..
	 * 
	 * @param stageId the stageId to set.
	 */
	public void setStageId(long stageId) {
		this.projectStage.getStage().setId(stageId);
	}

	/**
	 * Setter for position.
	 * 
	 * @param position the position to set.
	 */
	public void setPosition(long position) {
		this.projectStage.setPosition(position);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectStageInput [projectStage=");
		builder.append(projectStage);
		builder.append("]");
		return builder.toString();
	}

}
