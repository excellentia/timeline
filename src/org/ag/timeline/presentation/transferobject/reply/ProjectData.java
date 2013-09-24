package org.ag.timeline.presentation.transferobject.reply;

import org.ag.timeline.presentation.transferobject.common.CodeValue;

/**
 * Project related data.
 * 
 * @author Abhishek Gaurav
 */
public class ProjectData extends CodeValue {

	private String leadName = null;

	/**
	 * Getter for leadName.
	 * 
	 * @return the leadName
	 */
	public String getLeadName() {
		return leadName;
	}

	/**
	 * Setter for leadName.
	 * 
	 * @param leadName the leadName to set
	 */
	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

}
