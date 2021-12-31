package pos;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import main.Portal;
import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.RoundedPanel;
import utils.Statistic;
import utils.Utility;
import utils.VerticalLabelUI;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
@SuppressWarnings("serial")
public class POS extends JFrame {
	
	private final String POS_TITLE = "POS";
	private final String TRANSACTION_TITLE = "Transactions";
	private final String REPORTS_TITLE = "Reports";

	private String transactionMessage = "Today's Transaction #";
	private String defaultSearchMessage = "Search for products...";
	private String defaultQuantityMessage = "How many?";
	private String clearCartMessage = "Please let the manager put their password for verification.";
	
	private String transactionTutorial = "<h2>How to search for a transaction?</h2>"
			+ "<i>You can get the receipt's transaction number by looking at the upper "
			+ "side of the receipt and see the numbers written after the 'Transaction No:' "
			+ "part. Copy the numbers written and type it in the text field above and make sure "
			+ "that all numbers are correct, then click the search button to search the soft "
			+ "copy directory, if it has been deleted or moved or simply just did not exists, "
			+ "a message will pop up.</i>";
	
	private String productMostSalesTitle = "<html><p>Top 5 Most Sold Products<br><small>in descending order</small></p></html>";
	private String productLeastSalesTitle = "<html><p>Top 5 Least Sold Products<br><small>in descending order</small></p></html>";
	private String categorySalesTitle = "<html><p>Sales Per Category</p></html>";
	private String userSalesTitle = "<html><p>Top 5 Employee Sales<br><small>In descending order</small></p></html>";
	
	private String userSalesNotice = "<html><p><b>Note:</b> <i>The span of records calculated in this list is at all time range. "
			+ "Which means that the total amount here are the sales from the beginning of the system since it started up to latest "
			+ "or up to this moment.</i></p><html>";
	
	private String reportGeneratorTitle = "Generate Report";
	private String generateDescriptionMessage = "<html><p>Reports are automatically generated the day after (for daily sales report) "
			+ "and every 1st day of next month (for the montly sales report). To manually generate a report, press the button below that "
			+ "corresponds to the type of report you want to manually generated. Reports can be seen under the \"Business\" directory inside "
			+ "the \"Reports\".</p></html>";
	
	private int defaultHeight = 710; // 600
	private int defaultWidth = 990; // 1000
	private int minHeight = 710; // 710
	private int minWidth = 990; // 990
	
	private boolean minWidthTrigger = false;
	private Timer timer;
	
	// Customized query table variables
	private Object[][] queryResult;
	private ProductDisplay[] productUIs;
	private int tableMaxPerColumn = 3;
	private int tableMaxPerPage = 9;
	private int tableSelectedIndex = -1;
	private int tableCurrentPage = 1;
	private int tableTotalPage = 1;

	// Customized cart list variables
	private ArrayList<CartItem> cartList = new ArrayList<>();
	private int cartMaxPerPage = 11;
	private int cartCurrentPage = 1;
	private int cartTotalPage = 1;
	
	// Dynamic cart payment details
	private int totalCartQuantity = 0;
	private double totalCartPrice = 0.0;
	
	// User details
	private String cashierName; 
	
	// Statistics
	private Object[][] productMostSales;
	private Object[][] productLeastSales;
	private Object[][] categorySales;
	private Object[][] userSales;
	
	private Database database; 
	private Statistic statistic;
	private Utility utility;
	private Report report;
	private Logger logger;
	private Object[] user;
	private Gallery gallery;
	private VerticalLabelUI verticalUI;
	
	private JPanel mainPanel, cardLayoutPanel, queryEmptyPanel,
		queryResultPanel, cartListCardPanel, cartListPanel,
		cardListEmptyPanel;
	
	private RoundedPanel navigationPanel, displayPanel, 
		posPanel, transactionPanel, reportPanel,
		cartPanel, paymentPanel, checkoutPanel,
		searchPanel, tableContainerPanel;
	
	private JLabel lblDashboardNav, lblTransactionNav, lblReportNav,
		lblTransactionNo, lblDateTime, lblCheckoutButton,
		lblCancelButton, lblSearchIcon, lblAddToCart,
		lblQuantityIcon, lblDownButton, lblUpButton,
		lblNotFoundImage, lblPageIndicator, lblLeftButton,
		lblCartLabel, lblRightButton, lblCartIndicator,
		lblCartListEmpty, lblPaymentCashier, lblPaymentCartSize,
		lblPaymentCartQuantity, lblPaymentCartTotalPrice;
	
	private JTextField tfSearch, tfQuantity;
	
	private SpringLayout sl_mainPanel, sl_posPanel;
	
	private CardLayout cardLayout, queryCardLayout, cartListCardLayout;
	private JPanel transactionSearchPanel;
	private JPanel transactionStatisticPanel;
	private JPanel transactionReceiptPanel;
	private JLabel lblTransactionTitle;
	private JLabel lblTransactionSearch;
	private JLabel lblTransactionSearchButton;
	private JTextField tfTransactionSearch;
	private JLabel lblTransactionTutorial;
	private JLabel lblTransactionStatistics;
	private JLabel lblTransactionReceiptTitle;
	private JTextArea taReceiptPreview;
	private JPanel productMostSalePanel;
	private JPanel generateReportPanel;
	private JPanel userSalePanel;
	private JPanel productLeastSalePanel;
	private JPanel categorySalePanel;
	private JLabel lblProductMostSale;
	private JLabel lblProductMostSaleList;
	private JLabel lblProductLeastSale;
	private JLabel lblProductLeastSaleList;
	private JLabel lblCategorySale;
	private JLabel lblCategorySaleList;
	private JLabel lblUserSale;
	private JLabel lblUserSaleList;
	private JLabel lblUserSalesNotice;
	private JLabel lblGenerateReport;
	private JLabel lblDailyReportButton;
	private JLabel lblGenerateDescription;
	private JLabel lblMonthlyReportButton;

	public POS(Object[] user) {
		database = Database.getInstance();
		statistic = Statistic.getInstance();
		gallery = Gallery.getInstance();
		logger = Logger.getInstance();
		utility = Utility.getInstance();
		this.user = user;
		
		report = Report.getInstance(user);
		cashierName = user[3].toString();
		
		// rotated 90 degrees counter-clockwise
		verticalUI = new VerticalLabelUI(false); 

		setIconImage(gallery.getSystemIcon());
		setTitle(POS_TITLE + Utility.TITLE_SEPARATOR + Utility.BUSINESS_TITLE);
		setMinimumSize(new Dimension(minWidth, minHeight));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		Dimension selfDisplay = Toolkit.getDefaultToolkit().getScreenSize();
		if (defaultWidth >= selfDisplay.getWidth() || defaultHeight >= selfDisplay.getHeight()) {
			setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH );
		} else {
			setSize(defaultWidth, defaultHeight);
		}
		
		mainPanel = new JPanel();
		setContentPane(mainPanel);
		mainPanel.setBackground(Gallery.BLACK);
		sl_mainPanel = new SpringLayout();
		mainPanel.setLayout(sl_mainPanel);
		cardLayout = new CardLayout(0, 0);
		
		navigationPanel = new RoundedPanel(Gallery.BLUE);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, navigationPanel, 426, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, navigationPanel, 60, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, navigationPanel, 15, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, navigationPanel, -30, SpringLayout.WEST, mainPanel);
		mainPanel.add(navigationPanel);

		displayPanel = new RoundedPanel(Gallery.GRAY);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, displayPanel, 15, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, displayPanel, 15, SpringLayout.EAST, navigationPanel);
		SpringLayout sl_navigationPanel = new SpringLayout();
		navigationPanel.setLayout(sl_navigationPanel);
		
		lblDashboardNav = new JLabel("POS");
		lblDashboardNav.setName("primary");
		gallery.styleLabelToButton(lblDashboardNav, 15f, "pos.png", 15, 10, 10);
		lblDashboardNav.setUI(verticalUI);
		sl_navigationPanel.putConstraint(SpringLayout.NORTH, lblDashboardNav, 35, SpringLayout.NORTH, navigationPanel);
		sl_navigationPanel.putConstraint(SpringLayout.WEST, lblDashboardNav, 40, SpringLayout.WEST, navigationPanel);
		sl_navigationPanel.putConstraint(SpringLayout.EAST, lblDashboardNav, -10, SpringLayout.EAST, navigationPanel);
		navigationPanel.add(lblDashboardNav);
		
		lblTransactionNav = new JLabel("TRANSACTIONS");
		lblTransactionNav.setName("primary");
		gallery.styleLabelToButton(lblTransactionNav, 15f, "transaction.png", 15, 10, 10);
		lblTransactionNav.setUI(verticalUI);
		sl_navigationPanel.putConstraint(SpringLayout.NORTH, lblTransactionNav, 10, SpringLayout.SOUTH, lblDashboardNav);
		sl_navigationPanel.putConstraint(SpringLayout.WEST, lblTransactionNav, 0, SpringLayout.WEST, lblDashboardNav);
		sl_navigationPanel.putConstraint(SpringLayout.EAST, lblTransactionNav, 0, SpringLayout.EAST, lblDashboardNav);
		navigationPanel.add(lblTransactionNav);
		
		lblReportNav = new JLabel("REPORTS");
		lblReportNav.setName("primary");
		gallery.styleLabelToButton(lblReportNav, 15f, "report.png", 15, 10, 10);
		lblReportNav.setUI(verticalUI);
		sl_navigationPanel.putConstraint(SpringLayout.NORTH, lblReportNav, 10, SpringLayout.SOUTH, lblTransactionNav);
		sl_navigationPanel.putConstraint(SpringLayout.WEST, lblReportNav, 0, SpringLayout.WEST, lblDashboardNav);
		sl_navigationPanel.putConstraint(SpringLayout.EAST, lblReportNav, 0, SpringLayout.EAST, lblDashboardNav);
		navigationPanel.add(lblReportNav);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, displayPanel, -15, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, displayPanel, -15, SpringLayout.EAST, mainPanel);
		mainPanel.add(displayPanel);
		displayPanel.setLayout(cardLayout);
		
		posPanel = new RoundedPanel(Gallery.GRAY);
		displayPanel.add(posPanel, "pos");
		sl_posPanel = new SpringLayout();
		posPanel.setLayout(sl_posPanel);
		
		lblTransactionNo = new JLabel(transactionMessage);
		lblTransactionNo.setFont(gallery.getFont(23f));
		sl_posPanel.putConstraint(SpringLayout.NORTH, lblTransactionNo, 20, SpringLayout.NORTH, posPanel);
		sl_posPanel.putConstraint(SpringLayout.WEST, lblTransactionNo, 20, SpringLayout.WEST, posPanel);
		posPanel.add(lblTransactionNo);
		
		cartPanel = new RoundedPanel(Color.WHITE);
		SpringLayout sl_cartPanel = new SpringLayout();
		cartPanel.setLayout(sl_cartPanel);
		sl_posPanel.putConstraint(SpringLayout.NORTH, cartPanel, 20, SpringLayout.NORTH, posPanel);
		sl_posPanel.putConstraint(SpringLayout.WEST, cartPanel, -300, SpringLayout.EAST, posPanel);
		sl_posPanel.putConstraint(SpringLayout.SOUTH, cartPanel, -20, SpringLayout.SOUTH, posPanel);
		sl_posPanel.putConstraint(SpringLayout.EAST, cartPanel, -20, SpringLayout.EAST, posPanel);
		posPanel.add(cartPanel);
		
		lblDateTime = new JLabel(gallery.getTime(minWidthTrigger));
		sl_posPanel.putConstraint(SpringLayout.SOUTH, lblDateTime, -3, SpringLayout.SOUTH, lblTransactionNo);
		sl_posPanel.putConstraint(SpringLayout.EAST, lblDateTime, -20, SpringLayout.WEST, cartPanel);
		lblDateTime.setFont(gallery.getFont(15f));
		posPanel.add(lblDateTime);
		
		paymentPanel = new RoundedPanel(Color.WHITE);
		sl_posPanel.putConstraint(SpringLayout.NORTH, paymentPanel, -120, SpringLayout.SOUTH, cartPanel);
		sl_posPanel.putConstraint(SpringLayout.WEST, paymentPanel, -365, SpringLayout.WEST, cartPanel);
		sl_posPanel.putConstraint(SpringLayout.SOUTH, paymentPanel, 0, SpringLayout.SOUTH, cartPanel);
		sl_posPanel.putConstraint(SpringLayout.EAST, paymentPanel, -15, SpringLayout.WEST, cartPanel);
		posPanel.add(paymentPanel);
		
		checkoutPanel = new RoundedPanel(Color.WHITE);
		sl_posPanel.putConstraint(SpringLayout.EAST, checkoutPanel, 200, SpringLayout.WEST, posPanel);
		sl_posPanel.putConstraint(SpringLayout.SOUTH, checkoutPanel, 0, SpringLayout.SOUTH, cartPanel);
		sl_posPanel.putConstraint(SpringLayout.NORTH, checkoutPanel, 0, SpringLayout.NORTH, paymentPanel);
		posPanel.add(checkoutPanel);
		SpringLayout sl_checkoutPanel = new SpringLayout();
		checkoutPanel.setLayout(sl_checkoutPanel);
		
		lblCheckoutButton = new JLabel("CHECK OUT (F1)");
		lblCheckoutButton.setName("primary");
		gallery.styleLabelToButton(lblCheckoutButton, 15f, 15, 10);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblCheckoutButton, 15, SpringLayout.WEST, checkoutPanel);
		sl_checkoutPanel.putConstraint(SpringLayout.SOUTH, lblCheckoutButton, 55, SpringLayout.NORTH, checkoutPanel);
		sl_checkoutPanel.putConstraint(SpringLayout.EAST, lblCheckoutButton, -15, SpringLayout.EAST, checkoutPanel);
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblCheckoutButton, 15, SpringLayout.NORTH, checkoutPanel);
		sl_posPanel.putConstraint(SpringLayout.NORTH, lblCheckoutButton, 10, SpringLayout.NORTH, checkoutPanel);
		sl_posPanel.putConstraint(SpringLayout.WEST, lblCheckoutButton, 10, SpringLayout.WEST, checkoutPanel);
		sl_posPanel.putConstraint(SpringLayout.SOUTH, lblCheckoutButton, -487, SpringLayout.NORTH, paymentPanel);
		SpringLayout sl_paymentPanel = new SpringLayout();
		paymentPanel.setLayout(sl_paymentPanel);
		
		lblPaymentCashier = new JLabel();
		sl_paymentPanel.putConstraint(SpringLayout.NORTH, lblPaymentCashier, 12, SpringLayout.NORTH, paymentPanel);
		sl_paymentPanel.putConstraint(SpringLayout.WEST, lblPaymentCashier, 30, SpringLayout.WEST, paymentPanel);
		lblPaymentCashier.setFont(gallery.getFont(15f));
		paymentPanel.add(lblPaymentCashier);
		
		lblPaymentCartSize = new JLabel();
		sl_paymentPanel.putConstraint(SpringLayout.NORTH, lblPaymentCartSize, 3, SpringLayout.SOUTH, lblPaymentCashier);
		lblPaymentCartSize.setFont(gallery.getFont(15f));
		sl_paymentPanel.putConstraint(SpringLayout.WEST, lblPaymentCartSize, 0, SpringLayout.WEST, lblPaymentCashier);
		paymentPanel.add(lblPaymentCartSize);
		
		lblPaymentCartQuantity = new JLabel();
		lblPaymentCartQuantity.setFont(gallery.getFont(15f));
		sl_paymentPanel.putConstraint(SpringLayout.NORTH, lblPaymentCartQuantity, 3, SpringLayout.SOUTH, lblPaymentCartSize);
		sl_paymentPanel.putConstraint(SpringLayout.WEST, lblPaymentCartQuantity, 0, SpringLayout.WEST, lblPaymentCashier);
		paymentPanel.add(lblPaymentCartQuantity);
		
		lblPaymentCartTotalPrice = new JLabel();
		sl_paymentPanel.putConstraint(SpringLayout.NORTH, lblPaymentCartTotalPrice, 3, SpringLayout.SOUTH, lblPaymentCartQuantity);
		lblPaymentCartTotalPrice.setFont(gallery.getFont(15f));
		sl_paymentPanel.putConstraint(SpringLayout.WEST, lblPaymentCartTotalPrice, 0, SpringLayout.WEST, lblPaymentCashier);
		paymentPanel.add(lblPaymentCartTotalPrice);
		checkoutPanel.add(lblCheckoutButton);
		 
		lblCancelButton = new JLabel("CANCEL (F4)");
		lblCancelButton.setName("danger");
		gallery.styleLabelToButton(lblCancelButton, 15f, 15, 10);
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblCancelButton, 10, SpringLayout.SOUTH, lblCheckoutButton);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblCancelButton, 0, SpringLayout.WEST, lblCheckoutButton);
		sl_checkoutPanel.putConstraint(SpringLayout.SOUTH, lblCancelButton, -15, SpringLayout.SOUTH, checkoutPanel);
		sl_checkoutPanel.putConstraint(SpringLayout.EAST, lblCancelButton, 0, SpringLayout.EAST, lblCheckoutButton);
		checkoutPanel.add(lblCancelButton);
		
		searchPanel = new RoundedPanel(Color.WHITE);
		sl_posPanel.putConstraint(SpringLayout.WEST, checkoutPanel, 0, SpringLayout.WEST, searchPanel);
		sl_posPanel.putConstraint(SpringLayout.NORTH, searchPanel, 10, SpringLayout.SOUTH, lblTransactionNo);
		sl_posPanel.putConstraint(SpringLayout.SOUTH, searchPanel, 60, SpringLayout.SOUTH, lblTransactionNo);
		sl_posPanel.putConstraint(SpringLayout.WEST, searchPanel, 20, SpringLayout.WEST, posPanel);
		sl_posPanel.putConstraint(SpringLayout.EAST, searchPanel, -15, SpringLayout.WEST, cartPanel);
		
		lblCartLabel = new JLabel("Cart");
		lblCartLabel.setFont(gallery.getFont(23f));
		sl_cartPanel.putConstraint(SpringLayout.NORTH, lblCartLabel, 15, SpringLayout.NORTH, cartPanel);
		sl_cartPanel.putConstraint(SpringLayout.WEST, lblCartLabel, 15, SpringLayout.WEST, cartPanel);
		cartPanel.add(lblCartLabel);
		
		lblLeftButton = new JLabel();
		sl_cartPanel.putConstraint(SpringLayout.WEST, lblLeftButton, 70, SpringLayout.WEST, cartPanel);
		lblLeftButton.setName("primary");
		gallery.styleLabelToButton(lblLeftButton, 15f, "arrow-left.png", 25, 10, 3);
		sl_cartPanel.putConstraint(SpringLayout.SOUTH, lblLeftButton, -15, SpringLayout.SOUTH, cartPanel);
		cartPanel.add(lblLeftButton);
		
		lblRightButton = new JLabel();
		sl_cartPanel.putConstraint(SpringLayout.EAST, lblRightButton, -70, SpringLayout.EAST, cartPanel);
		lblRightButton.setName("primary");
		gallery.styleLabelToButton(lblRightButton, 15f, "arrow-right.png", 25, 10, 3);
		sl_cartPanel.putConstraint(SpringLayout.SOUTH, lblRightButton, -15, SpringLayout.SOUTH, cartPanel);
		cartPanel.add(lblRightButton);
		
		lblCartIndicator = new JLabel();
		lblCartIndicator.setFont(gallery.getFont(15f));
		sl_cartPanel.putConstraint(SpringLayout.NORTH, lblCartIndicator, 0, SpringLayout.NORTH, lblLeftButton);
		sl_cartPanel.putConstraint(SpringLayout.EAST, lblCartIndicator, 0, SpringLayout.WEST, lblRightButton);
		lblCartIndicator.setHorizontalAlignment(SwingConstants.CENTER);
		sl_cartPanel.putConstraint(SpringLayout.WEST, lblCartIndicator, 0, SpringLayout.EAST, lblLeftButton);
		sl_cartPanel.putConstraint(SpringLayout.SOUTH, lblCartIndicator, 0, SpringLayout.SOUTH, lblLeftButton);
		cartPanel.add(lblCartIndicator);
		
		cartListCardPanel = new JPanel();
		sl_cartPanel.putConstraint(SpringLayout.NORTH, cartListCardPanel, 5, SpringLayout.SOUTH, lblCartLabel);
		sl_cartPanel.putConstraint(SpringLayout.SOUTH, cartListCardPanel, -5, SpringLayout.NORTH, lblLeftButton);
		cartListCardPanel.setBackground(Gallery.WHITE);
		sl_cartPanel.putConstraint(SpringLayout.WEST, cartListCardPanel, 15, SpringLayout.WEST, cartPanel);
		sl_cartPanel.putConstraint(SpringLayout.EAST, cartListCardPanel, -15, SpringLayout.EAST, cartPanel);
		cartPanel.add(cartListCardPanel);
		cartListCardLayout = new CardLayout(0, 0);
		cartListCardPanel.setLayout(cartListCardLayout);
		
		cardListEmptyPanel = new JPanel();
		cardListEmptyPanel.setBackground(Gallery.WHITE);
		
		cartListPanel = new JPanel();
		cartListPanel.setBackground(Gallery.WHITE);

		posPanel.add(searchPanel);
		SpringLayout sl_searchPanel = new SpringLayout();
		searchPanel.setLayout(sl_searchPanel);
		
		lblAddToCart = new JLabel();
		sl_searchPanel.putConstraint(SpringLayout.WEST, lblAddToCart, -60, SpringLayout.EAST, searchPanel);
		sl_searchPanel.putConstraint(SpringLayout.EAST, lblAddToCart, -15, SpringLayout.EAST, searchPanel);
		lblAddToCart.setName("primary");
		gallery.styleLabelToButton(lblAddToCart, 1f, "cart.png", 22, 0, 0);
		sl_searchPanel.putConstraint(SpringLayout.NORTH, lblAddToCart, 10, SpringLayout.NORTH, searchPanel);
		sl_searchPanel.putConstraint(SpringLayout.SOUTH, lblAddToCart, -10, SpringLayout.SOUTH, searchPanel);
		searchPanel.add(lblAddToCart);
		
		tfSearch = new JTextField();
		gallery.styleTextField(tfSearch, defaultSearchMessage, 15f);
		tfSearch.setCaretPosition(0);
		searchPanel.add(tfSearch);
		
		lblSearchIcon = new JLabel(gallery.getImage("search.png", 20, 20));
		sl_searchPanel.putConstraint(SpringLayout.NORTH, tfSearch, 0, SpringLayout.NORTH, lblSearchIcon);
		sl_searchPanel.putConstraint(SpringLayout.WEST, tfSearch, 5, SpringLayout.EAST, lblSearchIcon);
		sl_searchPanel.putConstraint(SpringLayout.SOUTH, tfSearch, 0, SpringLayout.SOUTH, lblSearchIcon);
		sl_searchPanel.putConstraint(SpringLayout.NORTH, lblSearchIcon, 10, SpringLayout.NORTH, searchPanel);
		sl_searchPanel.putConstraint(SpringLayout.WEST, lblSearchIcon, 15, SpringLayout.WEST, searchPanel);
		sl_searchPanel.putConstraint(SpringLayout.SOUTH, lblSearchIcon, -10, SpringLayout.SOUTH, searchPanel);
		searchPanel.add(lblSearchIcon);
		
		tfQuantity = new JTextField();
		sl_searchPanel.putConstraint(SpringLayout.WEST, tfQuantity, -120, SpringLayout.WEST, lblAddToCart);
		gallery.styleTextField(tfQuantity, defaultQuantityMessage, 15f);
		tfQuantity.setCaretPosition(0);
		sl_searchPanel.putConstraint(SpringLayout.NORTH, tfQuantity, 0, SpringLayout.NORTH, lblAddToCart);
		sl_searchPanel.putConstraint(SpringLayout.SOUTH, tfQuantity, 0, SpringLayout.SOUTH, lblAddToCart);
		sl_searchPanel.putConstraint(SpringLayout.EAST, tfQuantity, 0, SpringLayout.WEST, lblAddToCart);
		searchPanel.add(tfQuantity);
		
		lblQuantityIcon = new JLabel(gallery.getImage("scale.png", 20, 20));
		sl_searchPanel.putConstraint(SpringLayout.EAST, tfSearch, 0, SpringLayout.WEST, lblQuantityIcon);
		sl_searchPanel.putConstraint(SpringLayout.NORTH, lblQuantityIcon, 0, SpringLayout.NORTH, tfQuantity);
		sl_searchPanel.putConstraint(SpringLayout.SOUTH, lblQuantityIcon, 0, SpringLayout.SOUTH, tfQuantity);
		sl_searchPanel.putConstraint(SpringLayout.EAST, lblQuantityIcon, 0, SpringLayout.WEST, tfQuantity);
		searchPanel.add(lblQuantityIcon);
		
		tableContainerPanel = new RoundedPanel(Gallery.WHITE);
		sl_posPanel.putConstraint(SpringLayout.NORTH, tableContainerPanel, 15, SpringLayout.SOUTH, searchPanel);
		sl_posPanel.putConstraint(SpringLayout.WEST, tableContainerPanel, 0, SpringLayout.WEST, searchPanel);
		sl_posPanel.putConstraint(SpringLayout.SOUTH, tableContainerPanel, -15, SpringLayout.NORTH, checkoutPanel);
		sl_posPanel.putConstraint(SpringLayout.EAST, tableContainerPanel, 0, SpringLayout.EAST, searchPanel);
		posPanel.add(tableContainerPanel);
		SpringLayout sl_tableContainerPanel = new SpringLayout();
		tableContainerPanel.setLayout(sl_tableContainerPanel);
		
		cardLayoutPanel = new JPanel();
		sl_tableContainerPanel.putConstraint(SpringLayout.NORTH, cardLayoutPanel, 15, SpringLayout.NORTH, tableContainerPanel);
		sl_tableContainerPanel.putConstraint(SpringLayout.WEST, cardLayoutPanel, 15, SpringLayout.WEST, tableContainerPanel);
		sl_tableContainerPanel.putConstraint(SpringLayout.SOUTH, cardLayoutPanel, -14, SpringLayout.SOUTH, tableContainerPanel);
		queryCardLayout = new CardLayout();
		cardLayoutPanel.setBackground(Gallery.WHITE);
		tableContainerPanel.add(cardLayoutPanel);
		cardLayoutPanel.setLayout(queryCardLayout);
		
		lblDownButton = new JLabel();
		sl_tableContainerPanel.putConstraint(SpringLayout.NORTH, lblDownButton, -50, SpringLayout.SOUTH, tableContainerPanel);
		sl_tableContainerPanel.putConstraint(SpringLayout.SOUTH, lblDownButton, -15, SpringLayout.SOUTH, tableContainerPanel);
		lblDownButton.setName("secondary");
		gallery.styleLabelToButton(lblDownButton, 1f, "arrow-down.png", 22, 0, 0);
		tableContainerPanel.add(lblDownButton);
		
		lblUpButton = new JLabel();
		sl_tableContainerPanel.putConstraint(SpringLayout.NORTH, lblUpButton, -45, SpringLayout.NORTH, lblDownButton);
		sl_tableContainerPanel.putConstraint(SpringLayout.SOUTH, lblUpButton, -10, SpringLayout.NORTH, lblDownButton);
		sl_tableContainerPanel.putConstraint(SpringLayout.WEST, lblDownButton, 0, SpringLayout.WEST, lblUpButton);
		sl_tableContainerPanel.putConstraint(SpringLayout.EAST, lblDownButton, 0, SpringLayout.EAST, lblUpButton);
		sl_tableContainerPanel.putConstraint(SpringLayout.EAST, lblUpButton, -15, SpringLayout.EAST, tableContainerPanel);
		lblUpButton.setName("secondary");
		gallery.styleLabelToButton(lblUpButton, 1f, "arrow-up.png", 22, 0, 0);
		tableContainerPanel.add(lblUpButton);

		queryEmptyPanel = new JPanel();
		queryEmptyPanel.setBackground(Gallery.WHITE);
		SpringLayout sl_queryEmptyPanel = new SpringLayout();
		queryEmptyPanel.setLayout(sl_queryEmptyPanel);
		
		lblNotFoundImage = new JLabel(gallery.getImage("not-found.png", 170, 190));
		sl_queryEmptyPanel.putConstraint(SpringLayout.NORTH, lblNotFoundImage, 0, SpringLayout.NORTH, queryEmptyPanel);
		sl_queryEmptyPanel.putConstraint(SpringLayout.WEST, lblNotFoundImage, 0, SpringLayout.WEST, queryEmptyPanel);
		sl_queryEmptyPanel.putConstraint(SpringLayout.SOUTH, lblNotFoundImage, 0, SpringLayout.SOUTH, queryEmptyPanel);
		sl_queryEmptyPanel.putConstraint(SpringLayout.EAST, lblNotFoundImage, 0, SpringLayout.EAST, queryEmptyPanel);
		lblNotFoundImage.setText("<html><p>No results found with the<br>keyword");
		lblNotFoundImage.setFont(gallery.getFont(15f));
		queryEmptyPanel.add(lblNotFoundImage);
		
		queryResultPanel = new JPanel();
		cardLayoutPanel.add(queryResultPanel, "result");
		cardLayoutPanel.add(queryEmptyPanel, "none");
		cartListCardPanel.add(cardListEmptyPanel, "empty");
		cartListCardPanel.add(cartListPanel, "cart");
		cartListPanel.setLayout(null);
		SpringLayout sl_cardListEmptyPanel = new SpringLayout();
		cardListEmptyPanel.setLayout(sl_cardListEmptyPanel);
		queryResultPanel.setBackground(Gallery.WHITE);
		queryResultPanel.setLayout(null);
		
		lblCartListEmpty = new JLabel(gallery.getImage("empty-cart.png", 200, 295));
		lblCartListEmpty.setHorizontalAlignment(SwingConstants.CENTER);
		sl_cardListEmptyPanel.putConstraint(SpringLayout.NORTH, lblCartListEmpty, 0, SpringLayout.NORTH, cardListEmptyPanel);
		sl_cardListEmptyPanel.putConstraint(SpringLayout.WEST, lblCartListEmpty, 0, SpringLayout.WEST, cardListEmptyPanel);
		sl_cardListEmptyPanel.putConstraint(SpringLayout.SOUTH, lblCartListEmpty, 0, SpringLayout.SOUTH, cardListEmptyPanel);
		sl_cardListEmptyPanel.putConstraint(SpringLayout.EAST, lblCartListEmpty, 0, SpringLayout.EAST, cardListEmptyPanel);
		cardListEmptyPanel.add(lblCartListEmpty);
		
		lblPageIndicator = new JLabel();
		sl_tableContainerPanel.putConstraint(SpringLayout.WEST, lblPageIndicator, -45, SpringLayout.EAST, tableContainerPanel);
		sl_tableContainerPanel.putConstraint(SpringLayout.EAST, cardLayoutPanel, -5, SpringLayout.WEST, lblPageIndicator);
		sl_tableContainerPanel.putConstraint(SpringLayout.WEST, lblUpButton, 0, SpringLayout.WEST, lblPageIndicator);
		sl_tableContainerPanel.putConstraint(SpringLayout.NORTH, lblPageIndicator, 15, SpringLayout.NORTH, tableContainerPanel);
		sl_tableContainerPanel.putConstraint(SpringLayout.EAST, lblPageIndicator, -15, SpringLayout.EAST, tableContainerPanel);
		lblPageIndicator.setHorizontalAlignment(SwingConstants.CENTER);
		lblPageIndicator.setFont(gallery.getFont(15f));
		tableContainerPanel.add(lblPageIndicator);
		
		transactionPanel = new RoundedPanel(Gallery.GRAY);
		displayPanel.add(transactionPanel, "transaction");
		SpringLayout sl_transactionPanel = new SpringLayout();
		transactionPanel.setLayout(sl_transactionPanel);
		
		transactionSearchPanel = new RoundedPanel(Gallery.WHITE);
		sl_transactionPanel.putConstraint(SpringLayout.NORTH, transactionSearchPanel, 20, SpringLayout.NORTH, transactionPanel);
		sl_transactionPanel.putConstraint(SpringLayout.WEST, transactionSearchPanel, 20, SpringLayout.WEST, transactionPanel);
		sl_transactionPanel.putConstraint(SpringLayout.SOUTH, transactionSearchPanel, 170, SpringLayout.NORTH, transactionPanel);
		sl_transactionPanel.putConstraint(SpringLayout.EAST, transactionSearchPanel, 445, SpringLayout.WEST, transactionPanel);
		transactionPanel.add(transactionSearchPanel);
		
		transactionStatisticPanel = new RoundedPanel(Gallery.WHITE);
		sl_transactionPanel.putConstraint(SpringLayout.NORTH, transactionStatisticPanel, 15, SpringLayout.SOUTH, transactionSearchPanel);
		sl_transactionPanel.putConstraint(SpringLayout.WEST, transactionStatisticPanel, 0, SpringLayout.WEST, transactionSearchPanel);
		sl_transactionPanel.putConstraint(SpringLayout.SOUTH, transactionStatisticPanel, 420, SpringLayout.SOUTH, transactionSearchPanel);
		sl_transactionPanel.putConstraint(SpringLayout.EAST, transactionStatisticPanel, 0, SpringLayout.EAST, transactionSearchPanel);
		transactionPanel.add(transactionStatisticPanel);
		
		transactionReceiptPanel = new RoundedPanel(Gallery.WHITE);
		sl_transactionPanel.putConstraint(SpringLayout.NORTH, transactionReceiptPanel, 0, SpringLayout.NORTH, transactionSearchPanel);
		sl_transactionPanel.putConstraint(SpringLayout.SOUTH, transactionReceiptPanel, -15, SpringLayout.SOUTH, transactionPanel);
		SpringLayout sl_transactionSearchPanel = new SpringLayout();
		transactionSearchPanel.setLayout(sl_transactionSearchPanel);
		
		lblTransactionTitle = new JLabel("View Transaction History");
		sl_transactionSearchPanel.putConstraint(SpringLayout.NORTH, lblTransactionTitle, 15, SpringLayout.NORTH, transactionSearchPanel);
		lblTransactionTitle.setFont(gallery.getFont(23f));
		sl_transactionSearchPanel.putConstraint(SpringLayout.WEST, lblTransactionTitle, 15, SpringLayout.WEST, transactionSearchPanel);
		transactionSearchPanel.add(lblTransactionTitle);
		
		lblTransactionSearch = new JLabel("Enter Transaction Number");
		sl_transactionSearchPanel.putConstraint(SpringLayout.NORTH, lblTransactionSearch, 65, SpringLayout.NORTH, lblTransactionTitle);
		lblTransactionSearch.setFont(gallery.getFont(15f));
		sl_transactionSearchPanel.putConstraint(SpringLayout.WEST, lblTransactionSearch, 0, SpringLayout.WEST, lblTransactionTitle);
		transactionSearchPanel.add(lblTransactionSearch);
		
		lblTransactionSearchButton = new JLabel("Search");
		sl_transactionSearchPanel.putConstraint(SpringLayout.NORTH, lblTransactionSearchButton, 3, SpringLayout.SOUTH, lblTransactionSearch);
		lblTransactionSearchButton.setName("primary");
		gallery.styleLabelToButton(lblTransactionSearchButton, 15f, 5, 5);
		sl_transactionSearchPanel.putConstraint(SpringLayout.EAST, lblTransactionSearchButton, -15, SpringLayout.EAST, transactionSearchPanel);
		transactionSearchPanel.add(lblTransactionSearchButton);
		
		tfTransactionSearch = new JTextField();
		sl_transactionSearchPanel.putConstraint(SpringLayout.NORTH, tfTransactionSearch, 2, SpringLayout.NORTH, lblTransactionSearchButton);
		sl_transactionSearchPanel.putConstraint(SpringLayout.SOUTH, tfTransactionSearch, -2, SpringLayout.SOUTH, lblTransactionSearchButton);
		tfTransactionSearch.setFont(gallery.getFont(15f));
		sl_transactionSearchPanel.putConstraint(SpringLayout.WEST, tfTransactionSearch, 0, SpringLayout.WEST, lblTransactionTitle);
		sl_transactionSearchPanel.putConstraint(SpringLayout.EAST, tfTransactionSearch, -10, SpringLayout.WEST, lblTransactionSearchButton);
		transactionSearchPanel.add(tfTransactionSearch);
		tfTransactionSearch.setColumns(10);
		sl_transactionPanel.putConstraint(SpringLayout.WEST, transactionReceiptPanel, -420, SpringLayout.EAST, transactionPanel);
		SpringLayout sl_transactionStatisticPanel = new SpringLayout();
		transactionStatisticPanel.setLayout(sl_transactionStatisticPanel);
		
		lblTransactionTutorial = new JLabel("<html>" + transactionTutorial + "</html>");
		sl_transactionStatisticPanel.putConstraint(SpringLayout.EAST, lblTransactionTutorial, -15, SpringLayout.EAST, transactionStatisticPanel);
		lblTransactionTutorial.setFont(gallery.getFont(15f));
		sl_transactionStatisticPanel.putConstraint(SpringLayout.NORTH, lblTransactionTutorial, 15, SpringLayout.NORTH, transactionStatisticPanel);
		sl_transactionStatisticPanel.putConstraint(SpringLayout.WEST, lblTransactionTutorial, 15, SpringLayout.WEST, transactionStatisticPanel);
		transactionStatisticPanel.add(lblTransactionTutorial);
		
		lblTransactionStatistics = new JLabel(getTransactionStatistics());
		sl_transactionStatisticPanel.putConstraint(SpringLayout.NORTH, lblTransactionStatistics, 15, SpringLayout.SOUTH, lblTransactionTutorial);
		lblTransactionStatistics.setFont(gallery.getFont(15f));
		sl_transactionStatisticPanel.putConstraint(SpringLayout.WEST, lblTransactionStatistics, 0, SpringLayout.WEST, lblTransactionTutorial);
		transactionStatisticPanel.add(lblTransactionStatistics);
		sl_transactionPanel.putConstraint(SpringLayout.EAST, transactionReceiptPanel, -20, SpringLayout.EAST, transactionPanel);
		transactionPanel.add(transactionReceiptPanel);
		SpringLayout sl_transactionReceiptPanel = new SpringLayout();
		transactionReceiptPanel.setLayout(sl_transactionReceiptPanel);
		
		lblTransactionReceiptTitle = new JLabel("Receipt Preview");
		lblTransactionReceiptTitle.setFont(gallery.getFont(23f));
		sl_transactionReceiptPanel.putConstraint(SpringLayout.NORTH, lblTransactionReceiptTitle, 15, SpringLayout.NORTH, transactionReceiptPanel);
		sl_transactionReceiptPanel.putConstraint(SpringLayout.WEST, lblTransactionReceiptTitle, 15, SpringLayout.WEST, transactionReceiptPanel);
		transactionReceiptPanel.add(lblTransactionReceiptTitle);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		sl_transactionReceiptPanel.putConstraint(SpringLayout.NORTH, scrollPane, 15, SpringLayout.SOUTH, lblTransactionReceiptTitle);
		sl_transactionReceiptPanel.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, lblTransactionReceiptTitle);
		sl_transactionReceiptPanel.putConstraint(SpringLayout.SOUTH, scrollPane, -15, SpringLayout.SOUTH, transactionReceiptPanel);
		sl_transactionReceiptPanel.putConstraint(SpringLayout.EAST, scrollPane, -15, SpringLayout.EAST, transactionReceiptPanel);
		transactionReceiptPanel.add(scrollPane);
		
		taReceiptPreview = new JTextArea();
		taReceiptPreview.setFont(gallery.getMonospacedFont(14f));
		taReceiptPreview.setEditable(false);
		scrollPane.setViewportView(taReceiptPreview);
		
		reportPanel = new RoundedPanel(Gallery.GRAY);
		displayPanel.add(reportPanel, "report");
		SpringLayout sl_reportPanel = new SpringLayout();
		reportPanel.setLayout(sl_reportPanel);
		
		generateReportPanel = new RoundedPanel(Gallery.WHITE);
		sl_reportPanel.putConstraint(SpringLayout.NORTH, generateReportPanel, -325, SpringLayout.SOUTH, reportPanel);
		sl_reportPanel.putConstraint(SpringLayout.WEST, generateReportPanel, -425, SpringLayout.EAST, reportPanel);
		sl_reportPanel.putConstraint(SpringLayout.SOUTH, generateReportPanel, -15, SpringLayout.SOUTH, reportPanel);
		sl_reportPanel.putConstraint(SpringLayout.EAST, generateReportPanel, -15, SpringLayout.EAST, reportPanel);
		reportPanel.add(generateReportPanel);
		
		userSalePanel = new RoundedPanel(Gallery.WHITE);
		sl_reportPanel.putConstraint(SpringLayout.NORTH, userSalePanel, 15, SpringLayout.NORTH, reportPanel);
		sl_reportPanel.putConstraint(SpringLayout.WEST, userSalePanel, 0, SpringLayout.WEST, generateReportPanel);
		sl_reportPanel.putConstraint(SpringLayout.SOUTH, userSalePanel, 300, SpringLayout.NORTH, reportPanel);
		sl_reportPanel.putConstraint(SpringLayout.EAST, userSalePanel, 0, SpringLayout.EAST, generateReportPanel);
		SpringLayout sl_generateReportPanel = new SpringLayout();
		generateReportPanel.setLayout(sl_generateReportPanel);
		
		lblGenerateReport = new JLabel(reportGeneratorTitle);
		lblGenerateReport.setFont(gallery.getFont(22f));
		sl_generateReportPanel.putConstraint(SpringLayout.NORTH, lblGenerateReport, 15, SpringLayout.NORTH, generateReportPanel);
		sl_generateReportPanel.putConstraint(SpringLayout.WEST, lblGenerateReport, 30, SpringLayout.WEST, generateReportPanel);
		sl_generateReportPanel.putConstraint(SpringLayout.EAST, lblGenerateReport, -30, SpringLayout.EAST, generateReportPanel);
		generateReportPanel.add(lblGenerateReport);
		
		lblDailyReportButton = new JLabel("Generate End-of-Day Report (Once per Day)");
		lblDailyReportButton.setName("primary");
		gallery.styleLabelToButton(lblDailyReportButton, 14f, 5, 5);
		generateReportPanel.add(lblDailyReportButton);
		
		lblGenerateDescription = new JLabel(generateDescriptionMessage);
		sl_generateReportPanel.putConstraint(SpringLayout.WEST, lblDailyReportButton, 0, SpringLayout.WEST, lblGenerateDescription);
		sl_generateReportPanel.putConstraint(SpringLayout.EAST, lblDailyReportButton, 0, SpringLayout.EAST, lblGenerateDescription);
		sl_generateReportPanel.putConstraint(SpringLayout.EAST, lblGenerateDescription, 0, SpringLayout.EAST, lblGenerateReport);
		sl_generateReportPanel.putConstraint(SpringLayout.NORTH, lblDailyReportButton, 15, SpringLayout.SOUTH, lblGenerateDescription);
		sl_generateReportPanel.putConstraint(SpringLayout.NORTH, lblGenerateDescription, 15, SpringLayout.SOUTH, lblGenerateReport);
		lblGenerateDescription.setFont(gallery.getFont(14f));
		sl_generateReportPanel.putConstraint(SpringLayout.WEST, lblGenerateDescription, 0, SpringLayout.WEST, lblGenerateReport);
		generateReportPanel.add(lblGenerateDescription);
		
		lblMonthlyReportButton = new JLabel("Generate Previous Month's Sales Report");
		lblMonthlyReportButton.setName("primary");
		gallery.styleLabelToButton(lblMonthlyReportButton, 14f, 5, 5);
		sl_generateReportPanel.putConstraint(SpringLayout.NORTH, lblMonthlyReportButton, 10, SpringLayout.SOUTH, lblDailyReportButton);
		sl_generateReportPanel.putConstraint(SpringLayout.WEST, lblMonthlyReportButton, 0, SpringLayout.WEST, lblDailyReportButton);
		sl_generateReportPanel.putConstraint(SpringLayout.EAST, lblMonthlyReportButton, 0, SpringLayout.EAST, lblDailyReportButton);
		lblMonthlyReportButton.setName("primary");
		generateReportPanel.add(lblMonthlyReportButton);
		reportPanel.add(userSalePanel);
		SpringLayout sl_userSalePanel = new SpringLayout();
		userSalePanel.setLayout(sl_userSalePanel);
		
		lblUserSale = new JLabel(userSalesTitle);
		lblUserSale.setFont(gallery.getFont(22f));
		sl_userSalePanel.putConstraint(SpringLayout.NORTH, lblUserSale, 15, SpringLayout.NORTH, userSalePanel);
		sl_userSalePanel.putConstraint(SpringLayout.WEST, lblUserSale, 30, SpringLayout.WEST, userSalePanel);
		sl_userSalePanel.putConstraint(SpringLayout.EAST, lblUserSale, -30, SpringLayout.EAST, userSalePanel);
		userSalePanel.add(lblUserSale);
		
		lblUserSaleList = new JLabel();
		lblUserSaleList.setFont(gallery.getFont(14f));
		lblUserSaleList.setVerticalAlignment(SwingConstants.TOP);
		sl_userSalePanel.putConstraint(SpringLayout.NORTH, lblUserSaleList, 10, SpringLayout.SOUTH, lblUserSale);
		sl_userSalePanel.putConstraint(SpringLayout.WEST, lblUserSaleList, 0, SpringLayout.WEST, lblUserSale);
		sl_userSalePanel.putConstraint(SpringLayout.EAST, lblUserSaleList, 0, SpringLayout.EAST, lblUserSale);
		userSalePanel.add(lblUserSaleList);
		
		lblUserSalesNotice = new JLabel(userSalesNotice);
		lblUserSalesNotice.setFont(gallery.getFont(14f));
		lblUserSalesNotice.setVerticalAlignment(SwingConstants.TOP);
		sl_userSalePanel.putConstraint(SpringLayout.NORTH, lblUserSalesNotice, 10, SpringLayout.SOUTH, lblUserSaleList);
		sl_userSalePanel.putConstraint(SpringLayout.WEST, lblUserSalesNotice, 0, SpringLayout.WEST, lblUserSale);
		sl_userSalePanel.putConstraint(SpringLayout.SOUTH, lblUserSalesNotice, -15, SpringLayout.SOUTH, userSalePanel);
		sl_userSalePanel.putConstraint(SpringLayout.EAST, lblUserSalesNotice, 0, SpringLayout.EAST, lblUserSaleList);
		userSalePanel.add(lblUserSalesNotice);
		
		productMostSalePanel = new RoundedPanel(Gallery.WHITE);
		sl_reportPanel.putConstraint(SpringLayout.NORTH, productMostSalePanel, 15, SpringLayout.NORTH, reportPanel);
		sl_reportPanel.putConstraint(SpringLayout.WEST, productMostSalePanel, 15, SpringLayout.WEST, reportPanel);
		sl_reportPanel.putConstraint(SpringLayout.SOUTH, productMostSalePanel, 205, SpringLayout.NORTH, reportPanel);
		sl_reportPanel.putConstraint(SpringLayout.EAST, productMostSalePanel, 440, SpringLayout.WEST, reportPanel);
		reportPanel.add(productMostSalePanel);
		
		productLeastSalePanel = new RoundedPanel(Gallery.WHITE);
		sl_reportPanel.putConstraint(SpringLayout.NORTH, productLeastSalePanel, 15, SpringLayout.SOUTH, productMostSalePanel);
		sl_reportPanel.putConstraint(SpringLayout.WEST, productLeastSalePanel, 0, SpringLayout.WEST, productMostSalePanel);
		sl_reportPanel.putConstraint(SpringLayout.SOUTH, productLeastSalePanel, 205, SpringLayout.SOUTH, productMostSalePanel);
		sl_reportPanel.putConstraint(SpringLayout.EAST, productLeastSalePanel, 0, SpringLayout.EAST, productMostSalePanel);
		reportPanel.add(productLeastSalePanel);
		
		categorySalePanel = new RoundedPanel(Gallery.WHITE);
		sl_reportPanel.putConstraint(SpringLayout.NORTH, categorySalePanel, 15, SpringLayout.SOUTH, productLeastSalePanel);
		sl_reportPanel.putConstraint(SpringLayout.WEST, categorySalePanel, 0, SpringLayout.WEST, productMostSalePanel);
		SpringLayout sl_productMostSalePanel = new SpringLayout();
		productMostSalePanel.setLayout(sl_productMostSalePanel);
		
		lblProductMostSale = new JLabel(productMostSalesTitle);
		lblProductMostSale.setFont(gallery.getFont(22f));
		sl_productMostSalePanel.putConstraint(SpringLayout.NORTH, lblProductMostSale, 15, SpringLayout.NORTH, productMostSalePanel);
		sl_productMostSalePanel.putConstraint(SpringLayout.WEST, lblProductMostSale, 30, SpringLayout.WEST, productMostSalePanel);
		sl_productMostSalePanel.putConstraint(SpringLayout.EAST, lblProductMostSale, -30, SpringLayout.EAST, productMostSalePanel);
		productMostSalePanel.add(lblProductMostSale);
		
		lblProductMostSaleList = new JLabel();
		lblProductMostSaleList.setFont(gallery.getFont(14f));
		lblProductMostSaleList.setVerticalAlignment(SwingConstants.TOP);
		sl_productMostSalePanel.putConstraint(SpringLayout.NORTH, lblProductMostSaleList, 10, SpringLayout.SOUTH, lblProductMostSale);
		sl_productMostSalePanel.putConstraint(SpringLayout.WEST, lblProductMostSaleList, 0, SpringLayout.WEST, lblProductMostSale);
		sl_productMostSalePanel.putConstraint(SpringLayout.SOUTH, lblProductMostSaleList, -15, SpringLayout.SOUTH, productMostSalePanel);
		sl_productMostSalePanel.putConstraint(SpringLayout.EAST, lblProductMostSaleList, 0, SpringLayout.EAST, lblProductMostSale);
		productMostSalePanel.add(lblProductMostSaleList);
		sl_reportPanel.putConstraint(SpringLayout.SOUTH, categorySalePanel, 205, SpringLayout.SOUTH, productLeastSalePanel);
		sl_reportPanel.putConstraint(SpringLayout.EAST, categorySalePanel, 0, SpringLayout.EAST, productLeastSalePanel);
		SpringLayout sl_productLeastSalePanel = new SpringLayout();
		productLeastSalePanel.setLayout(sl_productLeastSalePanel);
		
		lblProductLeastSale = new JLabel(productLeastSalesTitle);
		lblProductLeastSale.setFont(gallery.getFont(22f));
		sl_productLeastSalePanel.putConstraint(SpringLayout.NORTH, lblProductLeastSale, 15, SpringLayout.NORTH, productLeastSalePanel);
		sl_productLeastSalePanel.putConstraint(SpringLayout.WEST, lblProductLeastSale, 30, SpringLayout.WEST, productLeastSalePanel);
		sl_productLeastSalePanel.putConstraint(SpringLayout.EAST, lblProductLeastSale, -30, SpringLayout.EAST, productLeastSalePanel);
		productLeastSalePanel.add(lblProductLeastSale);
		
		lblProductLeastSaleList = new JLabel();
		lblProductLeastSaleList.setFont(gallery.getFont(14f));
		lblProductLeastSaleList.setVerticalAlignment(SwingConstants.TOP);
		sl_productLeastSalePanel.putConstraint(SpringLayout.NORTH, lblProductLeastSaleList, 10, SpringLayout.SOUTH, lblProductLeastSale);
		sl_productLeastSalePanel.putConstraint(SpringLayout.WEST, lblProductLeastSaleList, 0, SpringLayout.WEST, lblProductLeastSale);
		sl_productLeastSalePanel.putConstraint(SpringLayout.SOUTH, lblProductLeastSaleList, -15, SpringLayout.SOUTH, productLeastSalePanel);
		sl_productLeastSalePanel.putConstraint(SpringLayout.EAST, lblProductLeastSaleList, 0, SpringLayout.EAST, lblProductLeastSale);
		productLeastSalePanel.add(lblProductLeastSaleList);
		reportPanel.add(categorySalePanel);
		SpringLayout sl_categorySalePanel = new SpringLayout();
		categorySalePanel.setLayout(sl_categorySalePanel);
		
		lblCategorySale = new JLabel(categorySalesTitle);
		lblCategorySale.setFont(gallery.getFont(22f));
		sl_categorySalePanel.putConstraint(SpringLayout.NORTH, lblCategorySale, 15, SpringLayout.NORTH, categorySalePanel);
		sl_categorySalePanel.putConstraint(SpringLayout.WEST, lblCategorySale, 30, SpringLayout.WEST, categorySalePanel);
		sl_categorySalePanel.putConstraint(SpringLayout.EAST, lblCategorySale, -30, SpringLayout.EAST, categorySalePanel);
		categorySalePanel.add(lblCategorySale);
		
		lblCategorySaleList = new JLabel();
		sl_categorySalePanel.putConstraint(SpringLayout.NORTH, lblCategorySaleList, 5, SpringLayout.SOUTH, lblCategorySale);
		lblCategorySaleList.setFont(gallery.getFont(14f));
		lblCategorySaleList.setVerticalAlignment(SwingConstants.TOP);
		sl_categorySalePanel.putConstraint(SpringLayout.WEST, lblCategorySaleList, 0, SpringLayout.WEST, lblCategorySale);
		sl_categorySalePanel.putConstraint(SpringLayout.SOUTH, lblCategorySaleList, -15, SpringLayout.SOUTH, categorySalePanel);
		sl_categorySalePanel.putConstraint(SpringLayout.EAST, lblCategorySaleList, 0, SpringLayout.EAST, lblCategorySale);
		categorySalePanel.add(lblCategorySaleList);
		
		
		
		
		
		
		
		// Configure initial component state
		setTransactionCount();
		updatePaymentStatistics();
		updateSaleStatistics();
		lblLeftButton.setVisible(false);
		lblRightButton.setVisible(false);
		lblCartIndicator.setVisible(false);
		
		// Component behaviors, listeners

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (cartList.isEmpty()) {
					logger.addLog(Logger.LEVEL_2, String.format("User %s closed the Point of Sales.", user[0].toString()));
					
					new Portal(user);
					dispose();
				} else {
					logger.addLog(Logger.LEVEL_3, String.format("User %s tried to close Point of Sales whilst the cart list is not empty.", user[0].toString()));
					gallery.showMessage(new String[] {"You cannot close the window without finishing the transaction."});
				}
			}
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				
				minWidthTrigger = getWidth() <= minWidth;
				lblDateTime.setText(gallery.getTime(minWidthTrigger));
				
				resetAllPages();
				tfSearch.requestFocus();
			}
		});
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				setTransactionCount();
			}
			public void windowLostFocus(WindowEvent e) {}
		});
		lblDashboardNav.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblDashboardNav); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblDashboardNav); }
			
			@Override public void mouseClicked(MouseEvent e) {
				setTitle(POS_TITLE + Utility.TITLE_SEPARATOR + Utility.BUSINESS_TITLE);
				
				cardLayout.show(displayPanel, "pos");
				tfSearch.requestFocus();
			}
		});
		lblTransactionNav.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblTransactionNav); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblTransactionNav); }
			
			@Override public void mouseClicked(MouseEvent e) {
				setTitle(TRANSACTION_TITLE + Utility.TITLE_SEPARATOR + Utility.BUSINESS_TITLE);
				lblTransactionStatistics.setText(getTransactionStatistics());
				cardLayout.show(displayPanel, "transaction");
			}
		});
		lblReportNav.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblReportNav); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblReportNav); }
			
			@Override public void mouseClicked(MouseEvent e) {
				setTitle(REPORTS_TITLE + Utility.TITLE_SEPARATOR + Utility.BUSINESS_TITLE);
				
				if (requestManagerPermission()) {
					logger.addLog(Logger.LEVEL_2, 
						String.format("User %s viewed the product reports and statistics.", user[0].toString()));
					
					updateSaleStatistics();
					cardLayout.show(displayPanel, "report");
				}
			}
		});
		lblCheckoutButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblCheckoutButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblCheckoutButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				checkOut();
			}
		});
		lblCancelButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblCancelButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblCancelButton); }
			
			@Override public void mouseClicked(MouseEvent e) { clearCart(false); }
		});
		lblAddToCart.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblAddToCart); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblAddToCart); }
			
			@Override public void mouseClicked(MouseEvent e) {
				addToCart();
			}
		});
		lblDownButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) {
				lblDownButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				lblDownButton.setBackground(Gallery.WHITE);
				lblDownButton.setIcon(gallery.getImage("arrow-down-hovered.png", 22, 22));
			}
			
			@Override public void mouseExited(MouseEvent e) {
				gallery.buttonNormalized(lblDownButton);
				lblDownButton.setIcon(gallery.getImage("arrow-down.png", 22, 22));
			}
			
			@Override public void mouseClicked(MouseEvent e) {
				tableNextPage();
			}
		});
		lblUpButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				lblUpButton.setBackground(Gallery.WHITE);
				lblUpButton.setIcon(gallery.getImage("arrow-up-hovered.png", 22, 22));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				gallery.buttonNormalized(lblUpButton);
				lblUpButton.setIcon(gallery.getImage("arrow-up.png", 22, 22));
			}
			
			@Override public void mouseClicked(MouseEvent e) {
				tablePreviousPage();
			}
		});
		tfSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				gallery.textFieldFocusGained(tfSearch, defaultSearchMessage);
			}
			@Override
			public void focusLost(FocusEvent e) {
				gallery.textFieldFocusLost(tfSearch, defaultSearchMessage);
			}
		});
		tfSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int code = e.getKeyCode();
				systemKeyMappingShortcuts(code);
				
				// Catching all non-character key codes to contain it in a single condition
				if ((code >= 37 && code <= 40) || (code == 33 || code == 34)) {
					// If the selected index is initially empty or just recently unselected a product..
					if (tableSelectedIndex == -1) {
						selectProduct(0);
					} else {
						tableKeyMappingShortcuts(code);
					}
				} else if (code == KeyEvent.VK_ENTER) {
					tfQuantity.requestFocus();
				} else {
					searchQuery();
				}
			}
		});
		tfQuantity.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				gallery.textFieldFocusGained(tfQuantity, defaultQuantityMessage);
			}
			@Override
			public void focusLost(FocusEvent e) {
				gallery.textFieldFocusLost(tfQuantity, defaultQuantityMessage);
			}
		});
		tfQuantity.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				gallery.textFieldFocusGained(tfQuantity, defaultQuantityMessage);
			}
		});

		tfQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				systemKeyMappingShortcuts(code);
				tableKeyMappingShortcuts(code);
				
				if (code == KeyEvent.VK_ENTER) {
					addToCart();
				}
			}
		});
		tableContainerPanel.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) { scrollTable(e); }
		});
		cartPanel.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) { scrollCartList(e); }
		});
		lblLeftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				gallery.buttonHovered(lblLeftButton);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				gallery.buttonNormalized(lblLeftButton);
			}
			
			@Override public void mouseClicked(MouseEvent e) {
				cartPreviousPage();
			}
		});
		lblRightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				gallery.buttonHovered(lblRightButton);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				gallery.buttonNormalized(lblRightButton);
			}
			
			@Override public void mouseClicked(MouseEvent e) {
				cartNextPage();
			}
		});
		lblTransactionSearchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				gallery.buttonHovered(lblTransactionSearchButton);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				gallery.buttonNormalized(lblTransactionSearchButton);
			}
			
			@Override public void mouseClicked(MouseEvent e) {
				String inputTransactionID = tfTransactionSearch.getText();
				
				String contents = utility.readFile("transaction", inputTransactionID);
				if (contents != null) {
					logger.addLog(
						Logger.LEVEL_2, 
						String.format(
							"User %s viewed the transaction receipt with the ID: %s", 
							user[0].toString(), inputTransactionID));
					
					taReceiptPreview.setText(contents);
					taReceiptPreview.setCaretPosition(0);
				} else {
					taReceiptPreview.setText("");
					gallery.showMessage(new String[] {"No file found for specific ID."});
				}
			}
		});
		lblDailyReportButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				gallery.buttonHovered(lblDailyReportButton);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				gallery.buttonNormalized(lblDailyReportButton);
			}
			
			@Override public void mouseClicked(MouseEvent e) {
				if (report.generateDailyReport(Report.CURRENT_DAY_REPORT, false)) {
					logger.addLog(Logger.LEVEL_3, 
						String.format("User %s have generated the daily sales report file for today.", user[0].toString()));
					
					JOptionPane.showMessageDialog(
						null, "Successfully generated the daily sales report.\n\n"
							+ "To see the file, go to the system's path then go\n"
							+ "to reports -> business directory respectively.", 
						Utility.BUSINESS_TITLE, 
						JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		lblMonthlyReportButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				gallery.buttonHovered(lblMonthlyReportButton);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				gallery.buttonNormalized(lblMonthlyReportButton);
			}
			
			@Override public void mouseClicked(MouseEvent e) {
				if (report.generateMonthlyReport(false)) {
					logger.addLog(Logger.LEVEL_3, 
						String.format("User %s have generated the monthly sales report file for the previous month.", user[0].toString()));
					
					JOptionPane.showMessageDialog(
						null, "Successfully generated the monthly sales report.\n\n"
							+ "To see the file, go to the system's path then go\n"
							+ "to reports -> business directory respectively.", 
						Utility.BUSINESS_TITLE, 
						JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblDateTime.setText(gallery.getTime(minWidthTrigger));
			}
		});
		timer.start();
		
		setLocationRelativeTo(null);
		setVisible(true);
		/**
		 *  ========================= End of constructor ====================================
		 */
	}
	
	
	/**
	 * Used for global shortcut keys from this specified window only.
	 * <br> To make the usage more efficient and faster.
	 * 
	 * @param code keyCode from the KeyEvent
	 */
	private void systemKeyMappingShortcuts(int code) {
		if (code == KeyEvent.VK_F1) {
			checkOut();
		} else if (code == KeyEvent.VK_F4) {
			clearCart(false);
		}
	}

	/**
	 * Used for search shortcut keys from this specified table only.
	 * 
	 * @param code code keyCode from the KeyEvent
	 */
	private void tableKeyMappingShortcuts(int code) {
		int previousIndex = tableSelectedIndex;
		
		if (code == KeyEvent.VK_PAGE_DOWN) {
			tableNextPage();
		} else if (code == KeyEvent.VK_PAGE_UP) {
			tablePreviousPage();
		}
		
		if (code == KeyEvent.VK_LEFT) {
			if (tableSelectedIndex - 1 >= 0
					&& tableSelectedIndex % tableMaxPerColumn != 0) {
				tableSelectedIndex--;
			}
		} else if (code == KeyEvent.VK_UP) {
			if (tableSelectedIndex - tableMaxPerColumn >= 0) {
				tableSelectedIndex -= tableMaxPerColumn;
			}
		} else if (code == KeyEvent.VK_RIGHT) {
			if (tableSelectedIndex + 1 <= productUIs.length - 1
					&& (tableSelectedIndex + 1) % tableMaxPerColumn != 0) {
				tableSelectedIndex++;
			}
		} else if (code == KeyEvent.VK_DOWN) {
			if (tableSelectedIndex + tableMaxPerColumn <= productUIs.length - 1) {
				tableSelectedIndex += tableMaxPerColumn;
			} else {
				tableSelectedIndex = productUIs.length - 1;
			}
		}

		if (previousIndex != tableSelectedIndex) {
			selectProduct(tableSelectedIndex);
		}
	}
	
	private void setTransactionCount() {
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		Date start = calendar.getTime();
		
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		
		Date end = calendar.getTime();
		
		Object[][] transactions = database.getTransactionsByRange(start, end);
		int count = 0;
		
		if (transactions != null) {
			count = transactions.length + 1;
		}
		
		lblTransactionNo.setText(transactionMessage + count);
	}
	
	public void setSelectedIndex(int selectedIndex) {
		this.tableSelectedIndex = selectedIndex;
		
		for (ProductDisplay productDisplay : productUIs) {
			if (productDisplay.getSelected() && productDisplay.getIndex() != this.tableSelectedIndex) {
				productDisplay.unselect();
			}
		}
	}
	
	private void searchQuery() {
		String keyword = 
			(tfSearch.getText() == null || tfSearch.getText().equals(defaultSearchMessage)) 
			? "" : tfSearch.getText();
		
		queryResult = database.getProductsByKeyword(keyword);
		
		// Empty query result
		if (queryResult.length == 0) {
			tableSelectedIndex = -1;
			lblNotFoundImage.setText(
				String.format("<html><p>No results found with the<br>keyword \"%s\"</p></html>", 
					(keyword.length() > 7) 
					? keyword.substring(0, Math.min(keyword.length(), 6)) + "..."
					: keyword
			));
			lblPageIndicator.setText("");
			lblUpButton.setVisible(false);
			lblDownButton.setVisible(false);
			queryCardLayout.show(cardLayoutPanel, "none");
		
		// Query Result
		} else {
			ProductDisplay firstProduct = new ProductDisplay(queryResultPanel.getSize(), 
					0, queryResult[0], gallery, this);
			tableMaxPerPage = firstProduct.getMaxPerPage();
			tableMaxPerColumn = firstProduct.getMaxColumn();
			
			tableTotalPage = (queryResult.length / tableMaxPerPage) + 
				((queryResult.length % tableMaxPerPage > 0) ? 1 : 0);
			tableCurrentPage = 1;
			
			displayResults();
			selectProduct(0);
			
			lblUpButton.setVisible(true);
			lblDownButton.setVisible(true);
		}
	}
	
	private void displayResults() {
		queryResultPanel.removeAll();
		Dimension panelSize = queryResultPanel.getSize();
		
		int productPanelSize = 0;
		
		if (queryResult.length > tableMaxPerPage * tableCurrentPage) {
			productPanelSize = tableMaxPerPage;
		} else {
			productPanelSize = tableMaxPerPage - ((tableMaxPerPage * tableCurrentPage) - queryResult.length);
		}
		
		productUIs = new ProductDisplay[productPanelSize];

		lblPageIndicator.setText(String.format(
				"<html>"
				+ "<p style=\"text-align: center;\">"
				+ "%s<hr>%s"
				+ "</html>", 
				tableCurrentPage, 
				tableTotalPage));
		
		for (int queryIndex = (tableCurrentPage - 1) * tableMaxPerPage; 
				 queryIndex < (tableCurrentPage * tableMaxPerPage) - (tableMaxPerPage - productUIs.length); 
				 queryIndex++) {
			productUIs[queryIndex % tableMaxPerPage] = new ProductDisplay(
				panelSize, queryIndex, queryResult[queryIndex], gallery, this);
			queryResultPanel.add(productUIs[queryIndex % tableMaxPerPage]);
		}

		repaint();
		revalidate();
		
		queryCardLayout.show(cardLayoutPanel, "result");
	}
	
	public void displayCart() {
		cartListPanel.removeAll();
		if (cartList.isEmpty()) {
			cartListCardLayout.show(cartListCardPanel, "empty");
			
			lblLeftButton.setVisible(false);
			lblRightButton.setVisible(false);
			lblCartIndicator.setVisible(false);
		} else {
			cartListCardLayout.show(cartListCardPanel, "cart");

			lblLeftButton.setVisible(true);
			lblRightButton.setVisible(true);
			lblCartIndicator.setVisible(true);
		}
		
		int beforeCartTotalPage = cartTotalPage;
		cartTotalPage = (cartList.size() - 1) / cartMaxPerPage + 1;
		
		if (beforeCartTotalPage != cartTotalPage) {
			cartCurrentPage = cartTotalPage;
		}
		
		lblCartIndicator.setText(String.format(
				"<html>"
				+ "<p style=\"text-align: center;\">"
				+ "<sup>%s</sup>&frasl;"
				+ "<sub>%s</sub>"
				+ "</html>", 
				cartCurrentPage, 
				cartTotalPage));
		try {
			for (int cartIndex = 0; cartIndex < cartMaxPerPage; cartIndex++) {
				CartItem item = cartList.get(cartIndex + (cartMaxPerPage * (cartCurrentPage - 1)));
				item.rearrangeOrder(cartIndex);
				cartListPanel.add(item);
			}
		} catch (IndexOutOfBoundsException arrayException) {}
		
		repaint();
		revalidate();
		
		updatePaymentStatistics();
	}
	
	private void addToCart() {
		// Constructor for error handling to be displayed into the custom error message display
		int quantity = 0;
		ArrayList<String> errorMessages = new ArrayList<>(); // REQUIRED

		// If conditions to handle all kinds of possible errors
		if (tableSelectedIndex == -1) { 
			// Adding of error message, format:
			errorMessages.add("- Please select a product."); 
		}
		
		if (tfQuantity.getText().equals(defaultQuantityMessage) || 
				tfQuantity.getText().equals("")) {
			errorMessages.add("- Please input the quantity of the product.");
		} else {
			try {
				quantity = Integer.parseInt(tfQuantity.getText());
				if (quantity < 1 || quantity > 999) {
					errorMessages.add("- Please input quantity ranging from 1 to 999.");
				}
			} catch (NumberFormatException nfe) {
				errorMessages.add("- Only integers are allowed as product quantity.");
			}
		}
		
		// Condition statement to check if there are one or more errors.
		if (errorMessages.size() > 0) { // REQUIRED
			gallery.showMessage(errorMessages.toArray(new String[0])); // REQUIRED
		} else { 
			// REQUIRED the code under this will be the only correct path
			cartListPanel.removeAll();
			
			long selectedID = (long) queryResult[tableSelectedIndex + (tableMaxPerPage * (tableCurrentPage - 1))][0];
			
			boolean cartItemExisting = false;
			for (CartItem cartItem : cartList) {
				if ((long) cartItem.getTransactionDetail()[0] == selectedID) {
					
					cartItem.adjustQuantity(quantity);
					cartItemExisting = true;
				}
			} 
			
			if (!cartItemExisting) {
				cartList.add(
					new CartItem(cartList.size(), 
						queryResult[tableSelectedIndex + (tableMaxPerPage * (tableCurrentPage - 1))], 
						quantity, this)
				);
			}
			
			displayCart();
			
			tfQuantity.setText("");
		}
		tfSearch.requestFocus(true);
		tfSearch.selectAll();
	}
	
	private boolean requestManagerPermission() {
		int rank = Integer.parseInt(Character.toString(user[0].toString().charAt(1)));
		boolean proceed = false;
		
		if (rank == 1) {
			// Store Clerk
			// Manager password request panel setup and execution
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			JLabel label = new JLabel(clearCartMessage);
			JPasswordField pass = new JPasswordField(10);
			String[] options = new String[]{"OK", "Cancel"};
			
			panel.add(label);
			panel.add(pass);
			
			// Manager password request dialog shows up
			int option = 
				JOptionPane
					.showOptionDialog(null, panel, Utility.BUSINESS_TITLE,
			                         JOptionPane.NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
			                         null, options, pass);
			
			// Manager password return value
			String managerPassword = "";
			if(option == 0) {
			    char[] password = pass.getPassword();
			    managerPassword = utility.hashData(new String(password));
				String[] hashes = database.fetchManagerHashes();
				
				boolean hasEqual = false;
				for (String hash : hashes) {
					if (managerPassword.equals(hash)) {
						hasEqual = true;
					}
				}
				
				if (hasEqual) {
					proceed = true;
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect password", 
							Utility.BUSINESS_TITLE, JOptionPane.WARNING_MESSAGE);
				}
			}
		} else if (rank == 2) {
			// Manager
			proceed = true;
		}
		
		if (proceed) {
			return true;
		}
		
		
		return false;
	}
	
	public void removeToCart(int cartIndex) {
		if (requestManagerPermission()) {
			cartList.remove(cartIndex);
			
			for (int adjustmentIndex = cartIndex; 
					adjustmentIndex < cartList.size(); 
					adjustmentIndex++) {
				cartList.get(adjustmentIndex).rearrangeOrder(adjustmentIndex);
			}
			
			displayCart();
		}
	}
	
	public void clearCart(boolean bypass) {
		if (cartList.size() == 0) {
			gallery.showMessage(new String[] {"The cart has no items to cancel."});
		} else {
			boolean proceed = false;
			if (!bypass) {
				proceed = requestManagerPermission();
			} else {
				proceed = true;
			}
			
			if (proceed) {
				StringBuilder productsIncluded = new StringBuilder();
				cartList.forEach((item) -> productsIncluded.append(item.getName() + ", "));
				productsIncluded.setLength(Math.max(productsIncluded.length() - 2, 0));
				
				if (!bypass) {
					logger.addLog(Logger.LEVEL_3, String.format("User %s cancelled a cart including the items: %s", 
							user[0].toString(), productsIncluded.toString()));
				}
				
				cartList.clear();
				displayCart();
			}
		}
	}
	
	private void scrollTable(MouseWheelEvent e) {
		int rotation = e.getWheelRotation();
		if (rotation == 1) {
			// Mouse is rotated downward
			tableNextPage();
		} else {
			// Mouse is rotated upward
			tablePreviousPage();
		}
	}
	
	private void scrollCartList(MouseWheelEvent e) {
		int rotation = e.getWheelRotation();
		if (rotation == 1) {
			// Mouse is rotated downward
			cartNextPage();
		} else {
			// Mouse is rotated upward
			cartPreviousPage();
		}
	}
	
	private void tableNextPage() {
		if (tableCurrentPage < tableTotalPage) { 
			tableCurrentPage++;
			displayResults();
			selectProduct(0);
		}
	}
	
	private void tablePreviousPage() {
		if (tableCurrentPage > 1) { 
			tableCurrentPage--; 
			displayResults();
			selectProduct(0);
		}
	}
	
	private void cartNextPage() {
		if (cartCurrentPage < cartTotalPage) { 
			cartCurrentPage++;
			displayCart();
		}
	}
	
	private void cartPreviousPage() {
		if (cartCurrentPage > 1) { 
			cartCurrentPage--;
			displayCart();
		}
	}
	
	private void resetAllPages() {
		cartMaxPerPage = cartListPanel.getSize().height / CartItem.staticHeight;
		
		cartCurrentPage = 1;
		tableCurrentPage = 1;
		
		searchQuery();
		displayCart();
	}
	
	private void selectProduct(int index) {
		MouseEvent me = new MouseEvent(productUIs[index], 0, 0, 0, 100, 100, 1, false);
		productUIs[index].getMouseListeners()[0].mouseClicked(me);
	}
	
	public int getCartMaxPerPage() {
		return cartMaxPerPage;
	}
	
	public void updatePaymentStatistics() {
		totalCartQuantity = 0;
		totalCartPrice = 0.0;
		
		for (int cartIndex = 0; cartIndex < cartList.size(); cartIndex++) {
			Object[] cartItemDetails = cartList.get(cartIndex).getTransactionDetail();
			totalCartQuantity += (int) cartItemDetails[4];
			totalCartPrice += (double) cartItemDetails[7];
		}
		
		lblPaymentCashier.setText(String.format("Cashier:  %s", cashierName));
		lblPaymentCartSize.setText(String.format("Cart items:  %,d  product%s", cartList.size(),
				(cartList.size() == 1) ? "" : "s"));
		lblPaymentCartQuantity.setText(String.format("Cart contents:  %,d  piece%s", totalCartQuantity,
				(totalCartQuantity == 1) ? "" : "s"));
		lblPaymentCartTotalPrice.setText(String.format("Total price:  P%,.2f", totalCartPrice));
	}
	
	private void checkOut() {
		if (cartList.size() == 0) {
			gallery.showMessage(new String[] {"The cart has no items to checkout."});
		} else {
			new Checkout(user, cartList, this);
		}
	}
	
	private String getTransactionStatistics() {
		int totalCount = 0;
		int dayCount = 0;
		int weekCount = 0;
		int monthCount = 0;
		
		// TOTAL
		Object[][] transactionsTotal = database.getTransactionsByKeyword("");
		if (transactionsTotal != null) {
			totalCount = transactionsTotal.length;
		}
		// TOTAL
		
		Calendar calendar = Calendar.getInstance();
		
		// Reseting day
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);		
		Date dayStart = calendar.getTime();
		
		// Saving todays date
		int currentDayOfMonth = calendar.get(Calendar.DAY_OF_YEAR);
		int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		// Resetting week
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date weekStart = calendar.getTime();
		
		// Resetting month
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date monthStart = calendar.getTime();
		
		// Roll back to current date
		calendar.set(Calendar.DAY_OF_YEAR, currentDayOfMonth);
		
		// Adding 1 day
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		Date dayEnd = calendar.getTime();
		
		// Roll back to current date
		calendar.set(Calendar.DAY_OF_YEAR, currentDayOfMonth);
		
		// Going to Saturday for week
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		Date weekEnd = calendar.getTime();
		
		// Roll back to current date
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		calendar.set(Calendar.DAY_OF_WEEK, currentDayOfWeek);
		calendar.set(Calendar.DAY_OF_YEAR, currentDayOfMonth);
		
		// Getting the last day of month
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date monthEnd = calendar.getTime();
		
		Object[][] dayTransactions = database.getTransactionsByRange(dayStart, dayEnd);
		if (dayTransactions != null) {
			dayCount = dayTransactions.length;
		}
		
		Object[][] weekTransactions = database.getTransactionsByRange(weekStart, weekEnd);
		if (weekTransactions != null) {
			weekCount = weekTransactions.length;
		}
		
		Object[][] monthTransactions = database.getTransactionsByRange(monthStart, monthEnd);
		if (monthTransactions != null) {
			monthCount = monthTransactions.length;
		}

		StringBuilder stats = new StringBuilder("<html><h2>Transaction Statistics</h2>");
		stats.append("This day:  " + dayCount + " transaction(s)");
		stats.append("<br>This week:  " + weekCount + " transaction(s)");
		stats.append("<br>This month:  " + monthCount + " transaction(s)");
		stats.append("<br>All time: "  + totalCount + " transaction(s)");
		stats.append("</html>");
		
		
		return stats.toString();
	}
	
	public void updateSaleStatistics() {
		productMostSales = statistic.getProductMostSales();
		productLeastSales = statistic.getProductLeastSales();
		categorySales = statistic.getCategorySale();
		userSales = statistic.getUserMostSales();
		
		StringBuilder statisticMessage = new StringBuilder("<html>");
		String contentFormatting = "%d. %s @ Php %,.2f<br>";
		int nameLimit = 25;
		int numbering = 1;
		
		if (productMostSales == null) {
			statisticMessage.append("1. None<br>2. None<br> 3. None</html>");
		} else {
			numbering = 1;
			for (Object[] product : productMostSales) {
				String name = product[0].toString();
				double sales = (double) product[1];
				
				statisticMessage.append(
					String.format(contentFormatting, numbering,
						(name.length() > nameLimit) ? name.substring(0, nameLimit - 2) + "..." : name,
						sales));
				
				numbering++;
			}
			statisticMessage.append("</html>");
		}
		
		lblProductMostSaleList.setText(statisticMessage.toString());
		statisticMessage = new StringBuilder("<html>");
		numbering = 1;
		
		if (productLeastSales == null) {
			statisticMessage.append("1. None<br>2. None<br> 3. None</html>");
		} else {
			numbering = 1;
			for (Object[] product : productLeastSales) {
				String name = product[0].toString();
				double sales = (double) product[1];
				
				statisticMessage.append(
					String.format(contentFormatting, numbering,
						(name.length() > nameLimit) ? name.substring(0, nameLimit - 2) + "..." : name,
						sales));
				
				numbering++;
			}
			statisticMessage.append("</html>");
		}
		
		lblProductLeastSaleList.setText(statisticMessage.toString());
		statisticMessage = new StringBuilder("<html>");
		numbering = 1;
		
		if (categorySales == null) {
			statisticMessage.append("1. None<br>2. None<br> 3. None</html>");
		} else {
			numbering = 1;
			for (Object[] product : categorySales) {
				String name = product[0].toString();
				double sales = (double) product[1];
				
				statisticMessage.append(
					String.format(contentFormatting, numbering,
						(name.length() > nameLimit) ? name.substring(0, nameLimit - 2) + "..." : name,
						sales));
				
				numbering++;
			}
			statisticMessage.append("</html>");
		}
		
		lblCategorySaleList.setText(statisticMessage.toString());
		statisticMessage = new StringBuilder("<html>");
		numbering = 1;
		
		if (userSales == null) {
			statisticMessage.append("1. None<br>2. None<br> 3. None</html>");
		} else {
			numbering = 1;
			for (Object[] product : userSales) {
				String name = product[0].toString();
				double sales = (double) product[1];
				
				statisticMessage.append(
					String.format(contentFormatting, numbering,
						(name.length() > nameLimit) ? name.substring(0, nameLimit - 2) + "..." : name,
						sales));
				
				numbering++;
			}
			statisticMessage.append("</html>");
		}
		
		lblUserSaleList.setText(statisticMessage.toString());
	}
}

