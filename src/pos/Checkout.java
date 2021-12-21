package pos;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.RoundedPanel;
import utils.Utility;

import java.awt.Dialog.ModalityType;
import java.util.ArrayList;
import javax.swing.SpringLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.AbstractListModel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Checkout extends JDialog {

	private final String TITLE = "Checkout";
	private final String[] customerTypes = {"Regular Customer", "PWD/Senior Citizen"};
	
	private Logger logger;
	private Database database;
	private Gallery gallery;
	private Utility utility;
	
	private Object[] user;
	private ArrayList<CartItem> cartList;
	
	private String sampleTextDeleteThisAfter = 
			  "            PRIME BUSINESS SUPERMARKET\r\n"
			+ "   Primordial Retail & Sales Management System\r\n"
			+ "      Cavite State University - Imus Campus\r\n"
			+ "                 123-456-789-000\r\n"
			+ "==================================================\r\n"
			+ "  Date: 2021/05/17 14:52:09 PM\r\n"
			+ "  Transaction No: 1011621234329907\r\n"
			+ "  Cashier: ALLEN GLENN C.\r\n"
			+ "  Customer: ELON BOI MUSK\r\n"
			+ "==================================================\r\n"
			+ "\r\n"
			+ "  001: 5.0 Powerbank\r\n"
			+ "    @ Php 100.00 ................... Php 500.00\r\n"
			+ "  002: 7.0 Bag\r\n"
			+ "    @ Php 550.50 ................. Php 3,853.50\r\n\r\n"
			+ "  002: 7.0 Bag\r\n"
			+ "    @ Php 550.50 ................. Php 3,853.50\r\n"
			+ "  002: 7.0 Bag\r\n"
			+ "    @ Php 550.50 ................. Php 3,853.50\r\n"
			+ "  002: 7.0 Bag\r\n"
			+ "    @ Php 550.50 ................. Php 3,853.50\r\n"
			+ "  002: 7.0 Bag\r\n"
			+ "    @ Php 550.50 ................. Php 3,853.50"
			+ "\r\n"
			+ "  TOTAL ITEMS                              12.00\r\n"
			+ "\r\n"
			+ "  SUB-TOTAL                         Php 3,831.08\r\n"
			+ "  TAX                                 Php 522.42\r\n"
			+ "  TOTAL                             Php 4,353.50\r\n"
			+ "\r\n"
			+ "  AMOUNT TENDERED                   Php 5,000.00\r\n"
			+ "  CHANGE                              Php 646.50\r\n"
			+ "\r\n"
			+ "==================================================\r\n"
			+ "  Thank you for visiting our store, you can cont\r\n"
			+ "  act us via email castilloglenn@ymail.com and v\r\n"
			+ "  ia our social media facebook.com/ccastilloglen\r\n"
			+ "  n or our landline 0956-899-0812. Please come a\r\n"
			+ "                gain to our store.\r\n"
			+ "\r\n"
			+ "  This receipt is valid up to five (5) years sin\r\n"
			+ "  ce the date it has been printed. Items purchas\r\n"
			+ "  ed can be returned and exchange for goods but \r\n"
			+ "               cannot be refunded.\r\n"
			+ "";
	
	private JPanel receiptPanel;
	private JPanel discountPanel;
	private JPanel checkoutPanel;
	private JTextArea taReceipt;
	private JLabel lblDiscountTitle;
	private JLabel lblPaymentTitle;
	private JLabel lblSelect;
	private JComboBox<String> cbCustomerType;
	private JLabel lblSearch;
	private JTextField tfSearch;
	private JList<String> listCustomers;
	private JLabel lblUpdateButton;
	private JLabel lblNewButton;
	private JLabel lblSelected;
	private JLabel lblCustomerID;
	private JLabel lblSelectedValue;
	private JLabel lblCustomerIDValue;
	

	public Checkout(Object[] user, ArrayList<CartItem> cartList) {
		database = Database.getInstance();
		gallery = Gallery.getInstance();
		logger = Logger.getInstance();
		utility = Utility.getInstance();
		
		this.user = user;
		this.cartList = cartList;
		
		setIconImage(gallery.getSystemIcon());
		setTitle(TITLE + Utility.TITLE_SEPARATOR + Utility.BUSINESS_TITLE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(990, 710);
		setResizable(false);
		getContentPane().setBackground(Gallery.GRAY);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		receiptPanel = new RoundedPanel(Gallery.WHITE);
		springLayout.putConstraint(SpringLayout.NORTH, receiptPanel, 15, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, receiptPanel, -415, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, receiptPanel, -15, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, receiptPanel, -15, SpringLayout.EAST, getContentPane());
		getContentPane().add(receiptPanel);
		SpringLayout sl_receiptPanel = new SpringLayout();
		receiptPanel.setLayout(sl_receiptPanel);
		
		discountPanel = new RoundedPanel(Gallery.WHITE);
		springLayout.putConstraint(SpringLayout.NORTH, discountPanel, 15, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, discountPanel, 15, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, discountPanel, -15, SpringLayout.WEST, receiptPanel);
		getContentPane().add(discountPanel);
		
		checkoutPanel = new RoundedPanel(Gallery.WHITE);
		springLayout.putConstraint(SpringLayout.NORTH, checkoutPanel, -365, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, discountPanel, -15, SpringLayout.NORTH, checkoutPanel);
		SpringLayout sl_discountPanel = new SpringLayout();
		discountPanel.setLayout(sl_discountPanel);
		
		lblDiscountTitle = new JLabel("Apply Discount Priviledges");
		lblDiscountTitle.setFont(gallery.getFont(19f));
		sl_discountPanel.putConstraint(SpringLayout.NORTH, lblDiscountTitle, 15, SpringLayout.NORTH, discountPanel);
		sl_discountPanel.putConstraint(SpringLayout.WEST, lblDiscountTitle, 15, SpringLayout.WEST, discountPanel);
		discountPanel.add(lblDiscountTitle);
		
		lblSelect = new JLabel("Customer Type: ");
		sl_discountPanel.putConstraint(SpringLayout.NORTH, lblSelect, 30, SpringLayout.SOUTH, lblDiscountTitle);
		lblSelect.setFont(gallery.getFont(14f));
		sl_discountPanel.putConstraint(SpringLayout.WEST, lblSelect, 0, SpringLayout.WEST, lblDiscountTitle);
		discountPanel.add(lblSelect);
		
		cbCustomerType = new JComboBox<String>();
		cbCustomerType.setFont(gallery.getFont(14f));
		cbCustomerType.setModel(new DefaultComboBoxModel<String>(customerTypes));
		sl_discountPanel.putConstraint(SpringLayout.NORTH, cbCustomerType, -2, SpringLayout.NORTH, lblSelect);
		sl_discountPanel.putConstraint(SpringLayout.SOUTH, cbCustomerType, 2, SpringLayout.SOUTH, lblSelect);
		sl_discountPanel.putConstraint(SpringLayout.EAST, cbCustomerType, -15, SpringLayout.EAST, discountPanel);
		discountPanel.add(cbCustomerType);
		
		lblSearch = new JLabel("Search Customer: ");
		sl_discountPanel.putConstraint(SpringLayout.WEST, cbCustomerType, 15, SpringLayout.EAST, lblSearch);
		sl_discountPanel.putConstraint(SpringLayout.NORTH, lblSearch, 15, SpringLayout.SOUTH, lblSelect);
		lblSearch.setFont(gallery.getFont(14f));
		sl_discountPanel.putConstraint(SpringLayout.WEST, lblSearch, 0, SpringLayout.WEST, lblDiscountTitle);
		discountPanel.add(lblSearch);
		
		tfSearch = new JTextField();
		tfSearch.setEditable(false);
		tfSearch.setFont(gallery.getFont(14f));
		sl_discountPanel.putConstraint(SpringLayout.NORTH, tfSearch, -2, SpringLayout.NORTH, lblSearch);
		sl_discountPanel.putConstraint(SpringLayout.WEST, tfSearch, 0, SpringLayout.WEST, cbCustomerType);
		sl_discountPanel.putConstraint(SpringLayout.SOUTH, tfSearch, 2, SpringLayout.SOUTH, lblSearch);
		sl_discountPanel.putConstraint(SpringLayout.EAST, tfSearch, 0, SpringLayout.EAST, cbCustomerType);
		discountPanel.add(tfSearch);
		tfSearch.setColumns(10);
		
		listCustomers = new JList<String>();
		listCustomers.setEnabled(false);
		sl_discountPanel.putConstraint(SpringLayout.SOUTH, listCustomers, 67, SpringLayout.SOUTH, tfSearch);
		sl_discountPanel.putConstraint(SpringLayout.EAST, listCustomers, -1, SpringLayout.EAST, cbCustomerType);
		listCustomers.setModel(new AbstractListModel<String>() {
			String[] values = new String[] {"sample1", "sample2", "sample3"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		listCustomers.setFont(gallery.getFont(14f));
		sl_discountPanel.putConstraint(SpringLayout.NORTH, listCustomers, -1, SpringLayout.SOUTH, tfSearch);
		listCustomers.setBorder(new LineBorder(new Color(0, 0, 0)));
		listCustomers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sl_discountPanel.putConstraint(SpringLayout.WEST, listCustomers, 0, SpringLayout.WEST, cbCustomerType);
		discountPanel.add(listCustomers);
		
		lblUpdateButton = new JLabel("Update");
		sl_discountPanel.putConstraint(SpringLayout.WEST, lblUpdateButton, -75, SpringLayout.WEST, listCustomers);
		lblUpdateButton.setHorizontalAlignment(SwingConstants.CENTER);
		lblUpdateButton.setName("primary");
		gallery.styleLabelToButton(lblUpdateButton, 12f, 5, 5);
		sl_discountPanel.putConstraint(SpringLayout.SOUTH, lblUpdateButton, 0, SpringLayout.SOUTH, listCustomers);
		sl_discountPanel.putConstraint(SpringLayout.EAST, lblUpdateButton, -10, SpringLayout.WEST, listCustomers);
		discountPanel.add(lblUpdateButton);
		
		lblNewButton = new JLabel("New");
		sl_discountPanel.putConstraint(SpringLayout.WEST, lblNewButton, 0, SpringLayout.WEST, lblUpdateButton);
		sl_discountPanel.putConstraint(SpringLayout.SOUTH, lblNewButton, -5, SpringLayout.NORTH, lblUpdateButton);
		sl_discountPanel.putConstraint(SpringLayout.EAST, lblNewButton, 0, SpringLayout.EAST, lblUpdateButton);
		lblNewButton.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewButton.setName("primary");
		gallery.styleLabelToButton(lblNewButton, 12f, 5, 5);
		discountPanel.add(lblNewButton);

		lblSelected = new JLabel("Selected Customer: ");
		lblSelected.setFont(gallery.getFont(14f));
		sl_discountPanel.putConstraint(SpringLayout.NORTH, lblSelected, 10, SpringLayout.SOUTH, lblUpdateButton);
		sl_discountPanel.putConstraint(SpringLayout.WEST, lblSelected, 0, SpringLayout.WEST, lblDiscountTitle);
		discountPanel.add(lblSelected);
		
		lblCustomerID = new JLabel("Customer ID: ");
		lblCustomerID.setFont(gallery.getFont(14f));
		sl_discountPanel.putConstraint(SpringLayout.NORTH, lblCustomerID, 5, SpringLayout.SOUTH, lblSelected);
		sl_discountPanel.putConstraint(SpringLayout.WEST, lblCustomerID, 0, SpringLayout.WEST, lblDiscountTitle);
		discountPanel.add(lblCustomerID);
		
		lblSelectedValue = new JLabel("None");
		sl_discountPanel.putConstraint(SpringLayout.EAST, lblSelectedValue, 0, SpringLayout.EAST, tfSearch);
		lblSelectedValue.setFont(gallery.getFont(14f));
		sl_discountPanel.putConstraint(SpringLayout.NORTH, lblSelectedValue, 0, SpringLayout.NORTH, lblSelected);
		sl_discountPanel.putConstraint(SpringLayout.WEST, lblSelectedValue, 0, SpringLayout.WEST, cbCustomerType);
		discountPanel.add(lblSelectedValue);
		
		lblCustomerIDValue = new JLabel("None");
		lblCustomerIDValue.setFont(gallery.getFont(14f));
		sl_discountPanel.putConstraint(SpringLayout.NORTH, lblCustomerIDValue, 0, SpringLayout.NORTH, lblCustomerID);
		sl_discountPanel.putConstraint(SpringLayout.WEST, lblCustomerIDValue, 0, SpringLayout.WEST, cbCustomerType);
		sl_discountPanel.putConstraint(SpringLayout.EAST, lblCustomerIDValue, 0, SpringLayout.EAST, tfSearch);
		discountPanel.add(lblCustomerIDValue);
		springLayout.putConstraint(SpringLayout.WEST, checkoutPanel, 15, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, checkoutPanel, -15, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, checkoutPanel, -15, SpringLayout.WEST, receiptPanel);
		
		JScrollPane spReceipt = new JScrollPane();
		spReceipt.setBorder(null);
		spReceipt.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sl_receiptPanel.putConstraint(SpringLayout.NORTH, spReceipt, 15, SpringLayout.NORTH, receiptPanel);
		sl_receiptPanel.putConstraint(SpringLayout.WEST, spReceipt, 15, SpringLayout.WEST, receiptPanel);
		sl_receiptPanel.putConstraint(SpringLayout.SOUTH, spReceipt, -15, SpringLayout.SOUTH, receiptPanel);
		sl_receiptPanel.putConstraint(SpringLayout.EAST, spReceipt, -15, SpringLayout.EAST, receiptPanel);
		receiptPanel.add(spReceipt);
		
		taReceipt = new JTextArea(sampleTextDeleteThisAfter);
		taReceipt.setEditable(false);
		taReceipt.setFont(gallery.getMonospacedFont(14f));
		spReceipt.setViewportView(taReceipt);
		getContentPane().add(checkoutPanel);
		SpringLayout sl_checkoutPanel = new SpringLayout();
		checkoutPanel.setLayout(sl_checkoutPanel);
		
		lblPaymentTitle = new JLabel("Payment");
		lblPaymentTitle.setFont(gallery.getFont(19f));
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblPaymentTitle, 15, SpringLayout.NORTH, checkoutPanel);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblPaymentTitle, 15, SpringLayout.WEST, checkoutPanel);
		checkoutPanel.add(lblPaymentTitle);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		


		cbCustomerType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedCustomerType = cbCustomerType.getSelectedIndex();
				
				if (selectedCustomerType == 0) {
					// Regular customer
					tfSearch.setText("");
					tfSearch.setEditable(false);
					listCustomers.clearSelection();
					listCustomers.setEnabled(false);
					
				} else if (selectedCustomerType == 1) {
					// PWD/Senior Citizen
					tfSearch.setEditable(true);
					listCustomers.setEnabled(true);
					
				}
			}
		});
		lblNewButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblNewButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblNewButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				System.out.println(listCustomers.getSelectedValue());
				
			}
		});
		lblUpdateButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblUpdateButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblUpdateButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				System.out.println(listCustomers.getSelectedIndex());
				
			}
		});
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
