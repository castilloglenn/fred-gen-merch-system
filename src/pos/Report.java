package pos;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	private final int WIDTH = 40; // 107
	private final int MARGIN = 6;

	private final char SPACE_ENRYPTION_CHARACTER = '*';
	
	private final String HORIZONTAL_LINE_CHARACTER = "=";
	private final String VERTICAL_LINE_CHARACTER = "|";
	
	private final String SPACE = " ";
	private final String BR = "\n";
	
    private String horizontalLine = "";
    private String tableHorizontalLine = "";
	
	private String defaultMarginPadding = "";

	private Database database;
	private Utility utility;
	private Logger logger;
	private Object[] user;
	
	private String filePathFormat = "./reports/business/%s.txt";
	private String dailyFilePrefix = "DAILY-REPORT-";
	private String monthlyFilePrefix = "MONTHLY-REPORT-";
	private String reportDate;
	
	private SimpleDateFormat dailyFileDateTimeFormat;
	private SimpleDateFormat monthlyFileDateTimeFormat;
	private SimpleDateFormat reportDateTimeFormat;
	
	private Calendar calendar;
	
	private Report(Object[] user) {
		database = Database.getInstance();
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
		
		String sampleText = "12345678901234567890123456789012345";
		int sampleWidth = 10;
		
		System.out.println(horizontalLine);
		System.out.println(center(tableHorizontalLine));
		System.out.println(center(sampleText));
		
		
		// Reverting back to current time
		calendar = Calendar.getInstance();
		
		return true;
	}
	
	private void generateMonthlyReport() {
		// TODO Create monthly Report
		
		
	}
	
	private void setupObjects() {
		reportDate = reportDateTimeFormat.format(calendar.getTime());
		
        for (int space = 0; space < WIDTH; space++) {
            horizontalLine += HORIZONTAL_LINE_CHARACTER;
        }
        
        for (int space = 0; space < WIDTH - MARGIN; space++) {
            tableHorizontalLine += HORIZONTAL_LINE_CHARACTER;
        }
        
        for (int space = 0; space < (MARGIN / 2) - 2; space++) {
        	defaultMarginPadding += SPACE;
        }
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
	
	private String center(String message) {
        int length = message.length();
        if (length == WIDTH - MARGIN) 
        	return VERTICAL_LINE_CHARACTER + defaultMarginPadding + message + defaultMarginPadding + VERTICAL_LINE_CHARACTER;
        
        if (length > WIDTH - MARGIN) {
            return VERTICAL_LINE_CHARACTER + defaultMarginPadding + message.substring(0, WIDTH - MARGIN) + defaultMarginPadding + VERTICAL_LINE_CHARACTER + BR + 
                center(message.substring(WIDTH - MARGIN));
        }
        
        int padding = (WIDTH - length) / 2 - 3;
        for (int space = 0; space < padding; space++) {
            message = " " + message;
        }
        int rightPadding = padding + ((WIDTH - length) % 2);
        for (int space = 0; space < rightPadding; space++) {
            message = message + " ";
        }
        
        return VERTICAL_LINE_CHARACTER + SPACE + message + SPACE + VERTICAL_LINE_CHARACTER;
    }
	
	public String center(String message, long width) {
		int length = message.length();
		if (length == width) return message;
		
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
        String newMessage = "";
		ArrayList<Integer> spaceIndexes = new ArrayList<>();

        for (int index = 0; index < message.length(); index++) {
            if (message.charAt(index) == ' ') spaceIndexes.add(index);
        }

        if (spaceIndexes.size() == 0) return center(message);
        int limit = WIDTH - MARGIN;
        int totalSpaces = limit - (message.length() - spaceIndexes.size());
        int equalSpaces = totalSpaces / spaceIndexes.size();
        int residual = totalSpaces % spaceIndexes.size();

        int previousIndex = 0;
        for (int index = 0; index < spaceIndexes.size(); index++) {
            for (int start = previousIndex; start < spaceIndexes.get(index); start++)
                if (message.charAt(start) != ' ') newMessage += message.charAt(start);
            for (int spaceIndex = 0; spaceIndex < equalSpaces; spaceIndex++)
                newMessage += " ";
            previousIndex = spaceIndexes.get(index);
        }
        for (int residualIndex = 0; residualIndex < residual; residualIndex++)
            newMessage += " ";
        for (int index = previousIndex; index < message.length(); index++)
            if (message.charAt(index) != ' ') newMessage += message.charAt(index);
        
		return decryptSpaces(VERTICAL_LINE_CHARACTER + SPACE + newMessage + SPACE + VERTICAL_LINE_CHARACTER);
	}
	
	private String justify(String message, long width) {
        String newMessage = " ";
		ArrayList<Integer> spaceIndexes = new ArrayList<>();

        for (int index = 0; index < message.length(); index++) {
            if (message.charAt(index) == ' ') spaceIndexes.add(index);
        }

        if (spaceIndexes.size() == 0) return center(message);
        int limit = (int) width - 2;
        int totalSpaces = limit - (message.length() - spaceIndexes.size());
        int equalSpaces = totalSpaces / spaceIndexes.size();
        int residual = totalSpaces % spaceIndexes.size();

        int previousIndex = 0;
        for (int index = 0; index < spaceIndexes.size(); index++) {
            for (int start = previousIndex; start < spaceIndexes.get(index); start++)
                if (message.charAt(start) != ' ') newMessage += message.charAt(start);
            for (int spaceIndex = 0; spaceIndex < equalSpaces; spaceIndex++)
                newMessage += " ";
            previousIndex = spaceIndexes.get(index);
        }
        for (int residualIndex = 0; residualIndex < residual; residualIndex++)
            newMessage += " ";
        for (int index = previousIndex; index < message.length(); index++)
            if (message.charAt(index) != ' ') newMessage += message.charAt(index);
        
		return decryptSpaces(newMessage) + " ";
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
