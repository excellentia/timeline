/**
 * 
 */
package org.ag.timeline.business.model;

import java.math.BigDecimal;
import java.util.Date;
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

	private boolean active = false;

	private BigDecimal budgetAtCompletion = null;

	private Date startDate = null;

	private Date endDate = null;

	private boolean agile = true;

	private boolean metricsEnabled = false;

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

	/**
	 * Getter for budgetAtCompletion.
	 * 
	 * @return the budgetAtCompletion.
	 */
	public BigDecimal getBudgetAtCompletion() {
		return this.budgetAtCompletion;
	}

	/**
	 * Setter for budgetAtCompletion.
	 * 
	 * @param budgetAtCompletion the budgetAtCompletion to set.
	 */
	public void setBudgetAtCompletion(BigDecimal budgetAtCompletion) {
		this.budgetAtCompletion = budgetAtCompletion;
	}

	/**
	 * Getter for startDate.
	 * 
	 * @return the startDate.
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * Setter for startDate.
	 * 
	 * @param startDate the startDate to set.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Getter for endDate.
	 * 
	 * @return the endDate.
	 */
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * Setter for endDate.
	 * 
	 * @param endDate the endDate to set.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Getter for agile.
	 * 
	 * @return the agile.
	 */
	public boolean isAgile() {
		return this.agile;
	}

	/**
	 * Setter for agile.
	 * 
	 * @param agile the agile to set.
	 */
	public void setAgile(boolean agile) {
		this.agile = agile;
	}

	/**
	 * Business method for lead name.
	 * 
	 * @return Lead name, Null if not set.
	 */
	public String getLeadName() {

		String leadName = null;

		if (this.lead != null) {
			leadName = this.lead.getUserName();
		}

		return leadName;
	}

	/**
	 * Getter for metricsEnabled.
	 * 
	 * @return the metricsEnabled
	 */
	public boolean isMetricsEnabled() {
		return metricsEnabled;
	}

	/**
	 * Setter for metricsEnabled.
	 * 
	 * @param metricsEnabled the metricsEnabled to set
	 */
	public void setMetricsEnabled(boolean metricsEnabled) {
		this.metricsEnabled = metricsEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		builder.append(lead.getAbbrvUserName());
		builder.append(", agile=");
		builder.append(agile);
		builder.append(", leadName=");
		builder.append(", budgetAtCompletion=");
		builder.append(budgetAtCompletion);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);

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

		builder.append(", active=");
		builder.append(active);
		builder.append(", metricsEnabled=");
		builder.append(metricsEnabled);
		builder.append("]");

		return builder.toString();
	}

	@Override
	public String getDescription() {
		return this.getName();
	}

}
