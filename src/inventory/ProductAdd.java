package inventory;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.RoundedPanel;
import utils.Utility;

@SuppressWarnings("serial")
public class ProductAdd extends JFrame {
	
	private final String TITLE = "Add Product";

	private Database database;
	private Utility utility;
	private Gallery gallery;
	private Logger logger;
	private long[] supplierIDs;
	private String[] supplierNames;
	
	private String defaultIconPath;
	private String iconPath;
	
	private JPanel contentPane, p, newProductPanel, iconSelectionPanel, buttonPanel, formPanel;
	private JLabel lblNewProduct, btnConfirm,lblProductID,lblName,lblStocks,lblUOM,lblCategory,lblPriceBought,lblSupplier;
	private JTextField txtProductID,txtName,txtStocks,txtUOM,txtPriceBought;
	private JComboBox<String> comboSupplier;
	private JLabel lblSellingPrice;
	private JComboBox<String> comboCategory;
	private JTextField txtSellingPrice;
	private JLabel btnCancel;
	private JLabel lblPreviewIcon;
	private JLabel lblSelectIconButton;
	
		
	public ProductAdd(Object[] user) {
		defaultIconPath = System.getProperty("user.dir");
		defaultIconPath = defaultIconPath.replace("\\", "/");
		defaultIconPath = defaultIconPath + "/assets/images/product-default.png";
		iconPath = defaultIconPath;
		
		gallery = Gallery.getInstance();
		utility = Utility.getInstance();
		logger = Logger.getInstance();
		database = Database.getInstance();
		setIconImage(gallery.getSystemIcon());
		setTitle(TITLE + Utility.TITLE_SEPARATOR + Utility.BUSINESS_TITLE);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		p = new JPanel();
		p.setBackground(Gallery.GRAY);
		sl_contentPane.putConstraint(SpringLayout.NORTH, p, -5, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, p, -5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, p, 5, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, p, 5, SpringLayout.EAST, contentPane);
		SpringLayout sl_p = new SpringLayout();
		p.setLayout(sl_p);
		contentPane.add(p);
		
		newProductPanel = new RoundedPanel(Gallery.BLUE);
		sl_p.putConstraint(SpringLayout.NORTH, newProductPanel, -15, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.WEST, newProductPanel, -15, SpringLayout.WEST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, newProductPanel, 50, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.EAST, newProductPanel, 200, SpringLayout.WEST, p);
		SpringLayout sl_newProductPanel = new SpringLayout();
		newProductPanel.setLayout(sl_newProductPanel);
		p.add(newProductPanel);
		
		lblNewProduct = new JLabel("New Product");
		lblNewProduct.setFont(gallery.getFont(21f));
		sl_newProductPanel.putConstraint(SpringLayout.NORTH, lblNewProduct, 23, SpringLayout.NORTH, newProductPanel);
		sl_newProductPanel.putConstraint(SpringLayout.WEST, lblNewProduct, 35, SpringLayout.WEST, newProductPanel);
		lblNewProduct.setForeground(Color.WHITE);
		newProductPanel.add(lblNewProduct);
		
		formPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, formPanel, 15, SpringLayout.SOUTH, newProductPanel);
		sl_p.putConstraint(SpringLayout.WEST, formPanel, 15, SpringLayout.WEST, p);
		p.add(formPanel);
		
		iconSelectionPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.WEST, iconSelectionPanel, -79, SpringLayout.EAST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, iconSelectionPanel, 64, SpringLayout.NORTH, formPanel);
		sl_p.putConstraint(SpringLayout.EAST, iconSelectionPanel, -15, SpringLayout.EAST, p);
		sl_p.putConstraint(SpringLayout.EAST, formPanel, -15, SpringLayout.WEST, iconSelectionPanel);
		sl_p.putConstraint(SpringLayout.NORTH, iconSelectionPanel, 0, SpringLayout.NORTH, formPanel);
		SpringLayout sl_formPanel = new SpringLayout();
		formPanel.setLayout(sl_formPanel);
		
		lblProductID = new JLabel("Product ID");
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblProductID, 10, SpringLayout.NORTH, formPanel);
		lblProductID.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.WEST, lblProductID, 25, SpringLayout.WEST, formPanel);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblProductID, 125, SpringLayout.WEST, formPanel);
		formPanel.add(lblProductID);
		
		txtProductID = new JTextField(Long.toString(utility.generateProductID(database.fetchLastID("product", "product_id"))));
		txtProductID.setEditable(false);
		txtProductID.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtProductID, -2, SpringLayout.NORTH, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtProductID, 15, SpringLayout.EAST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtProductID, 2, SpringLayout.SOUTH, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, txtProductID, -25, SpringLayout.EAST, formPanel);
		formPanel.add(txtProductID);
		txtProductID.setColumns(10);
		
		lblCategory = new JLabel("Category");
		lblCategory.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.WEST, lblCategory, 0, SpringLayout.WEST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblCategory, 0, SpringLayout.EAST, lblProductID);
		formPanel.add(lblCategory);
		
		lblName = new JLabel("Name");
		lblName.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblName, 15, SpringLayout.SOUTH, lblCategory);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblName, 0, SpringLayout.EAST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblName, 0, SpringLayout.WEST, lblProductID);
		formPanel.add(lblName);
		
		txtName = new JTextField();
		txtName.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtName, -2, SpringLayout.NORTH, lblName);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtName, 2, SpringLayout.SOUTH, lblName);
		sl_formPanel.putConstraint(SpringLayout.EAST, txtName, 0, SpringLayout.EAST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtName, 0, SpringLayout.WEST, txtProductID);
		formPanel.add(txtName);
		txtName.setColumns(10);
		
		lblStocks = new JLabel("Initial Stocks");
		lblStocks.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblStocks, 15, SpringLayout.SOUTH, lblName);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblStocks, 0, SpringLayout.EAST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblStocks, 0, SpringLayout.WEST, lblProductID);
		formPanel.add(lblStocks);
		
		txtStocks = new JTextField();
		txtStocks.setText("0");
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtStocks, 0, SpringLayout.SOUTH, lblStocks);
		txtStocks.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtStocks, -2, SpringLayout.NORTH, lblStocks);
		sl_formPanel.putConstraint(SpringLayout.EAST, txtStocks, 0, SpringLayout.EAST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtStocks, 0, SpringLayout.WEST, txtProductID);
		formPanel.add(txtStocks);
		txtStocks.setColumns(10);
		
		lblUOM = new JLabel("<html>Unit of Measurement</html");
		lblUOM.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblUOM, 15, SpringLayout.SOUTH, lblStocks);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblUOM, 0, SpringLayout.WEST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblUOM, 0, SpringLayout.EAST, lblProductID);
		formPanel.add(lblUOM);
		
		txtUOM = new JTextField();
		txtUOM.setText("piece");
		txtUOM.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtUOM, -2, SpringLayout.NORTH, lblUOM);
		sl_formPanel.putConstraint(SpringLayout.EAST, txtUOM, 0, SpringLayout.EAST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtUOM, 0, SpringLayout.WEST, txtProductID);
		formPanel.add(txtUOM);
		txtUOM.setColumns(10);
		
		lblPriceBought = new JLabel("Price Bought");
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblPriceBought, 10, SpringLayout.SOUTH, lblUOM);
		lblPriceBought.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.WEST, lblPriceBought, 0, SpringLayout.WEST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblPriceBought, 0, SpringLayout.EAST, lblProductID);
		formPanel.add(lblPriceBought);
		
		txtPriceBought = new JTextField();
		txtPriceBought.setText("0.00");
		txtPriceBought.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtPriceBought, -2, SpringLayout.NORTH, lblPriceBought);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtPriceBought, 2, SpringLayout.SOUTH, lblPriceBought);
		sl_formPanel.putConstraint(SpringLayout.EAST, txtPriceBought, 0, SpringLayout.EAST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtPriceBought, 0, SpringLayout.WEST, txtProductID);
		formPanel.add(txtPriceBought);
		txtPriceBought.setColumns(10);
		p.add(iconSelectionPanel);
		
		buttonPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.SOUTH, buttonPanel, -15, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.SOUTH, formPanel, -15, SpringLayout.NORTH, buttonPanel);
		sl_p.putConstraint(SpringLayout.NORTH, buttonPanel, -70, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.WEST, buttonPanel, 0, SpringLayout.WEST, formPanel);
		sl_p.putConstraint(SpringLayout.EAST, buttonPanel, 0, SpringLayout.EAST, formPanel);
		
		lblSupplier = new JLabel("Supplier");
		lblSupplier.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblCategory, 15, SpringLayout.SOUTH, lblSupplier);
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblSupplier, 15, SpringLayout.SOUTH, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblSupplier, 0, SpringLayout.WEST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblSupplier, 0, SpringLayout.EAST, lblProductID);
		formPanel.add(lblSupplier);
		
		comboSupplier = new JComboBox<>();
		sl_formPanel.putConstraint(SpringLayout.NORTH, comboSupplier, -2, SpringLayout.NORTH, lblSupplier);
		sl_formPanel.putConstraint(SpringLayout.WEST, comboSupplier, 0, SpringLayout.WEST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, comboSupplier, 2, SpringLayout.SOUTH, lblSupplier);
		sl_formPanel.putConstraint(SpringLayout.EAST, comboSupplier, 0, SpringLayout.EAST, txtProductID);;
		SpringLayout sl_iconSelectionPanel = new SpringLayout();
		iconSelectionPanel.setLayout(sl_iconSelectionPanel);
		formPanel.add(comboSupplier);
		
		lblSellingPrice = new JLabel("Selling Price");
		lblSellingPrice.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblSellingPrice, 18, SpringLayout.SOUTH, lblPriceBought);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblSellingPrice, 0, SpringLayout.WEST, lblProductID);
		formPanel.add(lblSellingPrice);
		
		comboCategory = new JComboBox<>();
		DefaultComboBoxModel<String> categoryModel = new DefaultComboBoxModel<>(Database.productCategories);
		comboCategory.setModel(categoryModel);
		sl_formPanel.putConstraint(SpringLayout.NORTH, comboCategory, 0, SpringLayout.NORTH, lblCategory);
		sl_formPanel.putConstraint(SpringLayout.WEST, comboCategory, 0, SpringLayout.WEST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, comboCategory, 2, SpringLayout.SOUTH, lblCategory);
		sl_formPanel.putConstraint(SpringLayout.EAST, comboCategory, 0, SpringLayout.EAST, txtProductID);
		formPanel.add(comboCategory);
		
		txtSellingPrice = new JTextField();
		txtSellingPrice.setText("0.00");
		txtSellingPrice.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtSellingPrice, 0, SpringLayout.NORTH, lblSellingPrice);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtSellingPrice, 0, SpringLayout.WEST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, txtSellingPrice, 0, SpringLayout.EAST, txtProductID);
		txtSellingPrice.setColumns(10);
		formPanel.add(txtSellingPrice);
		
		lblPreviewIcon = new JLabel(gallery.getImageViaPath(defaultIconPath, 32, 32));
		sl_iconSelectionPanel.putConstraint(SpringLayout.NORTH, lblPreviewIcon, 16, SpringLayout.NORTH, iconSelectionPanel);
		sl_iconSelectionPanel.putConstraint(SpringLayout.WEST, lblPreviewIcon, 16, SpringLayout.WEST, iconSelectionPanel);
		sl_iconSelectionPanel.putConstraint(SpringLayout.SOUTH, lblPreviewIcon, 48, SpringLayout.NORTH, iconSelectionPanel);
		sl_iconSelectionPanel.putConstraint(SpringLayout.EAST, lblPreviewIcon, -16, SpringLayout.EAST, iconSelectionPanel);
		iconSelectionPanel.add(lblPreviewIcon);
		p.add(buttonPanel);
		SpringLayout sl_buttonPanel = new SpringLayout();
		buttonPanel.setLayout(sl_buttonPanel);
		
		btnConfirm = new JLabel("Confirm");
		btnConfirm.setName("primary");
		gallery.styleLabelToButton(btnConfirm, 14f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnConfirm, 15, SpringLayout.NORTH, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnConfirm, -15, SpringLayout.SOUTH, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnConfirm, -225, SpringLayout.EAST, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnConfirm, 15, SpringLayout.WEST, buttonPanel);
		btnConfirm.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.add(btnConfirm);
		
		btnCancel = new JLabel("Cancel");
		btnCancel.setName("danger");
		gallery.styleLabelToButton(btnCancel, 14f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnCancel, -2, SpringLayout.NORTH, btnConfirm);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnCancel, 10, SpringLayout.EAST, btnConfirm);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnCancel, 0, SpringLayout.SOUTH, btnConfirm);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnCancel, -15, SpringLayout.EAST, buttonPanel);
		btnCancel.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.add(btnCancel);
		
		lblSelectIconButton = new JLabel("<html><center>Select<br>icon</center></html>");
		lblSelectIconButton.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectIconButton.setName("primary");
		gallery.styleLabelToButton(lblSelectIconButton, 12f, 5, 5);
		sl_p.putConstraint(SpringLayout.NORTH, lblSelectIconButton, 15, SpringLayout.SOUTH, iconSelectionPanel);
		sl_p.putConstraint(SpringLayout.WEST, lblSelectIconButton, 0, SpringLayout.WEST, iconSelectionPanel);
		sl_p.putConstraint(SpringLayout.EAST, lblSelectIconButton, 0, SpringLayout.EAST, iconSelectionPanel);
		p.add(lblSelectIconButton);
		
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnConfirm);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnConfirm);}
			@Override
			public void mouseClicked(MouseEvent e) {
				if (checkFields()) {
					long userID = (long) user[0];
					long productID = Long.parseLong(txtProductID.getText());
					long supplier = supplierIDs[comboSupplier.getSelectedIndex()];
					String category = comboCategory.getSelectedItem().toString();
					String name = txtName.getText();
					double stock = Double.parseDouble(txtStocks.getText());
					String unitofMeasurement = txtUOM.getText();
					double priceBought = Double.parseDouble(txtPriceBought.getText());
					double sellingPrice = Double.parseDouble(txtSellingPrice.getText());
					
					if (database.addProduct(productID, category, name, iconPath, stock, unitofMeasurement, priceBought, sellingPrice)) {
						if (database.addSupplies(supplier, productID, userID, stock, priceBought * stock)) {
							logger.addLog(Logger.LEVEL_1, String.format("User %s added a new product with the ID:%s", user[0].toString(), productID));
							 
							 JOptionPane.showMessageDialog(
								null, "Successfully added new product '" + name + "'", 
								Utility.BUSINESS_TITLE, 
								JOptionPane.INFORMATION_MESSAGE);
							 
							 clearFields();
						}
					}
				}
			}
		});
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnCancel);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnCancel);}
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		lblSelectIconButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(lblSelectIconButton);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(lblSelectIconButton);}
			@Override
			public void mouseClicked(MouseEvent e) {
				String selectedPath = utility.showImageChooser();

				if (selectedPath == null) {
					iconPath = defaultIconPath;
				} else {
					iconPath = selectedPath;
				}
				
				lblPreviewIcon.setIcon(gallery.getImageViaPath(iconPath, 32, 32));
			}
		});

		
		setSuppliers();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private boolean checkFields() {
		ArrayList<String> errorMessages = new ArrayList<>();
		
		String name = txtName.getText();
		String unitofMeasurement = txtUOM.getText();
		
		if (name.isBlank()) {
			errorMessages.add("- Name field cannot be empty.");
		}
		
		if (unitofMeasurement.isBlank()) {
			errorMessages.add("- Unit of measurement field cannot be empty.");
		}
		
		try {
			Integer.parseInt(txtStocks.getText());
		} catch (NumberFormatException e) {
			errorMessages.add("- Invalid stock value, must be an integer/whole number.");
		}
		
		try {
			Double.parseDouble(txtPriceBought.getText());
		} catch (NumberFormatException | NullPointerException e) {
			errorMessages.add("- Invalid price bought value, must be a currency.");
		}
		
		try {
			Double.parseDouble(txtSellingPrice.getText());
		} catch (NumberFormatException | NullPointerException e) {
			errorMessages.add("- Invalid selling price value, must be a currency.");
		}
		
		if (errorMessages.size() > 0) { 
			gallery.showMessage(errorMessages.toArray(new String[0]));
			return false;
		}
		
		return true;
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
	
	public void clearFields() {
		txtProductID.setText(Long.toString(utility.generateProductID(database.fetchLastID("product", "product_id"))));
		comboSupplier.setSelectedIndex(0);
		comboSupplier.setSelectedIndex(0);
		txtName.setText("");
		iconPath = defaultIconPath;
		lblPreviewIcon.setIcon(gallery.getImageViaPath(iconPath, 32, 32));
		txtStocks.setText("0");
		txtUOM.setText("piece");
		txtPriceBought.setText("0.00");
		txtSellingPrice.setText("0.00");
	}
}
