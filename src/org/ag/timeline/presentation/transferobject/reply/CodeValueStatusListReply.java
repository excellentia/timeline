/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

import java.util.ArrayList;
import java.util.List;

import org.ag.timeline.presentation.transferobject.common.CodeValue;
import org.ag.timeline.presentation.transferobject.common.CodeValueStatus;

/**
 * Reply containing List of {@link CodeValue} objects.
 * 
 * @author Abhishek Gaurav
 */
public class CodeValueStatusListReply extends BusinessReply {

	private List<CodeValueStatus> codeValueStatusList = null;

	/**
	 * Constructor.
	 */
	public CodeValueStatusListReply() {
		this.codeValueStatusList = new ArrayList<CodeValueStatus>();
	}

	/**
	 * Getter for codeValueStatusList.
	 * 
	 * @return the codeValueStatusList.
	 */
	public List<CodeValueStatus> getCodeValueStatusList() {
		return this.codeValueStatusList;
	}

	/**
	 * Setter for codeValueStatusList.
	 * 
	 * @param codeValueStatusList the codeValueStatusList to set.
	 */
	public void setCodeValueStatusList(List<CodeValueStatus> codeValueStatus) {
		this.codeValueStatusList = codeValueStatus;
	}

	/**
	 * Adds a {@link CodeValue} object to the underlying list.
	 * 
	 * @param codeValue Object to be added.
	 */
	public void addCodeValueStatus(CodeValueStatus codeValueStatus) {
		this.codeValueStatusList.add(codeValueStatus);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CodeValueStatus [codeValueStatusList=");
		builder.append(codeValueStatusList);
		builder.append("]");
		return builder.toString();
	}

}