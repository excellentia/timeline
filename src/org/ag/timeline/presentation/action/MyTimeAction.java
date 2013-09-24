package org.ag.timeline.presentation.action;

import java.util.Date;

import org.ag.timeline.common.TextHelper;
import org.ag.timeline.presentation.transferobject.reply.TimeDataReply;
import org.ag.timeline.presentation.transferobject.search.TimeDataSearchParameters;

/**
 * Time Entry related functionality.
 * 
 * @author Abhishek Gaurav
 */
public class MyTimeAction extends SecureBaseAction {

	/**
	 * Contains Time data fetched from database.
	 */
	private TimeDataReply timeData = null;
	
	/**
	 * Current Date.
	 */
	private final Date currentDate = new Date();
	
	/**
	 * Getter for timeData.
	 * 
	 * @return the timeData
	 */
	public TimeDataReply getTimeData() {
		return timeData;
	}

	/**
	 * Setter for timeData.
	 * 
	 * @param timeData the timeData to set
	 */
	public void setTimeData(TimeDataReply timeData) {
		this.timeData = timeData;
	}
	
	public String getStartWeekLabel() {
		return TextHelper.getDisplayWeek(currentDate, TextHelper.getDateAfter(currentDate, 6));
	}
	
	public String getEndWeekLabel() {
		return getStartWeekLabel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ag.timeline.presentation.action.SecureBaseAction#secureExecute()
	 */
	public String secureExecute() throws Exception {

		// fetch the time data for current week
		TimeDataSearchParameters searchParameters = new TimeDataSearchParameters();

		// activities for current user
		if (!super.isSessionUserAdmin()) {
			searchParameters.setUserId(super.getSessionUserId());	
		}

		// activities for current week
		final Date currDate = new Date();
		
		searchParameters.setStartWeekNum(TextHelper.getWeekNumber(currDate));
		searchParameters.setStartYear(TextHelper.getYear(currDate));
		searchParameters.setEndWeekNum(TextHelper.getWeekNumber(currDate));
		searchParameters.setEndYear(TextHelper.getYear(currDate));

		// call the service
		timeData = service.searchTimeData(searchParameters);

		return SUCCESS;
	}
}