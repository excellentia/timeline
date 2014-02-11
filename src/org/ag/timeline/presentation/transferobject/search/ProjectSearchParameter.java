package org.ag.timeline.presentation.transferobject.search;

import org.ag.timeline.business.model.Project;
import org.ag.timeline.common.TimelineConstants.EntityStatus;

public class ProjectSearchParameter extends BasicSearchParameter {

	private Project project = null;

	private EntityStatus entityStatus = null;

	/**
	 * Constructor.
	 */
	public ProjectSearchParameter() {
		this.project = new Project();
	}

	public long getProjectId() {
		return this.project.getId();
	}

	public void setProjectId(long id) {
		this.project.setId(id);
	}

	public String getProjectName() {
		return this.project.getName();
	}

	public void setProjectName(String text) {
		this.project.setName(text);
	}

	/**
	 * Getter for entityStatus.
	 * 
	 * @return the entityStatus
	 */
	public EntityStatus getEntityStatus() {
		return entityStatus;
	}

	/**
	 * Setter for entityStatus.
	 * 
	 * @param entityStatus the entityStatus to set
	 */
	public void setEntityStatus(EntityStatus entityStatus) {
		this.entityStatus = entityStatus;
	}

}
