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

public class ProductAdd extends JFrame {

	private Utility utility;
	private Gallery gallery;
	
	private JPanel contentPane, p, newProductPanel;
	private JLabel lblNewProduct;
	private JPanel imagePanel;
	private JPanel buttonPanel;

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
		sl_newProductPanel.putConstraint(SpringLayout.NORTH, lblNewProduct, 30, SpringLayout.NORTH, newProductPanel);
		sl_newProductPanel.putConstraint(SpringLayout.SOUTH, lblNewProduct, -20, SpringLayout.SOUTH, newProductPanel);
		lblNewProduct.setFont(gallery.getFont(20f));
		lblNewProduct.setForeground(Color.WHITE);
		sl_newProductPanel.putConstraint(SpringLayout.WEST, lblNewProduct, 30, SpringLayout.WEST, newProductPanel);
		newProductPanel.add(lblNewProduct);
		
		JPanel formPanel = new RoundedPanel(Gallery.WHITE);
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
		
		buttonPanel = new RoundedPanel(Gallery.WHITE);;
		sl_p.putConstraint(SpringLayout.NORTH, buttonPanel, 10, SpringLayout.SOUTH, imagePanel);
		sl_p.putConstraint(SpringLayout.WEST, buttonPanel, 0, SpringLayout.WEST, imagePanel);
		sl_p.putConstraint(SpringLayout.SOUTH, buttonPanel, -10, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.EAST, buttonPanel, 0, SpringLayout.EAST, imagePanel);
		p.add(buttonPanel);
		
	}
}
