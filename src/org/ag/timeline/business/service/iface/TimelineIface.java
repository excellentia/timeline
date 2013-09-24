/**
 * 
 */
package org.ag.timeline.business.service.iface;

import org.ag.timeline.application.exception.TimelineException;
import org.ag.timeline.presentation.transferobject.input.AuthenticationInput;
import org.ag.timeline.presentation.transferobject.input.CodeValueInput;
import org.ag.timeline.presentation.transferobject.input.ProjectInput;
import org.ag.timeline.presentation.transferobject.input.TimeDataInput;
import org.ag.timeline.presentation.transferobject.input.UserInput;
import org.ag.timeline.presentation.transferobject.input.UserPreferencesInput;
import org.ag.timeline.presentation.transferobject.reply.ActivityReply;
import org.ag.timeline.presentation.transferobject.reply.AuditDataReply;
import org.ag.timeline.presentation.transferobject.reply.CodeValueReply;
import org.ag.timeline.presentation.transferobject.reply.ProjectReply;
import org.ag.timeline.presentation.transferobject.reply.ReportDataReply;
import org.ag.timeline.presentation.transferobject.reply.TimeDataReply;
import org.ag.timeline.presentation.transferobject.reply.UserPreferenceReply;
import org.ag.timeline.presentation.transferobject.reply.UserPreferenceSearchReply;
import org.ag.timeline.presentation.transferobject.reply.UserReply;
import org.ag.timeline.presentation.transferobject.reply.UserSearchReply;
import org.ag.timeline.presentation.transferobject.reply.WeekReply;
import org.ag.timeline.presentation.transferobject.search.ActivitySearchParameter;
import org.ag.timeline.presentation.transferobject.search.AuditDataSearchParameters;
import org.ag.timeline.presentation.transferobject.search.ProjectSearchParameter;
import org.ag.timeline.presentation.transferobject.search.ReportSearchParameters;
import org.ag.timeline.presentation.transferobject.search.TimeDataSearchParameters;
import org.ag.timeline.presentation.transferobject.search.UserPreferenceSearchParameter;
import org.ag.timeline.presentation.transferobject.search.UserSearchParameter;
import org.ag.timeline.presentation.transferobject.search.WeekSearchParameter;

/**
 * Service Interface.
 * 
 * @author Abhishek Gaurav
 */
public interface TimelineIface {

	public UserReply autheticateUser(AuthenticationInput input) throws TimelineException;
	public UserReply resetUserCredentials(CodeValueInput input) throws TimelineException;
	public void systemManagement() throws TimelineException;

	
	public CodeValueReply createProject(ProjectInput input) throws TimelineException;
	public CodeValueReply createActivity(CodeValueInput input) throws TimelineException;
	public UserReply createUser(UserInput input) throws TimelineException;
	public CodeValueReply createTimeData(final TimeDataInput myTimeData) throws TimelineException;
	
	public CodeValueReply deleteProject(CodeValueInput input) throws TimelineException;
	public CodeValueReply deleteActivity(CodeValueInput input) throws TimelineException;
	public UserReply deleteUser(CodeValueInput input) throws TimelineException;
	public CodeValueReply deleteTimeData(CodeValueInput input) throws TimelineException;
	public CodeValueReply deleteUserPreferences(CodeValueInput input) throws TimelineException;

	
	public CodeValueReply modifyActivity(CodeValueInput input) throws TimelineException;
	public CodeValueReply modifyProject(ProjectInput input) throws TimelineException;
	public UserReply modifyUser(final UserInput userInput) throws TimelineException;
	public CodeValueReply modifyTimeData(final TimeDataInput myTimeData) throws TimelineException;
	public UserPreferenceReply saveUserPreferences(final UserPreferencesInput input) throws TimelineException;

	
	public ProjectReply searchProjects(final ProjectSearchParameter searchParameters) throws TimelineException;
	public ActivityReply searchActivities(final ActivitySearchParameter searchParameters) throws TimelineException;
	public UserSearchReply searchUsers(final UserSearchParameter searchParameters) throws TimelineException;
	public UserPreferenceSearchReply searchUserPreferences(final UserPreferenceSearchParameter searchParameters ) throws TimelineException;
	public WeekReply searchWeeks(final WeekSearchParameter searchParameters) throws TimelineException;
	public TimeDataReply searchTimeData(final TimeDataSearchParameters searchParameters) throws TimelineException;
	public ReportDataReply getReport(final ReportSearchParameters searchParameters) throws TimelineException;
	
	public AuditDataReply searchAuditData(final AuditDataSearchParameters searchParameters) throws TimelineException;

}
