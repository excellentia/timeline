package org.ag.timeline.presentation.export;

/**
 * Handles Ms-Excel export.
 * 
 * @author Abhishek Gaurav
 */
public class ExcelManager {

	/**
	 * Singleton instance
	 */
	private static final ExcelManager INSTANCE = new ExcelManager();

	/**
	 * Constructor.
	 */
	private ExcelManager() {
		// no one calls me
	}

	public static final ExcelManager getInstance() {
		return INSTANCE;
	}

}
