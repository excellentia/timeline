/**
 * 
 */
package org.ag.timeline.business.model;

import org.ag.timeline.common.TimelineConstants;

/**
 * Denotes Stage mapped to a Project.
 * 
 * @author Abhishek Gaurav
 */
public class ProjectStage extends AbstractModel {

	private Project project = null;

	private Stage stage = null;

	private long position = 0;

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
	 * Getter for stage.
	 * 
	 * @return the stage.
	 */
	public Stage getStage() {
		return this.stage;
	}

	/**
	 * Setter for stage.
	 * 
	 * @param stage the stage to set.
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
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
		builder.append("ProjectStage [project=");
		builder.append(project);
		builder.append(", stage=");
		builder.append(stage);
		builder.append(", position=");
		builder.append(position);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String getDescription() {
		StringBuilder builder = new StringBuilder(this.getProject().getDescription());
		builder.append(TimelineConstants.COMMA);
		builder.append(this.getStage().getDescription());
		builder.append(TimelineConstants.COMMA);
		builder.append(this.getPosition());
		return builder.toString();
	}

}
