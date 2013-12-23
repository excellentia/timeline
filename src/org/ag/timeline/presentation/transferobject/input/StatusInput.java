/**
 * 
 */
package org.ag.timeline.presentation.transferobject.input;

import org.ag.timeline.common.TimelineConstants;
import org.ag.timeline.common.TimelineConstants.StatusEntity;

/**
 * Wrapper for Status input data.
 * 
 * @author Abhishek Gaurav
 */
public class StatusInput extends AbstractTimelineInput {

	private long entityId = 0;

	private TimelineConstants.StatusEntity entity = null;

	private boolean active = true;

	/**
	 * Constructor.
	 * 
	 * @param entityId Database id.
	 * @param entity {@link StatusEntity}
	 * @param active true or false
	 */
	public StatusInput(long entityId, StatusEntity entity, boolean active) {
		this.entityId = entityId;
		this.entity = entity;
		this.active = active;
	}

	/**
	 * Getter for entityId.
	 * 
	 * @return the entityId.
	 */
	public long getEntityId() {
		return this.entityId;
	}

	/**
	 * Setter for entityId.
	 * 
	 * @param entityId the entityId to set.
	 */
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	/**
	 * Getter for entity.
	 * 
	 * @return the entity.
	 */
	public TimelineConstants.StatusEntity getEntity() {
		return this.entity;
	}

	/**
	 * Setter for entity.
	 * 
	 * @param entity the entity to set.
	 */
	public void setEntity(TimelineConstants.StatusEntity entity) {
		this.entity = entity;
	}

	/**
	 * Getter for active.
	 * 
	 * @return the active.
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * Setter for active.
	 * 
	 * @param active the active to set.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StatusInput [entityId=");
		builder.append(entityId);
		builder.append(", entity=");
		builder.append(entity);
		builder.append(", active=");
		builder.append(active);
		builder.append("]");
		return builder.toString();
	}

}
