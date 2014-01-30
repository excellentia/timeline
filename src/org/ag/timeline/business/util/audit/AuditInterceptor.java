/**
 * 
 */
package org.ag.timeline.business.util.audit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ag.timeline.business.model.AbstractModel;
import org.ag.timeline.business.model.Activity;
import org.ag.timeline.business.model.Project;
import org.ag.timeline.business.model.Task;
import org.ag.timeline.business.model.TimeData;
import org.ag.timeline.business.model.User;
import org.ag.timeline.business.model.Week;
import org.ag.timeline.business.model.audit.AuditRecord;
import org.ag.timeline.business.model.audit.AuditRecordDetail;
import org.ag.timeline.business.model.metrics.ProjectMetrics;
import org.ag.timeline.business.util.HibernateUtil;
import org.ag.timeline.common.TextHelper;
import org.ag.timeline.common.TimelineConstants;
import org.hibernate.EmptyInterceptor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.AssociationType;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.EntityType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;

/**
 * Handles the auditing functionality.
 * 
 * @author Abhishek Gaurav
 */
public class AuditInterceptor extends EmptyInterceptor {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -5865695866198462308L;

	/**
	 * List containing audit related property names.
	 */
	private final List<String> auditPropertyNames;

	/**
	 * List containing names of properties that are encoded.
	 */
	private final List<String> encodedProperties;

	/**
	 * Constructor.
	 */
	public AuditInterceptor() {
		auditPropertyNames = AuditHelper.getAutoPoplulatedFieldList();
		encodedProperties = AuditHelper.getEncodedFieldList();
	}

	/**
	 * Creates an {@link AuditLog} for curren change.
	 * 
	 * @param entity Entity that is being audited.
	 * @param id Unique identifier of entity.
	 * @param currentState Array containing current values of all fields that
	 *            comprise the entity.
	 * @param previousState Array containing previous values of all fields that
	 *            comprise the entity.
	 * @param propertyNames Array containing names of all fields that comprise
	 *            the entity.
	 * @param types Array containing type of all fields that comprise the
	 *            entity.
	 * @return {@link AuditLog}.
	 */
	private final AuditLog getChanges(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types, final TimelineConstants.EntityOperation operation) {

		AuditLog auditLog = null;
		Set<FieldAuditEntry> changes = new HashSet<FieldAuditEntry>();
		String entityName = entity.getClass().getSimpleName();
		final Date timestamp = new Date();

		int counter = 0;
		String prevVal = null;
		String newVal = null;
		Type type = null;
		FieldAuditEntry fieldAuditEntry = null;
		Object foreignKeyType = null;

		User user = null;
		Project project = null;
		Activity activity = null;
		Week week = null;
		Task task = null;

		List<String> weekDayList = null;
		boolean isTimeEntry = false;
		int weekDayNum = 0;
		TimelineConstants.FieldType fieldType = TimelineConstants.FieldType.DATA;

		Map<String, String> propertyMap = null;
		boolean isMetricEntry = false;
		boolean isProject = false;
//		boolean isTask = false;

		if (entity instanceof TimeData) {
			weekDayList = TextHelper.getDisplayWeekDayList(((TimeData) entity).getWeek().getStartDate());
			isTimeEntry = true;
		} else if (entity instanceof ProjectMetrics) {
			isMetricEntry = true;
			propertyMap = AuditHelper.AuditableEntityField.METRICS.getFieldMap();
		} else if (entity instanceof Project) {
			isProject = true;
			propertyMap = AuditHelper.AuditableEntityField.PROJECT.getFieldMap();
		} else if(entity instanceof Task) {
//			isTask  = true;
			propertyMap = AuditHelper.AuditableEntityField.TASK.getFieldMap();
		}

		for (String property : propertyNames) {

			if (auditPropertyNames.contains(property)) {

				if ((AuditHelper.AutoPopulateAuditField.createDate.getText().equalsIgnoreCase(property))
						|| (AuditHelper.AutoPopulateAuditField.modifyDate.getText().equalsIgnoreCase(property))) {

					if (TimelineConstants.EntityOperation.CREATE.equals(operation)) {
						currentState[counter] = new Date();
					} else if (TimelineConstants.EntityOperation.UPDATE.equals(operation)) {
						currentState[counter] = new Date();
					}

				} else if ((AuditHelper.AutoPopulateAuditField.createUserId.getText().equalsIgnoreCase(property))
						|| (AuditHelper.AutoPopulateAuditField.modifyUserId.getText().equalsIgnoreCase(property))) {

					if (TimelineConstants.EntityOperation.CREATE.equals(operation)) {
						currentState[counter] = Long.valueOf(AuditHelper.getContextUserId());
					} else if (TimelineConstants.EntityOperation.UPDATE.equals(operation)) {
						currentState[counter] = Long.valueOf(AuditHelper.getContextUserId());
					}
				}
			} else {

				fieldType = TimelineConstants.FieldType.DATA;

				if (AuditHelper.isAuditableField(entityName, property)) {

					type = types[counter];
					prevVal = null;
					newVal = null;
					foreignKeyType = null;

					// Check if property is a reference / association
					if (type instanceof AssociationType) {

						// handle relationship type property
						if (type instanceof EntityType) {

							// reference type
							fieldType = TimelineConstants.FieldType.REFERENCE;

							foreignKeyType = AuditHelper.getRefObject(((EntityType) type).getAssociatedEntityName());

							if (foreignKeyType instanceof AbstractModel) {

								if (foreignKeyType instanceof User) {

									// user type
									user = (User) currentState[counter];

									newVal = AuditHelper.getNullSafeUserName(user);
									prevVal = AuditHelper.getNullSafeUserName((User) previousState[counter]);

								} else if (foreignKeyType instanceof Project) {

									// Project type
									project = (Project) currentState[counter];

									newVal = AuditHelper.getNullSafeProjectName(project);
									prevVal = AuditHelper.getNullSafeProjectName((Project) previousState[counter]);

								} else if (foreignKeyType instanceof Activity) {

									// Activity type
									activity = (Activity) currentState[counter];

									newVal = AuditHelper.getNullSafeActivityName(activity);
									prevVal = AuditHelper.getNullSafeActivityName((Activity) previousState[counter]);

								} else if (foreignKeyType instanceof Week) {

									// week type
									week = (Week) currentState[counter];

									newVal = AuditHelper.getNullSafeWeekText(week);
									prevVal = AuditHelper.getNullSafeWeekText((Week) previousState[counter]);

								} else if (foreignKeyType instanceof Task) {

									// task type
									task = (Task) currentState[counter];

									newVal = AuditHelper.getNullSafeTaskText(task);
									prevVal = AuditHelper.getNullSafeTaskText((Task) previousState[counter]);

								} else {

									// static data type
									newVal = AuditHelper.getNullSafeCodeId((AbstractModel) currentState[counter]);
									prevVal = AuditHelper.getNullSafeCodeId((AbstractModel) previousState[counter]);
								}
							}
						}
					} else if (type instanceof TimeData) {

						// week type
						week = (Week) currentState[counter];

						newVal = AuditHelper.getNullSafeWeekText(week);
						prevVal = AuditHelper.getNullSafeWeekText((Week) previousState[counter]);

					} else if (type instanceof TimestampType) {

						// time / date type

						if (isProject) {
							newVal = AuditHelper.getNullSafeWeekDay((Date) currentState[counter]);
							prevVal = AuditHelper.getNullSafeWeekDay((Date) previousState[counter]);

							property = propertyMap.get(property);

						} else {
							newVal = AuditHelper.getNullSafeTimestamp((Date) currentState[counter]);
							prevVal = AuditHelper.getNullSafeTimestamp((Date) previousState[counter]);
						}

					} else if (type instanceof BigDecimalType) {

						newVal = AuditHelper.getNullSafeBigDecValue((BigDecimal) currentState[counter]);
						prevVal = AuditHelper.getNullSafeBigDecValue((BigDecimal) previousState[counter]);

						// time data type
						if (isTimeEntry) {
							weekDayNum = TextHelper.getIntValue(property.substring(property.length() - 1,
									property.length()));

							if ((weekDayNum > 0) && (weekDayNum <= 7)) {
								property = weekDayList.get(weekDayNum - 1);
							}
						} else if (isMetricEntry) {

							// metric data type
							property = propertyMap.get(property);

						} else if (isProject) {

							// estimate data
							property = propertyMap.get(property);
						}

					} else {

						// handle encoded ones
						if (encodedProperties.contains(property)) {

							newVal = AuditHelper.getEncodedString(AuditHelper.getNullSafeString(currentState[counter]));
							prevVal = AuditHelper.getEncodedString(AuditHelper
									.getNullSafeString(previousState[counter]));

						} else {

							// any other type
							newVal = AuditHelper.getNullSafeString(currentState[counter]);
							prevVal = AuditHelper.getNullSafeString(previousState[counter]);
						}

					}

					if ((((newVal != null) && (!newVal.equalsIgnoreCase(prevVal))) || ((prevVal != null) && (!prevVal
							.equalsIgnoreCase(newVal))))) {

						fieldAuditEntry = new FieldAuditEntry();
						fieldAuditEntry.setFieldName(property);
						fieldAuditEntry.setNewValue(newVal);
						fieldAuditEntry.setOldValue(prevVal);
						fieldAuditEntry.setFieldType(fieldType.getTypeId());
						changes.add(fieldAuditEntry);
					}
				}
			}

			// increment the loop variable
			counter++;
		}

		if ((!changes.isEmpty())) {

			auditLog = new AuditLog();

			auditLog.setFieldAuditData(changes);
			auditLog.setEntityId((Long) id);
			auditLog.setOperation(operation.getOpCode());
			auditLog.setOperationTimestamp(timestamp);
			auditLog.setUserId(AuditHelper.getContextUserId());

			auditLog.setEntityName(((AbstractModel) entity).getDescription());
			auditLog.setDataType(AuditHelper.getDataType(entityName));
		}

		return auditLog;
	}

	/**
	 * Saves the incoming {@link AuditLog} object to database.
	 * 
	 * @param auditLog {@link AuditLog} to be saved.
	 */
	private void saveAudits(AuditLog auditLog) {
		if ((auditLog != null) && (auditLog.getFieldAuditData() != null) && (!auditLog.getFieldAuditData().isEmpty())) {

			Transaction transaction = null;
			Session session = null;
			Set<FieldAuditEntry> logs = auditLog.getFieldAuditData();

			try {
				session = HibernateUtil.getSessionFactory().openSession();
				transaction = session.beginTransaction();

				// create main audit record
				AuditRecord record = new AuditRecord();

				// populate the audit record
				{
					record.setEntityId(auditLog.getEntityId());
					record.setEntityName(auditLog.getEntityName());
					record.setOperation(auditLog.getOperation());
					record.setOperationDate(auditLog.getOperationTimestamp());
					record.setOperationTime(auditLog.getOperationTimestamp());
					record.setUserId(auditLog.getUserId());

					// data type
					TimelineConstants.AuditDataType dataType = auditLog.getDataType();

					if (dataType != null) {
						record.setDataType(dataType.getTypeId());
					}
				}

				// populate audit details
				{
					AuditRecordDetail detail = null;

					for (FieldAuditEntry log : logs) {
						detail = new AuditRecordDetail();

						// field data
						detail.setFieldName(log.getFieldName());
						detail.setOldValue(log.getOldValue());
						detail.setNewValue(log.getNewValue());
						detail.setFieldType(log.getFieldType());

						// add parent/child reference
						detail.setAuditRecord(record);
						record.addDetail(detail);
					}
				}

				// save the main audit
				session.saveOrUpdate(record);

				// flush a batch of inserts and release
				// memory:
				session.flush();
				session.clear();

				// commit the transaction
				transaction.commit();

			} catch (HibernateException hibernateException) {

				if (transaction != null) {
					transaction.rollback();
				}

				hibernateException.printStackTrace();

				// throw exception in case audit is not saved
				// prevents any entry to be saved without relevant audit
				throw hibernateException;

			} finally {
				// close the session
				if (session != null) {
					session.close();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.EmptyInterceptor#onDelete(java.lang.Object,
	 * java.io.Serializable, java.lang.Object[], java.lang.String[],
	 * org.hibernate.type.Type[])
	 */
	@Override
	public void onDelete(Object entity, Serializable id, Object[] currentState, String[] propertyNames, Type[] types) {
		long time = System.nanoTime();

		if (entity instanceof AbstractModel) {

			AuditLog auditLog = getChanges(entity, id, currentState, new Object[currentState.length], propertyNames,
					types, TimelineConstants.EntityOperation.DELETE);
			saveAudits(auditLog);
		}

		TextHelper.logMessage("Audit Delete Time", time);
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.EmptyInterceptor#onSave(java.lang.Object,
	 * java.io.Serializable, java.lang.Object[], java.lang.String[],
	 * org.hibernate.type.Type[])
	 */
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] currentState, String[] propertyNames, Type[] types) {
		long time = System.nanoTime();
		boolean changed = false;

		if (entity instanceof AbstractModel) {

			// set to true as auto-population happens
			changed = true;

			AuditLog auditLog = getChanges(entity, id, currentState, new Object[currentState.length], propertyNames,
					types, TimelineConstants.EntityOperation.CREATE);
			saveAudits(auditLog);

		}

		TextHelper.logMessage("Audit Save Time", time);

		return changed;
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.EmptyInterceptor#onFlushDirty(java.lang.Object,
	 * java.io.Serializable, java.lang.Object[], java.lang.Object[],
	 * java.lang.String[], org.hibernate.type.Type[])
	 */
	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {

		long time = System.nanoTime();
		boolean changed = false;

		if (entity instanceof AbstractModel) {

			// set to true as auto-population happens
			changed = true;

			AuditLog auditLog = getChanges(entity, id, currentState, previousState, propertyNames, types,
					TimelineConstants.EntityOperation.UPDATE);
			saveAudits(auditLog);

		}

		TextHelper.logMessage("Audit Update Time", time);

		return changed;
	}
}
