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

public class ProductAdd extends JFrame {

	private Utility utility;
	private Gallery gallery;
	private String imagePath;
	
	private JPanel contentPane, p, newProductPanel, imagePanel, buttonPanel, formPanel;
	private JLabel lblNewProduct, btnUploadImage, btnConfirm, btnCancel, lblImage;
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
		sl_p.putConstraint(SpringLayout.NORTH, formPanel, 15, SpringLayout.SOUTH, newProductPanel);
		sl_p.putConstraint(SpringLayout.WEST, formPanel, 10, SpringLayout.WEST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, formPanel, -100, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.EAST, formPanel, -250, SpringLayout.EAST, p);
		p.add(formPanel);
		formPanel.setLayout(new SpringLayout());
		
		imagePanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, imagePanel, 0, SpringLayout.NORTH, formPanel);
		sl_p.putConstraint(SpringLayout.WEST, imagePanel, 10, SpringLayout.EAST, formPanel);
		sl_p.putConstraint(SpringLayout.SOUTH, imagePanel, 0, SpringLayout.SOUTH, formPanel);
		sl_p.putConstraint(SpringLayout.EAST, imagePanel, -10, SpringLayout.EAST, p);
		p.add(imagePanel);
		
		buttonPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.WEST, buttonPanel, 0, SpringLayout.WEST, imagePanel);;
		sl_p.putConstraint(SpringLayout.NORTH, buttonPanel, 10, SpringLayout.SOUTH, imagePanel);
		sl_p.putConstraint(SpringLayout.SOUTH, buttonPanel, -10, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.EAST, buttonPanel, 0, SpringLayout.EAST, imagePanel);
		SpringLayout sl_imagePanel = new SpringLayout();
		imagePanel.setLayout(sl_imagePanel);
		
		lblImage = new JLabel("");
		lblImage.setBorder(new LineBorder(new Color(0, 0, 0)));
		sl_imagePanel.putConstraint(SpringLayout.NORTH, lblImage, 10, SpringLayout.NORTH, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.WEST, lblImage, 10, SpringLayout.WEST, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.SOUTH, lblImage, 250, SpringLayout.NORTH, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.EAST, lblImage, -10, SpringLayout.EAST, imagePanel);
		imagePanel.add(lblImage);
		
		btnUploadImage = new JLabel("Upload Image");
		btnUploadImage.setName("primary");
		gallery.styleLabelToButton(btnUploadImage, 14f, 15, 10);
		btnUploadImage.setHorizontalAlignment(SwingConstants.CENTER);
		sl_imagePanel.putConstraint(SpringLayout.NORTH, btnUploadImage, 10, SpringLayout.SOUTH, lblImage);
		sl_imagePanel.putConstraint(SpringLayout.WEST, btnUploadImage, 50, SpringLayout.WEST, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.SOUTH, btnUploadImage, -10, SpringLayout.SOUTH, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.EAST, btnUploadImage, -50, SpringLayout.EAST, imagePanel);
		imagePanel.add(btnUploadImage);
		p.add(buttonPanel);
		SpringLayout sl_buttonPanel = new SpringLayout();
		buttonPanel.setLayout(sl_buttonPanel);
		
		btnConfirm = new JLabel("Confirm");
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnConfirm, -43, SpringLayout.SOUTH, buttonPanel);
		btnConfirm.setName("primary");
		gallery.styleLabelToButton(btnConfirm, 14f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnConfirm, 10, SpringLayout.WEST, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnConfirm, -10, SpringLayout.EAST, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnConfirm, 10, SpringLayout.NORTH, buttonPanel);
		btnConfirm.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.add(btnConfirm);
		
		btnCancel = new JLabel("Cancel");
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnCancel, 7, SpringLayout.SOUTH, btnConfirm);
		btnCancel.setName("danger");
		gallery.styleLabelToButton(btnCancel, 14f, 15, 10);
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
