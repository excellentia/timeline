/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

import java.util.ArrayList;
import java.util.List;

import org.ag.timeline.presentation.transferobject.common.CodeValue;

/**
 * Reply containing List of {@link CodeValue} objects.
 * 
 * @author Abhishek Gaurav
 */
public class CodeValueListReply extends BusinessReply {

	/**
	 * Serial Version U-Id.
	 */
	private static final long serialVersionUID = 268828136698069405L;

	private List<CodeValue> codeValueList = null;

	/**
	 * Constructor.
	 */
	public CodeValueListReply() {
		this.codeValueList = new ArrayList<CodeValue>();
	}

	/**
	 * Getter for codeValueList.
	 * 
	 * @return the codeValueList.
	 */
	public List<CodeValue> getCodeValueList() {
		return this.codeValueList;
	}

	/**
	 * Setter for codeValueList.
	 * 
	 * @param codeValueList the codeValueList to set.
	 */
	public void setCodeValueList(List<CodeValue> codeValueList) {
		this.codeValueList = codeValueList;
	}

	/**
	 * Adds a {@link CodeValue} object to the underlying list.
	 * 
	 * @param codeValue Object to be added.
	 */
	public void addCodeValue(CodeValue codeValue) {
		this.codeValueList.add(codeValue);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CodeValueListReply [codeValueList=");
		builder.append(codeValueList);
		builder.append("]");
		return builder.toString();
	}

}