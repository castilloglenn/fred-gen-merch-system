package pos;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.Statistic;
import utils.Utility;

/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
public class Report {

	/**
	 * Singleton instance
	 */
	private static Report singletonInstance = null;
	
	public static int MONTHLY_REPORT = 0;
	public static int CURRENT_DAY_REPORT = 1;
	public static int PREVIOUS_DAY_REPORT = 2;
	
	// Paper formatting variables
	// Make sure that the margin is even number so both sides is equal
	// Width must be greater than the margin
	// Available space would be the value of WIDTH - MARGIN
	private final int WIDTH = 73; // 107
	private final int MARGIN = 4; 
	
	// Daily Report Table Column Sizes excluding the vertical characters
	private final int daily1stColumnWidth = 9;
	private final int daily2ndColumnWidth = 9;
	private final int daily3rdColumnWidth = 14;
	private final int daily4thColumnWidth = 15;
	private final int daily5thColumnWidth = 16;

	private final char SPACE_ENRYPTION_CHARACTER = '*';
	
	private final String HORIZONAL = "=";
	private final String VERTICAL = "|";
	
	private final String SPACE = " ";
	private final String BR = "\n";
	
    private String horizontalLine = "";
    private String tableHorizontalLine = "";
	
	private String defaultMarginPadding = "";

	private Database database;
	private Statistic statistic;
	private Utility utility;
	private Gallery gallery;
	private Logger logger;
	private Object[] user;
	
	private String filePathFormat = "./reports/business/%s.txt";
	private String dailyFilePrefix = "DAILY-REPORT-";
	private String monthlyFilePrefix = "MONTHLY-REPORT-";
	private String reportDate;
	
	private String businessName = Utility.BUSINESS_TITLE;
	private String businessAddress1 = Utility.BUSINESS_ADDRESS_1;
	private String businessAddress2 = Utility.BUSINESS_ADDRESS_2;
	private String dailySalesReportTitle = "Daily Sales Report";
	private String monthlySalesReportTitle = "Monthly Sales Report";
	
	private int totalTransactions = 0;
	private int totalProductSold = 0;
	private double grossSales = 0.0;
	
	private SimpleDateFormat dailyFileDateTimeFormat;
	private SimpleDateFormat monthlyFileDateTimeFormat;
	private SimpleDateFormat reportDateTimeFormat;
	
	private Calendar calendar;
	
	private Report(Object[] user) {
		database = Database.getInstance();
		statistic = Statistic.getInstance();
		utility = Utility.getInstance();
		gallery = Gallery.getInstance();
		logger = Logger.getInstance();
		this.user = user;
		
		dailyFileDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
		monthlyFileDateTimeFormat = new SimpleDateFormat("yyyy-MM");
		reportDateTimeFormat = new SimpleDateFormat("MM/dd/yyy");
		
		calendar = Calendar.getInstance();

		setupObjects();
		checkPreviousReports();
	}

	public static Report getInstance(Object[] user) {
		if (singletonInstance == null) {
			singletonInstance = new Report(user);
		}
		
		return singletonInstance;
	}
	
	public boolean generateDailyReport(int reportType) {
		String dailyReportFileName = generateFileName(reportType);
		File dailyFile = new File(String.format(filePathFormat, dailyReportFileName));

		calendar = Calendar.getInstance();
		if (reportType == PREVIOUS_DAY_REPORT) {
			calendar.add(Calendar.DAY_OF_YEAR, -1);
		}
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date dateDayStart = calendar.getTime();
		
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		Date dateDayEnd = calendar.getTime();
		
		// NOTE: This is sorted from AM-PM conveniently 
		Object[][] transactions = database.getTransactionsByRange(dateDayStart, dateDayEnd);
		ArrayList<String[]> hourlySales = parseDailyTransactions(reportType, transactions);
		calendar.setTime(dateDayEnd);
		
		if (hourlySales == null) {
			logger.addLog(Logger.LEVEL_2, 
				String.format("User %s tried to manually generate daily sales report without any transactions yet.", 
					user[0].toString()));
			
			gallery.showMessage(new String[] {"There are no sales recorded for the day."});
			return false;
		} else if (dailyFile.isFile()) {
			int confirmOverwrite = JOptionPane.showConfirmDialog(null, 
				"The sales report has already been generated, would you like to overwrite it?", 
				Utility.SYSTEM_TITLE, 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.INFORMATION_MESSAGE);
			if (confirmOverwrite != 0) {
				return false;
			}
		}
		
		String employeeID = Long.toString((long) user[0]);
		String employeeName = user[1].toString() + " " + user[2].toString() + " " + user[3].toString();
		String reportDate = reportDateTimeFormat.format(calendar.getTime());
		
		// Actual format of the document goes here
		// Headers
		StringBuilder dailyReport = new StringBuilder(horizontalLine + BR);
		dailyReport.append(center("") + BR);
		dailyReport.append(center(businessName) + BR);
		dailyReport.append(center(businessAddress1) + BR);
		dailyReport.append(center(businessAddress2) + BR);
		dailyReport.append(center(dailySalesReportTitle) + BR);
		dailyReport.append(center("") + BR);
		
		dailyReport.append(
			justify(
				encryptSpaces("Employee ID: " + employeeID) 
				+ SPACE 
				+ encryptSpaces("Date: " + reportDate)) + BR);
		
		dailyReport.append(leftAlign("Employee Name: " + employeeName) + BR);
		
		// Report Table
		dailyReport.append(tableHorizontalLine + BR);
		// Table Header
		// First Line
		dailyReport.append(
			center(
				VERTICAL + center("From", daily1stColumnWidth) + 
				VERTICAL + center("To", daily2ndColumnWidth) +
				VERTICAL + center("Total", daily3rdColumnWidth) +
				VERTICAL + center("Total", daily4thColumnWidth) +
				VERTICAL + center("Gross", daily5thColumnWidth) +
				VERTICAL
		) + BR);
		// Second Line
		dailyReport.append(
			center(
				VERTICAL + center("", daily1stColumnWidth) + 
				VERTICAL + center("", daily2ndColumnWidth) +
				VERTICAL + center("Transactions", daily3rdColumnWidth) +
				VERTICAL + center("Products Sold", daily4thColumnWidth) +
				VERTICAL + center("Sales", daily5thColumnWidth) +
				VERTICAL
		) + BR);
		// Table Contents
		dailyReport.append(tableHorizontalLine + BR);
		
		hourlySales.forEach(
			(hourlySale) ->
				dailyReport.append(
					center(
						VERTICAL + center(String.format("%s:00", hourlySale[0]), daily1stColumnWidth) + 
						VERTICAL + center(String.format("%s:00", hourlySale[1]), daily2ndColumnWidth) +
						VERTICAL + center(hourlySale[2], daily3rdColumnWidth) +
						VERTICAL + center(hourlySale[3], daily4thColumnWidth) +
						VERTICAL + center(hourlySale[4], daily5thColumnWidth) +
						VERTICAL
					) + BR)
		);

		dailyReport.append(tableHorizontalLine + BR);
		
		// Footer/Calculation part
		dailyReport.append(justify(VERTICAL + SPACE + VERTICAL) + BR);
		dailyReport.append(
			justify(
				encryptSpaces(VERTICAL + SPACE + "Total Transactions:") + 
				SPACE + 
				encryptSpaces(totalTransactions + SPACE + VERTICAL)) + BR
		);
		dailyReport.append(
			justify(
				encryptSpaces(VERTICAL + SPACE + "Total Product Sold:") + 
				SPACE + 
				encryptSpaces(totalProductSold + SPACE + VERTICAL)) + BR
		);
		dailyReport.append(
			justify(
				encryptSpaces(VERTICAL + SPACE + "Total Gross Sales:") + 
				SPACE + 
				encryptSpaces(utility.formatCurrency(grossSales) + SPACE + VERTICAL)) + BR
		);
		dailyReport.append(justify(VERTICAL + SPACE + VERTICAL) + BR);
		
		// Page closing
		dailyReport.append(tableHorizontalLine + BR);
		dailyReport.append(center("") + BR);
		dailyReport.append(horizontalLine + BR);
		
		utility.writeFile("business", dailyReportFileName, dailyReport.toString());
		return true;
	}
	
	private ArrayList<String[]> parseDailyTransactions(int reportType, Object[][] transactions) {
		if (transactions.length == 0) {
			return null;
		}
		
		ArrayList<String[]> hourlySales = new ArrayList<>();
		
		calendar.setTimeInMillis(((Timestamp) transactions[0][3]).getTime());
		int hourStart = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.setTimeInMillis(((Timestamp) transactions[transactions.length - 1][3]).getTime());
		int hourEnd = calendar.get(Calendar.HOUR_OF_DAY);

		// Reverting back to current time
		calendar = Calendar.getInstance();
		if (reportType == PREVIOUS_DAY_REPORT) {
			calendar.add(Calendar.DAY_OF_YEAR, -1);
		}
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		totalTransactions = 0;
		totalProductSold = 0;
		grossSales = 0.0;
		
		for (int timeSpanStart = hourStart; timeSpanStart <= hourEnd; timeSpanStart++) {
			calendar.set(Calendar.HOUR_OF_DAY, timeSpanStart);
			Date hourFrom = calendar.getTime();
			
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			calendar.add(Calendar.MILLISECOND, -1);
			Date hourTo = calendar.getTime();
			
			Object[] fetchedData = statistic.getHourlySaleStatistic(hourFrom, hourTo);
			
			totalTransactions += (int) fetchedData[0];
			totalProductSold += (double) fetchedData[1];
			grossSales += (double) fetchedData[2];
			
			String[] hourStatistic = {
				Integer.toString(timeSpanStart), 
				Integer.toString(timeSpanStart + 1), 
				Integer.toString((int) fetchedData[0]), 
				Double.toString((double) fetchedData[1]), 
				utility.formatCurrency((double) fetchedData[2]), 
			};
			hourlySales.add(hourStatistic);
			
			calendar.add(Calendar.MILLISECOND, 1);
		}
		
		return hourlySales;
	}

	public boolean generateMonthlyReport() {
		// TODO Create monthly Report
		
		return true;
	}
	
	private void checkPreviousReports() {
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			// Automatic report generation
			generateMonthlyReport();
		} else {
			String monthlyReportFileName = generateFileName(MONTHLY_REPORT);
			String dailyReportFileName = generateFileName(PREVIOUS_DAY_REPORT);
			
			File previousMonthFile = new File(String.format(filePathFormat, monthlyReportFileName));
			File previousDayFile = new File(String.format(filePathFormat, dailyReportFileName));
			
			if (!previousMonthFile.isFile()) {
				generateMonthlyReport();
			}
			
			if (!previousDayFile.isFile()) {
				logger.addLog(Logger.LEVEL_1, 
					String.format("The system have detected that the previous day's sales report does not exist, "
							+ "it will now automatically create under the User ID: %s.", user[0].toString()));
				
				generateDailyReport(PREVIOUS_DAY_REPORT);
			}
		};
	}
	
	private String generateFileName(int reportType) {
		calendar = Calendar.getInstance();
		StringBuilder fileName = new StringBuilder();
		
		if (reportType == CURRENT_DAY_REPORT) {
			// For daily file name (current day)
			fileName.append(dailyFilePrefix);
			fileName.append(dailyFileDateTimeFormat.format(calendar.getTime()));
		} 
		
		else if (reportType == PREVIOUS_DAY_REPORT) {
			// For daily file name (previous day)
			calendar.add(Calendar.DAY_OF_MONTH, -1);

			fileName.append(dailyFilePrefix);
			fileName.append(dailyFileDateTimeFormat.format(calendar.getTime()));
		}
		
		else if (reportType == MONTHLY_REPORT){
			// Previous month's file name 
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.MONTH, -1);
			
			fileName.append(monthlyFilePrefix);
			fileName.append(monthlyFileDateTimeFormat.format(calendar.getTime()));
		}

		return fileName.toString();
	}
	
	private void setupObjects() {
		reportDate = reportDateTimeFormat.format(calendar.getTime());
        
        for (int space = 0; space < (MARGIN - 2) / 2; space++) {
        	defaultMarginPadding += SPACE;
        }
		
        for (int space = 0; space < WIDTH; space++) {
            horizontalLine += HORIZONAL;
        }
        
        tableHorizontalLine += VERTICAL + defaultMarginPadding;
        for (int space = 0; space < WIDTH - MARGIN; space++) {
            tableHorizontalLine += HORIZONAL;
        }
        tableHorizontalLine += defaultMarginPadding + VERTICAL;
    }
	
	public String encryptSpaces(String message) {
		String newMessage = "";
		for (int index = 0; index < message.length(); index++) {
			if (message.charAt(index) == ' ') newMessage += SPACE_ENRYPTION_CHARACTER;
			else newMessage += message.charAt(index);
		}
		return newMessage;
	}
	
	public String decryptSpaces(String message) {
		String newMessage = "";
		for (int index = 0; index < message.length(); index++) {
			if (message.charAt(index) == SPACE_ENRYPTION_CHARACTER) newMessage += " ";
			else newMessage += message.charAt(index);
		}
		return newMessage;
	}
	
	private String leftAlign(String message) {
		int length = message.length();
        if (length == WIDTH - MARGIN) 
        	return VERTICAL + defaultMarginPadding + message + defaultMarginPadding + VERTICAL;
        
        if (length > WIDTH - MARGIN) {
            return VERTICAL + defaultMarginPadding + message.substring(0, WIDTH - MARGIN) + BR + 
                center(message.substring(WIDTH - MARGIN)) + defaultMarginPadding + VERTICAL;
        }
        
        int rightPadding = (WIDTH - MARGIN) - length;
        for (int paddingIndex = 0; paddingIndex < rightPadding; paddingIndex++) {
        	message = message + SPACE;
        }
        
        return VERTICAL + defaultMarginPadding + message + defaultMarginPadding + VERTICAL;
	}
	
	private String center(String message) {
        int length = message.length();
        
        // Condition if the length of the message is exactly the available space between the horizontal edges with margin
        if (length == WIDTH - MARGIN) 
        	return VERTICAL + defaultMarginPadding + message + defaultMarginPadding + VERTICAL;
        
        // Condition if the length of the message is greater than the available space
        if (length > WIDTH - MARGIN) {
            return VERTICAL + defaultMarginPadding + message.substring(0, WIDTH - MARGIN) 
            	+ defaultMarginPadding + VERTICAL + BR 
            	+ center(message.substring(WIDTH - MARGIN));
        }
        
        // Condition if the length of the message is less than the available space
        int padding = ((WIDTH - MARGIN) - length) / 2;
        for (int space = 0; space < padding; space++) {
            message = " " + message;
        }
        int rightPadding = padding + (((WIDTH - MARGIN) - length) % 2);
        for (int space = 0; space < rightPadding; space++) {
            message = message + " ";
        }
        
        return VERTICAL + defaultMarginPadding + message + defaultMarginPadding + VERTICAL;
    }
	
	public String center(String message, long width) {
		int length = message.length();
		
		if (length == width) 
			return message;
		
		String newMessage = "";
		if (length > width) {
			newMessage = message.substring(0, (int) width - 2);
			newMessage += "..";
			return newMessage;
		}
		
		long padding = (width - length) / 2;
		long extra = (width - length) % 2;
		
		for (int left = 0; left < padding + extra; left++)
			newMessage += " ";
		
		newMessage += message;
		
		for (int right = 0; right < padding; right++)
			newMessage += " ";
		
		return newMessage;
	}
	
	private String justify(String message) {
		return justify(message, WIDTH - MARGIN, true);
	}
	
	private String justify(String message, int width, boolean withVertical) {
		int messageLength = message.length();
		if (messageLength > width) {
			return VERTICAL + defaultMarginPadding 
					+ message.substring(0, width)
					+ defaultMarginPadding + VERTICAL + BR 
					+ justify(message.substring(width));
		}
		
		StringBuilder newMessage = new StringBuilder();
		ArrayList<String> spaces = new ArrayList<>();
		
		String[] words = message.split(" ");
		
		for (int messageIndex = 0; messageIndex < messageLength; messageIndex++) {
			if (message.charAt(messageIndex) == ' ') {
				spaces.add(" ");
			}
		}
		
		int spaceToBeDistributed = width - messageLength;
		while (spaceToBeDistributed > 0) {
			for (int spaceIndex = 0; spaceIndex < spaces.size(); spaceIndex++) {
				if (spaceToBeDistributed > 0) {
					String spaceToBeAdded = spaces.get(spaceIndex);
					spaceToBeAdded += " ";
					spaces.set(spaceIndex, spaceToBeAdded);
					
					spaceToBeDistributed--;
				} else break;
			}
		}
		
		for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
			newMessage.append(words[wordIndex]);
			if (wordIndex != words.length - 1) {
				newMessage.append(spaces.get(wordIndex));
			}
		}
		
		if (withVertical) {
			newMessage.insert(0, VERTICAL + defaultMarginPadding);
			newMessage.append(defaultMarginPadding + VERTICAL);
		}
		
		return decryptSpaces(newMessage.toString());
	}
}
