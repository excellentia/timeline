package org.ag.timeline.common;

/**
 * Constants used throughout application.
 * 
 * @author Abhishek Gaurav
 */
public interface TimelineConstants {

	public String DOT = ".";

	public String EMPTY = "";

	public String COMMA = ",";

	public String DATABASE_NAME = "timelinedb";

	public String DATABASE_PATH = "database/" + DATABASE_NAME;

	public String DATABASE_DRIVER = "org.hsqldb.jdbc.JDBCDriver";

	public String DATABASE_WEB_URL = "jdbc:hsqldb:hsql://localhost/" + DATABASE_NAME;

	public String DATABASE_USER_NAME = "sa";

	public String DATABASE_USER_PASSWORD = "";

	public String DATABASE_SCHEMA = "TIMELINE_SCHEMA";

	public String SPACE = " ";

	public String WEEK_SEPARATOR = " - ";

	public static enum StatusEntity {
		PROJECT_ACTIVE_FLAG, PROJECT_METRICS_FLAG, USER_ACTIVE_FLAG, TASK_ACTIVE_FLAG;

		public final String getText() {
			return this.toString();
		}
	}

	public static enum RequestType {
		GET, POST;

		public final String getText() {
			return this.toString();
		}
	}

	public static enum SessionAttribute {
		SESSION_USER, PROJECT_LIST, ACTIVITY_LIST, USER_LIST, DATA_TYPE_LIST, OPERATION_TYPE_LIST;

		public final String getText() {
			return this.toString();
		}
	}

	public static enum AuditDataType {
		PROJECT(1, "Project Data"),
		ACTIVITY(2, "Activity Data"),
		TIME_DATA(3, "Time Entry Data"),
		USER(4, "User Data"),
		METRICS(5, "Metrics"),
		TASK(6, "Task");

		private final int typeId;

		private final String typeText;

		private AuditDataType(final int id, final String text) {
			this.typeId = id;
			this.typeText = text;
		}

		public final int getTypeId() {
			return this.typeId;
		}

		public final String getTypeText() {
			return this.typeText;
		}

		public static final AuditDataType getAuditDataType(final int typeId) {
			AuditDataType retVal = null;

			{
				AuditDataType[] vals = AuditDataType.values();

				for (AuditDataType op : vals) {
					if (typeId == op.getTypeId()) {
						retVal = op;
						break;
					}
				}
			}

			return retVal;
		}
	}

	public static enum FieldType {
		DATA(1), REFERENCE(2);

		private final int typeId;

		private FieldType(final int id) {
			this.typeId = id;
		}

		public final int getTypeId() {
			return this.typeId;
		}

		public static final FieldType getFieldType(final int typeId) {
			FieldType retVal = null;

			{
				FieldType[] vals = FieldType.values();

				for (FieldType op : vals) {
					if (typeId == op.getTypeId()) {
						retVal = op;
						break;
					}
				}
			}

			return retVal;
		}
	}

	public static enum EntityStatus {
		
		ALL(0), ACTIVE(1), INACTIVE(2);

		private final int typeId;

		private EntityStatus(final int id) {
			this.typeId = id;
		}

		public final int getTypeId() {
			return this.typeId;
		}

		public static final EntityStatus getEntityStatus(final int typeId) {
			EntityStatus retVal = null;
			EntityStatus[] vals = EntityStatus.values();

			for (EntityStatus op : vals) {
				if (typeId == op.getTypeId()) {
					retVal = op;
					break;
				}
			}

			return retVal;
		}
	}

	public static enum AjaxRequestParam {
		operation,
		id,
		refId,
		text,
		status,
		field,
		entryId,
		projectId,
		activityId,
		weekStartDate,
		weekEndDate,
		weekStartYear,
		weekEndYear,
		startYear,
		endYear,
		startWeekNum,
		endWeekNum,
		userDbId,
		proxiedUserDbId,
		day1,
		day2,
		day3,
		day4,
		day5,
		day6,
		day7,
		metricId,
		bac,
		startDate,
		endDate,
		pv,
		ev,
		ac,
		atd,
		spe,
		bug,
		taskId,
		description,
		size;

		public static final AjaxRequestParam getParam(String text) {
			AjaxRequestParam type = null;

			String incomingText = TextHelper.trimToNull(text);

			if (incomingText != null) {
				for (AjaxRequestParam name : AjaxRequestParam.values()) {
					if (incomingText.equalsIgnoreCase(name.toString())) {
						type = name;
						break;
					}
				}
			}

			return type;
		}

		public final String getParamText() {
			return this.toString();
		}
	};

	public static enum OperationType {
		PROJECT(false),
		ACTIVTIES(false),
		LEAD(false),
		TASKS(false),
		SAVE_PROJECT(true),
		SAVE_PROJECT_STATUS(true),
		SAVE_PROJECT_METRICS_STATUS(true),
		SAVE_ACTIVITY(true),
		SAVE_LEAD(true),
		SAVE_USER(true),
		SAVE_USER_STATUS(true),
		SAVE_TIME_ENTRY(false),
		SAVE_TASK(false),
		SAVE_TASK_STATUS(false),
		SAVE_ESTIMATES(true),
		SAVE_METRIC_DETAILS(true),
		DELETE_PROJECT(true),
		DELETE_ACTIVITY(true),
		DELETE_USER(true),
		DELETE_TIME_ENTRY(false),
		DELETE_TASK(false),
		DELETE_METRIC_DETAILS(true),
		DELETE_ALL_METRICS(true),
		RESET_USER(true),
		MODIFY_USER(false),
		MODIFY_USER_PREF(false),
		MODIFY_TASK_STAGE(false),
		SEARCH_ENTRIES(false),
		SEARCH_USERS_WITHOUT_ENTRIES(false),
		SEARCH_PROJECT_METRICS(true),
		SEARCH_PROJECT_DETAIL_METRICS(true),
		SEARCH_ESTIMATES(true),
		REPORT_DETAIL(false),
		SEARCH_TASK_DETAIL(false);
//		SEARCH_PROJECT_STAGES(true),
//		SEARCH_POSSIBLE_STAGES(true),
//		SAVE_STAGE(true),
//		MODIFY_PROJECT_STAGE(true),
//		SEARCH_STAGES(true),
//		DELETE_STAGE(true);

		/**
		 * Denotes if an operation can be done by admin only.
		 */
		private final boolean adminAccessOnly;

		/**
		 * @param adminAccessOnly
		 */
		private OperationType(boolean adminAccessOnly) {
			this.adminAccessOnly = adminAccessOnly;
		}

		public final String getParamText() {
			return this.toString();
		}

		/**
		 * Getter for adminAccessOnly.
		 * 
		 * @return the adminAccessOnly
		 */
		public final boolean isAdminAccessOnly() {
			return adminAccessOnly;
		}

		public static final OperationType getOperationType(String text) {
			OperationType type = null;

			String incomingText = TextHelper.trimToNull(text);

			if (incomingText != null) {
				for (OperationType name : OperationType.values()) {
					if (incomingText.equalsIgnoreCase(name.toString())) {
						type = name;
						break;
					}
				}
			}

			return type;
		}

	}

	public static enum UserDataFieldType {

		FIRST_NAME, LAST_NAME, USER_ID, PASSWORD, ADMIN, ACTIVE;

		public static final UserDataFieldType getType(String text) {
			UserDataFieldType type = null;

			String incomingText = TextHelper.trimToNull(text);

			if (incomingText != null) {
				for (UserDataFieldType name : UserDataFieldType.values()) {
					if (incomingText.equalsIgnoreCase(name.toString())) {
						type = name;
						break;
					}
				}
			}

			return type;
		}

		public final String getParamText() {
			return this.toString();
		}
	}

	public static enum UserPrefDataFieldType {

		QUESTION, ANSWER, EMAIL;

		public static final UserPrefDataFieldType getType(String text) {
			UserPrefDataFieldType type = null;

			String incomingText = TextHelper.trimToNull(text);

			if (incomingText != null) {
				for (UserPrefDataFieldType name : UserPrefDataFieldType.values()) {
					if (incomingText.equalsIgnoreCase(name.toString())) {
						type = name;
						break;
					}
				}
			}

			return type;
		}

		public final String getParamText() {
			return this.toString();
		}
	}

	/**
	 * Constants for Data operations (like create, updated, delete).
	 * 
	 * @author Abhishek Gaurav
	 */
	public static enum EntityOperation {
		CREATE(1, "Create"), UPDATE(2, "Update"), DELETE(3, "Delete");

		/**
		 * Operation Code
		 */
		private final int opCode;

		/**
		 * Operation Text.
		 */
		private final String opText;

		/**
		 * Parameterised Constructor.
		 * 
		 * @param code Code to be set.
		 * @param text Text to be set.
		 */
		private EntityOperation(final int code, final String text) {
			this.opCode = code;
			this.opText = text;
		}

		/**
		 * Getter for opCode.
		 * 
		 * @return the opCode
		 */
		public int getOpCode() {
			return opCode;
		}

		/**
		 * Getter for opText.
		 * 
		 * @return the opText
		 */
		public String getOpText() {
			return opText;
		}

		public static final EntityOperation getOperation(final int code) {
			EntityOperation retVal = null;

			{
				EntityOperation[] vals = EntityOperation.values();

				for (EntityOperation op : vals) {
					if (code == op.getOpCode()) {
						retVal = op;
						break;
					}
				}
			}

			return retVal;
		}
	}
}
