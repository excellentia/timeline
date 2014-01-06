/**
 * 
 */
package org.ag.timeline.business.service.iface;

import org.ag.timeline.application.exception.TimelineException;
import org.ag.timeline.presentation.transferobject.input.AuthenticationInput;
import org.ag.timeline.presentation.transferobject.input.CodeValueInput;
import org.ag.timeline.presentation.transferobject.input.ProjectEstimatesInput;
import org.ag.timeline.presentation.transferobject.input.ProjectInput;
import org.ag.timeline.presentation.transferobject.input.ProjectMetricsInput;
import org.ag.timeline.presentation.transferobject.input.StatusInput;
import org.ag.timeline.presentation.transferobject.input.TimeDataInput;
import org.ag.timeline.presentation.transferobject.input.UserInput;
import org.ag.timeline.presentation.transferobject.input.UserPreferencesInput;
import org.ag.timeline.presentation.transferobject.reply.ActivityReply;
import org.ag.timeline.presentation.transferobject.reply.AuditDataReply;
import org.ag.timeline.presentation.transferobject.reply.CodeValueListReply;
import org.ag.timeline.presentation.transferobject.reply.CodeValueReply;
import org.ag.timeline.presentation.transferobject.reply.DetailedReportReply;
import org.ag.timeline.presentation.transferobject.reply.ProjectDetailMetricsReply;
import org.ag.timeline.presentation.transferobject.reply.ProjectEstimatesReply;
import org.ag.timeline.presentation.transferobject.reply.ProjectLevelMetricsReply;
import org.ag.timeline.presentation.transferobject.reply.ProjectReply;
import org.ag.timeline.presentation.transferobject.reply.SummaryReportReply;
import org.ag.timeline.presentation.transferobject.reply.TimeDataReply;
import org.ag.timeline.presentation.transferobject.reply.UserPreferenceReply;
import org.ag.timeline.presentation.transferobject.reply.UserPreferenceSearchReply;
import org.ag.timeline.presentation.transferobject.reply.UserReply;
import org.ag.timeline.presentation.transferobject.reply.UserSearchReply;
import org.ag.timeline.presentation.transferobject.reply.WeekReply;
import org.ag.timeline.presentation.transferobject.reply.WeeklyUserReply;
import org.ag.timeline.presentation.transferobject.search.ActivitySearchParameter;
import org.ag.timeline.presentation.transferobject.search.AuditDataSearchParameters;
import org.ag.timeline.presentation.transferobject.search.ProjectDetailMetricsSearchParameters;
import org.ag.timeline.presentation.transferobject.search.ProjectMetricsSearchParameters;
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
public interface TimelineService {

	public UserReply autheticateUser(AuthenticationInput input) throws TimelineException;
	public UserReply resetUserCredentials(CodeValueInput input) throws TimelineException;
	
	public CodeValueReply createProject(ProjectInput input) throws TimelineException;
	public CodeValueReply createActivity(CodeValueInput input) throws TimelineException;
	public UserReply createUser(UserInput input) throws TimelineException;
	public CodeValueReply createTimeData(final TimeDataInput myTimeData) throws TimelineException;
	public CodeValueReply createProjectDetailMetrics(final ProjectMetricsInput input) throws TimelineException;
	
	public CodeValueReply deleteProject(CodeValueInput input) throws TimelineException;
	public CodeValueReply deleteActivity(CodeValueInput input) throws TimelineException;
	public UserReply deleteUser(CodeValueInput input) throws TimelineException;
	public CodeValueReply deleteTimeData(CodeValueInput input) throws TimelineException;
	public CodeValueReply deleteUserPreferences(CodeValueInput input) throws TimelineException;
	public CodeValueReply deleteProjectMetrics(final CodeValueInput input) throws TimelineException;
	public CodeValueReply deleteProjectDetailMetrics(final CodeValueInput input) throws TimelineException;
		
	public CodeValueReply modifyActivity(CodeValueInput input) throws TimelineException;
	public CodeValueReply modifyProject(ProjectInput input) throws TimelineException;
	public UserReply modifyUser(final UserInput userInput) throws TimelineException;
	public CodeValueReply modifyTimeData(final TimeDataInput myTimeData) throws TimelineException;
	public CodeValueReply modifyStatus(final StatusInput input) throws TimelineException;
	public CodeValueReply modifyProjectDetailMetrics(final ProjectMetricsInput input) throws TimelineException;
	
	public UserPreferenceReply saveUserPreferences(final UserPreferencesInput input) throws TimelineException;
	public CodeValueReply saveProjectEstimates(final ProjectEstimatesInput input) throws TimelineException;
		
	public ProjectReply searchProjects(final ProjectSearchParameter searchParameters) throws TimelineException;
	public ActivityReply searchActivities(final ActivitySearchParameter searchParameters) throws TimelineException;
	public UserSearchReply searchUsers(final UserSearchParameter searchParameters) throws TimelineException;
	public UserPreferenceSearchReply searchUserPreferences(final UserPreferenceSearchParameter searchParameters ) throws TimelineException;
	public WeekReply searchWeeks(final WeekSearchParameter searchParameters) throws TimelineException;
	public TimeDataReply searchTimeData(final TimeDataSearchParameters searchParameters) throws TimelineException;
	public AuditDataReply searchAuditData(final AuditDataSearchParameters searchParameters) throws TimelineException;
	public WeeklyUserReply searchUsersWithoutEntries(final WeekSearchParameter searchParameters) throws TimelineException;
	public ProjectEstimatesReply searchProjectEstimates(final ProjectSearchParameter searchParameters) throws TimelineException;
	public CodeValueListReply searchTasks(final ProjectSearchParameter searchParameters) throws TimelineException;
	
	public SummaryReportReply getSummaryReport(final ReportSearchParameters searchParameters) throws TimelineException;
	public DetailedReportReply getDetailedReport(final ReportSearchParameters searchParameters) throws TimelineException;
	public ProjectLevelMetricsReply getProjectLevelMetricsReport(final ProjectMetricsSearchParameters searchParameters) throws TimelineException;
	public ProjectDetailMetricsReply getProjectDetailMetricsReport(final ProjectDetailMetricsSearchParameters searchParameters) throws TimelineException;

	public void systemManagement() throws TimelineException;

}
