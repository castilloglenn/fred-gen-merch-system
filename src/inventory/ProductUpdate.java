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

public class ProductUpdate extends JFrame {
	
	private Utility utility;
	private Gallery gallery;
	private String imagePath;

	private JPanel contentPane;

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
		
		JPanel p = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, p, -5, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, p, -5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, p, 5, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, p, 5, SpringLayout.EAST, contentPane);
		contentPane.add(p);
		SpringLayout sl_p = new SpringLayout();
		p.setLayout(sl_p);
		
		JPanel manageProductPanel = new RoundedPanel(gallery.BLUE);
		sl_p.putConstraint(SpringLayout.NORTH, manageProductPanel, -15, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.WEST, manageProductPanel, -15, SpringLayout.WEST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, manageProductPanel, 50, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.EAST, manageProductPanel, 200, SpringLayout.WEST, p);
		p.add(manageProductPanel);
		SpringLayout sl_manageProductPanel = new SpringLayout();
		manageProductPanel.setLayout(sl_manageProductPanel);
		
		JLabel lblManageProduct = new JLabel("Manage Product");
		lblManageProduct.setFont(gallery.getFont(20f));
		lblManageProduct.setForeground(Color.WHITE);
		sl_manageProductPanel.putConstraint(SpringLayout.NORTH, lblManageProduct, 25, SpringLayout.NORTH, manageProductPanel);
		sl_manageProductPanel.putConstraint(SpringLayout.SOUTH, lblManageProduct, -15, SpringLayout.SOUTH, manageProductPanel);
		sl_manageProductPanel.putConstraint(SpringLayout.WEST, lblManageProduct, 30, SpringLayout.WEST, manageProductPanel);
		manageProductPanel.add(lblManageProduct);
		
		JPanel formsPanel = new RoundedPanel(gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, formsPanel, 20, SpringLayout.SOUTH, manageProductPanel);
		sl_p.putConstraint(SpringLayout.WEST, formsPanel, 10, SpringLayout.WEST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, formsPanel, -10, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.EAST, formsPanel, -250, SpringLayout.EAST, p);
		p.add(formsPanel);
		
		JPanel imagePanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, imagePanel, 70, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.WEST, imagePanel, 10, SpringLayout.EAST, formsPanel);
		sl_p.putConstraint(SpringLayout.EAST, imagePanel, -10, SpringLayout.EAST, p);
		p.add(imagePanel);
		
		JPanel buttonsPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.SOUTH, imagePanel, -10, SpringLayout.NORTH, buttonsPanel);
		sl_p.putConstraint(SpringLayout.NORTH, buttonsPanel, -100, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.WEST, buttonsPanel, 0, SpringLayout.WEST, imagePanel);
		sl_p.putConstraint(SpringLayout.SOUTH, buttonsPanel, -10, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.EAST, buttonsPanel, 0, SpringLayout.EAST, imagePanel);
		p.add(buttonsPanel);

		
		
	}
}
