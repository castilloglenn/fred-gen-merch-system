package inventory;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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


/**
 * To be done by: Sebastian Garcia
 */
@SuppressWarnings("serial")
public class Inventory extends JFrame {
	
	private Gallery gallery;
	private Utility utility;
	
	private JPanel mainPanel, navigationalPanel, dashboardPanel;
	private JLabel lblDashboard;
	private JLabel lblSupplier;
	private JLabel lblProduct;
	

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
		 *    	And importantly set the opaque to false
		 * dashboardPanel.setOpaque(false);
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
		navigationalPanel.setBackground(Gallery.BLUE);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, navigationalPanel, 0, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, navigationalPanel, 0, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, navigationalPanel, 0, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, navigationalPanel, 162, SpringLayout.WEST, mainPanel);
		mainPanel.add(navigationalPanel);
		
		dashboardPanel = new JPanel();
		dashboardPanel.setBackground(Gallery.GRAY);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, dashboardPanel, 15, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, dashboardPanel, 15, SpringLayout.EAST, navigationalPanel);
		SpringLayout sl_navigationalPanel = new SpringLayout();
		navigationalPanel.setLayout(sl_navigationalPanel);
		
		lblDashboard = new JLabel("Dashboard");
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, lblDashboard, 104, SpringLayout.NORTH, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, lblDashboard, 0, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.SOUTH, lblDashboard, 138, SpringLayout.NORTH, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, lblDashboard, 1, SpringLayout.EAST, navigationalPanel);
		lblDashboard.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblDashboard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblDashboard.setBackground(Gallery.WHITE);
				lblDashboard.setOpaque(true);
				lblDashboard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				lblDashboard.setBackground(Gallery.BLUE);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("ge");
			}
		});
		lblDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		navigationalPanel.add(lblDashboard);
		
		lblSupplier = new JLabel("Supplier");
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, lblSupplier, 18, SpringLayout.SOUTH, lblDashboard);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, lblSupplier, 0, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.SOUTH, lblSupplier, -371, SpringLayout.SOUTH, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, lblSupplier, -10, SpringLayout.EAST, navigationalPanel);
		lblSupplier.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblSupplier.setBackground(Gallery.WHITE);
				lblSupplier.setOpaque(true);
			}
		});
		lblSupplier.setHorizontalAlignment(SwingConstants.CENTER);
		lblSupplier.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		navigationalPanel.add(lblSupplier);
		
		lblProduct = new JLabel("Product");
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, lblProduct, 19, SpringLayout.SOUTH, lblSupplier);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, lblProduct, 10, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.SOUTH, lblProduct, -318, SpringLayout.SOUTH, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, lblProduct, -10, SpringLayout.EAST, navigationalPanel);
		lblProduct.setHorizontalAlignment(SwingConstants.CENTER);
		lblProduct.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		navigationalPanel.add(lblProduct);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, dashboardPanel, -15, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, dashboardPanel, -15, SpringLayout.EAST, mainPanel);
		mainPanel.add(dashboardPanel);
		SpringLayout sl_dashboardPanel = new SpringLayout();
		dashboardPanel.setLayout(sl_dashboardPanel);
	}
}
