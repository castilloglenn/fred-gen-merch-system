package pos;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.RoundedPanel;
import utils.Utility;

@SuppressWarnings("serial")
public class RegisterCustomer extends JDialog {
	
	private String consentMessage = 
		"The customer must agree to give their personal information stated on the "
		+ "presented ID in accordance to the Republic Act No. 10173, also known as "
		+ "the Data Privacy Act of 2012.";
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
	private JCheckBox cbxConsent;
	private JLabel lblSubmitButton;
	
	public RegisterCustomer(Object[] user) {
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
		
		cbxConsent = new JCheckBox(
			"<html><i><small><p style=\"text-align: 'justify'; text-justify: 'inter-word';\">" 
			+ consentMessage + 
			"</p></i></small></html>");
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, cbxConsent, 60, SpringLayout.SOUTH, lblLastName);
		cbxConsent.setFont(gallery.getFont(20f));
		cbxConsent.setBackground(Gallery.WHITE);
		cbxConsent.setVerticalAlignment(SwingConstants.TOP);
		cbxConsent.setBorder(null);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, cbxConsent, 15, SpringLayout.SOUTH, lblLastName);
		sl_mainPanel.putConstraint(SpringLayout.WEST, cbxConsent, 30, SpringLayout.WEST, lblCustomerID);
		sl_mainPanel.putConstraint(SpringLayout.EAST, cbxConsent, -30, SpringLayout.EAST, tfLastName);
		mainPanel.add(cbxConsent);
		
		lblSubmitButton = new JLabel("Submit");
		lblSubmitButton.setName("primary");
		gallery.styleLabelToButton(lblSubmitButton, 14f, 5, 5);
		lblSubmitButton.setHorizontalAlignment(SwingConstants.CENTER);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, lblSubmitButton, 15, SpringLayout.SOUTH, cbxConsent);
		sl_mainPanel.putConstraint(SpringLayout.WEST, lblSubmitButton, 0, SpringLayout.WEST, lblCustomerID);
		sl_mainPanel.putConstraint(SpringLayout.EAST, lblSubmitButton, 0, SpringLayout.EAST, tfCustomerID);
		mainPanel.add(lblSubmitButton);
		
		lblSubmitButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblSubmitButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblSubmitButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				if (checkField()) {
					long id = Long.parseLong(tfCustomerID.getText());
					String type = cbType.getSelectedItem().toString();
					String cardID = tfCardID.getText();
					String fname = tfFirstName.getText();
					String mname = tfMiddleName.getText();
					String lname = tfLastName.getText();
					
					if (database.addCustomerDiscount(id, type, cardID, fname, mname, lname)) {
						logger.addLog(
							String.format(
								"User %s created customer with the ID: %s.", 
								user[0], Long.toString(id)));
						
						JOptionPane.showMessageDialog(
								null, "Successfully created customer: " + fname + " " + mname + " " + lname + ".", 
								Utility.BUSINESS_TITLE, 
								JOptionPane.INFORMATION_MESSAGE);
						
						dispose();
					}
				}
			}
		});
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private boolean checkField() {
		ArrayList<String> errorMessages = new ArrayList<>();
		
		if (cbType.getSelectedIndex() == -1) {
			errorMessages.add("- Please select a customer type.");
		}
		
		if (tfCardID.getText().isBlank()) {
			errorMessages.add("- The card ID field cannot be empty.");
		} else {
			String cardID = tfCardID.getText();
			Object[][] customerWithTheSameID = database.getCustomerDiscountsByKeyword(cardID);
			
			if (customerWithTheSameID.length > 0) {
				Object[] duplicatedIDCustomer = customerWithTheSameID[0];
				String customerFullName = duplicatedIDCustomer[3].toString() + " " + 
						duplicatedIDCustomer[4].toString() + " " + 
						duplicatedIDCustomer[5].toString();
				
				logger.addLog(
					String.format(
						"User %s encoded a new customer with a card id binded to %s. The system prevented the process.", 
						user[0], customerFullName));
				
				errorMessages.add("- The Card ID already exists in our system binded to the following customer: '" 
						+ customerFullName + "', Please use their data instead, if it is not the same name as the person, "
						+ "consider checking our fraud detection team in regards of the matter.");
			}
		}
		
		if (tfFirstName.getText().isBlank()) {
			errorMessages.add("- The first name field cannot be empty.");
		}
		
		if (tfLastName.getText().isBlank()) {
			errorMessages.add("- The middle name field cannot be empty.");
		}
		
		if (!cbxConsent.isSelected()) {
			errorMessages.add("- The customer must agree on the data privacy consent by clicking the checkbox toggle.");
		}
		
		if (errorMessages.size() > 0) {
			gallery.showMessage(errorMessages.toArray(new String[0]));
			
			return false;
		}
		
		return true;
	}
}
