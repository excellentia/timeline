package org.ag.timeline.application.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.ag.timeline.business.model.User;
import org.ag.timeline.common.TimelineConstants;

/**
 * Checks user access.
 * 
 * @author Abhishek Gaurav
 */
public class AccessFilter implements Filter {

	private final String initParamName = "login-page";
	private String initParamValue = null;
	private FilterConfig filterConfig;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession httpSession = httpServletRequest.getSession(false);
		User user = (User) httpSession.getAttribute(TimelineConstants.SessionAttribute.SESSION_USER.getText());

		if (user == null) {
			this.filterConfig.getServletContext().getRequestDispatcher(this.initParamValue).forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		if (config != null) {
			this.filterConfig = config;
			initParamValue = config.getInitParameter(initParamName);
		}
	}

}
