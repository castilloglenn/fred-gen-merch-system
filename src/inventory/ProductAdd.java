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
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ProductAdd extends JFrame {

	private Utility utility;
	private Gallery gallery;
	private String imagePath;
	
	private JPanel contentPane, p, newProductPanel, iconSelectionPanel, buttonPanel, formPanel,iconPanel;
	private JLabel lblNewProduct, btnConfirm, btnCancel,lblProductID,lblStocks,lblUOM,lblPriceBought,lblName,lblSellingPrice,lblSupplier;
	private JTextField txtProductID,txtName,txtStocks,txtUOM,txtPriceBought,txtSellingPrice;
	private JComboBox productCombo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductAdd frame = new ProductAdd();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
	public ProductAdd() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		gallery = new Gallery();
		utility = new Utility();
		
		p = new JPanel();
		p.setBackground(Gallery.GRAY);
		sl_contentPane.putConstraint(SpringLayout.NORTH, p, -5, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, p, -5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, p, 5, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, p, 5, SpringLayout.EAST, contentPane);
		SpringLayout sl_p = new SpringLayout();
		p.setLayout(sl_p);
		contentPane.add(p);
		
		newProductPanel = new RoundedPanel(gallery.BLUE);
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
		sl_p.putConstraint(SpringLayout.NORTH, formPanel, 10, SpringLayout.SOUTH, newProductPanel);
		sl_p.putConstraint(SpringLayout.WEST, formPanel, 10, SpringLayout.WEST, p);
		sl_p.putConstraint(SpringLayout.EAST, formPanel, -250, SpringLayout.EAST, p);
		p.add(formPanel);
		
		iconSelectionPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, iconSelectionPanel, 0, SpringLayout.NORTH, formPanel);
		sl_p.putConstraint(SpringLayout.SOUTH, iconSelectionPanel, -10, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.WEST, iconSelectionPanel, 10, SpringLayout.EAST, formPanel);
		SpringLayout sl_formPanel = new SpringLayout();
		formPanel.setLayout(sl_formPanel);
		
		lblProductID = new JLabel("Product ID");
		lblProductID.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblProductID, 25, SpringLayout.NORTH, formPanel);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblProductID, 25, SpringLayout.WEST, formPanel);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblProductID, 125, SpringLayout.WEST, formPanel);
		sl_p.putConstraint(SpringLayout.EAST, iconSelectionPanel, -10, SpringLayout.EAST, p);
		formPanel.add(lblProductID);
		
		txtProductID = new JTextField();
		txtProductID.setEnabled(false);
		txtProductID.setEditable(false);
		txtProductID.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtProductID, -2, SpringLayout.NORTH, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtProductID, 15, SpringLayout.EAST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtProductID, 2, SpringLayout.SOUTH, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, txtProductID, -25, SpringLayout.EAST, formPanel);
		formPanel.add(txtProductID);
		txtProductID.setColumns(10);
		
		lblName = new JLabel("Name");
		lblName.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.WEST, lblName, 0, SpringLayout.WEST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblName, 0, SpringLayout.EAST, lblProductID);
		formPanel.add(lblName);
		
		txtName = new JTextField();
		txtName.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtName, -2, SpringLayout.NORTH, lblName);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtName, 2, SpringLayout.SOUTH, lblName);
		sl_formPanel.putConstraint(SpringLayout.EAST, txtName, 0, SpringLayout.EAST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtName, 0, SpringLayout.WEST, txtProductID);
		formPanel.add(txtName);
		txtName.setColumns(10);
		
		lblStocks = new JLabel("Stocks");
		lblStocks.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblStocks, 15, SpringLayout.SOUTH, lblName);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblStocks, 0, SpringLayout.EAST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblStocks, 0, SpringLayout.WEST, lblProductID);
		formPanel.add(lblStocks);
		
		txtStocks = new JTextField();
		txtStocks.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtStocks, -2, SpringLayout.NORTH, lblStocks);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtStocks, 2, SpringLayout.SOUTH, lblStocks);
		sl_formPanel.putConstraint(SpringLayout.EAST, txtStocks, 0, SpringLayout.EAST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtStocks, 0, SpringLayout.WEST, txtProductID);
		formPanel.add(txtStocks);
		txtStocks.setColumns(10);
		
		lblUOM = new JLabel("<html>Unit of Measurement</html>\r\n");
		lblUOM.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblUOM, 15, SpringLayout.SOUTH, lblStocks);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblUOM, 0, SpringLayout.EAST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblUOM, 0, SpringLayout.WEST, lblProductID);
		formPanel.add(lblUOM);
		
		txtUOM = new JTextField();
		txtUOM.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtUOM, -2, SpringLayout.NORTH, lblUOM);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtUOM, -18, SpringLayout.SOUTH, lblUOM);
		sl_formPanel.putConstraint(SpringLayout.EAST, txtUOM, 0, SpringLayout.EAST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtUOM, 0, SpringLayout.WEST, txtProductID);
		formPanel.add(txtUOM);
		txtUOM.setColumns(10);
		
		lblPriceBought = new JLabel("Price Bought");
		lblPriceBought.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblPriceBought, 15, SpringLayout.SOUTH, lblUOM);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblPriceBought, 0, SpringLayout.WEST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblPriceBought, 0, SpringLayout.EAST, lblProductID);
		formPanel.add(lblPriceBought);
		
		txtPriceBought = new JTextField();
		txtPriceBought.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtPriceBought, -2, SpringLayout.NORTH, lblPriceBought);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtPriceBought, 2, SpringLayout.SOUTH, lblPriceBought);
		sl_formPanel.putConstraint(SpringLayout.EAST, txtPriceBought, 0, SpringLayout.EAST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtPriceBought, 0, SpringLayout.WEST, txtProductID);
		formPanel.add(txtPriceBought);
		txtPriceBought.setColumns(10);
		
		lblSellingPrice = new JLabel("Selling Price");
		lblSellingPrice.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblSellingPrice, 15, SpringLayout.SOUTH, lblPriceBought);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblSellingPrice, 0, SpringLayout.WEST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblSellingPrice, 0, SpringLayout.EAST, lblProductID);
		formPanel.add(lblSellingPrice);
		
		txtSellingPrice = new JTextField();
		txtSellingPrice.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtSellingPrice, -2, SpringLayout.NORTH, lblSellingPrice);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtSellingPrice, 2, SpringLayout.SOUTH, lblSellingPrice);
		sl_formPanel.putConstraint(SpringLayout.EAST, txtSellingPrice, 0, SpringLayout.EAST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtSellingPrice, 0, SpringLayout.WEST, txtProductID);
		formPanel.add(txtSellingPrice);
		txtSellingPrice.setColumns(10);
		p.add(iconSelectionPanel);
		
		buttonPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.SOUTH, formPanel, -10, SpringLayout.NORTH, buttonPanel);
		sl_p.putConstraint(SpringLayout.WEST, buttonPanel, 0, SpringLayout.WEST, formPanel);
		sl_p.putConstraint(SpringLayout.EAST, buttonPanel, 0, SpringLayout.EAST, formPanel);
		
		lblSupplier = new JLabel("Supplier");
		lblSupplier.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblName, 15, SpringLayout.SOUTH, lblSupplier);
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblSupplier, 15, SpringLayout.SOUTH, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblSupplier, 0, SpringLayout.WEST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblSupplier, 0, SpringLayout.EAST, lblProductID);
		formPanel.add(lblSupplier);
		
		productCombo = new JComboBox();
		sl_formPanel.putConstraint(SpringLayout.NORTH, productCombo, -2, SpringLayout.NORTH, lblSupplier);
		sl_formPanel.putConstraint(SpringLayout.WEST, productCombo, 0, SpringLayout.WEST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, productCombo, 2, SpringLayout.SOUTH, lblSupplier);
		sl_formPanel.putConstraint(SpringLayout.EAST, productCombo, 0, SpringLayout.EAST, txtProductID);
		sl_p.putConstraint(SpringLayout.NORTH, buttonPanel, -100, SpringLayout.SOUTH, p);;
		sl_p.putConstraint(SpringLayout.SOUTH, buttonPanel, -10, SpringLayout.SOUTH, p);
		SpringLayout sl_iconSelectionPanel = new SpringLayout();
		iconSelectionPanel.setLayout(sl_iconSelectionPanel);
		formPanel.add(productCombo);
		
		iconPanel = new JPanel();
		sl_iconSelectionPanel.putConstraint(SpringLayout.SOUTH, iconPanel, -20, SpringLayout.SOUTH, iconSelectionPanel);
		iconPanel.setBackground(Gallery.WHITE);
		iconSelectionPanel.add(iconPanel);
		iconPanel.setLayout(null);
		
		JLabel lblIconMessage = new JLabel("Select icons here:");
		lblIconMessage.setFont(gallery.getFont(14f));
		sl_iconSelectionPanel.putConstraint(SpringLayout.WEST, iconPanel, 0, SpringLayout.WEST, lblIconMessage);
		sl_iconSelectionPanel.putConstraint(SpringLayout.EAST, iconPanel, 0, SpringLayout.EAST, lblIconMessage);
		sl_iconSelectionPanel.putConstraint(SpringLayout.NORTH, lblIconMessage, 20, SpringLayout.NORTH, iconSelectionPanel);
		sl_iconSelectionPanel.putConstraint(SpringLayout.WEST, lblIconMessage, 15, SpringLayout.WEST, iconSelectionPanel);
		sl_iconSelectionPanel.putConstraint(SpringLayout.EAST, lblIconMessage, -15, SpringLayout.EAST, iconSelectionPanel);
		lblIconMessage.setHorizontalAlignment(SwingConstants.LEFT);
		sl_iconSelectionPanel.putConstraint(SpringLayout.NORTH, iconPanel, 10, SpringLayout.SOUTH, lblIconMessage);
		iconSelectionPanel.add(lblIconMessage);
		p.add(buttonPanel);
		SpringLayout sl_buttonPanel = new SpringLayout();
		buttonPanel.setLayout(sl_buttonPanel);
		
		btnConfirm = new JLabel("Confirm");
		btnConfirm.setName("primary");
		gallery.styleLabelToButton(btnConfirm, 14f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnConfirm, 15, SpringLayout.WEST, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnConfirm, -15, SpringLayout.EAST, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnConfirm, -49, SpringLayout.SOUTH, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnConfirm, 10, SpringLayout.NORTH, buttonPanel);
		btnConfirm.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.add(btnConfirm);
		
		btnCancel = new JLabel("Cancel");
		btnCancel.setName("danger");
		gallery.styleLabelToButton(btnCancel, 14f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnCancel, 7, SpringLayout.SOUTH, btnConfirm);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnCancel, 0, SpringLayout.WEST, btnConfirm);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnCancel, -10, SpringLayout.SOUTH, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnCancel, 0, SpringLayout.EAST, btnConfirm);
		btnCancel.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.add(btnCancel);
		
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnConfirm);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnConfirm);}
			@Override
			public void mouseClicked(MouseEvent e) {
				//backend here
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
		
	}
}
