/**
 * 
 */
package org.ag.timeline.business.model;

import org.ag.timeline.common.TimelineConstants;

/**
 * Contains Task details related to user's time logged against an
 * {@link Activity}.
 * 
 * @author Abhishek Gaurav
 */
public class Task extends AbstractModel {

	private Project project = null;

	private Activity activity = null;

	private String text = null;

	private String details = null;

	private boolean active = true;

	/**
	 * Getter for project.
	 * 
	 * @return the project.
	 */
	private Project getProject() {
		return this.project;
	}

	/**
	 * Setter for project.
	 * 
	 * @param project the project to set.
	 */
	private void setProject(Project project) {
		this.project = project;
	}

	/**
	 * Getter for text.
	 * 
	 * @return the text.
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Setter for text.
	 * 
	 * @param text the text to set.
	 */
	public void setText(String taskText) {
		this.text = taskText;
	}

	/**
	 * Getter for activity.
	 * 
	 * @return the activity.
	 */
	public Activity getActivity() {
		return this.activity;
	}

	/**
	 * Setter for activity.
	 * 
	 * @param activity the activity to set.
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;

		if (activity != null) {
			this.setProject(activity.getProject());
		}
	}

	/**
	 * Getter for details.
	 * 
	 * @return the details.
	 */
	public String getDetails() {
		return this.details;
	}

	/**
	 * Setter for details.
	 * 
	 * @param details the details to set.
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * Getter for active.
	 * 
	 * @return the active.
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * Setter for active.
	 * 
	 * @param active the active to set.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Task [id=");
		builder.append(super.getId());
		builder.append(", project=");
		builder.append(project);
		builder.append(", activity=");
		builder.append(activity);
		builder.append(", text=");
		builder.append(text);
		builder.append(", active=");
		builder.append(active);
		builder.append(", details=");
		builder.append(details);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String getDescription() {
		StringBuilder builder = new StringBuilder(this.getProject().getName());
		builder.append(TimelineConstants.COMMA);
//		builder.append(this.getActivity().getName());
//		builder.append(TimelineConstants.COMMA);
		builder.append(this.getText());
		return builder.toString();
	}

}
