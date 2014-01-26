/**
 * 
 */
package org.ag.timeline.presentation.transferobject.search;

import org.ag.timeline.business.model.Activity;

/**
 * Search parameter
 * 
 * @author Abhishek Gaurav
 */
public class ActivitySearchParameter extends ProjectSearchParameter {

	private Activity activity = null;

	/**
	 * Constructor.
	 */
	public ActivitySearchParameter() {
		this.activity = new Activity();
	}

	/**
	 * Getter for activityId.
	 * 
	 * @return the activityId
	 */
	public long getActivityId() {
		return this.activity.getId();
	}

	/**
	 * Setter for activityId.
	 * 
	 * @param activityId the activityId to set
	 */
	public void setActivityId(long activityId) {
		this.activity.setId(activityId);
	}

	/**
	 * Getter for activityName.
	 * 
	 * @return the activityName
	 */
	public String getActivityName() {
		return this.activity.getName();
	}

	/**
	 * Setter for activityName.
	 * 
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activity.setName(activityName);
	}

}
