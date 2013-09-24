package org.ag.timeline.business.model;

import java.util.Date;
import java.util.Stack;

import junit.framework.Assert;

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

	protected Project createProject(String text, Session session) {
		Project project = new Project();
		project.setName(text);
		saveOrUpdate(session, project);

		return project;
	}

	protected Activity createActivity(String text, Project project, Session session) {
		Activity activity = new Activity();
		activity.setName(text);
		project.addChild(activity);
		saveOrUpdate(session, project);
		saveOrUpdate(session, activity);

		return activity;
	}

	protected User createUser(String firstName, String lastName, Session session) {

		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		saveOrUpdate(session, user);
		return user;
	}

	protected Week createWeek(Date date, Session session) {
		Week week = new Week();
		week.setStartDate(TextHelper.getFirstDayOfWeek(date));
		week.setEndDate(TextHelper.getLastDayOfWeek(date));
		week.setWeekNumber(TextHelper.getWeekNumber(date));
		week.setYear(TextHelper.getYear(date));

		saveOrUpdate(session, week);

		return week;
	}

	protected UserPreferences createUserPreferences(User user, String question, String answer, Session session) {
		UserPreferences preferences = new UserPreferences();
		preferences.setUser(user);
		preferences.setQuestion(question);
		preferences.setAnswer(answer);
		saveOrUpdate(session, preferences);
		return preferences;
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

//	@Test
//	public void userTest() {
//		transaction = session.beginTransaction();
//
//		User user = createUser("Team", "Member", session);
//
//		transaction.commit();
//
//		Assert.assertTrue(user.getId() > 0);
//		System.out.println(user);
//	}
//
//	@Test
//	public void weekTest() {
//		transaction = session.beginTransaction();
//
//		Date date = new Date();
//		Week week = createWeek(date, session);
//
//		transaction.commit();
//
//		Assert.assertTrue(week.getId() > 0);
//		System.out.println(week);
//	}
//
//	@Test
//	public void userPreferencesTest() {
//		transaction = session.beginTransaction();
//
//		User user = createUser("Abhishek", "Gaurav", session);
//		UserPreferences preferences = createUserPreferences(user, "Company", "Accenture", session);
//
//		transaction.commit();
//
//		Assert.assertTrue(preferences.getId() > 0);
//		System.out.println(preferences);
//	}
//
//	@Test
//	public void TimeDataTest() {
//		transaction = session.beginTransaction();
//
//		User user = createUser("Abhishek", "Gaurav", session);
//		Project project = createProject("RTS", session);
//		Activity activity = createActivity("Build", project, session);
//		Week week = createWeek(new Date(), session);
//
//		TimeData data = new TimeData();
//		data.setProject(project);
//		data.setActivity(activity);
//		data.setUser(user);
//		data.setWeek(week);
//		data.setData_weekday_1(TextHelper.getScaledBigDecimal(4.641));
//		data.setData_weekday_2(TextHelper.getScaledBigDecimal(1.234));
//
//		saveOrUpdate(session, data);
//
//		transaction.commit();
//
//		Assert.assertTrue(data.getId() > 0);
//		System.out.println(data);
//	}

}
