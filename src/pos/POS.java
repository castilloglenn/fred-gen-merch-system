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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.ReadOnlyFileSystemException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import utils.Database;
import utils.Gallery;
import utils.RoundedPanel;
import utils.Utility;
import utils.VerticalLabelUI;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.FlowLayout;
import java.awt.event.WindowStateListener;
import java.awt.event.WindowEvent;


/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
@SuppressWarnings("serial")
public class POS extends JFrame {
	
	private int defaultHeight = 600; // 600
	private int defaultWidth = 990; // 1000
	private int minHeight = 600;
	private int minWidth = 990;
	
	private boolean breakpointTrigger = false;
	private Timer timer;

	private String defaultSearchMessage = "Search for products...";
	private String defaultQuantityMessage = "How many?";
	
	private Object[][] queryResult;
	private int selectedIndex = 0;
	private int currentPage = 1;
	private int totalPage = 1;
	
	private Utility utility;
	private Database database; 
	private Gallery gallery;
	private VerticalLabelUI verticalUI;
	
	private JPanel mainPanel, cardLayoutPanel, queryEmptyPanel,
		queryResultPanel;
	
	private RoundedPanel navigationPanel, displayPanel, 
		posPanel, transactionPanel, reportPanel,
		cartPanel, paymentPanel, checkoutPanel,
		searchPanel, tableContainerPanel;
	
	private JLabel lblDashboardNav, lblTransactionNav, lblReportNav,
		lblTransactionNo, lblDateTime, lblCheckoutButton,
		lblCancelButton, lblSearchIcon, lblAddToCart,
		lblQuantityIcon, lblDownButton, lblUpButton,
		lblNotFoundImage;
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
		utility = new Utility();
		database = new Database();
		gallery = new Gallery();
		
		// rotated 90 degrees counter-clockwise
		verticalUI = new VerticalLabelUI(false); 
		
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
		cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.X_AXIS));
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
		queryCardLayout = new CardLayout();
		cardLayoutPanel.setBackground(Gallery.WHITE);
		sl_tableContainerPanel.putConstraint(SpringLayout.SOUTH, cardLayoutPanel, -15, SpringLayout.SOUTH, tableContainerPanel);
		sl_tableContainerPanel.putConstraint(SpringLayout.NORTH, cardLayoutPanel, 15, SpringLayout.NORTH, tableContainerPanel);
		sl_tableContainerPanel.putConstraint(SpringLayout.WEST, cardLayoutPanel, 15, SpringLayout.WEST, tableContainerPanel);
		tableContainerPanel.add(cardLayoutPanel);
		cardLayoutPanel.setLayout(queryCardLayout);
		
		lblDownButton = new JLabel();
		sl_tableContainerPanel.putConstraint(SpringLayout.EAST, cardLayoutPanel, -15, SpringLayout.WEST, lblDownButton);
		sl_tableContainerPanel.putConstraint(SpringLayout.WEST, lblDownButton, -50, SpringLayout.EAST, tableContainerPanel);
		sl_tableContainerPanel.putConstraint(SpringLayout.EAST, lblDownButton, -15, SpringLayout.EAST, tableContainerPanel);
		lblDownButton.setName("secondary");
		gallery.styleLabelToButton(lblDownButton, 1f, "arrow-down.png", 22, 0, 0);
		sl_tableContainerPanel.putConstraint(SpringLayout.NORTH, lblDownButton, -50, SpringLayout.SOUTH, tableContainerPanel);
		sl_tableContainerPanel.putConstraint(SpringLayout.SOUTH, lblDownButton, -15, SpringLayout.SOUTH, tableContainerPanel);
		tableContainerPanel.add(lblDownButton);
		
		lblUpButton = new JLabel();
		sl_tableContainerPanel.putConstraint(SpringLayout.NORTH, lblUpButton, -40, SpringLayout.NORTH, lblDownButton);
		sl_tableContainerPanel.putConstraint(SpringLayout.SOUTH, lblUpButton, -5, SpringLayout.NORTH, lblDownButton);
		lblUpButton.setName("secondary");
		gallery.styleLabelToButton(lblUpButton, 1f, "arrow-up.png", 22, 0, 0);
		sl_tableContainerPanel.putConstraint(SpringLayout.WEST, lblUpButton, 0, SpringLayout.WEST, lblDownButton);
		sl_tableContainerPanel.putConstraint(SpringLayout.EAST, lblUpButton, 0, SpringLayout.EAST, lblDownButton);
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
		// This set text must be set dynamically based on the input
		lblNotFoundImage.setText("<html><p>No results found with the<br>keyword");
		lblNotFoundImage.setFont(gallery.getFont(15f));
		queryEmptyPanel.add(lblNotFoundImage);
		
		queryResultPanel = new JPanel();
		cardLayoutPanel.add(queryResultPanel, "result");
		cardLayoutPanel.add(queryEmptyPanel, "none");
		queryResultPanel.setBackground(Gallery.WHITE);
		queryResultPanel.setLayout(null);
		
		
		
		
		
		
		
		
		
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
			}
		});
		lblDashboardNav.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblDashboardNav); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblDashboardNav); }
			
			@Override public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "pos");
			}
		});
		lblTransactionNav.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblTransactionNav); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblTransactionNav); }
			
			@Override public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "transaction");
			}
		});
		lblReportNav.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblReportNav); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblReportNav); }
			
			@Override public void mouseClicked(MouseEvent e) {
				cardLayout.show(displayPanel, "report");
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
			
			@Override public void mouseClicked(MouseEvent e) {
				// TODO: Cancel means clear the cart
			}
		});
		lblAddToCart.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblAddToCart); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblAddToCart); }
			
			@Override public void mouseClicked(MouseEvent e) {
				// TODO: Add to cart list the product selected
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
				// TODO: Turn the page to the next set
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
				// TODO: Turn the page to the previous set
				
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
				searchQuery();
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
		tableContainerPanel.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) { scrollTable(e); }
		});
		
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblDateTime.setText(gallery.getTime(breakpointTrigger));
			}
		});
		timer.start();
		
		setLocationRelativeTo(null);
	}
	
	private void searchQuery() {
		String keyword = (tfSearch.getText() == null) ? "" : tfSearch.getText();
		queryResult = database.getProductsByKeyword(keyword);
		
		// Empty query result
		if (queryResult.length == 0) {
			lblNotFoundImage.setText(
				String.format("<html><p>No results found with the<br>keyword \"%s\"</p></html>", 
					(keyword.length() > 7) 
					? keyword.substring(0, Math.min(keyword.length(), 6)) + "..."
					: keyword
			));
			queryCardLayout.show(cardLayoutPanel, "none");
		
		// Query Result
		} else {
//			for (Object[] row : queryResult) {
//				
//			}
			queryResultPanel.add(new ProductDisplay(queryResultPanel.getSize(), 0, database.getProductsByKeyword("apple")[0], gallery));
			repaint();
			revalidate();
			
			queryCardLayout.show(cardLayoutPanel, "result");
		}
	}
	
	private void scrollTable(MouseWheelEvent e) {
		int rotation = e.getWheelRotation();
		if (rotation == 1) {
			// Mouse is rotated downward
			
		} else {
			// Mouse is rotated upward
			
		}
	}
}


