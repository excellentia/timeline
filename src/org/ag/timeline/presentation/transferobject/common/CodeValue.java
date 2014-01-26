/**
 * 
 */
package org.ag.timeline.presentation.transferobject.common;

/**
 * Code value wrapper.
 * 
 * @author Abhishek Gaurav
 */
public class CodeValue {

	private long code = 0;

	private String value = null;

	public CodeValue() {
	}

	public CodeValue(long code, String value) {
		super();
		this.code = code;
		this.value = value;
	}

	public CodeValue(long code) {
		super();
		this.code = code;
	}

	public CodeValue(String value) {
		super();
		this.value = value;
	}

	/**
	 * Getter for code.
	 * 
	 * @return the code
	 */
	public long getCode() {
		return code;
	}

	/**
	 * Setter for code.
	 * 
	 * @param code the code to set
	 */
	public void setCode(long code) {
		this.code = code;
	}

	/**
	 * Getter for value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setter for value.
	 * 
	 * @param value the value to set
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
		builder.append("CodeValue [code=");
		builder.append(code);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (code ^ (code >>> 32));
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CodeValue)) {
			return false;
		}
		CodeValue other = (CodeValue) obj;
		if (code != other.code) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

}
