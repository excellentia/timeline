/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for search results for {@link ProjectEstimateData}.
 * 
 * @author Abhishek Gaurav
 */
public class ProjectEstimatesReply extends BusinessReply {

	/**
	 * Serial Version U-Id.
	 */
	private static final long serialVersionUID = 3598326881240180613L;

	private List<ProjectEstimateData> estimates = null;

	/**
	 * Constructor.
	 */
	public ProjectEstimatesReply() {
		this.estimates = new ArrayList<ProjectEstimateData>();
	}

	/**
	 * @param message
	 * @param isMsgError
	 */
	public ProjectEstimatesReply(String message, boolean isMsgError) {
		super(message, isMsgError);
	}

	/**
	 * Getter for estimates.
	 * 
	 * @return the estimates
	 */
	public List<ProjectEstimateData> getEstimates() {
		List<ProjectEstimateData> list = new ArrayList<ProjectEstimateData>();
		list.addAll(estimates);
		return list;
	}

	/**
	 * Setter for estimates.
	 * 
	 * @param estimates the estimates to set
	 */
	public void addEstimates(ProjectEstimateData estimates) {
		this.estimates.add(estimates);
	}

}
