package org.ag.timeline.presentation.action;

import org.ag.timeline.common.TextHelper;

public abstract class SecureBaseAction extends BaseAction {

	public abstract String secureExecute() throws Exception;

	public final String execute() throws Exception {

		long time = System.nanoTime();

		String forward = ERROR;

		if (getSessionUser() != null) {

			// refresh the session data
			refreshSessionData();

			// call the real implementation
			forward = secureExecute();
		}

		if (ERROR.equalsIgnoreCase(forward)) {
			super.clearSession();
		}

		TextHelper.logMessage("SecureBaseAction.execute()", time);

		return forward;
	}
}
