package org.ag.timeline.business.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.ag.timeline.business.model.AbstractModel;
import org.ag.timeline.business.model.Activity;
import org.ag.timeline.business.model.AuditRecord;
import org.ag.timeline.business.model.AuditRecordDetail;
import org.ag.timeline.business.model.Project;
import org.ag.timeline.business.model.TimeData;
import org.ag.timeline.business.model.User;
import org.ag.timeline.business.model.UserPreferences;
import org.ag.timeline.business.model.Week;
import org.ag.timeline.common.TextHelper;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;

public final class TestDataHelper {

	private static TestDataHelper instance;

	private final Session session;

	// test data for User
	private final String testUserFirstName = "T FN ";
	private final String testUserLastName = "T LN ";
	private final String testUserId = "T UID ";
	private final String testUserPassword = "T PWD ";
	private final boolean testUserNonAdmin = false;
	private final boolean testUserAdmin = true;

	// test data for Project
	private final String testProjectName = "T PROJ ";

	// test data for Activity
	private final String testActivityName = "T ACT ";

	// test data for User Preferences
	private final String testPrefQuestion = "T DOB ";
	private final String testPrefAnswer = "T 1Jan ";

	private Date currentDate = null;
	private long currentUserId = 0;
	private Calendar calendar = null;

	private TestDataHelper(Session session) {
		this.session = session;
		this.currentDate = new Date();
		this.calendar = Calendar.getInstance();
		this.currentUserId = 1l;
	}

	private void populateBasicData(AbstractModel model) {

		if (model != null) {
			model.setCreateDate(currentDate);
			model.setCreateUserId(currentUserId);
		}
	}

	private String getUniqueTestId() {
		StringBuilder builder = new StringBuilder();
		calendar.setTime(new Date());
		builder.append(calendar.get(Calendar.HOUR_OF_DAY)).append(calendar.get(Calendar.MINUTE))
				.append(calendar.get(Calendar.SECOND));
		return builder.toString();

	}

	public void cleanTestData() {

		try {

			// begin transaction
			Transaction transaction = session.beginTransaction();

			// clean data
			session.createQuery("delete from " + TimeData.class.getSimpleName()).executeUpdate();
			session.createQuery("delete from " + Activity.class.getSimpleName()).executeUpdate();
			session.createQuery("delete from " + Project.class.getSimpleName()).executeUpdate();
			session.createQuery("delete from " + UserPreferences.class.getSimpleName()).executeUpdate();
			session.createQuery("delete from " + User.class.getSimpleName()).executeUpdate();
			session.createQuery("delete from " + AuditRecordDetail.class.getSimpleName()).executeUpdate();
			session.createQuery("delete from " + AuditRecord.class.getSimpleName()).executeUpdate();

			// reset sequences
			session.createSQLQuery("ALTER SEQUENCE TIMELINE_SCHEMA.ACTIVITY_S RESTART WITH 1").executeUpdate();
			session.createSQLQuery("ALTER SEQUENCE TIMELINE_SCHEMA.PROJECT_S RESTART WITH 1").executeUpdate();
			session.createSQLQuery("ALTER SEQUENCE TIMELINE_SCHEMA.USER_S RESTART WITH 1").executeUpdate();
			session.createSQLQuery("ALTER SEQUENCE TIMELINE_SCHEMA.USER_PREF_S RESTART WITH 1").executeUpdate();
			session.createSQLQuery("ALTER SEQUENCE TIMELINE_SCHEMA.TIME_DATA_S RESTART WITH 1").executeUpdate();
			session.createSQLQuery("ALTER SEQUENCE TIMELINE_SCHEMA.AUDIT_DETAIL_S RESTART WITH 1").executeUpdate();
			session.createSQLQuery("ALTER SEQUENCE TIMELINE_SCHEMA.AUDIT_S RESTART WITH 1").executeUpdate();

			session.flush();
			session.createSQLQuery("SHUTDOWN COMPACT").executeUpdate();
		
			// close session
			session.close();

			// commit transaction
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static TestDataHelper getInstance(Session session) {

		if (instance == null) {
			instance = new TestDataHelper(session);
			User testUser = instance.createUser("Test", "User", "testUser", "test", true);
			instance.currentUserId = testUser.getId();
		}

		return instance;
	}

	public long getTestUserDbId() {
		return this.currentUserId;
	}

	public User createBasicUser() {
		String uniqueId = getUniqueTestId();
		return createUser(testUserFirstName + uniqueId, testUserLastName + uniqueId, testUserId, testUserPassword,
				testUserNonAdmin);
	}

	public User createAdminUser() {
		String uniqueId = getUniqueTestId();
		return createUser(testUserFirstName + uniqueId, testUserLastName + uniqueId, testUserId, testUserPassword,
				testUserAdmin);
	}

	public User createUser(final String fName, final String lName, final String uId, final String pwd,
			final boolean admin) {
		User user = null;

		try {

			// begin transaction
			Transaction transaction = session.beginTransaction();

			// create new data object
			user = new User();

			// populate data object
			user.setFirstName(fName);
			user.setLastName(lName);
			user.setUserId(uId);
			user.setPassword(pwd);
			user.setAdmin(admin);

			populateBasicData(user);

			// save data object
			this.session.save(user);

			// commit transaction
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	public void verifyBasicUser(User user) {
		Assert.assertEquals("Mismatch in user first name.", testUserFirstName, user.getFirstName());
		Assert.assertEquals("Mismatch in user last name.", testUserLastName, user.getLastName());
		Assert.assertEquals("Mismatch in user text id.", testUserId, user.getUserId());
		Assert.assertEquals("Mismatch in user password.", testUserPassword, user.getPassword());
		Assert.assertEquals("Mismatch in user admin status.", testUserNonAdmin, user.isAdmin());
	}

	public void verifyAdminUser(User user) {
		Assert.assertEquals("Mismatch in user first name.", testUserFirstName, user.getFirstName());
		Assert.assertEquals("Mismatch in user last name.", testUserLastName, user.getLastName());
		Assert.assertEquals("Mismatch in user text id.", testUserId, user.getUserId());
		Assert.assertEquals("Mismatch in user password.", testUserPassword, user.getPassword());
		Assert.assertEquals("Mismatch in user admin status.", testUserAdmin, user.isAdmin());
	}

	public Project createProject(final String text, final User lead) {

		Project project = null;

		try {

			// begin transaction
			Transaction transaction = session.beginTransaction();

			// create new data object
			project = new Project();

			// populate data object
			project.setName(text);
			project.setLead(lead);
			populateBasicData(project);

			// save data object
			this.session.save(project);

			// commit transaction
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return project;
	}

	public Project createBasicProject() {
		final User admin = createAdminUser();
		String uniqueId = getUniqueTestId();
		return createProject(testProjectName + uniqueId, admin);
	}

	public Activity createActivity(final String text, final Project parentProject) {

		Activity activity = null;

		try {

			// begin transaction
			Transaction transaction = session.beginTransaction();

			// create new data object
			activity = new Activity();

			// populate data object
			activity.setName(text);
			activity.setProject(parentProject);
			populateBasicData(activity);

			// save data object
			this.session.save(activity);

			// commit transaction
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return activity;
	}

	public Activity createBasicActivity() {
		Project project = createBasicProject();
		String uniqueId = getUniqueTestId();
		return createActivity(testActivityName + uniqueId, project);
	}

	public UserPreferences createUserPreferences(final String question, final String answer, final User user) {

		UserPreferences preferences = null;

		try {

			// begin transaction
			Transaction transaction = session.beginTransaction();

			// create new data object
			preferences = new UserPreferences();

			// populate data object
			preferences.setQuestion(question);
			preferences.setAnswer(answer);
			preferences.setUser(user);
			populateBasicData(preferences);

			// save data object
			this.session.save(preferences);

			// commit transaction
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return preferences;
	}

	public UserPreferences createBasicUserPreferences() {
		User user = createBasicUser();
		String uniqueId = getUniqueTestId();
		return createUserPreferences(testPrefQuestion + uniqueId, testPrefAnswer + uniqueId, user);
	}

	public Week getWeek(Date date) {
		Week week = null;

		try {

			// begin transaction
			Transaction transaction = session.beginTransaction();
			final long weekNumber = TextHelper.getWeekNumber(currentDate);
			final long year = TextHelper.getYear(currentDate);
			final Date startDate = TextHelper.getFirstDayOfWeek(currentDate);
			final Date endDate = TextHelper.getLastDayOfWeek(currentDate);

			// find existing week in database
			Criteria criteria = session.createCriteria(Week.class);
			criteria.add(Restrictions.and(Restrictions.eq("weekNumber", weekNumber), Restrictions.eq("year", year)));
			criteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);

			@SuppressWarnings("unchecked")
			List<Week> list = criteria.list();

			if ((list != null) && (list.size() > 0)) {
				week = list.get(0);
			} else {
				week = new Week();
				week.setWeekNumber(weekNumber);
				week.setYear(year);
				week.setStartDate(startDate);
				week.setEndDate(endDate);

				populateBasicData(week);

				// save data object
				this.session.save(week);
			}

			// commit transaction
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return week;
	}

	public TimeData createTimeData(Activity activity, User user, Week week, double day1, double day2, double day3,
			double day4, double day5, double day6, double day7) {
		TimeData data = null;

		try {

			// begin transaction
			Transaction transaction = session.beginTransaction();

			// create new data object
			data = new TimeData();

			// populate data object
			data.setActivity(activity);
			data.setProject(activity.getProject());
			data.setUser(user);
			data.setWeek(week);

			data.setData_weekday_1(TextHelper.getScaledBigDecimal(day1));
			data.setData_weekday_2(TextHelper.getScaledBigDecimal(day2));
			data.setData_weekday_3(TextHelper.getScaledBigDecimal(day3));
			data.setData_weekday_4(TextHelper.getScaledBigDecimal(day4));
			data.setData_weekday_5(TextHelper.getScaledBigDecimal(day5));
			data.setData_weekday_6(TextHelper.getScaledBigDecimal(day6));
			data.setData_weekday_7(TextHelper.getScaledBigDecimal(day7));

			populateBasicData(data);

			// save data object
			this.session.save(data);

			// commit transaction
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public TimeData createBasicTimeData() {
		Activity activity = createBasicActivity();
		User user = createBasicUser();
		Week week = getWeek(currentDate);

		return createTimeData(activity, user, week, 4.5, 5.5, 6.5, 7.5, 8.5, 0, 0);
	}
}
