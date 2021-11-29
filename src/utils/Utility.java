package utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
public class Utility {
	
	/**
	 * System default values
	 */
	public static final String APP_TITLE = "Fred's General Merchandise Store";
	public static final String TITLE_SEPARATOR = " | ";
	

	private File file;
	private JFileChooser chooser;
	private FileNameExtensionFilter filter;
	
	/**
	 * This class will be used for the JDateChooser
	 */
	@SuppressWarnings("serial")
	class DateLabelFormatter extends AbstractFormatter {

	    private String datePattern = "MM-dd-yyyy";
	    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        return dateFormatter.parseObject(text);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }

	        return dateFormatter.format(Calendar.getInstance().getTime());
	    }

	}

	
	public Utility() {
		chooser = new JFileChooser();
	}
	
	/**
	 * Shows a JFileChooser with Images-only filter
	 * 
	 * @return String - the absolute path of the image selected
	 * 			<br> null - if the user did not press the select or closed the file chooser
	 * 
	 * @see javax.swing.JFileChooser
	 */
	public String showImageChooser() {
	    filter = new FileNameExtensionFilter(
	    	"JPG & PNG Images", 
	    	"jpg", "jpeg", "png", "bmp"
	    );
	    chooser.setFileFilter(filter);
	    
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       file = chooser.getSelectedFile();
	       return file.getPath();
	    }
	    
	    return null;
	}
	
	public JDatePickerImpl getDateChooser() {
		/**
		 * Source: https://stackoverflow.com/questions/26794698/how-do-i-implement-jdatepicker
		 */
		
		UtilDateModel model = new UtilDateModel();
		Calendar today = Calendar.getInstance();
		model.setDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		return new JDatePickerImpl(datePanel, new DateLabelFormatter());
	}
	
	@SuppressWarnings("serial")
	public DefaultTableModel generateTable(Object[][] rows, Object[] column) {
		return new DefaultTableModel(
			rows, column
			) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
	}
	
	public String hashData(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(data.getBytes());
            BigInteger bi = new BigInteger(1, messageDigest);
            String hashedText = bi.toString(16);
            while (hashedText.length() < 32) {
                hashedText = "0" + hashedText;
            }
            return hashedText;
        } catch (NoSuchAlgorithmException e ) {
            e.printStackTrace();
        }
        return null;
    }
	
	public void copyToClipboard(String text) {
		StringSelection ss = new StringSelection(text);
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		cb.setContents(ss, null);
	}
	
	public String getFromClipboard() {
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable t = cb.getContents(null);
		if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			try {
				return (String) t.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException | IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * USER BIGINT(20) MAX: 9223372036854775807 <p>
	 *  example: 11211128001 ==== length(11) 	<br>
	 *      1-1-21-11-28-001					<br>
	 *   1 = Employee Code						<br>
	 *   1 = Level of Access					<br>
	 *        { 1 : Store Clerk					<br>
	 *          2 : Manager/Owner				<br>
	 *          3 : Administrator				<br>
	 *         }								<br>
	 *   21 = Year								<br>
	 *   11 = Month								<br>
	 *   28 = Day								<br>
	 *   001 = auto increment first is 1, second is 2, etc.<br>
	 */
	public long generateUserID(long lastID, int level) {
		StringBuilder markup = new StringBuilder("1");
		
		markup.append(Integer.toString(level));
		
		Calendar c = Calendar.getInstance();
		markup.append(Integer.toString(c.get(Calendar.YEAR)).substring(2));
		
		int month = c.get(Calendar.MONTH) + 1;
		if (month < 10) markup.append("0");
		markup.append(Integer.toString(month));
		
		int day = c.get(Calendar.DAY_OF_MONTH);
		if (day < 10) markup.append("0");
		markup.append(Integer.toString(day));
		
		if (lastID == -1) {
			markup.append("001");
		} else {
			String lastNum = Long.toString(lastID).substring(8);
			int increment = Integer.parseInt(lastNum) + 1;
			markup.append(String.format("%03d", increment));
		}
		
		return Long.parseLong(markup.toString());
	}
}

