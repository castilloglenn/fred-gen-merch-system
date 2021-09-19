package inventory;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.Gallery;
import utils.RoundedPanel;
import utils.Database;

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
import java.awt.CardLayout;
import javax.swing.JTable;
import javax.swing.JSeparator;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JLayeredPane;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;


/**
 * To be done by: Sebastian Garcia
 */
@SuppressWarnings("serial")
public class Inventory extends JFrame {
	
	private Gallery gallery;
	private Utility utility;
	
	private JPanel mainPanel, navigationalPanel, displayPanel;
	private JLabel lblDashboard, lblSupplier, lblProduct;
	private JPanel dashboardPanel;
	
	private CardLayout cardLayout;
	private JPanel supplierPanel;
	private JDesktopPane supplierFormPane;
	private JLabel lblSupplierList;
	private JTable tblSupplierList;



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
		 * panelVariableExample = new RoundedPanel(Gallery.BLUE);
		 *     
		 * 	 	To set the default font, first set the size then set the font:
		 * utility.setFontSize(20f);
		 * lblNewLabel.setFont(gallery.font);
		 */
		
		setMinimumSize(new Dimension(640, 480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		mainPanel = new JPanel();
		mainPanel.setBackground(Gallery.BLACK);
		setContentPane(mainPanel);
		SpringLayout sl_mainPanel = new SpringLayout();
		mainPanel.setLayout(sl_mainPanel);
		setLocationRelativeTo(null); 
		
		
		navigationalPanel = new RoundedPanel(Gallery.BLUE);;	
		navigationalPanel.setBackground(Gallery.BLUE);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, navigationalPanel, 0, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, navigationalPanel, 0, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, navigationalPanel, 0, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, navigationalPanel, 162, SpringLayout.WEST, mainPanel);
		mainPanel.add(navigationalPanel);
		
		displayPanel = new JPanel();
		sl_mainPanel.putConstraint(SpringLayout.WEST, displayPanel, 15, SpringLayout.EAST, navigationalPanel);
		displayPanel.setBackground(Color.WHITE);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, displayPanel, 15, SpringLayout.NORTH, mainPanel);
		SpringLayout sl_navigationalPanel = new SpringLayout();
		navigationalPanel.setLayout(sl_navigationalPanel);
		cardLayout = new CardLayout(0, 0);
		
		
		lblDashboard = new JLabel("Dashboard");
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, lblDashboard, 104, SpringLayout.NORTH, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, lblDashboard, 0, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, lblDashboard, 1, SpringLayout.EAST, navigationalPanel);
		lblDashboard.setIcon(utility.getImage("dashboard.png", 15));
		lblDashboard.setFont(utility.getFont(20f));
		lblDashboard.setForeground(new Color(237, 237, 233));
		lblDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		navigationalPanel.add(lblDashboard);
		
		lblSupplier = new JLabel("Supplier");
		lblSupplier.setFont(utility.getFont(20f));
		lblSupplier.setForeground(new Color(237, 237, 233));
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, lblSupplier, 18, SpringLayout.SOUTH, lblDashboard);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, lblSupplier, 0, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, lblSupplier, 1, SpringLayout.EAST, navigationalPanel);
		lblSupplier.setIcon(utility.getImage("supplier.png", 15));
		lblSupplier.setHorizontalAlignment(SwingConstants.CENTER);
		navigationalPanel.add(lblSupplier);
		
		lblProduct = new JLabel("Product");
		lblProduct.setForeground(new Color(237, 237, 233));
		lblProduct.setFont(utility.getFont(20f));
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, lblProduct, 19, SpringLayout.SOUTH, lblSupplier);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, lblProduct, 0, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, lblProduct, 1, SpringLayout.EAST, navigationalPanel);
		lblProduct.setIcon(utility.getImage("product.png", 15));
		lblProduct.setHorizontalAlignment(SwingConstants.CENTER);
		navigationalPanel.add(lblProduct);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, displayPanel, -15, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, displayPanel, -15, SpringLayout.EAST, mainPanel);
		mainPanel.add(displayPanel);
		displayPanel.setLayout(cardLayout);
		
		dashboardPanel = new JPanel();
		dashboardPanel.setVisible(false);
		dashboardPanel.setBackground(Color.BLUE);
		displayPanel.add(dashboardPanel, "dashboard");
		
		supplierPanel = new JPanel();
		supplierPanel.setBackground(Gallery.GRAY);
		displayPanel.add(supplierPanel, "supplier");
		SpringLayout sl_supplierPanel = new SpringLayout();
		supplierPanel.setLayout(sl_supplierPanel);
		
		supplierFormPane = new JDesktopPane();
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, supplierFormPane, 40, SpringLayout.NORTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, supplierFormPane, 15, SpringLayout.WEST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, supplierFormPane, 243, SpringLayout.NORTH, supplierPanel);
		supplierFormPane.setBackground(Gallery.WHITE);
		supplierPanel.add(supplierFormPane);
		
		JScrollPane supplierPane = new JScrollPane();
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, supplierPane, 51, SpringLayout.SOUTH, supplierFormPane);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, supplierPane, 15, SpringLayout.WEST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, supplierPane, -10, SpringLayout.SOUTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, supplierPane, -10, SpringLayout.EAST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, supplierFormPane, 0, SpringLayout.EAST, supplierPane);
		SpringLayout sl_supplierFormPane = new SpringLayout();
		supplierFormPane.setLayout(sl_supplierFormPane);
		supplierPanel.add(supplierPane);
		
		lblSupplierList = new JLabel("Supplier List");
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, lblSupplierList, 264, SpringLayout.NORTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, lblSupplierList, 15, SpringLayout.WEST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, lblSupplierList, -6, SpringLayout.NORTH, supplierPane);
		
		tblSupplierList = new JTable();
		tblSupplierList.setFont(new Font("Times New Roman", Font.BOLD, 12));
		supplierPane.setViewportView(tblSupplierList);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, lblSupplierList, 129, SpringLayout.WEST, supplierPanel);
		lblSupplierList.setFont(new Font("Times New Roman", Font.BOLD, 20));
		supplierPanel.add(lblSupplierList);
		
		
		
		
		
		
		
		
		// NOTE: Please put all mouse listeners here at the end
		lblDashboard.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { mouseEnter(lblDashboard); }
			@Override public void mouseExited(MouseEvent e) { mouseExit(lblDashboard); }
			
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "dashboard");
			}
		});
		lblSupplier.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { mouseEnter(lblSupplier); }
			@Override public void mouseExited(MouseEvent e) { mouseExit(lblSupplier); }
			
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "supplier");
			}
		});
		lblProduct.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) {mouseEnter(lblProduct);	}
			@Override public void mouseExited(MouseEvent e) { mouseExit(lblProduct); }

			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "product");
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
}
