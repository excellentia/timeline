/**
 * 
 */
package org.ag.timeline.common;

import java.util.Comparator;

import org.ag.timeline.presentation.transferobject.common.CodeValue;

/**
 * Comparator for {@link CodeValue}.
 * 
 * @author Abhishek Gaurav
 */
public class CodeValueComparator implements Comparator<CodeValue> {

	public int compare(CodeValue one, CodeValue two) {
		int retVal = 0;

		if ((one != null) || (two != null)) {

			if (one == null) {
				retVal = 1;
			} else if (two == null) {
				retVal = -1;
			} else {
				retVal = one.getValue().compareTo(two.getValue());

				if (retVal == 0) {

					if (one.getCode() < two.getCode()) {
						retVal = -1;
					} else if (one.getCode() > two.getCode()) {
						retVal = 1;
					} else {
						retVal = 0;
					}
				}
			}
		}

		return retVal;
	}
}
