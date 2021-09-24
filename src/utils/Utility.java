package utils;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
public class Utility {

	private File file;
	private JFileChooser chooser;
	private FileNameExtensionFilter filter;

	
	public Utility() {
		chooser = new JFileChooser();
	}
	
	/**
	 * Shows a JFileChooser with Images-only filter
	 * 
	 * @return String - the absolute path of the image selected
	 * 			<br> null - if the user did not press the select or closed the file chooser
	 * 
	 * @see java.swing.JFileChooser
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
	
}

