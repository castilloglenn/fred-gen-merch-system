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
import java.util.ArrayList;

import javax.swing.BoxLayout;
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
import javax.swing.Icon;


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
	private int maxPerColumn = 3;
	private int maxPerPage = 6;
	private int selectedIndex = -1;
	private int currentPage = 1;
	private int totalPage = 1;

	// Customized cart list variables
	private ArrayList<CartItem> cartList = new ArrayList<>();
	
	private Database database; 
	private Gallery gallery;
	private VerticalLabelUI verticalUI;
	
	private JPanel mainPanel, cardLayoutPanel, queryEmptyPanel,
		queryResultPanel, cartListPanel;
	
	private RoundedPanel navigationPanel, displayPanel, 
		posPanel, transactionPanel, reportPanel,
		cartPanel, paymentPanel, checkoutPanel,
		searchPanel, tableContainerPanel;
	
	private JLabel lblDashboardNav, lblTransactionNav, lblReportNav,
		lblTransactionNo, lblDateTime, lblCheckoutButton,
		lblCancelButton, lblSearchIcon, lblAddToCart,
		lblQuantityIcon, lblDownButton, lblUpButton,
		lblNotFoundImage, lblPageIndicator, lblLeftButton,
		lblCartLabel, lblRightButton, lblCartIndicator;
	
	private JTextField tfSearch, tfQuantity;
	
	private SpringLayout sl_mainPanel, sl_posPanel;
	
	private CardLayout cardLayout, queryCardLayout;
	
	
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
		
		lblTransactionNo = new JLabel("Transaction No. 81273");
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
		sl_posPanel.putConstraint(SpringLayout.WEST, paymentPanel, 15, SpringLayout.EAST, checkoutPanel);
		sl_posPanel.putConstraint(SpringLayout.SOUTH, checkoutPanel, 0, SpringLayout.SOUTH, cartPanel);
		sl_posPanel.putConstraint(SpringLayout.NORTH, checkoutPanel, 0, SpringLayout.NORTH, paymentPanel);
		sl_posPanel.putConstraint(SpringLayout.EAST, checkoutPanel, 170, SpringLayout.WEST, posPanel);
		posPanel.add(checkoutPanel);
		SpringLayout sl_checkoutPanel = new SpringLayout();
		checkoutPanel.setLayout(sl_checkoutPanel);
		
		lblCheckoutButton = new JLabel("CHECK OUT");
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
		 
		lblCancelButton = new JLabel("CANCEL");
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
		sl_cartPanel.putConstraint(SpringLayout.WEST, lblLeftButton, 75, SpringLayout.WEST, cartPanel);
		lblLeftButton.setName("primary");
		gallery.styleLabelToButton(lblLeftButton, 15f, "arrow-left.png", 25, 10, 3);
		sl_cartPanel.putConstraint(SpringLayout.SOUTH, lblLeftButton, -15, SpringLayout.SOUTH, cartPanel);
		cartPanel.add(lblLeftButton);
		
		lblRightButton = new JLabel();
		sl_cartPanel.putConstraint(SpringLayout.EAST, lblRightButton, -75, SpringLayout.EAST, cartPanel);
		lblRightButton.setName("primary");
		gallery.styleLabelToButton(lblRightButton, 15f, "arrow-right.png", 25, 10, 3);
		sl_cartPanel.putConstraint(SpringLayout.SOUTH, lblRightButton, -15, SpringLayout.SOUTH, cartPanel);
		cartPanel.add(lblRightButton);
		
		lblCartIndicator = new JLabel("1/1");
		lblCartIndicator.setFont(gallery.getFont(15f));
		sl_cartPanel.putConstraint(SpringLayout.NORTH, lblCartIndicator, 0, SpringLayout.NORTH, lblLeftButton);
		sl_cartPanel.putConstraint(SpringLayout.EAST, lblCartIndicator, 0, SpringLayout.WEST, lblRightButton);
		lblCartIndicator.setHorizontalAlignment(SwingConstants.CENTER);
		sl_cartPanel.putConstraint(SpringLayout.WEST, lblCartIndicator, 0, SpringLayout.EAST, lblLeftButton);
		sl_cartPanel.putConstraint(SpringLayout.SOUTH, lblCartIndicator, 0, SpringLayout.SOUTH, lblLeftButton);
		cartPanel.add(lblCartIndicator);
		
		cartListPanel = new JPanel();
		sl_cartPanel.putConstraint(SpringLayout.NORTH, cartListPanel, 15, SpringLayout.SOUTH, lblCartLabel);
		sl_cartPanel.putConstraint(SpringLayout.SOUTH, cartListPanel, -15, SpringLayout.NORTH, lblLeftButton);
		cartListPanel.setBackground(Gallery.WHITE);
		sl_cartPanel.putConstraint(SpringLayout.WEST, cartListPanel, 15, SpringLayout.WEST, cartPanel);
		sl_cartPanel.putConstraint(SpringLayout.EAST, cartListPanel, -15, SpringLayout.EAST, cartPanel);
		cartPanel.add(cartListPanel);
		cartListPanel.setLayout(null);
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
		sl_tableContainerPanel.putConstraint(SpringLayout.EAST, cardLayoutPanel, -5, SpringLayout.WEST, lblDownButton);
		lblDownButton.setName("secondary");
		gallery.styleLabelToButton(lblDownButton, 1f, "arrow-down.png", 22, 0, 0);
		tableContainerPanel.add(lblDownButton);
		
		lblUpButton = new JLabel();
		sl_tableContainerPanel.putConstraint(SpringLayout.NORTH, lblUpButton, -45, SpringLayout.NORTH, lblDownButton);
		sl_tableContainerPanel.putConstraint(SpringLayout.SOUTH, lblUpButton, -10, SpringLayout.NORTH, lblDownButton);
		sl_tableContainerPanel.putConstraint(SpringLayout.WEST, lblDownButton, 0, SpringLayout.WEST, lblUpButton);
		sl_tableContainerPanel.putConstraint(SpringLayout.EAST, lblDownButton, 0, SpringLayout.EAST, lblUpButton);
		sl_tableContainerPanel.putConstraint(SpringLayout.WEST, lblUpButton, -50, SpringLayout.EAST, tableContainerPanel);
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
		queryResultPanel.setBackground(Gallery.WHITE);
		queryResultPanel.setLayout(null);
		
		lblPageIndicator = new JLabel();
		sl_tableContainerPanel.putConstraint(SpringLayout.NORTH, lblPageIndicator, 15, SpringLayout.NORTH, tableContainerPanel);
		sl_tableContainerPanel.putConstraint(SpringLayout.WEST, lblPageIndicator, -50, SpringLayout.EAST, tableContainerPanel);
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
		
		
		
		
		
		
		
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				
				breakpointTrigger = getWidth() <= minWidth;
				lblDateTime.setText(gallery.getTime(breakpointTrigger));
				
				resetPage();
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
				System.out.println("Checkout");
			}
		});
		lblCancelButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblCancelButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblCancelButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				// TODO: pass refresh of the cartlist here
				
			}
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
				nextPage();
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
				previousPage();
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
				
				// Catching all arrow-key codes to contain it in a single condition
				if (code >= 37 && code <= 40) {
					// If the selected index is initially empty or just recently unselected a product..
					if (selectedIndex == -1) {
						selectProduct(0);
					} else {
						int previousIndex = selectedIndex;
						
//						System.out.println("===========================\nColumns: "
//							+ maxPerColumn + "\nRow: " + maxPerRow 
//							+ "\nProduct UI size: " + productUIs.length
//							+ "\nSelected Index: " + selectedIndex);
						if (code == KeyEvent.VK_LEFT) {
							if (selectedIndex - 1 >= 0
									&& selectedIndex % maxPerColumn != 0) {
								selectedIndex--;
							}
						} else if (code == KeyEvent.VK_UP) {
							if (selectedIndex - maxPerColumn >= 0) {
								selectedIndex -= maxPerColumn;
							}
						} else if (code == KeyEvent.VK_RIGHT) {
							if (selectedIndex + 1 <= productUIs.length - 1
									&& (selectedIndex + 1) % maxPerColumn != 0) {
								selectedIndex++;
							}
						} else if (code == KeyEvent.VK_DOWN) {
							if (selectedIndex + maxPerColumn <= productUIs.length - 1) {
								selectedIndex += maxPerColumn;
							} else {
								selectedIndex = productUIs.length - 1;
							}
						}

						if (previousIndex != selectedIndex) {
							selectProduct(selectedIndex);
						}
					}
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
		tableContainerPanel.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) { scrollTable(e); }
		});
		
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblDateTime.setText(gallery.getTime(breakpointTrigger));
//				System.out.println(selectedIndex + " Name: " + ((selectedIndex != -1) ? productUIs[selectedIndex].getName() : "None"));
			}
		});
		timer.start();
		
		setLocationRelativeTo(null);
	}
	
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
		
		for (ProductDisplay productDisplay : productUIs) {
			if (productDisplay.getSelected() && productDisplay.getIndex() != this.selectedIndex) {
				productDisplay.unselect();
			}
		}

		System.out.println("Current index " + this.selectedIndex + ", " + 
			((selectedIndex != -1) ? productUIs[selectedIndex].getName() : "None"));
	}
	
	private void searchQuery() {
		String keyword = 
			(tfSearch.getText() == null || tfSearch.getText().equals(defaultSearchMessage)) 
			? "" : tfSearch.getText();
		
		queryResult = database.getProductsByKeyword(keyword);
		
		// Empty query result
		if (queryResult.length == 0) {
			selectedIndex = -1;
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
			maxPerPage = firstProduct.getMaxPerPage();
			maxPerColumn = firstProduct.getMaxColumn();
			
			totalPage = (queryResult.length / maxPerPage) + 
				((queryResult.length % maxPerPage > 0) ? 1 : 0);
			
			displayResults();
			selectProduct(0);
			lblUpButton.setVisible(true);
			lblDownButton.setVisible(true);
		}
	}
	
	private void displayResults() {
		// First, we will clean the panel of its child components
		queryResultPanel.removeAll();
		Dimension panelSize = queryResultPanel.getSize();

		productUIs = new ProductDisplay[Math.min(
		    maxPerPage - ((maxPerPage * currentPage) - queryResult.length), 
		    maxPerPage)];

		lblPageIndicator.setText(String.format("%s/%s", currentPage, totalPage));
		for (int queryIndex = (currentPage - 1) * maxPerPage; 
				 queryIndex < (currentPage * maxPerPage) - (maxPerPage - productUIs.length); 
				 queryIndex++) {
			productUIs[queryIndex % maxPerPage] = new ProductDisplay(
				panelSize, queryIndex, queryResult[queryIndex], gallery, this);
			queryResultPanel.add(productUIs[queryIndex % maxPerPage]);
		}

		repaint();
		revalidate();
		queryCardLayout.show(cardLayoutPanel, "result");
	}
	
	private void addToCart() {
		// TODO: Add to cart list the product selected
		int quantity = 0;
		ArrayList<String> errorMessages = new ArrayList<>();

		if (selectedIndex == -1) { errorMessages.add("- Please select a product."); }
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
			System.out.println("Add to cart, Selected Index: " + selectedIndex);
			cartListPanel.removeAll();
			
			cartList.add(
				new CartItem(
					cartList.size(), 
					queryResult[selectedIndex + (maxPerPage * (currentPage - 1))], 
					quantity, gallery, this)
			);
			System.out.println("Cart list size: " + cartList.size());
			
			tfQuantity.setText("");
			tfSearch.requestFocus(true);
			
			displayCart();
			System.out.println("JPanel size: " + cartListPanel.getComponents().length);
			
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
	
	public void displayCart() {
		// TODO: Complete this
		cartListPanel.removeAll();
		cartList.forEach((cartItem -> cartListPanel.add(cartItem)));
		repaint();
		revalidate();
	}
	
	private void scrollTable(MouseWheelEvent e) {
		int rotation = e.getWheelRotation();
		if (rotation == 1) {
			// Mouse is rotated downward
			nextPage();
		} else {
			// Mouse is rotated upward
			previousPage();
		}
	}
	
	private void nextPage() {
		if (currentPage < totalPage) { 
			currentPage++;
			displayResults();
			selectProduct(0);
		}
	}
	
	private void previousPage() {
		if (currentPage > 1) { 
			currentPage--; 
			displayResults();
			selectProduct(0);
		}
	}
	
	private void resetPage() {
		currentPage = 1;
		searchQuery();
	}
	
	private void selectProduct(int index) {
		MouseEvent me = new MouseEvent(productUIs[index], 0, 0, 0, 100, 100, 1, false);
		productUIs[index].getMouseListeners()[0].mouseClicked(me);
	}
}


