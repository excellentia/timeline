/**
 * 
 */
package org.ag.timeline.business.model;

import java.math.BigDecimal;

import org.ag.timeline.common.TextHelper;
import org.ag.timeline.common.TimelineConstants;

/**
 * Contains details related to user's time logged.
 * 
 * @author Abhishek Gaurav
 */
public class TimeData extends AbstractModel {

	private User user = null;

	private Project project = null;

	private Activity activity = null;

	private Week week = null;

	private BigDecimal data_weekday_1 = null;

	private BigDecimal data_weekday_2 = null;

	private BigDecimal data_weekday_3 = null;

	private BigDecimal data_weekday_4 = null;

	private BigDecimal data_weekday_5 = null;

	private BigDecimal data_weekday_6 = null;

	private BigDecimal data_weekday_7 = null;

	private Task task = null;

	/**
	 * Getter for user.
	 * 
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setter for user.
	 * 
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Getter for project.
	 * 
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * Setter for project.
	 * 
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * Getter for activity.
	 * 
	 * @return the activity
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * Setter for activity.
	 * 
	 * @param activity the activity to set
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	/**
	 * Getter for week.
	 * 
	 * @return the week
	 */
	public Week getWeek() {
		return week;
	}

	/**
	 * Setter for week.
	 * 
	 * @param week the week to set
	 */
	public void setWeek(Week week) {
		this.week = week;
	}

	/**
	 * Getter for data_weekday_1.
	 * 
	 * @return the data_weekday_1
	 */
	public BigDecimal getData_weekday_1() {
		return data_weekday_1;
	}

	/**
	 * Setter for data_weekday_1.
	 * 
	 * @param data_weekday_1 the data_weekday_1 to set
	 */
	public void setData_weekday_1(BigDecimal data_weekday_1) {
		this.data_weekday_1 = data_weekday_1;
	}

	/**
	 * Getter for data_weekday_2.
	 * 
	 * @return the data_weekday_2
	 */
	public BigDecimal getData_weekday_2() {
		return data_weekday_2;
	}

	/**
	 * Setter for data_weekday_2.
	 * 
	 * @param data_weekday_2 the data_weekday_2 to set
	 */
	public void setData_weekday_2(BigDecimal data_weekday_2) {
		this.data_weekday_2 = data_weekday_2;
	}

	/**
	 * Getter for data_weekday_3.
	 * 
	 * @return the data_weekday_3
	 */
	public BigDecimal getData_weekday_3() {
		return data_weekday_3;
	}

	/**
	 * Setter for data_weekday_3.
	 * 
	 * @param data_weekday_3 the data_weekday_3 to set
	 */
	public void setData_weekday_3(BigDecimal data_weekday_3) {
		this.data_weekday_3 = data_weekday_3;
	}

	/**
	 * Getter for data_weekday_4.
	 * 
	 * @return the data_weekday_4
	 */
	public BigDecimal getData_weekday_4() {
		return data_weekday_4;
	}

	/**
	 * Setter for data_weekday_4.
	 * 
	 * @param data_weekday_4 the data_weekday_4 to set
	 */
	public void setData_weekday_4(BigDecimal data_weekday_4) {
		this.data_weekday_4 = data_weekday_4;
	}

	/**
	 * Getter for data_weekday_5.
	 * 
	 * @return the data_weekday_5
	 */
	public BigDecimal getData_weekday_5() {
		return data_weekday_5;
	}

	/**
	 * Setter for data_weekday_5.
	 * 
	 * @param data_weekday_5 the data_weekday_5 to set
	 */
	public void setData_weekday_5(BigDecimal data_weekday_5) {
		this.data_weekday_5 = data_weekday_5;
	}

	/**
	 * Getter for data_weekday_6.
	 * 
	 * @return the data_weekday_6
	 */
	public BigDecimal getData_weekday_6() {
		return data_weekday_6;
	}

	/**
	 * Setter for data_weekday_6.
	 * 
	 * @param data_weekday_6 the data_weekday_6 to set
	 */
	public void setData_weekday_6(BigDecimal data_weekday_6) {
		this.data_weekday_6 = data_weekday_6;
	}

	/**
	 * Getter for data_weekday_7.
	 * 
	 * @return the data_weekday_7
	 */
	public BigDecimal getData_weekday_7() {
		return data_weekday_7;
	}

	/**
	 * Setter for data_weekday_7.
	 * 
	 * @param data_weekday_7 the data_weekday_7 to set
	 */
	public void setData_weekday_7(BigDecimal data_weekday_7) {
		this.data_weekday_7 = data_weekday_7;
	}

	/**
	 * Getter for task.
	 * 
	 * @return the task.
	 */
	public Task getTask() {
		return this.task;
	}

	/**
	 * Setter for task.
	 * 
	 * @param task the task to set.
	 */
	public void setTask(Task task) {
		this.task = task;
	}
	
	/**
	 * Business method. Checks if this time data was created as Proxy.
	 * 
	 * @return true is proxy data, false otherwise.
	 */
	public boolean isProxyData() {

		boolean proxy = false;

		if ((this.getUser() != null) && (this.getUser().getId() != this.getCreateUserId())) {
			proxy = true;
		}

		return proxy;

	}

	/**
	 * Business method. Returns the sum of all the week day time entries for
	 * this object.
	 * 
	 * @return {@link BigDecimal} total sum.
	 */
	public BigDecimal calculateTotalTime() {
		BigDecimal total = new BigDecimal(0);
		total = total.add(data_weekday_1).add(data_weekday_2).add(data_weekday_3).add(data_weekday_4)
				.add(data_weekday_5).add(data_weekday_6).add(data_weekday_7);
		return total;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TimeData [id=");
		builder.append(super.getId());
		builder.append(", user=");
		builder.append(user);
		builder.append(", project=");
		builder.append(project);
		builder.append(", activity=");
		builder.append(activity);
		builder.append(", week=");
		builder.append(week);
		builder.append(", data_weekday_1=");
		builder.append(data_weekday_1);
		builder.append(", data_weekday_2=");
		builder.append(data_weekday_2);
		builder.append(", data_weekday_3=");
		builder.append(data_weekday_3);
		builder.append(", data_weekday_4=");
		builder.append(data_weekday_4);
		builder.append(", data_weekday_5=");
		builder.append(data_weekday_5);
		builder.append(", data_weekday_6=");
		builder.append(data_weekday_6);
		builder.append(", data_weekday_7=");
		builder.append(data_weekday_7);
		builder.append(", task=");
		builder.append(task);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String getDescription() {
		StringBuilder builder = new StringBuilder(this.getProject().getName()).append(TimelineConstants.COMMA);
		builder.append(this.getActivity().getName()).append(TimelineConstants.COMMA);
		builder.append(TextHelper.getDisplayWeek(this.getWeek().getStartDate(), this.getWeek().getEndDate()));
		return builder.toString();
	}

}
