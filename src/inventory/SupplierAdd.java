package inventory;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.Gallery;
import utils.RoundedPanel;
import utils.Utility;

import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;

public class SupplierAdd extends JFrame {
	
	private Utility utility;
	private Gallery gallery;
	
	private JPanel contentPane, p, formsPanel, buttonPanel, newSupplierPanel;
	private JLabel btnCancel, btnAdd, lblSupplierID, lblName, lblContactNumber, lblAddress;
	private JTextField txtName, txtSupplierID, txtContactNumber, txtAddress;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupplierAdd frame = new SupplierAdd();
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
	public SupplierAdd() {
		
		utility = new Utility();
		gallery = new Gallery();
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 450);
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
		contentPane.add(p);
		SpringLayout sl_p = new SpringLayout();
		p.setLayout(sl_p);
		
		formsPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, formsPanel, 68, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.SOUTH, formsPanel, -128, SpringLayout.SOUTH, p);;
		sl_p.putConstraint(SpringLayout.WEST, formsPanel, 10, SpringLayout.WEST, p);
		sl_p.putConstraint(SpringLayout.EAST, formsPanel, -10, SpringLayout.EAST, p);
		formsPanel.setBackground(gallery.WHITE);
		p.add(formsPanel);
		
		buttonPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, buttonPanel, 6, SpringLayout.SOUTH, formsPanel);
		formsPanel.setLayout(null);
		
		lblSupplierID = new JLabel("Supplier ID");
		lblSupplierID.setFont(gallery.getFont(15f));
		lblSupplierID.setBounds(10, 22, 80, 25);
		formsPanel.add(lblSupplierID);
		
		lblName = new JLabel("Name");
		lblName.setFont(gallery.getFont(15f));
		lblName.setBounds(10, 65, 80, 25);
		formsPanel.add(lblName);
		
		txtName = new JTextField();
		txtName.setFont(gallery.getFont(15f));
		txtName.setColumns(10);
		txtName.setBounds(153, 59, 276, 27);
		formsPanel.add(txtName);
		
		txtSupplierID = new JTextField();
		txtSupplierID.setFont(gallery.getFont(15f));
		txtSupplierID.setEditable(false);
		txtSupplierID.setColumns(10);
		txtSupplierID.setBounds(153, 21, 151, 27);
		formsPanel.add(txtSupplierID);
		
		lblContactNumber = new JLabel("Contact Number");
		lblContactNumber.setFont(gallery.getFont(15f));
		lblContactNumber.setBounds(10, 99, 133, 25);
		formsPanel.add(lblContactNumber);
		
		txtContactNumber = new JTextField();
		txtContactNumber.setFont(gallery.getFont(15f));
		txtContactNumber.setColumns(10);
		txtContactNumber.setBounds(153, 101, 276, 27);
		formsPanel.add(txtContactNumber);
		
		lblAddress = new JLabel("Address");
		lblAddress.setFont(gallery.getFont(15f));
		lblAddress.setBounds(10, 140, 133, 25);
		formsPanel.add(lblAddress);
		
		txtAddress = new JTextField();
		txtAddress.setFont(gallery.getFont(15f));
		txtAddress.setColumns(10);
		txtAddress.setBounds(153, 139, 276, 27);
		formsPanel.add(txtAddress);
		
		sl_p.putConstraint(SpringLayout.WEST, buttonPanel, -193, SpringLayout.EAST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, buttonPanel, -11, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.EAST, buttonPanel, -10, SpringLayout.EAST, p);
		p.add(buttonPanel);
		SpringLayout sl_buttonPanel = new SpringLayout();
		buttonPanel.setLayout(sl_buttonPanel);
		
		btnCancel = new JLabel("Cancel");
		btnCancel.setName("danger");
		btnCancel.setHorizontalAlignment(SwingConstants.CENTER);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnCancel, -48, SpringLayout.SOUTH, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnCancel, 10, SpringLayout.WEST, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnCancel, -10, SpringLayout.SOUTH, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnCancel, 173, SpringLayout.WEST, buttonPanel);
		gallery.styleLabelToButton(btnCancel, 15f, 15, 10);
		buttonPanel.add(btnCancel);
		
		btnAdd = new JLabel("Add");
		btnAdd.setHorizontalAlignment(SwingConstants.CENTER);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnAdd, 10, SpringLayout.NORTH, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnAdd, 0, SpringLayout.WEST, btnCancel);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnAdd, -15, SpringLayout.NORTH, btnCancel);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnAdd, 0, SpringLayout.EAST, btnCancel);
		btnAdd.setName("primary");
		gallery.styleLabelToButton(btnAdd, 15f, 15, 10);
		buttonPanel.add(btnAdd);
		
		newSupplierPanel = new RoundedPanel(Gallery.BLUE);
		sl_p.putConstraint(SpringLayout.NORTH, newSupplierPanel, -15, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.WEST, newSupplierPanel, -15, SpringLayout.WEST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, newSupplierPanel, 50, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.EAST, newSupplierPanel, 200, SpringLayout.WEST, p);
		p.add(newSupplierPanel);
		SpringLayout sl_newSupplierPanel = new SpringLayout();
		newSupplierPanel.setLayout(sl_newSupplierPanel);
		
		JLabel lblNewSupplier = new JLabel("New Supplier");
		sl_newSupplierPanel.putConstraint(SpringLayout.NORTH, lblNewSupplier, 23, SpringLayout.NORTH, newSupplierPanel);
		lblNewSupplier.setFont(gallery.getFont(20f));
		lblNewSupplier.setForeground(Color.WHITE);
		sl_newSupplierPanel.putConstraint(SpringLayout.WEST, lblNewSupplier, 30, SpringLayout.WEST, newSupplierPanel);
		newSupplierPanel.add(lblNewSupplier);
		
		
		
		//	ActionListeners
		
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { gallery.buttonHovered(btnAdd);}
			@Override
			public void mouseExited(MouseEvent e) { gallery.buttonNormalized(btnAdd);}
			@Override
			public void mouseClicked(MouseEvent e) { System.out.println("Added");}
		});
		
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { gallery.buttonHovered(btnCancel);}
			@Override
			public void mouseExited(MouseEvent e) { gallery.buttonNormalized(btnCancel);}
			@Override
			public void mouseClicked(MouseEvent e) { dispose();}
		});
		
		
	}
	
}
