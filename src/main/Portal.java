package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.Gallery;
import utils.Utility;

import javax.swing.SpringLayout;
import javax.swing.JLabel;

/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
@SuppressWarnings("serial")
public class Portal extends JFrame {
	
	private final String portalTitle = "Portal";
	private final String greetingMessage = "Welcome, ";
	
	private int windowWidth = 450;
	private int windowHeight = 350;
	
	private Gallery gallery;
	private Object[] user;

	private JPanel contentPane;
	private JLabel lblGreetingMessage;

	
	public Portal(Object[] user) {
		gallery = Gallery.getInstance();
		this.user = user;

		setTitle(portalTitle + " | " + Utility.APP_TITLE);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(windowWidth, windowHeight);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		lblGreetingMessage = new JLabel(greetingMessage + user[1].toString());
		lblGreetingMessage.setFont(gallery.getFont(15f));
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblGreetingMessage, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblGreetingMessage, 10, SpringLayout.WEST, contentPane);
		contentPane.add(lblGreetingMessage);

		setLocationRelativeTo(null);
		setVisible(true);
	}
}
