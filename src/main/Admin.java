package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.awt.CardLayout;

@SuppressWarnings("serial")
public class Admin extends JFrame {
	
	private final String TITLE = "Administrator Mode";
	private final String logTitle = "System Logs";
	
	private int defaultHeight = 710; // 600
	private int defaultWidth = 990; // 1000
	private int minHeight = 600; // 710
	private int minWidth = 800; // 990

	private Database database; 
	private Gallery gallery;
	private Utility utility;
	private VerticalLabelUI verticalUI;
	
	private JDatePickerImpl datePicker;
	private RoundedPanel navigationPanel, displayPanel;
	private JLabel lblLogsButton, lblUsersButton, lblLogTitle, lblSearchButton,
				lblDate;
	
	private JPanel cardPanel, systemLogPanel, userPanel, logCardPanel;
	private CardLayout cardLayout, logCartLayout;
	private JPanel logListPanel;
	private JPanel logEmptyPanel;

	public Admin(Database database, Gallery gallery, Utility utility) {
		this.database = database;
		this.gallery = gallery;
		this.utility = utility;
		
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
		logCardPanel.add(logListPanel, "name_25864691607600");
		
		logEmptyPanel = new JPanel();
		logCardPanel.add(logEmptyPanel, "name_25906678664200");
		
		
		
		
		
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
		lblSearchButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblSearchButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblSearchButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				// TODO Search
			}
		});
	}
}
