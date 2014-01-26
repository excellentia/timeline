package org.ag.timeline.presentation.action;

import org.ag.timeline.common.TextHelper;

/**
 * Handles logout functionality.
 * 
 * @author Abhishek Gaurav
 */
public class LogoutAction extends BaseAction {

	public String execute() throws Exception {
		
		long time = System.nanoTime();

		// clear the session
		super.clearSession();

		TextHelper.logMessage("LogoutAction.execute()", time);
		
		return SUCCESS;
	}
}
