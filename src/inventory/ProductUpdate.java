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
	
	private JComboBox comboProductID;
	private JPanel contentPane, p, manageProductPanel, formsPanel, imagePanel, buttonsPanel;
	private JLabel btnCancel, btnConfirm, lblManageProduct, lblImageDisplay, btnUploadImage,lblName,lblStocks,lblUOM,lblUOM1,lblPriceBought,lblSellingPrice,lblProductID;
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
		sl_p.putConstraint(SpringLayout.NORTH, formsPanel, 20, SpringLayout.SOUTH, manageProductPanel);
		sl_p.putConstraint(SpringLayout.WEST, formsPanel, 10, SpringLayout.WEST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, formsPanel, -10, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.EAST, formsPanel, -250, SpringLayout.EAST, p);
		p.add(formsPanel);
		
		imagePanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, imagePanel, 70, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.WEST, imagePanel, 10, SpringLayout.EAST, formsPanel);
		SpringLayout sl_formsPanel = new SpringLayout();
		formsPanel.setLayout(sl_formsPanel);
		
		lblProductID = new JLabel("Product ID");
		lblProductID.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblProductID, 20, SpringLayout.NORTH, formsPanel);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblProductID, 15, SpringLayout.WEST, formsPanel);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, lblProductID, -345, SpringLayout.SOUTH, formsPanel);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblProductID, -310, SpringLayout.EAST, formsPanel);
		formsPanel.add(lblProductID);
		
		comboProductID = new JComboBox();
		sl_formsPanel.putConstraint(SpringLayout.NORTH, comboProductID, -5, SpringLayout.NORTH, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.WEST, comboProductID, 30, SpringLayout.EAST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, comboProductID, 5, SpringLayout.SOUTH, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, comboProductID, -160, SpringLayout.EAST, formsPanel);
		formsPanel.add(comboProductID);
		
		lblName = new JLabel("Name");
		lblName.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblName, 20, SpringLayout.SOUTH, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblName, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, lblName, -300, SpringLayout.SOUTH, formsPanel);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblName, 0, SpringLayout.EAST, lblProductID);
		formsPanel.add(lblName);
		
		txtName = new JTextField();
		txtName.setEnabled(false);
		txtName.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtName, 0, SpringLayout.NORTH, lblName);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtName, 0, SpringLayout.WEST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtName, 3, SpringLayout.SOUTH, lblName);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtName, 75, SpringLayout.EAST, comboProductID);
		formsPanel.add(txtName);
		txtName.setColumns(10);
		
		lblStocks = new JLabel("Stocks");
		lblStocks.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblStocks, 20, SpringLayout.SOUTH, lblName);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblStocks, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, lblStocks, -260, SpringLayout.SOUTH, formsPanel);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblStocks, 0, SpringLayout.EAST, lblName);
		formsPanel.add(lblStocks);
		
		txtStocks = new JTextField();
		txtStocks.setEnabled(false);
		txtStocks.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtStocks, 0, SpringLayout.NORTH, lblStocks);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtStocks, 0, SpringLayout.WEST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtStocks, 5, SpringLayout.SOUTH, lblStocks);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtStocks, 50, SpringLayout.EAST, comboProductID);
		formsPanel.add(txtStocks);
		txtStocks.setColumns(10);
		
		lblUOM = new JLabel("Unit Of");
		lblUOM.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblUOM, 20, SpringLayout.SOUTH, lblStocks);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblUOM, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, lblUOM, -220, SpringLayout.SOUTH, formsPanel);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblUOM, 0, SpringLayout.EAST, lblProductID);
		formsPanel.add(lblUOM);
		
		txtUOM = new JTextField();
		txtUOM.setEnabled(false);
		txtUOM.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtUOM, 0, SpringLayout.NORTH, lblUOM);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtUOM, 0, SpringLayout.WEST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtUOM, 5, SpringLayout.SOUTH, lblUOM);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtUOM, 0, SpringLayout.EAST, txtStocks);
		formsPanel.add(txtUOM);
		txtUOM.setColumns(10);
		
		lblUOM1 = new JLabel("Measurement");
		lblUOM1.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblUOM1, 0, SpringLayout.SOUTH, lblUOM);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblUOM1, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, lblUOM1, -200, SpringLayout.SOUTH, formsPanel);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblUOM1, 0, SpringLayout.EAST, lblUOM);
		formsPanel.add(lblUOM1);
		
		lblPriceBought = new JLabel("Price Bought");
		lblPriceBought.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblPriceBought, 20, SpringLayout.SOUTH, lblUOM1);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblPriceBought, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, lblPriceBought, -160, SpringLayout.SOUTH, formsPanel);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblPriceBought, 0, SpringLayout.EAST, lblProductID);
		formsPanel.add(lblPriceBought);
		
		txtPriceBought = new JTextField();
		txtPriceBought.setEnabled(false);
		txtPriceBought.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtPriceBought, 0, SpringLayout.EAST, txtStocks);
		txtPriceBought.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtPriceBought, 0, SpringLayout.NORTH, lblPriceBought);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtPriceBought, 0, SpringLayout.WEST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtPriceBought, 5, SpringLayout.SOUTH, lblPriceBought);
		formsPanel.add(txtPriceBought);
		txtPriceBought.setColumns(10);
		
		lblSellingPrice = new JLabel("Selling Price");
		lblSellingPrice.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblSellingPrice, 20, SpringLayout.SOUTH, lblPriceBought);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblSellingPrice, 0, SpringLayout.WEST, lblProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, lblSellingPrice, -120, SpringLayout.SOUTH, formsPanel);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblSellingPrice, 0, SpringLayout.EAST, lblProductID);
		formsPanel.add(lblSellingPrice);
		
		txtSellingPrice = new JTextField();
		txtSellingPrice.setEnabled(false);
		txtSellingPrice.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtSellingPrice, 0, SpringLayout.NORTH, lblSellingPrice);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtSellingPrice, 0, SpringLayout.WEST, comboProductID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtSellingPrice, 5, SpringLayout.SOUTH, lblSellingPrice);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtSellingPrice, 0, SpringLayout.EAST, txtStocks);
		formsPanel.add(txtSellingPrice);
		txtSellingPrice.setColumns(10);
		sl_p.putConstraint(SpringLayout.EAST, imagePanel, -10, SpringLayout.EAST, p);
		p.add(imagePanel);
		
		buttonsPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.SOUTH, imagePanel, -10, SpringLayout.NORTH, buttonsPanel);
		sl_p.putConstraint(SpringLayout.NORTH, buttonsPanel, -100, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.WEST, buttonsPanel, 0, SpringLayout.WEST, imagePanel);
		sl_p.putConstraint(SpringLayout.SOUTH, buttonsPanel, -10, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.EAST, buttonsPanel, 0, SpringLayout.EAST, imagePanel);
		SpringLayout sl_imagePanel = new SpringLayout();
		imagePanel.setLayout(sl_imagePanel);
		
		lblImageDisplay = new JLabel("");
		lblImageDisplay.setBorder(new LineBorder(new Color(0, 0, 0)));
		sl_imagePanel.putConstraint(SpringLayout.NORTH, lblImageDisplay, 10, SpringLayout.NORTH, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.WEST, lblImageDisplay, 10, SpringLayout.WEST, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.SOUTH, lblImageDisplay, 240, SpringLayout.NORTH, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.EAST, lblImageDisplay, -10, SpringLayout.EAST, imagePanel);
		imagePanel.add(lblImageDisplay);
		
		btnUploadImage = new JLabel("Upload Image");
		btnUploadImage.setName("primary");
		gallery.styleLabelToButton(btnUploadImage, 14f, 15, 10);
		btnUploadImage.setHorizontalAlignment(SwingConstants.CENTER);
		sl_imagePanel.putConstraint(SpringLayout.NORTH, btnUploadImage, 10, SpringLayout.SOUTH, lblImageDisplay);
		sl_imagePanel.putConstraint(SpringLayout.WEST, btnUploadImage, 50, SpringLayout.WEST, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.SOUTH, btnUploadImage, -10, SpringLayout.SOUTH, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.EAST, btnUploadImage, -50, SpringLayout.EAST, imagePanel);
		imagePanel.add(btnUploadImage);
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
		
		btnUploadImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { gallery.buttonHovered(btnUploadImage);}
			@Override
			public void mouseExited(MouseEvent e) { gallery.buttonNormalized(btnUploadImage);}
			@Override
			public void mouseClicked(MouseEvent e) {
				imagePath = utility.showImageChooser();
			}
		});
		
		
	}
}
