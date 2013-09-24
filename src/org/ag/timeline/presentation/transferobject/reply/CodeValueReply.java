/**
 * 
 */
package org.ag.timeline.presentation.transferobject.reply;

import org.ag.timeline.presentation.transferobject.common.CodeValue;

/**
 * Reply containing {@link CodeValue} instance.
 * 
 * @author Abhishek Gaurav
 */
public class CodeValueReply extends BusinessReply {

	private CodeValue codeValue = null;

	/**
	 * Getter for codeValue.
	 * 
	 * @return the codeValue
	 */
	public CodeValue getCodeValue() {
		return codeValue;
	}

	/**
	 * Setter for codeValue.
	 * 
	 * @param codeValue the codeValue to set
	 */
	public void setCodeValue(CodeValue codeValue) {
		this.codeValue = codeValue;
	}
}