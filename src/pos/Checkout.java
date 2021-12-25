package pos;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.RoundedPanel;
import utils.Utility;

@SuppressWarnings("serial")
public class Checkout extends JDialog {
	
	private final double VAT_RATE = 0.12;
	private final double MEDICINE_RATE = 0.20;
	private final double GROCERY_RATE = 0.05;
	private final double GROCERY_LIMIT = 1300.0;
	
	private final String TITLE = "Checkout";
	private final String[] customerTypes = {
		"Regular Customer", 
		"PWD/Senior (Medical, 20%, Non-VAT)", 
		"PWD/Senior (Grocery, 5%, VAT, Limit: P1,300/week)"};
	
	private String defaultVATMessage = "VAT (12%): ";
	private String defaultDiscountMessage = "Discount: ";
	
	private Logger logger;
	private Database database;
	private Gallery gallery;
	private Utility utility;
	private Receipt receipt;
	
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
	private JLabel lblTotalItems;
	private JLabel lblTotalItemsValue;
	private JLabel lblSubTotal;
	private JLabel lblSubTotalValue;
	private JLabel lblVAT;
	private JLabel lblVATValue;
	private JLabel lblDiscount;
	private JLabel lblDiscountValue;
	private JLabel lblTotal;
	private JLabel lblTotalValue;
	private JLabel lblAmountTendered;
	private JTextField tfAmountTendered;
	private JLabel lblAmountTenderedValue;
	private JLabel lblChange;
	private JLabel lblChangeValue;
	private JLabel lblEnterAmountTendered;
	private JLabel lblFinishButton;

	private Object[] user;
	private ArrayList<CartItem> cartList;
	private Object[][] customers;
	
	private double totalItems = 0.0;
	private double subTotal = 0.0;
	private double grossTotal = 0.0;
	private double vat = 0.0;
	private double scPwdDiscount = 0.0;
	private double total = 0.0;
	private double amountTendered = 0.0;
	private double change = 0.0;
	private JPanel receiptTitlePanel;
	private JLabel lblReceiptTitle;

	public Checkout(Object[] user, ArrayList<CartItem> cartList) {
		database = Database.getInstance();
		gallery = Gallery.getInstance();
		logger = Logger.getInstance();
		utility = Utility.getInstance();
		receipt = Receipt.getInstance(user, cartList);
		
		this.user = user;
		this.cartList = cartList;
		
		for (CartItem cartItem : cartList) {
			Object[] product = cartItem.getProduct();
			
			totalItems += cartItem.getQuantity();
			grossTotal += ((double) product[7] * cartItem.getQuantity());
		}
		vat = grossTotal * VAT_RATE;
		subTotal = grossTotal - vat;
		total = grossTotal;
		
		setIconImage(gallery.getSystemIcon());
		setTitle(TITLE + Utility.TITLE_SEPARATOR + Utility.BUSINESS_TITLE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(990, 710);
		setResizable(false);
		getContentPane().setBackground(Gallery.GRAY);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		receiptTitlePanel = new RoundedPanel(Gallery.BLUE);
		springLayout.putConstraint(SpringLayout.NORTH, receiptTitlePanel, -15, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, receiptTitlePanel, -385, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, receiptTitlePanel, 45, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, receiptTitlePanel, -45, SpringLayout.EAST, getContentPane());
		getContentPane().add(receiptTitlePanel);
		SpringLayout sl_receiptTitlePanel = new SpringLayout();
		receiptTitlePanel.setLayout(sl_receiptTitlePanel);
		
		lblReceiptTitle = new JLabel("Receipt Preview");
		lblReceiptTitle.setFont(gallery.getFont(17f));
		lblReceiptTitle.setForeground(Gallery.WHITE);
		lblReceiptTitle.setHorizontalAlignment(SwingConstants.CENTER);
		sl_receiptTitlePanel.putConstraint(SpringLayout.WEST, lblReceiptTitle, 30, SpringLayout.WEST, receiptTitlePanel);
		sl_receiptTitlePanel.putConstraint(SpringLayout.SOUTH, lblReceiptTitle, -12, SpringLayout.SOUTH, receiptTitlePanel);
		sl_receiptTitlePanel.putConstraint(SpringLayout.EAST, lblReceiptTitle, -30, SpringLayout.EAST, receiptTitlePanel);
		receiptTitlePanel.add(lblReceiptTitle);
		
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
		sl_discountPanel.putConstraint(SpringLayout.WEST, lblDiscountTitle, 45, SpringLayout.WEST, discountPanel);
		sl_discountPanel.putConstraint(SpringLayout.EAST, lblDiscountTitle, -45, SpringLayout.EAST, discountPanel);
		lblDiscountTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiscountTitle.setFont(gallery.getFont(19f));
		sl_discountPanel.putConstraint(SpringLayout.NORTH, lblDiscountTitle, 15, SpringLayout.NORTH, discountPanel);
		discountPanel.add(lblDiscountTitle);
		
		lblSelect = new JLabel("Customer Type: ");
		sl_discountPanel.putConstraint(SpringLayout.NORTH, lblSelect, 30, SpringLayout.SOUTH, lblDiscountTitle);
		lblSelect.setFont(gallery.getFont(14f));
		sl_discountPanel.putConstraint(SpringLayout.WEST, lblSelect, 0, SpringLayout.WEST, lblDiscountTitle);
		discountPanel.add(lblSelect);
		
		cbCustomerType = new JComboBox<String>();
		sl_discountPanel.putConstraint(SpringLayout.EAST, cbCustomerType, 0, SpringLayout.EAST, lblDiscountTitle);
		cbCustomerType.setFont(gallery.getFont(14f));
		cbCustomerType.setModel(new DefaultComboBoxModel<String>(customerTypes));
		sl_discountPanel.putConstraint(SpringLayout.NORTH, cbCustomerType, -2, SpringLayout.NORTH, lblSelect);
		sl_discountPanel.putConstraint(SpringLayout.SOUTH, cbCustomerType, 2, SpringLayout.SOUTH, lblSelect);
		discountPanel.add(cbCustomerType);
		
		lblSearch = new JLabel("Search Customer: ");
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
		sl_discountPanel.putConstraint(SpringLayout.NORTH, listCustomers, 0, SpringLayout.SOUTH, tfSearch);
		sl_discountPanel.putConstraint(SpringLayout.EAST, listCustomers, 0, SpringLayout.EAST, cbCustomerType);
		listCustomers.setEnabled(false);
		sl_discountPanel.putConstraint(SpringLayout.SOUTH, listCustomers, 67, SpringLayout.SOUTH, tfSearch);
		listCustomers.setFont(gallery.getFont(14f));
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
		sl_discountPanel.putConstraint(SpringLayout.WEST, cbCustomerType, 10, SpringLayout.EAST, lblSelected);
		lblSelected.setFont(gallery.getFont(14f));
		sl_discountPanel.putConstraint(SpringLayout.NORTH, lblSelected, 10, SpringLayout.SOUTH, lblUpdateButton);
		sl_discountPanel.putConstraint(SpringLayout.WEST, lblSelected, 0, SpringLayout.WEST, lblDiscountTitle);
		discountPanel.add(lblSelected);
		
		lblCustomerID = new JLabel("Customer Card ID: ");
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
		sl_receiptPanel.putConstraint(SpringLayout.NORTH, spReceipt, 45, SpringLayout.NORTH, receiptPanel);
		spReceipt.setBorder(null);
		spReceipt.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sl_receiptPanel.putConstraint(SpringLayout.WEST, spReceipt, 15, SpringLayout.WEST, receiptPanel);
		sl_receiptPanel.putConstraint(SpringLayout.SOUTH, spReceipt, -15, SpringLayout.SOUTH, receiptPanel);
		sl_receiptPanel.putConstraint(SpringLayout.EAST, spReceipt, -15, SpringLayout.EAST, receiptPanel);
		receiptPanel.add(spReceipt);
		
		taReceipt = new JTextArea(receipt.get(true));
		taReceipt.setEditable(false);
		taReceipt.setFont(gallery.getMonospacedFont(14f));
		spReceipt.setViewportView(taReceipt);
		getContentPane().add(checkoutPanel);
		SpringLayout sl_checkoutPanel = new SpringLayout();
		checkoutPanel.setLayout(sl_checkoutPanel);
		
		lblPaymentTitle = new JLabel("Payment");
		lblPaymentTitle.setHorizontalAlignment(SwingConstants.CENTER);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblPaymentTitle, 45, SpringLayout.WEST, checkoutPanel);
		sl_checkoutPanel.putConstraint(SpringLayout.EAST, lblPaymentTitle, -45, SpringLayout.EAST, checkoutPanel);
		lblPaymentTitle.setFont(gallery.getFont(19f));
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblPaymentTitle, 15, SpringLayout.NORTH, checkoutPanel);
		checkoutPanel.add(lblPaymentTitle);
		
		lblTotalItems = new JLabel("Total Items: ");
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblTotalItems, 5, SpringLayout.SOUTH, lblPaymentTitle);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblTotalItems, 0, SpringLayout.WEST, lblPaymentTitle);
		lblTotalItems.setFont(gallery.getFont(17f));
		checkoutPanel.add(lblTotalItems);
		
		lblTotalItemsValue = new JLabel(Double.toString(totalItems));
		sl_checkoutPanel.putConstraint(SpringLayout.EAST, lblTotalItemsValue, 0, SpringLayout.EAST, lblPaymentTitle);
		lblTotalItemsValue.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalItemsValue.setFont(gallery.getFont(17f));
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblTotalItemsValue, 0, SpringLayout.NORTH, lblTotalItems);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblTotalItemsValue, 6, SpringLayout.EAST, lblTotalItems);
		checkoutPanel.add(lblTotalItemsValue);
		
		lblSubTotal = new JLabel("Sub-total: ");
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblSubTotal, 2, SpringLayout.SOUTH, lblTotalItems);
		lblSubTotal.setFont(gallery.getFont(17f));
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblSubTotal, 0, SpringLayout.WEST, lblTotalItems);
		checkoutPanel.add(lblSubTotal);
		
		lblSubTotalValue = new JLabel(utility.formatCurrency(grossTotal - vat));
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblSubTotalValue, 5, SpringLayout.EAST, lblSubTotal);
		lblSubTotalValue.setFont(gallery.getFont(17f));
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblSubTotalValue, 0, SpringLayout.NORTH, lblSubTotal);
		lblSubTotalValue.setHorizontalAlignment(SwingConstants.RIGHT);
		sl_checkoutPanel.putConstraint(SpringLayout.EAST, lblSubTotalValue, 0, SpringLayout.EAST, lblTotalItemsValue);
		checkoutPanel.add(lblSubTotalValue);
		
		lblVAT = new JLabel(defaultVATMessage);
		lblVAT.setFont(gallery.getFont(17f));
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblVAT, 2, SpringLayout.SOUTH, lblSubTotal);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblVAT, 0, SpringLayout.WEST, lblTotalItems);
		checkoutPanel.add(lblVAT);
		
		lblVATValue = new JLabel(utility.formatCurrency(vat));
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblVATValue, 5, SpringLayout.EAST, lblVAT);
		lblVATValue.setFont(gallery.getFont(17f));
		sl_checkoutPanel.putConstraint(SpringLayout.EAST, lblVATValue, 0, SpringLayout.EAST, lblSubTotalValue);
		lblVATValue.setHorizontalAlignment(SwingConstants.RIGHT);
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblVATValue, 0, SpringLayout.NORTH, lblVAT);
		checkoutPanel.add(lblVATValue);
		
		lblDiscount = new JLabel(defaultDiscountMessage);
		lblDiscount.setFont(gallery.getFont(17f));
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblDiscount, 2, SpringLayout.SOUTH, lblVAT);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblDiscount, 0, SpringLayout.WEST, lblTotalItems);
		checkoutPanel.add(lblDiscount);
		
		lblDiscountValue = new JLabel(utility.formatCurrency(scPwdDiscount));
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblDiscountValue, 5, SpringLayout.EAST, lblDiscount);
		lblDiscountValue.setFont(gallery.getFont(17f));
		lblDiscountValue.setHorizontalAlignment(SwingConstants.RIGHT);
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblDiscountValue, 0, SpringLayout.NORTH, lblDiscount);
		sl_checkoutPanel.putConstraint(SpringLayout.EAST, lblDiscountValue, 0, SpringLayout.EAST, lblVATValue);
		checkoutPanel.add(lblDiscountValue);
		
		lblTotal = new JLabel("Total: ");
		lblTotal.setFont(gallery.getFont(17f));
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblTotal, 2, SpringLayout.SOUTH, lblDiscount);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblTotal, 0, SpringLayout.WEST, lblTotalItems);
		checkoutPanel.add(lblTotal);
		
		lblTotalValue = new JLabel(utility.formatCurrency(total));
		lblTotalValue.setFont(gallery.getFont(17f));
		lblTotalValue.setHorizontalAlignment(SwingConstants.RIGHT);
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblTotalValue, 0, SpringLayout.NORTH, lblTotal);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblTotalValue, 5, SpringLayout.EAST, lblTotal);
		sl_checkoutPanel.putConstraint(SpringLayout.EAST, lblTotalValue, 0, SpringLayout.EAST, lblDiscountValue);
		checkoutPanel.add(lblTotalValue);
		
		lblAmountTendered = new JLabel("Amount Tendered: ");
		lblAmountTendered.setFont(gallery.getFont(17f));
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblAmountTendered, 0, SpringLayout.WEST, lblPaymentTitle);
		checkoutPanel.add(lblAmountTendered);
		
		lblAmountTenderedValue = new JLabel(utility.formatCurrency(amountTendered));
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblAmountTenderedValue, 0, SpringLayout.NORTH, lblAmountTendered);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblAmountTenderedValue, 5, SpringLayout.EAST, lblAmountTendered);
		sl_checkoutPanel.putConstraint(SpringLayout.EAST, lblAmountTenderedValue, 0, SpringLayout.EAST, lblTotalValue);
		lblAmountTenderedValue.setFont(gallery.getFont(17f));
		lblAmountTenderedValue.setHorizontalAlignment(SwingConstants.RIGHT);
		checkoutPanel.add(lblAmountTenderedValue);
		
		lblChange = new JLabel("Change: ");
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblChange, 2, SpringLayout.SOUTH, lblAmountTendered);
		lblChange.setFont(gallery.getFont(17f));
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblChange, 0, SpringLayout.WEST, lblPaymentTitle);
		checkoutPanel.add(lblChange);
		
		lblChangeValue = new JLabel("<html><p style=\"color: red\">" + utility.formatCurrency(change) + "</p></html>");
		lblChangeValue.setFont(gallery.getFont(17f));
		lblChangeValue.setHorizontalAlignment(SwingConstants.RIGHT);
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblChangeValue, 0, SpringLayout.NORTH, lblChange);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblChangeValue, 5, SpringLayout.EAST, lblChange);
		sl_checkoutPanel.putConstraint(SpringLayout.EAST, lblChangeValue, 0, SpringLayout.EAST, lblAmountTenderedValue);
		checkoutPanel.add(lblChangeValue);
		
		lblEnterAmountTendered = new JLabel("Enter Amount Tendered: ");
		lblEnterAmountTendered.setFont(gallery.getFont(17f));
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblEnterAmountTendered, 0, SpringLayout.WEST, lblPaymentTitle);
		checkoutPanel.add(lblEnterAmountTendered);

		tfAmountTendered = new JTextField();
		tfAmountTendered.setDocument(utility.new LengthRestrictedDocument(9));
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, tfAmountTendered, -2, SpringLayout.NORTH, lblEnterAmountTendered);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, tfAmountTendered, 15, SpringLayout.EAST, lblEnterAmountTendered);
		sl_checkoutPanel.putConstraint(SpringLayout.SOUTH, tfAmountTendered, 2, SpringLayout.SOUTH, lblEnterAmountTendered);
		sl_checkoutPanel.putConstraint(SpringLayout.EAST, tfAmountTendered, 0, SpringLayout.EAST, lblChangeValue);
		tfAmountTendered.setFont(gallery.getFont(17f));
		tfAmountTendered.setHorizontalAlignment(SwingConstants.RIGHT);
		checkoutPanel.add(tfAmountTendered);
		tfAmountTendered.setColumns(10);
		
		JSeparator separator1 = new JSeparator();
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblAmountTendered, 2, SpringLayout.SOUTH, separator1);
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, separator1, 2, SpringLayout.SOUTH, lblTotal);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, separator1, 0, SpringLayout.WEST, lblTotal);
		sl_checkoutPanel.putConstraint(SpringLayout.EAST, separator1, 0, SpringLayout.EAST, lblTotalValue);
		checkoutPanel.add(separator1);
		
		JSeparator separator2 = new JSeparator();
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, lblEnterAmountTendered, 7, SpringLayout.SOUTH, separator2);
		sl_checkoutPanel.putConstraint(SpringLayout.NORTH, separator2, 2, SpringLayout.SOUTH, lblChange);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, separator2, 0, SpringLayout.WEST, lblChange);
		sl_checkoutPanel.putConstraint(SpringLayout.EAST, separator2, 0, SpringLayout.EAST, lblChangeValue);
		checkoutPanel.add(separator2);
		
		lblFinishButton = new JLabel("FINISH TRANSACTION");
		lblFinishButton.setName("primary");
		gallery.styleLabelToButton(lblFinishButton, 17f, 5, 5);
		lblFinishButton.setHorizontalAlignment(SwingConstants.CENTER);
		sl_checkoutPanel.putConstraint(SpringLayout.WEST, lblFinishButton, 0, SpringLayout.WEST, lblPaymentTitle);
		sl_checkoutPanel.putConstraint(SpringLayout.SOUTH, lblFinishButton, -15, SpringLayout.SOUTH, checkoutPanel);
		sl_checkoutPanel.putConstraint(SpringLayout.EAST, lblFinishButton, 0, SpringLayout.EAST, lblPaymentTitle);
		checkoutPanel.add(lblFinishButton);
		

		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				updateListCustomers();
			}
			
			public void windowLostFocus(WindowEvent e) {}
		});
		cbCustomerType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedCustomerType = cbCustomerType.getSelectedIndex();
				scPwdDiscount = 0.0;
				
				if (selectedCustomerType == 0) {
					// Regular customer
					tfSearch.setText("");
					tfSearch.setEditable(false);
					listCustomers.clearSelection();
					listCustomers.setEnabled(false);
					
					lblVAT.setText(defaultVATMessage);
					lblDiscount.setText(defaultDiscountMessage);

					lblSelectedValue.setText("None");
					lblCustomerIDValue.setText("None");
					
					total = grossTotal;
					subTotal = grossTotal - vat;

					lblVATValue.setText(utility.formatCurrency(vat));
					lblDiscountValue.setText(utility.formatCurrency(scPwdDiscount));
					
				} else if (selectedCustomerType == 1) {
					// PWD/Senior Citizen (Medical) (20%)
					tfSearch.setEditable(true);
					listCustomers.setEnabled(true);

					lblVAT.setText("VAT (12%) (Exempted): ");
					lblDiscount.setText("Discount (20%): ");
					
					scPwdDiscount = (grossTotal - vat) * MEDICINE_RATE;
					total = grossTotal - (scPwdDiscount + vat);
					subTotal = grossTotal;

					lblVATValue.setText(utility.formatCurrency(-vat));
					lblDiscountValue.setText(utility.formatCurrency(-scPwdDiscount));
					
				} else if (selectedCustomerType == 2) {
					// PWD/Senior Citizen (Grocery) (5%)
					if (grossTotal > GROCERY_LIMIT) {
						gallery.showMessage(
							new String[] {
								"According to the Joint DTI-DA-DOE Administrative Order No.17-01 to No.17-02, "
								+ "The weekly limit for grocery discount for both senior citizen and PWD is P1,300.00 "
								+ "per calendar week (Current: " + utility.formatCurrency(grossTotal) + "). "
								+ "Please decrease some products or inform the customer."});
						dispose();
						
					} else {
						tfSearch.setEditable(true);
						listCustomers.setEnabled(true);

						lblVAT.setText("VAT (12%) (Included): ");
						lblDiscount.setText("Discount (5%): ");

						scPwdDiscount = grossTotal * GROCERY_RATE;
						total = grossTotal - scPwdDiscount;
						subTotal = grossTotal - vat;

						lblVATValue.setText(utility.formatCurrency(vat));
						lblDiscountValue.setText(utility.formatCurrency(-scPwdDiscount));
						
					}
				}

				lblSubTotalValue.setText(utility.formatCurrency(subTotal));
				lblTotalValue.setText(utility.formatCurrency(total));
				updateChange();
			}
		});
		lblNewButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblNewButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblNewButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				new RegisterCustomer(user);
			}
		});
		lblUpdateButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblUpdateButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblUpdateButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				if (cbCustomerType.getSelectedIndex() == 0) {
					gallery.showMessage(new String[] {"- Please unselect the 'Regular Customer' to update a customer."});
				} else {
					int selectedIndex = listCustomers.getSelectedIndex();
					
					if (selectedIndex == -1) {
						gallery.showMessage(new String[] {"- Please select a customer to update."});
					} else {
						new UpdateCustomer(user, customers[selectedIndex]);
					}
				}
			}
		});
		tfAmountTendered.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					finishTransaction();
				} else {
					updateChange();
				}
			}
		});
		lblFinishButton.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) { gallery.buttonHovered(lblFinishButton); }
			@Override public void mouseExited(MouseEvent e) { gallery.buttonNormalized(lblFinishButton); }
			
			@Override public void mouseClicked(MouseEvent e) {
				// TODO atleast a joptionpane success
				// TODO transaction insertion to the database
				finishTransaction();
			}
		});
		tfSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateListCustomers();
			}
		});
		listCustomers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				updateSelectedCustomer();
			}
		});
		listCustomers.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateSelectedCustomer();
			}
		});
		
		updateListCustomers();
		updateReceiptPayment();
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void updateListCustomers() {
		Object[][] fetchedCustomers = database.getCustomerDiscountsByKeyword(tfSearch.getText());
		ArrayList<Object[]> finalCustomers = new ArrayList<>();
		
		for (Object[] fetched : fetchedCustomers) {
			if (!fetched[0].toString().equals("3000000000")) {
				finalCustomers.add(fetched);
			}
		}
		
		customers = new Object[finalCustomers.size()][6];
		for (int customerIndex = 0; customerIndex < finalCustomers.size(); customerIndex++) {
			customers[customerIndex] = finalCustomers.get(customerIndex);
		}
		
		
		if (customers.length > 0) {
			ArrayList<String> customerNames = new ArrayList<>();
			
			for (Object[] customer : customers) {
				String fullname = customer[3].toString() + " " + customer[4].toString() + " " + customer[5].toString();
				customerNames.add(fullname);
			}
			
			listCustomers.setModel(new AbstractListModel<String>() {
				String[] values = customerNames.toArray(new String[0]);
				
				public int getSize() {
					return values.length;
				}
				
				public String getElementAt(int index) {
					return values[index];
				}
			});
		} else {
			DefaultListModel<String> model = new DefaultListModel<>();
			model.clear();
			
			listCustomers.setModel(model);
		}
	}
	
	private void updateSelectedCustomer() {
		int selectedIndex = listCustomers.getSelectedIndex();
		if (selectedIndex != -1) {
			Object[] customerSelected = customers[selectedIndex];
			
			lblSelectedValue.setText(customerSelected[3].toString() + " " 
					+ customerSelected[4].toString() + " " 
					+ customerSelected[5].toString());
			lblCustomerIDValue.setText(customerSelected[2].toString());
		}
		updateReceiptPayment();
	}
	
	private void updateChange() {
		try {
			double amount = Double.parseDouble(tfAmountTendered.getText());
			lblAmountTenderedValue.setText(utility.formatCurrency(amount));
			
			amountTendered = amount;
			change = amountTendered - total;
		} catch (NullPointerException | NumberFormatException ex) {
			amountTendered = 0.0;
			change = amountTendered;

			lblAmountTenderedValue.setText(utility.formatCurrency(amountTendered));
		}
		
		lblChangeValue.setText("<html><p style=\"color: red\">" + utility.formatCurrency(change) + "</p></html>");
		updateReceiptPayment();
	}
	
	private void updateReceiptPayment() {
		int selectedIndex = listCustomers.getSelectedIndex();
		if (selectedIndex != -1) {
			receipt.setCustomer(customers[selectedIndex]);
		} else {
			receipt.setCustomer(null);
		}
		
		receipt.setTotalItems(totalItems);
		receipt.setSubTotal(subTotal);
		receipt.setTax(vat);
		receipt.setDiscount(scPwdDiscount);
		receipt.setTotal(total);
		receipt.setAmountTendered(amountTendered);
		
		taReceipt.setText(receipt.get(true));
		taReceipt.setCaretPosition(0);
	}
	
	private void finishTransaction() {
		System.out.println("gegege");
	}
}
