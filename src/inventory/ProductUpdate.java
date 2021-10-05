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

public class ProductUpdate extends JFrame {
	
	private Utility utility;
	private Gallery gallery;
	private String imagePath;

	private JPanel contentPane, p, manageProductPanel, formsPanel, imagePanel, buttonsPanel;
	private JLabel btnCancel, btnConfirm, lblManageProduct;

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
		formsPanel.setLayout(new SpringLayout());
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
		
		JLabel lblImageDisplay = new JLabel("");
		lblImageDisplay.setBorder(new LineBorder(new Color(0, 0, 0)));
		sl_imagePanel.putConstraint(SpringLayout.NORTH, lblImageDisplay, 10, SpringLayout.NORTH, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.WEST, lblImageDisplay, 10, SpringLayout.WEST, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.SOUTH, lblImageDisplay, 240, SpringLayout.NORTH, imagePanel);
		sl_imagePanel.putConstraint(SpringLayout.EAST, lblImageDisplay, -10, SpringLayout.EAST, imagePanel);
		imagePanel.add(lblImageDisplay);
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
