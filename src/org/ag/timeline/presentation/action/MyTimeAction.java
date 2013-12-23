package org.ag.timeline.presentation.action;

import java.util.Date;

import org.ag.timeline.common.TextHelper;
import org.ag.timeline.presentation.transferobject.reply.TimeDataReply;
import org.ag.timeline.presentation.transferobject.reply.WeeklyUserReply;
import org.ag.timeline.presentation.transferobject.search.TimeDataSearchParameters;
import org.ag.timeline.presentation.transferobject.search.WeekSearchParameter;

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
	 * Users who have not made time entries.
	 */
	private WeeklyUserReply pendingUsers = null;

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

	/**
	 * Getter for pendingUsers.
	 * 
	 * @return the pendingUsers.
	 */
	public WeeklyUserReply getPendingUsers() {
		return this.pendingUsers;
	}

	/**
	 * Setter for pendingUsers.
	 * 
	 * @param pendingUsers the pendingUsers to set.
	 */
	public void setPendingUsers(WeeklyUserReply pendingUsers) {
		this.pendingUsers = pendingUsers;
	}

	public String getStartWeekLabel() {
		return TextHelper.getDisplayWeek(currentDate, TextHelper.getDateAfter(currentDate, 6));
	}

	public String getEndWeekLabel() {
		return getStartWeekLabel();
	}

	/*
	 * (non-Javadoc)
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
		final long currWeekNum = TextHelper.getWeekNumber(currDate);
		final long currYear = TextHelper.getYearForWeekDay(currDate);

		searchParameters.setStartWeekNum(currWeekNum);
		searchParameters.setStartYear(currYear);
		searchParameters.setEndWeekNum(currWeekNum);
		searchParameters.setEndYear(currYear);

		// 1. call the service to get Time Entries
		timeData = service.searchTimeData(searchParameters);

		// 2. call the service to get Users without any Time Entries
		WeekSearchParameter weekSearchParameter = new WeekSearchParameter();
		weekSearchParameter.setStartDate(TextHelper.getFirstDayOfWeek(currDate));
		weekSearchParameter.setEndDate(TextHelper.getLastDayOfWeek(currDate));
		
		pendingUsers = service.searchUsersWithoutEntries(weekSearchParameter);

		return SUCCESS;
	}
}