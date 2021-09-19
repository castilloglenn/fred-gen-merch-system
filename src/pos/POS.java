package pos;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import utils.Database;
import utils.Gallery;
import utils.Utility;
import utils.RoundedPanel;
import utils.VerticalLabelUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Dimension;
import java.awt.event.WindowStateListener;
import java.awt.event.WindowEvent;
import java.awt.CardLayout;
import javax.swing.JLabel;


/**
 * To be done by: Glenn
 */
@SuppressWarnings("serial")
public class POS extends JFrame {

	private JPanel mainPanel, navigationPanel, posPanel;
	private SpringLayout sl_mainPanel;
	
	private int height = 600;
	private int width = 1000;
	private int heightDiff = 0;

	private Gallery gallery;
	private Utility utility;
	private JLabel lblDashboardNav;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					POS frame = new POS();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public POS() {
		gallery = new Gallery();
		utility = new Utility();
		
		/**
		 *  	After designing, change all Panel to Rounded Panel like this:
		 * panelVariableExample = new RoundedPanel(Gallery.BLUE);
		 *     
		 * 	 	To set the default font, first set the size then set the font:
		 * utility.setFontSize(20f);
		 * lblNewLabel.setFont(gallery.font);
		 */
		
		setMinimumSize(new Dimension(640, 480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, width, height);
		
		mainPanel = new JPanel();
		setContentPane(mainPanel);
		mainPanel.setBackground(Gallery.BLACK);
		sl_mainPanel = new SpringLayout();
		mainPanel.setLayout(sl_mainPanel);
		
		navigationPanel = new RoundedPanel(Gallery.BLUE);
//		navigationPanel = new JPanel();
//		navigationPanel.setBackground(Gallery.BLUE);
		sl_mainPanel.putConstraint(SpringLayout.EAST, navigationPanel, 60, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, navigationPanel, height / 8, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, navigationPanel, -30, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, navigationPanel, -height / 8, SpringLayout.SOUTH, mainPanel);
		mainPanel.add(navigationPanel);

		posPanel = new RoundedPanel(Gallery.GRAY);
//		posPanel = new JPanel();
//		posPanel.setBackground(Gallery.GRAY);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, posPanel, 15, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, posPanel, 15, SpringLayout.EAST, navigationPanel);
		SpringLayout sl_navigationPanel = new SpringLayout();
		navigationPanel.setLayout(sl_navigationPanel);
		
		lblDashboardNav = new JLabel("POS");
		lblDashboardNav.setFont(utility.getFont(25f));
		lblDashboardNav.setForeground(Color.WHITE);
		sl_navigationPanel.putConstraint(SpringLayout.NORTH, lblDashboardNav, 10, SpringLayout.NORTH, navigationPanel);
		sl_navigationPanel.putConstraint(SpringLayout.WEST, lblDashboardNav, 40, SpringLayout.WEST, navigationPanel);
		lblDashboardNav.setUI(new VerticalLabelUI(false));
		lblDashboardNav.setIcon(utility.getImage("pos.png", 25));
		sl_navigationPanel.putConstraint(SpringLayout.EAST, lblDashboardNav, -10, SpringLayout.EAST, navigationPanel);
		navigationPanel.add(lblDashboardNav);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, posPanel, -15, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, posPanel, -15, SpringLayout.EAST, mainPanel);
		mainPanel.add(posPanel);
		SpringLayout sl_posPanel = new SpringLayout();
		posPanel.setLayout(sl_posPanel);
		
		
		
		
		
		
		

		

		setLocationRelativeTo(null);
	}
	
	public void setHeightDiff() {
		heightDiff = mainPanel.getHeight() / 8;
		if (heightDiff > 0) {
			sl_mainPanel.putConstraint(SpringLayout.NORTH, navigationPanel, heightDiff, SpringLayout.NORTH, mainPanel);
			sl_mainPanel.putConstraint(SpringLayout.SOUTH, navigationPanel, -heightDiff, SpringLayout.SOUTH, mainPanel);
		}
	}
	
	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		setHeightDiff();
	}
}
