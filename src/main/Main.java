package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.RoundedPanel;
import utils.Utility;


/**
 *  @author Allen Glenn E. Castillo
 */
@SuppressWarnings("serial")
public class Main extends JFrame {
	
	private final String TITLE = "Welcome!";
	private final String forgotPasswordMessage = "Please let the manager put their password for verification.";
	
	private Gallery gallery;
	private Database database;
	private Utility utility;
	private Logger logger;
	
	// System Modules
	public Admin admin;
	private Object[] user;
	
	// Window Components
	private RoundedPanel adminPanel, loginPanel, shadowPanel, titlePanel;
	private JLabel lblLeftDesign, lblRightDesign, lblTitle, lblUsername, lblPassword, lblAdminButton,
				lblForgotPasswordButton, lblLoginButton;
	private JTextField tfUsername;
	private JPasswordField tfPassword;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
	}

	public Main() {
		gallery = Gallery.getInstance();
		database = Database.getInstance();
		utility = Utility.getInstance();
		logger = Logger.getInstance();
		
		logger.addLog("Login window opened.");

		setIconImage(gallery.getSystemIcon());
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Gallery.GRAY);
		setSize(550, 400);

		setLocationRelativeTo(null);
		setResizable(false);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		lblLeftDesign = new JLabel(gallery.getImage("left-design.png", 132, 363));
		springLayout.putConstraint(SpringLayout.NORTH, lblLeftDesign, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblLeftDesign, 0, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblLeftDesign);
		
		lblRightDesign = new JLabel(gallery.getImage("right-design.png", 135, 363));
		springLayout.putConstraint(SpringLayout.NORTH, lblRightDesign, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblRightDesign, 0, SpringLayout.EAST, getContentPane());
		getContentPane().add(lblRightDesign);
		
		titlePanel = new RoundedPanel(Gallery.BLUE);
		springLayout.putConstraint(SpringLayout.NORTH, titlePanel, 15, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, titlePanel, 15, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, titlePanel, 115, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, titlePanel, -15, SpringLayout.EAST, getContentPane());
		getContentPane().add(titlePanel);
		SpringLayout sl_titlePanel = new SpringLayout();
		titlePanel.setLayout(sl_titlePanel);
		
		lblTitle = new JLabel("<html><p style='text-align: center;'>" + Utility.BUSINESS_TITLE + 
				"<br><small>Please login your credentials</small></p></html>");
		sl_titlePanel.putConstraint(SpringLayout.NORTH, lblTitle, 15, SpringLayout.NORTH, titlePanel);
		sl_titlePanel.putConstraint(SpringLayout.WEST, lblTitle, 15, SpringLayout.WEST, titlePanel);
		sl_titlePanel.putConstraint(SpringLayout.SOUTH, lblTitle, -15, SpringLayout.SOUTH, titlePanel);
		sl_titlePanel.putConstraint(SpringLayout.EAST, lblTitle, -15, SpringLayout.EAST, titlePanel);
		lblTitle.setFont(gallery.getFont(20f));
		lblTitle.setForeground(Gallery.WHITE);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		titlePanel.add(lblTitle);
		
		shadowPanel = new RoundedPanel(Gallery.DARK_BLUE);
		springLayout.putConstraint(SpringLayout.NORTH, shadowPanel, 20, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, shadowPanel, 15, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, shadowPanel, 135, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, shadowPanel, -15, SpringLayout.EAST, getContentPane());
		getContentPane().add(shadowPanel);
		
		loginPanel = new RoundedPanel(Gallery.WHITE);
		springLayout.putConstraint(SpringLayout.NORTH, loginPanel, 60, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, loginPanel, 30, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, loginPanel, -65, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, loginPanel, -30, SpringLayout.EAST, getContentPane());
		getContentPane().add(loginPanel);
		SpringLayout sl_loginPanel = new SpringLayout();
		loginPanel.setLayout(sl_loginPanel);
		
		lblUsername = new JLabel("Username: ");
		sl_loginPanel.putConstraint(SpringLayout.NORTH, lblUsername, 105, SpringLayout.NORTH, loginPanel);
		sl_loginPanel.putConstraint(SpringLayout.WEST, lblUsername, 110, SpringLayout.WEST, loginPanel);
		lblUsername.setFont(gallery.getFont(15f));
		loginPanel.add(lblUsername);
		
		lblPassword = new JLabel("Password: ");
		lblPassword.setFont(gallery.getFont(15f));
		sl_loginPanel.putConstraint(SpringLayout.NORTH, lblPassword, 10, SpringLayout.SOUTH, lblUsername);
		sl_loginPanel.putConstraint(SpringLayout.WEST, lblPassword, 0, SpringLayout.WEST, lblUsername);
		loginPanel.add(lblPassword);
		
		tfUsername = new JTextField();
		tfUsername.setFont(gallery.getFont(15f));
		sl_loginPanel.putConstraint(SpringLayout.NORTH, tfUsername, -2, SpringLayout.NORTH, lblUsername);
		sl_loginPanel.putConstraint(SpringLayout.WEST, tfUsername, 10, SpringLayout.EAST, lblUsername);
		sl_loginPanel.putConstraint(SpringLayout.SOUTH, tfUsername, 2, SpringLayout.SOUTH, lblUsername);
		sl_loginPanel.putConstraint(SpringLayout.EAST, tfUsername, 170, SpringLayout.EAST, lblUsername);
		loginPanel.add(tfUsername);
		tfUsername.setColumns(10);
		
		tfPassword = new JPasswordField();
		tfPassword.setFont(gallery.getFont(15f));
		sl_loginPanel.putConstraint(SpringLayout.NORTH, tfPassword, -2, SpringLayout.NORTH, lblPassword);
		sl_loginPanel.putConstraint(SpringLayout.WEST, tfPassword, 0, SpringLayout.WEST, tfUsername);
		sl_loginPanel.putConstraint(SpringLayout.SOUTH, tfPassword, 2, SpringLayout.SOUTH, lblPassword);
		sl_loginPanel.putConstraint(SpringLayout.EAST, tfPassword, 0, SpringLayout.EAST, tfUsername);
		loginPanel.add(tfPassword);
		
		lblForgotPasswordButton = new JLabel("<html>Forgot Password</html>");
		lblForgotPasswordButton.setName("danger");
		gallery.styleLabelToButton(lblForgotPasswordButton, 15f, 10, 7);
		sl_loginPanel.putConstraint(SpringLayout.NORTH, lblForgotPasswordButton, 15, SpringLayout.SOUTH, lblPassword);
		sl_loginPanel.putConstraint(SpringLayout.WEST, lblForgotPasswordButton, 0, SpringLayout.WEST, lblUsername);
		loginPanel.add(lblForgotPasswordButton);
		
		lblLoginButton = new JLabel("Login");
		sl_loginPanel.putConstraint(SpringLayout.WEST, lblLoginButton, 10, SpringLayout.EAST, lblForgotPasswordButton);
		lblLoginButton.setName("primary");
		gallery.styleLabelToButton(lblLoginButton, 15f, 10, 7);
		sl_loginPanel.putConstraint(SpringLayout.SOUTH, lblLoginButton, 0, SpringLayout.SOUTH, lblForgotPasswordButton);
		sl_loginPanel.putConstraint(SpringLayout.EAST, lblLoginButton, 0, SpringLayout.EAST, tfUsername);
		loginPanel.add(lblLoginButton);
		
		adminPanel = new RoundedPanel(Gallery.BLACK);
		springLayout.putConstraint(SpringLayout.NORTH, adminPanel, 65, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, adminPanel, 30, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, adminPanel, -15, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, adminPanel, -30, SpringLayout.EAST, getContentPane());
		getContentPane().add(adminPanel);
		SpringLayout sl_adminPanel = new SpringLayout();
		adminPanel.setLayout(sl_adminPanel);
		
		lblAdminButton = new JLabel("Login as Administrator");
		lblAdminButton.setName("secondary");
		gallery.styleLabelToButton(lblAdminButton, 15f, 20, 7);
		lblAdminButton.setForeground(Gallery.WHITE);
		sl_adminPanel.putConstraint(SpringLayout.WEST, lblAdminButton, 135, SpringLayout.WEST, adminPanel);
		sl_adminPanel.putConstraint(SpringLayout.SOUTH, lblAdminButton, -8, SpringLayout.SOUTH, adminPanel);
		adminPanel.add(lblAdminButton);
		
		
		
		


		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				logger.addLog("System closed.");
			}
		});
		lblForgotPasswordButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblForgotPasswordButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblForgotPasswordButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				// Manager password request panel setup and execution
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				JLabel label = new JLabel(forgotPasswordMessage);
				JPasswordField pass = new JPasswordField(10);
				String[] options = new String[]{"OK", "Cancel"};
				
				panel.add(label);
				panel.add(pass);
				
				// Manager password request dialog shows up
				int option = 
					JOptionPane
						.showOptionDialog(null, panel, Utility.BUSINESS_TITLE,
				                         JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
				                         null, options, options[0]);
				
				// Manager password return value
				String managerPassword = "";
				if(option == 0) {
				    char[] password = pass.getPassword();
				    managerPassword = utility.hashData(new String(password));
					String[] hashes = database.fetchManagerHashes();
					
					boolean hasEqual = false;
					for (String hash : hashes) {
						if (managerPassword.equals(hash)) {
							hasEqual = true;
						}
					}
					
					if (hasEqual) {
						JPanel panel2 = new JPanel();
						panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
						
						JLabel label3 = new JLabel("Enter your username.");
						JTextField field = new JTextField();
						
						JLabel label4 = new JLabel("Please enter a new password.");
						JPasswordField pass4 = new JPasswordField(10);
						
						JLabel label5 = new JLabel("Please confirm it by typing again.");
						JPasswordField pass5 = new JPasswordField(10);
						String[] options2 = new String[]{"OK", "Cancel"};
						
						panel2.add(label3);
						panel2.add(field);
						panel2.add(label4);
						panel2.add(pass4);
						panel2.add(label5);
						panel2.add(pass5);
						
						// Manager password request dialog shows up
						int option2 = 
							JOptionPane
								.showOptionDialog(null, panel2, Utility.BUSINESS_TITLE,
						                         JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
						                         null, options2, options2[0]);
						if (option2 == 0) {
							// Constructor for error handling to be displayed into the custom error message display
							ArrayList<String> errorMessages = new ArrayList<>(); // REQUIRED
							
							String enteredUsername = field.getText();
							String enteredPassword = new String(pass4.getPassword());
							String enteredConfirmation = new String(pass5.getPassword());
							Object[] fetchedUserDetail = database.getUserLogin(enteredUsername);
							
							if (fetchedUserDetail == null) {
								errorMessages.add("- Username does not exists.");
							} else {
								if (enteredPassword.isBlank() || enteredConfirmation.isBlank() || enteredUsername.isBlank()) {
									errorMessages.add("- Please fill up all the fields.");
								}
								
								if (!enteredPassword.equals(enteredConfirmation)) {
									errorMessages.add("- Passwords do not match.");
									
									if (enteredPassword.length() < 6) {
										errorMessages.add("- Password size must be 6 or more characters.");
									}
								}
							}
							
							if (errorMessages.size() > 0) { // REQUIRED
								errorMessages.add("<br>Please restart the process.");
								gallery.showMessage(errorMessages.toArray(new String[0])); // REQUIRED
							} else {
								String hashedNewPassword = utility.hashData(enteredConfirmation);
								
								if (database.setUser((long) fetchedUserDetail[0], fetchedUserDetail[1].toString(), 
										fetchedUserDetail[2].toString(), fetchedUserDetail[3].toString(), 
										fetchedUserDetail[4].toString(), fetchedUserDetail[5].toString(), 
										fetchedUserDetail[6].toString(), hashedNewPassword)) {
									JOptionPane.showMessageDialog(
										null, "Successfully updated the password.", 
										Utility.BUSINESS_TITLE, 
										JOptionPane.INFORMATION_MESSAGE);
								}
							}
						}
					} else {
						gallery.showMessage(new String[] {"- Incorrect input."}); // REQUIRED
					}
				}
			}
		});
		lblLoginButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblLoginButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblLoginButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				checkLogin();
			}
		});
		lblAdminButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblAdminButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblAdminButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				checkAdminLogin();
			}
		});
		
		// Key bindings to password
		tfPassword
			.getInputMap(JComponent.WHEN_FOCUSED)
			.put(
				KeyStroke.getKeyStroke(
					KeyEvent.VK_ENTER, 
					KeyEvent.SHIFT_DOWN_MASK, 
					true)
						, "Shift-Enter Action");
		tfPassword.getActionMap().put("Shift-Enter Action", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	checkAdminLogin();
		    }
		});
		tfPassword
		.getInputMap(JComponent.WHEN_FOCUSED)
		.put(
			KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 
				0, 
				false)
					, "Enter Action");
		tfPassword.getActionMap().put("Enter Action", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	checkLogin();
		    }
		});
		
		setVisible(true);
	}
	
	private Object[] checkAndGetUserDetails(String[] user) {
		ArrayList<String> errorMessages = new ArrayList<>(); // REQUIRED
		Object[] userDetails = new Object[8];
		
		if (database.checkUsername(user[0])) {
			userDetails = database.getUserLogin(user[0]);
			if (!userDetails[7].equals(user[1])) {
				errorMessages.add("- Password does not match the username provided.");
			}
		} else {
			errorMessages.add("- Username does not exists.");
		}
		
		if (errorMessages.size() > 0) { 
			gallery.showMessage(errorMessages.toArray(new String[0])); 
			return null;
		}
		
		return userDetails;
	}
	
	private void checkLogin() {
		String[] inputs = getInput();
		Object[] userDetails = checkAndGetUserDetails(inputs);
		
		logger.addLog(String.format("Username '%s' has been attempted to sign in.", inputs[0]));
			
		if (userDetails != null) {
			int rank = Integer.parseInt(Character.toString(userDetails[0].toString().charAt(1)));
			
			if (rank == 0 || rank == 3) {
				logger.addLog("An admin account has been attempted to use for business transactions.");
				gallery.showMessage(new String[] {"- You cannot use the administrator account for business transactions."});
			} else {
				user = userDetails;
				logger.addLog(String.format("Username '%s' has been logged on and opened the portal.", inputs[0]));
				
				new Portal(user);
				dispose();
			}
		}

	}
	
	private void checkAdminLogin() {
		String[] inputs = getInput();
		logger.addLog(String.format("The admin account has been attempted to sign in.", inputs[0]));
		
		if (inputs[0].equals("admin")) {
			Object[] fetch = database.getUserLogin("admin");
			String[] result = {fetch[6].toString(), fetch[7].toString()};

			if (Arrays.equals(inputs, result)) {
				user = fetch;
				logger.addLog(String.format("The admin account has been logged on and opened Administrator Mode.", user[0]));
				openAdmin();
			}
		} else {
			Object[] userDetails = checkAndGetUserDetails(inputs);
			if (userDetails != null) {
				char level = userDetails[0].toString().charAt(1);
				if (level == '2' || level == '3') {
					user = userDetails;
					logger.addLog(String.format("User %s has logged on and opened Administrator Mode.", user[0]));
					openAdmin();
				}
			}
		}
	}
	
	private String[] getInput() {
		String[] inputs = new String[2];
		
		inputs[0] = tfUsername.getText();
		inputs[1] = utility.hashData(
						new String(
							tfPassword.getPassword()));
		
		return inputs;
	}
	
	private void openAdmin() {
		new Admin(user);
		dispose();
	}
}
