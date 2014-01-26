package org.ag.timeline.business.model;

import java.util.Date;
import java.util.Stack;

import junit.framework.Assert;

import org.ag.timeline.business.model.metrics.ProjectMetrics;
import org.ag.timeline.business.util.HibernateUtil;
import org.ag.timeline.common.TextHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test case for Model Objects
 * 
 * @author Abhishek Gaurav
 */
public class ModelTest {

	private static SessionFactory factory = null;

	private Session session = null;

	private Transaction transaction = null;

	private Stack<AbstractModel> inserts = null;

	private boolean readOnly = true;

	protected void saveOrUpdate(Session session, AbstractModel object) {
		session.saveOrUpdate(object);
		if (readOnly) {
			inserts.add(object);
		}

	}

	protected User createUser(String first, String last, Session session) {
		User user = new User();
		user.setFirstName(first);
		user.setLastName(last);
		user.setUserId(first + last);
		populateCreationInfo(user);
		saveOrUpdate(session, user);

		return user;
	}

	protected Project createProject(String text, Session session) {
		Project project = new Project();
		project.setName(text);
		populateCreationInfo(project);
		saveOrUpdate(session, project);

		return project;
	}

	protected Task createTask(Activity activity, String text, Session session) {
		Task task = new Task();
		task.setActivity(activity);
		task.setText(text);

		populateCreationInfo(task);
		saveOrUpdate(session, task);

		return task;
	}

	protected Activity createActivity(String text, Project project, Session session) {
		Activity activity = new Activity();
		activity.setName(text);
		project.addChild(activity);
		populateCreationInfo(activity);
		saveOrUpdate(session, project);
		saveOrUpdate(session, activity);

		return activity;
	}

	protected User createUser(String userId, String firstName, String lastName, Session session) {

		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUserId(userId);

		populateCreationInfo(user);

		saveOrUpdate(session, user);
		return user;
	}

	protected Week createWeek(Date date, Session session) {
		Week week = new Week();
		week.setStartDate(TextHelper.getFirstDayOfWeek(date));
		week.setEndDate(TextHelper.getLastDayOfWeek(date));
		week.setWeekNumber(TextHelper.getWeekNumber(date));
		week.setYear(TextHelper.getYearForWeekDay(date));
		populateCreationInfo(week);
		saveOrUpdate(session, week);

		return week;
	}

	protected UserPreferences createUserPreferences(User user, String question, String answer, Session session) {
		UserPreferences preferences = new UserPreferences();
		preferences.setUser(user);
		preferences.setQuestion(question);
		preferences.setAnswer(answer);
		populateCreationInfo(preferences);
		saveOrUpdate(session, preferences);
		return preferences;
	}

	protected void populateCreationInfo(AbstractModel model) {
		model.setCreateUserId(Long.valueOf(1000));
		model.setCreateDate(new Date());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		factory = HibernateUtil.getSessionFactory("hibernate-test.cfg.xml");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		factory.close();
		factory = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		session = factory.openSession();
		if (readOnly) {
			inserts = new Stack<AbstractModel>();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

		if (readOnly) {
			if (inserts.size() > 0) {
				Transaction transaction = session.beginTransaction();

				while (!inserts.empty()) {
					session.delete(inserts.pop());
				}

				transaction.commit();
			}

			inserts = null;
		}

		session.close();
	}

	@Test
	public void labelTest() {
		transaction = session.beginTransaction();

		Project project = createProject("BCloud", session);

		Activity activity = null;
		for (int i = 1; i <= 5; i++) {
			activity = createActivity("Build " + i, project, session);
		}

		transaction.commit();

		Assert.assertTrue(project.getId() > 0);
		System.out.println(activity.getProject());

	}

	@Test
	public void userTest() {
		transaction = session.beginTransaction();

		User user = createUser("Team", "Member", session);

		transaction.commit();

		Assert.assertTrue(user.getId() > 0);
		System.out.println(user);
	}

	@Test
	public void weekTest() {
		transaction = session.beginTransaction();

		Date date = new Date();
		Week week = createWeek(date, session);

		transaction.commit();

		Assert.assertTrue(week.getId() > 0);
		System.out.println(week);
	}

	@Test
	public void userPreferencesTest() {
		transaction = session.beginTransaction();

		User user = createUser("Abhishek", "Gaurav", session);
		UserPreferences preferences = createUserPreferences(user, "Company", "Accenture", session);

		transaction.commit();

		Assert.assertTrue(preferences.getId() > 0);
		System.out.println(preferences);
	}

	@Test
	public void TimeDataTest() {
		transaction = session.beginTransaction();

		User user = createUser("A", "G", session);
		Project project = createProject("RTS", session);
		Activity activity = createActivity("Build", project, session);
		Week week = createWeek(new Date(), session);

		TimeData data = new TimeData();
		data.setProject(project);
		data.setActivity(activity);
		data.setUser(user);
		data.setWeek(week);
		data.setData_weekday_1(TextHelper.getScaledBigDecimal(4.641));
		data.setData_weekday_2(TextHelper.getScaledBigDecimal(1.234));

		populateCreationInfo(data);

		saveOrUpdate(session, data);

		transaction.commit();

		Assert.assertTrue(data.getId() > 0);
		System.out.println(data);
	}

	@Test
	public void ProjectMetricsTest() {
		transaction = session.beginTransaction();

		Project project = createProject("RTS", session);
		Week week = createWeek(new Date(), session);

		ProjectMetrics metrics = new ProjectMetrics();

		metrics.setProject(project);
		metrics.setWeek(week);

		metrics.setPlannedValue(TextHelper.getScaledBigDecimal(100.123));
		metrics.setEarnedValue(TextHelper.getScaledBigDecimal(95.567));
		metrics.setActualCost(TextHelper.getScaledBigDecimal(97.998));
		metrics.setActualsToDate(TextHelper.getScaledBigDecimal(98));
		metrics.setSoftwareProgrammingEffort(TextHelper.getScaledBigDecimal(90.356));
		metrics.setDefects(5);
		populateCreationInfo(metrics);

		saveOrUpdate(session, metrics);
		transaction.commit();

		Assert.assertTrue(metrics.getId() > 0);
		System.out.println(metrics);
	}

}
