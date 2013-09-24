/**
 * 
 */
package org.ag.timeline.business.model;


/**
 * Denotes data related to an Activity.
 * 
 * @author Abhishek Gaurav
 */
public class Activity extends AbstractModel {

	/**
	 * Activity name.
	 */
	private String name = null;

	/**
	 * Parent project.
	 */
	private Project project = null;

	/**
	 * Getter for name.
	 * 
	 * @return the name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setter for name.
	 * 
	 * @param name the name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

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
	public void setProject(Project parent) {
		this.project = parent;
	}

	@Override
	public String getDescription() {
		return this.getName();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Activity [name=");
		builder.append(name);
		builder.append(", project=");
		builder.append(project);
		builder.append("]");
		return builder.toString();
	}

}
