package org.ag.timeline.presentation.transferobject.reply;

import org.ag.timeline.presentation.transferobject.common.CodeValue;

/**
 * Project related data.
 * 
 * @author Abhishek Gaurav
 */
public class ProjectData extends CodeValue {

	/**
	 * Lead name.
	 */
	private String leadName = null;

	/**
	 * LeadDbId.
	 */
	private long leadDbId = 0;

	private boolean active = false;

	/**
	 * Getter for leadName.
	 * 
	 * @return The leadName.
	 */
	public String getLeadName() {
		return leadName;
	}

	/**
	 * Setter for leadName.
	 * 
	 * @param leadName The leadName to set.
	 */
	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

	/**
	 * Getter for leadDbId.
	 * 
	 * @return The leadDbId.
	 */
	public long getLeadDbId() {
		return leadDbId;
	}

	/**
	 * Setter for leadDbId.
	 * 
	 * @param leadDbId The leadDbId to set.
	 */
	public void setLeadDbId(long leadDbId) {
		this.leadDbId = leadDbId;
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

}