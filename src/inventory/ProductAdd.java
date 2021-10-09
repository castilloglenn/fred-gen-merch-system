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

public class ProductAdd extends JFrame {

	private Utility utility;
	private Gallery gallery;
	private String imagePath;
	
	private JPanel contentPane, p, newProductPanel, imagePanel, buttonPanel, formPanel;
	private JLabel lblNewProduct, btnUploadImage, btnConfirm, btnCancel, lblImage,lblProductID,lblStocks,lblUOM,lblUOM1,lblPriceBought;
	private JLabel lblName,lblSellingPrice;
	private JTextField txtProductID,txtName,txtStocks,txtUOM,txtPriceBought,txtSellingPrice;

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
		lblNewProduct.setFont(gallery.getFont(20f));
		lblNewProduct.setForeground(Color.WHITE);
		sl_newProductPanel.putConstraint(SpringLayout.NORTH, lblNewProduct, 30, SpringLayout.NORTH, newProductPanel);
		sl_newProductPanel.putConstraint(SpringLayout.SOUTH, lblNewProduct, -20, SpringLayout.SOUTH, newProductPanel);
		sl_newProductPanel.putConstraint(SpringLayout.WEST, lblNewProduct, 30, SpringLayout.WEST, newProductPanel);
		newProductPanel.add(lblNewProduct);
		
		formPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, formPanel, 20, SpringLayout.SOUTH, newProductPanel);
		sl_p.putConstraint(SpringLayout.WEST, formPanel, 10, SpringLayout.WEST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, formPanel, -10, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.EAST, formPanel, -250, SpringLayout.EAST, p);
		p.add(formPanel);
		
		imagePanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, imagePanel, 70, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.WEST, imagePanel, 10, SpringLayout.EAST, formPanel);
		SpringLayout sl_formPanel = new SpringLayout();
		formPanel.setLayout(sl_formPanel);
		
		lblProductID = new JLabel("Product ID");
		lblProductID.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.SOUTH, lblProductID, -345, SpringLayout.SOUTH, formPanel);
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblProductID, 20, SpringLayout.NORTH, formPanel);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblProductID, -310, SpringLayout.EAST, formPanel);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblProductID, 15, SpringLayout.WEST, formPanel);
		sl_p.putConstraint(SpringLayout.EAST, imagePanel, -10, SpringLayout.EAST, p);
		formPanel.add(lblProductID);
		
		txtProductID = new JTextField();
		txtProductID.setEnabled(false);
		txtProductID.setEditable(false);
		txtProductID.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.EAST, txtProductID, -160, SpringLayout.EAST, formPanel);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtProductID, 30, SpringLayout.EAST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtProductID, -5, SpringLayout.NORTH, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtProductID, 5, SpringLayout.SOUTH, lblProductID);
		formPanel.add(txtProductID);
		txtProductID.setColumns(10);
		
		lblName = new JLabel("Name");
		lblName.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.SOUTH, lblName, -300, SpringLayout.SOUTH, formPanel);
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblName, 20, SpringLayout.SOUTH, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblName, 0, SpringLayout.WEST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblName, 0, SpringLayout.EAST, lblProductID);
		formPanel.add(lblName);
		
		txtName = new JTextField();
		txtName.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.EAST, txtName, 75, SpringLayout.EAST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtName, 3, SpringLayout.SOUTH, lblName);
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtName, 0, SpringLayout.NORTH, lblName);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtName, 0, SpringLayout.WEST, txtProductID);
		formPanel.add(txtName);
		txtName.setColumns(10);
		
		lblStocks = new JLabel("Stocks");
		lblStocks.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.SOUTH, lblStocks, -260, SpringLayout.SOUTH, formPanel);
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblStocks, 20, SpringLayout.SOUTH, lblName);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblStocks, 0, SpringLayout.WEST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblStocks, 0, SpringLayout.EAST, lblName);
		formPanel.add(lblStocks);
		
		txtStocks = new JTextField();
		txtStocks.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.EAST, txtStocks, 50, SpringLayout.EAST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtStocks, 0, SpringLayout.NORTH, lblStocks);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtStocks, 0, SpringLayout.WEST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtStocks, 5, SpringLayout.SOUTH, lblStocks);
		formPanel.add(txtStocks);
		txtStocks.setColumns(10);
		
		lblUOM = new JLabel("Unit of");
		lblUOM.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.SOUTH, lblUOM, -220, SpringLayout.SOUTH, formPanel);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblUOM, 0, SpringLayout.EAST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblUOM, 20, SpringLayout.SOUTH, lblStocks);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblUOM, 0, SpringLayout.WEST, lblProductID);
		formPanel.add(lblUOM);
		
		lblUOM1 = new JLabel("Measurement");
		sl_formPanel.putConstraint(SpringLayout.SOUTH, lblUOM1, -200, SpringLayout.SOUTH, formPanel);
		lblUOM1.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblUOM1, 0, SpringLayout.SOUTH, lblUOM);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblUOM1, 0, SpringLayout.WEST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblUOM1, 0, SpringLayout.EAST, lblProductID);
		formPanel.add(lblUOM1);
		
		txtUOM = new JTextField();
		txtUOM.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.EAST, txtUOM, 50, SpringLayout.EAST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtUOM, 0, SpringLayout.NORTH, lblUOM);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtUOM, 0, SpringLayout.WEST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtUOM, 5, SpringLayout.SOUTH, lblUOM);
		formPanel.add(txtUOM);
		txtUOM.setColumns(10);
		
		lblPriceBought = new JLabel("Price Bought");
		sl_formPanel.putConstraint(SpringLayout.SOUTH, lblPriceBought, -160, SpringLayout.SOUTH, formPanel);
		lblPriceBought.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblPriceBought, 20, SpringLayout.SOUTH, lblUOM1);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblPriceBought, 0, SpringLayout.WEST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblPriceBought, 0, SpringLayout.EAST, lblProductID);
		formPanel.add(lblPriceBought);
		
		txtPriceBought = new JTextField();
		txtPriceBought.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtPriceBought, 0, SpringLayout.NORTH, lblPriceBought);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtPriceBought, 0, SpringLayout.WEST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtPriceBought, 5, SpringLayout.SOUTH, lblPriceBought);
		sl_formPanel.putConstraint(SpringLayout.EAST, txtPriceBought, 0, SpringLayout.EAST, txtUOM);
		formPanel.add(txtPriceBought);
		txtPriceBought.setColumns(10);
		
		lblSellingPrice = new JLabel("Selling Price");
		sl_formPanel.putConstraint(SpringLayout.SOUTH, lblSellingPrice, -120, SpringLayout.SOUTH, formPanel);
		lblSellingPrice.setFont(gallery.getFont(14f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, lblSellingPrice, 20, SpringLayout.SOUTH, lblPriceBought);
		sl_formPanel.putConstraint(SpringLayout.WEST, lblSellingPrice, 0, SpringLayout.WEST, lblProductID);
		sl_formPanel.putConstraint(SpringLayout.EAST, lblSellingPrice, 0, SpringLayout.EAST, lblProductID);
		formPanel.add(lblSellingPrice);
		
		txtSellingPrice = new JTextField();
		txtSellingPrice.setFont(gallery.getFont(15f));
		sl_formPanel.putConstraint(SpringLayout.NORTH, txtSellingPrice, 0, SpringLayout.NORTH, lblSellingPrice);
		sl_formPanel.putConstraint(SpringLayout.WEST, txtSellingPrice, 0, SpringLayout.WEST, txtProductID);
		sl_formPanel.putConstraint(SpringLayout.SOUTH, txtSellingPrice, 5, SpringLayout.SOUTH, lblSellingPrice);
		sl_formPanel.putConstraint(SpringLayout.EAST, txtSellingPrice, 0, SpringLayout.EAST, txtPriceBought);
		formPanel.add(txtSellingPrice);
		txtSellingPrice.setColumns(10);
		p.add(imagePanel);
		
		buttonPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.SOUTH, imagePanel, -10, SpringLayout.NORTH, buttonPanel);
		sl_p.putConstraint(SpringLayout.NORTH, buttonPanel, -100, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.WEST, buttonPanel, 0, SpringLayout.WEST, imagePanel);;
		sl_p.putConstraint(SpringLayout.SOUTH, buttonPanel, -10, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.EAST, buttonPanel, 0, SpringLayout.EAST, imagePanel);
		SpringLayout sl_imagePanel = new SpringLayout();
		imagePanel.setLayout(sl_imagePanel);
		
		lblImage = new JLabel("");
		lblImage.setBorder(new LineBorder(new Color(0, 0, 0)));
		sl_imagePanel.putConstraint(SpringLayout.SOUTH, lblImage, 240, SpringLayout.NORTH, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.NORTH, lblImage, 10, SpringLayout.NORTH, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.WEST, lblImage, 10, SpringLayout.WEST, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.EAST, lblImage, -10, SpringLayout.EAST, imagePanel);
		imagePanel.add(lblImage);
		
		btnUploadImage = new JLabel("Upload Image");
		btnUploadImage.setName("primary");
		gallery.styleLabelToButton(btnUploadImage, 14f, 15, 10);
		btnUploadImage.setHorizontalAlignment(SwingConstants.CENTER);
		sl_imagePanel.putConstraint(SpringLayout.SOUTH, btnUploadImage, -10, SpringLayout.SOUTH, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.NORTH, btnUploadImage, 10, SpringLayout.SOUTH, lblImage);
		sl_imagePanel.putConstraint(SpringLayout.WEST, btnUploadImage, 50, SpringLayout.WEST, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.EAST, btnUploadImage, -50, SpringLayout.EAST, imagePanel);
		imagePanel.add(btnUploadImage);
		p.add(buttonPanel);
		SpringLayout sl_buttonPanel = new SpringLayout();
		buttonPanel.setLayout(sl_buttonPanel);
		
		btnConfirm = new JLabel("Confirm");
		btnConfirm.setName("primary");
		gallery.styleLabelToButton(btnConfirm, 14f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnConfirm, -49, SpringLayout.SOUTH, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnConfirm, 10, SpringLayout.WEST, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnConfirm, -10, SpringLayout.EAST, buttonPanel);
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
		
		//Action Listeners
		btnUploadImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {gallery.buttonHovered(btnUploadImage);}
			@Override
			public void mouseExited(MouseEvent e) { gallery.buttonNormalized(btnUploadImage);}
			@Override
			public void mouseClicked(MouseEvent e) { 
				imagePath = utility.showImageChooser();
			}	
		});
		
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
