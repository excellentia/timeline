package org.ag.timeline.application.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.ag.timeline.business.service.iface.TimelineIface;
import org.ag.timeline.business.service.impl.TimelineImpl;
import org.ag.timeline.common.TextHelper;
import org.ag.timeline.common.TimelineConstants;
import org.ag.timeline.presentation.transferobject.common.CodeValue;
import org.ag.timeline.presentation.transferobject.input.CodeValueInput;
import org.ag.timeline.presentation.transferobject.input.ProjectInput;
import org.ag.timeline.presentation.transferobject.input.TimeDataInput;
import org.ag.timeline.presentation.transferobject.input.UserInput;
import org.ag.timeline.presentation.transferobject.input.UserPreferencesInput;
import org.ag.timeline.presentation.transferobject.reply.ActivityReply;
import org.ag.timeline.presentation.transferobject.reply.BusinessReply;
import org.ag.timeline.presentation.transferobject.reply.CodeValueReply;
import org.ag.timeline.presentation.transferobject.reply.ProjectData;
import org.ag.timeline.presentation.transferobject.reply.ProjectReply;
import org.ag.timeline.presentation.transferobject.reply.TimeDataReply;
import org.ag.timeline.presentation.transferobject.reply.TimeDataRow;
import org.ag.timeline.presentation.transferobject.reply.UserPreferenceReply;
import org.ag.timeline.presentation.transferobject.reply.UserReply;
import org.ag.timeline.presentation.transferobject.reply.UserSearchReply;
import org.ag.timeline.presentation.transferobject.search.ActivitySearchParameter;
import org.ag.timeline.presentation.transferobject.search.ProjectSearchParameter;
import org.ag.timeline.presentation.transferobject.search.TimeDataSearchParameters;
import org.ag.timeline.presentation.transferobject.search.UserSearchParameter;

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
	private static final TimelineIface SERVICE = new TimelineImpl();

	/**
	 * Response type JSON.
	 */
	private static final String RESPONSE_TYPE = "text/json";

	private User getSessionUser(HttpServletRequest request) {
		return (User) request.getSession().getAttribute(TimelineConstants.SessionAttribute.SESSION_USER.getText());
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
					List<CodeValue> activities = new ArrayList<CodeValue>();

					for (long projId : projectIds) {
						activities.addAll(activityReply.getProjectActivities(projId));
					}

					if (activities != null) {

						builder.append("\"activities\" : [");

						for (CodeValue activity : activities) {
							builder.append("{\"code\" : \"").append(activity.getCode()).append("\", \"value\" : \"")
									.append(activity.getValue());
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
					builder.append("\"message\" : \"").append(preferenceReply.getMessage()).append("\"");

				} else {
					builder.append("\"error\" : \"" + preferenceReply.getMessage() + "\"");
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
			}

			builder.append("}");
			json = builder.toString();
		}

		return json;
	}

	private void handleInvalidRequest(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;

		try {
			String json = "{\"error\" : \"No projects\"}";
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

	private void getProjects(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;

		try {
			final long id = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.id
					.getParamText()));
			final ProjectSearchParameter searchParameters = new ProjectSearchParameter();
			searchParameters.setProjectId(id);

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
			final long id = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.id
					.getParamText()));
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

		long id = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.id.getParamText()));
		String projectText = TextHelper.trimToNull(request.getParameter(TimelineConstants.AjaxRequestParam.text
				.getParamText()));
		PrintWriter out = null;

		if (projectText != null) {
			CodeValueReply reply = null;
			ProjectInput input = new ProjectInput();
			input.setNewLabelText(projectText);
			input.setProjectId(id);

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

	private void deleteProject(HttpServletRequest request, HttpServletResponse response) {

		long id = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.id.getParamText()));

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

		long id = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.id.getParamText()));

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

	private void saveLead(HttpServletRequest request, HttpServletResponse response) {

		PrintWriter out = null;
		CodeValueReply reply = null;

		long projectId = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.id
				.getParamText()));
		long leadId = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.refId
				.getParamText()));

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

		long id = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.id.getParamText()));

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

		long projId = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.id
				.getParamText()));
		long actId = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.refId
				.getParamText()));
		String text = TextHelper.trimToNull(request.getParameter(TimelineConstants.AjaxRequestParam.text
				.getParamText()));

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

		final long id = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.id
				.getParamText()));

		if (id > 0) {
			PrintWriter out = null;
			UserReply reply = null;

			final String field = TextHelper.trimToNull(request.getParameter(TimelineConstants.AjaxRequestParam.field
					.getParamText()));

			final String value = TextHelper.trimToNull(request.getParameter(TimelineConstants.AjaxRequestParam.text
					.getParamText()));

			if ((field != null) && (value != null)) {
				UserInput input = new UserInput();

				input.setId(id);

				final TimelineConstants.UserDataFieldType type = TimelineConstants.UserDataFieldType
						.getType(field);
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

		final long userDbId = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.id
				.getParamText()));

		if (userDbId > 0) {
			PrintWriter out = null;
			UserPreferenceReply reply = null;

			final String field = TextHelper.trimToNull(request.getParameter(TimelineConstants.AjaxRequestParam.field
					.getParamText()));

			final String value = TextHelper.trimToNull(request.getParameter(TimelineConstants.AjaxRequestParam.text
					.getParamText()));

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

		final long id = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.id
				.getParamText()));
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

		long id = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.id.getParamText()));
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
		long entryId = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.entryId
				.getParamText()));

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

		long projectId = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.projectId
				.getParamText()));
		long activityId = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.activityId
				.getParamText()));

		if ((projectId > 0) && (activityId > 0)) {

			long entryId = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.entryId
					.getParamText()));

			long userId = 0;
			User user = getSessionUser(request);

			if (user != null) {
				userId = user.getId();
			}

			Date weekStartDate = TextHelper.getValidDate(request
					.getParameter(TimelineConstants.AjaxRequestParam.weekStartDate.getParamText()));

			double day1 = TextHelper.getDoubleValue(request.getParameter(TimelineConstants.AjaxRequestParam.day1
					.getParamText()));
			double day2 = TextHelper.getDoubleValue(request.getParameter(TimelineConstants.AjaxRequestParam.day2
					.getParamText()));
			double day3 = TextHelper.getDoubleValue(request.getParameter(TimelineConstants.AjaxRequestParam.day3
					.getParamText()));
			double day4 = TextHelper.getDoubleValue(request.getParameter(TimelineConstants.AjaxRequestParam.day4
					.getParamText()));
			double day5 = TextHelper.getDoubleValue(request.getParameter(TimelineConstants.AjaxRequestParam.day5
					.getParamText()));
			double day6 = TextHelper.getDoubleValue(request.getParameter(TimelineConstants.AjaxRequestParam.day6
					.getParamText()));
			double day7 = TextHelper.getDoubleValue(request.getParameter(TimelineConstants.AjaxRequestParam.day7
					.getParamText()));

			TimeDataInput myTimeData = new TimeDataInput();

			myTimeData.setActivityId(activityId);
			myTimeData.setDate(weekStartDate);
			myTimeData.setUserId(userId);

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
		long weekStartYear = 0;
		long weekEndYear = 0;
		long projectId = 0;
		long activityId = 0;

		Date weekStartDate = null;
		Date weekEndDate = null;

		// populate search parameters
		{
			weekStartDate = TextHelper.getValidDate(request
					.getParameter(TimelineConstants.AjaxRequestParam.weekStartDate.getParamText()));
			weekEndDate = TextHelper.getValidDate(request.getParameter(TimelineConstants.AjaxRequestParam.weekEndDate
					.getParamText()));
			projectId = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.projectId
					.getParamText()));
			activityId = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.activityId
					.getParamText()));
			userDbId = TextHelper.getLongValue(request.getParameter(TimelineConstants.AjaxRequestParam.userDbId
					.getParamText()));

			User user = getSessionUser(request);

			// non-admin user can search only his/her entries
			if ((user != null) && (!user.isAdmin())) {
				userDbId = user.getId();
			}

			// set default values
			if (weekStartDate == null) {
				weekStartDate = new Date();
				weekStartYear = TextHelper.getYear(weekStartDate);
				startWeekNum = TextHelper.getWeekNumber(weekStartDate);
			} else {
				weekStartYear = TextHelper.getYear(weekStartDate);
				startWeekNum = TextHelper.getWeekNumber(weekStartDate);
			}

			if (weekEndDate == null) {
				weekEndDate = new Date();
				weekEndYear = TextHelper.getYear(weekEndDate);
				endWeekNum = TextHelper.getWeekNumber(weekEndDate);
			} else {
				weekEndYear = TextHelper.getYear(weekEndDate);
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
				searchParameters.setUserId(userDbId);
				searchParameters.setStartWeekNum(startWeekNum);
				searchParameters.setStartYear(weekStartYear);
				searchParameters.setEndWeekNum(endWeekNum);
				searchParameters.setEndYear(weekEndYear);

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

	/*
	 * (non-Javadoc)
	 * 
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
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		boolean validUser = false;

		final String typeStr = TextHelper.trimToNull(request
				.getParameter(TimelineConstants.AjaxRequestParam.operation.getParamText()));
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
			} else if (TimelineConstants.OperationType.SAVE_PROJECT.toString().equalsIgnoreCase(typeStr)) {
				saveProject(request, response);
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
			}
		} else {
			handleInvalidRequest(request, response);
		}

		response.setContentType(RESPONSE_TYPE);
	}
}