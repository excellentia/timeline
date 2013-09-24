package org.ag.timeline.presentation.transferobject.input;


public class ProjectInput extends AbstractTimelineInput {

	private long projectId = 0;
	private long leadId = 0;
	private String newLabelText = null;

	/**
	 * Getter for projectId.
	 * 
	 * @return the projectId.
	 */
	public long getProjectId() {
		return projectId;
	}

	/**
	 * Setter for projectId.
	 * 
	 * @param id the projectId to set.
	 */
	public void setProjectId(long id) {
		this.projectId = id;
	}

	/**
	 * Getter for leadId.
	 * 
	 * @return the leadId
	 */
	public long getLeadId() {
		return leadId;
	}

	/**
	 * Setter for leadId.
	 * 
	 * @param leadId the leadId to set
	 */
	public void setLeadId(long leadId) {
		this.leadId = leadId;
	}

	/**
	 * Getter for newLabelText.
	 * 
	 * @return the newLabelText
	 */
	public String getNewLabelText() {
		return newLabelText;
	}

	/**
	 * Setter for newLabelText.
	 * 
	 * @param newLabelText the newLabelText to set
	 */
	public void setNewLabelText(String newLabelText) {
		this.newLabelText = newLabelText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectInput [projectId=").append(projectId).append(", leadId=").append(leadId)
				.append(", newLabelText=").append(newLabelText).append("]");
		return builder.toString();
	}

}