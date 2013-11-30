package org.ag.timeline.presentation.transferobject.reply;

import java.util.ArrayList;
import java.util.List;

public class DetailedReportReply extends BusinessReply {

	private List<DetailedReportRow> rowList = null;

	private double total = 0.0d;

	/**
	 * Constructor.
	 */
	public DetailedReportReply() {
		this.rowList = new ArrayList<DetailedReportRow>();
	}

	public void addRow(DetailedReportRow row) {

		if (row != null) {
			rowList.add(row);
			total += row.getWeeklySum();
		}
	}

	/**
	 * Getter for rowList.
	 * 
	 * @return the rowList.
	 */
	public List<DetailedReportRow> getRowList() {
		return this.rowList;
	}

	/**
	 * Getter for rowsPresent.
	 * 
	 * @return the rowsPresent.
	 */
	public boolean isRowsPresent() {
		return ((this.rowList != null) && (this.rowList.size() > 0));
	}

	/**
	 * Getter for total.
	 * 
	 * @return the total.
	 */
	public double getTotal() {
		return this.total;
	}

}
