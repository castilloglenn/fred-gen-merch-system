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
import javax.swing.table.DefaultTableModel;


/**
 * To be done by: Sebastian Garcia
 */
@SuppressWarnings("serial")
public class Inventory extends JFrame {
	
	private Gallery gallery;
	private Utility utility;
	
	private JPanel mainPanel, navigationalPanel, displayPanel;
	private JLabel btnDashboard, btnSupplier, btnProduct;
	private JPanel dashboardPanel;
	
	private CardLayout cardLayout;
	private JPanel supplierPanel;
	private JDesktopPane supplierFormPane;
	private JLabel lblSupplierList;
	private JTable tblSupplierList;
	private JLabel lblSupplierID;
	private JLabel lblSupplierName;
	private JLabel lblSupplierContactNumber;
	private JLabel lblSupplierAddress;
	private JTextField textField;
	private JTextField txtSupplierName;
	private JTextField txtSupplierAddress;
	private JTextField txtSupplierConatactNumber;
	private JLabel btnSupplierAdd;
	private JLabel btnSupplierUpdate;
	private JLabel btnSupplierDelete;



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
		
		
		btnDashboard = new JLabel("Dashboard");
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, btnDashboard, 104, SpringLayout.NORTH, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, btnDashboard, 0, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, btnDashboard, 1, SpringLayout.EAST, navigationalPanel);
		btnDashboard.setIcon(gallery.getImage("dashboard.png", 15));
		btnDashboard.setFont(gallery.getFont(20f));
		btnDashboard.setForeground(new Color(237, 237, 233));
		btnDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		navigationalPanel.add(btnDashboard);
		
		btnSupplier = new JLabel("Supplier");
		btnSupplier.setFont(gallery.getFont(20f));
		btnSupplier.setForeground(new Color(237, 237, 233));
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, btnSupplier, 18, SpringLayout.SOUTH, btnDashboard);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, btnSupplier, 0, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, btnSupplier, 1, SpringLayout.EAST, navigationalPanel);
		btnSupplier.setIcon(gallery.getImage("supplier.png", 15));
		btnSupplier.setHorizontalAlignment(SwingConstants.CENTER);
		navigationalPanel.add(btnSupplier);
		
		btnProduct = new JLabel("Product");
		btnProduct.setForeground(new Color(237, 237, 233));
		btnProduct.setFont(gallery.getFont(20f));
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, btnProduct, 19, SpringLayout.SOUTH, btnSupplier);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, btnProduct, 0, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, btnProduct, 1, SpringLayout.EAST, navigationalPanel);
		btnProduct.setIcon(gallery.getImage("product.png", 15));
		btnProduct.setHorizontalAlignment(SwingConstants.CENTER);
		navigationalPanel.add(btnProduct);
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
		
		lblSupplierID = new JLabel("Supplier ID");
		sl_supplierFormPane.putConstraint(SpringLayout.NORTH, lblSupplierID, 30, SpringLayout.NORTH, supplierFormPane);
		sl_supplierFormPane.putConstraint(SpringLayout.WEST, lblSupplierID, 14, SpringLayout.WEST, supplierFormPane);
		lblSupplierID.setFont(new Font("Times New Roman", Font.BOLD, 15));
		supplierFormPane.add(lblSupplierID);
		
		lblSupplierName = new JLabel("Supplier Name");
		sl_supplierFormPane.putConstraint(SpringLayout.NORTH, lblSupplierName, 79, SpringLayout.NORTH, supplierFormPane);
		sl_supplierFormPane.putConstraint(SpringLayout.WEST, lblSupplierName, 10, SpringLayout.WEST, supplierFormPane);
		sl_supplierFormPane.putConstraint(SpringLayout.SOUTH, lblSupplierID, -24, SpringLayout.NORTH, lblSupplierName);
		sl_supplierFormPane.putConstraint(SpringLayout.EAST, lblSupplierID, 0, SpringLayout.EAST, lblSupplierName);
		lblSupplierName.setFont(new Font("Times New Roman", Font.BOLD, 15));
		supplierFormPane.add(lblSupplierName);
		
		lblSupplierContactNumber = new JLabel("Contact Number");
		sl_supplierFormPane.putConstraint(SpringLayout.NORTH, lblSupplierContactNumber, 0, SpringLayout.NORTH, lblSupplierID);
		lblSupplierContactNumber.setFont(new Font("Times New Roman", Font.BOLD, 15));
		supplierFormPane.add(lblSupplierContactNumber);
		
		lblSupplierAddress = new JLabel("Address");
		sl_supplierFormPane.putConstraint(SpringLayout.EAST, lblSupplierContactNumber, 0, SpringLayout.EAST, lblSupplierAddress);
		sl_supplierFormPane.putConstraint(SpringLayout.NORTH, lblSupplierAddress, 0, SpringLayout.NORTH, lblSupplierName);
		lblSupplierAddress.setFont(new Font("Times New Roman", Font.BOLD, 15));
		supplierFormPane.add(lblSupplierAddress);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setFont(new Font("Times New Roman", Font.BOLD, 15));
		sl_supplierFormPane.putConstraint(SpringLayout.NORTH, textField, -20, SpringLayout.SOUTH, lblSupplierID);
		sl_supplierFormPane.putConstraint(SpringLayout.WEST, textField, 6, SpringLayout.EAST, lblSupplierID);
		sl_supplierFormPane.putConstraint(SpringLayout.SOUTH, textField, 0, SpringLayout.SOUTH, lblSupplierID);
		sl_supplierFormPane.putConstraint(SpringLayout.EAST, textField, 217, SpringLayout.EAST, lblSupplierID);
		supplierFormPane.add(textField);
		textField.setColumns(10);
		
		txtSupplierName = new JTextField();
		txtSupplierName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		sl_supplierFormPane.putConstraint(SpringLayout.NORTH, txtSupplierName, 0, SpringLayout.NORTH, lblSupplierName);
		sl_supplierFormPane.putConstraint(SpringLayout.WEST, txtSupplierName, 6, SpringLayout.EAST, lblSupplierName);
		sl_supplierFormPane.putConstraint(SpringLayout.SOUTH, txtSupplierName, 20, SpringLayout.NORTH, lblSupplierName);
		sl_supplierFormPane.putConstraint(SpringLayout.EAST, txtSupplierName, 0, SpringLayout.EAST, textField);
		txtSupplierName.setColumns(10);
		supplierFormPane.add(txtSupplierName);
		
		txtSupplierAddress = new JTextField();
		sl_supplierFormPane.putConstraint(SpringLayout.WEST, txtSupplierAddress, 496, SpringLayout.WEST, supplierFormPane);
		sl_supplierFormPane.putConstraint(SpringLayout.EAST, txtSupplierAddress, -27, SpringLayout.EAST, supplierFormPane);
		sl_supplierFormPane.putConstraint(SpringLayout.EAST, lblSupplierAddress, -14, SpringLayout.WEST, txtSupplierAddress);
		sl_supplierFormPane.putConstraint(SpringLayout.NORTH, txtSupplierAddress, 79, SpringLayout.NORTH, supplierFormPane);
		txtSupplierAddress.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtSupplierAddress.setColumns(10);
		supplierFormPane.add(txtSupplierAddress);
		
		txtSupplierConatactNumber = new JTextField();
		sl_supplierFormPane.putConstraint(SpringLayout.WEST, txtSupplierConatactNumber, 17, SpringLayout.EAST, lblSupplierContactNumber);
		sl_supplierFormPane.putConstraint(SpringLayout.SOUTH, txtSupplierConatactNumber, -25, SpringLayout.NORTH, txtSupplierAddress);
		sl_supplierFormPane.putConstraint(SpringLayout.EAST, txtSupplierConatactNumber, -27, SpringLayout.EAST, supplierFormPane);
		txtSupplierConatactNumber.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtSupplierConatactNumber.setColumns(10);
		supplierFormPane.add(txtSupplierConatactNumber);
		
		btnSupplierAdd = new JLabel("Add");
		btnSupplierAdd.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnSupplierAdd.setBackground(Gallery.GRAY);
		btnSupplierAdd.setForeground(Gallery.BLUE);
		btnSupplierAdd.setOpaque(true);
		sl_supplierFormPane.putConstraint(SpringLayout.WEST, btnSupplierAdd, 58, SpringLayout.WEST, lblSupplierAddress);
		btnSupplierAdd.setHorizontalAlignment(SwingConstants.CENTER);
		btnSupplierAdd.setFont(new Font("Times New Roman", Font.BOLD, 15));
		supplierFormPane.add(btnSupplierAdd);
		
		btnSupplierUpdate = new JLabel("Update");
		btnSupplierUpdate.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnSupplierUpdate.setBackground(Gallery.GRAY);
		btnSupplierUpdate.setForeground(Gallery.BLUE);
		btnSupplierUpdate.setOpaque(true);
		sl_supplierFormPane.putConstraint(SpringLayout.NORTH, btnSupplierUpdate, 72, SpringLayout.SOUTH, txtSupplierAddress);
		sl_supplierFormPane.putConstraint(SpringLayout.WEST, btnSupplierUpdate, 571, SpringLayout.WEST, supplierFormPane);
		sl_supplierFormPane.putConstraint(SpringLayout.SOUTH, btnSupplierUpdate, -10, SpringLayout.SOUTH, supplierFormPane);
		sl_supplierFormPane.putConstraint(SpringLayout.NORTH, btnSupplierAdd, 0, SpringLayout.NORTH, btnSupplierUpdate);
		sl_supplierFormPane.putConstraint(SpringLayout.EAST, btnSupplierAdd, -6, SpringLayout.WEST, btnSupplierUpdate);
		btnSupplierUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		btnSupplierUpdate.setFont(new Font("Times New Roman", Font.BOLD, 15));
		supplierFormPane.add(btnSupplierUpdate);
		
		btnSupplierDelete = new JLabel("Delete");
		btnSupplierDelete.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnSupplierDelete.setBackground(Gallery.GRAY);
		btnSupplierDelete.setForeground(Gallery.BLUE);
		sl_supplierFormPane.putConstraint(SpringLayout.SOUTH, btnSupplierDelete, -10, SpringLayout.SOUTH, supplierFormPane);
		sl_supplierFormPane.putConstraint(SpringLayout.EAST, btnSupplierUpdate, -6, SpringLayout.WEST, btnSupplierDelete);
		sl_supplierFormPane.putConstraint(SpringLayout.WEST, btnSupplierDelete, 648, SpringLayout.WEST, supplierFormPane);
		sl_supplierFormPane.putConstraint(SpringLayout.EAST, btnSupplierDelete, 0, SpringLayout.EAST, txtSupplierAddress);
		btnSupplierDelete.setHorizontalAlignment(SwingConstants.CENTER);
		btnSupplierDelete.setFont(new Font("Times New Roman", Font.BOLD, 15));
		supplierFormPane.add(btnSupplierDelete);
		supplierPanel.add(supplierPane);
		
		lblSupplierList = new JLabel("Supplier List");
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, lblSupplierList, 264, SpringLayout.NORTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, lblSupplierList, 15, SpringLayout.WEST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, lblSupplierList, -6, SpringLayout.NORTH, supplierPane);
		
		tblSupplierList = new JTable();
		tblSupplierList.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
			},
			new String[] {
				"Supplier ID", "Name", "Contact Number", "Address"
			}
		));
		tblSupplierList.setFont(new Font("Times New Roman", Font.BOLD, 12));
		supplierPane.setViewportView(tblSupplierList);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, lblSupplierList, 129, SpringLayout.WEST, supplierPanel);
		lblSupplierList.setFont(new Font("Times New Roman", Font.BOLD, 20));
		supplierPanel.add(lblSupplierList);
		
		
		
		
		
		
		
		
		// NOTE: Please put all mouse listeners here at the end
		btnDashboard.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { mouseEnter(btnDashboard); }
			@Override public void mouseExited(MouseEvent e) { mouseExit(btnDashboard); }
			
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "dashboard");
			}
		});
		btnSupplier.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { mouseEnter(btnSupplier); }
			@Override public void mouseExited(MouseEvent e) { mouseExit(btnSupplier); }
			
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "supplier");
			}
		});
		btnProduct.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) {mouseEnter(btnProduct);	}
			@Override public void mouseExited(MouseEvent e) { mouseExit(btnProduct); }

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
				mouseExit2(btnSupplierAdd);
			}
		});
		
		btnSupplierUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mouseEnter2(btnSupplierUpdate);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mouseExit2(btnSupplierUpdate);
			}
		});
		
		btnSupplierDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mouseEnter2(btnSupplierDelete);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mouseExit2(btnSupplierDelete);
			}
		});
		
	}
	

	
	/**
	 *  Can be used to other UI's, if so, transfer to Utility class.
	 */
	public void mouseEnter(JLabel label) {
		label.setBackground(Gallery.WHITE);
		label.setForeground(Gallery.BLACK);
		label.setOpaque(true);
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public void mouseEnter2(JLabel label) {
		label.setBackground(Gallery.BLUE);
		label.setForeground(Gallery.WHITE);
		label.setOpaque(true);
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public void mouseExit(JLabel label) {
		label.setBackground(Gallery.BLUE);
		label.setForeground(Gallery.WHITE);
	}
	
	public void mouseExit2(JLabel label) {
		label.setBackground(Gallery.GRAY);
		label.setForeground(Gallery.BLUE);
	}
}
