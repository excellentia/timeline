/**
 * 
 */
package org.ag.timeline.business.model;


/**
 * Denotes Stage available across project (System wide scope).
 * 
 * @author Abhishek Gaurav
 */
public class Stage extends AbstractModel {

	private String name = null;

	/**
	 * Getter for name.
	 * 
	 * @return the name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setter for name.
	 * 
	 * @param name the name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Stage [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String getDescription() {
		return this.getName();
	}

}
