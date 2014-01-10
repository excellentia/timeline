package org.ag.timeline.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ag.timeline.application.context.RequestContext;
import org.ag.timeline.application.exception.TimelineException;
import org.ag.timeline.business.model.Activity;
import org.ag.timeline.business.model.AuditRecord;
import org.ag.timeline.business.model.AuditRecordDetail;
import org.ag.timeline.business.model.Project;
import org.ag.timeline.business.model.ProjectMetrics;
import org.ag.timeline.business.model.ProjectStage;
import org.ag.timeline.business.model.ProjectStageTask;
import org.ag.timeline.business.model.Stage;
import org.ag.timeline.business.model.SystemSettings;
import org.ag.timeline.business.model.Task;
import org.ag.timeline.business.model.TimeData;
import org.ag.timeline.business.model.User;
import org.ag.timeline.business.model.UserPreferences;
import org.ag.timeline.business.model.Week;
import org.ag.timeline.business.service.iface.TimelineService;
import org.ag.timeline.business.util.HibernateUtil;
import org.ag.timeline.business.util.audit.AuditInterceptor;
import org.ag.timeline.common.TextHelper;
import org.ag.timeline.common.TimelineConstants;
import org.ag.timeline.common.UserComparator;
import org.ag.timeline.presentation.transferobject.common.CodeValue;
import org.ag.timeline.presentation.transferobject.input.AuthenticationInput;
import org.ag.timeline.presentation.transferobject.input.CodeValueInput;
import org.ag.timeline.presentation.transferobject.input.ProjectEstimatesInput;
import org.ag.timeline.presentation.transferobject.input.ProjectInput;
import org.ag.timeline.presentation.transferobject.input.ProjectMetricsInput;
import org.ag.timeline.presentation.transferobject.input.ProjectStageInput;
import org.ag.timeline.presentation.transferobject.input.ProjectStageTaskInput;
import org.ag.timeline.presentation.transferobject.input.StatusInput;
import org.ag.timeline.presentation.transferobject.input.TimeDataInput;
import org.ag.timeline.presentation.transferobject.input.UserInput;
import org.ag.timeline.presentation.transferobject.input.UserPreferencesInput;
import org.ag.timeline.presentation.transferobject.reply.ActivityReply;
import org.ag.timeline.presentation.transferobject.reply.AuditDataReply;
import org.ag.timeline.presentation.transferobject.reply.AuditDetailRow;
import org.ag.timeline.presentation.transferobject.reply.AuditRow;
import org.ag.timeline.presentation.transferobject.reply.BasicProjectMetrics;
import org.ag.timeline.presentation.transferobject.reply.CodeValueListReply;
import org.ag.timeline.presentation.transferobject.reply.CodeValueReply;
import org.ag.timeline.presentation.transferobject.reply.DetailedReportReply;
import org.ag.timeline.presentation.transferobject.reply.DetailedReportRow;
import org.ag.timeline.presentation.transferobject.reply.ProjectData;
import org.ag.timeline.presentation.transferobject.reply.ProjectDetailMetrics;
import org.ag.timeline.presentation.transferobject.reply.ProjectDetailMetricsReply;
import org.ag.timeline.presentation.transferobject.reply.ProjectEstimateData;
import org.ag.timeline.presentation.transferobject.reply.ProjectEstimatesReply;
import org.ag.timeline.presentation.transferobject.reply.ProjectLevelMetrics;
import org.ag.timeline.presentation.transferobject.reply.ProjectLevelMetricsReply;
import org.ag.timeline.presentation.transferobject.reply.ProjectReply;
import org.ag.timeline.presentation.transferobject.reply.ReportRow;
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
import org.ag.timeline.presentation.transferobject.search.ProjectStageSearchParameters;
import org.ag.timeline.presentation.transferobject.search.ReportSearchParameters;
import org.ag.timeline.presentation.transferobject.search.StageSearchParameter;
import org.ag.timeline.presentation.transferobject.search.TimeDataSearchParameters;
import org.ag.timeline.presentation.transferobject.search.UserPreferenceSearchParameter;
import org.ag.timeline.presentation.transferobject.search.UserSearchParameter;
import org.ag.timeline.presentation.transferobject.search.WeekSearchParameter;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;

/**
 * Implementation of {@link TimelineService}.
 * 
 * @author Abhishek Gaurav
 */
public class TimelineServiceImpl implements TimelineService {

	/**
	 * Hibernate Session Factory.
	 */
	private static final SessionFactory SESSION_FACTORY = HibernateUtil.getSessionFactory();

	/**
	 * Opens a session with an attached Audit Interceptor.
	 * 
	 * @return {@link Session} with interceptor.
	 */
	protected final Session getAuditableSession() {
		return SESSION_FACTORY.withOptions().interceptor(new AuditInterceptor()).openSession();
	}

	/**
	 * Opens a normal session without any interceptor.
	 * 
	 * @return {@link Session} without interceptor.
	 */
	protected final Session getNormalSession() {
		return SESSION_FACTORY.openSession();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#autheticateUser(
	 * org.ag.timeline.presentation.transferobject.input.AuthenticationInput)
	 */
	public UserReply autheticateUser(AuthenticationInput input) throws TimelineException {
		Session session = null;
		Transaction transaction = null;
		UserReply reply = null;

		if (input != null) {
			final String pwd = TextHelper.trimToNull(input.getPassword());
			final String usrId = TextHelper.trimToNull(input.getUserId());
			final String question = TextHelper.trimToNull(input.getQuestion());
			final String answer = TextHelper.trimToNull(input.getAnswer());

			if (usrId != null) {
				try {
					// create data, hence using AuditableSession()
					session = getNormalSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();
					User user = null;

					if (pwd != null) {

						Criteria criteria = session.createCriteria(User.class);
						criteria.add(Restrictions.and(Restrictions.eq("userId", usrId),
								Restrictions.eq("password", pwd), Restrictions.eq("active", Boolean.TRUE)));
						criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

						@SuppressWarnings("unchecked")
						List<User> dbList = criteria.list();

						if ((dbList != null) && (dbList.size() > 0)) {
							user = dbList.get(0);
						}
					}

					if ((question != null) && (answer != null)) {

						Criteria criteria = session.createCriteria(UserPreferences.class);
						criteria.createAlias("user", "usr");
						criteria.add(Restrictions.and(Restrictions.eq("usr.userId", usrId),
								Restrictions.eq("usr.active", Boolean.TRUE), Restrictions.eq("question", question),
								Restrictions.eq("answer", answer)));
						criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

						@SuppressWarnings("unchecked")
						List<UserPreferences> dbList = criteria.list();

						if ((dbList != null) && (dbList.size() > 0)) {
							UserPreferences preferences = dbList.get(0);

							if (preferences != null) {
								user = preferences.getUser();
							}
						}
					}

					if (user != null) {
						reply = new UserReply();
						reply.setUser(user);
					}

					TextHelper.logMessage("autheticateUser() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			}
		}

		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#createActivity(org
	 * .ag.timeline.presentation.transferobject.input.CodeValueInput)
	 */
	public CodeValueReply createActivity(CodeValueInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if ((input != null) && (input.getCodeValue() != null)) {

			final long projectId = input.getCodeValue().getCode();
			final String text = TextHelper.trimToNull(input.getCodeValue().getValue());

			if ((projectId > 0) && (text != null)) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();
					final Long id = projectId;
					Activity activity = null;

					Criteria criteria = session.createCriteria(Activity.class);
					criteria.createAlias("project", "pr");
					criteria.add(Restrictions.and(Restrictions.eq("pr.id", id), Restrictions.eq("name", text)));
					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<Activity> list = criteria.list();

					if ((list != null) && (list.size() > 0)) {

						// already a project exists with given name.
						reply.setErrorMessage("This activity name is already present in project.");

					} else {

						Project project = (Project) session.get(Project.class, id);

						if (project != null) {

							activity = new Activity();
							activity.setProject(project);
							activity.setName(text);
							project.addChild(activity);
							session.saveOrUpdate(project);

							reply.setSuccessMessage("Created successfully.");

						} else {
							reply.setErrorMessage("Specified project not present in system.");
						}
					}

					TextHelper.logMessage("createActivity() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

					if (activity != null) {
						reply.setCodeValue(new CodeValue(activity.getId(), activity.getName()));
					}

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Create failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (projectId <= 0) {
					reply.setErrorMessage("Invalid project specified.");
				} else if (text == null) {
					reply.setErrorMessage("Invalid activity name specified.");
				}
			}
		}

		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#createProject(java
	 * .lang.String)
	 */

	public CodeValueReply createProject(ProjectInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if (input != null) {
			final String text = TextHelper.trimToNull(input.getNewLabelText());

			if (text != null) {
				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();
					Project project = null;

					Criteria criteria = session.createCriteria(Project.class);
					criteria.add(Restrictions.eq("name", text));
					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<Project> dbList = criteria.list();

					if ((dbList != null) && (dbList.size() > 0)) {

						// already a project exists with given name.
						reply.setErrorMessage("A project with same name already exists.");

					} else {
						project = new Project();
						project.setName(text);

						// Handle the Copy Project feature
						final long copyProjId = input.getCopyProjectId();

						if (copyProjId > 0) {

							Project srcProj = (Project) session.get(Project.class, Long.valueOf(copyProjId));

							if ((srcProj != null) && (srcProj.hasActivities())) {

								Activity copy = null;

								for (Activity original : srcProj.getActivities()) {
									copy = new Activity();
									copy.setName(original.getName());
									copy.setProject(project);
									project.addChild(copy);
								}
							}
						}

						session.save(project);
						reply.setCodeValue(new CodeValue(project.getId(), project.getName()));

						reply.setSuccessMessage("Created successfully.");
					}

					TextHelper.logMessage("createProject() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Create failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (text == null) {
					reply.setErrorMessage("Invalid project name specified.");
				}
			}
		}

		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#deleteProject(long)
	 */

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#createTimeData(org
	 * .ag.timeline.presentation.transferobject.input.TimeDataInput)
	 */
	public CodeValueReply createTimeData(TimeDataInput myTimeData) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if (myTimeData != null) {

			long entryId = myTimeData.getId();

			if (entryId == 0) {

				try {

					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();

					Activity activity = (Activity) session.get(Activity.class, new Long(myTimeData.getActivityId()));
					User currentUser = (User) session.get(User.class, myTimeData.getUserId());
					Date startDate = myTimeData.getDate();
					Long year = TextHelper.getYearForWeekDay(startDate);
					User proxiedUser = null;

					// set the user as current user by default
					User user = currentUser;

					if (myTimeData.getProxiedUserDbId() > 0) {
						proxiedUser = (User) session.get(User.class, myTimeData.getProxiedUserDbId());

						if (proxiedUser != null) {

							// update the user as proxied user
							user = proxiedUser;

						} else {

							// throw exception as user being proxied is not
							// present in system
							throw new TimelineException("Proxied User does not exist in system.");

						}
					}

					if ((activity != null) && (user != null) && (startDate != null) && (user.getId() > 0)) {

						// week handling
						Week week = null;
						TimeData data = null;

						if (week == null) {
							Criteria criteria = session.createCriteria(Week.class);
							criteria.add(Restrictions.and(Restrictions.eq("year", year),
									Restrictions.eq("startDate", startDate)));
							criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

							@SuppressWarnings("unchecked")
							List<Week> list = criteria.list();

							if ((list != null) && (list.size() > 0)) {
								week = list.get(0);
							}

							if (week == null) {
								week = new Week();

								week.setYear(year);
								week.setWeekNumber(TextHelper.getWeekNumber(startDate));
								week.setStartDate(TextHelper.getFirstDayOfWeek(startDate));
								week.setEndDate(TextHelper.getLastDayOfWeek(startDate));

								// save the week
								session.saveOrUpdate(week);
							}
						}

						// check if duplicate entry (same week, same year, same
						// project, same activity) is being requested
						{
							// search for existing activities
							Criteria criteria = session.createCriteria(TimeData.class);
							criteria.createAlias("user", "usr");
							criteria.createAlias("activity", "act");
							criteria.createAlias("project", "proj");
							criteria.createAlias("week", "wk");
							criteria.add(Restrictions.and(
									Restrictions.eq("wk.weekNumber", new Long(week.getWeekNumber())),
									Restrictions.eq("wk.year", new Long(year)),
									Restrictions.eq("usr.id", new Long(user.getId())),
									Restrictions.eq("act.id", new Long(activity.getId())),
									Restrictions.eq("proj.id", new Long(activity.getProject().getId()))));

							criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

							@SuppressWarnings("unchecked")
							List<TimeData> list = criteria.list();

							// user is trying to add same activity as existing
							if ((list != null) && (list.size() > 0)) {
								reply.setErrorMessage("An entry for same user, project, activity and week already exists.");
							} else {

								// ceate new entry
								data = new TimeData();

								data.setActivity(activity);
								data.setProject(activity.getProject());
								data.setUser(user);
								data.setWeek(week);

								data.setData_weekday_1(TextHelper.getScaledBigDecimal(myTimeData.getDay_1_time()));
								data.setData_weekday_2(TextHelper.getScaledBigDecimal(myTimeData.getDay_2_time()));
								data.setData_weekday_3(TextHelper.getScaledBigDecimal(myTimeData.getDay_3_time()));
								data.setData_weekday_4(TextHelper.getScaledBigDecimal(myTimeData.getDay_4_time()));
								data.setData_weekday_5(TextHelper.getScaledBigDecimal(myTimeData.getDay_5_time()));
								data.setData_weekday_6(TextHelper.getScaledBigDecimal(myTimeData.getDay_6_time()));
								data.setData_weekday_7(TextHelper.getScaledBigDecimal(myTimeData.getDay_7_time()));

								// save the data
								session.saveOrUpdate(data);

								reply.setCodeValue(new CodeValue(data.getId()));
								reply.setSuccessMessage("Saved successfully.");
							}
						}
					} else {
						reply.setErrorMessage("Mandatory data is missing.");
					}

					TextHelper.logMessage("createTimeData() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Save failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				reply.setErrorMessage("Cannot create entry as it already exists");
			}

		} else {
			if (myTimeData == null) {
				reply.setErrorMessage("Invalid data specified.");
			}
		}

		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#modifyProject(long,
	 * java.lang.String)
	 */

	public UserReply createUser(UserInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final UserReply reply = new UserReply();

		if (input != null) {

			final String first = TextHelper.trimToNull(input.getFirstName());
			final String last = TextHelper.trimToNull(input.getLastName());
			final String userId = TextHelper.trimToNull(input.getUserId());
			final String password = TextHelper.trimToNull(input.getPassword());
			final boolean admin = input.isAdmin();

			if (((first != null) || (last != null)) && (userId != null)) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();

					Criteria criteria = session.createCriteria(User.class);
					criteria.add(Restrictions.eq("userId", userId));
					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<User> dbList = criteria.list();

					if ((dbList != null) && (dbList.size() > 0)) {

						// already a project exists with given name.
						reply.setErrorMessage("A user with same id already exists.");

					} else {
						User user = new User();
						user.setFirstName(first);
						user.setLastName(last);
						user.setUserId(userId);
						user.setActive(true);

						if (password != null) {
							user.setPassword(password);
						}

						if (admin) {
							user.setAdmin(admin);
						}

						session.save(user);

						reply.setUser(user);
						reply.setSuccessMessage("Created successfully.");
					}

					TextHelper.logMessage("createUser() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Create failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if ((first == null) || (last == null)) {
					reply.setErrorMessage("Invalid user name specified.");
				}
			}
		}

		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#deleteActivity(long)
	 */

	public CodeValueReply deleteActivity(CodeValueInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if ((input != null) && (input.getCodeValue() != null)) {

			final long id = input.getCodeValue().getCode();

			if (id > 0) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();
					final Long activityId = id;

					Criteria criteria = session.createCriteria(TimeData.class);
					criteria.createAlias("activity", "act");
					criteria.add(Restrictions.eq("act.id", activityId));
					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<TimeData> list = criteria.list();

					if ((list != null) && (list.size() > 0)) {

						// already a project exists with given name.
						reply.setErrorMessage("There are already time entries present for this activity.");

					} else {

						Activity activity = (Activity) session.get(Activity.class, activityId);

						if (activity != null) {

							session.delete(activity);
							reply.setSuccessMessage("Deleted successfully.");
							reply.setCodeValue(new CodeValue(activity.getId(), activity.getName()));

						} else {
							reply.setErrorMessage("Specified activity not present in system.");
						}
					}

					TextHelper.logMessage("deleteActivity() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Delete failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (id <= 0) {
					reply.setErrorMessage("Invalid activity specified.");
				}
			}
		}

		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#modifyActivity(long,
	 * long, java.lang.String)
	 */

	public CodeValueReply deleteProject(CodeValueInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if ((input != null) && (input.getCodeValue() != null)) {
			final long id = input.getCodeValue().getCode();

			if (id > 0) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();

					final Long projectId = id;

					Criteria criteria = session.createCriteria(TimeData.class);
					criteria.createAlias("project", "project");
					criteria.add(Restrictions.eq("project.id", projectId));
					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<TimeData> list = criteria.list();

					if ((list != null) && (list.size() > 0)) {

						// already a project exists with given name.
						reply.setErrorMessage("There are already time entries present for this project.");

					} else {

						Project project = (Project) session.get(Project.class, projectId);

						if (project != null) {
							session.delete(project);
							reply.setSuccessMessage("Deleted successfully.");
							reply.setCodeValue(new CodeValue(project.getId(), project.getName()));
						} else {
							reply.setErrorMessage("Specified project not present in system.");
						}
					}

					TextHelper.logMessage("deleteProject() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Delete failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (id <= 0) {
					reply.setErrorMessage("Invalid project specified.");
				}
			}
		}

		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#createUser(java.
	 * lang.String, java.lang.String)
	 */

	public CodeValueReply deleteTimeData(CodeValueInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if ((input != null) && (input.getCodeValue() != null)) {

			final long id = input.getCodeValue().getCode();

			if (id > 0) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();
					final Long timeDataId = id;

					TimeData data = (TimeData) session.get(TimeData.class, timeDataId);

					if (data == null) {

						// already a project exists with given name.
						reply.setErrorMessage("Specified time entries not present in system.");

					} else {
						session.delete(data);
						reply.setCodeValue(new CodeValue(data.getId()));
						reply.setSuccessMessage("Deleted successfully.");
					}

					TextHelper.logMessage("deleteTimeData() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Delete failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (id <= 0) {
					reply.setErrorMessage("Invalid time entry specified.");
				}
			}

		}

		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#modifyUser(long,
	 * java.lang.String, java.lang.String)
	 */

	public UserReply deleteUser(CodeValueInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final UserReply reply = new UserReply();

		if ((input != null) && (input.getCodeValue() != null)) {

			final long id = input.getCodeValue().getCode();

			if (id > 0) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();

					final Long userId = id;

					Criteria criteria = session.createCriteria(TimeData.class);
					criteria.createAlias("user", "usr");
					criteria.add(Restrictions.eq("usr.id", userId));
					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<TimeData> list = criteria.list();

					if ((list != null) && (list.size() > 0)) {

						// already a project exists with given name.
						reply.setErrorMessage("There are already time entries present for this user.");

					} else {

						User user = (User) session.get(User.class, userId);

						if (user != null) {
							session.delete(user);
							reply.setSuccessMessage("Deleted successfully.");
							reply.setUser(user);
						} else {
							reply.setErrorMessage("Specified user not present in system.");
						}
					}

					TextHelper.logMessage("deleteUser() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Delete failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (id <= 0) {
					reply.setErrorMessage("Invalid user specified.");
				}
			}
		}

		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#deleteUser(long)
	 */

	public CodeValueReply deleteUserPreferences(CodeValueInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if ((input != null) && (input.getCodeValue() != null)) {

			final long id = input.getCodeValue().getCode();

			if (id > 0) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();
					final Long prefId = id;
					UserPreferences preferences = (UserPreferences) session.get(UserPreferences.class, prefId);

					if (preferences != null) {
						session.delete(preferences);
						reply.setSuccessMessage("Deleted successfully.");
						reply.setCodeValue(new CodeValue(preferences.getId()));
					} else {
						reply.setErrorMessage("Specified user preference not present in system.");
					}

					TextHelper.logMessage("deleteUserPreferences() > Time taken : "
							+ ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Delete failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (id <= 0) {
					reply.setErrorMessage("Invalid user preference specified.");
				}
			}
		}

		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#resetUserCredentials
	 * (long)
	 */
	public SummaryReportReply getSummaryReport(ReportSearchParameters searchParameters) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final SummaryReportReply reply = new SummaryReportReply();

		try {
			// read data, hence using normal session()
			session = getNormalSession();
			transaction = session.beginTransaction();

			long time = System.nanoTime();
			Set<Long> projectIdSet = new HashSet<Long>();
			Set<Long> activityIdSet = new HashSet<Long>();
			Set<Long> userIdSet = new HashSet<Long>();

			final long searchProjectId = searchParameters.getProjectDbId();
			final long searchActivityId = searchParameters.getActivityDbId();
			final long searchUserId = searchParameters.getUserDbId();
			final boolean searchActiveProjects = searchParameters.isSearchOnlyActiveProjects();
			final boolean searchActiveUsers = searchParameters.isSearchOnlyActiveUsers();

			// get activity aggregates
			{
				StringBuilder builder = new StringBuilder(" SELECT project.id,  activity.id  ");
				builder.append(" , sum(data_weekday_1+data_weekday_2+data_weekday_3+data_weekday_4+data_weekday_5+data_weekday_6+data_weekday_7) ");
				builder.append(" FROM TimeData ");

				boolean whereUsed = false;

				if (searchActiveProjects) {
					builder.append(" WHERE ");
					builder.append(" project.active = ").append(true);
					whereUsed = true;
				}

				if (!searchParameters.isSearchAllData()) {

					if (searchProjectId > 0) {

						if (whereUsed) {
							builder.append(" AND ");
						} else {
							builder.append(" WHERE ");
							whereUsed = true;
						}

						builder.append(" project.id = ").append(searchProjectId);
						whereUsed = true;
					}

					if (searchActivityId > 0) {

						if (whereUsed) {
							builder.append(" AND ");
						} else {
							builder.append(" WHERE ");
							whereUsed = true;
						}

						builder.append(" activity.id = ").append(searchActivityId);
					}

					if (searchUserId > 0) {

						if (whereUsed) {
							builder.append(" AND ");
						} else {
							builder.append(" WHERE ");
						}

						builder.append(" user.id = ").append(searchUserId);
					}
				}

				builder.append(" GROUP BY ");
				builder.append(" project.id, activity.id ");

				builder.append(" ORDER BY ");
				builder.append(" project.id, activity.id ");

				Query query = session.createQuery(builder.toString());

				@SuppressWarnings("rawtypes")
				List list = query.list();

				if ((list != null) && (list.size() > 0)) {

					// create a new data object
					ReportRow row = null;
					Object[] values = null;
					long projId = 0;
					long actId = 0;

					for (int i = 0; i < list.size(); i++) {
						values = (Object[]) list.get(i);

						// get values
						projId = (Long) values[0];
						actId = (Long) values[1];

						// create new row
						row = new ReportRow();

						// populate row
						row.setProjectId(projId);
						row.setRowId(actId);
						row.setRowTime(TextHelper.getScaledDouble((BigDecimal) values[2]));

						// update the activities in reply
						reply.addActivityRow(row);

						// update the lists that fetch names later
						projectIdSet.add(projId);
						activityIdSet.add(actId);
					}
				}
			}

			// get user aggregates
			{

				StringBuilder builder = new StringBuilder(" SELECT project.id, user.id ");
				builder.append(" , sum(data_weekday_1+data_weekday_2+data_weekday_3+data_weekday_4+data_weekday_5+data_weekday_6+data_weekday_7) ");
				builder.append(" FROM TimeData ");

				boolean whereUsed = false;

				if (searchActiveProjects) {
					builder.append(" WHERE ");
					builder.append(" project.active = ").append(true);
					whereUsed = true;
				}

				if (!searchParameters.isSearchAllData()) {

					if (searchProjectId > 0) {

						if (whereUsed) {
							builder.append(" AND ");
						} else {
							builder.append(" WHERE ");
						}

						builder.append(" project.id = ").append(searchProjectId);
						whereUsed = true;
					}

					if (searchActivityId > 0) {

						if (whereUsed) {
							builder.append(" AND ");
						} else {
							builder.append(" WHERE ");
							whereUsed = true;
						}

						builder.append(" activity.id = ").append(searchActivityId);
					}

					if (searchUserId > 0) {

						if (whereUsed) {
							builder.append(" AND ");
						} else {
							builder.append(" WHERE ");
						}

						builder.append(" user.id = ").append(searchUserId);
					}
				}

				builder.append(" GROUP BY ");
				builder.append(" project.id, user.id ");

				builder.append(" ORDER BY ");
				builder.append(" project.id, user.id ");

				Query query = session.createQuery(builder.toString());

				@SuppressWarnings("rawtypes")
				List list = query.list();

				if ((list != null) && (list.size() > 0)) {

					// create a new data object
					ReportRow row = null;
					Object[] values = null;

					long projId = 0;
					long userId = 0;

					for (int i = 0; i < list.size(); i++) {
						values = (Object[]) list.get(i);

						// get values
						projId = (Long) values[0];
						userId = (Long) values[1];

						// create new row
						row = new ReportRow();

						// populate row
						row.setProjectId(projId);
						row.setRowId(userId);
						row.setRowTime(TextHelper.getScaledDouble((BigDecimal) values[2]));

						// update the users in reply
						reply.addUserRow(row);

						// update the lists that fetch names later
						projectIdSet.add(projId);
						userIdSet.add(userId);
					}
				}
			}

			if (reply.getRowCount() > 0) {

				Map<Long, String> actMap = new HashMap<Long, String>();
				Map<Long, String> projMap = new HashMap<Long, String>();
				Map<Long, String> usrMap = new HashMap<Long, String>();

				// fetch all project & activity names
				{
					Criteria criteria = session.createCriteria(Project.class);

					if (searchActiveProjects) {
						criteria.add(Restrictions.eq("active", Boolean.TRUE));
					}

					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<Project> list = criteria.list();

					if ((list != null) && (list.size() > 0)) {

						for (Project project : list) {
							projMap.put(project.getId(), project.getName());

							if (project.hasActivities()) {
								for (Activity activity : project.getActivities()) {
									actMap.put(activity.getId(), activity.getName());
								}
							}
						}
					}
				}

				// fetch all user names
				{
					Criteria criteria = session.createCriteria(User.class);

					if (searchActiveUsers) {
						criteria.add(Restrictions.eq("active", Boolean.TRUE));
					}

					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<User> list = criteria.list();

					if ((list != null) && (list.size() > 0)) {

						for (User usr : list) {
							usrMap.put(usr.getId(), usr.getUserName());
						}
					}
				}

				// populate the names in reply
				for (Long projectId : projectIdSet) {

					// activity names
					{
						List<ReportRow> activities = reply.getActivityRowList(projectId);

						if ((activities != null) && (activities.size() > 0)) {

							for (ReportRow reportRow : activities) {
								reportRow.setProjectName(projMap.get(projectId));
								reportRow.setRowName(actMap.get(reportRow.getRowId()));
							}
						}
					}

					// user names
					{
						List<ReportRow> users = reply.getUserRowList(projectId);

						if ((users != null) && (users.size() > 0)) {

							for (ReportRow reportRow : users) {
								reportRow.setProjectName(projMap.get(projectId));
								reportRow.setRowName(usrMap.get(reportRow.getRowId()));
							}
						}
					}
				}

			} else {
				// No results found
				reply.setErrorMessage("No results found.");
			}

			TextHelper.logMessage("getReport() > Time taken : " + ((System.nanoTime() - time) / 1000000));

			// commit the transaction
			transaction.commit();
		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

			// create a reply for error message
			reply.setErrorMessage("Search failed due to Technical Reasons.");

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}
		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#deleteTimeData(long)
	 */

	public CodeValueReply modifyActivity(CodeValueInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if ((input != null) && (input.getCodeValue() != null)) {

			final long id = input.getCodeValue().getCode();
			final String newActivtyText = input.getCodeValue().getValue();

			final String newText = TextHelper.trimToNull(newActivtyText);

			if ((id > 0) && (newText != null)) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();
					final Long activityId = id;

					Project activity = (Project) session.get(Project.class, activityId);

					if (activity != null) {
						activity.setName(newText);
						session.update(activity);
						reply.setSuccessMessage("Updated successfully.");
						reply.setCodeValue(new CodeValue(activity.getId(), activity.getName()));

					} else {
						reply.setErrorMessage("Specified activity not present in system.");
					}

					TextHelper.logMessage("modifyActivity() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Update failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (id <= 0) {
					reply.setErrorMessage("Invalid activity specified.");
				} else if (newText == null) {
					reply.setErrorMessage("Invalid activity name specified.");
				}
			}
		}

		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#createUserPreferences
	 * (long, long)
	 */

	public CodeValueReply modifyProject(ProjectInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();
		final String newText = TextHelper.trimToNull(input.getNewLabelText());
		final long id = input.getProjectId();
		final long leadId = input.getLeadId();

		if ((id > 0) && ((newText != null) || (leadId > 0))) {

			try {
				// create data, hence using AuditableSession()
				session = getAuditableSession();
				transaction = session.beginTransaction();

				long time = System.nanoTime();
				final Long projectId = id;
				final Long leadID = leadId;
				Project project = null;

				Criteria criteria = session.createCriteria(Project.class);
				criteria.add(Restrictions.and(Restrictions.eq("name", newText), Restrictions.ne("id", projectId)));
				criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

				@SuppressWarnings("unchecked")
				List<Project> list = criteria.list();

				if ((list != null) && (list.size() > 0)) {

					// already a project exists with given name.
					reply.setErrorMessage("This project name is already present in system.");

				} else {

					project = (Project) session.get(Project.class, projectId);
					User lead = null;

					if (leadId > 0) {
						lead = (User) session.get(User.class, leadID);
					}

					boolean leadSaved = false;

					if (project != null) {

						if (newText != null) {
							project.setName(newText);
						}

						if (lead != null) {
							leadSaved = true;
							project.setLead(lead);
						}

						session.update(project);
						reply.setSuccessMessage("Updated successfully.");

						// populate the code value
						{
							CodeValue codeValue = new CodeValue(project.getId());

							if (leadSaved) {
								codeValue.setValue(project.getLead().getUserName());
							} else {
								codeValue.setValue(project.getName());
							}

							reply.setCodeValue(codeValue);
						}

					} else {
						reply.setErrorMessage("Specified project not present in system.");
					}
				}

				TextHelper.logMessage("modifyProject() > Time taken : " + ((System.nanoTime() - time) / 1000000));

				// commit the transaction
				transaction.commit();

			} catch (HibernateException hibernateException) {

				// rollback transaction
				if (transaction != null) {
					transaction.rollback();
				}

				hibernateException.printStackTrace();

				// create a reply for error message
				reply.setErrorMessage("Update failed due to Technical Reasons.");

			} finally {
				// close the session
				if (session != null) {
					session.close();
				}
			}
		} else {
			if (id <= 0) {
				reply.setErrorMessage("Invalid project specified.");
			} else if (newText == null) {
				reply.setErrorMessage("Invalid project name specified.");
			}
		}
		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#deleteUserPreferences
	 * (long)
	 */

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#modifyTimeData(org
	 * .ag.timeline.presentation.transferobject.input.TimeDataInput)
	 */
	public CodeValueReply modifyTimeData(TimeDataInput myTimeData) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if (myTimeData != null) {

			long entryId = myTimeData.getId();

			if (entryId > 0) {

				try {

					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();

					TimeData data = (TimeData) session.get(TimeData.class, new Long(entryId));
					Activity activity = (Activity) session.get(Activity.class, new Long(myTimeData.getActivityId()));

					if ((data != null) && (activity != null)) {

						// check if there is already an entry with same
						// activity, user and week
						{

							boolean hasConflict = false;

							if (data.getActivity().getId() != activity.getId()) {

								// search for existing activities
								Criteria criteria = session.createCriteria(TimeData.class);
								criteria.createAlias("user", "usr");
								criteria.createAlias("activity", "act");
								criteria.createAlias("project", "proj");
								criteria.createAlias("week", "wk");
								criteria.add(Restrictions.and(
										Restrictions.eq("wk.weekNumber", new Long(data.getWeek().getWeekNumber())),
										Restrictions.eq("wk.year", new Long(data.getWeek().getYear())),
										Restrictions.eq("usr.id", new Long(data.getUser().getId())),
										Restrictions.eq("act.id", new Long(activity.getId())),
										Restrictions.eq("proj.id", new Long(activity.getProject().getId()))));

								criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

								@SuppressWarnings("unchecked")
								List<TimeData> list = criteria.list();

								if ((list != null) && (list.size() > 0)) {
									hasConflict = true;
								}

							}

							if (hasConflict) {

								// user is trying to add same activity as
								// existing
								reply.setErrorMessage("An entry for same user, project, activity and week already exists.");

							} else {

								// update the entry
								data.setActivity(activity);
								data.setProject(activity.getProject());

								data.setData_weekday_1(TextHelper.getScaledBigDecimal(myTimeData.getDay_1_time()));
								data.setData_weekday_2(TextHelper.getScaledBigDecimal(myTimeData.getDay_2_time()));
								data.setData_weekday_3(TextHelper.getScaledBigDecimal(myTimeData.getDay_3_time()));
								data.setData_weekday_4(TextHelper.getScaledBigDecimal(myTimeData.getDay_4_time()));
								data.setData_weekday_5(TextHelper.getScaledBigDecimal(myTimeData.getDay_5_time()));
								data.setData_weekday_6(TextHelper.getScaledBigDecimal(myTimeData.getDay_6_time()));
								data.setData_weekday_7(TextHelper.getScaledBigDecimal(myTimeData.getDay_7_time()));

								// save the data
								session.saveOrUpdate(data);

								reply.setCodeValue(new CodeValue(data.getId()));
								reply.setSuccessMessage("Saved successfully.");
							}
						}
					} else {
						reply.setErrorMessage("Mandatory data is missing.");
					}

					TextHelper.logMessage("modifyTimeData() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Save failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				reply.setErrorMessage("Cannot modify entry as it is non-existent.");
			}

		} else {
			if (myTimeData == null) {
				reply.setErrorMessage("Invalid data specified.");
			}
		}

		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#modifyUser(org.ag
	 * .timeline.presentation.transferobject.input.UserInput)
	 */
	public UserReply modifyUser(final UserInput input) throws TimelineException {

		final UserReply reply = new UserReply();

		if (input != null) {
			Session session = null;
			Transaction transaction = null;

			final long id = input.getId();
			final String first = TextHelper.trimToNull(input.getFirstName());
			final String last = TextHelper.trimToNull(input.getLastName());
			final String userId = TextHelper.trimToNull(input.getUserId());
			final String password = TextHelper.trimToNull(input.getPassword());
			final boolean adminFlag = input.isAdmin();
			final boolean activeFlag = input.isActive();
			final TimelineConstants.UserDataFieldType type = input.getType();

			if (id > 0) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();
					User user = (User) session.get(User.class, new Long(id));

					if (user == null) {

						// user does not exists in system
						reply.setErrorMessage("Specified user not present in system.");

					} else {

						List<User> list = null;

						if (userId != null) {
							Criteria criteria = session.createCriteria(User.class);
							criteria.add(Restrictions.and(Restrictions.ne("id", id), Restrictions.eq("userId", userId)));
							criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

							@SuppressWarnings("unchecked")
							List<User> users = criteria.list();
							list = users;
						}

						if ((list != null) && (list.size() > 0)) {

							// already a user exists with given
							// name.
							reply.setErrorMessage("This user id is already present in system.");
						} else {

							if (type != null) {
								switch (type) {
									case ADMIN:
										user.setAdmin(adminFlag);
										break;
									case FIRST_NAME:
										user.setFirstName(first);
										break;
									case LAST_NAME:
										user.setLastName(last);
										break;
									case PASSWORD:
										user.setPassword(password);
										break;
									case USER_ID:
										user.setUserId(userId);
										break;
									case ACTIVE:
										user.setActive(activeFlag);
										break;
									default:
										break;
								}
							} else {
								user.setFirstName(first);
								user.setLastName(last);
								user.setUserId(userId);
								user.setAdmin(input.isAdmin());
								user.setActive(input.isActive());
							}

							session.update(user);
							reply.setSuccessMessage("Updated successfully.");
							reply.setUser(user);
						}
					}

					TextHelper.logMessage("modifyUser() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Update failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (id <= 0) {
					reply.setErrorMessage("Invalid user specified.");
				} else {
					if ((first == null) || (last == null)) {
						reply.setErrorMessage("Invalid user name specified.");
					}
				}
			}
		} else {
			reply.setMsgError(true);
			reply.setErrorMessage("No user specified.");
		}

		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#saveUserPreferences
	 * (org.ag.timeline.presentation.transferobject.input.UserPreferencesInput)
	 */
	public UserPreferenceReply saveUserPreferences(final UserPreferencesInput input) throws TimelineException {

		final UserPreferenceReply reply = new UserPreferenceReply();

		if (input != null) {
			Session session = null;
			Transaction transaction = null;

			final long userDbId = input.getUserDbId();
			final String question = TextHelper.trimToNull(input.getQuestion());
			final String answer = TextHelper.trimToNull(input.getAnswer());
			final String email = TextHelper.trimToNull(input.getEmail());
			final TimelineConstants.UserPrefDataFieldType type = input.getType();

			if (userDbId > 0) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();
					final Long userId = new Long(userDbId);

					long time = System.nanoTime();
					User user = (User) session.get(User.class, userId);

					if (user == null) {

						// user does not exists in system
						reply.setErrorMessage("Specified user not present in system.");

					} else {

						Criteria criteria = session.createCriteria(UserPreferences.class);
						criteria.createAlias("user", "usr");
						criteria.add(Restrictions.eq("usr.id", userId));
						criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

						@SuppressWarnings("unchecked")
						List<UserPreferences> list = criteria.list();
						UserPreferences preferences = null;

						if ((list != null) && (list.size() > 0)) {
							preferences = list.get(0);
						}

						// create new if not existing
						if (preferences == null) {
							preferences = new UserPreferences();
							preferences.setUser(user);
						}

						if (type == null) {

							preferences.setQuestion(question);
							preferences.setAnswer(answer);

						} else {
							switch (type) {
								case QUESTION:
									preferences.setQuestion(question);
									break;
								case ANSWER:
									preferences.setAnswer(answer);
									break;
								case EMAIL:
									preferences.setEmail(email);
									break;
								default:
									break;
							}
						}

						session.saveOrUpdate(preferences);
						reply.setSuccessMessage("Saved successfully.");
						reply.setPreference(preferences);
					}

					TextHelper.logMessage("modifyUserPreferences() > Time taken : "
							+ ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Update failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (userDbId <= 0) {
					reply.setErrorMessage("Invalid user specified.");
				} else {
					if ((question == null) || (answer == null)) {
						reply.setErrorMessage("Invalid data specified.");
					}
				}
			}
		} else {
			reply.setMsgError(true);
			reply.setErrorMessage("No user specified.");
		}

		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#resetUserCredentials
	 * (org.ag.timeline.presentation.transferobject.input.CodeValueInput)
	 */
	public UserReply resetUserCredentials(CodeValueInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final UserReply reply = new UserReply();

		if ((input != null) && (input.getCodeValue() != null)) {

			final long id = input.getCodeValue().getCode();

			if (id > 0) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();
					final Long userId = id;

					User user = (User) session.get(User.class, userId);

					if (user != null) {
						user.setPassword("default");
						session.saveOrUpdate(user);

						reply.setSuccessMessage("Reset successfully.");
						reply.setUser(user);
					} else {
						reply.setErrorMessage("Specified user not present in system.");
					}

					TextHelper.logMessage("resetUserCredentials() > Time taken : "
							+ ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Reset failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (id <= 0) {
					reply.setErrorMessage("Invalid user specified.");
				}
			}
		}

		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#searchActivities
	 * (org.
	 * ag.timeline.presentation.transferobject.search.ActivitySearchParameter)
	 */
	public ActivityReply searchActivities(ActivitySearchParameter searchParameters) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final ActivityReply reply = new ActivityReply();

		try {
			// read data, hence using normal session()
			session = getNormalSession();
			transaction = session.beginTransaction();

			long time = System.nanoTime();
			Long projectId = 0l;
			String projectName = null;
			Long activityId = 0l;
			String activityName = null;

			Criteria criteria = session.createCriteria(Activity.class);
			criteria.add(Restrictions.isNotNull("project"));

			if (searchParameters != null) {
				projectId = searchParameters.getProjectId();
				projectName = TextHelper.trimToNull(searchParameters.getProjectName());
				activityId = searchParameters.getActivityId();
				activityName = TextHelper.trimToNull(searchParameters.getActivityName());
			}

			if (activityId > 0) {
				criteria.add(Restrictions.eq("id", activityId));
			}

			if (activityName != null) {
				criteria.add(Restrictions.ilike("name", activityName.toLowerCase(), MatchMode.ANYWHERE));
			}

			if (projectId > 0) {
				criteria.createAlias("project", "p");
				criteria.add(Restrictions.eq("p.id", projectId));
			}

			if (projectName != null) {
				criteria.createAlias("project", "p");
				criteria.add(Restrictions.ilike("p.name", projectName.toLowerCase(), MatchMode.ANYWHERE));
			}

			// add order
			criteria.addOrder(Order.asc("name"));

			criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

			@SuppressWarnings("unchecked")
			List<Activity> dbList = criteria.list();

			if ((dbList != null) && (dbList.size() > 0)) {

				for (Activity label : dbList) {
					reply.addActivity(label.getProject().getId(), label.getProject().getName(), label.getId(),
							label.getName());
				}

			} else {
				// No results found
				reply.setErrorMessage("No results found.");
			}

			TextHelper.logMessage("searchProjects() > Time taken : " + ((System.nanoTime() - time) / 1000000));

			// commit the transaction
			transaction.commit();

		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

			// create a reply for error message
			reply.setErrorMessage("Search failed due to Technical Reasons.");

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}
		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#searchProjects(org
	 * .ag.timeline.presentation.transferobject.search.ProjectSearchParameter)
	 */
	public ProjectReply searchProjects(ProjectSearchParameter searchParameters) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final ProjectReply reply = new ProjectReply();

		try {
			// read data, hence using normal session()
			session = getNormalSession();
			transaction = session.beginTransaction();

			long time = System.nanoTime();
			Long projectId = 0l;
			String projectName = null;
			boolean searchActiveProjects = false;

			Criteria criteria = session.createCriteria(Project.class);

			if (searchParameters != null) {
				projectId = searchParameters.getProjectId();
				projectName = TextHelper.trimToNull(searchParameters.getProjectName());
				searchActiveProjects = searchParameters.isSearchActiveProjects();
			}

			if (projectId > 0) {
				criteria.add(Restrictions.eq("id", projectId));
			}

			if (projectName != null) {
				criteria.add(Restrictions.ilike("name", projectName.toLowerCase(), MatchMode.ANYWHERE));
			}

			if (searchActiveProjects) {
				criteria.add(Restrictions.eq("active", Boolean.TRUE));
			}

			// add order
			criteria.addOrder(Order.asc("name"));
			criteria.addOrder(Order.desc("active"));

			criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

			@SuppressWarnings("unchecked")
			List<Project> dbList = criteria.list();

			if ((dbList != null) && (dbList.size() > 0)) {

				ProjectData projectData = null;

				for (Project project : dbList) {
					projectData = new ProjectData();
					projectData.setCode(project.getId());
					projectData.setValue(project.getName());
					projectData.setActive(project.isActive());

					if (project.getLead() != null) {
						projectData.setLeadName(project.getLead().getUserName());
						projectData.setLeadDbId(project.getLead().getId());
					}

					reply.addProject(projectData);
				}

			} else {
				// No results found
				reply.setErrorMessage("No results found.");
			}

			TextHelper.logMessage("searchProjects() > Time taken : " + ((System.nanoTime() - time) / 1000000));

			// commit the transaction
			transaction.commit();

		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

			// create a reply for error message
			reply.setErrorMessage("Search failed due to Technical Reasons.");

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}
		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#searchTimeData(org
	 * .ag.timeline.presentation.transferobject.search.TimeDataSearchParameters)
	 */
	public TimeDataReply searchTimeData(TimeDataSearchParameters searchParameters) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final TimeDataReply reply = new TimeDataReply();

		try {
			// read data, hence using normal session()
			session = getNormalSession();
			transaction = session.beginTransaction();

			long time = System.nanoTime();
			Long userId = 0l;
			Long projectId = 0l;
			Long activityId = 0l;

			Long startWeekId = 0l;
			Long startWeekNum = 0l;
			Long startYear = 0l;

			Long endWeekId = 0l;
			Long endWeekNum = 0l;
			Long endYear = 0l;

			Criteria criteria = session.createCriteria(TimeData.class);

			if (searchParameters != null) {
				userId = searchParameters.getUserId();
				projectId = searchParameters.getProjectId();
				activityId = searchParameters.getActivityid();

				startWeekId = searchParameters.getStartWeekId();
				startWeekNum = searchParameters.getStartWeekNum();
				startYear = searchParameters.getStartYear();

				endWeekId = searchParameters.getEndWeekId();
				endWeekNum = searchParameters.getEndWeekNum();
				endYear = searchParameters.getEndYear();

			}

			criteria.createAlias("user", "usr");
			criteria.createAlias("week", "wk");
			criteria.createAlias("project", "proj");
			criteria.createAlias("activity", "act");

			User user = (User) session.get(User.class, userId);

			if ((user != null) && (!user.isAdmin())) {
				criteria.add(Restrictions.eq("usr.id", user.getId()));
			} else if ((userId > 0) && (user.isAdmin())) {
				criteria.add(Restrictions.eq("usr.id", userId));
			}

			if (projectId > 0) {
				criteria.add(Restrictions.eq("proj.id", projectId));
			}

			if (activityId > 0) {
				criteria.add(Restrictions.eq("act.id", activityId));
			}

			if (startWeekId > 0) {
				criteria.add(Restrictions.ge("wk.id", startWeekId));
			}

			if (endWeekId > 0) {
				criteria.add(Restrictions.le("wk.id", endWeekId));
			}

			// add date restrictions
			if ((startWeekNum > 0) && (startYear > 0) && (endWeekNum > 0) && (endYear > 0)) {

				{
					Criteria subCriteria = session.createCriteria(Week.class);
					subCriteria.add(Restrictions.and(Restrictions.eq("year", startYear),
							Restrictions.eq("weekNumber", startWeekNum)));
					subCriteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<Week> list = subCriteria.list();

					if ((list != null) && (list.size() > 0)) {
						startWeekId = ((Week) list.get(0)).getId();
					}
				}

				{
					Criteria subCriteria = session.createCriteria(Week.class);
					subCriteria.add(Restrictions.and(Restrictions.eq("year", endYear),
							Restrictions.eq("weekNumber", endWeekNum)));
					subCriteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<Week> list = subCriteria.list();

					if ((list != null) && (list.size() > 0)) {
						endWeekId = ((Week) list.get(0)).getId();
					}
				}

				criteria.add(Restrictions.and(Restrictions.between("wk.year", startYear, endYear),
						Restrictions.between("wk.id", startWeekId, endWeekId)));

			} else if ((startWeekNum > 0) && (startYear > 0) && ((endWeekNum <= 0) || ((endYear <= 0)))) {

				criteria.add(Restrictions.and(Restrictions.ge("wk.year", startYear),
						Restrictions.ge("wk.weekNumber", startWeekNum)));

			} else if ((endWeekNum > 0) && (endYear > 0) && ((startWeekNum <= 0) || ((startYear <= 0)))) {

				criteria.add(Restrictions.and(Restrictions.le("wk.year", endYear),
						Restrictions.le("wk.weekNumber", endWeekNum)));

			}

			criteria.addOrder(Order.asc("usr.id"));
			criteria.addOrder(Order.asc("wk.weekNumber"));
			criteria.addOrder(Order.asc("wk.year"));
			criteria.addOrder(Order.asc("proj.id"));
			criteria.addOrder(Order.asc("act.id"));

			criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

			@SuppressWarnings("unchecked")
			List<TimeData> dbList = criteria.list();

			if ((dbList != null) && (dbList.size() > 0)) {

				for (TimeData data : dbList) {
					reply.addTimeData(data);
				}

			} else {
				// No results found
				reply.setErrorMessage("No results found.");
			}

			TextHelper.logMessage("searchTimeData() > Time taken : " + ((System.nanoTime() - time) / 1000000));

			// commit the transaction
			transaction.commit();
		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

			// create a reply for error message
			reply.setErrorMessage("Search failed due to Technical Reasons.");

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}
		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#searchUserPreferences
	 * (org.ag.timeline.presentation.transferobject.search.
	 * UserPreferenceSearchParameter)
	 */
	public UserPreferenceSearchReply searchUserPreferences(UserPreferenceSearchParameter searchParameters)
			throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final UserPreferenceSearchReply reply = new UserPreferenceSearchReply();

		try {
			// read data, hence using normal session()
			session = getNormalSession();
			transaction = session.beginTransaction();

			long time = System.nanoTime();
			Long id = 0l;
			String userId = TextHelper.trimToNull(searchParameters.getUserId());

			Criteria criteria = session.createCriteria(UserPreferences.class);
			criteria.createAlias("user", "usr");

			if (searchParameters != null) {
				id = searchParameters.getId();
			}

			if (id > 0) {
				criteria.add(Restrictions.eq("usr.id", id));
			}

			if (userId != null) {
				criteria.add(Restrictions.eq("usr.userId", userId));
			}

			// add order
			criteria.addOrder(Order.asc("usr.userId"));
			criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

			@SuppressWarnings("unchecked")
			List<UserPreferences> dbList = criteria.list();

			if ((dbList != null) && (dbList.size() > 0)) {

				for (UserPreferences preference : dbList) {
					reply.addPreference(preference);
				}

			} else {
				// No results found
				reply.setErrorMessage("No results found.");
			}

			TextHelper.logMessage("searchUserPreferences() > Time taken : " + ((System.nanoTime() - time) / 1000000));

			// commit the transaction
			transaction.commit();

		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

			// create a reply for error message
			reply.setErrorMessage("Search failed due to Technical Reasons.");

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}
		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#searchUsers(org.
	 * ag.timeline.presentation.transferobject.search.UserSearchParameter)
	 */
	public UserSearchReply searchUsers(UserSearchParameter searchParameters) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final UserSearchReply reply = new UserSearchReply();

		try {
			// read data, hence using normal session()
			session = getNormalSession();
			transaction = session.beginTransaction();

			long time = System.nanoTime();
			Long userId = 0l;
			String firstName = null;
			String lastName = null;
			boolean admin = false;
			boolean active = false;

			Criteria criteria = session.createCriteria(User.class);

			if (searchParameters != null) {
				userId = searchParameters.getId();
				firstName = TextHelper.trimToNull(searchParameters.getFirstName());
				lastName = TextHelper.trimToNull(searchParameters.getLastName());
				admin = searchParameters.getOnlyAdmin();
				active = searchParameters.getOnlyActive();
			}

			if (userId > 0) {
				criteria.add(Restrictions.eq("id", userId));
			}

			if (firstName != null) {
				criteria.add(Restrictions.ilike("firstName", firstName, MatchMode.ANYWHERE));
			}

			if (lastName != null) {
				criteria.add(Restrictions.ilike("lastName", lastName, MatchMode.ANYWHERE));
			}

			if (admin) {
				criteria.add(Restrictions.eq("admin", Boolean.TRUE));
			}

			if (active) {
				criteria.add(Restrictions.eq("active", Boolean.TRUE));
			}

			// remove admin getting fetched
			criteria.add(Restrictions.gt("id", new Long(0)));

			// add order
			criteria.addOrder(Order.asc("admin"));
			criteria.addOrder(Order.asc("userId"));
			criteria.addOrder(Order.desc("active"));

			criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

			@SuppressWarnings("unchecked")
			List<User> dbList = criteria.list();

			if ((dbList != null) && (dbList.size() > 0)) {
				for (User user : dbList) {
					reply.addUsers(user);
				}

			} else {
				// No results found
				reply.setErrorMessage("No results found.");
			}

			TextHelper.logMessage("searchUsers() > Time taken : " + ((System.nanoTime() - time) / 1000000));

			// commit the transaction
			transaction.commit();

		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

			// create a reply for error message
			reply.setErrorMessage("Search failed due to Technical Reasons.");

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}
		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#searchWeeks(org.
	 * ag.timeline.presentation.transferobject.search.WeekSearchParameter)
	 */
	public WeekReply searchWeeks(WeekSearchParameter searchParameters) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final WeekReply reply = new WeekReply();

		try {
			// read data, hence using normal session()
			session = getNormalSession();
			transaction = session.beginTransaction();

			long time = System.nanoTime();
			Long weekId = 0l;
			Long weekNum = 0l;
			Long year = 0l;

			Criteria criteria = session.createCriteria(Week.class);

			if (searchParameters != null) {
				weekId = searchParameters.getWeekId();
				weekNum = searchParameters.getWeekNumber();
				year = searchParameters.getYear();
			}

			if (weekId > 0) {
				criteria.add(Restrictions.eq("id", weekId));
			}

			if (weekNum > 0) {
				criteria.add(Restrictions.eq("weekNum", weekNum));
			}

			if (year > 0) {
				criteria.add(Restrictions.eq("year", year));
			}

			// add order
			criteria.addOrder(Order.asc("year"));
			criteria.addOrder(Order.asc("weekNumber"));

			criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

			@SuppressWarnings("unchecked")
			List<Week> dbList = criteria.list();

			if ((dbList != null) && (dbList.size() > 0)) {

				Set<Week> set = new HashSet<Week>();
				set.addAll(dbList);
				reply.setWeeks(set);

			} else {
				// No results found
				reply.setErrorMessage("No results found.");
			}

			TextHelper.logMessage("searchWeeks() > Time taken : " + ((System.nanoTime() - time) / 1000000));

			// commit the transaction
			transaction.commit();

		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

			// create a reply for error message
			reply.setErrorMessage("Search failed due to Technical Reasons.");

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}
		return reply;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#systemManagement()
	 */
	public void systemManagement() throws TimelineException {
		Session session = null;
		Transaction transaction = null;

		try {
			// create data, hence using AuditableSession()
			session = getNormalSession();
			transaction = session.beginTransaction();

			long time = System.nanoTime();

			// handle week management
			{
				Criteria criteria = session.createCriteria(Week.class);
				criteria.setProjection(Projections.count("id"));
				criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

				@SuppressWarnings("unchecked")
				List<Long> dbList = criteria.list();

				if ((dbList != null) && (dbList.size() > 0)) {

					long weekCount = dbList.get(0);

					// if no week data present (happens when application is
					// installed first time with empty database)
					if (weekCount == 0) {
						criteria = session.createCriteria(SystemSettings.class);
						criteria.add(Restrictions.or(Restrictions.eq("name", SystemSettings.Type.START_DATE.getText()),
								Restrictions.eq("name", SystemSettings.Type.END_DATE.getText())));
						criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

						@SuppressWarnings("unchecked")
						List<SystemSettings> list = criteria.list();

						if ((list != null) && (list.size() > 0)) {

							Date startDate = null;
							Date endDate = null;

							for (SystemSettings setting : list) {

								if ((setting != null)
										&& (SystemSettings.Type.START_DATE.toString().equalsIgnoreCase(setting
												.getName()))) {
									startDate = TextHelper.getValidDate(setting.getValue());
								}

								if ((setting != null)
										&& (SystemSettings.Type.END_DATE.toString().equalsIgnoreCase(setting.getName()))) {
									endDate = TextHelper.getValidDate(setting.getValue());
								}
							}

							if (startDate != null) {

								// create week entries
								long count = 0;
								Week week = null;
								Date start = startDate;
								Date weekStartDate = null;
								Date weekEndDate = null;
								long weekNum = 0;
								long year = 0;
								final Date createDate = new Date();
								final long createUserId = RequestContext.getTimelineContext().getContextUserId();

								while (start.before(endDate)) {

									weekStartDate = TextHelper.getFirstDayOfWeek(start);
									weekEndDate = TextHelper.getLastDayOfWeek(start);
									weekNum = TextHelper.getWeekNumber(start);

									week = new Week();
									week.setStartDate(weekStartDate);
									week.setEndDate(weekEndDate);
									week.setWeekNumber(weekNum);
									/*
									 * if (weekNum > 1) { year =
									 * TextHelper.getYear(weekStartDate); } else
									 * { // Fix for setting the year correctly
									 * as new year // for 1st week of year year
									 * = TextHelper.getYear(weekEndDate); }
									 */
									year = TextHelper.getYearForWeekDay(start);
									week.setYear(year);
									week.setCreateDate(createDate);
									week.setCreateUserId(createUserId);

									// save
									session.saveOrUpdate(week);

									count++;

									if (count % 20 == 0) {

										// flush a batch of inserts and release
										// memory:
										session.flush();
										session.clear();
									}

									// get day a week later
									start = TextHelper.getDateAfter(weekStartDate, 7);

								}
							}
						}
					}
				}
			}

			TextHelper.logMessage("systemManagement() > Time taken : " + ((System.nanoTime() - time) / 1000000));

			// commit the transaction
			transaction.commit();

		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.ag.timeline.business.service.iface.TimelineIface#searchAuditData(
	 * org.ag
	 * .timeline.presentation.transferobject.search.AuditDataSearchParameters)
	 */
	public AuditDataReply searchAuditData(AuditDataSearchParameters searchParameters) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		AuditDataReply reply = new AuditDataReply();

		try {

			session = getNormalSession();
			transaction = session.beginTransaction();

			long time = System.nanoTime();

			if (searchParameters != null) {

				Map<Long, String> userMap = new HashMap<Long, String>();

				{
					Criteria criteria = session.createCriteria(User.class);
					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<User> list = criteria.list();

					if ((list != null) && (list.size() > 0)) {

						for (User user : list) {
							userMap.put(user.getId(), user.getUserName());
						}
					}
				}

				final TimelineConstants.AuditDataType type = searchParameters.getType();
				final TimelineConstants.EntityOperation operationType = searchParameters.getOperationType();
				final long userDbId = searchParameters.getUserId();
				Date fromDate = searchParameters.getFromDate();
				Date toDate = searchParameters.getToDate();

				Criteria criteria = session.createCriteria(AuditRecord.class);

				if (type != null) {
					criteria.add(Restrictions.eq("dataType", type.getTypeId()));
				}

				if (fromDate == null) {
					fromDate = new Date();
				}

				if (toDate == null) {
					toDate = new Date();
				}

				criteria.add(Restrictions.between("operationDate", TextHelper.getAuditDayStartTimestamp(fromDate),
						TextHelper.getAuditDayEndTimestamp(toDate)));

				if (operationType != null) {
					criteria.add(Restrictions.eq("operation", operationType.getOpCode()));
				}

				if (userDbId > 0) {
					criteria.add(Restrictions.eq("userId", userDbId));
				} else {
					criteria.add(Restrictions.gt("userId", Long.valueOf(0)));
				}

				criteria.addOrder(Order.desc("operationTime"));
				criteria.addOrder(Order.asc("entityId"));
				criteria.addOrder(Order.asc("entityName"));
				criteria.addOrder(Order.asc("userId"));

				criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

				@SuppressWarnings("unchecked")
				List<AuditRecord> list = criteria.list();

				if ((list != null) && (list.size() > 0)) {

					AuditRow row = null;
					AuditDetailRow detailRow = null;

					for (AuditRecord record : list) {

						row = new AuditRow();

						row.setOperation((TimelineConstants.EntityOperation) TimelineConstants.EntityOperation
								.getOperation(record.getOperation()));
						row.setOperationDate(record.getOperationDate());
						row.setOperationTime(record.getOperationTime());
						row.setId(record.getId());
						row.setEntityName(record.getEntityName());
						row.setDataType((TimelineConstants.AuditDataType) TimelineConstants.AuditDataType
								.getAuditDataType(record.getDataType()));
						row.setUserName(userMap.get(record.getUserId()));

						for (AuditRecordDetail detail : record.getDetails()) {

							detailRow = new AuditDetailRow();

							// populate the details
							detailRow.setFieldName(detail.getFieldName());
							detailRow.setOldValue(detail.getOldValue());
							detailRow.setNewValue(detail.getNewValue());

							// add to main list
							row.addDetail(detailRow);
						}

						// add to reply
						reply.addAuditRow(row);
					}

				} else {
					reply.setErrorMessage("No results found.");
				}
			}

			TextHelper.logMessage("searchAuditData() > Time taken : " + ((System.nanoTime() - time) / 1000000));

			// commit the transaction
			transaction.commit();

		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}

		return reply;

	}

	public DetailedReportReply getDetailedReport(ReportSearchParameters searchParameters) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		DetailedReportReply reply = new DetailedReportReply();

		try {

			session = getNormalSession();
			transaction = session.beginTransaction();

			long time = System.nanoTime();

			if (searchParameters != null) {
				long projectId = searchParameters.getProjectDbId();

				if (projectId > 0) {

					StringBuilder builder = new StringBuilder(
							" SELECT week.year, week.weekNumber, week.startDate, week.endDate, activity.id, activity.name ");
					builder.append(" , sum(data_weekday_1+data_weekday_2+data_weekday_3+data_weekday_4+data_weekday_5+data_weekday_6+data_weekday_7) ");
					builder.append(" FROM TimeData ");
					builder.append(" WHERE ");
					builder.append(" project.id = ").append(projectId);

					builder.append(" GROUP BY ");
					builder.append(" week.year, week.weekNumber, week.startDate, week.endDate, activity.id, activity.name ");

					builder.append(" ORDER BY ");
					builder.append(" week.year desc , week.weekNumber desc, activity.name asc");

					Query query = session.createQuery(builder.toString());

					@SuppressWarnings("rawtypes")
					List list = query.list();

					if ((list != null) && (list.size() > 0)) {

						// create a new data object
						DetailedReportRow row = null;
						Object[] values = null;

						for (int i = 0; i < list.size(); i++) {
							values = (Object[]) list.get(i);

							if ((values != null) && (values.length > 0)) {

								// create new row
								row = new DetailedReportRow();

								// populate row
								row.setYear((Long) values[0]);
								row.setWeekNumber((Long) values[1]);
								row.setWeekStartDate((Date) values[2]);
								row.setWeekEndDate((Date) values[3]);
								row.setActivityId((Long) values[4]);
								row.setActivityName((String) values[5]);
								row.setWeeklySum(TextHelper.getScaledDouble((BigDecimal) values[6]));

								// update the rows in reply
								reply.addRow(row);
							}
						}
					}
				} else {
					reply.setErrorMessage("Invalid project id specified.");
				}

			} else {
				reply.setErrorMessage("Missing search data.");
			}

			TextHelper.logMessage("getDetailedReport() > Time taken : " + ((System.nanoTime() - time) / 1000000));

			// commit the transaction
			transaction.commit();

		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

			// create a reply for error message
			reply.setErrorMessage("Report Details Search failed due to Technical Reasons.");

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}

		return reply;
	}

	@Override
	public CodeValueReply modifyStatus(StatusInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if (input != null) {
			TimelineConstants.StatusEntity entity = input.getEntity();
			final Long id = input.getEntityId();

			if ((id > 0) && (entity != null)) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();
					String retVal = null;
					boolean hasError = false;

					switch (entity) {
						case PROJECT: {
							Project project = (Project) session.get(Project.class, id);

							if (project == null) {
								hasError = true;
								reply.setErrorMessage("Specified Project is not present in system.");
							} else {
								project.setActive(input.isActive());
								session.update(project);
								retVal = project.getName();
							}
						}
							break;

						case USER: {
							User user = (User) session.get(User.class, id);

							if (user == null) {
								hasError = true;
								reply.setErrorMessage("Specified User is not present in system.");
							} else if (id == RequestContext.getTimelineContext().getContextUserId()) {
								hasError = true;
								reply.setErrorMessage("User can not change own status.");
							} else {
								user.setActive(input.isActive());
								session.update(user);
								retVal = user.getUserName();
							}
						}
							break;

						default:
							hasError = true;
							break;
					}

					if (!hasError) {
						reply.setSuccessMessage("Updated successfully.");

						// populate the code value
						CodeValue codeValue = new CodeValue(id, retVal);

						reply.setCodeValue(codeValue);
					}

					TextHelper.logMessage("modifyStatus() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Status update failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (id <= 0) {
					reply.setErrorMessage("Invalid ID specified.");
				} else if (entity == null) {
					reply.setErrorMessage("Invalid entity specified.");
				}
			}
		}

		return reply;
	}

	@Override
	public WeeklyUserReply searchUsersWithoutEntries(WeekSearchParameter searchParameters) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final WeeklyUserReply reply = new WeeklyUserReply();

		try {
			// read data, hence using normal session()
			session = getNormalSession();
			transaction = session.beginTransaction();

			long time = System.nanoTime();
			Date startDate = null;
			Date endDate = null;
			final Date currentDate = new Date();

			if (searchParameters != null) {
				startDate = searchParameters.getStartDate();
				endDate = searchParameters.getEndDate();
			}

			if (startDate == null) {
				startDate = TextHelper.getFirstDayOfWeek(currentDate);
			}

			if (endDate == null) {
				endDate = TextHelper.getLastDayOfWeek(currentDate);
			}

			{
				Criteria criteria = session.createCriteria(User.class);
				criteria.add(Restrictions.and(Restrictions.eq("active", Boolean.TRUE),
						Restrictions.gt("id", Long.valueOf(0))));
				criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

				@SuppressWarnings("unchecked")
				List<User> activeUsers = criteria.list();

				if ((activeUsers != null) && (activeUsers.size() > 0)) {

					Criteria timeEntryCriteria = session.createCriteria(TimeData.class);
					timeEntryCriteria.createAlias("week", "week");
					timeEntryCriteria.createAlias("user", "user");
					timeEntryCriteria.add(Restrictions.and(Restrictions.ge("week.startDate", startDate),
							Restrictions.le("week.endDate", endDate), Restrictions.eq("user.active", Boolean.TRUE)));
					timeEntryCriteria.addOrder(Order.asc("week"));
					timeEntryCriteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<TimeData> timeEntries = timeEntryCriteria.list();

					if ((timeEntries != null) && (timeEntries.size() > 0)) {

						// populate the existing entries
						for (TimeData timeData : timeEntries) {
							reply.addWeeklyUser(timeData.getWeek(), timeData.getUser());
						}

						// iterate over the collected users & replace
						List<Week> weekIdList = reply.getWeekList();
						List<User> userList = null;
						List<User> missingUserList = null;

						for (Week week : weekIdList) {

							missingUserList = new ArrayList<User>();
							missingUserList.addAll(activeUsers);

							userList = reply.getWeeklyUsers(week.getId());

							if (userList != null) {
								missingUserList.removeAll(userList);
							}

							Collections.sort(missingUserList, new UserComparator());

							reply.setWeeklyUserList(week, missingUserList);
						}
					} else {

						// if no entries in a given period
						Criteria weekCriteria = session.createCriteria(Week.class);
						weekCriteria.add(Restrictions.and(Restrictions.ge("startDate", startDate),
								Restrictions.le("endDate", endDate)));
						weekCriteria.addOrder(Order.asc("id"));
						weekCriteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

						@SuppressWarnings("unchecked")
						List<Week> allWeekList = weekCriteria.list();

						Collections.sort(activeUsers, new UserComparator());

						for (Week week : allWeekList) {
							reply.setWeeklyUserList(week, activeUsers);
						}
					}
				}
			}

			if (reply.getUserCount() == 0) {
				reply.setErrorMessage("No results found.");
			}

			TextHelper.logMessage("searchUsersWithoutEntries() > Time taken : "
					+ ((System.nanoTime() - time) / 1000000));

			// commit the transaction
			transaction.commit();

		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

			// create a reply for error message
			reply.setErrorMessage("Search failed due to Technical Reasons.");

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}
		return reply;
	}

	@Override
	public CodeValueReply createProjectDetailMetrics(ProjectMetricsInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if (input != null) {

			final long projectId = input.getProjectId();

			if (projectId > 0) {

				try {

					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();
					final Long id = projectId;
					Date date = input.getDate();
					Long year = TextHelper.getYear(date);
					Long weekNum = TextHelper.getWeekNumber(date);
					ProjectMetrics metrics = null;

					Criteria criteria = session.createCriteria(ProjectMetrics.class);
					criteria.createAlias("project", "pr");
					criteria.createAlias("week", "wk");
					criteria.add(Restrictions.and(Restrictions.eq("pr.id", id), Restrictions.eq("wk.year", year),
							Restrictions.eq("wk.weekNumber", weekNum)));
					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<ProjectMetrics> list = criteria.list();

					if ((list != null) && (list.size() > 0)) {

						// already a project metric exists with given week &
						// project.
						reply.setErrorMessage("Metrics for this week is already present in project.");

					} else {

						// get project
						Project project = (Project) session.get(Project.class, id);

						if (project != null) {

							// get Week
							criteria = session.createCriteria(Week.class);
							criteria.add(Restrictions.and(Restrictions.eq("year", year),
									Restrictions.eq("weekNumber", weekNum)));
							criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

							@SuppressWarnings("unchecked")
							List<Week> weekList = criteria.list();

							if ((weekList != null) && (weekList.size() > 0)) {

								Week week = weekList.get(0);

								if (week != null) {

									// final double bac =
									// TextHelper.getScaledDouble(project.getBudgetAtCompletion());

									metrics = new ProjectMetrics();

									// relationships
									metrics.setProject(project);
									metrics.setWeek(week);

									// user entered values
									metrics.setPlannedValue(TextHelper.getScaledBigDecimal(input.getPlannedValue()));
									metrics.setEarnedValue(TextHelper.getScaledBigDecimal(input.getEarnedValue()));
									metrics.setActualCost(TextHelper.getScaledBigDecimal(input.getActualCost()));
									metrics.setActualsToDate(TextHelper.getScaledBigDecimal(input.getActualsToDate()));
									metrics.setSoftwareProgrammingEffort(TextHelper.getScaledBigDecimal(input
											.getSoftwareProgrammingEffort()));
									metrics.setDefects(input.getDefects());

									// calculated values
									// metrics.setExpectedToComplete(TextHelper.getScaledBigDecimal(bac
									// - input.getEarnedValue()));
									// metrics.setCostPerformanceIndex(TextHelper.getScaledBigDecimal(input
									// .getEarnedValue() /
									// input.getActualCost()));
									// metrics.setSchedulePerformanceIndex(TextHelper.getScaledBigDecimal(input
									// .getEarnedValue() /
									// input.getPlannedValue()));
									// metrics.setDefectRatio(TextHelper.getScaledBigDecimal(input.getDefects()
									// / input.getSoftwareProgrammingEffort()));

									// save the metric data
									session.saveOrUpdate(metrics);

									reply.setSuccessMessage("Created successfully.");

								} else {
									reply.setErrorMessage("Specified week not present in system.");
								}

							} else {
								reply.setErrorMessage("No week data present in system.");
							}

						} else {
							reply.setErrorMessage("Specified project not present in system.");
						}
					}

					TextHelper.logMessage("createProjectDetailMetrics() > Time taken : "
							+ ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

					if (metrics != null) {
						reply.setCodeValue(new CodeValue(metrics.getId(), metrics.getWeek().getDescription()));
					}

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Create failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				reply.setErrorMessage("Invalid project specified.");
			}
		}

		return reply;
	}

	@Override
	public CodeValueReply deleteProjectMetrics(CodeValueInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if ((input != null) && (input.getCodeValue() != null)) {

			final long projectId = input.getCodeValue().getCode();

			if (projectId > 0) {

				try {

					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();

					// delete entries
					StringBuilder builder = new StringBuilder("DELETE FROM ProjectMetrics WHERE project.id = ");
					builder.append(projectId);

					Query query = session.createQuery(builder.toString());
					int deleteCount = query.executeUpdate();

					StringBuilder msg = new StringBuilder(TimelineConstants.EMPTY);

					if (deleteCount > 0) {
						msg.append(deleteCount).append(" Metric entries deleted successfully. ");
					}

					// delete project estimates
					Project project = (Project) session.get(Project.class, projectId);

					project.setBudgetAtCompletion(TextHelper.getScaledBigDecimal(0));
					project.setStartDate(null);
					project.setEndDate(null);

					session.saveOrUpdate(project);

					msg.append("Project Estimates deleted successfully.");

					reply.setCodeValue(new CodeValue(projectId, project.getName()));
					reply.setSuccessMessage(msg.toString());

					TextHelper.logMessage("deleteProjectMetrics() > Time taken : "
							+ ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Delete failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (projectId <= 0) {
					reply.setErrorMessage("Invalid project specified.");
				}
			}
		}

		return reply;

	}

	@Override
	public CodeValueReply deleteProjectDetailMetrics(CodeValueInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if ((input != null) && (input.getCodeValue() != null)) {

			final long metricId = input.getCodeValue().getCode();

			if (metricId > 0) {

				try {

					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();
					ProjectMetrics metrics = (ProjectMetrics) session.get(ProjectMetrics.class, metricId);

					if (metrics != null) {

						session.delete(metrics);
						reply.setMessage("Deleted Successfully.");

					} else {
						reply.setErrorMessage("Specified project metrics entry not present in System.");
					}

					TextHelper.logMessage("deleteProjectDetailMetrics() > Time taken : "
							+ ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Delete failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (metricId <= 0) {
					reply.setErrorMessage("Invalid project metric entry specified.");
				}
			}
		}

		return reply;
	}

	@Override
	public CodeValueReply modifyProjectDetailMetrics(ProjectMetricsInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if (input != null) {

			final long metricId = input.getMetricId();

			if (metricId > 0) {

				try {

					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();
					final Long id = metricId;
					ProjectMetrics metrics = (ProjectMetrics) session.get(ProjectMetrics.class, id);

					if (metrics == null) {

						// non-existent metric entry
						reply.setErrorMessage("Specified metrics entry not present in System.");

					} else {

						// final double bac =
						// TextHelper.getScaledDouble(metrics.getProject().getBudgetAtCompletion());

						// user entered values
						metrics.setPlannedValue(TextHelper.getScaledBigDecimal(input.getPlannedValue()));
						metrics.setEarnedValue(TextHelper.getScaledBigDecimal(input.getEarnedValue()));
						metrics.setActualCost(TextHelper.getScaledBigDecimal(input.getActualCost()));
						metrics.setActualsToDate(TextHelper.getScaledBigDecimal(input.getActualsToDate()));
						metrics.setSoftwareProgrammingEffort(TextHelper.getScaledBigDecimal(input
								.getSoftwareProgrammingEffort()));
						metrics.setDefects(input.getDefects());

						// calculated values
						// metrics.setExpectedToComplete(TextHelper.getScaledBigDecimal(bac
						// - input.getEarnedValue()));
						// metrics.setCostPerformanceIndex(TextHelper.getScaledBigDecimal(input.getEarnedValue()
						// / input.getActualCost()));
						// metrics.setSchedulePerformanceIndex(TextHelper.getScaledBigDecimal(input.getEarnedValue()
						// / input.getPlannedValue()));
						// metrics.setDefectRatio(TextHelper.getScaledBigDecimal(input.getDefects()
						// / input.getSoftwareProgrammingEffort()));

						// save the metric data
						session.saveOrUpdate(metrics);

						reply.setSuccessMessage("Modified successfully.");
					}

					TextHelper.logMessage("modifyProjectDetailMetrics() > Time taken : "
							+ ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

					if (metrics != null) {
						reply.setCodeValue(new CodeValue(metrics.getId(), metrics.getWeek().getDescription()));
					}

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Create failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				reply.setErrorMessage("Invalid project metric entry specified.");
			}
		}

		return reply;

	}

	@Override
	public CodeValueReply saveProjectEstimates(ProjectEstimatesInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if ((input != null) && (input.getEstimateData() != null) && (input.getEstimateData().getProjectData() != null)) {

			final long projectId = input.getEstimateData().getProjectData().getCode();
			final double bac = input.getEstimateData().getBudgetAtCompletion();
			final Date startDate = input.getEstimateData().getStartDate();
			final Date endDate = input.getEstimateData().getEndDate();

			if ((projectId > 0) && (bac > 0) && (startDate != null) && (endDate != null)) {

				try {

					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();
					final Long id = projectId;
					Project project = (Project) session.get(Project.class, id);

					if (project == null) {

						// already a project exists with given name.
						reply.setErrorMessage("Specified project is not present in System.");

					} else {

						// populate values
						project.setBudgetAtCompletion(TextHelper.getScaledBigDecimal(bac));
						project.setStartDate(startDate);
						project.setEndDate(endDate);

						// save project
						session.saveOrUpdate(project);
						reply.setCodeValue(new CodeValue(projectId, project.getName()));
						reply.setSuccessMessage("Saved successfully.");

					}

					TextHelper.logMessage("saveProjectEstimates() > Time taken : "
							+ ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Save failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (projectId <= 0) {
					reply.setErrorMessage("Invalid project specified.");
				} else {
					reply.setErrorMessage("Invalid value(s) specified.");
				}
			}
		}

		return reply;

	}

	@Override
	public ProjectEstimatesReply searchProjectEstimates(ProjectSearchParameter searchParameters)
			throws TimelineException {
		Session session = null;
		Transaction transaction = null;
		final ProjectEstimatesReply reply = new ProjectEstimatesReply();

		try {
			// read data, hence using normal session()
			session = getNormalSession();
			transaction = session.beginTransaction();

			long time = System.nanoTime();
			Long projectId = 0l;
			String projectName = null;

			Criteria criteria = session.createCriteria(Project.class);

			if (searchParameters != null) {
				projectId = searchParameters.getProjectId();
				projectName = TextHelper.trimToNull(searchParameters.getProjectName());
			}

			if (projectId > 0) {
				criteria.add(Restrictions.eq("id", projectId));
			}

			if (projectName != null) {
				criteria.add(Restrictions.ilike("name", projectName.toLowerCase(), MatchMode.ANYWHERE));
			}

			// add order
			criteria.addOrder(Order.asc("name"));

			criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

			@SuppressWarnings("unchecked")
			List<Project> dbList = criteria.list();

			if ((dbList != null) && (dbList.size() > 0)) {

				ProjectData projectData = null;
				ProjectEstimateData estimate = null;
				StringBuilder builder = new StringBuilder(
						" FROM Week as wk where wk.id = (SELECT max(week.id) FROM ProjectMetrics WHERE project.id = ?)");
				Query query = null;
				Week lastSubmittedWeek = null;

				for (Project project : dbList) {
					projectData = new ProjectData();
					projectData.setCode(project.getId());
					projectData.setValue(project.getName());

					if (project.getLead() != null) {
						projectData.setLeadName(project.getLead().getUserName());
					}

					estimate = new ProjectEstimateData();
					estimate.setProjectData(projectData);

					estimate.setBudgetAtCompletion(TextHelper.getScaledDouble(project.getBudgetAtCompletion()));
					estimate.setStartDate(project.getStartDate());
					estimate.setEndDate(project.getEndDate());

					try {
						query = session.createQuery(builder.toString()).setLong(0, project.getId());
						lastSubmittedWeek = (Week) query.uniqueResult();

						if (lastSubmittedWeek != null) {
							estimate.setLastSubmittedPeriod(TextHelper.getDisplayWeek(lastSubmittedWeek.getStartDate(),
									lastSubmittedWeek.getEndDate()));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					reply.addEstimates(estimate);
				}

			} else {
				// No results found
				reply.setErrorMessage("No results found.");
			}

			TextHelper.logMessage("searchProjectEstimates() > Time taken : " + ((System.nanoTime() - time) / 1000000));

			// commit the transaction
			transaction.commit();

		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

			// create a reply for error message
			reply.setErrorMessage("Search failed due to Technical Reasons.");

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}
		return reply;
	}

	@Override
	public CodeValueListReply searchTasks(ProjectSearchParameter searchParameters) throws TimelineException {
		Session session = null;
		Transaction transaction = null;
		final CodeValueListReply reply = new CodeValueListReply();

		try {
			Long projectId = 0l;

			if (searchParameters != null) {
				projectId = searchParameters.getProjectId();

				if (projectId > 0) {

					// read data, hence using normal session()
					session = getNormalSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();

					Criteria criteria = session.createCriteria(Task.class);
					criteria.createAlias("project", "p");
					criteria.add(Restrictions.eq("p.id", projectId));

					// add order
					criteria.addOrder(Order.asc("taskText"));

					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<Task> dbList = criteria.list();

					if ((dbList != null) && (dbList.size() > 0)) {

						for (Task task : dbList) {
							reply.addCodeValue(new CodeValue(task.getId(), task.getTaskText()));
						}

					} else {
						// No results found
						reply.setErrorMessage("No results found.");
					}

					TextHelper.logMessage("searchTasks() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();
				}
			}

		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

			// create a reply for error message
			reply.setErrorMessage("Search failed due to Technical Reasons.");

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}
		return reply;
	}

	@Override
	public ProjectLevelMetricsReply getProjectLevelMetricsReport(ProjectMetricsSearchParameters searchParameters)
			throws TimelineException {

		final ProjectLevelMetricsReply reply = new ProjectLevelMetricsReply();

		Session session = null;
		Transaction transaction = null;

		try {

			// read data, hence using normal session()
			session = getNormalSession();
			transaction = session.beginTransaction();

			long time = System.nanoTime();
			long searchProjectId = searchParameters.getProjectDbId();

			// get cumulative metrics
			StringBuilder builder = new StringBuilder(" SELECT project.id ");
			builder.append(" , sum(plannedValue) , sum(earnedValue) , sum(actualCost) , sum(actualsToDate) , sum(softwareProgrammingEffort), sum(defects), max(week.id) ");
			builder.append(" FROM ProjectMetrics ");

			if (!searchParameters.isSearchAllData() && (searchProjectId > 0)) {

				if (searchProjectId > 0) {
					builder.append(" WHERE ");
					builder.append(" project.id = ").append(searchProjectId);
				}

			}

			builder.append(" GROUP BY ");
			builder.append(" project.id ");

			builder.append(" ORDER BY ");
			builder.append(" project.id ");

			Query query = session.createQuery(builder.toString());

			@SuppressWarnings("rawtypes")
			List list = query.list();

			ProjectLevelMetrics reportRow = null;
			ProjectData projectData = null;
			ProjectEstimateData estimateData = null;
			BasicProjectMetrics basicMetrics = null;
			List<Long> projectsWithMetrics = new ArrayList<Long>();

			if ((list != null) && (list.size() > 0)) {

				Object[] values = null;
				long projId = 0;
				long weekId = 0;
				Week week = null;
				Project project = null;

				for (int i = 0; i < list.size(); i++) {

					values = (Object[]) list.get(i);

					// get values
					projId = (Long) values[0];

					projectsWithMetrics.add(projId);

					// get project
					project = (Project) session.get(Project.class, new Long(projId));

					reportRow = new ProjectLevelMetrics();

					// create basic data
					{
						projectData = new ProjectData();
						projectData.setCode(project.getId());
						projectData.setValue(project.getName());
						projectData.setLeadName(project.getLeadName());

						estimateData = new ProjectEstimateData();
						estimateData.setBudgetAtCompletion(TextHelper.getScaledDouble(project.getBudgetAtCompletion()));
						estimateData.setStartDate(project.getStartDate());
						estimateData.setEndDate(project.getEndDate());
						estimateData.setProjectData(projectData);

						weekId = (Long) values[7];

						if (weekId > 0) {
							week = (Week) session.get(Week.class, Long.valueOf(weekId));

							if (week != null) {
								estimateData.setLastSubmittedPeriod(TextHelper.getDisplayWeek(week.getStartDate(),
										week.getEndDate()));
							}
						}

						// populate the row
						reportRow.setBasicData(estimateData);
					}

					// create metric data
					{
						basicMetrics = new BasicProjectMetrics();
						basicMetrics.setPlannedValue(TextHelper.getScaledDouble((BigDecimal) values[1]));
						basicMetrics.setEarnedValue(TextHelper.getScaledDouble((BigDecimal) values[2]));
						basicMetrics.setActualCost(TextHelper.getScaledDouble((BigDecimal) values[3]));
						basicMetrics.setActualsToDate(TextHelper.getScaledDouble((BigDecimal) values[4]));
						basicMetrics.setSoftwareProgrammingEffort(TextHelper.getScaledDouble((BigDecimal) values[5]));
						basicMetrics.setDefects((Long) values[6]);

						// populate the row
						reportRow.setCumulativeMetrics(basicMetrics);
					}

					// populate the calculated metrics
					reportRow.populateCalculatedMetrics();

					// populate reply
					reply.addProjectLevelMetrics(reportRow);
				}
			}

			// populate projects without metrics
			{
				// populate basic project data

				Criteria criteria = session.createCriteria(Project.class);
				criteria.add(Restrictions.eq("active", Boolean.TRUE));

				if (!searchParameters.isSearchAllData() && (searchProjectId > 0)) {

					if (searchProjectId > 0) {
						criteria.add(Restrictions.eq("id", Long.valueOf(searchProjectId)));
					}
				}

				criteria.addOrder(Order.asc("name"));
				criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

				@SuppressWarnings("unchecked")
				List<Project> projectList = criteria.list();

				if ((projectList != null) && (projectList.size() > 0)) {

					long projId = 0;

					for (Project proj : projectList) {

						projId = proj.getId();

						// ignore ones that have metrics
						if (!projectsWithMetrics.contains(projId)) {

							reportRow = new ProjectLevelMetrics();

							// create basic data
							{
								projectData = new ProjectData();
								projectData.setCode(projId);
								projectData.setValue(proj.getName());
								projectData.setLeadName(proj.getLeadName());

								estimateData = new ProjectEstimateData();
								estimateData.setBudgetAtCompletion(TextHelper.getScaledDouble(proj
										.getBudgetAtCompletion()));
								estimateData.setStartDate(proj.getStartDate());
								estimateData.setEndDate(proj.getEndDate());

								estimateData.setProjectData(projectData);

								// populate the row
								reportRow.setBasicData(estimateData);
							}

							// create metric data
							{
								basicMetrics = new BasicProjectMetrics();
								basicMetrics.setPlannedValue(0);
								basicMetrics.setEarnedValue(0);
								basicMetrics.setActualCost(0);
								basicMetrics.setActualsToDate(0);
								basicMetrics.setSoftwareProgrammingEffort(0);
								basicMetrics.setDefects(0);

								// populate the row
								reportRow.setCumulativeMetrics(basicMetrics);
							}

							// populate reply
							reply.addProjectLevelMetrics(reportRow);
						}
					}
				} else {

					// No results found
					reply.setErrorMessage("No results found.");
				}

			}

			TextHelper.logMessage("getProjectLevelMetricsReport() > Time taken : "
					+ ((System.nanoTime() - time) / 1000000));

			// commit the transaction
			transaction.commit();
		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

			// create a reply for error message
			reply.setErrorMessage("Search failed due to Technical Reasons.");

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}

		return reply;

	}

	@Override
	public ProjectDetailMetricsReply getProjectDetailMetricsReport(ProjectDetailMetricsSearchParameters searchParameters)
			throws TimelineException {

		final ProjectDetailMetricsReply reply = new ProjectDetailMetricsReply();

		Session session = null;
		Transaction transaction = null;

		try {

			// read data, hence using normal session()
			session = getNormalSession();
			transaction = session.beginTransaction();

			long time = System.nanoTime();
			long searchProjectId = searchParameters.getProjectDbId();
			Project project = null;

			if (searchProjectId <= 0) {

				// create a reply for error message
				reply.setErrorMessage("Invalid project specified.");

			} else {

				// get project
				project = (Project) session.get(Project.class, new Long(searchProjectId));

				if (project == null) {

					// create a reply for error message
					reply.setErrorMessage("Specified project not present in System.");

				} else {

					// create basic data
					{
						ProjectData projectData = new ProjectData();
						projectData.setCode(project.getId());
						projectData.setValue(project.getName());
						projectData.setLeadName(project.getLeadName());

						ProjectEstimateData extimateData = new ProjectEstimateData();
						extimateData.setBudgetAtCompletion(TextHelper.getScaledDouble(project.getBudgetAtCompletion()));
						extimateData.setStartDate(project.getStartDate());
						extimateData.setEndDate(project.getEndDate());
						extimateData.setProjectData(projectData);

						// populate the row
						reply.setBasicData(extimateData);
					}

					// get detailed metrics
					Criteria criteria = session.createCriteria(ProjectMetrics.class);
					criteria.createAlias("project", "pr");
					criteria.add(Restrictions.eq("pr.id", new Long(searchProjectId)));
					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<ProjectMetrics> list = criteria.list();

					if ((list != null) && (list.size() > 0)) {

						BasicProjectMetrics basicMetrics = null;
						ProjectDetailMetrics reportRow = null;
						Date lastSaved = null;

						for (ProjectMetrics metrics : list) {

							reportRow = new ProjectDetailMetrics();

							// create metric data
							basicMetrics = new BasicProjectMetrics();
							basicMetrics.setPlannedValue(TextHelper.getScaledDouble(metrics.getPlannedValue()));
							basicMetrics.setEarnedValue(TextHelper.getScaledDouble(metrics.getEarnedValue()));
							basicMetrics.setActualCost(TextHelper.getScaledDouble(metrics.getActualCost()));
							basicMetrics.setActualsToDate(TextHelper.getScaledDouble(metrics.getActualsToDate()));
							basicMetrics.setSoftwareProgrammingEffort(TextHelper.getScaledDouble(metrics
									.getSoftwareProgrammingEffort()));
							basicMetrics.setDefects(metrics.getDefects());

							// populate the row
							reportRow.setWeeklyMetrics(basicMetrics);

							// set metric week
							reportRow.setMetricWeek(TextHelper.getDisplayWeek(metrics.getWeek().getStartDate(), metrics
									.getWeek().getEndDate()));

							lastSaved = metrics.getModifyDate();

							if (lastSaved == null) {
								lastSaved = metrics.getCreateDate();
							}

							reportRow.setLastSaved(lastSaved);
							reportRow.setMetricId(metrics.getId());

							// populate reply
							reply.addProjectDetailMetrics(reportRow);
						}
					} else {
						// No results found
						reply.setErrorMessage("No results found.");
					}
				}
			}

			TextHelper.logMessage("getProjectDetailMetricsReport() > Time taken : "
					+ ((System.nanoTime() - time) / 1000000));

			// commit the transaction
			transaction.commit();
		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

			// create a reply for error message
			reply.setErrorMessage("Search failed due to Technical Reasons.");

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}

		return reply;

	}

	@Override
	public CodeValueReply createStage(CodeValueInput input) throws TimelineException {
		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if ((input != null) && (input.getCodeValue() != null)) {

			final String text = TextHelper.trimToNull(input.getCodeValue().getValue());

			if (text != null) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();
					Stage stage = null;

					Criteria criteria = session.createCriteria(Stage.class);
					criteria.add(Restrictions.eq("name", text));
					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<Stage> list = criteria.list();

					if ((list != null) && (list.size() > 0)) {

						// already a project exists with given name.
						reply.setErrorMessage("This stage name is already present in system.");

					} else {

						stage = new Stage();
						stage.setName(text);
						session.saveOrUpdate(stage);

						reply.setSuccessMessage("Created successfully.");

					}

					TextHelper.logMessage("createStage() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

					if (stage != null) {
						reply.setCodeValue(new CodeValue(stage.getId(), stage.getName()));
					}

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Create failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				reply.setErrorMessage("Invalid stage name specified.");
			}
		}

		return reply;
	}

	@Override
	public CodeValueReply createProjectStage(ProjectStageInput input) throws TimelineException {
		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if (input != null) {

			final Long projectId = input.getProjectId();
			final Long stageId = input.getStageId();

			if ((projectId > 0) && (stageId > 0)) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();

					ProjectStage projectStage = null;

					Criteria criteria = session.createCriteria(ProjectStage.class);
					criteria.createAlias("project", "project");
					criteria.createAlias("stage", "stage");

					criteria.add(Restrictions.and(Restrictions.eq("project.id", projectId),
							Restrictions.eq("stage.id", stageId)));

					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<ProjectStage> list = criteria.list();

					if ((list != null) && (list.size() > 0)) {

						// already a project stage exists
						reply.setErrorMessage("This project stage is already present in system.");

					} else {

						Project project = (Project) session.get(Project.class, projectId);
						Stage stage = (Stage) session.get(Stage.class, stageId);

						final long position = input.getPosition();

						if ((project != null) && (stage != null) && (position > 0)) {

							projectStage = new ProjectStage();
							projectStage.setPosition(position);
							projectStage.setProject(project);
							projectStage.setStage(stage);
							session.saveOrUpdate(projectStage);
							reply.setSuccessMessage("Created successfully.");

						} else {
							reply.setErrorMessage("Invalid data provided.");
						}
					}

					TextHelper.logMessage("createProjectStage() > Time taken : "
							+ ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

					if (projectStage != null) {
						reply.setCodeValue(new CodeValue(projectStage.getId(), projectStage.getDescription()));
					}

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Create failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				reply.setErrorMessage("Invalid Project or Stage specified.");
			}
		}

		return reply;
	}

	@Override
	public CodeValueReply deleteStage(CodeValueInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if ((input != null) && (input.getCodeValue() != null)) {
			final long id = input.getCodeValue().getCode();

			if (id > 0) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();

					final Long stageId = id;

					Criteria criteria = session.createCriteria(ProjectStageTask.class);
					criteria.createAlias("projectStage", "projectStage");
					criteria.createAlias("projectStage.stage", "stage");

					criteria.add(Restrictions.eq("stage.id", stageId));
					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<ProjectStageTask> list = criteria.list();

					if ((list != null) && (list.size() > 0)) {

						// already a project exists with given name.
						reply.setErrorMessage("There are already tasks present for this stage.");

					} else {

						Stage stage = (Stage) session.get(Stage.class, stageId);

						if (stage != null) {
							session.delete(stage);
							reply.setSuccessMessage("Deleted successfully.");
							reply.setCodeValue(new CodeValue(stage.getId(), stage.getName()));
						} else {
							reply.setErrorMessage("Specified stage not present in system.");
						}
					}

					TextHelper.logMessage("deleteStage() > Time taken : " + ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Delete failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (id <= 0) {
					reply.setErrorMessage("Invalid stage specified.");
				}
			}
		}

		return reply;

	}

	@Override
	public CodeValueReply deleteProjectStage(CodeValueInput input) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if ((input != null) && (input.getCodeValue() != null)) {
			final long id = input.getCodeValue().getCode();

			if (id > 0) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();

					final Long projectStageId = id;

					Criteria criteria = session.createCriteria(ProjectStageTask.class);
					criteria.createAlias("projectStage", "projectStage");

					criteria.add(Restrictions.eq("projectStage.id", projectStageId));
					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<ProjectStageTask> list = criteria.list();

					if ((list != null) && (list.size() > 0)) {

						// already a project exists with given name.
						reply.setErrorMessage("There are already tasks present for this project stage.");

					} else {

						ProjectStage projectStage = (ProjectStage) session.get(ProjectStage.class, projectStageId);

						if (projectStage != null) {
							session.delete(projectStage);
							reply.setSuccessMessage("Deleted successfully.");
							reply.setCodeValue(new CodeValue(projectStage.getId(), projectStage.getDescription()));
						} else {
							reply.setErrorMessage("Specified project stage not present in system.");
						}
					}

					TextHelper.logMessage("deleteProjectStage() > Time taken : "
							+ ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Delete failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				if (id <= 0) {
					reply.setErrorMessage("Invalid project stage specified.");
				}
			}
		}

		return reply;

	}

	@Override
	public CodeValueReply modifyProjectStageTask(ProjectStageTaskInput input) throws TimelineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CodeValueListReply searchStages(StageSearchParameter searchParameters) throws TimelineException {

		Session session = null;
		Transaction transaction = null;
		final CodeValueListReply reply = new CodeValueListReply();

		try {
			// read data, hence using normal session()
			session = getNormalSession();
			transaction = session.beginTransaction();

			long time = System.nanoTime();
			Long stageId = 0l;
			String stageName = null;

			Criteria criteria = session.createCriteria(Stage.class);

			if (searchParameters != null) {
				stageId = searchParameters.getStageId();
				stageName = TextHelper.trimToNull(searchParameters.getStageName());
			}

			if (stageId > 0) {
				criteria.add(Restrictions.eq("id", stageId));
			}

			if (stageName != null) {
				criteria.add(Restrictions.ilike("name", stageName.toLowerCase(), MatchMode.ANYWHERE));
			}

			// add order
			criteria.addOrder(Order.asc("name"));

			criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

			@SuppressWarnings("unchecked")
			List<Stage> dbList = criteria.list();

			if ((dbList != null) && (dbList.size() > 0)) {

				CodeValue codeValue = null;

				for (Stage stage : dbList) {
					codeValue = new CodeValue();
					codeValue.setCode(stage.getId());
					codeValue.setValue(stage.getName());

					reply.addCodeValue(codeValue);
				}

			} else {
				// No results found
				reply.setErrorMessage("No results found.");
			}

			TextHelper.logMessage("searchStages() > Time taken : " + ((System.nanoTime() - time) / 1000000));

			// commit the transaction
			transaction.commit();

		} catch (HibernateException hibernateException) {

			// rollback transaction
			if (transaction != null) {
				transaction.rollback();
			}

			hibernateException.printStackTrace();

			// create a reply for error message
			reply.setErrorMessage("Search failed due to Technical Reasons.");

		} finally {
			// close the session
			if (session != null) {
				session.close();
			}
		}
		return reply;
	}

	@Override
	public CodeValueListReply searchProjectStage(ProjectStageSearchParameters searchParameters)
			throws TimelineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CodeValueReply createProjectStageTask(ProjectStageTaskInput input) throws TimelineException {
		Session session = null;
		Transaction transaction = null;
		final CodeValueReply reply = new CodeValueReply();

		if (input != null) {

			final Long projectId = input.getProjectId();
			final Long stageId = input.getStageId();
			final Long taskId = input.getTaskId();

			if ((projectId > 0) && (stageId > 0) && (taskId > 0)) {

				try {
					// create data, hence using AuditableSession()
					session = getAuditableSession();
					transaction = session.beginTransaction();

					long time = System.nanoTime();

					ProjectStageTask projectStageTask = null;

					Criteria criteria = session.createCriteria(ProjectStageTask.class);
					criteria.createAlias("projectStage", "projectStage");
					criteria.createAlias("projectStage.project", "project");
					criteria.createAlias("projectStage.stage", "stage");
					criteria.createAlias("task", "task");

					criteria.add(Restrictions.and(Restrictions.eq("project.id", projectId),
							Restrictions.eq("stage.id", stageId), Restrictions.eq("task.id", taskId)));

					criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

					@SuppressWarnings("unchecked")
					List<ProjectStageTask> list = criteria.list();

					if ((list != null) && (list.size() > 0)) {

						// already a project stage task exists
						reply.setErrorMessage("This combination of Task, Project & Stage already exists");

					} else {

						Project project = (Project) session.get(Project.class, projectId);
						Stage stage = (Stage) session.get(Stage.class, stageId);
						Task task = (Task) session.get(Task.class, taskId);

						final long position = input.getPosition();

						if ((project != null) && (stage != null) && (task != null) && (position > 0)) {

							ProjectStage projectStage = null;

							{
								Criteria innerCtiteria = session.createCriteria(ProjectStage.class);
								innerCtiteria.createAlias("project", "project");
								innerCtiteria.createAlias("stage", "stage");

								innerCtiteria.add(Restrictions.and(Restrictions.eq("project.id", projectId),
										Restrictions.eq("stage.id", stageId)));

								criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

								@SuppressWarnings("unchecked")
								List<ProjectStage> innerList = criteria.list();

								if (innerList != null) {
									projectStage = innerList.get(0);
								}

								if (projectStage != null) {

									projectStageTask = new ProjectStageTask();
									projectStageTask.setPosition(position);
									projectStageTask.setProjectStage(projectStage);
									projectStageTask.setTask(task);
									session.saveOrUpdate(projectStage);
									reply.setSuccessMessage("Created successfully.");

								} else {
									reply.setErrorMessage("Specified ProjectStage not present in system.");
								}
							}

						} else {
							reply.setErrorMessage("Invalid data provided.");
						}
					}

					TextHelper.logMessage("createProjectStageTask() > Time taken : "
							+ ((System.nanoTime() - time) / 1000000));

					// commit the transaction
					transaction.commit();

					if (projectStageTask != null) {
						reply.setCodeValue(new CodeValue(projectStageTask.getId(), projectStageTask.getDescription()));
					}

				} catch (HibernateException hibernateException) {

					// rollback transaction
					if (transaction != null) {
						transaction.rollback();
					}

					hibernateException.printStackTrace();

					// create a reply for error message
					reply.setErrorMessage("Create failed due to Technical Reasons.");

				} finally {
					// close the session
					if (session != null) {
						session.close();
					}
				}
			} else {
				reply.setErrorMessage("Invalid Project or Stage or Task specified.");
			}
		}

		return reply;
	}

	@Override
	public CodeValueReply deleteProjectStageTask(CodeValueInput input) throws TimelineException {
		// TODO Auto-generated method stub
		return null;
	}
}