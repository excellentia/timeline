package org.ag.timeline.presentation.action;

import org.ag.timeline.presentation.transferobject.reply.ActivityReply;
import org.ag.timeline.presentation.transferobject.reply.ProjectReply;
import org.ag.timeline.presentation.transferobject.reply.UserSearchReply;

/**
 * Admin related functionality.
 * 
 * @author Abhishek Gaurav
 */
public class AdminAction extends SecureBaseAction {

	private ProjectReply projectReply = null;

	private ActivityReply activityReply = null;

	private UserSearchReply userReply = null;

	public String secureExecute() throws Exception {
		String forward = ERROR;

		if (super.isSessionUserAdmin()) {

			// fetch the projects
			projectReply = service.searchProjects(null);

			// fetch the activities
			activityReply = service.searchActivities(null);

			// fetch the users
			userReply = service.searchUsers(null);

			forward = SUCCESS;
		}

		return forward;
	}

	/**
	 * Getter for projectReply.
	 * 
	 * @return the projectReply
	 */
	public ProjectReply getProjectReply() {
		return projectReply;
	}

	/**
	 * Setter for projectReply.
	 * 
	 * @param projectReply the projectReply to set
	 */
	public void setProjectReply(ProjectReply projectReply) {
		this.projectReply = projectReply;
	}

	/**
	 * Getter for activityReply.
	 * 
	 * @return the activityReply
	 */
	public ActivityReply getActivityReply() {
		return activityReply;
	}

	/**
	 * Setter for activityReply.
	 * 
	 * @param activityReply the activityReply to set
	 */
	public void setActivityReply(ActivityReply activityReply) {
		this.activityReply = activityReply;
	}

	/**
	 * Getter for userReply.
	 * 
	 * @return the userReply
	 */
	public UserSearchReply getUserReply() {
		return userReply;
	}

	/**
	 * Setter for userReply.
	 * 
	 * @param userReply the userReply to set
	 */
	public void setUserReply(UserSearchReply userReply) {
		this.userReply = userReply;
	}
}
