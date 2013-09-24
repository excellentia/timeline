package org.ag.timeline.application.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.ag.timeline.application.context.RequestContext;
import org.ag.timeline.application.context.TimelineContext;
import org.ag.timeline.business.model.User;
import org.ag.timeline.common.TextHelper;
import org.ag.timeline.common.TimelineConstants;

/**
 * Listener for Requests created by Timeline application users.
 * 
 * @author Abhishek Gaurav
 */
public class TimelineRequestListener implements ServletRequestListener {

	private static final String ACCEPTED_METHOD_NAME = TimelineConstants.RequestType.POST.getText();

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletRequestListener#requestDestroyed(javax.servlet.
	 * ServletRequestEvent)
	 */
	public void requestDestroyed(ServletRequestEvent event) {
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();

		if (ACCEPTED_METHOD_NAME.equalsIgnoreCase(request.getMethod())) {
			TextHelper.logMessage("Destroying POST Request : " + request.getRequestURL());
			RequestContext.destroy();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletRequestListener#requestInitialized(javax.servlet
	 * .ServletRequestEvent)
	 */
	public void requestInitialized(ServletRequestEvent event) {
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();

		if (ACCEPTED_METHOD_NAME.equalsIgnoreCase(request.getMethod())) {

			TextHelper.logMessage("Initialising POST Request : " + request.getRequestURL());
			HttpSession session = request.getSession(false);

			if (session != null) {
				User user = (User) session.getAttribute(TimelineConstants.SessionAttribute.SESSION_USER.getText());

				if (user != null) {
					TimelineContext context = new TimelineContext(user.getId());
					RequestContext.setTimelineContext(context);
				}
			}
		}

	}

}
