package org.ag.timeline.application.context;

/**
 * Timeline context.
 * 
 * @author Abhishek Gaurav
 */
public final class TimelineContext {

	private long userId = 0;

	/**
	 * Constructor.
	 * 
	 * @param contextUserId Unique database id for context user.
	 */
	public TimelineContext(long contextUserId) {
		this.userId = contextUserId;
	}

	/**
	 * Getter for contextUserId.
	 * 
	 * @return the contextUserId
	 */
	public long getContextUserId() {
		return userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TimelineContext [contextUserId=").append(userId).append("]");
		return builder.toString();
	}

}
