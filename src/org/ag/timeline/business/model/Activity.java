/**
 * 
 */
package org.ag.timeline.business.model;

import java.util.HashSet;
import java.util.Set;

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
	 * Status flag denoting if the activity is default landing spot for any newly created tasks.
	 */
	private boolean defaultActivity = false;

	/**
	 * All tasks belonging to this activity.
	 */
	private Set<Task> tasks = null;

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

	/**
	 * Getter for defaultActivity.
	 * 
	 * @return the defaultActivity.
	 */
	public boolean isDefaultActivity() {
		return this.defaultActivity;
	}

	/**
	 * Setter for defaultActivity.
	 * 
	 * @param defaultActivity the defaultActivity to set.
	 */
	public void setDefaultActivity(boolean defaultActivity) {
		this.defaultActivity = defaultActivity;
	}

	/**
	 * Getter for tasks.
	 * 
	 * @return the tasks.
	 */
	public Set<Task> getTasks() {
		return this.tasks;
	}

	/**
	 * Setter for tasks.
	 * 
	 * @param tasks the tasks to set.
	 */
	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Add single Task.
	 * 
	 * @param task {@link Task}
	 */
	public void addTask(Task task) {
		if (this.tasks == null) {
			this.tasks = new HashSet<Task>();
		}

		this.tasks.add(task);
		task.setActivity(this);
	}

	/**
	 * Remove single task.
	 * 
	 * @param task {@link Task}
	 */
	public void removeChild(Task task) {
		if ((this.tasks != null) && (this.tasks.size() > 0)) {
			this.tasks.remove(task);
		}
	}

	/**
	 * Checks if this project has any {@link Task}.
	 * 
	 * @return true if Tasks found, false otherwise.
	 */
	public boolean hasTasks() {
		return ((this.tasks != null) && (this.tasks.size() > 0));
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
		builder.append(", default=");
		builder.append(defaultActivity);
		builder.append("]");
		return builder.toString();
	}

}
