/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply.agile;

import java.util.Date;

/**
 * @author Abhishek Gaurav
 */
public class TaskStageRow implements Comparable<TaskStageRow> {

	private String stageName = null;

	// private Date startDate = null;

	// private Date endDate = null;

	private Date changeDate = null;

	private String modifierUser = null;

	/**
	 * Getter for stageName.
	 * 
	 * @return the stageName
	 */
	public String getStageName() {
		return stageName;
	}

	/**
	 * Setter for stageName.
	 * 
	 * @param stageName the stageName to set
	 */
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	// /**
	// * Getter for startDate.
	// *
	// * @return the startDate
	// */
	// public Date getStartDate() {
	// return startDate;
	// }
	//
	// /**
	// * Setter for startDate.
	// *
	// * @param startDate the startDate to set
	// */
	// public void setStartDate(Date startDate) {
	// this.startDate = startDate;
	// }
	//
	// /**
	// * Getter for endDate.
	// *
	// * @return the endDate
	// */
	// public Date getEndDate() {
	// return endDate;
	// }
	//
	// /**
	// * Setter for endDate.
	// *
	// * @param endDate the endDate to set
	// */
	// public void setEndDate(Date endDate) {
	// this.endDate = endDate;
	// }

	/**
	 * Getter for modifierUser.
	 * 
	 * @return the modifierUser
	 */
	public String getModifierUser() {
		return modifierUser;
	}

	/**
	 * Getter for changeDate.
	 * 
	 * @return the changeDate.
	 */
	public Date getChangeDate() {
		return this.changeDate;
	}

	/**
	 * Setter for changeDate.
	 * 
	 * @param changeDate the changeDate to set.
	 */
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	/**
	 * Setter for modifierUser.
	 * 
	 * @param modifierUser the modifierUser to set
	 */
	public void setModifierUser(String modifierUser) {
		this.modifierUser = modifierUser;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskStageRow [stageName=");
		builder.append(stageName);
		// builder.append(", startDate=");
		// builder.append(startDate);
		// builder.append(", endDate=");
		// builder.append(endDate);
		builder.append(", changeDate=");
		builder.append(changeDate);
		builder.append(", modifierUser=");
		builder.append(modifierUser);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(TaskStageRow other) {

		int retVal = 0;

		if (other == null) {
			retVal = 1;
		} else {

			if (this.changeDate == null) {
				retVal = -1;
			} else if (other.changeDate == null) {
				retVal = 1;
			} else {

				if (this.changeDate.before(other.getChangeDate())) {
					retVal = 1;
				} else {
					retVal = -1;
				}
			}

		}

		return retVal;
	}
}
