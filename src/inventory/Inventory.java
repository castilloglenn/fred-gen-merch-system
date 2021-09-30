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
import net.miginfocom.swing.MigLayout;

/**
 * To be done by: Sebastian Garcia
 * test
 */
@SuppressWarnings("serial")
public class Inventory extends JFrame {
	
	private Gallery gallery;
	private Utility utility;
	
	private JPanel mainPanel, navigationalPanel, displayPanel, supplierPanel, productPanel, dashboardPanel, buttonPanel;
	private JPanel productSearchPanel, supplierSearchPanel, productButtonPanel, panel;
	private JLabel btnDashboard, btnSupplier, btnProduct, lblSupplierList, lblProductList, lblSearchIcon,btnNew, btnManage;
	private JLabel btnDelete, lblProductSearchIcon, btnProductManage, btnProductRemove;
	private JTextField txtSupplierSearch, txtProductSearch;
	private JScrollPane supplierScrollPane;
	
	private CardLayout cardLayout;
	private VerticalLabelUI verticalUI;

	private String supplierSearchMessage = "Search for Supplier...";
	private String productSearchMessage = "Search for Products...";
	
	
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
		ProductAdd invProductAdd = new ProductAdd();
		
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
		
		lblSupplierList = new JLabel("Supplier List");
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, lblSupplierList, 15, SpringLayout.NORTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, lblSupplierList, 15, SpringLayout.WEST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, lblSupplierList, 45, SpringLayout.NORTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, lblSupplierList, -626, SpringLayout.EAST, supplierPanel);
		lblSupplierList.setFont(gallery.getFont(20f));
		supplierPanel.add(lblSupplierList);
		
		supplierSearchPanel = new RoundedPanel(gallery.WHITE);
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, supplierSearchPanel, 15, SpringLayout.SOUTH, lblSupplierList);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, supplierSearchPanel, 15, SpringLayout.WEST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, supplierSearchPanel, 55, SpringLayout.SOUTH, lblSupplierList);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, supplierSearchPanel, -15, SpringLayout.EAST, supplierPanel);
		supplierPanel.add(supplierSearchPanel);
		SpringLayout sl_supplierSearchPanel = new SpringLayout();
		supplierSearchPanel.setLayout(sl_supplierSearchPanel);
		
		lblSearchIcon = new JLabel(gallery.getImage("search.png", 20, 20));
		sl_supplierSearchPanel.putConstraint(SpringLayout.NORTH, lblSearchIcon, 10, SpringLayout.NORTH, supplierSearchPanel);
		sl_supplierSearchPanel.putConstraint(SpringLayout.WEST, lblSearchIcon, 10, SpringLayout.WEST, supplierSearchPanel);
		sl_supplierSearchPanel.putConstraint(SpringLayout.SOUTH, lblSearchIcon, -10, SpringLayout.SOUTH, supplierSearchPanel);
		supplierSearchPanel.add(lblSearchIcon);
		
		txtSupplierSearch = new JTextField();
		txtSupplierSearch.setBorder(null);
		gallery.styleTextField(txtSupplierSearch, supplierSearchMessage, 15f);
		sl_supplierSearchPanel.putConstraint(SpringLayout.EAST, lblSearchIcon, -8, SpringLayout.WEST, txtSupplierSearch);
		sl_supplierSearchPanel.putConstraint(SpringLayout.NORTH, txtSupplierSearch, 5, SpringLayout.NORTH, supplierSearchPanel);
		sl_supplierSearchPanel.putConstraint(SpringLayout.WEST, txtSupplierSearch, 30, SpringLayout.WEST, lblSearchIcon);
		sl_supplierSearchPanel.putConstraint(SpringLayout.SOUTH, txtSupplierSearch, -5, SpringLayout.SOUTH, supplierSearchPanel);
		sl_supplierSearchPanel.putConstraint(SpringLayout.EAST, txtSupplierSearch, -10, SpringLayout.EAST, supplierSearchPanel);
		supplierSearchPanel.add(txtSupplierSearch);
		txtSupplierSearch.setColumns(10);
		
		supplierScrollPane = new JScrollPane();
		sl_supplierPanel.putConstraint(SpringLayout.WEST, supplierScrollPane, 15, SpringLayout.WEST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, supplierScrollPane, -15, SpringLayout.EAST, supplierPanel);
		supplierScrollPane.setBorder(new LineBorder(Color.BLACK, 1, true));
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, supplierScrollPane, 15, SpringLayout.SOUTH, supplierSearchPanel);
		supplierPanel.add(supplierScrollPane);
		
		buttonPanel = new RoundedPanel(Gallery.WHITE);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, buttonPanel, -355, SpringLayout.EAST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, buttonPanel, -15, SpringLayout.SOUTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, buttonPanel, -15, SpringLayout.EAST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, supplierScrollPane, -15, SpringLayout.NORTH, buttonPanel);
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, buttonPanel, -100, SpringLayout.SOUTH, supplierPanel);
		supplierPanel.add(buttonPanel);
		SpringLayout sl_buttonPanel = new SpringLayout();
		buttonPanel.setLayout(sl_buttonPanel);
		
		btnNew = new JLabel("New");
		btnNew.setName("primary");
		gallery.styleLabelToButton(btnNew, 15f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnNew, 25, SpringLayout.NORTH, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnNew, -25, SpringLayout.SOUTH, buttonPanel);
		btnNew.setHorizontalAlignment(SwingConstants.CENTER);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnNew, 15, SpringLayout.WEST, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnNew, 115, SpringLayout.WEST, buttonPanel);
		buttonPanel.add(btnNew);
		
		btnManage = new JLabel("Manage");
		btnManage.setName("primary");
		gallery.styleLabelToButton(btnManage, 15f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnManage, 0, SpringLayout.NORTH, btnNew);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnManage, 10, SpringLayout.EAST, btnNew);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnManage, 0, SpringLayout.SOUTH, btnNew);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnManage, 110, SpringLayout.EAST, btnNew);
		btnManage.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.add(btnManage);
		
		btnDelete = new JLabel("Delete");
		btnDelete.setName("danger");
		gallery.styleLabelToButton(btnDelete, 15f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnDelete, 0, SpringLayout.NORTH, btnNew);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnDelete, 10, SpringLayout.EAST, btnManage);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnDelete, 0, SpringLayout.SOUTH, btnNew);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnDelete, 210, SpringLayout.EAST, btnNew);
		btnDelete.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.add(btnDelete);
		
		productPanel = new RoundedPanel(Gallery.GRAY);
		productPanel.setBackground(Gallery.GRAY);
		displayPanel.add(productPanel, "product");
		SpringLayout sl_productPanel = new SpringLayout();
		productPanel.setLayout(sl_productPanel);
		
		lblProductList = new JLabel("Product List");
		sl_productPanel.putConstraint(SpringLayout.NORTH, lblProductList, 15, SpringLayout.NORTH, productPanel);
		sl_productPanel.putConstraint(SpringLayout.WEST, lblProductList, 15, SpringLayout.WEST, productPanel);
		sl_productPanel.putConstraint(SpringLayout.SOUTH, lblProductList, 45, SpringLayout.NORTH, productPanel);
		sl_productPanel.putConstraint(SpringLayout.EAST, lblProductList, 143, SpringLayout.WEST, productPanel);
		lblProductList.setFont(gallery.getFont(20f));
		productPanel.add(lblProductList);
		
		productSearchPanel = new RoundedPanel(Gallery.WHITE);
		sl_productPanel.putConstraint(SpringLayout.NORTH, productSearchPanel, 15, SpringLayout.SOUTH, lblProductList);
		sl_productPanel.putConstraint(SpringLayout.WEST, productSearchPanel, 0, SpringLayout.WEST, lblProductList);
		sl_productPanel.putConstraint(SpringLayout.SOUTH, productSearchPanel, 55, SpringLayout.SOUTH, lblProductList);
		sl_productPanel.putConstraint(SpringLayout.EAST, productSearchPanel, -15, SpringLayout.EAST, productPanel);
		productPanel.add(productSearchPanel);
		SpringLayout sl_productSearchPanel = new SpringLayout();
		productSearchPanel.setLayout(sl_productSearchPanel);
		
		lblProductSearchIcon = new JLabel(gallery.getImage("search.png", 20, 20));		
		sl_productSearchPanel.putConstraint(SpringLayout.NORTH, lblProductSearchIcon, 10, SpringLayout.NORTH, productSearchPanel);
		sl_productSearchPanel.putConstraint(SpringLayout.WEST, lblProductSearchIcon, 10, SpringLayout.WEST, productSearchPanel);
		sl_productSearchPanel.putConstraint(SpringLayout.SOUTH, lblProductSearchIcon, -10, SpringLayout.SOUTH, productSearchPanel);
		productSearchPanel.add(lblProductSearchIcon);
		
		txtProductSearch = new JTextField();
		txtProductSearch.setBorder(null);
		gallery.styleTextField(txtProductSearch, productSearchMessage, 15f);
		sl_productSearchPanel.putConstraint(SpringLayout.EAST, lblProductSearchIcon, -8, SpringLayout.WEST, txtProductSearch);
		sl_productSearchPanel.putConstraint(SpringLayout.WEST, txtProductSearch, 30, SpringLayout.WEST, lblProductSearchIcon);
		sl_productSearchPanel.putConstraint(SpringLayout.NORTH, txtProductSearch, 5, SpringLayout.NORTH, productSearchPanel);
		sl_productSearchPanel.putConstraint(SpringLayout.SOUTH, txtProductSearch, -5, SpringLayout.SOUTH, productSearchPanel);
		sl_productSearchPanel.putConstraint(SpringLayout.EAST, txtProductSearch, -10, SpringLayout.EAST, productSearchPanel);
		productSearchPanel.add(txtProductSearch);
		txtProductSearch.setColumns(10);
		
		JPanel productImagePanel = new RoundedPanel(gallery.WHITE);
		sl_productPanel.putConstraint(SpringLayout.NORTH, productImagePanel, 15, SpringLayout.SOUTH, productSearchPanel);
		sl_productPanel.putConstraint(SpringLayout.WEST, productImagePanel, -315, SpringLayout.EAST, productPanel);
		sl_productPanel.putConstraint(SpringLayout.SOUTH, productImagePanel, 315, SpringLayout.SOUTH, productSearchPanel);
		sl_productPanel.putConstraint(SpringLayout.EAST, productImagePanel, -15, SpringLayout.EAST, productPanel);
		productPanel.add(productImagePanel);
		
		productButtonPanel = new RoundedPanel(gallery.WHITE);
		sl_productPanel.putConstraint(SpringLayout.WEST, productButtonPanel, 0, SpringLayout.WEST, productImagePanel);
		productButtonPanel.setBackground(Color.RED);
		sl_productPanel.putConstraint(SpringLayout.NORTH, productButtonPanel, -100, SpringLayout.SOUTH, productPanel);
		sl_productPanel.putConstraint(SpringLayout.SOUTH, productButtonPanel, -15, SpringLayout.SOUTH, productPanel);
		sl_productPanel.putConstraint(SpringLayout.EAST, productButtonPanel, 0, SpringLayout.EAST, productImagePanel);
		productPanel.add(productButtonPanel);
		SpringLayout sl_productButtonPanel = new SpringLayout();
		productButtonPanel.setLayout(sl_productButtonPanel);
		
		JLabel btnProductNew = new JLabel("New");
		sl_productButtonPanel.putConstraint(SpringLayout.EAST, btnProductNew, -200, SpringLayout.EAST, productButtonPanel);
		btnProductNew.setName("primary");
		gallery.styleLabelToButton(btnProductNew, 14f, 15, 10);
		sl_productButtonPanel.putConstraint(SpringLayout.WEST, btnProductNew, 10, SpringLayout.WEST, productButtonPanel);
		sl_productButtonPanel.putConstraint(SpringLayout.NORTH, btnProductNew, 25, SpringLayout.NORTH, productButtonPanel);
		sl_productButtonPanel.putConstraint(SpringLayout.SOUTH, btnProductNew, -25, SpringLayout.SOUTH, productButtonPanel);
		btnProductNew.setHorizontalAlignment(SwingConstants.CENTER);
		productButtonPanel.add(btnProductNew);
		
		btnProductManage = new JLabel("Manage");
		btnProductManage.setName("primary");
		gallery.styleLabelToButton(btnProductManage, 14f, 15, 10);
		sl_productButtonPanel.putConstraint(SpringLayout.EAST, btnProductManage, 100, SpringLayout.EAST, btnProductNew);
		sl_productButtonPanel.putConstraint(SpringLayout.WEST, btnProductManage, 10, SpringLayout.EAST, btnProductNew);
		sl_productButtonPanel.putConstraint(SpringLayout.NORTH, btnProductManage, 0, SpringLayout.NORTH, btnProductNew);
		sl_productButtonPanel.putConstraint(SpringLayout.SOUTH, btnProductManage, 0, SpringLayout.SOUTH, btnProductNew);
		btnProductManage.setHorizontalAlignment(SwingConstants.CENTER);
		productButtonPanel.add(btnProductManage);
		
		btnProductRemove = new JLabel("Remove");
		btnProductRemove.setName("danger");
		gallery.styleLabelToButton(btnProductRemove, 14f, 15, 10);
		sl_productButtonPanel.putConstraint(SpringLayout.EAST, btnProductRemove, 200, SpringLayout.EAST, btnProductNew);
		sl_productButtonPanel.putConstraint(SpringLayout.NORTH, btnProductRemove, 0, SpringLayout.NORTH, btnProductNew);
		sl_productButtonPanel.putConstraint(SpringLayout.WEST, btnProductRemove, 10, SpringLayout.EAST, btnProductManage);
		sl_productButtonPanel.putConstraint(SpringLayout.SOUTH, btnProductRemove, 0, SpringLayout.SOUTH, btnProductNew);
		btnProductRemove.setHorizontalAlignment(SwingConstants.CENTER);
		productButtonPanel.add(btnProductRemove);
		
		panel = new RoundedPanel(Gallery.WHITE);
		sl_productPanel.putConstraint(SpringLayout.NORTH, panel, 15, SpringLayout.SOUTH, productSearchPanel);
		sl_productPanel.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, productSearchPanel);
		sl_productPanel.putConstraint(SpringLayout.SOUTH, panel, -15, SpringLayout.SOUTH, productPanel);
		sl_productPanel.putConstraint(SpringLayout.EAST, panel, -15, SpringLayout.WEST, productImagePanel);
		productPanel.add(panel);
		

		
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
		
		txtSupplierSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				gallery.textFieldFocusGained(txtSupplierSearch, supplierSearchMessage);}
			@Override
			public void focusLost(FocusEvent e) {
				gallery.textFieldFocusLost(txtSupplierSearch, supplierSearchMessage);}
		});
		
		btnNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnNew);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnNew);}
			@Override
			public void mouseClicked(MouseEvent e) {
				invSupplierAdd.setVisible(true);
				invSupplierAdd.setLocationRelativeTo(null);
			}
		});
		
		btnManage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnManage); }
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnManage);}
			@Override
			public void mouseClicked(MouseEvent e) {invSupplierUpdate.setVisible(true);}
		});
		
		txtProductSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {gallery.textFieldFocusGained(txtProductSearch, productSearchMessage);}
			@Override
			public void focusLost(FocusEvent e) {	gallery.textFieldFocusLost(txtProductSearch, productSearchMessage);}
		});
		
		btnProductNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnProductNew);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnProductNew);}
			@Override
			public void mouseClicked(MouseEvent e) {
				invProductAdd.setVisible(true);
				invProductAdd.setLocationRelativeTo(null);
			}
		});		
		
		btnProductManage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnProductManage);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnProductManage);}
			@Override
			public void mouseClicked(MouseEvent e) {
				//code d2
			}
		});
		
		btnProductRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnProductRemove);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnProductRemove);	}
			@Override
			public void mouseClicked(MouseEvent e) {
				//code d2
			}
		});
	}
}

