package pos;

import java.awt.CardLayout;
import java.awt.Color;
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
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import utils.Database;
import utils.Gallery;
import utils.RoundedPanel;
import utils.Utility;
import utils.VerticalLabelUI;


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

	private String defaultSearchMessage = "Search for products...";
	private String defaultQuantityMessage = "How many?";
	
	private int defaultHeight = 600; // 600
	private int defaultWidth = 990; // 1000
	private int minHeight = 600;
	private int minWidth = 990;
	
	private boolean breakpointTrigger = false;
	private Timer timer;
	
	// Customized query table variables
	private Object[][] queryResult;
	private ProductDisplay[] productUIs;
	private int tableMaxPerColumn = 3;
	private int tableMaxPerPage = 6;
	private int tableSelectedIndex = -1;
	private int tableCurrentPage = 1;
	private int tableTotalPage = 1;

	// Customized cart list variables
	private ArrayList<CartItem> cartList = new ArrayList<>();
	private int cartMaxPerPage = 11;
	private int cartCurrentPage = 1;
	private int cartTotalPage = 1;
	
	private Database database; 
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
		lblCartListEmpty;
	
	private JTextField tfSearch, tfQuantity;
	
	private SpringLayout sl_mainPanel, sl_posPanel;
	
	private CardLayout cardLayout, queryCardLayout, cartListCardLayout;
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				POS frame = new POS();
				frame.setVisible(true);
			}
		});
	}

	public POS() {
		database = new Database();
		gallery = new Gallery();
		
		// rotated 90 degrees counter-clockwise
		verticalUI = new VerticalLabelUI(false); 
		
		setTitle(POS_TITLE + Utility.TITLE_SEPARATOR + Utility.APP_TITLE);
		setMinimumSize(new Dimension(minWidth, minHeight));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
		
		lblTransactionNo = new JLabel("Transaction No. 1");
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
		
		lblDateTime = new JLabel(gallery.getTime(breakpointTrigger));
		sl_posPanel.putConstraint(SpringLayout.SOUTH, lblDateTime, -3, SpringLayout.SOUTH, lblTransactionNo);
		sl_posPanel.putConstraint(SpringLayout.EAST, lblDateTime, -20, SpringLayout.WEST, cartPanel);
		lblDateTime.setFont(gallery.getFont(15f));
		posPanel.add(lblDateTime);
		
		paymentPanel = new RoundedPanel(Color.WHITE);
		sl_posPanel.putConstraint(SpringLayout.NORTH, paymentPanel, -120, SpringLayout.SOUTH, cartPanel);
		sl_posPanel.putConstraint(SpringLayout.SOUTH, paymentPanel, 0, SpringLayout.SOUTH, cartPanel);
		sl_posPanel.putConstraint(SpringLayout.EAST, paymentPanel, -15, SpringLayout.WEST, cartPanel);
		posPanel.add(paymentPanel);
		
		checkoutPanel = new RoundedPanel(Color.WHITE);
		sl_posPanel.putConstraint(SpringLayout.EAST, checkoutPanel, 220, SpringLayout.WEST, posPanel);
		sl_posPanel.putConstraint(SpringLayout.WEST, paymentPanel, 15, SpringLayout.EAST, checkoutPanel);
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
		paymentPanel.setLayout(new SpringLayout());
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
		
		lblCartListEmpty = new JLabel(gallery.getImage("empty-cart.png", 200, 295));
		lblCartListEmpty.setHorizontalAlignment(SwingConstants.CENTER);
		sl_cardListEmptyPanel.putConstraint(SpringLayout.NORTH, lblCartListEmpty, 0, SpringLayout.NORTH, cardListEmptyPanel);
		sl_cardListEmptyPanel.putConstraint(SpringLayout.WEST, lblCartListEmpty, 0, SpringLayout.WEST, cardListEmptyPanel);
		sl_cardListEmptyPanel.putConstraint(SpringLayout.SOUTH, lblCartListEmpty, 0, SpringLayout.SOUTH, cardListEmptyPanel);
		sl_cardListEmptyPanel.putConstraint(SpringLayout.EAST, lblCartListEmpty, 0, SpringLayout.EAST, cardListEmptyPanel);
		cardListEmptyPanel.add(lblCartListEmpty);
		queryResultPanel.setBackground(Gallery.WHITE);
		queryResultPanel.setLayout(null);
		
		lblPageIndicator = new JLabel();
		sl_tableContainerPanel.putConstraint(SpringLayout.WEST, lblPageIndicator, -45, SpringLayout.EAST, tableContainerPanel);
		sl_tableContainerPanel.putConstraint(SpringLayout.EAST, cardLayoutPanel, -5, SpringLayout.WEST, lblPageIndicator);
		sl_tableContainerPanel.putConstraint(SpringLayout.WEST, lblUpButton, 0, SpringLayout.WEST, lblPageIndicator);
		sl_tableContainerPanel.putConstraint(SpringLayout.NORTH, lblPageIndicator, 15, SpringLayout.NORTH, tableContainerPanel);
		sl_tableContainerPanel.putConstraint(SpringLayout.EAST, lblPageIndicator, -15, SpringLayout.EAST, tableContainerPanel);
		lblPageIndicator.setHorizontalAlignment(SwingConstants.CENTER);
		lblPageIndicator.setFont(gallery.getFont(15f));
		tableContainerPanel.add(lblPageIndicator);
		
		transactionPanel = new RoundedPanel(Color.BLUE); // Gallery.GRAY
		displayPanel.add(transactionPanel, "transaction");
		transactionPanel.setLayout(new SpringLayout());
		
		
		
		
		
		
		
		reportPanel = new RoundedPanel(Color.GREEN); // Gallery.GRAY
		displayPanel.add(reportPanel, "report");
		reportPanel.setLayout(new SpringLayout());
		
		
		
		
		
		
		
		// Configure initial component state
		lblLeftButton.setVisible(false);
		lblRightButton.setVisible(false);
		lblCartIndicator.setVisible(false);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				
				breakpointTrigger = getWidth() <= minWidth;
				lblDateTime.setText(gallery.getTime(breakpointTrigger));
				
				resetAllPages();
				tfSearch.requestFocus();
			}
		});
		lblDashboardNav.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblDashboardNav); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblDashboardNav); }
			
			@Override public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "pos");
				setTitle(POS_TITLE + Utility.TITLE_SEPARATOR + Utility.APP_TITLE);
			}
		});
		lblTransactionNav.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblTransactionNav); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblTransactionNav); }
			
			@Override public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "transaction");
				setTitle(TRANSACTION_TITLE + Utility.TITLE_SEPARATOR + Utility.APP_TITLE);
			}
		});
		lblReportNav.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblReportNav); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblReportNav); }
			
			@Override public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "report");
				setTitle(REPORTS_TITLE + Utility.TITLE_SEPARATOR + Utility.APP_TITLE);
			}
		});
		lblCheckoutButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblCheckoutButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblCheckoutButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				// TODO: Add function for the checkout button
			}
		});
		lblCancelButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblCancelButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblCancelButton); }
			
			@Override public void mouseClicked(MouseEvent e) { clearCart(); }
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
				gallery.buttonHovered(lblDownButton);
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
				gallery.buttonHovered(lblUpButton);
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
			public void keyReleased(KeyEvent e) {
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
		
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblDateTime.setText(gallery.getTime(breakpointTrigger));
			}
		});
		timer.start();
		
		setLocationRelativeTo(null);
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
			// TODO Checkout
			
		} else if (code == KeyEvent.VK_F2) { // Not included
			/**
			 * TEST ONLY FOR DEBUGGING
			 */
			System.out.println("\n\nCart count: " + cartList.size());
			cartList.forEach((cartItem) -> System.out.println(cartItem));
			System.out.println("========================================");
		} else if (code == KeyEvent.VK_F4) {
			clearCart();
		} else if (code == KeyEvent.VK_F5) {
			// TODO Remove only the last item on the cart
			
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
	}
	
	private void addToCart() {
		int quantity = 0;
		ArrayList<String> errorMessages = new ArrayList<>();

		if (tableSelectedIndex == -1) { errorMessages.add("- Please select a product."); }
		if (tfQuantity.getText().equals(defaultQuantityMessage) || 
				tfQuantity.getText().equals("")) {
			errorMessages.add("- Please input the quantity of the product.");
		} else {
			try {
				quantity = Integer.parseInt(tfQuantity.getText());
				if (quantity < 1 || quantity > 999) {
					errorMessages.add("- Please input the quantity between 1 and 999.");
				}
			} catch (NumberFormatException nfe) {
				errorMessages.add("- Only integers are allowed as product quantity.");
			}
		}
		
		if (errorMessages.size() > 0) {
			gallery.showMessage(errorMessages.toArray(new String[0]));
		} else {
			cartListPanel.removeAll();
			
			cartList.add(
				new CartItem(cartList.size(), 
					queryResult[tableSelectedIndex + (tableMaxPerPage * (tableCurrentPage - 1))], 
					quantity, gallery, this)
			);
			
			displayCart();
			
			tfQuantity.setText("");
			tfSearch.requestFocus(true);
		}
	}
	
	public void removeToCart(int cartIndex) {
		cartList.remove(cartIndex);
		
		for (int adjustmentIndex = cartIndex; 
				adjustmentIndex < cartList.size(); 
				adjustmentIndex++) {
			cartList.get(adjustmentIndex).rearrangeOrder(adjustmentIndex);
		}
		
		displayCart();
	}
	
	public void clearCart() {
		// TODO: Administrator/Manager authorization checker first, before clearing.
		
		cartList.clear();
		displayCart();
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
	
	public int getCartMaxPerPage() { return cartMaxPerPage; }
}


