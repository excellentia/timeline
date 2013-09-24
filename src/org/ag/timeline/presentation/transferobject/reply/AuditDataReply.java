package org.ag.timeline.presentation.transferobject.reply;

import java.util.ArrayList;
import java.util.List;

public class AuditDataReply extends BusinessReply {

	/**
	 * List of {@link AuditRow} objects.
	 */
	private List<AuditRow> rowList = null;

	/**
	 * Constructor.
	 */
	public AuditDataReply() {
		rowList = new ArrayList<AuditRow>();
	}

	/**
	 * Getter for rowList.
	 * 
	 * @return the rowList
	 */
	public List<AuditRow> getRowList() {
		return rowList;
	}

	/**
	 * Setter for rowList.
	 * 
	 * @param rowList the rowList to set
	 */
	public void addAuditRow(AuditRow auditRow) {

		if (auditRow != null) {
			this.rowList.add(auditRow);
		}
	}

}
