package org.ag.timeline.presentation.export;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.ag.timeline.common.TextHelper;
import org.ag.timeline.presentation.transferobject.reply.TimeDataReply;
import org.ag.timeline.presentation.transferobject.reply.TimeDataRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * Handles Ms-Excel export.
 * 
 * @author Abhishek Gaurav
 */
public class ExcelManager {

	private HSSFCell createCell(HSSFRow row, int cellIndex, String value) {
		HSSFCell cell = row.createCell(cellIndex);
		cell.setCellValue(value);
		return cell;
	}

	private HSSFCell createCell(HSSFRow row, int cellIndex, double value) {
		HSSFCell cell = row.createCell(cellIndex);
		cell.setCellValue(value);
		return cell;
	}

	private HSSFCell createCell(HSSFRow row, int cellIndex, String value, CellStyle style) {
		HSSFCell cell = createCell(row, cellIndex, value);
		cell.setCellStyle(style);
		return cell;
	}

	private HSSFCell createCell(HSSFRow row, int cellIndex, double value, CellStyle style) {
		HSSFCell cell = createCell(row, cellIndex, value);
		cell.setCellStyle(style);
		return cell;
	}

	private HSSFRow createRow(HSSFSheet sheet, int rowIndex) {
		HSSFRow row = sheet.createRow(rowIndex);
		return row;
	}

	private CellStyle getBorderStyle(HSSFWorkbook workBook) {

		final short blackIndex = IndexedColors.BLACK.getIndex();
		CellStyle style = workBook.createCellStyle();

		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);

		style.setTopBorderColor(blackIndex);
		style.setRightBorderColor(blackIndex);
		style.setBottomBorderColor(blackIndex);
		style.setLeftBorderColor(blackIndex);

		return style;
	}

	private CellStyle getHeaderStyle(HSSFWorkbook workBook) {

		CellStyle style = this.getBorderStyle(workBook);
		Font font = getHeaderFont(workBook);
		style.setFont(font);

		return style;
	}

	private Font getHeaderFont(HSSFWorkbook workBook) {
		Font font = workBook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Arial");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		return font;

	}

	public InputStream createTimeEntryReport(TimeDataReply reply) throws IOException {

		HSSFWorkbook workBook = new HSSFWorkbook();
		final CellStyle borderStyle = getBorderStyle(workBook);
		final CellStyle headerStyle = getHeaderStyle(workBook);

		HSSFSheet sheet = workBook.createSheet("Time Entries");

		int rowIndex = 0;
		int colIndex = 0;

		// Header row
		HSSFRow row = createRow(sheet, rowIndex++);
		createCell(row, colIndex++, "Report Date", headerStyle);
		createCell(row, colIndex++, TextHelper.getDateAsString(new Date()), borderStyle);

		// blank row
		row = createRow(sheet, rowIndex++);

		if ((reply != null) && (reply.isEntryPresent())) {

			// column headers
			row = createRow(sheet, rowIndex++);

			// reset colIndex
			colIndex = 0;

			createCell(row, colIndex++, "User Name", headerStyle);
			createCell(row, colIndex++, "Project", headerStyle);
			createCell(row, colIndex++, "Activity", headerStyle);
			createCell(row, colIndex++, "Lead", headerStyle);

			createCell(row, colIndex++, "Week", headerStyle);

			createCell(row, colIndex++, "Mon", headerStyle);
			createCell(row, colIndex++, "Tue", headerStyle);
			createCell(row, colIndex++, "Wed", headerStyle);
			createCell(row, colIndex++, "Thu", headerStyle);
			createCell(row, colIndex++, "Fri", headerStyle);
			createCell(row, colIndex++, "Sat", headerStyle);
			createCell(row, colIndex++, "Sun", headerStyle);

			createCell(row, colIndex++, "Weekly Sum", headerStyle);

			List<Long> weekIdList = reply.getWeekIds();

			for (long weekDbId : weekIdList) {

				for (TimeDataRow dataRow : reply.getEntriesForWeek(weekDbId)) {

					// create a new row
					row = createRow(sheet, rowIndex++);

					// reset colIndex
					colIndex = 0;

					// create project related info
					createCell(row, colIndex++, dataRow.getUserFullName(), borderStyle);
					createCell(row, colIndex++, dataRow.getProjectName(), borderStyle);
					createCell(row, colIndex++, dataRow.getActivityName(), borderStyle);
					createCell(row, colIndex++, dataRow.getLeadName(), borderStyle);

					// create week label
					createCell(row, colIndex++, reply.getWeekLabel(weekDbId), borderStyle);

					// create time data
					createCell(row, colIndex++, dataRow.getDay_1_time(), borderStyle);
					createCell(row, colIndex++, dataRow.getDay_2_time(), borderStyle);
					createCell(row, colIndex++, dataRow.getDay_3_time(), borderStyle);
					createCell(row, colIndex++, dataRow.getDay_4_time(), borderStyle);
					createCell(row, colIndex++, dataRow.getDay_5_time(), borderStyle);
					createCell(row, colIndex++, dataRow.getDay_6_time(), borderStyle);
					createCell(row, colIndex++, dataRow.getDay_7_time(), borderStyle);

					// create weekly sum
					createCell(row, colIndex++, dataRow.getWeeklySum(), borderStyle);

				}
			}
		} else {

			// Message row
			row = createRow(sheet, rowIndex++);

			// reset colIndex
			colIndex = 0;
			createCell(row, colIndex++, "No Data Found", borderStyle);
		}

		ByteArrayOutputStream boas = new ByteArrayOutputStream();
		workBook.write(boas);

		return new ByteArrayInputStream(boas.toByteArray());
	}
}
