/**
 * 
 */
package org.ag.timeline.business.model;

import java.util.Date;

/**
 * Base class for all model objects.
 * 
 * @author Abhishek Gaurav
 */
public abstract class AbstractModel {

	private long id = 0;

	private Long createUserId = null;
	private Long modifyUserId = null;

	private Date createDate = null;
	private Date modifyDate = null;
	
	public abstract String getDescription();

	/**
	 * Getter for id.
	 * 
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for id.
	 * 
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for createUserId.
	 * 
	 * @return the createUserId
	 */
	public Long getCreateUserId() {
		return createUserId;
	}

	/**
	 * Setter for createUserId.
	 * 
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * Getter for modifyUserId.
	 * 
	 * @return the modifyUserId
	 */
	public Long getModifyUserId() {
		return modifyUserId;
	}

	/**
	 * Setter for modifyUserId.
	 * 
	 * @param modifyUserId the modifyUserId to set
	 */
	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	/**
	 * Getter for createDate.
	 * 
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Setter for createDate.
	 * 
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Getter for modifyDate.
	 * 
	 * @return the modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * Setter for modifyDate.
	 * 
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractModel [id=");
		builder.append(id);
		builder.append(", createUserId=");
		builder.append(createUserId);
		builder.append(", modifyUserId=");
		builder.append(modifyUserId);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", modifyDate=");
		builder.append(modifyDate);
		builder.append("]");
		return builder.toString();
	}

}