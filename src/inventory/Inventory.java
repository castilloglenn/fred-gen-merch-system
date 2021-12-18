package inventory;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.Portal;
import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.RoundedPanel;
import utils.Utility;
import utils.VerticalLabelUI;

/**
 * @author Sebastian Garcia (UI design)
 * @author Allen Glenn E. Castillo (Logical implementation)
 */
@SuppressWarnings("serial")
public class Inventory extends JFrame {
	
	private final String TITLE = "Inventory";
	
	private Database database;
	private Gallery gallery;
	private Utility utility;
	private Logger logger;
	
	private Object[][] products;
	private int productSelectedRow = -1;
	
	private JPanel mainPanel, navigationalPanel, displayPanel, supplierPanel, productPanel, dashboardPanel, buttonPanel;
	private JPanel productSearchPanel, supplierSearchPanel, productButtonPanel, tablePanel, supplierTablePanel,productDescriptionPanel;
	
	private JLabel btnDashboard, btnSupplier, btnProduct, lblSupplierList, lblProductList, lblSearchIcon,btnNew, btnManage;
	private JLabel btnDelete, lblProductSearchIcon, btnProductManage, btnProductRemove, btnProductNew;
	
	private JTextField txtSupplierSearch, txtProductSearch;
	
	private JTable supplierTable,productTable;
	private JScrollPane productScrollPane,supplierScrollPane;
	
	private CardLayout cardLayout;
	private VerticalLabelUI verticalUI;

	private String supplierSearchMessage = "Search for Supplier...";
	private String productSearchMessage = "Search for Products...";
	private String priceBoughtDefaultText = "Price from supplier: ";
	private String sellingPriceDefaultText = "Selling price: ";
	private String stocksDefaultText = "Stocks on hand: ";
	
	private SpringLayout sl_descriptionDisplayPanel;
	private CardLayout descriptionCardLayout;
	private JPanel descriptionCardPanel;
	private JPanel descriptionEmptyPanel;
	private JPanel descriptionDisplayPanel;
	private JLabel lblEmptyDescription;
	private JLabel lblReceiveStocksButton;
	private JLabel lblPullOutButton;
	private JLabel lblProductIcon;
	private JLabel lblProductName;
	private JLabel lblPriceBought;
	private JLabel lblSellingPrice;
	private JLabel lblStocks;

	public Inventory(Object[] user) {
		database = Database.getInstance();
		gallery = Gallery.getInstance();
		utility = Utility.getInstance();
		logger = Logger.getInstance();
		// rotated 90 degrees counter-clockwise
		verticalUI = new VerticalLabelUI(false);

		setIconImage(gallery.getSystemIcon());
		setTitle(TITLE + Utility.TITLE_SEPARATOR + Utility.APP_TITLE);
		setMinimumSize(new Dimension(990, 600));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
		
		supplierSearchPanel = new RoundedPanel(Gallery.WHITE);
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
		
		buttonPanel = new RoundedPanel(Gallery.WHITE);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, buttonPanel, -325, SpringLayout.EAST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, buttonPanel, -15, SpringLayout.SOUTH, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, buttonPanel, -15, SpringLayout.EAST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, buttonPanel, -100, SpringLayout.SOUTH, supplierPanel);
		supplierPanel.add(buttonPanel);
		SpringLayout sl_buttonPanel = new SpringLayout();
		buttonPanel.setLayout(sl_buttonPanel);
		
		btnNew = new JLabel("New");
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnNew, 10, SpringLayout.WEST, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnNew, -210, SpringLayout.EAST, buttonPanel);
		btnNew.setName("primary");
		gallery.styleLabelToButton(btnNew, 14f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnNew, 25, SpringLayout.NORTH, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnNew, -25, SpringLayout.SOUTH, buttonPanel);
		btnNew.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.add(btnNew);
		
		btnManage = new JLabel("Manage");
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnManage, 100, SpringLayout.EAST, btnNew);
		btnManage.setName("primary");
		gallery.styleLabelToButton(btnManage, 14f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnManage, 0, SpringLayout.NORTH, btnNew);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnManage, 10, SpringLayout.EAST, btnNew);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnManage, 0, SpringLayout.SOUTH, btnNew);
		btnManage.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.add(btnManage);
		
		btnDelete = new JLabel("Remove");
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnDelete, 200, SpringLayout.EAST, btnNew);
		btnDelete.setName("danger");
		gallery.styleLabelToButton(btnDelete, 14f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnDelete, 0, SpringLayout.NORTH, btnNew);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnDelete, 10, SpringLayout.EAST, btnManage);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnDelete, 0, SpringLayout.SOUTH, btnNew);
		btnDelete.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.add(btnDelete);
		
		supplierTablePanel = new RoundedPanel(Gallery.WHITE);
		sl_supplierPanel.putConstraint(SpringLayout.NORTH, supplierTablePanel, 15, SpringLayout.SOUTH, supplierSearchPanel);
		sl_supplierPanel.putConstraint(SpringLayout.WEST, supplierTablePanel, 15, SpringLayout.WEST, supplierPanel);
		sl_supplierPanel.putConstraint(SpringLayout.SOUTH, supplierTablePanel, -15, SpringLayout.NORTH, buttonPanel);
		sl_supplierPanel.putConstraint(SpringLayout.EAST, supplierTablePanel, -15, SpringLayout.EAST, supplierPanel);
		supplierPanel.add(supplierTablePanel);
		SpringLayout sl_supplierTablePanel = new SpringLayout();
		supplierTablePanel.setLayout(sl_supplierTablePanel);
						
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
		
		productDescriptionPanel = new RoundedPanel(Gallery.WHITE);
		sl_productPanel.putConstraint(SpringLayout.NORTH, productDescriptionPanel, 15, SpringLayout.SOUTH, productSearchPanel);
		sl_productPanel.putConstraint(SpringLayout.WEST, productDescriptionPanel, -325, SpringLayout.EAST, productPanel);
		sl_productPanel.putConstraint(SpringLayout.SOUTH, productDescriptionPanel, 245, SpringLayout.SOUTH, productSearchPanel);
		sl_productPanel.putConstraint(SpringLayout.EAST, productDescriptionPanel, -15, SpringLayout.EAST, productPanel);
		productPanel.add(productDescriptionPanel);
		
		productButtonPanel = new RoundedPanel(Gallery.WHITE);
		sl_productPanel.putConstraint(SpringLayout.NORTH, productButtonPanel, 15, SpringLayout.SOUTH, productDescriptionPanel);
		sl_productPanel.putConstraint(SpringLayout.WEST, productButtonPanel, 0, SpringLayout.WEST, productDescriptionPanel);
		sl_productPanel.putConstraint(SpringLayout.SOUTH, productButtonPanel, 65, SpringLayout.SOUTH, productDescriptionPanel);
		productButtonPanel.setBackground(Color.RED);
		sl_productPanel.putConstraint(SpringLayout.EAST, productButtonPanel, 0, SpringLayout.EAST, productDescriptionPanel);
		productPanel.add(productButtonPanel);
		SpringLayout sl_productButtonPanel = new SpringLayout();
		productButtonPanel.setLayout(sl_productButtonPanel);
		
		btnProductNew = new JLabel("New");
		sl_productButtonPanel.putConstraint(SpringLayout.NORTH, btnProductNew, 10, SpringLayout.NORTH, productButtonPanel);
		sl_productButtonPanel.putConstraint(SpringLayout.SOUTH, btnProductNew, -10, SpringLayout.SOUTH, productButtonPanel);
		sl_productButtonPanel.putConstraint(SpringLayout.EAST, btnProductNew, -210, SpringLayout.EAST, productButtonPanel);
		btnProductNew.setName("primary");
		gallery.styleLabelToButton(btnProductNew, 14f, 15, 10);
		sl_productButtonPanel.putConstraint(SpringLayout.WEST, btnProductNew, 10, SpringLayout.WEST, productButtonPanel);
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
		
		tablePanel = new RoundedPanel(Gallery.WHITE);
		sl_productPanel.putConstraint(SpringLayout.NORTH, tablePanel, 15, SpringLayout.SOUTH, productSearchPanel);
		sl_productPanel.putConstraint(SpringLayout.WEST, tablePanel, 0, SpringLayout.WEST, productSearchPanel);
		sl_productPanel.putConstraint(SpringLayout.SOUTH, tablePanel, -15, SpringLayout.SOUTH, productPanel);
		sl_productPanel.putConstraint(SpringLayout.EAST, tablePanel, -15, SpringLayout.WEST, productDescriptionPanel);
		SpringLayout sl_productDescriptionPanel = new SpringLayout();
		productDescriptionPanel.setLayout(sl_productDescriptionPanel);
		
		descriptionCardPanel = new JPanel();
		descriptionCardPanel.setBackground(Gallery.WHITE);
		sl_productDescriptionPanel.putConstraint(SpringLayout.NORTH, descriptionCardPanel, 15, SpringLayout.NORTH, productDescriptionPanel);
		sl_productDescriptionPanel.putConstraint(SpringLayout.WEST, descriptionCardPanel, 15, SpringLayout.WEST, productDescriptionPanel);
		sl_productDescriptionPanel.putConstraint(SpringLayout.SOUTH, descriptionCardPanel, -15, SpringLayout.SOUTH, productDescriptionPanel);
		sl_productDescriptionPanel.putConstraint(SpringLayout.EAST, descriptionCardPanel, -15, SpringLayout.EAST, productDescriptionPanel);
		productDescriptionPanel.add(descriptionCardPanel);
		descriptionCardLayout = new CardLayout(0, 0);
		descriptionCardPanel.setLayout(descriptionCardLayout);
		
		descriptionDisplayPanel = new JPanel();
		descriptionDisplayPanel.setBackground(Gallery.WHITE);
		sl_descriptionDisplayPanel = new SpringLayout();
		descriptionDisplayPanel.setLayout(sl_descriptionDisplayPanel);
		
		descriptionEmptyPanel = new JPanel();
		descriptionEmptyPanel.setBackground(Gallery.WHITE);
		descriptionCardPanel.add(descriptionEmptyPanel, "description_empty");
		descriptionCardPanel.add(descriptionDisplayPanel, "description_display");
		
		lblReceiveStocksButton = new JLabel("Receive Stocks");
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.NORTH, lblReceiveStocksButton, -25, SpringLayout.SOUTH, descriptionDisplayPanel);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.EAST, lblReceiveStocksButton, 133, SpringLayout.WEST, descriptionDisplayPanel);
		lblReceiveStocksButton.setName("primary");
		gallery.styleLabelToButton(lblReceiveStocksButton, 14f, 0, 0);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.WEST, lblReceiveStocksButton, 0, SpringLayout.WEST, descriptionDisplayPanel);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.SOUTH, lblReceiveStocksButton, 0, SpringLayout.SOUTH, descriptionDisplayPanel);
		descriptionDisplayPanel.add(lblReceiveStocksButton);
		
		lblPullOutButton = new JLabel("Pull Out");
		lblPullOutButton.setName("danger");
		gallery.styleLabelToButton(lblPullOutButton, 14f, 0, 0);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.NORTH, lblPullOutButton, 0, SpringLayout.NORTH, lblReceiveStocksButton);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.WEST, lblPullOutButton, 15, SpringLayout.EAST, lblReceiveStocksButton);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.SOUTH, lblPullOutButton, 0, SpringLayout.SOUTH, lblReceiveStocksButton);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.EAST, lblPullOutButton, 0, SpringLayout.EAST, descriptionDisplayPanel);
		descriptionDisplayPanel.add(lblPullOutButton);
		
		lblProductIcon = new JLabel();
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.NORTH, lblProductIcon, 0, SpringLayout.NORTH, descriptionDisplayPanel);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.WEST, lblProductIcon, 0, SpringLayout.WEST, lblReceiveStocksButton);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.SOUTH, lblProductIcon, 48, SpringLayout.NORTH, descriptionDisplayPanel);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.EAST, lblProductIcon, 48, SpringLayout.WEST, descriptionDisplayPanel);
		descriptionDisplayPanel.add(lblProductIcon);
		
		lblProductName = new JLabel();
		lblProductName.setFont(gallery.getFont(14f));
		lblProductName.setVerticalAlignment(SwingConstants.TOP);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.NORTH, lblProductName, 0, SpringLayout.NORTH, lblProductIcon);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.WEST, lblProductName, 6, SpringLayout.EAST, lblProductIcon);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.SOUTH, lblProductName, 0, SpringLayout.SOUTH, lblProductIcon);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.EAST, lblProductName, 0, SpringLayout.EAST, descriptionDisplayPanel);
		descriptionDisplayPanel.add(lblProductName);
		
		lblPriceBought = new JLabel(priceBoughtDefaultText);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.NORTH, lblPriceBought, 20, SpringLayout.SOUTH, lblProductIcon);
		lblPriceBought.setFont(gallery.getFont(14f));
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.WEST, lblPriceBought, 0, SpringLayout.WEST, descriptionDisplayPanel);
		descriptionDisplayPanel.add(lblPriceBought);
		
		lblSellingPrice = new JLabel(sellingPriceDefaultText);
		lblSellingPrice.setFont(gallery.getFont(14f));
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.NORTH, lblSellingPrice, 6, SpringLayout.SOUTH, lblPriceBought);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.WEST, lblSellingPrice, 0, SpringLayout.WEST, lblReceiveStocksButton);
		descriptionDisplayPanel.add(lblSellingPrice);
		
		lblStocks = new JLabel(stocksDefaultText);
		lblStocks.setFont(gallery.getFont(14f));
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.NORTH, lblStocks, 20, SpringLayout.SOUTH, lblSellingPrice);
		sl_descriptionDisplayPanel.putConstraint(SpringLayout.WEST, lblStocks, 0, SpringLayout.WEST, lblReceiveStocksButton);
		descriptionDisplayPanel.add(lblStocks);
		SpringLayout sl_descriptionEmptyPanel = new SpringLayout();
		descriptionEmptyPanel.setLayout(sl_descriptionEmptyPanel);
		
		lblEmptyDescription = new JLabel("<html><center>Please select a product on<br>the table first.</center></html>");
		sl_descriptionEmptyPanel.putConstraint(SpringLayout.SOUTH, lblEmptyDescription, 0, SpringLayout.SOUTH, descriptionEmptyPanel);
		lblEmptyDescription.setFont(gallery.getFont(14f));
		lblEmptyDescription.setHorizontalAlignment(SwingConstants.CENTER);
		sl_descriptionEmptyPanel.putConstraint(SpringLayout.NORTH, lblEmptyDescription, 0, SpringLayout.NORTH, descriptionEmptyPanel);
		sl_descriptionEmptyPanel.putConstraint(SpringLayout.WEST, lblEmptyDescription, 0, SpringLayout.WEST, descriptionEmptyPanel);
		sl_descriptionEmptyPanel.putConstraint(SpringLayout.EAST, lblEmptyDescription, 280, SpringLayout.WEST, descriptionEmptyPanel);
		descriptionEmptyPanel.add(lblEmptyDescription);
		productPanel.add(tablePanel);
		SpringLayout sl_tablePanel = new SpringLayout();
		tablePanel.setLayout(sl_tablePanel);
		
		productScrollPane = new JScrollPane();
		productScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		productScrollPane.getViewport().setBackground(Gallery.WHITE);
		sl_tablePanel.putConstraint(SpringLayout.NORTH, productScrollPane, 10, SpringLayout.NORTH, tablePanel);
		sl_tablePanel.putConstraint(SpringLayout.WEST, productScrollPane, 10, SpringLayout.WEST, tablePanel);
		sl_tablePanel.putConstraint(SpringLayout.SOUTH, productScrollPane, -10, SpringLayout.SOUTH, tablePanel);
		sl_tablePanel.putConstraint(SpringLayout.EAST, productScrollPane, -10, SpringLayout.EAST, tablePanel);
		tablePanel.add(productScrollPane);
		
		supplierScrollPane = new JScrollPane();
		supplierScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		supplierScrollPane.getViewport().setBackground(Gallery.WHITE);
		sl_supplierTablePanel.putConstraint(SpringLayout.NORTH, supplierScrollPane, 10, SpringLayout.NORTH, supplierTablePanel);
		sl_supplierTablePanel.putConstraint(SpringLayout.WEST, supplierScrollPane, 10, SpringLayout.WEST, supplierTablePanel);
		sl_supplierTablePanel.putConstraint(SpringLayout.SOUTH, supplierScrollPane, -10, SpringLayout.SOUTH, supplierTablePanel);
		sl_supplierTablePanel.putConstraint(SpringLayout.EAST, supplierScrollPane, -10, SpringLayout.EAST, supplierTablePanel);
		supplierTablePanel.add(supplierScrollPane);
		
		//JTable
		
		supplierTable = new JTable();
		supplierTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		supplierScrollPane.setViewportView(supplierTable);
		
		productTable = new JTable();
		productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		productScrollPane.setViewportView(productTable);
		
		
		
		// NOTE: Please put all mouse listeners here at the end
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				logger.addLog(String.format("User %s has been close the Inventory System.", user[0].toString()));

				dispose();
				new Portal(user);
			}
		});
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				refreshTables();
			}
			public void windowLostFocus(WindowEvent e) {}
		});
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
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "supplier");
			}

		});
		btnProduct.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnProduct);}
			
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(btnProduct);}

			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "product");
			}
		});
		
		txtSupplierSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				gallery.textFieldFocusGained(txtSupplierSearch, supplierSearchMessage);}
			@Override
			public void focusLost(FocusEvent e) {
				gallery.textFieldFocusLost(txtSupplierSearch, supplierSearchMessage);}
		});
		txtSupplierSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String keyword = txtSupplierSearch.getText();
				Object[][] result = database.getSuppliersByKeyword(keyword);
				if (result != null) {
					supplierTable.setModel(utility.generateTable(result, Database.supplierHeaders));
				}
			}
		});
		txtProductSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String keyword = txtProductSearch.getText();
				Object[][] result = database.getSuppliersByKeyword(keyword);
				if (result != null) {
					productSelectedRow = -1;
					productTable.setModel(utility.generateTable(trimProductDetails(keyword), Database.productHeaders));
					descriptionCardLayout.show(descriptionCardPanel, "description_empty");
				}
			}
		});
		btnNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnNew);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnNew);}
			@Override
			public void mouseClicked(MouseEvent e) {
				new SupplierAdd(user);
			}
		});
		
		btnManage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnManage); }
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnManage);}
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowSelected = supplierTable.getSelectedRow();
				if (rowSelected == -1) {
					gallery.showMessage(new String[] {"Please select a row on the table to edit it."});
				} else {
					Object[] supplierData = {
						supplierTable.getValueAt(rowSelected, 0),
						supplierTable.getValueAt(rowSelected, 1),
						supplierTable.getValueAt(rowSelected, 2),
						supplierTable.getValueAt(rowSelected, 3)
					};

					new SupplierUpdate(user, supplierData);
				}
			}
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
				int supplierQuantity = database.getSuppliersByKeyword("").length;
				
				if (supplierQuantity == 0) {
					gallery.showMessage(new String[] {"You should create atleast one supplier first."});
				} else {
					new ProductAdd(user);
				}
			}
		});		
		
		btnProductManage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnProductManage);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnProductManage);}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (productSelectedRow == -1) {
					gallery.showMessage(new String[] {"Please select a product on the table first in order to edit it."});
				} else {
					new ProductUpdate(user, products[productSelectedRow]);
				}
			}
		});
		
		btnProductRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnProductRemove);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnProductRemove);	}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (productSelectedRow == -1) {
					gallery.showMessage(new String[] {"Please select a product on the table first in order to delete it."});
				} else {
					int confirmation1 = JOptionPane.showConfirmDialog(null, 
						"<html>Are your sure you want to delete a product? <br><br>"
						+ "This is not recoverable and it would delete all <br>"
						+ "records that are tied to this product including <br>"
						+ "the transactions and supplies. <br><br>"
						+ "Make sure that only newly created products without <br>"
						+ "any ties are to be deleted.</html>", 
						Utility.APP_TITLE, JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if (confirmation1 == 0) {
						if (database.deleteEntry("product", "product_id", (long) products[productTable.getSelectedRow()][0])) {
							logger.addLog(String.format("User %s deleted a product with the ID:%s", user[0].toString(), 
								products[productTable.getSelectedRow()][0].toString()));
							 
							JOptionPane.showMessageDialog(
								null, "Successfully deleted the product '" + products[productTable.getSelectedRow()][2].toString() + "'", 
								Utility.APP_TITLE, 
								JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { gallery.buttonHovered(btnDelete);}
			@Override
			public void mouseExited(MouseEvent e) { gallery.buttonNormalized(btnDelete);}
			@Override
			public void mouseClicked(MouseEvent e) {
				int rowSelected = supplierTable.getSelectedRow();
				if (rowSelected == -1) {
					gallery.showMessage(new String[] {"Please select a row on the table to remove it."});
				} else {
					String supplierID = supplierTable.getValueAt(rowSelected, 0).toString();

					int confirmation = JOptionPane.showConfirmDialog(
						null, "Are you sure you want to remove supplier with the ID of: " + supplierID, 
						Utility.APP_TITLE,
						JOptionPane.YES_OPTION,
						JOptionPane.WARNING_MESSAGE);
					
					if (confirmation == 0) {
						if (database.deleteEntry("supplier", "supplier_id", Long.parseLong(supplierID))) {
							logger.addLog(String.format("User %s removed a new supplier with the ID:%s", user[0].toString(), supplierID));
							
							JOptionPane.showMessageDialog(
									null, "Successfully removed the supplier with the ID of " + supplierID + ".", 
									Utility.APP_TITLE, 
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		lblReceiveStocksButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(lblReceiveStocksButton);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(lblReceiveStocksButton);	}
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO receive stock window
				new ProductStock(0, user, products[productSelectedRow]);
			}
		});
		lblPullOutButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(lblPullOutButton);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(lblPullOutButton);	}
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO receive stock window
				new ProductStock(1, user, products[productSelectedRow]);
			}
		});
		productTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				productSelectedRow = productTable.getSelectedRow();

				if (productSelectedRow == -1) {
					descriptionCardLayout.show(descriptionCardPanel, "description_empty");
				} else {
					lblProductIcon.setIcon((ImageIcon) products[productSelectedRow][3]);
					lblProductName.setText("<html>" + products[productSelectedRow][2].toString() + "</html>");
					lblPriceBought.setText(priceBoughtDefaultText + utility.formatCurrency((double) products[productSelectedRow][6]));
					lblSellingPrice.setText(sellingPriceDefaultText + utility.formatCurrency((double) products[productSelectedRow][7]));
					lblStocks.setText(stocksDefaultText + products[productSelectedRow][4].toString() + " "  + products[productSelectedRow][5].toString());

					descriptionCardLayout.show(descriptionCardPanel, "description_display");
				}
			}
		});
		
		
		refreshTables();
		setVisible(true);
	}
	
	private void refreshTables() {
		productSelectedRow = -1;
		descriptionCardLayout.show(descriptionCardPanel, "description_empty");
		
		supplierTable.setModel(utility.generateTable(database.getSuppliersByKeyword(""), Database.supplierHeaders));
		productTable.setModel(utility.generateTable(trimProductDetails(""), Database.productHeaders));
	}
	
	private Object[][] trimProductDetails(String keyword) {
		products = database.getProductsByKeyword(keyword);
		
		if (products != null) {
			Object[][] trimmedProducts = new Object[products.length][3];
			
			for (int productIndex = 0; productIndex < products.length; productIndex++) {
				trimmedProducts[productIndex][0] = products[productIndex][0];
				trimmedProducts[productIndex][1] = products[productIndex][1];
				trimmedProducts[productIndex][2] = products[productIndex][2];
			}
			
			return trimmedProducts;
		}
		
		return null;
	}
}

