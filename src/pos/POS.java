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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Dimension;
import java.awt.event.WindowStateListener;
import java.awt.event.WindowEvent;


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
		 * panelVariableExample = utility.new RoundedPanel(Gallery.WHITE);
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
		
//		navigationPanel = utility.new RoundedPanel(Gallery.BLUE);
		navigationPanel = new JPanel();
		navigationPanel.setBackground(Gallery.BLUE);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, navigationPanel, height / 8, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, navigationPanel, -30, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, navigationPanel, -height / 8, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, navigationPanel, 50, SpringLayout.WEST, mainPanel);
		mainPanel.add(navigationPanel);

//		posPanel = utility.new RoundedPanel(Gallery.GRAY);
		posPanel = new JPanel();
		posPanel.setBackground(Gallery.GRAY);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, posPanel, 15, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, posPanel, 15, SpringLayout.EAST, navigationPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, posPanel, -15, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, posPanel, -15, SpringLayout.EAST, mainPanel);
		mainPanel.add(posPanel);
		
		
		
		
		

		

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
