package org.ag.timeline.presentation.transferobject.reply;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ag.timeline.business.model.User;
import org.ag.timeline.business.model.UserPreferences;
import org.ag.timeline.business.model.Week;
import org.ag.timeline.common.TextHelper;
import org.ag.timeline.common.TimelineConstants;
import org.ag.timeline.common.UserComparator;

/**
 * Wrapper for search results for collection of {@link User} objects grouped per
 * week.
 * 
 * @author Abhishek Gaurav
 */

public class WeeklyUserReply extends BusinessReply {

	private Map<Long, List<User>> weeklyUserMap = null;

	private Map<Long, String> weeklyLabelMap = null;
	
	private Map<Long, String> userEmailMap = null;

	private Set<Week> weekSet = null;

	private long userCount = 0;

	/**
	 * Constructor.
	 * 
	 * @param weeklyUserMap
	 */
	public WeeklyUserReply() {
		this.weekSet = new HashSet<Week>();
		this.weeklyLabelMap = new HashMap<Long, String>();
		this.weeklyUserMap = new HashMap<Long, List<User>>();
		this.userEmailMap = new HashMap<Long, String>();
	}

	public void addWeeklyUser(Week week, User user) {

		if ((week != null) && (user != null)) {

			final long key = week.getId();

			List<User> list = weeklyUserMap.get(key);

			if (list == null) {
				list = new ArrayList<User>();
			}

			list.add(user);
			weeklyUserMap.put(key, list);
			weekSet.add(week);
			weeklyLabelMap.put(key,
					TextHelper.getDisplayWeek(week.getStartDate(), week.getEndDate(), TimelineConstants.EMPTY));
		}
	}

	public void setWeeklyUserList(Week week, List<User> users) {

		if (week != null) {

			final long key = week.getId();
			this.weeklyUserMap.put(key, users);
			weekSet.add(week);
			weeklyLabelMap.put(key,
					TextHelper.getDisplayWeek(week.getStartDate(), week.getEndDate(), TimelineConstants.EMPTY));

			if (users != null) {
				this.userCount += users.size();
				
				for (User user : users) {
					
					for (UserPreferences preference : user.getPreferences()) {
						
						if (preference != null) {
							userEmailMap.put(user.getId(), preference.getEmail());
						}
					}
				}
			}
		}
	}
	
	public String getWeekLabel(final long weekId) {
		return weeklyLabelMap.get(weekId);
	}

	/**
	 * Getter for weekList.
	 * 
	 * @return the weekList.
	 */
	public List<Week> getWeekList() {

		List<Week> list = new ArrayList<Week>();
		list.addAll(this.weekSet);

		return list;
	}

	public List<Long> getWeekIdList() {

		List<Long> list = new ArrayList<Long>(this.weekSet.size());

		for (Week week : this.weekSet) {
			list.add(week.getId());
		}

		Collections.sort(list);

		return list;

	}

	/**
	 * Getter for WeeklyUsers.
	 * 
	 * @return the WeeklyUsers.
	 */
	public List<User> getWeeklyUsers(Long weekId) {
		List<User> list = this.weeklyUserMap.get(weekId);
		Collections.sort(list, new UserComparator());
		return list;
	}

	/**
	 * Getter for userCount.
	 * 
	 * @return the userCount.
	 */
	public long getUserCount() {
		return this.userCount;
	}
	
	public String getUserEmail(final long userDbId) {
		return this.userEmailMap.get(userDbId);
	}

}
