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
import utils.VerticalLabelUI;

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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


/**
 * To be done by: Sebastian Garcia
 * test
 */
@SuppressWarnings("serial")
public class Inventory extends JFrame {
	
	private Gallery gallery;
	private Utility utility;
	
	private JPanel mainPanel, navigationalPanel, displayPanel, supplierSearchPanel,
				supplierPanel;
	private JLabel btnDashboard, btnSupplier, btnProduct, btnSupplierCreate;
	private JPanel dashboardPanel, supplierButtonPanel;
	private JScrollPane supplierPane;
	
	private CardLayout cardLayout;
	private JLabel lblSupplierList;
	private JTable tblSupplierList;
	private JLabel btnSupplierManage;
	private JLabel btnSupplierRemove;
	private JLabel lblSupplierSearchIcon;
	private JTextField txtSupplierSearch;
	private JPanel productPanel;
	private VerticalLabelUI verticalUI;

	private String supplierSearchMessage = "Search for Supplier...";

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
		
		SupplierAdd invSupplierAdd = new SupplierAdd();
		SupplierUpdate invSupplierUpdate = new SupplierUpdate();
		
		/**
		 *  	After designing, change all Panel to Rounded Panel like this:
		 * panelVariableExample = new RoundedPanel(Gallery.BLUE);
		 *     
		 * 	 	To set the default font, first set the size then set the font:
		 * utility.setFontSize(20f);
		 * lblNewLabel.setFont(gallery.font);
		 */
		
		// rotated 90 degrees counter-clockwise
		verticalUI = new VerticalLabelUI(false);
		
		setMinimumSize(new Dimension(990, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		mainPanel = new JPanel();
		mainPanel.setBackground(Gallery.BLACK);
		setContentPane(mainPanel);
		SpringLayout sl_mainPanel = new SpringLayout();
		mainPanel.setLayout(sl_mainPanel);
		setLocationRelativeTo(null); 
		
		
		navigationalPanel = new RoundedPanel(Gallery.BLUE);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, navigationalPanel, 15, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, navigationalPanel, -15, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, navigationalPanel, 390, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, navigationalPanel, 75, SpringLayout.WEST, mainPanel);;	
		navigationalPanel.setBackground(Gallery.BLUE);
		mainPanel.add(navigationalPanel);
		
		displayPanel = new JPanel();
		sl_mainPanel.putConstraint(SpringLayout.WEST, displayPanel, 15, SpringLayout.EAST, navigationalPanel);
		displayPanel.setBackground(Color.BLACK);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, displayPanel, 15, SpringLayout.NORTH, mainPanel);
		SpringLayout sl_navigationalPanel = new SpringLayout();
		navigationalPanel.setLayout(sl_navigationalPanel);
		cardLayout = new CardLayout(0, 0);
		
		btnDashboard = new JLabel("Dashboard");
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, btnDashboard, 20, SpringLayout.NORTH, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, btnDashboard, 25, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, btnDashboard, -10, SpringLayout.EAST, navigationalPanel);
		btnDashboard.setName("primary");
		gallery.styleLabelToButton(btnDashboard, 15f, "dashboard.png", 15, 10, 10);
		btnDashboard.setUI(verticalUI);
		navigationalPanel.add(btnDashboard);
		
		btnSupplier = new JLabel("Supplier");
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, btnSupplier, 10, SpringLayout.SOUTH, btnDashboard);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, btnSupplier, 25, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, btnSupplier, -10, SpringLayout.EAST, navigationalPanel);
		btnSupplier.setName("primary");
		gallery.styleLabelToButton(btnSupplier, 15f, "supplier.png", 15, 10, 10);
		btnSupplier.setUI(verticalUI);
		navigationalPanel.add(btnSupplier);
		
		btnProduct = new JLabel("Product");
		sl_navigationalPanel.putConstraint(SpringLayout.NORTH, btnProduct, 10, SpringLayout.SOUTH, btnSupplier);
		sl_navigationalPanel.putConstraint(SpringLayout.WEST, btnProduct, 25, SpringLayout.WEST, navigationalPanel);
		sl_navigationalPanel.putConstraint(SpringLayout.EAST, btnProduct, -10, SpringLayout.EAST, navigationalPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, displayPanel, -15, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, displayPanel, -15, SpringLayout.EAST, mainPanel);
		btnProduct.setName("primary");
		gallery.styleLabelToButton(btnProduct, 15f, "product.png", 15, 10, 10);
		btnProduct.setUI(verticalUI);
		navigationalPanel.add(btnProduct);
		mainPanel.add(displayPanel);
		displayPanel.setLayout(cardLayout);
		
		dashboardPanel = new RoundedPanel(Gallery.BLUE);
		dashboardPanel.setBackground(Color.BLUE);
		displayPanel.add(dashboardPanel, "dashboard");
		
		supplierPanel = new RoundedPanel(Gallery.GRAY);
		supplierPanel.setBackground(Gallery.GRAY);
		displayPanel.add(supplierPanel, "supplier");
		SpringLayout sl_supplierPanel = new SpringLayout();
		supplierPanel.setLayout(sl_supplierPanel);
		
		supplierPane = new JScrollPane();
		sl_supplierPanel.putConstraint(SpringLayout.WEST, supplierPane, 10, SpringLayout.WEST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, supplierPane, -15, SpringLayout.EAST, supplierPanel);
		supplierPanel.add(supplierPane);
		
		lblSupplierList = new JLabel("Supplier List");
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, lblSupplierList, 10, SpringLayout.NORTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, lblSupplierList, 10, SpringLayout.WEST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, lblSupplierList, -497, SpringLayout.SOUTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, lblSupplierList, -626, SpringLayout.EAST, supplierPanel);
		
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
		lblSupplierList.setFont(gallery.getFont(20f));
		supplierPanel.add(lblSupplierList);
		
		supplierSearchPanel = new RoundedPanel(Gallery.WHITE);
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, supplierPane, 20, SpringLayout.SOUTH, supplierSearchPanel);
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, supplierSearchPanel, 14, SpringLayout.SOUTH, lblSupplierList);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, supplierSearchPanel, 0, SpringLayout.WEST, supplierPane);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, supplierSearchPanel, -447, SpringLayout.SOUTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, supplierSearchPanel, 0, SpringLayout.EAST, supplierPane);
		supplierSearchPanel.setBackground(Color.WHITE);
		supplierPanel.add(supplierSearchPanel);
		SpringLayout sl_supplierSearchPanel = new SpringLayout();
		supplierSearchPanel.setLayout(sl_supplierSearchPanel);
		
		lblSupplierSearchIcon = new JLabel("");
		sl_supplierSearchPanel.putConstraint(SpringLayout.NORTH, lblSupplierSearchIcon, 6, SpringLayout.NORTH, supplierSearchPanel);
		sl_supplierSearchPanel.putConstraint(SpringLayout.WEST, lblSupplierSearchIcon, 10, SpringLayout.WEST, supplierSearchPanel);
		sl_supplierSearchPanel.putConstraint(SpringLayout.SOUTH, lblSupplierSearchIcon, -10, SpringLayout.SOUTH, supplierSearchPanel);
		sl_supplierSearchPanel.putConstraint(SpringLayout.EAST, lblSupplierSearchIcon, 27, SpringLayout.WEST, supplierSearchPanel);
		lblSupplierSearchIcon.setIcon(gallery.getImage("search.png", 15, 15));
		supplierSearchPanel.add(lblSupplierSearchIcon);
		
		txtSupplierSearch = new JTextField();
		gallery.styleTextField(txtSupplierSearch, supplierSearchMessage, 15f);
		sl_supplierSearchPanel.putConstraint(SpringLayout.NORTH, txtSupplierSearch, 5, SpringLayout.NORTH, supplierSearchPanel);
		sl_supplierSearchPanel.putConstraint(SpringLayout.SOUTH, txtSupplierSearch, -5, SpringLayout.SOUTH, supplierSearchPanel);
		sl_supplierSearchPanel.putConstraint(SpringLayout.EAST, txtSupplierSearch, -5, SpringLayout.EAST, supplierSearchPanel);
		txtSupplierSearch.setFont(gallery.getFont(20f));
		txtSupplierSearch.setBorder(null);
		sl_supplierSearchPanel.putConstraint(SpringLayout.WEST, txtSupplierSearch, 3, SpringLayout.EAST, lblSupplierSearchIcon);
		supplierSearchPanel.add(txtSupplierSearch);
		txtSupplierSearch.setColumns(10);
		
		supplierButtonPanel = new RoundedPanel(Gallery.WHITE);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, supplierPane, -17, SpringLayout.NORTH, supplierButtonPanel);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, supplierButtonPanel, -372, SpringLayout.EAST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, supplierButtonPanel, 426, SpringLayout.NORTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, supplierButtonPanel, -23, SpringLayout.SOUTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, supplierButtonPanel, 0, SpringLayout.EAST, supplierPane);
		supplierPanel.add(supplierButtonPanel);
		SpringLayout sl_supplierButtonPanel = new SpringLayout();
		supplierButtonPanel.setLayout(sl_supplierButtonPanel);
		
		btnSupplierCreate = new JLabel("Create");
		sl_supplierButtonPanel.putConstraint(SpringLayout.NORTH, btnSupplierCreate, 22, SpringLayout.NORTH, supplierButtonPanel);
		sl_supplierButtonPanel.putConstraint(SpringLayout.WEST, btnSupplierCreate, 10, SpringLayout.WEST, supplierButtonPanel);
		sl_supplierButtonPanel.putConstraint(SpringLayout.SOUTH, btnSupplierCreate, -23, SpringLayout.SOUTH, supplierButtonPanel);
		btnSupplierCreate.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnSupplierCreate.setHorizontalAlignment(SwingConstants.CENTER);
		btnSupplierCreate.setName("primary");
		gallery.styleLabelToButton(btnSupplierCreate, 15f, 15, 10);
		supplierButtonPanel.add(btnSupplierCreate);
		
		btnSupplierManage = new JLabel("Manage");
		sl_supplierButtonPanel.putConstraint(SpringLayout.NORTH, btnSupplierManage, 22, SpringLayout.NORTH, supplierButtonPanel);
		sl_supplierButtonPanel.putConstraint(SpringLayout.WEST, btnSupplierManage, 125, SpringLayout.WEST, supplierButtonPanel);
		sl_supplierButtonPanel.putConstraint(SpringLayout.SOUTH, btnSupplierManage, -23, SpringLayout.SOUTH, supplierButtonPanel);
		sl_supplierButtonPanel.putConstraint(SpringLayout.EAST, btnSupplierCreate, -6, SpringLayout.WEST, btnSupplierManage);
		btnSupplierManage.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnSupplierManage.setHorizontalAlignment(SwingConstants.CENTER);
		btnSupplierManage.setName("primary");
		gallery.styleLabelToButton(btnSupplierManage, 15f, 15, 10);
		supplierButtonPanel.add(btnSupplierManage);
		
		btnSupplierRemove = new JLabel("Remove");
		sl_supplierButtonPanel.putConstraint(SpringLayout.EAST, btnSupplierManage, -6, SpringLayout.WEST, btnSupplierRemove);
		sl_supplierButtonPanel.putConstraint(SpringLayout.WEST, btnSupplierRemove, 248, SpringLayout.WEST, supplierButtonPanel);
		sl_supplierButtonPanel.putConstraint(SpringLayout.NORTH, btnSupplierRemove, 22, SpringLayout.NORTH, supplierButtonPanel);
		sl_supplierButtonPanel.putConstraint(SpringLayout.SOUTH, btnSupplierRemove, -23, SpringLayout.SOUTH, supplierButtonPanel);
		sl_supplierButtonPanel.putConstraint(SpringLayout.EAST, btnSupplierRemove, -10, SpringLayout.EAST, supplierButtonPanel);
		btnSupplierRemove.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnSupplierRemove.setHorizontalAlignment(SwingConstants.CENTER);
		btnSupplierRemove.setName("danger");
		gallery.styleLabelToButton(btnSupplierRemove, 15f, 15, 10);
		supplierButtonPanel.add(btnSupplierRemove);
		
		productPanel = new RoundedPanel(Gallery.GRAY);
		productPanel.setBackground(Gallery.GRAY);
		displayPanel.add(productPanel, "product");
		productPanel.setLayout(new SpringLayout());
		
		
		
		
		
		
		
		
		// NOTE: Please put all mouse listeners here at the end
		btnDashboard.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(btnDashboard);;}
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(btnDashboard); }
			
			@Override
			public void mouseClicked(MouseEvent e) {cardLayout.show(displayPanel, "dashboard");}

		});
		btnSupplier.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(btnSupplier);}
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(btnSupplier); }
			
			@Override
			public void mouseClicked(MouseEvent e) { cardLayout.show(displayPanel, "supplier");}

		});
		btnProduct.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnProduct);}
			
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(btnProduct);}

			@Override
			public void mouseClicked(MouseEvent e) { cardLayout.show(displayPanel, "product");}
		});
		
		btnSupplierCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnSupplierCreate);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnSupplierCreate);}
			@Override
			public void mouseClicked(MouseEvent e) { 	
				invSupplierAdd.setVisible(true);
				invSupplierAdd.setLocationRelativeTo(null);
			;}
		});
		
		btnSupplierManage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { gallery.buttonHovered(btnSupplierManage);}
			@Override
			public void mouseExited(MouseEvent e) { gallery.buttonNormalized(btnSupplierManage);}
			@Override
			public void mouseClicked(MouseEvent e) {
				invSupplierUpdate.setVisible(true);
				invSupplierUpdate.setLocationRelativeTo(null);
			}
		});
		
		btnSupplierRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnSupplierRemove);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnSupplierRemove);}
		});
		
		txtSupplierSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				gallery.textFieldFocusGained(txtSupplierSearch, supplierSearchMessage);
			}
			@Override
			public void focusLost(FocusEvent e) {
				gallery.textFieldFocusLost(txtSupplierSearch, supplierSearchMessage);
			}
		});
		
	}
	
	
	
	
}

