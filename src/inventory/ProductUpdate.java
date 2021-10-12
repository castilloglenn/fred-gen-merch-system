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
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ProductUpdate extends JFrame {
	
	private Utility utility;
	private Gallery gallery;
	private String imagePath;
	
	private JComboBox comboProductID,productCombo;
	private JPanel contentPane, p, manageProductPanel, formsPanel, iconSelectionPanel, buttonsPanel;
	private JLabel btnCancel, btnConfirm, lblManageProduct,lblName,lblStocks,lblUOM,lblPriceBought,lblSellingPrice,lblProductID,lblSupplier;
	private JTextField txtName,txtStocks,txtPriceBought,txtSellingPrice,txtUOM;
		

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
		
		gallery = new Gallery();
		utility = new Utility();
		
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
		
		lblName = new JLabel("Name");
		lblName.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblName, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblName, 0, SpringLayout.EAST, lblProductID);
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
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblStocks, 0, SpringLayout.EAST, lblName);
		formsPanel.add(lblStocks);
		
		txtStocks = new JTextField();
		txtStocks.setEnabled(false);
		txtStocks.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtStocks, -2, SpringLayout.NORTH, lblStocks);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtStocks, 2, SpringLayout.SOUTH, lblStocks);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtStocks, 0, SpringLayout.EAST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtStocks, 0, SpringLayout.WEST, comboProductID);
		formsPanel.add(txtStocks);
		txtStocks.setColumns(10);
		
		lblUOM = new JLabel("<html>Unit of Measurement</html>");
		lblUOM.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblUOM, 15, SpringLayout.SOUTH, lblStocks);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblUOM, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblUOM, 0, SpringLayout.EAST, lblProductID);
		formsPanel.add(lblUOM);
		
		txtUOM = new JTextField();
		txtUOM.setEnabled(false);
		txtUOM.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtUOM, -2, SpringLayout.NORTH, lblUOM);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtUOM, -18, SpringLayout.SOUTH, lblUOM);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtUOM, 0, SpringLayout.WEST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtUOM, 0, SpringLayout.EAST, txtStocks);
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
		
		lblSellingPrice = new JLabel("Selling Price");
		lblSellingPrice.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblSellingPrice, 15, SpringLayout.SOUTH, lblPriceBought);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblSellingPrice, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblSellingPrice, 0, SpringLayout.EAST, lblProductID);
		formsPanel.add(lblSellingPrice);
		
		txtSellingPrice = new JTextField();
		txtSellingPrice.setEnabled(false);
		txtSellingPrice.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtSellingPrice, -2, SpringLayout.NORTH, lblSellingPrice);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtSellingPrice, 2, SpringLayout.SOUTH, lblSellingPrice);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtSellingPrice, 0, SpringLayout.EAST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtSellingPrice, 0, SpringLayout.WEST, comboProductID);
		formsPanel.add(txtSellingPrice);
		txtSellingPrice.setColumns(10);
		sl_p.putConstraint(SpringLayout.EAST, iconSelectionPanel, -10, SpringLayout.EAST, p);
		p.add(iconSelectionPanel);
		
		buttonsPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.SOUTH, formsPanel, -10, SpringLayout.NORTH, buttonsPanel);
		sl_p.putConstraint(SpringLayout.WEST, buttonsPanel, 0, SpringLayout.WEST, formsPanel);
		sl_p.putConstraint(SpringLayout.EAST, buttonsPanel, 0, SpringLayout.EAST, formsPanel);
		
		lblSupplier = new JLabel("Supplier");
		lblSupplier.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblName, 15, SpringLayout.SOUTH, lblSupplier);
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblSupplier, 15, SpringLayout.SOUTH, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblSupplier, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblSupplier, 0, SpringLayout.EAST, lblProductID);
		formsPanel.add(lblSupplier);
		
		productCombo = new JComboBox();
		productCombo.setEnabled(false);
		sl_formsPanel.putConstraint(SpringLayout.NORTH, productCombo, -2, SpringLayout.NORTH, lblSupplier);
		sl_formsPanel.putConstraint(SpringLayout.WEST, productCombo, 0, SpringLayout.WEST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, productCombo, 2, SpringLayout.SOUTH, lblSupplier);
		sl_formsPanel.putConstraint(SpringLayout.EAST, productCombo, 0, SpringLayout.EAST, comboProductID);
		formsPanel.add(productCombo);
		sl_p.putConstraint(SpringLayout.NORTH, buttonsPanel, -100, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.SOUTH, buttonsPanel, -10, SpringLayout.SOUTH, p);
		SpringLayout sl_iconSelectionPanel = new SpringLayout();
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
		btnConfirm.setHorizontalAlignment(SwingConstants.CENTER);
		sl_buttonsPanel.putConstraint(SpringLayout.NORTH, btnConfirm, 10, SpringLayout.NORTH, buttonsPanel);
		sl_buttonsPanel.putConstraint(SpringLayout.WEST, btnConfirm, 10, SpringLayout.WEST, buttonsPanel);
		sl_buttonsPanel.putConstraint(SpringLayout.SOUTH, btnConfirm, -49, SpringLayout.SOUTH, buttonsPanel);
		sl_buttonsPanel.putConstraint(SpringLayout.EAST, btnConfirm, -10, SpringLayout.EAST, buttonsPanel);
		buttonsPanel.add(btnConfirm);
		
		btnCancel = new JLabel("Cancel");
		btnCancel.setName("danger");
		gallery.styleLabelToButton(btnCancel, 14f, 15, 10);
		sl_buttonsPanel.putConstraint(SpringLayout.NORTH, btnCancel, 7, SpringLayout.SOUTH, btnConfirm);
		sl_buttonsPanel.putConstraint(SpringLayout.WEST, btnCancel, 0, SpringLayout.WEST, btnConfirm);
		sl_buttonsPanel.putConstraint(SpringLayout.EAST, btnCancel, 0, SpringLayout.EAST, btnConfirm);
		btnCancel.setHorizontalAlignment(SwingConstants.CENTER);
		sl_buttonsPanel.putConstraint(SpringLayout.SOUTH, btnCancel, -10, SpringLayout.SOUTH, buttonsPanel);
		buttonsPanel.add(btnCancel);

		//Action Listeners
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnConfirm);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnConfirm);}
		});
		
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnCancel);}
			@Override
			public void mouseExited(MouseEvent e) {gallery.buttonNormalized(btnCancel);}
			@Override
			public void mouseClicked(MouseEvent e) {dispose();}
		});
		
		
	}
}
