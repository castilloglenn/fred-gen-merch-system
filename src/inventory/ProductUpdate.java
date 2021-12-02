package inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.Gallery;
import utils.RoundedPanel;
import utils.Utility;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ProductUpdate extends JFrame {
	
	private Utility utility;
	private Gallery gallery;
	private String imagePath;
	
	private JComboBox comboProductID,comboSupplier;
	private JPanel contentPane, p, manageProductPanel, formsPanel, iconSelectionPanel, buttonsPanel;
	private JLabel btnConfirm, lblManageProduct,lblCategory,lblName,lblStocks,lblUOM,lblPriceBought,lblProductID,lblSupplier;
	private JTextField txtName,txtUOM,txtPriceBought,txtStocks;
	private JLabel lblSellingPrice;
	private JComboBox comboCategory;
	private JTextField txtSellingPrice;
	private JLabel btnCancel;
		

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductUpdate frame = new ProductUpdate();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProductUpdate() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		gallery = Gallery.getInstance();
		utility = Utility.getInstance();
		
		p = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, p, -5, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, p, -5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, p, 5, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, p, 5, SpringLayout.EAST, contentPane);
		contentPane.add(p);
		SpringLayout sl_p = new SpringLayout();
		p.setLayout(sl_p);
		
		manageProductPanel = new RoundedPanel(gallery.BLUE);
		sl_p.putConstraint(SpringLayout.NORTH, manageProductPanel, -15, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.WEST, manageProductPanel, -15, SpringLayout.WEST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, manageProductPanel, 50, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.EAST, manageProductPanel, 200, SpringLayout.WEST, p);
		p.add(manageProductPanel);
		SpringLayout sl_manageProductPanel = new SpringLayout();
		manageProductPanel.setLayout(sl_manageProductPanel);
		
		lblManageProduct = new JLabel("Manage Product");
		lblManageProduct.setFont(gallery.getFont(20f));
		lblManageProduct.setForeground(Color.WHITE);
		sl_manageProductPanel.putConstraint(SpringLayout.NORTH, lblManageProduct, 25, SpringLayout.NORTH, manageProductPanel);
		sl_manageProductPanel.putConstraint(SpringLayout.SOUTH, lblManageProduct, -15, SpringLayout.SOUTH, manageProductPanel);
		sl_manageProductPanel.putConstraint(SpringLayout.WEST, lblManageProduct, 30, SpringLayout.WEST, manageProductPanel);
		manageProductPanel.add(lblManageProduct);
		
		formsPanel = new RoundedPanel(gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, formsPanel, 10, SpringLayout.SOUTH, manageProductPanel);
		sl_p.putConstraint(SpringLayout.WEST, formsPanel, 10, SpringLayout.WEST, p);
		sl_p.putConstraint(SpringLayout.EAST, formsPanel, -250, SpringLayout.EAST, p);
		p.add(formsPanel);
		
		iconSelectionPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, iconSelectionPanel, 0, SpringLayout.NORTH, formsPanel);
		sl_p.putConstraint(SpringLayout.WEST, iconSelectionPanel, 10, SpringLayout.EAST, formsPanel);
		sl_p.putConstraint(SpringLayout.SOUTH, iconSelectionPanel, -10, SpringLayout.SOUTH, p);
		SpringLayout sl_formsPanel = new SpringLayout();
		formsPanel.setLayout(sl_formsPanel);
		
		lblProductID = new JLabel("Product ID");
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblProductID, 25, SpringLayout.NORTH, formsPanel);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblProductID, 25, SpringLayout.WEST, formsPanel);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblProductID, 125, SpringLayout.WEST, formsPanel);
		lblProductID.setFont(gallery.getFont(14f));
		formsPanel.add(lblProductID);
		
		comboProductID = new JComboBox();
		sl_formsPanel.putConstraint(SpringLayout.NORTH, comboProductID, -2, SpringLayout.NORTH, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.WEST, comboProductID, 15, SpringLayout.EAST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, comboProductID, 2, SpringLayout.SOUTH, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, comboProductID, -25, SpringLayout.EAST, formsPanel);
		formsPanel.add(comboProductID);
		
		lblCategory = new JLabel("Category");
		lblCategory.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblCategory, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblCategory, 0, SpringLayout.EAST, lblProductID);
		formsPanel.add(lblCategory);
		
		lblName = new JLabel("Name");
		lblName.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblName, 15, SpringLayout.SOUTH, lblCategory);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblName, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblName, 0, SpringLayout.EAST, lblCategory);
		formsPanel.add(lblName);
		
		txtName = new JTextField();
		txtName.setEnabled(false);
		txtName.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtName, -2, SpringLayout.NORTH, lblName);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtName, 2, SpringLayout.SOUTH, lblName);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtName, 0, SpringLayout.EAST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtName, 0, SpringLayout.WEST, comboProductID);
		formsPanel.add(txtName);
		txtName.setColumns(10);
		
		lblStocks = new JLabel("Stocks");
		lblStocks.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblStocks, 15, SpringLayout.SOUTH, lblName);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblStocks, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblStocks, 0, SpringLayout.EAST, lblProductID);
		formsPanel.add(lblStocks);
		
		txtStocks = new JTextField();
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtStocks, 0, SpringLayout.NORTH, lblStocks);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtStocks, 0, SpringLayout.SOUTH, lblStocks);
		txtStocks.setEnabled(false);
		txtStocks.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtStocks, 0, SpringLayout.WEST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtStocks, 0, SpringLayout.EAST, txtName);
		formsPanel.add(txtStocks);
		txtStocks.setColumns(10);
		
		lblUOM = new JLabel("<html>Unit of Measurement </html>");
		lblUOM.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblUOM, 15, SpringLayout.SOUTH, lblStocks);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblUOM, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblUOM, 0, SpringLayout.EAST, lblProductID);
		formsPanel.add(lblUOM);
		
		txtUOM = new JTextField();
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtUOM, -20, SpringLayout.SOUTH, lblUOM);
		txtUOM.setEnabled(false);
		txtUOM.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtUOM, -2, SpringLayout.NORTH, lblUOM);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtUOM, 0, SpringLayout.EAST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtUOM, 0, SpringLayout.WEST, comboProductID);
		formsPanel.add(txtUOM);
		txtUOM.setColumns(10);
		
		lblPriceBought = new JLabel("Price Bought");
		lblPriceBought.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblPriceBought, 15, SpringLayout.SOUTH, lblUOM);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblPriceBought, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblPriceBought, 0, SpringLayout.EAST, lblProductID);
		formsPanel.add(lblPriceBought);
		
		txtPriceBought = new JTextField();
		txtPriceBought.setEnabled(false);
		txtPriceBought.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtPriceBought, -2, SpringLayout.NORTH, lblPriceBought);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtPriceBought, 2, SpringLayout.SOUTH, lblPriceBought);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtPriceBought, 0, SpringLayout.EAST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtPriceBought, 0, SpringLayout.WEST, comboProductID);
		formsPanel.add(txtPriceBought);
		txtPriceBought.setColumns(10);
		sl_p.putConstraint(SpringLayout.EAST, iconSelectionPanel, -10, SpringLayout.EAST, p);
		p.add(iconSelectionPanel);
		
		buttonsPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.SOUTH, formsPanel, -5, SpringLayout.NORTH, buttonsPanel);
		sl_p.putConstraint(SpringLayout.NORTH, buttonsPanel, -70, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.WEST, buttonsPanel, 0, SpringLayout.WEST, formsPanel);
		sl_p.putConstraint(SpringLayout.EAST, buttonsPanel, 0, SpringLayout.EAST, formsPanel);
		
		lblSupplier = new JLabel("Supplier");
		lblSupplier.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblCategory, 15, SpringLayout.SOUTH, lblSupplier);
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblSupplier, 15, SpringLayout.SOUTH, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblSupplier, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblSupplier, 0, SpringLayout.EAST, lblProductID);
		formsPanel.add(lblSupplier);
		
		comboSupplier = new JComboBox();
		comboSupplier.setEnabled(false);
		sl_formsPanel.putConstraint(SpringLayout.NORTH, comboSupplier, -2, SpringLayout.NORTH, lblSupplier);
		sl_formsPanel.putConstraint(SpringLayout.WEST, comboSupplier, 0, SpringLayout.WEST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, comboSupplier, 2, SpringLayout.SOUTH, lblSupplier);
		sl_formsPanel.putConstraint(SpringLayout.EAST, comboSupplier, 0, SpringLayout.EAST, comboProductID);
		formsPanel.add(comboSupplier);
		
		lblSellingPrice = new JLabel("Selling Price");
		lblSellingPrice.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblSellingPrice, 15, SpringLayout.SOUTH, lblPriceBought);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblSellingPrice, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblSellingPrice, 0, SpringLayout.EAST, lblProductID);
		formsPanel.add(lblSellingPrice);
		
		comboCategory = new JComboBox();
		sl_formsPanel.putConstraint(SpringLayout.NORTH, comboCategory, -2, SpringLayout.NORTH, lblCategory);
		sl_formsPanel.putConstraint(SpringLayout.WEST, comboCategory, 0, SpringLayout.WEST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, comboCategory, 2, SpringLayout.SOUTH, lblCategory);
		sl_formsPanel.putConstraint(SpringLayout.EAST, comboCategory, 0, SpringLayout.EAST, comboProductID);
		comboCategory.setEnabled(false);
		formsPanel.add(comboCategory);
		
		txtSellingPrice = new JTextField();
		txtSellingPrice.setFont(gallery.getFont(15f));
		txtSellingPrice.setEnabled(false);
		txtSellingPrice.setColumns(10);
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtSellingPrice, 0, SpringLayout.NORTH, lblSellingPrice);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtSellingPrice, 0, SpringLayout.WEST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtSellingPrice, 0, SpringLayout.SOUTH, lblSellingPrice);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtSellingPrice, 0, SpringLayout.EAST, comboProductID);
		sl_p.putConstraint(SpringLayout.SOUTH, buttonsPanel, -10, SpringLayout.SOUTH, p);
		SpringLayout sl_iconSelectionPanel = new SpringLayout();
		formsPanel.add(txtSellingPrice);
		iconSelectionPanel.setLayout(sl_iconSelectionPanel);
		
		JLabel lblIconMessage = new JLabel("Select icons here:");
		sl_iconSelectionPanel.putConstraint(SpringLayout.NORTH, lblIconMessage, 20, SpringLayout.NORTH, iconSelectionPanel);
		sl_iconSelectionPanel.putConstraint(SpringLayout.WEST, lblIconMessage, 15, SpringLayout.WEST, iconSelectionPanel);
		sl_iconSelectionPanel.putConstraint(SpringLayout.EAST, lblIconMessage, -15, SpringLayout.EAST, iconSelectionPanel);
		iconSelectionPanel.add(lblIconMessage);
		
		JPanel iconPanel = new JPanel();
		iconPanel.setBackground(Color.WHITE);
		sl_iconSelectionPanel.putConstraint(SpringLayout.NORTH, iconPanel, 10, SpringLayout.SOUTH, lblIconMessage);
		sl_iconSelectionPanel.putConstraint(SpringLayout.WEST, iconPanel, 0, SpringLayout.WEST, lblIconMessage);
		sl_iconSelectionPanel.putConstraint(SpringLayout.SOUTH, iconPanel, -20, SpringLayout.SOUTH, iconSelectionPanel);
		sl_iconSelectionPanel.putConstraint(SpringLayout.EAST, iconPanel, 0, SpringLayout.EAST, lblIconMessage);
		iconSelectionPanel.add(iconPanel);
		p.add(buttonsPanel);
		SpringLayout sl_buttonsPanel = new SpringLayout();
		buttonsPanel.setLayout(sl_buttonsPanel);
		
		btnConfirm = new JLabel("Confirm");
		btnConfirm.setName("primary");
		gallery.styleLabelToButton(btnConfirm, 14f, 15, 10);
		sl_buttonsPanel.putConstraint(SpringLayout.NORTH, btnConfirm, 15, SpringLayout.NORTH, buttonsPanel);
		sl_buttonsPanel.putConstraint(SpringLayout.WEST, btnConfirm, 15, SpringLayout.WEST, buttonsPanel);
		sl_buttonsPanel.putConstraint(SpringLayout.SOUTH, btnConfirm, -15, SpringLayout.SOUTH, buttonsPanel);
		sl_buttonsPanel.putConstraint(SpringLayout.EAST, btnConfirm, -225, SpringLayout.EAST, buttonsPanel);
		btnConfirm.setHorizontalAlignment(SwingConstants.CENTER);
		buttonsPanel.add(btnConfirm);
		
		btnCancel = new JLabel("Cancel");
		btnCancel.setName("danger");
		gallery.styleLabelToButton(btnCancel, 14f, 15, 10);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnCancel);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnCancel);}
			@Override
			public void mouseClicked(MouseEvent e) {dispose();}
		});
		sl_buttonsPanel.putConstraint(SpringLayout.NORTH, btnCancel, 0, SpringLayout.NORTH, btnConfirm);
		sl_buttonsPanel.putConstraint(SpringLayout.WEST, btnCancel, 10, SpringLayout.EAST, btnConfirm);
		sl_buttonsPanel.putConstraint(SpringLayout.SOUTH, btnCancel, 0, SpringLayout.SOUTH, btnConfirm);
		sl_buttonsPanel.putConstraint(SpringLayout.EAST, btnCancel, -15, SpringLayout.EAST, buttonsPanel);
		
		btnCancel.setHorizontalAlignment(SwingConstants.CENTER);
		buttonsPanel.add(btnCancel);

		//Action Listeners
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnConfirm);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnConfirm);}
			@Override
			public void mouseClicked(MouseEvent e) {
				missingFields();
			}
		});
		
	}
	
	//User Defined Methods
	
	private void missingFields() {
		ArrayList<String> errorMessages = new ArrayList<>();
		
		String supplier = String.valueOf(comboSupplier.getSelectedItem());
		String name = txtName.getText();
		String stocks = txtName.getText();
		String unitofMeasurement = txtStocks.getText();
		String priceBought = txtUOM.getText();
		String sellPrice = txtPriceBought.getText();
		
		if(supplier.equals("") || name.equals("") || stocks.equals("") || unitofMeasurement.equals("") || priceBought.equals("") || sellPrice.equals("")) {
			errorMessages.add(" - Please fill out the missing fields!");
			}
		
		if (errorMessages.size() > 0) { 
			gallery.showMessage(errorMessages.toArray(new String[0]));
		}
	}
}
