/**
 * 
 */
package org.ag.timeline.business.model;


/**
 * Denotes System Settings.
 * 
 * @author Abhishek Gaurav
 */
public class SystemSettings {

	private long id = 0;

	private String name = null;

	private String type = null;

	private String value = null;

	public static enum Type {
		IGNORE_EXTRA_HOURS("BOOLEAN"), START_DATE("DATE"), END_DATE("DATE");

		private final String type;

		private Type(final String type) {
			this.type = type;
		}

		public final String getText() {
			return this.toString();
		}

		public static final Type getType(final String typeStr) {
			Type type = null;

			for (Type t : Type.values()) {
				if (t.type.equalsIgnoreCase(typeStr)) {
					type = t;
					break;
				}
			}

			return type;
		}
	}

	/**
	 * Getter for id.
	 * 
	 * @return the id.
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * Setter for id.
	 * 
	 * @param id the id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}

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

	/**
	 * Getter for type.
	 * 
	 * @return the type.
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Setter for type.
	 * 
	 * @param type the type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Getter for value.
	 * 
	 * @return the value.
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Setter for value.
	 * 
	 * @param value the value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SystemSettings [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

}
