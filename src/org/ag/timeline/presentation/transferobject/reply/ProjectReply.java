/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

import java.util.ArrayList;
import java.util.List;

import org.ag.timeline.business.model.Project;

/**
 * Wrapper for search results for {@link Project}.
 * 
 * @author Abhishek Gaurav
 */
public class ProjectReply extends BusinessReply {

	private List<ProjectData> projects = null;

	/**
	 * Constructor.
	 */
	public ProjectReply() {
		this.projects = new ArrayList<ProjectData>();
	}

	/**
	 * @param message
	 * @param isMsgError
	 */
	public ProjectReply(String message, boolean isMsgError) {
		super(message, isMsgError);
	}

	/**
	 * Getter for projects.
	 * 
	 * @return the projects
	 */
	public List<ProjectData> getProjects() {
		List<ProjectData> list = new ArrayList<ProjectData>();
		list.addAll(projects);
		return list;
	}

	/**
	 * Setter for projects.
	 * 
	 * @param projects the projects to set
	 */
	public void addProject(ProjectData projects) {
		this.projects.add(projects);
	}

}
