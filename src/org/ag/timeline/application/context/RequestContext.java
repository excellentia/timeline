package org.ag.timeline.application.context;


/**
 * Request specific context.
 * 
 * @author Abhishek Gaurav
 */
public class RequestContext {

	/**
	 * Contains the {@link TimelineContext} for current request.
	 */
	private static final ThreadLocal<TimelineContext> THREAD_LOCAL = new ThreadLocal<TimelineContext>();

	/**
	 * Getter for TimelineContext.
	 * 
	 * @return TimelineContext.
	 */
	public static final TimelineContext getTimelineContext() {
		return THREAD_LOCAL.get();
	}

	/**
	 * Setter for TimelineContext.
	 * 
	 * @param context to be set.
	 */
	public static final void setTimelineContext(TimelineContext context) {
		THREAD_LOCAL.set(context);
	}

	/**
	 * Cleans up the cached {@link TimelineContext} objcts.
	 */
	public static final void destroy() {
		THREAD_LOCAL.remove();
	}

}
