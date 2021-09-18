package inventory;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.Database;
import utils.Gallery;
import javax.swing.SpringLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;


/**
 * To be done by: Sebastian Garcia
 */
@SuppressWarnings("serial")
public class Inventory extends JFrame {
	
	// private Gallery gallery; uncomment this after designing the UI
	private JPanel mainPanel, navigationalPanel, dashboardPanel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inventory frame = new Inventory();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Inventory() {
		
		// gallery = new Gallery();
		// 			After designing, change all Panel to Rounded Panel like this:
		// panelVariableExample = gallery.new RoundedPanel(Gallery.WHITE);
		// 			And importantly set the opaque to false
		// dashboardPanel.setOpaque(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		mainPanel = new JPanel();
		mainPanel.setBackground(Gallery.BLACK);
		setContentPane(mainPanel);
		SpringLayout sl_mainPanel = new SpringLayout();
		mainPanel.setLayout(sl_mainPanel);
		setLocationRelativeTo(null); 
		
		navigationalPanel = new JPanel();
		navigationalPanel.setBackground(Gallery.BLUE);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, navigationalPanel, 0, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, navigationalPanel, 0, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, navigationalPanel, 0, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, navigationalPanel, 162, SpringLayout.WEST, mainPanel);
		mainPanel.add(navigationalPanel);
		
		dashboardPanel = new JPanel();
		dashboardPanel.setBackground(Gallery.WHITE);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, dashboardPanel, 15, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, dashboardPanel, 15, SpringLayout.EAST, navigationalPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, dashboardPanel, -15, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, dashboardPanel, -15, SpringLayout.EAST, mainPanel);
		mainPanel.add(dashboardPanel);
		
	}
}
