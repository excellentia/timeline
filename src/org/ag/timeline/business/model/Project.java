/**
 * 
 */
package org.ag.timeline.business.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Denotes data related to a project.
 * 
 * @author Abhishek Gaurav
 */
public class Project extends AbstractModel {

	private String name = null;

	private User lead = null;

	private Set<Activity> activities = null;

	/**
	 * Getter for name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name.
	 * 
	 * @param name the name to set
	 */
	public void setName(String labelText) {
		this.name = labelText;
	}

	/**
	 * Getter for activities.
	 * 
	 * @return the activities
	 */
	public Set<Activity> getActivities() {
		return activities;
	}

	/**
	 * Setter for activities.
	 * 
	 * @param activities the activities to set
	 */
	public void setActivities(Set<Activity> children) {
		this.activities = children;
	}

	/**
	 * Add single child.
	 * 
	 * @param child
	 */
	public void addChild(Activity child) {
		if (this.activities == null) {
			this.activities = new HashSet<Activity>();
		}

		this.activities.add(child);
		child.setProject(this);
	}

	/**
	 * Remove single child.
	 * 
	 * @param child
	 */
	public void removeChild(Activity child) {
		if ((this.activities != null) && (this.activities.size() > 0)) {
			this.activities.remove(child);
			child.setProject(null);
		}
	}

	/**
	 * Checks if this project has any activites.
	 * 
	 * @return true if activities found, false otherwise.
	 */
	public boolean hasActivities() {
		return ((this.activities != null) && (this.activities.size() > 0));
	}

	/**
	 * Getter for lead.
	 * 
	 * @return the lead
	 */
	public User getLead() {
		return lead;
	}

	/**
	 * Setter for lead.
	 * 
	 * @param lead the lead to set
	 */
	public void setLead(User lead) {
		this.lead = lead;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Project [id=");
		builder.append(super.getId());
		builder.append(", name=");
		builder.append(name);
		builder.append(", leadName=");

		if (this.lead != null) {
			builder.append(lead.getUserName());
		}

		if (activities != null) {
			builder.append(", activities=[");

			for (Activity child : activities) {
				builder.append(child.getId()).append(",");
			}

			builder.append("]");
		}

		builder.append("]");
		return builder.toString();
	}

	@Override
	public String getDescription() {
		return this.getName();
	}

}
