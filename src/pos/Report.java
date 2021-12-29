package pos;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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
	
	private SimpleDateFormat dailyFileDateTimeFormat;
	private SimpleDateFormat monthlyFileDateTimeFormat;
	private SimpleDateFormat reportDateTimeFormat;
	
	private Calendar calendar;
	
	private Report(Object[] user) {
		database = Database.getInstance();
		statistic = Statistic.getInstance();
		utility = Utility.getInstance();
		logger = Logger.getInstance();
		this.user = user;
		
		dailyFileDateTimeFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		monthlyFileDateTimeFormat = new SimpleDateFormat("yyyy-MM");
		reportDateTimeFormat = new SimpleDateFormat("MM/dd/yyy");
		
		calendar = Calendar.getInstance();
		
		checkPreviousMonthReport();
		setupObjects();
	}

	public static Report getInstance(Object[] user) {
		if (singletonInstance == null) {
			singletonInstance = new Report(user);
		}
		
		return singletonInstance;
	}
	
	public boolean generateDailyReport() {
		String monthlyReportFileName = generateFileName(true);
		File dailyFile = new File(String.format(filePathFormat, monthlyReportFileName));
		
		if (dailyFile.isFile()) {
			return false;
		}
		
		String employeeID = Long.toString((long) user[0]);
		String employeeName = user[1].toString() + " " + user[2].toString() + " " + user[3].toString();
		String reportDate = reportDateTimeFormat.format(calendar.getTime());
		
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
		ArrayList<String[]> hourlySales = parseTransactions(transactions);
		
		for (int qq = 0; qq < hourlySales.size(); qq++) {
			String[] w = hourlySales.get(qq);
			if (w == null) {
				System.out.println("null");
			} else {
				for (Object ow: w) {
					System.out.print(ow + " ");
				}
				System.out.println();
			}
		}
		
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
		
		
		
		// Reverting back to current time
		calendar = Calendar.getInstance();
		
		// TODO Remove this debug after
		System.out.println(dailyReport.toString());
		
		return true;
	}
	
	private ArrayList<String[]> parseTransactions(Object[][] transactions) {
		if (transactions == null) {
			return null;
		}
		
		ArrayList<String[]> hourlySales = new ArrayList<>();
		
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(((Timestamp) transactions[0][3]).getTime());
		int hourStart = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.setTimeInMillis(((Timestamp) transactions[transactions.length - 1][3]).getTime());
		int hourEnd = calendar.get(Calendar.HOUR_OF_DAY);

		// Reverting back to current time
		calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		for (int timeSpanStart = hourStart; timeSpanStart <= hourEnd; timeSpanStart++) {
			calendar.set(Calendar.HOUR_OF_DAY, timeSpanStart);
			Date hourFrom = calendar.getTime();
			
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			calendar.add(Calendar.MILLISECOND, -1);
			Date hourTo = calendar.getTime();
			
			Object[] fetchedData = statistic.getHourlySaleStatistic(hourFrom, hourTo);
			String[] hourStatistic = {
				Integer.toString(timeSpanStart), 
				Integer.toString(timeSpanStart + 1), 
				Integer.toString((int) fetchedData[0]), 
				Double.toString((double) fetchedData[1]), 
				Double.toString((double) fetchedData[2]), 
			};
			hourlySales.add(hourStatistic);
			
			calendar.add(Calendar.MILLISECOND, 1);
		}
		
		

		// Reverting back to current time
		calendar = Calendar.getInstance();
		
		return hourlySales;
	}

	private void generateMonthlyReport() {
		// TODO Create monthly Report
		
		
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
	
	private String justify(String message, int width) {
		return justify(message, width, false);
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
	
	private void checkPreviousMonthReport() {
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			generateMonthlyReport();
		} else {
			String monthlyReportFileName = generateFileName(false);
			File previousMonthFile = new File(String.format(filePathFormat, monthlyReportFileName));
			
			if (!previousMonthFile.isFile()) {
				generateMonthlyReport();
			}
		};
	}
	
	private String generateFileName(boolean isDaily) {
		StringBuilder fileName = new StringBuilder();
		
		if (isDaily) {
			// For daily file name
			fileName.append(dailyFilePrefix);
			fileName.append(dailyFileDateTimeFormat.format(calendar.getTime()));
		} 
		
		else {
			// Previous month's file name 
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.MONTH, -1);
			
			fileName.append(monthlyFilePrefix);
			fileName.append(monthlyFileDateTimeFormat.format(calendar.getTime()));
		}
		
		// Reverting back to current time
		calendar = Calendar.getInstance();
		
		return fileName.toString();
	}
}
