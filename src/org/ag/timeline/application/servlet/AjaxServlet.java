package org.ag.timeline.application.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ag.timeline.application.exception.TimelineException;
import org.ag.timeline.business.model.User;
import org.ag.timeline.business.model.UserPreferences;
import org.ag.timeline.business.service.iface.TimelineService;
import org.ag.timeline.business.service.impl.TimelineServiceImpl;
import org.ag.timeline.common.TextHelper;
import org.ag.timeline.common.TimelineConstants;
import org.ag.timeline.presentation.transferobject.common.CodeValue;
import org.ag.timeline.presentation.transferobject.common.CodeValueStatus;
import org.ag.timeline.presentation.transferobject.input.CodeValueInput;
import org.ag.timeline.presentation.transferobject.input.ProjectEstimatesInput;
import org.ag.timeline.presentation.transferobject.input.ProjectInput;
import org.ag.timeline.presentation.transferobject.input.ProjectMetricsInput;
import org.ag.timeline.presentation.transferobject.input.StatusInput;
import org.ag.timeline.presentation.transferobject.input.TaskInput;
import org.ag.timeline.presentation.transferobject.input.TimeDataInput;
import org.ag.timeline.presentation.transferobject.input.UserInput;
import org.ag.timeline.presentation.transferobject.input.UserPreferencesInput;
import org.ag.timeline.presentation.transferobject.reply.ActivityReply;
import org.ag.timeline.presentation.transferobject.reply.BusinessReply;
import org.ag.timeline.presentation.transferobject.reply.CodeValueListReply;
import org.ag.timeline.presentation.transferobject.reply.CodeValueReply;
import org.ag.timeline.presentation.transferobject.reply.CodeValueStatusListReply;
import org.ag.timeline.presentation.transferobject.reply.ProjectData;
import org.ag.timeline.presentation.transferobject.reply.ProjectReply;
import org.ag.timeline.presentation.transferobject.reply.TaskReply;
import org.ag.timeline.presentation.transferobject.reply.TimeDataReply;
import org.ag.timeline.presentation.transferobject.reply.TimeDataRow;
import org.ag.timeline.presentation.transferobject.reply.UserPreferenceReply;
import org.ag.timeline.presentation.transferobject.reply.UserReply;
import org.ag.timeline.presentation.transferobject.reply.UserSearchReply;
import org.ag.timeline.presentation.transferobject.reply.WeeklyUserReply;
import org.ag.timeline.presentation.transferobject.reply.metrics.ProjectEstimateData;
import org.ag.timeline.presentation.transferobject.reply.metrics.ProjectEstimatesReply;
import org.ag.timeline.presentation.transferobject.reply.report.DetailedReportReply;
import org.ag.timeline.presentation.transferobject.reply.report.DetailedReportRow;
import org.ag.timeline.presentation.transferobject.search.ActivitySearchParameter;
import org.ag.timeline.presentation.transferobject.search.ProjectSearchParameter;
import org.ag.timeline.presentation.transferobject.search.ReportSearchParameters;
import org.ag.timeline.presentation.transferobject.search.TaskSearchParameter;
import org.ag.timeline.presentation.transferobject.search.TimeDataSearchParameters;
import org.ag.timeline.presentation.transferobject.search.UserSearchParameter;
import org.ag.timeline.presentation.transferobject.search.WeekSearchParameter;

/**
 * Ajax Application controller servlet.
 * 
 * @author Abhishek Gaurav
 */
public class AjaxServlet extends HttpServlet {

	/**
	 * Serial version U-ID.
	 */
	private static final long serialVersionUID = 8277709565473674499L;

	/**
	 * Service interface to be used for backend calls.
	 */
	private static final TimelineService SERVICE = new TimelineServiceImpl();

	/**
	 * Response type JSON.
	 */
	private static final String RESPONSE_TYPE = "text/json";

	private User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(TimelineConstants.SessionAttribute.SESSION_USER.getText());
	}

	private String getFormattedTaskText(final String text) {
		String value = TextHelper.trimToNull(text);

		if (value != null) {
			value = value.toUpperCase();
		}

		return value;
	}

	private String getJSON(final BusinessReply reply) {
		String json = null;

		if (reply != null) {
			StringBuilder builder = new StringBuilder("{");

			if (reply instanceof ProjectReply) {
				ProjectReply projectReply = (ProjectReply) reply;

				if ((projectReply != null) && (!projectReply.isMsgError())) {
					builder.append("\"projects\" : [");
					for (ProjectData project : projectReply.getProjects()) {
						builder.append("{\"code\" : \"").append(project.getCode()).append("\", \"value\" : \"")
								.append(project.getValue());
						builder.append("\", \"leadname\" : \"").append(project.getLeadName()).append("\"},");
					}

					builder = new StringBuilder(builder.substring(0, builder.length() - 1)).append("]");

				} else {
					builder.append("\"error\" : \"No projects available.\"");
				}
			} else if (reply instanceof ActivityReply) {
				ActivityReply activityReply = (ActivityReply) reply;

				if ((activityReply != null) && (activityReply.getProjectIds() != null)) {

					List<Long> projectIds = activityReply.getProjectIds();
					List<CodeValueStatus> activities = new ArrayList<CodeValueStatus>();

					for (long projId : projectIds) {
						activities.addAll(activityReply.getProjectActivitiesById(projId));
					}

					if (activities != null) {

						builder.append("\"activities\" : [");

						for (CodeValueStatus activity : activities) {
							builder.append("{\"code\" : \"").append(activity.getCode());
							builder.append("\",");
							builder.append("\"value\" : \"").append(activity.getValue());
							builder.append("\",");
							builder.append("\"status\" : \"").append(activity.isStatus());
							builder.append("\"},");
						}

						builder = new StringBuilder(builder.substring(0, builder.length() - 1)).append("]");
					}

				} else {
					builder.append("\"error\" : \"No Activities\"");
				}
			} else if (reply instanceof CodeValueReply) {
				CodeValueReply codeValueReply = (CodeValueReply) reply;

				if ((codeValueReply != null) && (codeValueReply.getCodeValue() != null)) {
					CodeValue codeValue = codeValueReply.getCodeValue();
					builder.append("\"code\" : \"").append(codeValue.getCode()).append("\",");
					builder.append("\"value\" : \"").append(codeValue.getValue()).append("\"");
				} else {
					builder.append("\"error\" : \"" + codeValueReply.getMessage() + "\"");
				}
			} else if (reply instanceof UserSearchReply) {
				UserSearchReply userSearchReply = (UserSearchReply) reply;

				if ((userSearchReply != null) && (userSearchReply.getUsers() != null)
						&& (userSearchReply.getUsers().size() > 0)) {
					builder.append("\"admins\" : [");
					for (User user : userSearchReply.getUsers()) {
						builder.append("{\"code\" : \"").append(user.getId()).append("\",");
						builder.append("\"value\" : \"").append(user.getUserName()).append("\"},");
					}

					builder = new StringBuilder(builder.substring(0, builder.length() - 1)).append("]");
				} else {
					builder.append("\"error\" : \"No Admins\"");
				}
			} else if (reply instanceof UserReply) {
				UserReply userReply = (UserReply) reply;

				if ((userReply != null) && (userReply.getUser() != null)) {

					User user = userReply.getUser();
					builder.append("\"id\" : \"").append(user.getId()).append("\",");
					builder.append("\"firstName\" : \"").append(user.getFirstName()).append("\",");
					builder.append("\"lastName\" : \"").append(user.getLastName()).append("\",");
					builder.append("\"userId\" : \"").append(user.getUserId()).append("\",");
					builder.append("\"userName\" : \"").append(user.getUserName()).append("\",");
					builder.append("\"admin\" : \"").append(user.isAdmin()).append("\",");
					builder.append("\"password\" : \"").append(user.getPassword()).append("\",");
					builder.append("\"message\" : \"").append(userReply.getMessage()).append("\"");

				} else {
					builder.append("\"error\" : \"" + userReply.getMessage() + "\"");
				}
			} else if (reply instanceof UserPreferenceReply) {
				UserPreferenceReply preferenceReply = (UserPreferenceReply) reply;

				if ((preferenceReply != null) && (preferenceReply.getPreference() != null)) {

					UserPreferences preferences = preferenceReply.getPreference();
					builder.append("\"question\" : \"").append(preferences.getQuestion()).append("\",");
					builder.append("\"answer\" : \"").append(preferences.getAnswer()).append("\",");
					builder.append("\"email\" : \"").append(preferences.getEmail()).append("\",");
					builder.append("\"message\" : \"").append(preferenceReply.getMessage()).append("\"");

				} else {
					builder.append("\"error\" : \"" + preferenceReply.getMessage() + "\"");
				}
			} else if (reply instanceof DetailedReportReply) {

				DetailedReportReply detailedReportReply = (DetailedReportReply) reply;

				if ((detailedReportReply != null) && (detailedReportReply.isRowsPresent())) {

					builder.append("\"totalSum\" : \"" + detailedReportReply.getTotal() + "\",");
					builder.append("\"details\" : [");

					for (DetailedReportRow row : detailedReportReply.getRowList()) {
						builder.append("{\"weekStartDate\" : \"")
								.append(TextHelper.getDisplayWeekDay(row.getWeekStartDate())).append("\",");
						builder.append("\"weekEndDate\" : \"")
								.append(TextHelper.getDisplayWeekDay(row.getWeekEndDate())).append("\",");
						builder.append("\"activityName\" : \"").append(row.getActivityName()).append("\",");
						builder.append("\"weeklySum\" : \"").append(row.getWeeklySum()).append("\"},");
					}

					builder = new StringBuilder(builder.substring(0, builder.length() - 1)).append("]");

				} else {
					builder.append("\"error\" : \"No Details Available.\"");
				}

			} else if (reply instanceof TimeDataReply) {
				TimeDataReply timeDataReply = (TimeDataReply) reply;

				if ((timeDataReply != null) && (timeDataReply.isEntryPresent())) {

					List<Long> weekIdList = timeDataReply.getWeekIds();
					builder.append("\"weeklyData\" : [");

					for (long weekId : weekIdList) {
						builder.append("{");
						builder.append("\"weekId\" : \"").append(weekId).append("\",");
						builder.append("\"weekName\" : \"").append(timeDataReply.getWeekLabel(weekId)).append("\",");

						// populate week Project info
						{
							builder.append("\"weekDayLabels\" : [");

							for (String label : timeDataReply.getDayLabels(weekId)) {
								builder.append("{");
								builder.append("\"label\" : \"").append(label).append("\"");
								builder.append("},");
							}

							// remove trailing comma
							builder = new StringBuilder(builder.substring(0, builder.length() - 1));
							builder.append("],");
						}

						// populate entry data info
						{
							builder.append("\"entryData\" : [");

							for (TimeDataRow entry : timeDataReply.getEntriesForWeek(weekId)) {
								builder.append("{");
								builder.append("\"entryId\" : \"").append(entry.getId()).append("\",");
								builder.append("\"userName\" : \"").append(entry.getUserFullName()).append("\",");
								builder.append("\"projectName\" : \"").append(entry.getProjectName()).append("\",");
								builder.append("\"projectId\" : \"").append(entry.getProjectId()).append("\",");
								builder.append("\"activityName\" : \"").append(entry.getActivityName()).append("\",");
								builder.append("\"activityId\" : \"").append(entry.getActivityId()).append("\",");
								builder.append("\"leadName\" : \"").append(entry.getLeadName()).append("\",");
								builder.append("\"taskId\" : \"").append(entry.getTaskId()).append("\",");
								builder.append("\"taskName\" : \"").append(entry.getTaskName()).append("\",");
								builder.append("\"startDate\" : \"").append(entry.getFormattedStartDate())
										.append("\",");

								// time data
								builder.append("\"day1\" : \"").append(entry.getDay_1_time()).append("\",");
								builder.append("\"day2\" : \"").append(entry.getDay_2_time()).append("\",");
								builder.append("\"day3\" : \"").append(entry.getDay_3_time()).append("\",");
								builder.append("\"day4\" : \"").append(entry.getDay_4_time()).append("\",");
								builder.append("\"day5\" : \"").append(entry.getDay_5_time()).append("\",");
								builder.append("\"day6\" : \"").append(entry.getDay_6_time()).append("\",");
								builder.append("\"day7\" : \"").append(entry.getDay_7_time()).append("\",");

								// sum total
								builder.append("\"total\" : \"").append(entry.getWeeklySum()).append("\"");
								builder.append("},");
							}

							// remove trailing comma
							builder = new StringBuilder(builder.substring(0, builder.length() - 1));
							builder.append("]");
						}

						builder.append("},");
					}

					// remove trailing comma
					builder = new StringBuilder(builder.substring(0, builder.length() - 1)).append("]");

				} else {
					builder.append("\"error\" : \"No Entries Found.\"");
				}
			} else if (reply instanceof WeeklyUserReply) {
				WeeklyUserReply userReply = (WeeklyUserReply) reply;

				if ((userReply != null) && (userReply.getUserCount() > 0)) {

					List<Long> weekIdList = userReply.getWeekIdList();
					builder.append("\"weeklyUserData\" : [");

					for (long weekId : weekIdList) {
						builder.append("{");
						builder.append("\"weekName\" : \"").append(userReply.getWeekLabel(weekId)).append("\",");

						// populate entry data info
						{
							builder.append("\"users\" : [");

							for (User user : userReply.getWeeklyUsers(weekId)) {
								builder.append("{");
								builder.append("\"userName\" : \"").append(user.getUserName()).append("\",");
								builder.append("\"email\" : \"").append(userReply.getUserEmail(user.getId()))
										.append("\"");
								builder.append("},");
							}

							// remove trailing comma
							builder = new StringBuilder(builder.substring(0, builder.length() - 1));
							builder.append("]");
						}

						builder.append("},");
					}

					// remove trailing comma
					builder = new StringBuilder(builder.substring(0, builder.length() - 1)).append("]");

				} else {
					builder.append("\"error\" : \"No Results Found.\"");
				}
			} else if (reply instanceof ProjectEstimatesReply) {
				ProjectEstimatesReply estimatesReply = (ProjectEstimatesReply) reply;

				if ((estimatesReply != null) && (estimatesReply.getEstimates() != null)) {

					ProjectEstimateData estimate = estimatesReply.getEstimates().get(0);
					builder.append("\"id\" : \"").append(estimate.getProjectData().getCode()).append("\",");
					builder.append("\"bac\" : \"").append(estimate.getBudgetAtCompletion()).append("\",");
					builder.append("\"startDate\" : \"").append(TextHelper.getDateAsString(estimate.getStartDate()))
							.append("\",");
					builder.append("\"endDate\" : \"").append(TextHelper.getDateAsString(estimate.getEndDate()))
							.append("\"");
				} else {
					builder.append("\"error\" : \"" + estimatesReply.getMessage() + "\"");
				}
			} else if (reply instanceof CodeValueListReply) {

				CodeValueListReply codeValueListReply = (CodeValueListReply) reply;

				if ((codeValueListReply != null) && (codeValueListReply.getCodeValueList() != null)
						&& (codeValueListReply.getCodeValueList().size() > 0)) {

					List<CodeValue> taskList = codeValueListReply.getCodeValueList();

					builder.append("\"elements\" : [");

					for (CodeValue task : taskList) {
						builder.append("{\"code\" : \"").append(task.getCode()).append("\", \"value\" : \"")
								.append(task.getValue());
						builder.append("\"},");
					}

					builder = new StringBuilder(builder.substring(0, builder.length() - 1)).append("]");

				} else {
					builder.append("\"error\" : \"" + codeValueListReply.getMessage() + "\"");
				}
			} else if (reply instanceof CodeValueStatusListReply) {

				CodeValueStatusListReply statusReply = (CodeValueStatusListReply) reply;

				if ((statusReply != null) && (statusReply.getCodeValueStatusList() != null)
						&& (statusReply.getCodeValueStatusList().size() > 0)) {

					List<CodeValueStatus> list = statusReply.getCodeValueStatusList();

					builder.append("\"elements\" : [");

					for (CodeValueStatus entry : list) {
						builder.append("{\"code\" : \"").append(entry.getCode()).append("\", \"value\" : \"")
								.append(entry.getValue()).append("\", \"status\" : \"").append(entry.isStatus());
						builder.append("\"},");
					}

					builder = new StringBuilder(builder.substring(0, builder.length() - 1)).append("]");

				} else {
					builder.append("\"error\" : \"" + statusReply.getMessage() + "\"");
				}
			} else if (reply instanceof TaskReply) {

				TaskReply taskReply = (TaskReply) reply;

				if ((taskReply != null) && (taskReply.hasTasks())) {

					builder.append("\"projects\" : [");

					for (long projectId : taskReply.getProjectIds()) {
						builder.append("{");
						builder.append("\"projectId\" : \"").append(projectId).append("\",");
						builder.append("\"projectName\" : \"").append(taskReply.getProjectName(projectId))
								.append("\",");

						// populate activity info
						{
							builder.append("\"activities\" : [");

							for (long activityId : taskReply.getProjectActivityIds(projectId)) {
								builder.append("{");
								builder.append("\"activityId\" : \"").append(activityId).append("\",");
								builder.append("\"activityName\" : \"").append(taskReply.getActivityName(activityId))
										.append("\",");

								// populate task info
								{
									builder.append("\"tasks\" : [");
									long taskId = 0;

									for (CodeValueStatus task : taskReply.getActivityTasks(activityId)) {

										taskId = task.getCode();

										builder.append("{");
										builder.append("\"taskId\" : \"").append(taskId).append("\",");
										// builder.append("\"taskUser\" : \"").append(taskReply.getTaskUserName(taskId)).append("\",");
										builder.append("\"taskName\" : \"").append(task.getValue()).append("\",");
										builder.append("\"active\" : \"").append(task.isStatus()).append("\",");
										builder.append("\"taskDescription\" : \"")
												.append(taskReply.getTaskDetail(taskId)).append("\"");
										builder.append("},");
									}

									// remove trailing comma
									builder = new StringBuilder(builder.substring(0, builder.length() - 1));
									builder.append("]");
								}

								builder.append("},");
							}

							// remove trailing comma
							builder = new StringBuilder(builder.substring(0, builder.length() - 1));
							builder.append("]");
						}

						builder.append("},");
					}

					// remove trailing comma
					builder = new StringBuilder(builder.substring(0, builder.length() - 1)).append("]");

				} else {
					builder.append("\"error\" : \"No Results Found.\"");
				}

			}

			builder.append("}");
			json = builder.toString();
		}

		return json;
	}

	private void handleInvalidRequest(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;

		try {
			String json = "{\"error\" : \"Invalid Operation Requested\"}";
			out = response.getWriter();
			out.println(json);
		} catch (Exception ex) {
			System.out.println("Error message" + ex.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	private long getLongRequestValue(TimelineConstants.AjaxRequestParam param, HttpServletRequest request) {
		return TextHelper.getLongValue(request.getParameter(param.getParamText()));
	}

	private double getDoubleRequestValue(TimelineConstants.AjaxRequestParam param, HttpServletRequest request) {
		return TextHelper.getDoubleValue(request.getParameter(param.getParamText()));
	}

	private String getStringRequestValue(TimelineConstants.AjaxRequestParam param, HttpServletRequest request) {
		return TextHelper.trimToNull(request.getParameter(param.getParamText()));
	}

	private Date getDateRequestValue(TimelineConstants.AjaxRequestParam param, HttpServletRequest request) {
		return TextHelper.getValidDate(request.getParameter(param.getParamText()));
	}

	private Date getDateRequestValue(TimelineConstants.AjaxRequestParam param, final DateFormat format,
			HttpServletRequest request) {
		return TextHelper.getValidDate(request.getParameter(param.getParamText()), format);
	}

	private void getProjects(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;

		try {
			final long id = getLongRequestValue(TimelineConstants.AjaxRequestParam.id, request);
			final boolean searchActiveProjects = Boolean.valueOf(getStringRequestValue(
					TimelineConstants.AjaxRequestParam.status, request));
			final ProjectSearchParameter searchParameters = new ProjectSearchParameter();
			searchParameters.setProjectId(id);
			searchParameters.setSearchActiveProjects(searchActiveProjects);

			ProjectReply reply = SERVICE.searchProjects(searchParameters);
			String json = getJSON(reply);

			out = response.getWriter();
			out.println(json);

		} catch (Exception ex) {
			System.out.println("Error message" + ex.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	private void getActivities(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;

		try {
			final long id = getLongRequestValue(TimelineConstants.AjaxRequestParam.id, request);
			final ActivitySearchParameter searchParameters = new ActivitySearchParameter();

			if (id > 0) {
				searchParameters.setProjectId(id);
			}

			ActivityReply reply = SERVICE.searchActivities(searchParameters);
			String json = getJSON(reply);

			out = response.getWriter();
			out.println(json);

		} catch (Exception ex) {
			System.out.println("Error message" + ex.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	private void saveProject(HttpServletRequest request, HttpServletResponse response) {

		final long id = getLongRequestValue(TimelineConstants.AjaxRequestParam.id, request);
		final long copyProjectId = getLongRequestValue(TimelineConstants.AjaxRequestParam.refId, request);
		final String projectText = getStringRequestValue(TimelineConstants.AjaxRequestParam.text, request);
		PrintWriter out = null;

		if (projectText != null) {
			CodeValueReply reply = null;
			ProjectInput input = new ProjectInput();
			input.setNewLabelText(projectText);
			input.setProjectId(id);
			input.setCopyProjectId(copyProjectId);

			// set the status as Active by default
			input.setActive(Boolean.TRUE);

			try {
				if (id == 0) {
					reply = SERVICE.createProject(input);
				} else {
					reply = SERVICE.modifyProject(input);
				}

				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	private void saveEntityStatus(HttpServletRequest request, HttpServletResponse response,
			final TimelineConstants.StatusEntity entity) {

		long id = getLongRequestValue(TimelineConstants.AjaxRequestParam.id, request);
		final String statusText = getStringRequestValue(TimelineConstants.AjaxRequestParam.status, request);

		PrintWriter out = null;

		if (statusText != null) {

			final boolean status = TextHelper.getBooleanValue(statusText);

			CodeValueReply reply = null;
			StatusInput input = new StatusInput(id, entity, status);

			try {
				reply = SERVICE.modifyStatus(input);

				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	private void deleteProject(HttpServletRequest request, HttpServletResponse response) {

		long id = getLongRequestValue(TimelineConstants.AjaxRequestParam.id, request);

		if (id > 0) {
			PrintWriter out = null;
			CodeValueReply reply = null;
			CodeValueInput input = new CodeValueInput();
			input.setCodeValue(new CodeValue(id));

			try {
				reply = SERVICE.deleteProject(input);

				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	private void deleteActivity(HttpServletRequest request, HttpServletResponse response) {

		long id = getLongRequestValue(TimelineConstants.AjaxRequestParam.id, request);

		if (id > 0) {
			PrintWriter out = null;
			CodeValueReply reply = null;
			CodeValueInput input = new CodeValueInput();
			input.setCodeValue(new CodeValue(id));

			try {
				reply = SERVICE.deleteActivity(input);

				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	private void getLeads(HttpServletRequest request, HttpServletResponse response) {

		PrintWriter out = null;
		UserSearchReply reply = null;

		try {
			UserSearchParameter searchParameters = new UserSearchParameter();
			searchParameters.setOnlyAdmin(Boolean.TRUE);
			searchParameters.setOnlyActive(Boolean.TRUE);
			reply = SERVICE.searchUsers(searchParameters);

			String json = getJSON(reply);
			out = response.getWriter();
			out.println(json);

		} catch (TimelineException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	private void getTasks(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;

		try {
			final long projectId = getLongRequestValue(TimelineConstants.AjaxRequestParam.projectId, request);
			final long activityId = getLongRequestValue(TimelineConstants.AjaxRequestParam.activityId, request);
			final long taskId = getLongRequestValue(TimelineConstants.AjaxRequestParam.taskId, request);

			final TaskSearchParameter searchParameters = new TaskSearchParameter();
			searchParameters.setProjectId(projectId);
			searchParameters.setActivityId(activityId);
			searchParameters.setTaskId(taskId);

			// set search all
			if ((projectId == 0) && (activityId == 0) && (taskId == 0)) {
				searchParameters.setSearchAllTasks(Boolean.TRUE);
			}

			// User user = getSessionUser(request);
			//
			// if (!user.isAdmin()) {
			// searchParameters.setUserDbId(user.getId());
			// } else {
			// long userDbId =
			// getLongRequestValue(TimelineConstants.AjaxRequestParam.userDbId,
			// request);
			// searchParameters.setUserDbId(userDbId);
			// }

			TaskReply reply = SERVICE.searchTasks(searchParameters);
			String json = getJSON(reply);

			out = response.getWriter();
			out.println(json);

		} catch (Exception ex) {
			System.out.println("Error message" + ex.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	private void saveLead(HttpServletRequest request, HttpServletResponse response) {

		PrintWriter out = null;
		CodeValueReply reply = null;

		long projectId = getLongRequestValue(TimelineConstants.AjaxRequestParam.id, request);
		long leadId = getLongRequestValue(TimelineConstants.AjaxRequestParam.refId, request);

		try {
			// populate the data
			ProjectInput input = new ProjectInput();
			input.setLeadId(leadId);
			input.setProjectId(projectId);

			// call service
			reply = SERVICE.modifyProject(input);

			String json = getJSON(reply);
			out = response.getWriter();
			out.println(json);
		} catch (TimelineException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) {

		long id = getLongRequestValue(TimelineConstants.AjaxRequestParam.id, request);

		if (id > 0) {
			PrintWriter out = null;
			UserReply reply = null;
			CodeValueInput input = new CodeValueInput();
			{
				CodeValue codeValue = new CodeValue();
				codeValue.setCode(id);
				input.setCodeValue(codeValue);
			}

			try {
				reply = SERVICE.deleteUser(input);

				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	private void saveActivity(HttpServletRequest request, HttpServletResponse response) {

		long projId = getLongRequestValue(TimelineConstants.AjaxRequestParam.id, request);
		long actId = getLongRequestValue(TimelineConstants.AjaxRequestParam.refId, request);
		String text = getStringRequestValue(TimelineConstants.AjaxRequestParam.text, request);

		PrintWriter out = null;

		if (text != null) {
			CodeValueReply reply = null;
			CodeValueInput input = new CodeValueInput();

			try {
				if (actId <= 0) {
					input.setCodeValue(new CodeValue(projId, text));
					reply = SERVICE.createActivity(input);
				} else {
					input.setCodeValue(new CodeValue(actId, text));
					reply = SERVICE.modifyActivity(input);
				}

				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	private void modifyUser(HttpServletRequest request, HttpServletResponse response) {

		final long id = getLongRequestValue(TimelineConstants.AjaxRequestParam.id, request);

		if (id > 0) {
			PrintWriter out = null;
			UserReply reply = null;

			final String field = getStringRequestValue(TimelineConstants.AjaxRequestParam.field, request);
			final String value = getStringRequestValue(TimelineConstants.AjaxRequestParam.text, request);

			if ((field != null) && (value != null)) {
				UserInput input = new UserInput();

				input.setId(id);

				final TimelineConstants.UserDataFieldType type = TimelineConstants.UserDataFieldType.getType(field);
				input.setType(type);

				switch (type) {
					case FIRST_NAME:
						input.setFirstName(value);
						break;
					case LAST_NAME:
						input.setLastName(value);
						break;
					case USER_ID:
						input.setUserId(value);
						break;
					case PASSWORD:
						input.setPassword(value);
						break;
					case ADMIN:
						input.setAdmin(Boolean.valueOf(value));
						break;
					default:
						break;
				}

				try {
					reply = SERVICE.modifyUser(input);
					String json = getJSON(reply);
					out = response.getWriter();
					out.println(json);

				} catch (TimelineException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (out != null) {
						out.close();
					}
				}
			}
		}
	}

	private void modifyUserPreferences(HttpServletRequest request, HttpServletResponse response) {

		final long userDbId = getLongRequestValue(TimelineConstants.AjaxRequestParam.id, request);

		if (userDbId > 0) {
			PrintWriter out = null;
			UserPreferenceReply reply = null;

			final String field = getStringRequestValue(TimelineConstants.AjaxRequestParam.field, request);
			final String value = getStringRequestValue(TimelineConstants.AjaxRequestParam.text, request);

			if ((field != null) && (value != null)) {
				UserPreferencesInput input = new UserPreferencesInput();
				input.setUserDbId(userDbId);

				final TimelineConstants.UserPrefDataFieldType type = TimelineConstants.UserPrefDataFieldType
						.getType(field);
				input.setType(type);

				switch (type) {
					case QUESTION:
						input.setQuestion(value);
						break;
					case ANSWER:
						input.setAnswer(value);
						break;
					case EMAIL:
						input.setEmail(value);
						break;
					default:
						break;
				}

				try {

					reply = SERVICE.saveUserPreferences(input);
					String json = getJSON(reply);
					out = response.getWriter();
					out.println(json);

				} catch (TimelineException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (out != null) {
						out.close();
					}
				}
			}
		}

	}

	private void saveUser(HttpServletRequest request, HttpServletResponse response) {

		final long id = getLongRequestValue(TimelineConstants.AjaxRequestParam.id, request);
		PrintWriter out = null;
		UserReply reply = null;

		String firstName = TextHelper.trimToNull(request.getParameter(TimelineConstants.UserDataFieldType.FIRST_NAME
				.getParamText()));
		String lastName = TextHelper.trimToNull(request.getParameter(TimelineConstants.UserDataFieldType.LAST_NAME
				.getParamText()));
		String userID = TextHelper.trimToNull(request.getParameter(TimelineConstants.UserDataFieldType.USER_ID
				.getParamText()));
		boolean admin = Boolean.valueOf(TextHelper.trimToNull(request
				.getParameter(TimelineConstants.UserDataFieldType.ADMIN.getParamText())));

		UserInput input = new UserInput();

		input.setId(id);
		input.setFirstName(firstName);
		input.setLastName(lastName);
		input.setUserId(userID);
		input.setAdmin(admin);

		try {
			if (id == 0) {

				if ((id == 0) && (firstName != null) && (lastName != null) && (userID != null)) {
					reply = SERVICE.createUser(input);
				}

			} else {

				reply = SERVICE.modifyUser(input);

			}

			String json = getJSON(reply);
			out = response.getWriter();
			out.println(json);

		} catch (TimelineException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	private void resetUser(HttpServletRequest request, HttpServletResponse response) {

		long id = getLongRequestValue(TimelineConstants.AjaxRequestParam.id, request);
		PrintWriter out = null;

		if (id > 0) {
			UserReply reply = null;

			try {
				CodeValueInput input = new CodeValueInput();
				input.setCodeValue(new CodeValue(id));
				reply = SERVICE.resetUserCredentials(input);

				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	private void deleteTimeEntry(HttpServletRequest request, HttpServletResponse response) {

		long entryId = getLongRequestValue(TimelineConstants.AjaxRequestParam.entryId, request);

		if (entryId > 0) {

			PrintWriter out = null;

			try {

				CodeValueInput input = new CodeValueInput();
				input.setCodeValue(new CodeValue(entryId));
				CodeValueReply reply = SERVICE.deleteTimeData(input);
				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	private void saveTimeEntry(HttpServletRequest request, HttpServletResponse response) {

		long projectId = getLongRequestValue(TimelineConstants.AjaxRequestParam.projectId, request);
		long activityId = getLongRequestValue(TimelineConstants.AjaxRequestParam.activityId, request);
		long taskId = getLongRequestValue(TimelineConstants.AjaxRequestParam.taskId, request);

		if ((projectId > 0) && (activityId > 0) && (taskId > 0)) {

			long entryId = getLongRequestValue(TimelineConstants.AjaxRequestParam.entryId, request);

			long userId = 0;
			User user = getSessionUser(request);

			if (user != null) {
				userId = user.getId();
			}

			long proxiedUserDbId = getLongRequestValue(TimelineConstants.AjaxRequestParam.proxiedUserDbId, request);

			Date weekStartDate = getDateRequestValue(TimelineConstants.AjaxRequestParam.weekStartDate, request);

			double day1 = getDoubleRequestValue(TimelineConstants.AjaxRequestParam.day1, request);
			double day2 = getDoubleRequestValue(TimelineConstants.AjaxRequestParam.day2, request);
			double day3 = getDoubleRequestValue(TimelineConstants.AjaxRequestParam.day3, request);
			double day4 = getDoubleRequestValue(TimelineConstants.AjaxRequestParam.day4, request);
			double day5 = getDoubleRequestValue(TimelineConstants.AjaxRequestParam.day5, request);
			double day6 = getDoubleRequestValue(TimelineConstants.AjaxRequestParam.day6, request);
			double day7 = getDoubleRequestValue(TimelineConstants.AjaxRequestParam.day7, request);

			TimeDataInput myTimeData = new TimeDataInput();

			myTimeData.setActivityId(activityId);
			myTimeData.setTaskId(taskId);
			myTimeData.setDate(weekStartDate);
			myTimeData.setUserId(userId);
			myTimeData.setProxiedUserDbId(proxiedUserDbId);

			myTimeData.setDay_1_time(day1);
			myTimeData.setDay_2_time(day2);
			myTimeData.setDay_3_time(day3);
			myTimeData.setDay_4_time(day4);
			myTimeData.setDay_5_time(day5);
			myTimeData.setDay_6_time(day6);
			myTimeData.setDay_7_time(day7);
			myTimeData.setId(entryId);

			PrintWriter out = null;

			try {
				CodeValueReply reply = null;

				if (entryId > 0) {
					reply = SERVICE.modifyTimeData(myTimeData);
				} else {
					reply = SERVICE.createTimeData(myTimeData);
				}

				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	private void searchEntries(HttpServletRequest request, HttpServletResponse response) {

		long userDbId = 0;
		long startWeekNum = 0;
		long endWeekNum = 0;
		long startYear = 0;
		long endYear = 0;
		long projectId = 0;
		long activityId = 0;
		long taskId = 0;

		Date weekStartDate = null;
		Date weekEndDate = null;

		// populate search parameters
		{
			weekStartDate = getDateRequestValue(TimelineConstants.AjaxRequestParam.weekStartDate, request);
			weekEndDate = getDateRequestValue(TimelineConstants.AjaxRequestParam.weekEndDate, request);
			projectId = getLongRequestValue(TimelineConstants.AjaxRequestParam.projectId, request);
			activityId = getLongRequestValue(TimelineConstants.AjaxRequestParam.activityId, request);
			taskId = getLongRequestValue(TimelineConstants.AjaxRequestParam.taskId, request);
			userDbId = getLongRequestValue(TimelineConstants.AjaxRequestParam.userDbId, request);

			startWeekNum = getLongRequestValue(TimelineConstants.AjaxRequestParam.startWeekNum, request);
			startYear = getLongRequestValue(TimelineConstants.AjaxRequestParam.startYear, request);

			endWeekNum = getLongRequestValue(TimelineConstants.AjaxRequestParam.endWeekNum, request);
			endYear = getLongRequestValue(TimelineConstants.AjaxRequestParam.endYear, request);

			User user = getSessionUser(request);

			// non-admin user can search only his/her entries
			if ((user != null) && (!user.isAdmin())) {
				userDbId = user.getId();
			}

			// set default values
			if (weekStartDate == null) {
				weekStartDate = new Date();
				startYear = TextHelper.getYearForWeekDay(weekStartDate);
				startWeekNum = TextHelper.getWeekNumber(weekStartDate);
			}

			if (weekEndDate == null) {
				weekEndDate = new Date();
				endYear = TextHelper.getYearForWeekDay(weekEndDate);
				endWeekNum = TextHelper.getWeekNumber(weekEndDate);
			}

		}

		// start search
		{
			PrintWriter out = null;

			try {
				// populate search parameter
				TimeDataSearchParameters searchParameters = new TimeDataSearchParameters();
				searchParameters.setActivityid(activityId);
				searchParameters.setProjectId(projectId);
				searchParameters.setTaskId(taskId);
				searchParameters.setUserId(userDbId);
				searchParameters.setStartDate(weekStartDate);
				searchParameters.setStartWeekNum(startWeekNum);
				searchParameters.setStartYear(startYear);
				searchParameters.setEndDate(weekEndDate);
				searchParameters.setEndWeekNum(endWeekNum);
				searchParameters.setEndYear(endYear);

				// call service
				TimeDataReply reply = SERVICE.searchTimeData(searchParameters);

				// prepare JSON
				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	/**
	 * Search Users without entries.
	 * 
	 * @param request
	 * @param response
	 */
	private void searchUsersWithoutEntries(HttpServletRequest request, HttpServletResponse response) {

		// populate search parameters
		Date startDate = getDateRequestValue(TimelineConstants.AjaxRequestParam.weekStartDate, request);
		Date endDate = getDateRequestValue(TimelineConstants.AjaxRequestParam.weekEndDate, request);

		// start search

		PrintWriter out = null;

		try {
			// populate search parameter
			WeekSearchParameter searchParameters = new WeekSearchParameter();
			searchParameters.setEndDate(endDate);
			searchParameters.setStartDate(startDate);

			// call service
			WeeklyUserReply reply = SERVICE.searchUsersWithoutEntries(searchParameters);

			// prepare JSON
			String json = getJSON(reply);
			out = response.getWriter();
			out.println(json);

		} catch (TimelineException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * Fetches report details.
	 * 
	 * @param request
	 * @param response
	 */
	private void getReportDetails(HttpServletRequest request, HttpServletResponse response) {

		long projectId = getLongRequestValue(TimelineConstants.AjaxRequestParam.projectId, request);

		if (projectId > 0) {

			// start search
			{
				PrintWriter out = null;

				try {
					// populate search parameter
					ReportSearchParameters searchParameters = new ReportSearchParameters();
					searchParameters.setProjectDbId(projectId);

					// call service
					DetailedReportReply reply = SERVICE.getDetailedReport(searchParameters);

					// prepare JSON
					String json = getJSON(reply);
					out = response.getWriter();
					out.println(json);

				} catch (TimelineException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (out != null) {
						out.close();
					}
				}
			}
		}
	}

	private void saveEstimates(HttpServletRequest request, HttpServletResponse response) {

		long projectId = getLongRequestValue(TimelineConstants.AjaxRequestParam.projectId, request);
		double bac = getDoubleRequestValue(TimelineConstants.AjaxRequestParam.bac, request);

		Date startDate = getDateRequestValue(TimelineConstants.AjaxRequestParam.startDate, TextHelper.WEEK_DAY_FORMAT,
				request);
		Date endDate = getDateRequestValue(TimelineConstants.AjaxRequestParam.endDate, TextHelper.WEEK_DAY_FORMAT,
				request);

		PrintWriter out = null;

		if ((projectId > 0) && (bac > 0) && (startDate != null) && (endDate != null) && (startDate.before(endDate))) {

			CodeValueReply reply = null;
			ProjectEstimatesInput input = new ProjectEstimatesInput();

			// populate estimates
			{
				ProjectEstimateData estimates = new ProjectEstimateData();
				estimates.setBudgetAtCompletion(bac);
				estimates.setStartDate(startDate);
				estimates.setEndDate(endDate);

				ProjectData projectData = new ProjectData();
				projectData.setCode(projectId);

				estimates.setProjectData(projectData);

				// update input
				input.setEstimateData(estimates);

			}

			try {
				if (projectId > 0) {
					reply = SERVICE.saveProjectEstimates(input);
				}

				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	private void saveMetrics(HttpServletRequest request, HttpServletResponse response) {

		long projectId = getLongRequestValue(TimelineConstants.AjaxRequestParam.projectId, request);

		if (projectId > 0) {

			long metricId = getLongRequestValue(TimelineConstants.AjaxRequestParam.metricId, request);
			Date weekStartDate = getDateRequestValue(TimelineConstants.AjaxRequestParam.weekStartDate, request);

			double plannedValue = getDoubleRequestValue(TimelineConstants.AjaxRequestParam.pv, request);
			double earnedValue = getDoubleRequestValue(TimelineConstants.AjaxRequestParam.ev, request);
			double actualValue = getDoubleRequestValue(TimelineConstants.AjaxRequestParam.ac, request);
			double actualsToDate = getDoubleRequestValue(TimelineConstants.AjaxRequestParam.atd, request);
			double softwareProgEffort = getDoubleRequestValue(TimelineConstants.AjaxRequestParam.spe, request);
			long defects = getLongRequestValue(TimelineConstants.AjaxRequestParam.bug, request);

			ProjectMetricsInput input = new ProjectMetricsInput();

			input.setProjectId(projectId);
			input.setMetricId(metricId);
			input.setPlannedValue(plannedValue);
			input.setEarnedValue(earnedValue);
			input.setActualCost(actualValue);
			input.setActualsToDate(actualsToDate);
			input.setSoftwareProgrammingEffort(softwareProgEffort);
			input.setDefects(defects);
			input.setDate(weekStartDate);

			PrintWriter out = null;

			try {
				CodeValueReply reply = null;

				if (metricId > 0) {
					reply = SERVICE.modifyProjectDetailMetrics(input);
				} else {
					reply = SERVICE.createProjectDetailMetrics(input);
				}

				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	private void deleteAllMetrics(HttpServletRequest request, HttpServletResponse response) {

		long projectId = getLongRequestValue(TimelineConstants.AjaxRequestParam.projectId, request);

		if (projectId > 0) {

			PrintWriter out = null;
			CodeValueReply reply = null;

			try {

				if (projectId > 0) {

					CodeValueInput input = new CodeValueInput();
					input.setCodeValue(new CodeValue(projectId));

					reply = SERVICE.deleteProjectMetrics(input);
				}

				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	/**
	 * Searches the Project Estimates.
	 * 
	 * @param request
	 * @param response
	 */
	private void searchEstimates(HttpServletRequest request, HttpServletResponse response) {
		long projectId = getLongRequestValue(TimelineConstants.AjaxRequestParam.projectId, request);

		if (projectId > 0) {

			PrintWriter out = null;

			try {

				ProjectSearchParameter searchParameters = new ProjectSearchParameter();
				searchParameters.setProjectId(projectId);

				ProjectEstimatesReply reply = SERVICE.searchProjectEstimates(searchParameters);

				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	private void saveTask(HttpServletRequest request, HttpServletResponse response) {

		final long projectId = getLongRequestValue(TimelineConstants.AjaxRequestParam.projectId, request);
		final long activityId = getLongRequestValue(TimelineConstants.AjaxRequestParam.activityId, request);
		final long taskId = getLongRequestValue(TimelineConstants.AjaxRequestParam.taskId, request);
		final String taskText = getStringRequestValue(TimelineConstants.AjaxRequestParam.text, request);
		final String taskDesc = getStringRequestValue(TimelineConstants.AjaxRequestParam.description, request);

		if (taskText != null) {

			boolean hasEnoughData = false;

			if ((taskId == 0) && ((projectId > 0) && (activityId > 0))) {
				hasEnoughData = true;
			} else if (taskId > 0) {
				hasEnoughData = true;
			}

			if (hasEnoughData) {

				PrintWriter out = null;

				try {

					CodeValueReply reply = null;
					TaskInput input = new TaskInput();

					input.setActivityId(activityId);
					input.setProjectId(projectId);
					input.setTaskText(this.getFormattedTaskText(taskText));
					// input.setUserDbId(userDbId);
					input.setTaskDescription(taskDesc);
					input.setTaskId(taskId);

					if (taskId > 0) {
						reply = SERVICE.modifyTask(input);
					} else {
						reply = SERVICE.createTask(input);
					}

					String json = getJSON(reply);
					out = response.getWriter();
					out.println(json);

				} catch (TimelineException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (out != null) {
						out.close();
					}
				}
			}
		}
	}

	private void modifyTaskStage(HttpServletRequest request, HttpServletResponse response) {

		final long activityId = getLongRequestValue(TimelineConstants.AjaxRequestParam.activityId, request);
		final long taskId = getLongRequestValue(TimelineConstants.AjaxRequestParam.taskId, request);

		if ((taskId > 0) && (activityId > 0)) {

			PrintWriter out = null;

			try {

				CodeValueReply reply = null;

				TaskInput input = new TaskInput();
				input.setActivityId(activityId);
				input.setTaskId(taskId);

				reply = SERVICE.modifyTask(input);

				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	private void deleteTask(HttpServletRequest request, HttpServletResponse response) {

		long id = getLongRequestValue(TimelineConstants.AjaxRequestParam.id, request);

		if (id > 0) {
			PrintWriter out = null;
			CodeValueReply reply = null;
			CodeValueInput input = new CodeValueInput();
			input.setCodeValue(new CodeValue(id));

			try {
				reply = SERVICE.deleteTask(input);
				String json = getJSON(reply);
				out = response.getWriter();
				out.println(json);

			} catch (TimelineException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		boolean validUser = false;

		final String typeStr = getStringRequestValue(TimelineConstants.AjaxRequestParam.operation, request);
		final TimelineConstants.OperationType type = TimelineConstants.OperationType.getOperationType(typeStr);

		{
			User user = getSessionUser(request);

			// kick out user trying to access admin features
			if ((user != null) && (type.isAdminAccessOnly()) && (!user.isAdmin())) {
				validUser = false;
			} else {
				validUser = true;
			}
		}

		if (validUser) {
			if (TimelineConstants.OperationType.PROJECT.toString().equalsIgnoreCase(typeStr)) {
				getProjects(request, response);
			} else if (TimelineConstants.OperationType.ACTIVTIES.toString().equalsIgnoreCase(typeStr)) {
				getActivities(request, response);
			} else if (TimelineConstants.OperationType.LEAD.toString().equalsIgnoreCase(typeStr)) {
				getLeads(request, response);
			} else if (TimelineConstants.OperationType.TASKS.toString().equalsIgnoreCase(typeStr)) {
				getTasks(request, response);
			} else if (TimelineConstants.OperationType.SAVE_PROJECT.toString().equalsIgnoreCase(typeStr)) {
				saveProject(request, response);
			} else if (TimelineConstants.OperationType.SAVE_PROJECT_STATUS.toString().equalsIgnoreCase(typeStr)) {
				saveEntityStatus(request, response, TimelineConstants.StatusEntity.PROJECT);
			} else if (TimelineConstants.OperationType.DELETE_PROJECT.toString().equalsIgnoreCase(typeStr)) {
				deleteProject(request, response);
			} else if (TimelineConstants.OperationType.SAVE_LEAD.toString().equalsIgnoreCase(typeStr)) {
				saveLead(request, response);
			} else if (TimelineConstants.OperationType.SAVE_ACTIVITY.toString().equalsIgnoreCase(typeStr)) {
				saveActivity(request, response);
			} else if (TimelineConstants.OperationType.DELETE_ACTIVITY.toString().equalsIgnoreCase(typeStr)) {
				deleteActivity(request, response);
			} else if (TimelineConstants.OperationType.SAVE_USER.toString().equalsIgnoreCase(typeStr)) {
				saveUser(request, response);
			} else if (TimelineConstants.OperationType.SAVE_USER_STATUS.toString().equalsIgnoreCase(typeStr)) {
				saveEntityStatus(request, response, TimelineConstants.StatusEntity.USER);
			} else if (TimelineConstants.OperationType.DELETE_USER.toString().equalsIgnoreCase(typeStr)) {
				deleteUser(request, response);
			} else if (TimelineConstants.OperationType.RESET_USER.toString().equalsIgnoreCase(typeStr)) {
				resetUser(request, response);
			} else if (TimelineConstants.OperationType.SAVE_TIME_ENTRY.toString().equalsIgnoreCase(typeStr)) {
				saveTimeEntry(request, response);
			} else if (TimelineConstants.OperationType.DELETE_TIME_ENTRY.toString().equalsIgnoreCase(typeStr)) {
				deleteTimeEntry(request, response);
			} else if (TimelineConstants.OperationType.MODIFY_USER.toString().equalsIgnoreCase(typeStr)) {
				modifyUser(request, response);
			} else if (TimelineConstants.OperationType.MODIFY_USER_PREF.toString().equalsIgnoreCase(typeStr)) {
				modifyUserPreferences(request, response);
			} else if (TimelineConstants.OperationType.SEARCH_ENTRIES.toString().equalsIgnoreCase(typeStr)) {
				searchEntries(request, response);
			} else if (TimelineConstants.OperationType.SEARCH_USERS_WITHOUT_ENTRIES.toString()
					.equalsIgnoreCase(typeStr)) {
				searchUsersWithoutEntries(request, response);
			} else if (TimelineConstants.OperationType.REPORT_DETAIL.toString().equalsIgnoreCase(typeStr)) {
				getReportDetails(request, response);
			} else if (TimelineConstants.OperationType.SAVE_ESTIMATES.toString().equalsIgnoreCase(typeStr)) {
				saveEstimates(request, response);
			} else if (TimelineConstants.OperationType.DELETE_ALL_METRICS.toString().equalsIgnoreCase(typeStr)) {
				deleteAllMetrics(request, response);
			} else if (TimelineConstants.OperationType.SAVE_METRIC_DETAILS.toString().equalsIgnoreCase(typeStr)) {
				saveMetrics(request, response);
			} else if (TimelineConstants.OperationType.SEARCH_ESTIMATES.toString().equalsIgnoreCase(typeStr)) {
				searchEstimates(request, response);
			} else if (TimelineConstants.OperationType.SAVE_TASK.toString().equalsIgnoreCase(typeStr)) {
				saveTask(request, response);
			} else if (TimelineConstants.OperationType.MODIFY_TASK_STAGE.toString().equalsIgnoreCase(typeStr)) {
				modifyTaskStage(request, response);
			} else if (TimelineConstants.OperationType.DELETE_TASK.toString().equalsIgnoreCase(typeStr)) {
				deleteTask(request, response);
			}
		} else {
			handleInvalidRequest(request, response);
		}

		response.setContentType(RESPONSE_TYPE);
	}
}