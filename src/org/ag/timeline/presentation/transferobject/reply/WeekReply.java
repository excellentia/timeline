/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

import java.util.Set;

import org.ag.timeline.business.model.Week;

/**
 * 
 * 
 * @author Abhishek Gaurav
 */
public class WeekReply extends BusinessReply {

	private Set<Week> weeks = null;

	/**
	 * Getter for weeks.
	 * 
	 * @return the weeks
	 */
	public Set<Week> getWeeks() {
		return weeks;
	}

	/**
	 * Setter for weeks.
	 * 
	 * @param weeks the weeks to set
	 */
	public void setWeeks(Set<Week> weeks) {
		this.weeks = weeks;
	}

}
