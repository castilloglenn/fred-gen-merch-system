package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JOptionPane;

public class Logger {
	
	/**
	 * Singleton instance
	 */
	private static Logger singletonInstance = null;
	
	public static final String LEVEL_1 = "LOW";
	public static final String LEVEL_2 = "MODERATE";
	public static final String LEVEL_3 = "HIGH";

    private String formattedFileName;

    private Logger() {
        StringBuilder string = new StringBuilder("reports/system/");
        Calendar time = Calendar.getInstance();

        string.append(time.get(Calendar.YEAR));
        string.append(String.format("%02d", time.get(Calendar.MONTH) + 1));
        string.append(String.format("%02d", time.get(Calendar.DAY_OF_MONTH)));

        string.append(".txt");

        formattedFileName = string.toString();
    }

    public static Logger getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new Logger();
        }

        return singletonInstance;
    }

    public void addLog(String level, String message) {
        try {
            File file = new File(formattedFileName);
            
			if (!file.isFile() && !file.createNewFile()) {
				JOptionPane.showMessageDialog(
						null, "Logger: File creation unsuccessful.", 
						Utility.BUSINESS_TITLE, 
						JOptionPane.WARNING_MESSAGE);
				
				throw new IOException("Error creating new file: " + file.getAbsolutePath());
			}
			
			FileWriter fw = new FileWriter(formattedFileName, true);
		    BufferedWriter bw = new BufferedWriter(fw);

	        try {
	        	StringBuilder timestamp = new StringBuilder("[");
	            Calendar time = Calendar.getInstance();

	            timestamp.append(String.format("%02d", time.get(Calendar.HOUR_OF_DAY)));
	            timestamp.append(":");
	            timestamp.append(String.format("%02d", time.get(Calendar.MINUTE)));
	            timestamp.append(":");
	            timestamp.append(String.format("%02d", time.get(Calendar.SECOND)));
	            timestamp.append("] " + level);
	            
	            String fullLog = 
	            	String.format(
            			"%s: %s",
            			timestamp.toString(),
            			message);
	            
	            String encodedLog = 
	            	Utility
	            		.getInstance()
	            		.encodeData(fullLog);
	            
//	            bw.write(fullLog);
	            bw.write(encodedLog);
	            bw.newLine();
	        } finally {
	        	bw.close();
	        	fw.close();
	        }
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					null, "Logger: Error handling file.", 
					Utility.BUSINESS_TITLE, 
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
        
        
    }

}
