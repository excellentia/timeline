/**
 * 
 */
package org.ag.timeline.presentation.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ag.timeline.application.exception.TimelineException;
import org.ag.timeline.business.model.User;
import org.ag.timeline.business.service.iface.TimelineService;
import org.ag.timeline.business.service.impl.TimelineServiceImpl;
import org.ag.timeline.common.TimelineConstants;
import org.ag.timeline.presentation.transferobject.common.CodeValue;
import org.ag.timeline.presentation.transferobject.reply.ActivityReply;
import org.ag.timeline.presentation.transferobject.reply.ProjectReply;
import org.ag.timeline.presentation.transferobject.reply.UserSearchReply;
import org.ag.timeline.presentation.transferobject.search.ProjectSearchParameter;
import org.ag.timeline.presentation.transferobject.search.UserSearchParameter;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * Base action class.
 * 
 * @author Abhishek Gaurav
 */
public abstract class BaseAction implements Action, SessionAware {

	/**
	 * Session Map.
	 */
	private Map<String, Object> session = null;

	/**
	 * Business service instance.
	 */
	protected final TimelineService service = new TimelineServiceImpl();

	/**
	 * Returns current session user.
	 * 
	 * @return {@link User}.
	 */
	protected User getSessionUser() {
		return (User) this.session.get(TimelineConstants.SessionAttribute.SESSION_USER.getText());
	}

	/**
	 * Getter for sessionUserId.
	 * 
	 * @return the sessionUserId
	 */
	protected long getSessionUserId() {
		long retVal = 0;
		User user = getSessionUser();

		if (user != null) {
			retVal = user.getId();
		}
		return retVal;
	}

	protected boolean isSessionUserAdmin() {
		boolean retVal = false;
		User user = getSessionUser();

		if (user != null) {
			retVal = user.isAdmin();
		}
		return retVal;
	}

	protected void setSessionUser(User user) {
		this.setSessionData(TimelineConstants.SessionAttribute.SESSION_USER.getText(), user);
	}

	/**
	 * Common method for storing data in session.
	 * 
	 * @param name String name of attribute to be stored.
	 * @param data Object attribute data to be stored.
	 */
	protected void setSessionData(final String name, final Object data) {
		if (this.session != null) {
			this.session.put(name, data);
		}
	}

	/**
	 * Common method for fetching data from session.
	 * 
	 * @param name String name of attribute to be fetched.
	 * @return Object attribute data fetched from session, null if not found.
	 */
	protected Object getSessionData(final String name) {
		Object data = null;

		if (this.session != null) {
			data = this.session.get(name);
		}

		return data;
	}

	protected void clearSession() {
		ActionContext.getContext().getSession().clear();
	}

	protected void refreshSessionData() throws TimelineException {

		// get projects
		ProjectSearchParameter projectSearchParameter = new ProjectSearchParameter();
//		projectSearchParameter.setSearchActiveProjects(Boolean.TRUE);
		projectSearchParameter.setEntityStatus(TimelineConstants.EntityStatus.ACTIVE);
		ProjectReply projectReply = service.searchProjects(projectSearchParameter);

		// get activities
		ActivityReply activityReply = service.searchActivities(null);

		// get users
		UserSearchParameter userSearchParameter = new UserSearchParameter();
		userSearchParameter.setOnlyActive(Boolean.TRUE);
		UserSearchReply searchReply = service.searchUsers(userSearchParameter);

		// get data types
		List<CodeValue> typeList = new ArrayList<CodeValue>();

		for (TimelineConstants.AuditDataType type : TimelineConstants.AuditDataType.values()) {
			typeList.add(new CodeValue(type.getTypeId(), type.getTypeText()));
		}

		// get operation types
		List<CodeValue> operationTypeList = new ArrayList<CodeValue>();

		for (TimelineConstants.EntityOperation type : TimelineConstants.EntityOperation.values()) {
			operationTypeList.add(new CodeValue(type.getOpCode(), type.getOpText()));
		}

		// set data in session
		setSessionData(TimelineConstants.SessionAttribute.PROJECT_LIST.getText(), projectReply);
		setSessionData(TimelineConstants.SessionAttribute.ACTIVITY_LIST.getText(), activityReply);
		setSessionData(TimelineConstants.SessionAttribute.USER_LIST.getText(), searchReply);
		setSessionData(TimelineConstants.SessionAttribute.DATA_TYPE_LIST.getText(), typeList);
		setSessionData(TimelineConstants.SessionAttribute.OPERATION_TYPE_LIST.getText(), operationTypeList);

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	public void setSession(Map<String, Object> sessionMap) {
		this.session = sessionMap;
	}
}
