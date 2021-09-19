package inventory;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.Database;
import utils.Gallery;
import utils.Utility;

import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Image;


/**
 * To be done by: Sebastian Garcia
 */
@SuppressWarnings("serial")
public class Inventory extends JFrame {
	
	private Gallery gallery;
	private Utility utility;
	
	private JPanel mainPanel, navigationalPanel, displayPanel, dashboardPanel;
	private JLabel lblDashboard, lblSupplier, lblProduct;



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


	public Inventory() {
		
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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		mainPanel = new JPanel();
		mainPanel.setBackground(Gallery.BLACK);
		setContentPane(mainPanel);
		SpringLayout sl_mainPanel = new SpringLayout();
		mainPanel.setLayout(sl_mainPanel);
		setLocationRelativeTo(null); 
		
		
		navigationalPanel = new JPanel();
		//navigationalPanel = utility.new RoundedPanel(Gallery.BLUE);
		navigationalPanel.setBackground(Gallery.BLUE);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, navigationalPanel, 0, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, navigationalPanel, 0, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, navigationalPanel, 0, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, navigationalPanel, 162, SpringLayout.WEST, mainPanel);
		mainPanel.add(navigationalPanel);
		
		displayPanel = new JPanel();
		//dashboardPanel = utility.new RoundedPanel(Gallery.BLUE);
		displayPanel.setBackground(Gallery.GRAY);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, displayPanel, 15, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, displayPanel, 15, SpringLayout.EAST, navigationalPanel);
		SpringLayout sl_navigationalPanel = new SpringLayout();
		navigationalPanel.setLayout(sl_navigationalPanel);
		
		
		lblDashboard = new JLabel("Dashboard");
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, lblDashboard, 104, SpringLayout.NORTH, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, lblDashboard, 0, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.SOUTH, lblDashboard, 138, SpringLayout.NORTH, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, lblDashboard, 1, SpringLayout.EAST, navigationalPanel);
		lblDashboard.setIcon(utility.getImage("dashboard.png"));
		
		lblDashboard.setFont(utility.getFont(20f));
		lblDashboard.setForeground(new Color(237, 237, 233));
		lblDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		navigationalPanel.add(lblDashboard);
		
		lblSupplier = new JLabel("Supplier");
		lblSupplier.setForeground(Color.WHITE);
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, lblSupplier, 18, SpringLayout.SOUTH, lblDashboard);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, lblSupplier, 0, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.SOUTH, lblSupplier, -371, SpringLayout.SOUTH, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, lblSupplier, 1, SpringLayout.EAST, navigationalPanel);
		lblSupplier.setIcon(utility.getImage("supplier.png"));
		lblSupplier.setHorizontalAlignment(SwingConstants.CENTER);
		lblSupplier.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		navigationalPanel.add(lblSupplier);
		
		lblProduct = new JLabel("Product");
		lblProduct.setForeground(Color.WHITE);
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, lblProduct, 19, SpringLayout.SOUTH, lblSupplier);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, lblProduct, 0, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.SOUTH, lblProduct, -318, SpringLayout.SOUTH, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, lblProduct, 1, SpringLayout.EAST, navigationalPanel);
		lblProduct.setIcon(utility.getImage("product.png"));
		lblProduct.setHorizontalAlignment(SwingConstants.CENTER);
		lblProduct.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		navigationalPanel.add(lblProduct);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, displayPanel, -15, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, displayPanel, -15, SpringLayout.EAST, mainPanel);
		mainPanel.add(displayPanel);
		SpringLayout sl_displayPanel = new SpringLayout();
		displayPanel.setLayout(sl_displayPanel);
		
		dashboardPanel = new JPanel();
		dashboardPanel.setVisible(false);
		dashboardPanel.setBackground(Color.GREEN);
		sl_displayPanel.putConstraint(SpringLayout.NORTH, dashboardPanel, 0, SpringLayout.NORTH, displayPanel);
		sl_displayPanel.putConstraint(SpringLayout.WEST, dashboardPanel, 0, SpringLayout.WEST, displayPanel);
		sl_displayPanel.putConstraint(SpringLayout.SOUTH, dashboardPanel, 0, SpringLayout.SOUTH, displayPanel);
		sl_displayPanel.putConstraint(SpringLayout.EAST, dashboardPanel, 1, SpringLayout.EAST, displayPanel);
		displayPanel.add(dashboardPanel);
		dashboardPanel.setLayout(new SpringLayout());
		
		
		
		
		
		
		
		
		// NOTE: Please put all mouse listeners here at the end
		lblDashboard.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { mouseEnter(lblDashboard); }
			@Override public void mouseExited(MouseEvent e) { mouseExit(lblDashboard); }
			
			@Override
			public void mouseClicked(MouseEvent e) {
				dashboardPanel.setVisible(true);
			}
		});
		lblSupplier.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { mouseEnter(lblSupplier); }
			@Override public void mouseExited(MouseEvent e) { mouseExit(lblSupplier); }
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("lblSupplier");
			}
		});
		lblProduct.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) {mouseEnter(lblProduct);	}
			@Override public void mouseExited(MouseEvent e) { mouseExit(lblProduct); }

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("lblProduct");
			}
		});
	}
	
	/**
	 *  Can be used to other UI's, if so, transfer to Utility class.
	 */
	public void mouseEnter(JLabel label) {
		label.setBackground(Gallery.WHITE);
		label.setOpaque(true);
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public void mouseExit(JLabel label) {
		label.setBackground(Gallery.BLUE);
	}
	
	public void buttonIcons() {
		
	}
}
