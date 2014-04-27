package org.ag.timeline.presentation.transferobject.input;

import org.ag.timeline.business.model.Project;
import org.ag.timeline.business.model.User;

public class ProjectInput extends AbstractTimelineInput {

	private Project project = null;

	private long copyProjectId = 0;

	private boolean copyTaskFlag = false;

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

	/**
	 * @return the copyProjectId
	 */
	public long getCopyProjectId() {
		return copyProjectId;
	}

	/**
	 * @param copyProjectId the copyProjectId to set
	 */
	public void setCopyProjectId(long copyProjectId) {
		this.copyProjectId = copyProjectId;
	}

	/**
	 * Getter for copyTaskFlag.
	 * 
	 * @return the copyTaskFlag.
	 */
	public boolean isCopyTaskFlag() {
		return this.copyTaskFlag;
	}

	/**
	 * Setter for copyTaskFlag.
	 * 
	 * @param copyTaskFlag the copyTaskFlag to set.
	 */
	public void setCopyTaskFlag(boolean copyTaskFlag) {
		this.copyTaskFlag = copyTaskFlag;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectInput [project=");
		builder.append(project);
		builder.append(", copyProjectId=");
		builder.append(copyProjectId);
		builder.append(", copyTaskFlag=");
		builder.append(copyTaskFlag);
		builder.append("]");
		return builder.toString();
	}

}