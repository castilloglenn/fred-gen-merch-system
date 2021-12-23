package pos;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dialog.ModalityType;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.RoundedPanel;
import utils.Utility;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class UpdateCustomer extends JDialog {
	
	private String[] customerTypes = {"Senior Citizen", "Person With Disability"};
	
	private Logger logger;
	private Database database;
	private Gallery gallery;
	private Utility utility;

	private Object[] user;
	private JPanel mainPanel;
	private JLabel lblTitle;
	private JLabel lblCustomerID;
	private JTextField tfCustomerID;
	private JLabel lblType;
	private JComboBox<String> cbType;
	private JLabel lblCardID;
	private JTextField tfCardID;
	private JLabel lblFirstName;
	private JTextField tfFirstName;
	private JLabel lblMiddleName;
	private JTextField tfMiddleName;
	private JLabel lblLastName;
	private JTextField tfLastName;
	
	
	
	
	
	
	
	
	
	public UpdateCustomer(Object[] user) {
		database = Database.getInstance();
		gallery = Gallery.getInstance();
		logger = Logger.getInstance();
		utility = Utility.getInstance();

		this.user = user;
		
		setIconImage(gallery.getSystemIcon());
		setTitle(Utility.BUSINESS_TITLE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(550, 450);
		setResizable(false);
		getContentPane().setBackground(Gallery.GRAY);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JPanel titlePanel = new RoundedPanel(Gallery.BLUE);
		springLayout.putConstraint(SpringLayout.NORTH, titlePanel, -15, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, titlePanel, 45, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, titlePanel, 50, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, titlePanel, -45, SpringLayout.EAST, getContentPane());
		getContentPane().add(titlePanel);
		SpringLayout sl_titlePanel = new SpringLayout();
		titlePanel.setLayout(sl_titlePanel);
		
		lblTitle = new JLabel("Register New Customer");
		lblTitle.setFont(gallery.getFont(20f));
		lblTitle.setForeground(Gallery.WHITE);
		sl_titlePanel.putConstraint(SpringLayout.NORTH, lblTitle, 25, SpringLayout.NORTH, titlePanel);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		sl_titlePanel.putConstraint(SpringLayout.WEST, lblTitle, 15, SpringLayout.WEST, titlePanel);
		sl_titlePanel.putConstraint(SpringLayout.EAST, lblTitle, -15, SpringLayout.EAST, titlePanel);
		titlePanel.add(lblTitle);
		
		mainPanel = new RoundedPanel(Gallery.WHITE);
		springLayout.putConstraint(SpringLayout.NORTH, mainPanel, 15, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, mainPanel, 15, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, mainPanel, -15, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, mainPanel, -15, SpringLayout.EAST, getContentPane());
		getContentPane().add(mainPanel);
		SpringLayout sl_mainPanel = new SpringLayout();
		mainPanel.setLayout(sl_mainPanel);
		
		lblCustomerID = new JLabel("Generated ID: ");
		lblCustomerID.setFont(gallery.getFont(14f));
		sl_mainPanel.putConstraint(SpringLayout.NORTH, lblCustomerID, 50, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, lblCustomerID, 25, SpringLayout.WEST, mainPanel);
		mainPanel.add(lblCustomerID);
		
		tfCustomerID = new JTextField(Long.toString(utility.generateCustomerDiscountID(database.fetchLastID("customer_discount", "customer_discount_id"))));
		tfCustomerID.setFont(gallery.getFont(14f));
		tfCustomerID.setEditable(false);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, tfCustomerID, -2, SpringLayout.NORTH, lblCustomerID);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, tfCustomerID, 2, SpringLayout.SOUTH, lblCustomerID);
		sl_mainPanel.putConstraint(SpringLayout.EAST, tfCustomerID, -25, SpringLayout.EAST, mainPanel);
		mainPanel.add(tfCustomerID);
		tfCustomerID.setColumns(10);
		
		lblType = new JLabel("Discount Type: ");
		sl_mainPanel.putConstraint(SpringLayout.NORTH, lblType, 15, SpringLayout.SOUTH, lblCustomerID);
		lblType.setFont(gallery.getFont(14f));
		sl_mainPanel.putConstraint(SpringLayout.WEST, lblType, 0, SpringLayout.WEST, lblCustomerID);
		mainPanel.add(lblType);
		
		cbType = new JComboBox<String>();
		cbType.setModel(new DefaultComboBoxModel<String>(customerTypes));
		cbType.setFont(gallery.getFont(14f));
		sl_mainPanel.putConstraint(SpringLayout.NORTH, cbType, -2, SpringLayout.NORTH, lblType);
		sl_mainPanel.putConstraint(SpringLayout.WEST, cbType, 0, SpringLayout.WEST, tfCustomerID);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, cbType, 2, SpringLayout.SOUTH, lblType);
		sl_mainPanel.putConstraint(SpringLayout.EAST, cbType, 0, SpringLayout.EAST, tfCustomerID);
		mainPanel.add(cbType);
		
		lblCardID = new JLabel("ID Card Number: ");
		lblCardID.setFont(gallery.getFont(14f));
		sl_mainPanel.putConstraint(SpringLayout.WEST, tfCustomerID, 15, SpringLayout.EAST, lblCardID);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, lblCardID, 15, SpringLayout.SOUTH, lblType);
		sl_mainPanel.putConstraint(SpringLayout.WEST, lblCardID, 0, SpringLayout.WEST, lblCustomerID);
		mainPanel.add(lblCardID);
		
		tfCardID = new JTextField();
		tfCardID.setFont(gallery.getFont(14f));
		sl_mainPanel.putConstraint(SpringLayout.NORTH, tfCardID, -2, SpringLayout.NORTH, lblCardID);
		sl_mainPanel.putConstraint(SpringLayout.WEST, tfCardID, 0, SpringLayout.WEST, tfCustomerID);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, tfCardID, 2, SpringLayout.SOUTH, lblCardID);
		sl_mainPanel.putConstraint(SpringLayout.EAST, tfCardID, 0, SpringLayout.EAST, cbType);
		mainPanel.add(tfCardID);
		tfCardID.setColumns(10);
		
		lblFirstName = new JLabel("First Name: ");
		lblFirstName.setFont(gallery.getFont(14f));
		sl_mainPanel.putConstraint(SpringLayout.NORTH, lblFirstName, 15, SpringLayout.SOUTH, lblCardID);
		sl_mainPanel.putConstraint(SpringLayout.WEST, lblFirstName, 0, SpringLayout.WEST, lblCustomerID);
		mainPanel.add(lblFirstName);
		
		tfFirstName = new JTextField();
		tfFirstName.setFont(gallery.getFont(14f));
		sl_mainPanel.putConstraint(SpringLayout.NORTH, tfFirstName, -2, SpringLayout.NORTH, lblFirstName);
		sl_mainPanel.putConstraint(SpringLayout.WEST, tfFirstName, 0, SpringLayout.WEST, tfCustomerID);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, tfFirstName, 2, SpringLayout.SOUTH, lblFirstName);
		sl_mainPanel.putConstraint(SpringLayout.EAST, tfFirstName, 0, SpringLayout.EAST, tfCardID);
		mainPanel.add(tfFirstName);
		tfFirstName.setColumns(10);
		
		lblMiddleName = new JLabel("Middle Name: ");
		lblMiddleName.setFont(gallery.getFont(14f));
		sl_mainPanel.putConstraint(SpringLayout.NORTH, lblMiddleName, 15, SpringLayout.SOUTH, lblFirstName);
		sl_mainPanel.putConstraint(SpringLayout.WEST, lblMiddleName, 0, SpringLayout.WEST, lblCustomerID);
		mainPanel.add(lblMiddleName);
		
		tfMiddleName = new JTextField();
		tfMiddleName.setFont(gallery.getFont(14f));
		sl_mainPanel.putConstraint(SpringLayout.NORTH, tfMiddleName, -2, SpringLayout.NORTH, lblMiddleName);
		sl_mainPanel.putConstraint(SpringLayout.WEST, tfMiddleName, 0, SpringLayout.WEST, tfCustomerID);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, tfMiddleName, 2, SpringLayout.SOUTH, lblMiddleName);
		sl_mainPanel.putConstraint(SpringLayout.EAST, tfMiddleName, 0, SpringLayout.EAST, tfFirstName);
		mainPanel.add(tfMiddleName);
		tfMiddleName.setColumns(10);
		
		lblLastName = new JLabel("Last Name: ");
		lblLastName.setFont(gallery.getFont(14f));
		sl_mainPanel.putConstraint(SpringLayout.NORTH, lblLastName, 15, SpringLayout.SOUTH, lblMiddleName);
		sl_mainPanel.putConstraint(SpringLayout.WEST, lblLastName, 0, SpringLayout.WEST, lblCustomerID);
		mainPanel.add(lblLastName);
		
		tfLastName = new JTextField();
		tfLastName.setFont(gallery.getFont(14f));
		sl_mainPanel.putConstraint(SpringLayout.NORTH, tfLastName, -2, SpringLayout.NORTH, lblLastName);
		sl_mainPanel.putConstraint(SpringLayout.WEST, tfLastName, 0, SpringLayout.WEST, tfCustomerID);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, tfLastName, 2, SpringLayout.SOUTH, lblLastName);
		sl_mainPanel.putConstraint(SpringLayout.EAST, tfLastName, 0, SpringLayout.EAST, tfMiddleName);
		mainPanel.add(tfLastName);
		tfLastName.setColumns(10);


		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
