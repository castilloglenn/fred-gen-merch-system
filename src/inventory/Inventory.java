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
	private JPanel supplierPanel;
	private JPanel productPanel;
	private JTextField txtSupplierID;
	private JTextField txtSupplierName;
	private JTextField txtSupplierContactNumber;
	private JTextField txtSupplierAddress;
	private JLabel btnSupplierAdd;
	private JLabel btnSupplierUpdate;
	private JLabel btnSupplierDelete;
	
	private CardLayout cardLayout;



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
		sl_navigationalPanel.putConstraint(SpringLayout.SOUTH, lblDashboard, 138, SpringLayout.NORTH, navigationalPanel);
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
		sl_navigationalPanel.putConstraint(SpringLayout.SOUTH, lblSupplier, -371, SpringLayout.SOUTH, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, lblSupplier, 1, SpringLayout.EAST, navigationalPanel);
		lblSupplier.setIcon(utility.getImage("supplier.png", 15));
		lblSupplier.setHorizontalAlignment(SwingConstants.CENTER);
		navigationalPanel.add(lblSupplier);
		
		lblProduct = new JLabel("Product");
		lblProduct.setForeground(new Color(237, 237, 233));
		lblProduct.setFont(utility.getFont(20f));
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, lblProduct, 19, SpringLayout.SOUTH, lblSupplier);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, lblProduct, 0, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.SOUTH, lblProduct, -318, SpringLayout.SOUTH, navigationalPanel);
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
		supplierPanel.setVisible(false);
		supplierPanel.setBackground(Gallery.GRAY);
		displayPanel.add(supplierPanel, "supplier");
		SpringLayout sl_supplierPanel = new SpringLayout();
		supplierPanel.setLayout(sl_supplierPanel);
		
		JDesktopPane supplierFormPane = new JDesktopPane();
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, supplierFormPane, 52, SpringLayout.NORTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, supplierFormPane, 10, SpringLayout.WEST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, supplierFormPane, 206, SpringLayout.NORTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, supplierFormPane, -10, SpringLayout.EAST, supplierPanel);
		supplierFormPane.setBackground(Color.WHITE);
		supplierPanel.add(supplierFormPane);
		
		JLabel lblSupplierID = new JLabel("Supplier ID");
		lblSupplierID.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblSupplierID.setBounds(32, 11, 81, 31);
		supplierFormPane.add(lblSupplierID);
		
		txtSupplierID = new JTextField();
		txtSupplierID.setEditable(false);
		txtSupplierID.setFont(new Font("Times New Roman", Font.BOLD, 15));
		txtSupplierID.setBounds(123, 13, 228, 26);
		supplierFormPane.add(txtSupplierID);
		txtSupplierID.setColumns(10);
		
		JLabel lblSupplierName = new JLabel("Supplier Name");
		lblSupplierName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblSupplierName.setBounds(20, 53, 96, 31);
		supplierFormPane.add(lblSupplierName);
		
		txtSupplierName = new JTextField();
		txtSupplierName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		txtSupplierName.setColumns(10);
		txtSupplierName.setBounds(121, 59, 230, 26);
		supplierFormPane.add(txtSupplierName);
		
		JLabel lblSupplierContactNumber = new JLabel("Contact Number");
		lblSupplierContactNumber.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblSupplierContactNumber.setBounds(407, 11, 115, 31);
		supplierFormPane.add(lblSupplierContactNumber);
		
		txtSupplierContactNumber = new JTextField();
		txtSupplierContactNumber.setFont(new Font("Times New Roman", Font.BOLD, 15));
		txtSupplierContactNumber.setColumns(10);
		txtSupplierContactNumber.setBounds(532, 11, 228, 26);
		supplierFormPane.add(txtSupplierContactNumber);
		
		txtSupplierAddress = new JTextField();
		txtSupplierAddress.setFont(new Font("Times New Roman", Font.BOLD, 15));
		txtSupplierAddress.setColumns(10);
		txtSupplierAddress.setBounds(532, 59, 228, 26);
		supplierFormPane.add(txtSupplierAddress);
		
		JLabel lblSupplierAddress = new JLabel("Address");
		lblSupplierAddress.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblSupplierAddress.setBounds(467, 53, 55, 31);
		supplierFormPane.add(lblSupplierAddress);
		
		btnSupplierAdd = new JLabel("Add");
		btnSupplierAdd.setHorizontalAlignment(SwingConstants.CENTER);
		btnSupplierAdd.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnSupplierAdd.setBounds(504, 117, 75, 26);
		supplierFormPane.add(btnSupplierAdd);
		
		btnSupplierUpdate = new JLabel("Update");
		btnSupplierUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		btnSupplierUpdate.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnSupplierUpdate.setBounds(599, 117, 75, 26);
		supplierFormPane.add(btnSupplierUpdate);
		
		btnSupplierDelete = new JLabel("Delete");
		btnSupplierDelete.setHorizontalAlignment(SwingConstants.CENTER);
		btnSupplierDelete.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnSupplierDelete.setBounds(685, 117, 75, 26);
		supplierFormPane.add(btnSupplierDelete);
		
		productPanel = new JPanel();
		productPanel.setBackground(Color.CYAN);
		displayPanel.add(productPanel, "product");
		
		
		
		
		
		
		
		
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
		
		btnSupplierAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				 mouseEnter2(btnSupplierAdd); 
			}
			@Override
			public void mouseExited(MouseEvent e) {
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
		
	public void mouseEnter2(JLabel label) {
		label.setBackground(Gallery.BLUE);
		label.setOpaque(true);
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public void mouseExit(JLabel label) {
		label.setBackground(Gallery.BLUE);
	}
	
	
}
