/**
 * 
 */
package org.ag.timeline.presentation.action;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ag.timeline.common.TextHelper;
import org.ag.timeline.presentation.export.ExcelManager;
import org.ag.timeline.presentation.transferobject.reply.TimeDataReply;
import org.ag.timeline.presentation.transferobject.search.TimeDataSearchParameters;

/**
 * Handles export functionality.
 * 
 * @author Abhishek Gaurav
 */
public class ExportAction extends SecureBaseAction {

	private static final DateFormat EXPORT_FORMAT = new SimpleDateFormat("dd.MM.yyyy_HH-mm-ss");

	private long exportUserDbId = 0;

	private String exportStartDate = null;

	private String exportEndDate = null;

	private long exportProjectId = 0;

	private long exportActivityId = 0;

	private String command = null;

	private InputStream excelStream;

	private String fileName = null;

	/**
	 * Getter for exportUserDbId.
	 * 
	 * @return the exportUserDbId.
	 */
	public long getExportUserDbId() {
		return this.exportUserDbId;
	}

	/**
	 * Setter for exportUserDbId.
	 * 
	 * @param exportUserDbId the exportUserDbId to set.
	 */
	public void setExportUserDbId(long exportUserDbId) {
		this.exportUserDbId = exportUserDbId;
	}

	/**
	 * Getter for exportStartDate.
	 * 
	 * @return the exportStartDate.
	 */
	public String getExportStartDate() {
		return this.exportStartDate;
	}

	/**
	 * Setter for exportStartDate.
	 * 
	 * @param exportStartDate the exportStartDate to set.
	 */
	public void setExportStartDate(String exportStartDate) {
		this.exportStartDate = exportStartDate;
	}

	/**
	 * Getter for exportEndDate.
	 * 
	 * @return the exportEndDate.
	 */
	public String getExportEndDate() {
		return this.exportEndDate;
	}

	/**
	 * Setter for exportEndDate.
	 * 
	 * @param exportEndDate the exportEndDate to set.
	 */
	public void setExportEndDate(String exportEndDate) {
		this.exportEndDate = exportEndDate;
	}

	/**
	 * Getter for exportProjectId.
	 * 
	 * @return the exportProjectId.
	 */
	public long getExportProjectId() {
		return this.exportProjectId;
	}

	/**
	 * Setter for exportProjectId.
	 * 
	 * @param exportProjectId the exportProjectId to set.
	 */
	public void setExportProjectId(long exportProjectId) {
		this.exportProjectId = exportProjectId;
	}

	/**
	 * Getter for exportActivityId.
	 * 
	 * @return the exportActivityId.
	 */
	public long getExportActivityId() {
		return this.exportActivityId;
	}

	/**
	 * Setter for exportActivityId.
	 * 
	 * @param exportActivityId the exportActivityId to set.
	 */
	public void setExportActivityId(long exportActivityId) {
		this.exportActivityId = exportActivityId;
	}

	/**
	 * Getter for command.
	 * 
	 * @return the command.
	 */
	public String getCommand() {
		return this.command;
	}

	/**
	 * Setter for command.
	 * 
	 * @param command the command to set.
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * Getter for excelStream.
	 * 
	 * @return the excelStream.
	 */
	public InputStream getExcelStream() {
		return this.excelStream;
	}

	/**
	 * Setter for excelStream.
	 * 
	 * @param excelStream the excelStream to set.
	 */
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	/**
	 * Getter for fileName.
	 * 
	 * @return the fileName.
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * Setter for fileName.
	 * 
	 * @param fileName the fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ag.timeline.presentation.action.SecureBaseAction#secureExecute()
	 */
	@Override
	public String secureExecute() throws Exception {

		String cmd = TextHelper.trimToNull(this.command);

		if (cmd != null) {

			if ("TIME_ENTRIES".equalsIgnoreCase(cmd)) {

				// Export Time Entries
				exportTimeEntries();
			}

		}

		return SUCCESS;
	}

	private void exportTimeEntries() throws Exception {

		TimeDataSearchParameters searchParameters = new TimeDataSearchParameters();

		Date startDate = TextHelper.getValidDate(this.exportStartDate);
		Date endDate = TextHelper.getValidDate(this.exportEndDate);

		if (startDate == null) {
			startDate = TextHelper.getFirstDayOfWeek(new Date());
		}

		if (endDate == null) {
			endDate = TextHelper.getFirstDayOfWeek(new Date());
		}

		// rows for current user, if not admin
		if (!super.isSessionUserAdmin()) {
			this.exportUserDbId = super.getSessionUserId();
		}

		searchParameters.setActivityid(this.exportActivityId);
		searchParameters.setStartDate(startDate);
		searchParameters.setEndDate(endDate);
		searchParameters.setProjectId(this.exportProjectId);
		searchParameters.setUserId(this.exportUserDbId);

		TimeDataReply reply = service.searchTimeData(searchParameters);

		ExcelManager manager = new ExcelManager();
		this.fileName = TextHelper.getDateAsString(new Date(), EXPORT_FORMAT) + ".xls";
		this.excelStream = manager.createTimeEntryReport(reply);

	}

}
