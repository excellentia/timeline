package org.ag.timeline.common;

import java.util.Comparator;

import org.ag.timeline.presentation.transferobject.reply.TimeDataRow;

/**
 * Compataror for {@link TimeDataRow}.
 * 
 * @author Abhishek Gaurav
 */
public class TimeDataRowComparator implements Comparator<TimeDataRow> {

	public int compare(TimeDataRow row1, TimeDataRow row2) {
		int retVal = 0;

		if ((row1 != null) || (row2 != null)) {

			if (row1 == null) {
				retVal = 1;
			} else if (row2 == null) {
				retVal = -1;
			} else {
				retVal = row1.getUserFullName().compareTo(row2.getUserFullName());
			}
		}

		return retVal;
	}

}
