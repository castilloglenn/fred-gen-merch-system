package pos;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.Utility;

import java.awt.Dialog.ModalityType;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Checkout extends JDialog {

	private final String TITLE = "Checkout";
	
	private Logger logger;
	private Database database;
	private Gallery gallery;
	private Utility utility;
	
	private Object[] user;
	private ArrayList<CartItem> cartList;
	
	
	

	public Checkout(Object[] user, ArrayList<CartItem> cartList) {
		database = Database.getInstance();
		gallery = Gallery.getInstance();
		logger = Logger.getInstance();
		utility = Utility.getInstance();
		
		this.user = user;
		this.cartList = cartList;
		
		setIconImage(gallery.getSystemIcon());
		setTitle(TITLE + Utility.TITLE_SEPARATOR + Utility.APP_TITLE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(990, 710);
		setResizable(false);
		getContentPane().setBackground(Gallery.BLACK);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
