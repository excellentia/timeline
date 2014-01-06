package org.ag.timeline.presentation.transferobject.input;

import org.ag.timeline.business.model.Project;
import org.ag.timeline.business.model.User;

public class ProjectInput extends AbstractTimelineInput {

	private Project project = null;

	/**
	 * Default Constructor.
	 */
	public ProjectInput() {
		this.project = new Project();
		this.project.setLead(new User());
	}

	/**
	 * Getter for projectId.
	 * 
	 * @return the projectId.
	 */
	public long getProjectId() {
		return this.project.getId();
	}

	/**
	 * Setter for projectId.
	 * 
	 * @param id the projectId to set.
	 */
	public void setProjectId(long id) {
		this.project.setId(id);
	}

	/**
	 * Getter for leadId.
	 * 
	 * @return the leadId
	 */
	public long getLeadId() {
		return this.project.getLead().getId();
	}

	/**
	 * Setter for leadId.
	 * 
	 * @param leadId the leadId to set
	 */
	public void setLeadId(long leadId) {
		this.project.getLead().setId(leadId);
	}

	/**
	 * Getter for newLabelText.
	 * 
	 * @return the newLabelText
	 */
	public String getNewLabelText() {
		return this.project.getName();
	}

	/**
	 * Setter for newLabelText.
	 * 
	 * @param newLabelText the newLabelText to set
	 */
	public void setNewLabelText(String newLabelText) {
		this.project.setName(newLabelText);
	}

	/**
	 * Getter for active.
	 * 
	 * @return the active.
	 */
	public boolean isActive() {
		return this.project.isActive();
	}

	/**
	 * Setter for active.
	 * 
	 * @param active the active to set.
	 */
	public void setActive(boolean active) {
		this.project.setActive(active);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectInput [").append(project).append("]");
		return builder.toString();
	}
}