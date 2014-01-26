package org.ag.timeline.business.util.audit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ag.timeline.application.context.RequestContext;
import org.ag.timeline.application.context.TimelineContext;
import org.ag.timeline.business.model.AbstractModel;
import org.ag.timeline.business.model.Activity;
import org.ag.timeline.business.model.Project;
import org.ag.timeline.business.model.Task;
import org.ag.timeline.business.model.TimeData;
import org.ag.timeline.business.model.User;
import org.ag.timeline.business.model.UserPreferences;
import org.ag.timeline.business.model.Week;
import org.ag.timeline.business.model.metrics.ProjectMetrics;
import org.ag.timeline.common.TextHelper;
import org.ag.timeline.common.TimelineConstants;

/**
 * Package Helper class for Audit related functionality.
 * 
 * @author Abhishek Gaurav
 */
final class AuditHelper {

	/**
	 * Mask used for encryption.
	 */
	private static final String mask = "**************************************************";

	/**
	 * Stores class name as key, and single object of same class as value.
	 */
	private static final Map<String, Object> referenceMap = new HashMap<String, Object>();

	/**
	 * List containing property names related to Audit information.
	 */
	private static final List<String> autoPoplulatedFields = new ArrayList<String>();

	/**
	 * List containing property names that are encoded .
	 */
	private static final List<String> encodedProperties = new ArrayList<String>();

	/**
	 * Map containing properties that are ignored.
	 */
	private static final Map<String, List<String>> ignoreMap = new HashMap<String, List<String>>();

	/**
	 * Map containing entity names mapped to field names and presentation
	 * specific names.
	 */
	private static final Map<String, Map<String, String>> entityFieldNameMap = new HashMap<String, Map<String, String>>();

	/**
	 * No one accesses this outside this class.
	 */
	private AuditHelper() {
		// no access
	}

	/**
	 * Returns context user id.
	 * 
	 * @return long unique database identifier of the current user.
	 */
	static long getContextUserId() {
		long userId = -1;
		TimelineContext context = RequestContext.getTimelineContext();

		if (context != null) {
			userId = context.getContextUserId();
		}

		return userId;
	}

	/**
	 * Create a string representation of the incoming object, ensuring only
	 * non-null objects are handled.
	 * 
	 * @param obj Object whose {@link String} representation is required.
	 * @return null / String.
	 */
	static String getNullSafeString(Object obj) {
		String retVal = null;

		if (obj != null) {
			retVal = TextHelper.trimToNull(String.valueOf(obj));
		}

		return retVal;
	}

	/**
	 * Encodes incoming string.
	 * 
	 * @param plainText String text to be encoded.
	 * @return Encoded String.
	 */
	static String getEncodedString(String plainText) {
		String retVal = null;

		if (plainText != null) {
			retVal = AuditHelper.mask.substring(0, plainText.length());
		}

		return retVal;
	}

	static String getNullSafeCodeId(AbstractModel codeValue) {
		String value = null;

		if (codeValue != null) {
			value = String.valueOf(codeValue.getId());
		}

		return value;
	}

	static String getNullSafeUserName(User user) {
		String value = null;

		if (user != null) {
			value = String.valueOf(user.getUserName());
		}

		return value;
	}

	static String getNullSafeProjectName(Project project) {
		String value = null;

		if (project != null) {
			value = String.valueOf(project.getName());
		}

		return value;
	}

	static String getNullSafeActivityName(Activity activity) {
		String value = null;

		if (activity != null) {
			value = String.valueOf(activity.getName());
		}

		return value;
	}

	static String getNullSafeWeekText(Week week) {
		String value = null;

		if (week != null) {
			value = String.valueOf(TextHelper.getDisplayWeek(week.getStartDate(), week.getEndDate()));
		}

		return value;
	}
	
	static String getNullSafeTaskText(Task task) {
		String value = null;

		if (task != null) {
			value = task.getDescription();
		}

		return value;
	}
	
	static String getNullSafeTimestamp(Date date) {
		String value = null;

		if (date != null) {
			value = TextHelper.getAuditTimestamp(date);
		}

		return value;
	}

	static String getNullSafeWeekDay(Date date) {
		String value = null;

		if (date != null) {
			value = TextHelper.getDisplayWeekDay(date);
		}

		return value;
	}

	static String getNullSafeBigDecValue(BigDecimal number) {
		String value = null;

		if (number != null) {
			value = String.valueOf(TextHelper.getScaledBigDecimal(number));
		}

		return value;
	}

	static TimelineConstants.AuditDataType getDataType(String entityName) {
		TimelineConstants.AuditDataType type = null;

		if (entityName.contains("Task")) {
			type = TimelineConstants.AuditDataType.TASK;
		} else if (entityName.contains("Metrics")) {
			type = TimelineConstants.AuditDataType.METRICS;
		} else if (entityName.contains("Project")) {
			type = TimelineConstants.AuditDataType.PROJECT;
		} else if (entityName.contains("User")) {
			type = TimelineConstants.AuditDataType.USER;
		} else if (entityName.contains("Time")) {
			type = TimelineConstants.AuditDataType.TIME_DATA;
		} else if (entityName.contains("Activity")) {
			type = TimelineConstants.AuditDataType.ACTIVITY;
		}

		return type;
	}

	/**
	 * Returns the object of referenced association type.
	 * 
	 * @param className Class name of the object to be fetched.
	 * @return Object.
	 */
	static Object getRefObject(final String className) {

		Object obj = referenceMap.get(className);

		if (obj == null) {
			try {
				obj = Class.forName(className).newInstance();
				referenceMap.put(className, obj);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return obj;
	}

	/**
	 * Creates a list of fields that encoded in model objects.
	 * 
	 * @return List<String> containing field names.
	 */
	static List<String> getEncodedFieldList() {

		if (encodedProperties.size() == 0) {
			encodedProperties.add("password");
			encodedProperties.add("answer");
		}

		return encodedProperties;
	}

	/**
	 * Field names that are auto populated in {@link AbstractModel} objects.
	 * 
	 * @author Abhishek Gaurav
	 */
	static enum AutoPopulateAuditField {
		createDate, createUserId, modifyUserId, modifyDate;

		public final String getText() {
			return this.toString();
		}
	}

	/**
	 * Populates and returns a map containing auditable fields.
	 * 
	 * @return Map<String, Map<String, String>> containing auditable fields with
	 *         entity names as key.
	 */
	private static Map<String, Map<String, String>> getEntityFieldNamesMap() {

		if (entityFieldNameMap.keySet().isEmpty()) {

			// Project entity
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", "Name");
				map.put("lead", "Lead");
				map.put("active", "Active");
				map.put("budgetAtCompletion", "BAC");
				map.put("startDate", "Start Date");
				map.put("endDate", "End Date");

				entityFieldNameMap.put(Project.class.getSimpleName(), map);
			}

			// Activity entity
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", "Name");

				entityFieldNameMap.put(Activity.class.getSimpleName(), map);
			}

			// User entity
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("firstName", "First Name");
				map.put("lastName", "First Name");
				map.put("userId", "User Id");
				map.put("password", "Password");
				map.put("admin", "Admin");
				map.put("active", "Active");

				entityFieldNameMap.put(User.class.getSimpleName(), map);
			}

			// UserPreferences entity
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("question", "Question");
				map.put("answer", "Answer");

				entityFieldNameMap.put(UserPreferences.class.getSimpleName(), map);
			}

			// TimeData entity
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("data_weekday_1", "Day 1 Time");
				map.put("data_weekday_2", "Day 2 Time");
				map.put("data_weekday_3", "Day 3 Time");
				map.put("data_weekday_4", "Day 4 Time");
				map.put("data_weekday_5", "Day 5 Time");
				map.put("data_weekday_6", "Day 6 Time");
				map.put("data_weekday_7", "Day 7 Time");

				entityFieldNameMap.put(TimeData.class.getSimpleName(), map);
			}

			// Week entity
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("year", "Year");
				map.put("weekNumber", "Week Number");
				map.put("startDate", "Start Date");
				map.put("endDate", "End Date");

				entityFieldNameMap.put(Week.class.getSimpleName(), map);
			}

			// Metrics entity
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("plannedValue", "PV");
				map.put("earnedValue", "EV");
				map.put("actualCost", "AC");
				map.put("actualsToDate", "ATD");
				map.put("softwareProgrammingEffort", "SPE");
				map.put("defects", "Bugs");

				entityFieldNameMap.put(ProjectMetrics.class.getSimpleName(), map);
			}

			// Task entity
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("text", "Name");
				map.put("details", "Description");
				map.put("activity", "Activity");

				entityFieldNameMap.put(Task.class.getSimpleName(), map);
			}
		}

		return entityFieldNameMap;

	}

	/**
	 * Auditable fields in business entities.
	 * 
	 * @author Abhishek Gaurav
	 */
	static enum AuditableEntityField {

		PROJECT(Project.class.getSimpleName()),
		ACTIVITY(Activity.class.getSimpleName()),
		USER(User.class.getSimpleName()),
		PREFERENCES(UserPreferences.class.getSimpleName()),
		TIME_DATA(TimeData.class.getSimpleName()),
		WEEK(Week.class.getSimpleName()),
		METRICS(ProjectMetrics.class.getSimpleName()),
		TASK(Task.class.getSimpleName());

		private final String entityName;

		private final List<String> auditableFields;

		private final Map<String, String> fieldMap;

		private static final Map<String, List<String>> auditableFieldMap = new HashMap<String, List<String>>();

		/**
		 * Constructor.
		 * 
		 * @param entity String entity name.
		 */
		private AuditableEntityField(String entity) {
			this.entityName = entity;
			this.fieldMap = getEntityFieldNamesMap().get(entity);

			this.auditableFields = new ArrayList<String>();
			this.auditableFields.addAll(this.fieldMap.keySet());
		}

		/**
		 * Getter for entityName.
		 * 
		 * @return the entityName.
		 */
		public String getEntityName() {
			return this.entityName;
		}

		/**
		 * Getter for auditableFields.
		 * 
		 * @return the auditableFields.
		 */
		public List<String> getAuditableFields() {
			return this.auditableFields;
		}

		/**
		 * Getter for fieldMap.
		 * 
		 * @return the fieldMap.
		 */
		public Map<String, String> getFieldMap() {
			return this.fieldMap;
		}

		/**
		 * Getter for auditablefieldmap.
		 * 
		 * @return the auditablefieldmap.
		 */
		public static final Map<String, List<String>> getAuditablefieldmap() {

			if (auditableFieldMap.keySet().isEmpty()) {

				for (AuditableEntityField entity : AuditableEntityField.values()) {
					auditableFieldMap.put(entity.getEntityName(), entity.getAuditableFields());
				}
			}

			return auditableFieldMap;
		}
	}

	/**
	 * Checks if a given field is auditable.
	 * 
	 * @param auditableEntityField String entity containing the field to be
	 *            checked.
	 * @param fieldName {@link String} field name.
	 * @return boolean true if auditable, false otherwise.
	 */
	static final boolean isAuditableField(final String entityName, final String fieldName) {

		Map<String, List<String>> map = AuditableEntityField.getAuditablefieldmap();

		boolean auditable = false;
		List<String> fieldList = map.get(entityName);

		if (fieldList != null) {
			auditable = fieldList.contains(fieldName);
		}

		return auditable;
	}

	/**
	 * Creates a list of fields that are auto populated in model objects.
	 * 
	 * @return List<String> containing field names.
	 */
	static List<String> getAutoPoplulatedFieldList() {

		if (autoPoplulatedFields.size() == 0) {
			for (AuditHelper.AutoPopulateAuditField field : AuditHelper.AutoPopulateAuditField.values()) {
				autoPoplulatedFields.add(field.getText());
			}
		}

		return autoPoplulatedFields;
	}

	/**
	 * Checks if field has to be ignored.
	 * 
	 * @param entityName String parent entity containing the field.
	 * @param field String field to be checked.
	 * @return boolean true if ignored, false otherwise.
	 */
	static boolean isIgnored(final String entityName, final String field) {

		// set to false, prevents ignore by mistake/error in logic
		boolean ignore = false;

		if (ignoreMap.size() == 0) {

			// Time Data
			{
				List<String> fields = new ArrayList<String>();
				fields.add("user");
				fields.add("week");
				ignoreMap.put(TimeData.class.getSimpleName(), fields);
			}

			// User Preferences
			{
				List<String> fields = new ArrayList<String>();
				fields.add("user");
				ignoreMap.put(UserPreferences.class.getSimpleName(), fields);
			}

			// Project
			{
				List<String> fields = new ArrayList<String>();
				fields.add("user");
				ignoreMap.put(Project.class.getSimpleName(), fields);
			}
		}

		if ((entityName != null) && (field != null)) {
			List<String> list = ignoreMap.get(entityName);

			if ((list != null) && (list.contains(field))) {
				ignore = true;
			}
		}

		return ignore;
	}
}
