package main;

import java.awt.EventQueue;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;

import pos.POS;
import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.RoundedPanel;
import utils.Utility;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;

import inventory.Inventory;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JPasswordField;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.KeyAdapter;


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
	private Admin admin;
	private POS pos;
	private Inventory inventory;
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
		
		logger.addLog("System started.");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.addLog("System closed.");
			}
		});

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
		
		lblTitle = new JLabel("<html><p style='text-align: center;'>" + Utility.APP_TITLE + 
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
		sl_loginPanel.putConstraint(SpringLayout.NORTH, tfUsername, -2, SpringLayout.NORTH, lblUsername);
		sl_loginPanel.putConstraint(SpringLayout.WEST, tfUsername, 10, SpringLayout.EAST, lblUsername);
		sl_loginPanel.putConstraint(SpringLayout.SOUTH, tfUsername, 2, SpringLayout.SOUTH, lblUsername);
		sl_loginPanel.putConstraint(SpringLayout.EAST, tfUsername, 170, SpringLayout.EAST, lblUsername);
		loginPanel.add(tfUsername);
		tfUsername.setColumns(10);
		
		tfPassword = new JPasswordField();
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
		
		
		
		

		lblForgotPasswordButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblForgotPasswordButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblForgotPasswordButton); }
			
			@Override public void mouseClicked(MouseEvent e) { 
				// TODO forgot password UI
				
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
						.showOptionDialog(null, panel, Utility.APP_TITLE,
				                         JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
				                         null, options, options[0]);
				
				// Manager password return value
				String managerPassword = null;
				if(option == 0) {
				    char[] password = pass.getPassword();
				    managerPassword = new String(password);
				}
				
				// TODO continue the logic by creating the id of users and fetch only the managers
				//	then compare the hashed value of the inputed password if it matches any of the
				//	managers or administrators password then continue the logic here
//				System.out.println(managerPassword);

				System.out.println(utility.generateTransactionID());
//				System.out.println(utility.generateProductID(4211129419L));
			}
		});
		lblLoginButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblLoginButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblLoginButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				// TODO login redirect to portal
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
		
//		pos = new POS(gallery);
//		pos.setVisible(true);
		
//		inventory = new Inventory();
//		inventory.setVisible(true);
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
			System.out.println("hehe boi");
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
					logger.addLog(String.format("User '%s' has logged on and opened Administrator Mode.", user[0]));
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
		setVisible(false);
		admin = new Admin(this, user);
		admin.setVisible(true);
	}
}
