package org.ag.timeline.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.ag.timeline.application.context.RequestContext;
import org.ag.timeline.application.context.TimelineContext;
import org.ag.timeline.application.exception.TimelineException;
import org.ag.timeline.business.model.Activity;
import org.ag.timeline.business.model.Project;
import org.ag.timeline.business.model.TimeData;
import org.ag.timeline.business.model.User;
import org.ag.timeline.business.model.UserPreferences;
import org.ag.timeline.business.service.iface.TimelineService;
import org.ag.timeline.business.service.impl.TimelineServiceImpl;
import org.ag.timeline.business.util.HibernateUtil;
import org.ag.timeline.common.TextHelper;
import org.ag.timeline.common.TimelineConstants.AuditDataType;
import org.ag.timeline.presentation.transferobject.common.CodeValue;
import org.ag.timeline.presentation.transferobject.input.CodeValueInput;
import org.ag.timeline.presentation.transferobject.input.ProjectInput;
import org.ag.timeline.presentation.transferobject.input.TimeDataInput;
import org.ag.timeline.presentation.transferobject.input.UserInput;
import org.ag.timeline.presentation.transferobject.input.UserPreferencesInput;
import org.ag.timeline.presentation.transferobject.reply.ActivityReply;
import org.ag.timeline.presentation.transferobject.reply.AuditDataReply;
import org.ag.timeline.presentation.transferobject.reply.AuditRow;
import org.ag.timeline.presentation.transferobject.reply.CodeValueReply;
import org.ag.timeline.presentation.transferobject.reply.ProjectReply;
import org.ag.timeline.presentation.transferobject.reply.TimeDataReply;
import org.ag.timeline.presentation.transferobject.reply.TimeDataRow;
import org.ag.timeline.presentation.transferobject.reply.UserPreferenceReply;
import org.ag.timeline.presentation.transferobject.reply.UserPreferenceSearchReply;
import org.ag.timeline.presentation.transferobject.reply.UserReply;
import org.ag.timeline.presentation.transferobject.reply.UserSearchReply;
import org.ag.timeline.presentation.transferobject.search.ActivitySearchParameter;
import org.ag.timeline.presentation.transferobject.search.AuditDataSearchParameters;
import org.ag.timeline.presentation.transferobject.search.ProjectSearchParameter;
import org.ag.timeline.presentation.transferobject.search.TimeDataSearchParameters;
import org.ag.timeline.presentation.transferobject.search.UserPreferenceSearchParameter;
import org.ag.timeline.presentation.transferobject.search.UserSearchParameter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test case for Business Service.
 * 
 * @author Abhishek Gaurav
 */
public class ServiceTest {

	/**
	 * Service implementation.
	 */
	private static TimelineService impl = null;

	/**
	 * Helper for test data management.
	 */
	private static TestDataHelper dataHelper = null;

	/**
	 * Executes once at the class loading. Sets up basic test data environment.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			SessionFactory factory = HibernateUtil.getSessionFactory("hibernate-test.cfg.xml");

			Session session = factory.openSession();
			dataHelper = TestDataHelper.getInstance(session);
			RequestContext.setTimelineContext(new TimelineContext(dataHelper.getTestUserDbId()));

			impl = new TimelineServiceImpl();
			impl.systemManagement();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Executes once in the lifetime of this class. Cleans up all test
	 * environment set up.
	 */
	@AfterClass
	public static void tearDownAfterClass() {

		try {

			// clean the test data created
			dataHelper.cleanTestData();

			// destroy the context
			RequestContext.destroy();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test user creation.
	 */
	@Test
	public void createUserTest() {

		final String userFirstName = "First";
		final String userLastName = "Last";
		final String userID = "userId";
		final String pwd = "password";
		final boolean admin = true;

		try {
			UserInput input = new UserInput();
			input.setFirstName(userFirstName);
			input.setLastName(userLastName);
			input.setUserId(userID);
			input.setPassword(pwd);
			input.setAdmin(admin);

			UserReply reply = impl.createUser(input);
			User user = reply.getUser();

			Assert.assertTrue("Reply has error.", !reply.isMsgError());
			Assert.assertNotNull("Reply does not contain saved user.", user);
			Assert.assertTrue("Created User's Database Id is invalid.", user.getId() > 0);
			Assert.assertEquals(userFirstName, user.getFirstName());
			Assert.assertEquals(userLastName, user.getLastName());
			Assert.assertEquals(userID, user.getUserId());
			Assert.assertEquals(pwd, user.getPassword());
			Assert.assertEquals(admin, user.isAdmin());

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Test user modification.
	 */
	@Test
	public void modifyUserTest() {

		try {

			long id = 0;

			// create an new user
			{
				User user = dataHelper.createBasicUser();
				id = user.getId();
			}

			String newFirstName = "First  New";
			String newLastName = "Last  New";
			String newUserID = "userId  New";
			String newPwd = "password  New";
			boolean newAdminStatus = true;

			UserInput input = new UserInput();

			input.setId(id);
			input.setFirstName(newFirstName);
			input.setLastName(newLastName);
			input.setUserId(newUserID);
			input.setPassword(newPwd);
			input.setAdmin(newAdminStatus);

			UserReply reply = impl.modifyUser(input);
			User modifiedUser = reply.getUser();

			Assert.assertNotNull("Reply does not contain saved user.", modifiedUser);
			Assert.assertTrue(modifiedUser.getId() == id);
			Assert.assertEquals(newFirstName, modifiedUser.getFirstName());
			Assert.assertEquals(newLastName, modifiedUser.getLastName());
			Assert.assertEquals(newUserID, modifiedUser.getUserId());
			Assert.assertEquals(newPwd, modifiedUser.getPassword());
			Assert.assertEquals(newAdminStatus, modifiedUser.isAdmin());

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Test user deletion.
	 */
	@Test
	public void deleteUserTest() {

		try {

			long id = 0;

			// create an new user
			{
				String userFirstName = "First";
				String userLastName = "Last";
				String userID = "userId";
				String pwd = "password";
				boolean admin = false;

				User user = dataHelper.createUser(userFirstName, userLastName, userID, pwd, admin);
				id = user.getId();
			}

			CodeValueInput input = new CodeValueInput();
			input.setCodeValue(new CodeValue(id));
			UserReply reply = impl.deleteUser(input);
			User deletedUser = reply.getUser();

			Assert.assertNotNull("Reply does not contain deleted user.", deletedUser);
			Assert.assertTrue(deletedUser.getId() == id);

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Test project creation.
	 */
	@Test
	public void createProjectTest() {

		try {
			long userId = 0;

			// create an new user
			{
				User user = dataHelper.createAdminUser();
				userId = user.getId();
			}

			final String projName = "New Project";

			ProjectInput input = new ProjectInput();
			input.setNewLabelText(projName);
			input.setLeadId(userId);

			CodeValueReply reply = impl.createProject(input);

			Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
			Assert.assertTrue("Created Project's Database Id is invalid.", reply.getCodeValue().getCode() > 0);
			Assert.assertEquals("Created Project's name does not match with saved value.", projName, reply
					.getCodeValue().getValue());

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Test project modification.
	 */
	@Test
	public void modifyProjectTest() {

		try {
			long projectId = 0;

			{
				// create test data
				Project project = dataHelper.createBasicProject();
				projectId = project.getId();
			}

			final String newprojectName = "New Project X";
			ProjectInput input = new ProjectInput();

			input.setProjectId(projectId);
			input.setNewLabelText(newprojectName);

			CodeValueReply reply = impl.modifyProject(input);

			Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
			Assert.assertEquals(projectId, reply.getCodeValue().getCode());
			Assert.assertEquals(newprojectName, reply.getCodeValue().getValue());

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Test project deletion.
	 */
	@Test
	public void deleteProjectTest() {

		try {
			long projectId = 0;

			{
				// create test data
				Project project = dataHelper.createBasicProject();
				projectId = project.getId();
			}

			CodeValueInput input = new CodeValueInput();
			input.setCodeValue(new CodeValue(projectId));

			CodeValueReply reply = impl.deleteProject(input);

			Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
			Assert.assertEquals(projectId, reply.getCodeValue().getCode());

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Test activity creation.
	 */
	@Test
	public void createActivityTest() {

		try {

			long projectId = 0;

			{
				// create test data
				Project project = dataHelper.createBasicProject();
				projectId = project.getId();
			}

			final String name = "Activity A";

			CodeValueInput actInput = new CodeValueInput();
			actInput.setCodeValue(new CodeValue(projectId, name));
			CodeValueReply reply = impl.createActivity(actInput);

			Assert.assertTrue("Reply has error.", !reply.isMsgError());
			Assert.assertTrue(reply.getCodeValue().getCode() > 0);
			Assert.assertEquals(name, reply.getCodeValue().getValue());

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Test activity modification.
	 */
	@Test
	public void modifyActivityTest() {

		try {

			long activityId = 0;

			{
				// create test data
				Activity activity = dataHelper.createBasicActivity();
				activityId = activity.getId();
			}

			final String newActivityName = "Activity B";
			CodeValueInput input = new CodeValueInput();
			input.setCodeValue(new CodeValue(activityId, newActivityName));

			CodeValueReply reply = impl.modifyActivity(input);

			Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
			Assert.assertEquals(activityId, reply.getCodeValue().getCode());
			Assert.assertEquals(newActivityName, reply.getCodeValue().getValue());

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Test activity deletion.
	 */
	@Test
	public void deleteActivityTest() {

		try {

			long activityId = 0;

			{
				// create test data
				Activity activity = dataHelper.createBasicActivity();
				activityId = activity.getId();
			}

			CodeValueInput input = new CodeValueInput();
			input.setCodeValue(new CodeValue(activityId));
			CodeValueReply reply = impl.deleteActivity(input);

			Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
			Assert.assertEquals(activityId, reply.getCodeValue().getCode());

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	@Test
	public void saveUserPreferences() {

		try {

			long userDbId = 0;

			// create an new user
			{
				User user = dataHelper.createAdminUser();
				userDbId = user.getId();
			}

			UserPreferencesInput input = new UserPreferencesInput();
			input.setUserDbId(userDbId);
			input.setQuestion("DOB");
			input.setAnswer("01.01.2001");

			UserPreferenceReply reply = impl.saveUserPreferences(input);
			UserPreferences preferences = reply.getPreference();

			Assert.assertTrue("Reply has error.", !reply.isMsgError());
			Assert.assertNotNull("Preferences object was not saved.", preferences);
			Assert.assertEquals("Mismatch in question.", "DOB", preferences.getQuestion());
			Assert.assertEquals("Mismatch in answer.", "01.01.2001", preferences.getAnswer());
			Assert.assertEquals("Mismatch in user id.", userDbId, preferences.getUser().getId());

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	@Test
	public void modifyUserPreferencesTest() {

		try {

			long userDbId = 0;

			{
				// set up test data
				UserPreferences preferences = dataHelper.createBasicUserPreferences();
				userDbId = preferences.getUser().getId();
			}

			final String newQuestion = "Age";
			final String newAnswer = "11";

			UserPreferencesInput input = new UserPreferencesInput();
			input.setUserDbId(userDbId);
			input.setQuestion(newQuestion);
			input.setAnswer(newAnswer);

			UserPreferenceReply reply = impl.saveUserPreferences(input);
			UserPreferences modifiedpref = reply.getPreference();

			Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
			Assert.assertNotNull(modifiedpref);
			Assert.assertEquals(userDbId, modifiedpref.getUser().getId());
			Assert.assertEquals(newQuestion, modifiedpref.getQuestion());
			Assert.assertEquals(newAnswer, modifiedpref.getAnswer());

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	@Test
	public void deleteUserPreferencesTest() {

		try {

			long prefId = 0;

			{
				// set up test data
				UserPreferences preferences = dataHelper.createBasicUserPreferences();
				prefId = preferences.getId();
			}

			CodeValueInput input = new CodeValueInput();
			input.setCodeValue(new CodeValue(prefId));
			CodeValueReply reply = impl.deleteUserPreferences(input);

			Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
			Assert.assertEquals(prefId, reply.getCodeValue().getCode());

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	@Test
	public void createTimeDataTest() {

		try {

			// set up test data
			Activity activity = dataHelper.createBasicActivity();
			User user = dataHelper.createBasicUser();

			// create time data
			TimeDataInput myTimeData = new TimeDataInput();
			myTimeData.setActivityId(activity.getId());
			myTimeData.setUserId(user.getId());
			myTimeData.setDate(new Date());
			myTimeData.setDay_1_time(9);
			myTimeData.setDay_2_time(4.25);
			myTimeData.setDay_3_time(4.4);
			myTimeData.setDay_4_time(4.5);
			myTimeData.setDay_5_time(4.9);

			CodeValueReply reply = impl.createTimeData(myTimeData);

			Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
			Assert.assertTrue(reply.getCodeValue().getCode() > 0);

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	@Test
	public void deleteTimeData() {

		try {
			TimeData timeData = dataHelper.createBasicTimeData();

			CodeValueInput input = new CodeValueInput();
			input.setCodeValue(new CodeValue(timeData.getId()));

			CodeValueReply reply = impl.deleteTimeData(input);

			Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
			Assert.assertTrue(reply.getCodeValue().getCode() == timeData.getId());

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	@Test
	public void searchProjectsByNameTest() {

		try {

			// setup test data
			final int projCount = 5;
			final Map<Long, String> projMap = new HashMap<Long, String>();
			final String projectNamePrefix = "Test A ";

			{
				User lead = dataHelper.createAdminUser();

				for (int i = 1; i <= projCount; i++) {
					Project project = dataHelper.createProject(projectNamePrefix + i, lead);
					projMap.put(project.getId(), project.getName());
				}
			}

			ProjectSearchParameter searchParameters = new ProjectSearchParameter();
			searchParameters.setProjectName(projectNamePrefix);

			ProjectReply reply = impl.searchProjects(searchParameters);

			Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
			Assert.assertNotNull("No projects found", reply.getProjects());
			Assert.assertEquals("Result size incorrect.", projCount, reply.getProjects().size());

			for (CodeValue project : reply.getProjects()) {
				Assert.assertTrue("Incorrect Id.", projMap.containsKey(project.getCode()));
				Assert.assertEquals("Incorrect Id/Name mapping.", projMap.get(project.getCode()), project.getValue());
			}

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	@Test
	public void searchProjectsByIdTest() {

		try {

			// setup test data
			final int projCount = 5;
			final Map<Long, String> projMap = new HashMap<Long, String>();
			final String projectNamePrefix = "Test B";

			{
				User lead = dataHelper.createAdminUser();

				for (int i = 1; i <= projCount; i++) {
					Project project = dataHelper.createProject(projectNamePrefix + i, lead);
					projMap.put(project.getId(), project.getName());
				}
			}

			final long projId = projMap.keySet().iterator().next();

			ProjectSearchParameter searchParameters = new ProjectSearchParameter();
			searchParameters.setProjectId(projId);

			ProjectReply reply = impl.searchProjects(searchParameters);

			Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
			Assert.assertNotNull("No projects found", reply.getProjects());
			Assert.assertEquals("Result size incorrect.", reply.getProjects().size(), 1);

			CodeValue codeValue = reply.getProjects().iterator().next();

			Assert.assertEquals("Incorrect Id.", projId, codeValue.getCode());
			Assert.assertEquals("Incorrect Id/Name mapping.", projMap.get(projId), codeValue.getValue());

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	@Test
	public void searchActivitiesTest() {

		try {

			final String projName = "Prj ";
			final int projCount = 2;
			Map<Long, String> projNameMap = new HashMap<Long, String>();
			Map<Long, Project> projMap = new HashMap<Long, Project>();

			// setup project data
			{
				User lead = dataHelper.createAdminUser();
				Project project = null;

				for (int i = 1; i <= projCount; i++) {
					project = dataHelper.createProject(projName + i, lead);
					projMap.put(project.getId(), project);
					projNameMap.put(project.getId(), projName + i);
				}
			}

			final String actName = "MAct ";
			final int actCount = 3;
			Map<Long, String> actMap = new HashMap<Long, String>();

			Map<Long, Set<Activity>> mapping = new HashMap<Long, Set<Activity>>();

			// setup activity data
			{
				Activity activity = null;

				for (Project project : projMap.values()) {

					for (int j = 1; j <= actCount; j++) {

						activity = dataHelper.createActivity(actName + j, project);
						actMap.put(activity.getId(), actName + j);

						if (mapping.get(project.getId()) == null) {
							mapping.put(project.getId(), new HashSet<Activity>());
						}

						mapping.get(project.getId()).add(activity);
					}
				}
			}

			// search by project id
			{
				final long projId = projMap.keySet().iterator().next();

				ActivitySearchParameter searchParameters = new ActivitySearchParameter();
				searchParameters.setProjectId(projId);

				ActivityReply reply = impl.searchActivities(searchParameters);

				Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
				Assert.assertTrue("No activities found", (reply.getActivityCount() > 0));
				Assert.assertEquals("Result activity size incorrect.", reply.getActivityCount(), actCount);
				Assert.assertEquals("Result project size incorrect.", reply.getProjectIds().size(), 1);

			}

			// search by project name
			{
				ActivitySearchParameter searchParameters = new ActivitySearchParameter();
				searchParameters.setProjectName(projName + 1);

				ActivityReply reply = impl.searchActivities(searchParameters);

				Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
				Assert.assertTrue("No activities found", (reply.getActivityCount() > 0));
				Assert.assertEquals("Result size incorrect.", (1 * actCount), reply.getActivityCount());

				for (long projectId : reply.getProjectIds()) {
					Assert.assertTrue("Incorrect Project Id.", projMap.containsKey(projectId));
					Assert.assertEquals("Incorrect Activity Count.", mapping.get(projectId).size(), reply
							.getProjectActivitiesById(projectId).size());

					for (CodeValue activity : reply.getProjectActivitiesById(projectId)) {
						Assert.assertTrue("Incorrect Activity results.", actMap.containsKey(activity.getCode()));
						Assert.assertEquals("Incorrect Activity results.", actMap.get(activity.getCode()),
								activity.getValue());
					}

				}
			}

			// search by activity name
			{
				ActivitySearchParameter searchParameters = new ActivitySearchParameter();

				final long projId = projMap.keySet().iterator().next();
				final long activityId = mapping.get(projId).iterator().next().getId();

				searchParameters.setActivityName(actMap.get(activityId));

				ActivityReply reply = impl.searchActivities(searchParameters);

				Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
				Assert.assertTrue("No activities found", (reply.getActivityCount() > 0));
				Assert.assertEquals("Result size incorrect.", (1 * projCount), reply.getActivityCount());

				for (long projectId : reply.getProjectIds()) {
					Assert.assertTrue("Incorrect Project Id.", projMap.containsKey(projectId));

					for (CodeValue activity : reply.getProjectActivitiesById(projectId)) {
						Assert.assertTrue("Incorrect Activity results.", actMap.containsKey(activity.getCode()));
						Assert.assertEquals("Incorrect Activity results.", actMap.get(activity.getCode()),
								activity.getValue());
					}
				}
			}

			// search by activity id
			{
				ActivitySearchParameter searchParameters = new ActivitySearchParameter();

				final long projId = projMap.keySet().iterator().next();
				final long activityId = mapping.get(projId).iterator().next().getId();

				searchParameters.setActivityId(activityId);

				ActivityReply reply = impl.searchActivities(searchParameters);

				Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
				Assert.assertTrue("No activities found", (reply.getActivityCount() > 0));
				Assert.assertEquals("Result size incorrect.", 1, reply.getActivityCount());

				long projectId = reply.getProjectIds().iterator().next();
				Assert.assertTrue("Incorrect Project Id.", projMap.containsKey(projectId));

				CodeValue activity = reply.getProjectActivitiesById(projectId).iterator().next();
				Assert.assertTrue("Incorrect Activity results.", (activityId == activity.getCode()));
				Assert.assertEquals("Incorrect Activity results.", actMap.get(activity.getCode()), activity.getValue());

			}
		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	@Test
	public void searchUsersTest() {

		try {

			final long userCount = 5;
			final String fNamePrefix = "SrcUsrF ";
			final String lNamePrefix = "SrcUsrL ";
			final String uidPrefix = "Uid ";
			final String pwdPrefix = "Pass ";

			Map<Long, String> firstNameMap = new HashMap<Long, String>();
			Map<Long, String> lastNameMap = new HashMap<Long, String>();

			// set up user data
			{
				User user = null;

				for (int i = 1; i <= userCount; i++) {
					user = dataHelper.createUser(fNamePrefix + i, lNamePrefix + i, uidPrefix + i, pwdPrefix + i, true);
					firstNameMap.put(user.getId(), user.getFirstName());
					lastNameMap.put(user.getId(), user.getLastName());
				}
			}

			// search by user id
			{
				final long userId = firstNameMap.keySet().iterator().next();
				UserSearchParameter searchParameters = new UserSearchParameter();
				searchParameters.setId(userId);

				UserSearchReply reply = impl.searchUsers(searchParameters);

				Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
				Assert.assertTrue("No users found", (reply.getUsers() != null));
				Assert.assertEquals("Result size incorrect.", 1, reply.getUsers().size());

				Assert.assertEquals("Incorrect User Id found", userId, (reply.getUsers().iterator().next().getId()));
				Assert.assertEquals("Incorrect User First Name found", firstNameMap.get(userId), (reply.getUsers()
						.iterator().next().getFirstName()));
				Assert.assertEquals("Incorrect User Last Name found", lastNameMap.get(userId), (reply.getUsers()
						.iterator().next().getLastName()));
			}

			// search by first name
			{
				UserSearchParameter searchParameters = new UserSearchParameter();
				searchParameters.setFirstName(fNamePrefix);

				UserSearchReply reply = impl.searchUsers(searchParameters);

				Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
				Assert.assertTrue("No users found", (reply.getUsers() != null));
				Assert.assertEquals("Result size incorrect.", userCount, reply.getUsers().size());

				for (User user : reply.getUsers()) {
					Assert.assertTrue("Incorrect User Id found", firstNameMap.containsKey(user.getId()));
					Assert.assertEquals("Incorrect User First Name found", firstNameMap.get(user.getId()),
							user.getFirstName());
					Assert.assertEquals("Incorrect User Last Name found", lastNameMap.get(user.getId()),
							user.getLastName());
				}
			}

			// search by last name
			{
				UserSearchParameter searchParameters = new UserSearchParameter();
				searchParameters.setLastName(lNamePrefix);

				UserSearchReply reply = impl.searchUsers(searchParameters);

				Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
				Assert.assertTrue("No users found", (reply.getUsers() != null));
				Assert.assertEquals("Result size incorrect.", userCount, reply.getUsers().size());

				for (User user : reply.getUsers()) {
					Assert.assertTrue("Incorrect User Id found", firstNameMap.containsKey(user.getId()));
					Assert.assertEquals("Incorrect User First Name found", firstNameMap.get(user.getId()),
							user.getFirstName());
					Assert.assertEquals("Incorrect User Last Name found", lastNameMap.get(user.getId()),
							user.getLastName());
				}
			}

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	@Test
	public void searchUserPreferencesTest() {

		try {

			// create data
			{
				Map<Long, User> userMap = new HashMap<Long, User>();
				final int count = 5;
				User user = null;

				// set up users
				{
					for (int i = 1; i <= count; i++) {
						user = dataHelper.createBasicUser();
						userMap.put(user.getId(), user);
					}
				}

				final String quesPrefix = "Ques ";
				final String ansPrefix = "Answ ";

				// create user preferences
				{
					for (int i = 1; i <= count; i++) {
						user = userMap.values().iterator().next();
						dataHelper.createUserPreferences(quesPrefix + i, ansPrefix + i, user);
					}
				}

				// search by user id
				{
					UserPreferenceSearchParameter searchParameters = new UserPreferenceSearchParameter();
					final long userId = userMap.keySet().iterator().next();
					searchParameters.setId(userId);

					UserPreferenceSearchReply prefReply = impl.searchUserPreferences(searchParameters);

					Assert.assertTrue("Reply has error : " + prefReply.getMessage(), !prefReply.isMsgError());
					Assert.assertTrue("No results found", (prefReply.getPreference(userId) != null));
					Assert.assertEquals("Result user incorrect.", userId, prefReply.getPreference(userId).getUser()
							.getId());

				}
			}

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	@Test
	public void searchTimeDataTest() {

		try {

			final int dataCount = 5;
			final int relatedEntityCount = 2;

			// setup data
			List<Long> projList = new ArrayList<Long>();
			List<Long> activityList = new ArrayList<Long>();
			List<Long> userList = new ArrayList<Long>();
			List<Long> dataList = new ArrayList<Long>();
			Map<Long, Set<Long>> projTimeMap = new HashMap<Long, Set<Long>>();
			Map<Long, Set<Long>> actTimeMap = new HashMap<Long, Set<Long>>();
			Map<Long, Set<Long>> userTimeMap = new HashMap<Long, Set<Long>>();

			{
				// setup test data
				{
					final String projNamePrefix = "TDP ";
					final String actNamePrefix = "TDA ";

					User lead = null;
					Project project = null;
					Activity activity = null;

					for (int i = 1; i <= relatedEntityCount; i++) {
						lead = dataHelper.createAdminUser();
						project = dataHelper.createProject(projNamePrefix + i, lead);
						activity = dataHelper.createActivity(actNamePrefix + i, project);

						projList.add(0, project.getId());
						projTimeMap.put(project.getId(), new HashSet<Long>());
						activityList.add(0, activity.getId());
						actTimeMap.put(activity.getId(), new HashSet<Long>());
					}
				}

				// set up user
				{
					final String firstNamePrefix = "UF ";
					final String lastNamePrefix = "UL ";
					final String userIdPrefix = "UI ";
					final String pwdPrefix = "UPWD ";
					User user = null;

					for (int i = 1; i <= relatedEntityCount; i++) {

						user = dataHelper.createUser(firstNamePrefix + i, lastNamePrefix + i, userIdPrefix + i,
								pwdPrefix + i, false);

						userList.add(0, user.getId());
						userTimeMap.put(user.getId(), new HashSet<Long>());
					}

				}

				// set time data
				TimeDataInput myTimeData = null;

				for (int i = 0; i < relatedEntityCount; i++) {

					for (int j = 1; j <= dataCount; j++) {

						myTimeData = new TimeDataInput();
						myTimeData.setActivityId(activityList.get(i));
						myTimeData.setUserId(userList.get(i));
						myTimeData.setDate(TextHelper.getDateAfter(new Date(), 7 * j * (i + 1)));

						myTimeData.setDay_1_time(3.1 + j);
						myTimeData.setDay_2_time(4.2 + j);
						myTimeData.setDay_3_time(5.3 + j);
						myTimeData.setDay_4_time(6.4 + j);
						myTimeData.setDay_5_time(7.5 + j);
						myTimeData.setDay_6_time(8.6 + j);
						myTimeData.setDay_7_time(9.7 + j);

						CodeValueReply reply = impl.createTimeData(myTimeData);

						dataList.add(reply.getCodeValue().getCode());
						projTimeMap.get(projList.get(i)).add(reply.getCodeValue().getCode());

						actTimeMap.get(activityList.get(i)).add(reply.getCodeValue().getCode());
						userTimeMap.get(userList.get(i)).add(reply.getCodeValue().getCode());
					}
				}
			}

			// search by project id
			{
				final long projId = projList.get(0);
				TimeDataSearchParameters searchParameters = new TimeDataSearchParameters();
				searchParameters.setProjectId(projId);

				TimeDataReply reply = impl.searchTimeData(searchParameters);

				Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
				Assert.assertTrue("No results found", (reply.getEntriesForProject(projId) != null));
				Assert.assertEquals("Result size incorrect.", dataCount, reply.getEntriesForProject(projId).size());

				for (TimeDataRow row : reply.getEntriesForProject(projId)) {
					Assert.assertTrue("Incorrect project in results found", (row.getProjectId() == projId));
				}
			}

			// search by activity id
			{
				final long activityId = activityList.get(0);
				TimeDataSearchParameters searchParameters = new TimeDataSearchParameters();
				searchParameters.setActivityid(activityId);

				TimeDataReply reply = impl.searchTimeData(searchParameters);

				Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
				Assert.assertTrue("No results found", (reply.getEntriesForActivity(activityId) != null));
				Assert.assertEquals("Result size incorrect.", dataCount, reply.getEntriesForActivity(activityId).size());

				for (TimeDataRow row : reply.getEntriesForActivity(activityId)) {
					Assert.assertTrue("Incorrect activity in results found", (row.getActivityId() == activityId));
				}
			}

			// search by user id
			{
				final long userId = userList.get(0);
				TimeDataSearchParameters searchParameters = new TimeDataSearchParameters();
				searchParameters.setUserId(userId);

				TimeDataReply reply = impl.searchTimeData(searchParameters);

				Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
				Assert.assertTrue("No results found", (reply.getEntriesForUser(userId) != null));
				Assert.assertEquals("Result size incorrect.", dataCount, reply.getEntriesForUser(userId).size());

				for (TimeDataRow row : reply.getEntriesForUser(userId)) {
					Assert.assertTrue("Incorrect user in results found", (row.getUserId() == userId));
				}
			}

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}

	@Test
	public void searchAuditTest() {

		@SuppressWarnings("unused")
		User user = dataHelper.createAdminUser();
		AuditDataSearchParameters searchParameters = new AuditDataSearchParameters();

		try {

			// search by data type
			{
				searchParameters.setType(AuditDataType.USER);
				AuditDataReply reply = impl.searchAuditData(searchParameters);

				Assert.assertNotNull(reply);
				Assert.assertTrue("Reply has error : " + reply.getMessage(), !reply.isMsgError());
				Assert.assertNotNull(reply.getRowList());
				Assert.assertTrue(reply.getRowList().size() > 0);

				for (AuditRow row : reply.getRowList()) {
					System.out.println(row);
				}
			}

		} catch (TimelineException e) {
			Assert.fail("TimelineException occurred.");
			e.printStackTrace();
		}
	}
}
