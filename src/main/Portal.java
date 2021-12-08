package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import inventory.Inventory;
import pos.POS;
import utils.Gallery;
import utils.Logger;
import utils.RoundedPanel;
import utils.Utility;

import javax.swing.SpringLayout;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingConstants;

/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
@SuppressWarnings("serial")
public class Portal extends JFrame {
	
	private final String portalTitle = "Portal";
	
	private int windowWidth = 450;
	private int windowHeight = 325;
	
	private Gallery gallery;
	private Logger logger;
	private Object[] user;

	private JPanel contentPane;
	private JPanel mainPanel;
	private JLabel lblGreetingMessage;
	private JLabel lblPosButton;
	private JLabel lblInventoryButton;

	
	public Portal(Object[] user) {
		gallery = Gallery.getInstance();
		logger = Logger.getInstance();
		this.user = user;

		setIconImage(gallery.getSystemIcon());
		setTitle(portalTitle + " | " + Utility.APP_TITLE);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(windowWidth, windowHeight);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBackground(Gallery.GRAY);
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		mainPanel = new RoundedPanel(Gallery.WHITE);
		sl_contentPane.putConstraint(SpringLayout.NORTH, mainPanel, 15, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, mainPanel, 15, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, mainPanel, -15, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, mainPanel, -15, SpringLayout.EAST, contentPane);
		contentPane.add(mainPanel);
		SpringLayout sl_mainPanel = new SpringLayout();
		mainPanel.setLayout(sl_mainPanel);
		
		lblGreetingMessage = new JLabel(String.format(
			"<html><center>[%s] %s<br><small>Please select which system would you like to use today.</small></center></html>", 
			user[4].toString(), user[1].toString()));
		lblGreetingMessage.setFont(gallery.getFont(20f));
		lblGreetingMessage.setHorizontalAlignment(SwingConstants.CENTER);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, lblGreetingMessage, 15, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, lblGreetingMessage, 15, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, lblGreetingMessage, -15, SpringLayout.EAST, mainPanel);
		mainPanel.add(lblGreetingMessage);
		
		lblPosButton = new JLabel("<html>Point of Sales<br><small>Start business transactions</small></html>");
		lblPosButton.setName("primary");
		gallery.styleLabelToButton(lblPosButton, 20f, "main-pos.png", 64, 0, 0);
		lblPosButton.setHorizontalAlignment(SwingConstants.CENTER);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, lblPosButton, 15, SpringLayout.SOUTH, lblGreetingMessage);
		sl_mainPanel.putConstraint(SpringLayout.WEST, lblPosButton, 30, SpringLayout.WEST, lblGreetingMessage);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, lblPosButton, 90, SpringLayout.SOUTH, lblGreetingMessage);
		sl_mainPanel.putConstraint(SpringLayout.EAST, lblPosButton, -30, SpringLayout.EAST, lblGreetingMessage);
		mainPanel.add(lblPosButton);
		
		lblInventoryButton = new JLabel("<html>Inventory System<br><small>Manage products and suppliers</small></html>");
		lblInventoryButton.setName("primary");
		gallery.styleLabelToButton(lblInventoryButton, 20f, "main-inventory.png", 64, 0, 0);
		lblInventoryButton.setHorizontalAlignment(SwingConstants.CENTER);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, lblInventoryButton, 15, SpringLayout.SOUTH, lblPosButton);
		sl_mainPanel.putConstraint(SpringLayout.WEST, lblInventoryButton, 0, SpringLayout.WEST, lblPosButton);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, lblInventoryButton, 90, SpringLayout.SOUTH, lblPosButton);
		sl_mainPanel.putConstraint(SpringLayout.EAST, lblInventoryButton, 0, SpringLayout.EAST, lblPosButton);
		mainPanel.add(lblInventoryButton);
		

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				logger.addLog(String.format("User %s has been closed the portal.", user[0].toString()));

				new Main();
				dispose();
			}
		});
		lblPosButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblPosButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblPosButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				logger.addLog(String.format("User %s has been opened the Point of Sales.", user[0].toString()));

				new POS(user);
				dispose();
			}
		});
		lblInventoryButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblInventoryButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblInventoryButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				logger.addLog(String.format("User %s has been opened the Inventory System.", user[0].toString()));

				new Inventory(user);
				dispose();
			}
		});

		setLocationRelativeTo(null);
		setVisible(true);
	}
}
