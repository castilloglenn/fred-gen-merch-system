package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import utils.Database;
import utils.Gallery;
import utils.RoundedPanel;
import utils.Utility;
import utils.VerticalLabelUI;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.CardLayout;

@SuppressWarnings("serial")
public class Admin extends JFrame {
	
	private final String TITLE = "Administrator Mode";
	
	private int defaultHeight = 710; // 600
	private int defaultWidth = 990; // 1000
	private int minHeight = 600; // 710
	private int minWidth = 800; // 990

	private Database database; 
	private Gallery gallery;
	private VerticalLabelUI verticalUI;
	
	private RoundedPanel navigationPanel, displayPanel;
	private JLabel lblLogsButton, lblUsersButton;
	
	private JPanel cardPanel;
	private CardLayout cardLayout;
	private JPanel systemLogPanel;
	private JPanel userPanel;
	

	public Admin(Database database, Gallery gallery) {
		this.database = database;
		this.gallery = gallery;
		
		// rotated 90 degrees counter-clockwise
		verticalUI = new VerticalLabelUI(false); 
		
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
		springLayout.putConstraint(SpringLayout.EAST, navigationPanel, 275, SpringLayout.WEST, getContentPane());
		getContentPane().add(navigationPanel);
		
		displayPanel = new RoundedPanel(Gallery.WHITE);
		springLayout.putConstraint(SpringLayout.NORTH, displayPanel, 15, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, displayPanel, 15, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, displayPanel, -15, SpringLayout.NORTH, navigationPanel);
		SpringLayout sl_navigationPanel = new SpringLayout();
		navigationPanel.setLayout(sl_navigationPanel);
		
		lblLogsButton = new JLabel("SYSTEM LOGS");
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
		systemLogPanel.setBackground(Gallery.RED);
		cardPanel.add(systemLogPanel, "system_log");
		
		userPanel = new JPanel();
		userPanel.setBackground(Gallery.BLUE);
		cardPanel.add(userPanel, "user");
		
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
	}
}
