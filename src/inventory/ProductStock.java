package inventory;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.RoundedPanel;
import utils.Utility;

@SuppressWarnings("serial")
public class ProductStock extends JDialog {
	
	private final String[] title = {
			"Receive Stock", "Pull-out"
	};
	private final Color[] titleBG = {
			Gallery.BLUE, Gallery.RED
	};
	
	private Gallery gallery;
	private Database database;
	private Logger logger;
	
	private JPanel mainPanel;
	private JPanel titlePanel;
	
	private long[] supplierIDs;
	private String[] supplierNames;
	
	private JLabel lblTitle;
	private JLabel lblSupplier;
	private JComboBox<String> comboSupplier;
	private JLabel lblQuantity;
	private JTextField txtQuantity;
	private JLabel lblProceedButton;
	

	public ProductStock(int config, Object[] user, Object[] product) {
		gallery = Gallery.getInstance();
		database = Database.getInstance();
		logger = Logger.getInstance();
		
		setTitle(Utility.APP_TITLE);
		setIconImage(gallery.getSystemIcon());
		setResizable(false);
		setSize(550, 250);
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		getContentPane().setBackground(Gallery.GRAY);
		
		mainPanel = new RoundedPanel(Gallery.WHITE);
		springLayout.putConstraint(SpringLayout.NORTH, mainPanel, 15, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, mainPanel, 15, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, mainPanel, -15, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, mainPanel, -15, SpringLayout.EAST, getContentPane());
		getContentPane().add(mainPanel);
		SpringLayout sl_mainPanel = new SpringLayout();
		mainPanel.setLayout(sl_mainPanel);
		
		titlePanel = new RoundedPanel(titleBG[config]);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, titlePanel, 0, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, titlePanel, 0, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, titlePanel, 50, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, titlePanel, 0, SpringLayout.EAST, mainPanel);
		mainPanel.add(titlePanel);
		SpringLayout sl_titlePanel = new SpringLayout();
		titlePanel.setLayout(sl_titlePanel);
		
		lblTitle = new JLabel(title[config] + "/" + product[2].toString());
		sl_titlePanel.putConstraint(SpringLayout.WEST, lblTitle, 15, SpringLayout.WEST, titlePanel);
		sl_titlePanel.putConstraint(SpringLayout.EAST, lblTitle, -15, SpringLayout.EAST, titlePanel);
		lblTitle.setFont(gallery.getFont(20f));
		lblTitle.setForeground(Gallery.WHITE);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		sl_titlePanel.putConstraint(SpringLayout.NORTH, lblTitle, 0, SpringLayout.NORTH, titlePanel);
		sl_titlePanel.putConstraint(SpringLayout.SOUTH, lblTitle, 0, SpringLayout.SOUTH, titlePanel);
		titlePanel.add(lblTitle);
		
		lblSupplier = new JLabel("Select Supplier: ");
		sl_mainPanel.putConstraint(SpringLayout.NORTH, lblSupplier, 15, SpringLayout.SOUTH, titlePanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, lblSupplier, 15, SpringLayout.WEST, titlePanel);
		lblSupplier.setFont(gallery.getFont(14f));
		mainPanel.add(lblSupplier);
		
		comboSupplier = new JComboBox<String>();
		sl_mainPanel.putConstraint(SpringLayout.NORTH, comboSupplier, -2, SpringLayout.NORTH, lblSupplier);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, comboSupplier, 2, SpringLayout.SOUTH, lblSupplier);
		sl_mainPanel.putConstraint(SpringLayout.EAST, comboSupplier, -15, SpringLayout.EAST, mainPanel);
		mainPanel.add(comboSupplier);
		
		lblQuantity = new JLabel("Number of stocks: ");
		sl_mainPanel.putConstraint(SpringLayout.NORTH, lblQuantity, 10, SpringLayout.SOUTH, lblSupplier);
		lblQuantity.setFont(gallery.getFont(14f));
		sl_mainPanel.putConstraint(SpringLayout.WEST, lblQuantity, 0, SpringLayout.WEST, lblSupplier);
		mainPanel.add(lblQuantity);
		
		txtQuantity = new JTextField();
		sl_mainPanel.putConstraint(SpringLayout.EAST, txtQuantity, -15, SpringLayout.EAST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, comboSupplier, 0, SpringLayout.WEST, txtQuantity);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, txtQuantity, -2, SpringLayout.NORTH, lblQuantity);
		sl_mainPanel.putConstraint(SpringLayout.WEST, txtQuantity, 10, SpringLayout.EAST, lblQuantity);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, txtQuantity, 2, SpringLayout.SOUTH, lblQuantity);
		mainPanel.add(txtQuantity);
		txtQuantity.setColumns(10);
		
		lblProceedButton = new JLabel("Proceed");
		sl_mainPanel.putConstraint(SpringLayout.EAST, lblProceedButton, 0, SpringLayout.EAST, txtQuantity);
		lblProceedButton.setName("primary");
		gallery.styleLabelToButton(lblProceedButton, 14f, 5, 5);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, lblProceedButton, 15, SpringLayout.SOUTH, txtQuantity);
		sl_mainPanel.putConstraint(SpringLayout.WEST, lblProceedButton, 0, SpringLayout.WEST, comboSupplier);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, lblProceedButton, -15, SpringLayout.SOUTH, mainPanel);
		mainPanel.add(lblProceedButton);
		
		lblProceedButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(lblProceedButton);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(lblProceedButton);}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (checkField()) {
					long supplierID = supplierIDs[comboSupplier.getSelectedIndex()];
					long productID = (long) product[0];
					long userID = (long) user[0];
					double quantity = Double.parseDouble(txtQuantity.getText());
					double productPrice = (double) product[6];
					double totalPrice = quantity * productPrice;
					
					if (config == 0) {
						double newStock = (double) product[4] + quantity;
						if (database.addSupplies(supplierID, productID, userID, quantity, totalPrice)) {
							if (database.setProduct((long) product[0], product[1].toString(), product[2].toString(), 
									newStock, product[5].toString(), (double) product[7])) {
								logger.addLog(String.format("User %s increased the stock of product with the ID:%s by %s", 
										user[0].toString(), productID, Double.toString(quantity)));
								 
								JOptionPane.showMessageDialog(
									null, "Successfully increased the stock of product '" + product[2].toString() + "'", 
									Utility.APP_TITLE, 
									JOptionPane.INFORMATION_MESSAGE);
								 
								dispose();
							}
						}
					} else if (config == 1) {
						double newStock = (double) product[4] - quantity;
						if (database.addSupplies(supplierID, productID, userID, -quantity, totalPrice)) {
							if (database.setProduct((long) product[0], product[1].toString(), product[2].toString(), 
									newStock, product[5].toString(), (double) product[7])) {
								logger.addLog(String.format("User %s decreased the stock of product with the ID:%s by %s", 
										user[0].toString(), productID, Double.toString(quantity)));
								 
								JOptionPane.showMessageDialog(
									null, "Successfully decreased the stock of product '" + product[2].toString() + "'", 
									Utility.APP_TITLE, 
									JOptionPane.INFORMATION_MESSAGE);
								 
								dispose();
							}
						}
					}
				}
			}
		});
		
		setSuppliers();
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void setSuppliers() {
		Object[][] suppliers = database.getSuppliersByKeyword("");
		supplierIDs = new long[suppliers.length];
		supplierNames = new String[suppliers.length];
		
		for (int supplierIndex = 0; supplierIndex < suppliers.length; supplierIndex++) {
			supplierIDs[supplierIndex] = (long) suppliers[supplierIndex][0];
			supplierNames[supplierIndex] = suppliers[supplierIndex][1].toString();
		}
		
		DefaultComboBoxModel<String> supplierModel = new DefaultComboBoxModel<>(supplierNames);
		comboSupplier.setModel(supplierModel);
	}
	
	private boolean checkField() {
		ArrayList<String> errorMessages = new ArrayList<>();
		
		if (txtQuantity.getText().isBlank()) {
			errorMessages.add("- Please enter a quantity of the stock being receive/pull-out.");
		} else {
			try {
				Integer.parseInt(txtQuantity.getText());
			} catch (NumberFormatException e) {
				errorMessages.add("- Invalid quantity, please input an integer value.");
			}
		}
		
		if (errorMessages.size() > 0) { 
			gallery.showMessage(errorMessages.toArray(new String[0]));
			return false;
		}
		
		return true;
	}
}
