package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;

import utils.Database;
import utils.Gallery;
import utils.RoundedPanel;
import utils.Utility;
import utils.VerticalLabelUI;
import javax.swing.SpringLayout;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class Admin extends JFrame {
	
	private final String TITLE = "Administrator Mode";
	private final String logTitle = "System Logs";
	private final String userTitle = "Users Management";
	private final String[] positionsList = new String[] {"Store Clerk", "Manager"};
	
	private int defaultHeight = 710; // 600
	private int defaultWidth = 990; // 1000
	private int minHeight = 600; // 710
	private int minWidth = 800; // 990

	private Database database; 
	private Gallery gallery;
	private Utility utility;
	private Main main;
	
	private JDatePickerImpl datePicker;
	private RoundedPanel navigationPanel, displayPanel;
	private JLabel lblLogsButton, lblUsersButton, lblLogTitle, lblSearchButton,
				lblDate, lblUsersTitle, lblUserID;
	
	private JPanel cardPanel, systemLogPanel, userPanel, 
				   logCardPanel, usersLeftPanel, usersRightPanel;
	private CardLayout cardLayout, logCartLayout;
	private JPanel logListPanel;
	private JPanel logEmptyPanel;
	private JScrollPane scrollPane;
	private JTable table;
	private JLabel lblFirstName;
	private JLabel lblMiddleName;
	private JLabel lblLastName;
	private JLabel lblPosition;
	private JLabel lblContact;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JTextField tfMiddleName;
	private JTextField tfUserID;
	private JTextField tfFirstName;
	private JTextField tfLastName;
	private JComboBox<String> cbPosition;
	private JTextField tfContact;
	private JTextField tfUsername;
	private JPasswordField pfPassword;
	private JLabel lblUpdateButton;
	private JLabel lblRemoveButton;
	private JLabel lblCreateButton;
	private JScrollPane logsScrollPane;
	private JTextArea taLogs;
	private JLabel lblClearButton;

	public Admin(Main main) {
		this.database = Database.getInstance();
		this.gallery = Gallery.getInstance();
		this.utility = Utility.getInstance();
		this.main = main;
		
		setIconImage(gallery.getSystemIcon());
		setTitle(TITLE + Utility.TITLE_SEPARATOR + Utility.APP_TITLE);
		setMinimumSize(new Dimension(minWidth, minHeight));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension selfDisplay = Toolkit.getDefaultToolkit().getScreenSize();
		if (defaultWidth >= selfDisplay.getWidth() || defaultHeight >= selfDisplay.getHeight()) {
			setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH );
		} else {
			setSize(defaultWidth, defaultHeight);
		}

		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		navigationPanel = new RoundedPanel(Gallery.BLUE);
		springLayout.putConstraint(SpringLayout.NORTH, navigationPanel, -55, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, navigationPanel, 15, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, navigationPanel, 15, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, navigationPanel, 215, SpringLayout.WEST, getContentPane());
		getContentPane().add(navigationPanel);
		
		displayPanel = new RoundedPanel(Gallery.WHITE);
		springLayout.putConstraint(SpringLayout.NORTH, displayPanel, 15, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, displayPanel, 15, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, displayPanel, -15, SpringLayout.NORTH, navigationPanel);
		SpringLayout sl_navigationPanel = new SpringLayout();
		navigationPanel.setLayout(sl_navigationPanel);
		
		lblLogsButton = new JLabel("LOGS");
		lblLogsButton.setName("primary");
		gallery.styleLabelToButton(lblLogsButton, 15f, "logs.png", 15, 10, 5);
		sl_navigationPanel.putConstraint(SpringLayout.NORTH, lblLogsButton, 15, SpringLayout.NORTH, navigationPanel);
		sl_navigationPanel.putConstraint(SpringLayout.WEST, lblLogsButton, 15, SpringLayout.WEST, navigationPanel);
		navigationPanel.add(lblLogsButton);
		
		lblUsersButton = new JLabel("USERS");
		lblUsersButton.setName("primary");
		gallery.styleLabelToButton(lblUsersButton, 15f, "users.png", 15, 10, 5);
		sl_navigationPanel.putConstraint(SpringLayout.NORTH, lblUsersButton, 0, SpringLayout.NORTH, lblLogsButton);
		sl_navigationPanel.putConstraint(SpringLayout.WEST, lblUsersButton, 10, SpringLayout.EAST, lblLogsButton);
		sl_navigationPanel.putConstraint(SpringLayout.SOUTH, lblUsersButton, 0, SpringLayout.SOUTH, lblLogsButton);
		navigationPanel.add(lblUsersButton);
		springLayout.putConstraint(SpringLayout.EAST, displayPanel, -15, SpringLayout.EAST, getContentPane());
		getContentPane().add(displayPanel);
		SpringLayout sl_displayPanel = new SpringLayout();
		displayPanel.setLayout(sl_displayPanel);
		
		cardPanel = new JPanel();
		cardLayout = new CardLayout(0, 0);
		cardPanel.setBackground(Gallery.WHITE);
		sl_displayPanel.putConstraint(SpringLayout.NORTH, cardPanel, 15, SpringLayout.NORTH, displayPanel);
		sl_displayPanel.putConstraint(SpringLayout.WEST, cardPanel, 15, SpringLayout.WEST, displayPanel);
		sl_displayPanel.putConstraint(SpringLayout.SOUTH, cardPanel, -15, SpringLayout.SOUTH, displayPanel);
		sl_displayPanel.putConstraint(SpringLayout.EAST, cardPanel, -15, SpringLayout.EAST, displayPanel);
		displayPanel.add(cardPanel);
		cardPanel.setLayout(cardLayout);
		
		systemLogPanel = new JPanel();
		systemLogPanel.setBackground(Gallery.WHITE);
		cardPanel.add(systemLogPanel, "system_log");
		SpringLayout sl_systemLogPanel = new SpringLayout();
		systemLogPanel.setLayout(sl_systemLogPanel);
		
		lblLogTitle = new JLabel(logTitle);
		lblLogTitle.setFont(gallery.getFont(30f));
		sl_systemLogPanel.putConstraint(SpringLayout.NORTH, lblLogTitle, 0, SpringLayout.NORTH, systemLogPanel);
		sl_systemLogPanel.putConstraint(SpringLayout.WEST, lblLogTitle, 0, SpringLayout.WEST, systemLogPanel);
		systemLogPanel.add(lblLogTitle);
		
		lblDate = new JLabel("Select date: ");
		lblDate.setFont(gallery.getFont(15f));
		sl_systemLogPanel.putConstraint(SpringLayout.NORTH, lblDate, 15, SpringLayout.SOUTH, lblLogTitle);
		sl_systemLogPanel.putConstraint(SpringLayout.WEST, lblDate, 0, SpringLayout.WEST, lblLogTitle);
		systemLogPanel.add(lblDate);
		
		datePicker = utility.getDateChooser();
		sl_systemLogPanel.putConstraint(SpringLayout.NORTH, datePicker, -4, SpringLayout.NORTH, lblDate);
		sl_systemLogPanel.putConstraint(SpringLayout.WEST, datePicker, 10, SpringLayout.EAST, lblDate);
		systemLogPanel.add(datePicker);
		
		lblSearchButton = new JLabel("Search");
		lblSearchButton.setName("primary");
		gallery.styleLabelToButton(lblSearchButton, 15f, 10, 4);
		sl_systemLogPanel.putConstraint(SpringLayout.NORTH, lblSearchButton, -5, SpringLayout.NORTH, lblDate);
		sl_systemLogPanel.putConstraint(SpringLayout.WEST, lblSearchButton, 225, SpringLayout.EAST, lblDate);
		systemLogPanel.add(lblSearchButton);
		
		logCardPanel = new JPanel();
		logCartLayout = new CardLayout(0, 0);
		logCardPanel.setBackground(Gallery.WHITE);
		sl_systemLogPanel.putConstraint(SpringLayout.NORTH, logCardPanel, 15, SpringLayout.SOUTH, lblDate);
		sl_systemLogPanel.putConstraint(SpringLayout.WEST, logCardPanel, 0, SpringLayout.WEST, lblLogTitle);
		sl_systemLogPanel.putConstraint(SpringLayout.SOUTH, logCardPanel, 0, SpringLayout.SOUTH, systemLogPanel);
		sl_systemLogPanel.putConstraint(SpringLayout.EAST, logCardPanel, 0, SpringLayout.EAST, systemLogPanel);
		systemLogPanel.add(logCardPanel);
		logCardPanel.setLayout(logCartLayout);
		
		logListPanel = new JPanel();
		logCardPanel.add(logListPanel, "log_result");
		SpringLayout sl_logListPanel = new SpringLayout();
		logListPanel.setLayout(sl_logListPanel);
		
		logsScrollPane = new JScrollPane();
		logsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sl_logListPanel.putConstraint(SpringLayout.NORTH, logsScrollPane, 0, SpringLayout.NORTH, logListPanel);
		sl_logListPanel.putConstraint(SpringLayout.WEST, logsScrollPane, 0, SpringLayout.WEST, logListPanel);
		sl_logListPanel.putConstraint(SpringLayout.SOUTH, logsScrollPane, 0, SpringLayout.SOUTH, logListPanel);
		sl_logListPanel.putConstraint(SpringLayout.EAST, logsScrollPane, 0, SpringLayout.EAST, logListPanel);
		logListPanel.add(logsScrollPane);
		
		taLogs = new JTextArea();
		taLogs.setWrapStyleWord(true);
		taLogs.setEditable(false);
		taLogs.setFont(gallery.getFont(15f));
		logsScrollPane.setViewportView(taLogs);
		
		logEmptyPanel = new JPanel();
		logEmptyPanel.setBackground(Gallery.RED);
		logCardPanel.add(logEmptyPanel, "log_empty");
		
		userPanel = new JPanel();
		userPanel.setBackground(Gallery.WHITE);
		cardPanel.add(userPanel, "user");
		SpringLayout sl_userPanel = new SpringLayout();
		userPanel.setLayout(sl_userPanel);
		
		lblUsersTitle = new JLabel(userTitle);
		lblUsersTitle.setFont(gallery.getFont(30f));
		sl_userPanel.putConstraint(SpringLayout.NORTH, lblUsersTitle, 0, SpringLayout.NORTH, userPanel);
		sl_userPanel.putConstraint(SpringLayout.WEST, lblUsersTitle, 0, SpringLayout.WEST, userPanel);
		userPanel.add(lblUsersTitle);
		
		usersLeftPanel = new JPanel();
		sl_userPanel.putConstraint(SpringLayout.SOUTH, usersLeftPanel, 0, SpringLayout.SOUTH, userPanel);
		usersLeftPanel.setBackground(Gallery.WHITE);
		sl_userPanel.putConstraint(SpringLayout.NORTH, usersLeftPanel, 15, SpringLayout.SOUTH, lblUsersTitle);
		sl_userPanel.putConstraint(SpringLayout.WEST, usersLeftPanel, 0, SpringLayout.WEST, lblUsersTitle);
		sl_userPanel.putConstraint(SpringLayout.EAST, usersLeftPanel, 350, SpringLayout.WEST, userPanel);
		userPanel.add(usersLeftPanel);
		
		usersRightPanel = new JPanel();
		usersRightPanel.setBackground(Gallery.WHITE);
		sl_userPanel.putConstraint(SpringLayout.NORTH, usersRightPanel, 0, SpringLayout.NORTH, usersLeftPanel);
		sl_userPanel.putConstraint(SpringLayout.WEST, usersRightPanel, 0, SpringLayout.EAST, usersLeftPanel);
		sl_userPanel.putConstraint(SpringLayout.SOUTH, usersRightPanel, 0, SpringLayout.SOUTH, usersLeftPanel);
		SpringLayout sl_usersLeftPanel = new SpringLayout();
		usersLeftPanel.setLayout(sl_usersLeftPanel);
		
		lblUserID = new JLabel("User ID: ");
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, lblUserID, 2, SpringLayout.NORTH, usersLeftPanel);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, lblUserID, 0, SpringLayout.WEST, usersLeftPanel);
		lblUserID.setFont(gallery.getFont(15f));
		usersLeftPanel.add(lblUserID);
		
		lblFirstName = new JLabel("First Name: ");
		lblFirstName.setFont(gallery.getFont(15f));
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, lblFirstName, 7, SpringLayout.SOUTH, lblUserID);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, lblFirstName, 0, SpringLayout.WEST, lblUserID);
		usersLeftPanel.add(lblFirstName);
		
		lblMiddleName = new JLabel("Middle Name: ");
		lblMiddleName.setFont(gallery.getFont(15f));
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, lblMiddleName, 7, SpringLayout.SOUTH, lblFirstName);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, lblMiddleName, 0, SpringLayout.WEST, lblUserID);
		usersLeftPanel.add(lblMiddleName);
		
		lblLastName = new JLabel("Last Name: ");
		lblLastName.setFont(gallery.getFont(15f));
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, lblLastName, 7, SpringLayout.SOUTH, lblMiddleName);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, lblLastName, 0, SpringLayout.WEST, lblUserID);
		usersLeftPanel.add(lblLastName);
		
		lblPosition = new JLabel("Position: ");
		lblPosition.setFont(gallery.getFont(15f));
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, lblPosition, 7, SpringLayout.SOUTH, lblLastName);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, lblPosition, 0, SpringLayout.WEST, lblUserID);
		usersLeftPanel.add(lblPosition);
		
		lblContact = new JLabel("Contact: ");
		lblContact.setFont(gallery.getFont(15f));
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, lblContact, 7, SpringLayout.SOUTH, lblPosition);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, lblContact, 0, SpringLayout.WEST, lblUserID);
		usersLeftPanel.add(lblContact);
		
		lblUsername = new JLabel("Username: ");
		lblUsername.setFont(gallery.getFont(15f));
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, lblUsername, 7, SpringLayout.SOUTH, lblContact);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, lblUsername, 0, SpringLayout.WEST, lblUserID);
		usersLeftPanel.add(lblUsername);
		
		lblPassword = new JLabel("Password: ");
		lblPassword.setFont(gallery.getFont(15f));
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, lblPassword, 7, SpringLayout.SOUTH, lblUsername);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, lblPassword, 0, SpringLayout.WEST, lblUserID);
		usersLeftPanel.add(lblPassword);
		
		tfMiddleName = new JTextField();
		tfMiddleName.setFont(gallery.getFont(15f));
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, tfMiddleName, -2, SpringLayout.NORTH, lblMiddleName);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, tfMiddleName, 5, SpringLayout.EAST, lblMiddleName);
		sl_usersLeftPanel.putConstraint(SpringLayout.SOUTH, tfMiddleName, 2, SpringLayout.SOUTH, lblMiddleName);
		sl_usersLeftPanel.putConstraint(SpringLayout.EAST, tfMiddleName, -10, SpringLayout.EAST, usersLeftPanel);
		usersLeftPanel.add(tfMiddleName);
		
		tfUserID = new JTextField(Long.toString(utility.generateUserID(database.fetchLastEmployee(), 1)));
		tfUserID.setFont(gallery.getFont(15f));
		tfUserID.setEditable(false);
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, tfUserID, -2, SpringLayout.NORTH, lblUserID);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, tfUserID, 0, SpringLayout.WEST, tfMiddleName);
		sl_usersLeftPanel.putConstraint(SpringLayout.SOUTH, tfUserID, 2, SpringLayout.SOUTH, lblUserID);
		sl_usersLeftPanel.putConstraint(SpringLayout.EAST, tfUserID, 0, SpringLayout.EAST, tfMiddleName);
		usersLeftPanel.add(tfUserID);
		
		tfFirstName = new JTextField();
		tfFirstName.setFont(gallery.getFont(15f));
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, tfFirstName, -2, SpringLayout.NORTH, lblFirstName);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, tfFirstName, 0, SpringLayout.WEST, tfMiddleName);
		sl_usersLeftPanel.putConstraint(SpringLayout.SOUTH, tfFirstName, 2, SpringLayout.SOUTH, lblFirstName);
		sl_usersLeftPanel.putConstraint(SpringLayout.EAST, tfFirstName, 0, SpringLayout.EAST, tfMiddleName);
		usersLeftPanel.add(tfFirstName);
		
		tfLastName = new JTextField();
		tfLastName.setFont(gallery.getFont(15f));
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, tfLastName, -2, SpringLayout.NORTH, lblLastName);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, tfLastName, 0, SpringLayout.WEST, tfMiddleName);
		sl_usersLeftPanel.putConstraint(SpringLayout.SOUTH, tfLastName, 2, SpringLayout.SOUTH, lblLastName);
		sl_usersLeftPanel.putConstraint(SpringLayout.EAST, tfLastName, 0, SpringLayout.EAST, tfMiddleName);
		usersLeftPanel.add(tfLastName);
		
		cbPosition = new JComboBox<>();
		cbPosition.setModel(new DefaultComboBoxModel<String>(positionsList));
		cbPosition.setFont(gallery.getFont(15f));
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, cbPosition, -2, SpringLayout.NORTH, lblPosition);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, cbPosition, 0, SpringLayout.WEST, tfMiddleName);
		sl_usersLeftPanel.putConstraint(SpringLayout.SOUTH, cbPosition, 2, SpringLayout.SOUTH, lblPosition);
		sl_usersLeftPanel.putConstraint(SpringLayout.EAST, cbPosition, 0, SpringLayout.EAST, tfMiddleName);
		usersLeftPanel.add(cbPosition);
		
		tfContact = new JTextField();
		tfContact.setFont(gallery.getFont(15f));
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, tfContact, -2, SpringLayout.NORTH, lblContact);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, tfContact, 0, SpringLayout.WEST, tfMiddleName);
		sl_usersLeftPanel.putConstraint(SpringLayout.SOUTH, tfContact, 2, SpringLayout.SOUTH, lblContact);
		sl_usersLeftPanel.putConstraint(SpringLayout.EAST, tfContact, 0, SpringLayout.EAST, tfMiddleName);
		usersLeftPanel.add(tfContact);
		
		tfUsername = new JTextField();
		tfUsername.setFont(gallery.getFont(15f));
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, tfUsername, -2, SpringLayout.NORTH, lblUsername);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, tfUsername, 0, SpringLayout.WEST, tfMiddleName);
		sl_usersLeftPanel.putConstraint(SpringLayout.SOUTH, tfUsername, 2, SpringLayout.SOUTH, lblUsername);
		sl_usersLeftPanel.putConstraint(SpringLayout.EAST, tfUsername, 0, SpringLayout.EAST, tfMiddleName);
		usersLeftPanel.add(tfUsername);
		
		pfPassword = new JPasswordField();
		pfPassword.setFont(gallery.getFont(15f));
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, pfPassword, -2, SpringLayout.NORTH, lblPassword);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, pfPassword, 0, SpringLayout.WEST, tfMiddleName);
		sl_usersLeftPanel.putConstraint(SpringLayout.SOUTH, pfPassword, 2, SpringLayout.SOUTH, lblPassword);
		sl_usersLeftPanel.putConstraint(SpringLayout.EAST, pfPassword, 0, SpringLayout.EAST, tfMiddleName);
		usersLeftPanel.add(pfPassword);
		
		lblUpdateButton = new JLabel("UPDATE DETAILS");
		sl_usersLeftPanel.putConstraint(SpringLayout.SOUTH, lblUpdateButton, 0, SpringLayout.SOUTH, usersLeftPanel);
		sl_usersLeftPanel.putConstraint(SpringLayout.EAST, lblUpdateButton, 165, SpringLayout.WEST, usersLeftPanel);
		lblUpdateButton.setName("secondary");
		gallery.styleLabelToButton(lblUpdateButton, 15f, 10, 3);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, lblUpdateButton, 0, SpringLayout.WEST, usersLeftPanel);
		usersLeftPanel.add(lblUpdateButton);
		
		lblRemoveButton = new JLabel("REMOVE USER");
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, lblRemoveButton, -175, SpringLayout.EAST, usersLeftPanel);
		sl_usersLeftPanel.putConstraint(SpringLayout.SOUTH, lblRemoveButton, 0, SpringLayout.SOUTH, usersLeftPanel);
		lblRemoveButton.setName("danger");
		gallery.styleLabelToButton(lblRemoveButton, 15f, 10, 4);
		sl_usersLeftPanel.putConstraint(SpringLayout.EAST, lblRemoveButton, -10, SpringLayout.EAST, usersLeftPanel);
		usersLeftPanel.add(lblRemoveButton);
		
		lblCreateButton = new JLabel("REGISTER USER");
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, lblCreateButton, -175, SpringLayout.EAST, usersLeftPanel);
		sl_usersLeftPanel.putConstraint(SpringLayout.EAST, lblCreateButton, -10, SpringLayout.EAST, usersLeftPanel);
		lblCreateButton.setName("primary");
		gallery.styleLabelToButton(lblCreateButton, 15f, 10, 4);
		sl_usersLeftPanel.putConstraint(SpringLayout.SOUTH, lblCreateButton, -10, SpringLayout.NORTH, lblUpdateButton);
		usersLeftPanel.add(lblCreateButton);
		
		lblClearButton = new JLabel("CLEAR FORM");
		lblClearButton.setName("primary");
		gallery.styleLabelToButton(lblClearButton, 15f, 10, 4);
		sl_usersLeftPanel.putConstraint(SpringLayout.NORTH, lblClearButton, 0, SpringLayout.NORTH, lblCreateButton);
		sl_usersLeftPanel.putConstraint(SpringLayout.WEST, lblClearButton, 0, SpringLayout.WEST, usersLeftPanel);
		sl_usersLeftPanel.putConstraint(SpringLayout.SOUTH, lblClearButton, 0, SpringLayout.SOUTH, lblCreateButton);
		sl_usersLeftPanel.putConstraint(SpringLayout.EAST, lblClearButton, 165, SpringLayout.WEST, usersLeftPanel);
		usersLeftPanel.add(lblClearButton);
		sl_userPanel.putConstraint(SpringLayout.EAST, usersRightPanel, 0, SpringLayout.EAST, userPanel);
		userPanel.add(usersRightPanel);
		SpringLayout sl_usersRightPanel = new SpringLayout();
		usersRightPanel.setLayout(sl_usersRightPanel);
		
		scrollPane = new JScrollPane();
		sl_usersRightPanel.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, usersRightPanel);
		sl_usersRightPanel.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, usersRightPanel);
		sl_usersRightPanel.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, usersRightPanel);
		sl_usersRightPanel.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, usersRightPanel);
		usersRightPanel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		getContentPane().setBackground(Gallery.BLACK);
		setLocationRelativeTo(null);
		
		
		lblLogsButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblLogsButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblLogsButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				
				cardLayout.show(cardPanel, "system_log");
			}
		});
		lblUsersButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblUsersButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblUsersButton); }
			
			@Override public void mouseClicked(MouseEvent e) {

				cardLayout.show(cardPanel, "user");
			}
		});
		lblSearchButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblSearchButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblSearchButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				// TODO Search
				Date datePicked = (Date) datePicker.getModel().getValue();
				
				System.out.println(datePicked);
			}
		});
		lblUpdateButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblUpdateButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblUpdateButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				// TODO Update the user details
					
			}
		});
		lblRemoveButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblRemoveButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblRemoveButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				// TODO Delete the user details
				
			}
		});
		lblCreateButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblCreateButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblCreateButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				// TODO Create a new user
				if (checkForm()) {
					database.addUser(
						Long.parseLong(
							tfUserID.getText()), 
							tfFirstName.getText(), 
							tfMiddleName.getText(), 
							tfLastName.getText(), 
							cbPosition.getSelectedItem().toString(), 
							tfContact.getText(), 
							tfUsername.getText(), 
							utility.hashData(
								new String(
									pfPassword.getPassword())));
					JOptionPane.showMessageDialog(
						null, "Successfully registered new user.", 
						Utility.APP_TITLE, 
						JOptionPane.INFORMATION_MESSAGE);
					clearForm();
				}
			}
		});
		lblClearButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblClearButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblClearButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				clearForm();
			}
		});
	}
	
	private boolean checkForm() {
		// Constructor for error handling to be displayed into the custom error message display
		ArrayList<String> errorMessages = new ArrayList<>(); // REQUIRED
		String fetchedPassword = new String(pfPassword.getPassword());
		
		// If conditions to handle all kinds of possible errors
		if (tfFirstName.getText().isBlank()) errorMessages.add("- First Name field cannot be empty.");
		if (tfLastName.getText().isBlank()) errorMessages.add("- Last Name field cannot be empty.");
		if (tfContact.getText().isBlank()) errorMessages.add("- Contact detail field cannot be empty.");
		
		if (tfUsername.getText().isBlank()) {
			errorMessages.add("- Username field cannot be empty.");
		} else if (database.checkUsername(tfUsername.getText())) {
			errorMessages.add("- Username already exists.");
		}
		
		if (fetchedPassword.isBlank()) {
			errorMessages.add("- Password field cannot be empty.");
		} else if (fetchedPassword.length() < 6) {
			errorMessages.add("- Passwords must be greater than or equal to 6 characters.");
		}
		
		if (errorMessages.size() > 0) { // REQUIRED
			gallery.showMessage(errorMessages.toArray(new String[0])); // REQUIRED
			return false;
		} else {
			
			return true;
		}
	}
	
	private void clearForm() {
		tfUserID.setText(Long.toString(utility.generateUserID(database.fetchLastEmployee(), 1)));
		tfFirstName.setText("");
		tfMiddleName.setText("");
		tfLastName.setText("");
		cbPosition.setSelectedIndex(0);
		tfContact.setText("");
		tfUsername.setText("");
		pfPassword.setText("");
	}
}
