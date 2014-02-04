package org.ag.timeline.common;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Helper providing text manipulations.
 * 
 * @author Abhishek Gaurav*
 */
public class TextHelper {

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

	private static final DateFormat DISPLAY_FORMAT = new SimpleDateFormat("E : dd-MMM");

	private static final DateFormat WEEK_FORMAT = new SimpleDateFormat("dd MMM");

	public static final DateFormat WEEK_DAY_FORMAT = new SimpleDateFormat("dd MMM yyyy");

	// private static final DateFormat SERVER_FORMAT = new
	// SimpleDateFormat("dd MMM yyyy");
	//
	private static final DateFormat AUDIT_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

	//
	// private static final long MILLISEC_PER_DAY = 24 * 60 * 60 * 1000;

	private static final int SCALE = 2;

	private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

	private static final boolean LOGGING_ON = true;

	private TextHelper() {
		// no instantiations allowed
	}

	/**
	 * Trims incoming string, returns null in case the value is "" empty.
	 * 
	 * @param str String to be trimmed.
	 * @return trimmed string or null.
	 */
	public static String trimToNull(final String str) {
		String retVal = null;

		if (str != null) {
			retVal = str.trim();

			if (retVal.equalsIgnoreCase(TimelineConstants.EMPTY)) {
				retVal = null;
			}
		}

		return retVal;
	}

	/**
	 * Trims incoming string, returns "" in case the value is null.
	 * 
	 * @param str String to be trimmed.
	 * @return trimmed string or null.
	 */
	public static String trimToEmpty(final String str) {
		String retVal = TextHelper.trimToNull(str);

		if (str == null) {
			retVal = TimelineConstants.EMPTY;
		}

		return retVal;
	}

	public static int getIntValue(String str) {
		int retVal = 0;

		String val = trimToNull(str);
		if (val != null) {
			try {
				retVal = Integer.parseInt(val);
			} catch (NumberFormatException e) {
				// we'll user default value
				retVal = 0;
			}

		}

		return retVal;
	}

	// public static Integer getIntegerValue(String str) {
	// Integer retVal = null;
	// String val = trimToNull(str);
	// if (val != null) {
	// try {
	// retVal = Integer.parseInt(val);
	// } catch (NumberFormatException e) {
	// // we'll user default value
	// }
	// }
	//
	// return retVal;
	// }

	public static long getLongValue(String str) {
		long retVal = 0;

		String val = trimToNull(str);
		if (val != null) {
			try {
				retVal = Long.parseLong(val);
			} catch (NumberFormatException e) {
				// we'll user default value
				retVal = 0;
			}

		}

		return retVal;
	}

	public static double getDoubleValue(String str) {
		double retVal = 0.00;

		String val = trimToNull(str);
		if (val != null) {
			try {
				retVal = Double.parseDouble(val);
			} catch (NumberFormatException e) {
				// we'll user default value
				retVal = 0.00;
			}
		}

		return retVal;
	}

	public static double getScaledDouble(BigDecimal input) {
		double retVal = 0;

		if (input != null) {
			retVal = input.setScale(SCALE, ROUNDING_MODE).doubleValue();
		}

		return retVal;
	}

	public static BigDecimal getScaledBigDecimal(double input) {
		return new BigDecimal(input).setScale(SCALE, ROUNDING_MODE);
	}

	public static BigDecimal getScaledBigDecimal(String text) {
		double num = 0;

		try {
			num = Double.parseDouble(TextHelper.trimToNull(text));
		} catch (Exception e) {
			num = 0;
		}

		return new BigDecimal(num).setScale(SCALE, ROUNDING_MODE);
	}

	public static BigDecimal getScaledBigDecimal(BigDecimal input) {
		BigDecimal retVal = null;

		if (input != null) {
			retVal = input.setScale(SCALE, ROUNDING_MODE);
		}

		return retVal;
	}

	public static boolean getBooleanValue(String str) {
		boolean retVal = Boolean.valueOf(trimToNull(str));
		return retVal;
	}

	public static Date getValidDate(String str) {
		Date date = null;
		String data = trimToNull(str);

		if (data != null) {
			try {
				date = DATE_FORMAT.parse(data);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return date;
	}

	public static Date getValidDate(String str, DateFormat dateFormat) {
		Date date = null;
		String data = trimToNull(str);

		if ((data != null) && (dateFormat != null)) {
			try {
				date = dateFormat.parse(data);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return date;
	}

	//
	// // public static String getCodeText(AbstractModel codeValue) {
	// // String retVal = TimelineConstants.EMPTY;
	// // if (codeValue != null) {
	// // retVal = TextHelper.trimToEmpty(codeValue.getText());
	// // }
	// //
	// // return retVal;
	// // }
	//
	public static String getDateAsString(Date date) {
		String str = null;
		if (date != null) {
			str = DATE_FORMAT.format(date);
		}

		return str;
	}

	public static String getDateAsString(Date date, DateFormat format) {
		String str = null;
		if ((date != null) && (format != null)) {
			str = format.format(date);
		}

		return str;
	}

	//
	// public static String getDateAsString() {
	// return getDateAsString(new Date());
	// }
	//
	// public static String getServerDate() {
	// return SERVER_FORMAT.format(new Date());
	// }
	//
	public static String getAuditTimestamp(Date date) {
		String value = null;

		if (date != null) {
			value = AUDIT_FORMAT.format(date);
		}
		return value;
	}

	public static String getTimeAsString(Date date) {
		String value = null;

		if (date != null) {
			value = TIME_FORMAT.format(date);
		}
		return value;
	}

	public static Date getAuditDayStartTimestamp(Date date) {
		Calendar calendar = Calendar.getInstance();

		if (date == null) {
			date = new Date();
		}

		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}

	public static Date getAuditDayEndTimestamp(Date date) {
		Calendar calendar = Calendar.getInstance();

		if (date == null) {
			date = new Date();
		}

		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		return calendar.getTime();
	}

	//
	// public static long getMilliseconds(final Date date) {
	// long retVal = 0;
	//
	// if (date != null) {
	// Calendar calendar = Calendar.getInstance();
	// calendar.setTime(date);
	// // ignore time
	//
	// calendar.set(Calendar.HOUR_OF_DAY, 0);
	// calendar.set(Calendar.MINUTE, 0);
	// calendar.set(Calendar.SECOND, 0);
	// retVal = calendar.getTimeInMillis();
	// }
	//
	// return retVal;
	// }
	//
	// public static Date getDate(final long millisecs) {
	// Calendar calendar = Calendar.getInstance();
	// calendar.setTimeInMillis(millisecs);
	// return calendar.getTime();
	// }
	//
	// public static long getPeriodInDays(final Date olderDate, final Date
	// newerDate) {
	// long retVal = 0;
	//
	// if ((olderDate != null) && (newerDate != null)) {
	// long one = getMilliseconds(olderDate);
	// long two = getMilliseconds(newerDate);
	//
	// retVal = (two - one) / MILLISEC_PER_DAY;
	// }
	//
	// return retVal;
	// }
	//

	public static Date getDateAfter(Date date, int increment) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.add(Calendar.DAY_OF_YEAR, increment);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			calendar.add(Calendar.DAY_OF_YEAR, -1);
		}

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		return calendar.getTime();
	}

	public static Date getLastDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			calendar.add(Calendar.DAY_OF_YEAR, -1);
		}

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.add(Calendar.DATE, 6);

		return calendar.getTime();
	}

	public static long getWeekNumber(Date date) {
		long retVal = 0;

		if (date != null) {

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				calendar.add(Calendar.DAY_OF_YEAR, -1);
			}

			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);

			return calendar.get(Calendar.WEEK_OF_YEAR);

		}

		return retVal;

	}

	public static long getYear(Date date) {
		long year = 0;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			year = calendar.get(Calendar.YEAR);
		}

		return year;
	}

	public static long getYearForWeekDay(Date date) {
		long year = 0;

		if (date != null) {

			long weekNum = TextHelper.getWeekNumber(date);

			if (weekNum > 1) {
				year = TextHelper.getYear(TextHelper.getFirstDayOfWeek(date));
			} else {
				// Fix for setting the year correctly as
				// new year for 1st week of year
				year = TextHelper.getYear(TextHelper.getLastDayOfWeek(date));
			}
		}

		return year;
	}

	public static final void logMessage(String msg) {
		if (LOGGING_ON) {
			System.out.println("[Timeline] " + msg);
		}
	}

	public static final void logMessage(String msg, long time) {
		if (LOGGING_ON) {
			System.out.println("[Timeline] " + msg + " > Time taken : " + ((System.nanoTime() - time) / 1000000));
		}
	}

	public static String getDisplayDate(final Date date, final int offsetDays) {
		String val = null;

		if (date != null) {
			Date offsetDate = date;

			if (offsetDays > 0) {
				offsetDate = getDateAfter(date, offsetDays);
			}

			val = DISPLAY_FORMAT.format(offsetDate);
		}

		return val;
	}

	public static String getDisplayWeek(final Date startDate, final Date endDate) {
		String val = null;

		if ((startDate != null) && (endDate != null)) {

			StringBuilder builder = new StringBuilder(WEEK_FORMAT.format(TextHelper.getFirstDayOfWeek(startDate)));
			builder.append(TimelineConstants.WEEK_SEPARATOR);
			builder.append(WEEK_FORMAT.format(TextHelper.getLastDayOfWeek(endDate)));
			builder.append(TimelineConstants.COMMA);
			builder.append(TimelineConstants.SPACE);
			builder.append(TextHelper.getYearForWeekDay(endDate));

			val = builder.toString();
		}

		return val;
	}

	public static String getDisplayWeek(final Date startDate, final Date endDate, final String defaultLabel) {

		String val = TextHelper.trimToNull(TextHelper.getDisplayWeek(startDate, endDate));

		if (val == null) {
			val = defaultLabel;
		}

		return val;
	}

	public static String getDisplayWeekDay(final Date date) {
		String val = null;

		if (date != null) {
			val = WEEK_DAY_FORMAT.format(date);
		}

		return val;
	}

	public static List<String> getDisplayWeekDayList(final Date startDate) {

		List<String> list = null;

		if (startDate != null) {

			list = new ArrayList<String>();

			for (int i = 0; i < 7; i++) {
				list.add(TextHelper.getDisplayWeekDay(TextHelper.getDateAfter(startDate, i)));
			}

		}

		return list;
	}

	public static void main(String[] args) {
		{
			Date date = getValidDate("01.07.2012");
			System.out.println("Current Date : " + date);
			System.out.println("First Day Of Week : " + getFirstDayOfWeek(date));
			System.out.println("Last Day Of Week : " + getLastDayOfWeek(date));
			System.out.println("Week Number : " + getWeekNumber(date));
			System.out.println("Year : " + getYear(date));
			System.out.println("Display Formatted : " + DISPLAY_FORMAT.format(date));
			System.out.println("Week Formatted : " + WEEK_FORMAT.format(date));
		}

		System.out.println("===============================");

		{
			final Date currentDate = getValidDate("31.12.2012");
			System.out.println("Current Date : " + currentDate);
			System.out.println("Week Number : " + getWeekNumber(currentDate));
			System.out.println("Year For Week Day : " + getYearForWeekDay(currentDate));
		}

		System.out.println("===============================");

		{
			final Date currentDate = getValidDate("9.12.2013");
			System.out.println("Current Date : " + currentDate);
			{
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(currentDate);
				// calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				calendar.add(Calendar.DAY_OF_YEAR, 6);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);

				System.out.println("Date (Week Day is not set) : " + calendar.getTime());
			}

			System.out.println("Date (Week Day is set) : " + TextHelper.getDateAfter(currentDate, 6));

			System.out.println(TextHelper.getDisplayWeek(currentDate, TextHelper.getDateAfter(currentDate, 6)));
		}

		System.out.println("===============================");

		{
			Date date = new Date();
			System.out.println("Day Start : " + getAuditTimestamp(getAuditDayStartTimestamp(date)));
			System.out.println("Day End : " + getAuditTimestamp(getAuditDayEndTimestamp(date)));

			System.out.println("Week Start Date :" + getFirstDayOfWeek(date));
			System.out.println("Week End Date :" + getLastDayOfWeek(date));
		}

	}
}