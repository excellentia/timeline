package org.ag.timeline.presentation.transferobject.search;

import org.ag.timeline.business.model.Project;

public class ProjectSearchParameter extends BasicSearchParameter {

	private Project project = null;

	private boolean searchActiveProjects = false;

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
	 * Getter for searchActiveProjects.
	 * 
	 * @return the searchActiveProjects.
	 */
	public boolean isSearchActiveProjects() {
		return this.searchActiveProjects;
	}

	/**
	 * Setter for searchActiveProjects.
	 * 
	 * @param searchActiveProjects the searchActiveProjects to set.
	 */
	public void setSearchActiveProjects(boolean searchActiveProjects) {
		this.searchActiveProjects = searchActiveProjects;
	}

}
