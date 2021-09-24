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
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class SupplierUpdate extends JFrame {

	private JPanel contentPane, p, formsPanel, buttonPanel, manageSupplierPanel;
	private JLabel lblName, lblManageSupplier, lblSupplierID, lblContactNumber, lblAddress, btnConfirm, btnCancel;
	private JComboBox comboSupplierID;
	private JTextField txtName, txtContactNumber;
	private JTextArea txtAddress;
	
	private Utility utility;
	private Gallery gallery;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupplierUpdate frame = new SupplierUpdate();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	
	public SupplierUpdate() {
		
		utility = new Utility();
		gallery = new Gallery();
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		
		p = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.WEST, p, -5, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, p, 5, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, p, 5, SpringLayout.EAST, contentPane);
		p.setBackground(Gallery.GRAY);
		sl_contentPane.putConstraint(SpringLayout.NORTH, p, -5, SpringLayout.NORTH, contentPane);
		contentPane.add(p);
		SpringLayout sl_p = new SpringLayout();
		p.setLayout(sl_p);
		
		formsPanel =  new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, formsPanel, 68, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.WEST, formsPanel, 10, SpringLayout.WEST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, formsPanel, -128, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.EAST, formsPanel, -10, SpringLayout.EAST, p);
		p.add(formsPanel);
		
		buttonPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, buttonPanel, 6, SpringLayout.SOUTH, formsPanel);
		sl_p.putConstraint(SpringLayout.WEST, buttonPanel, -193, SpringLayout.EAST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, buttonPanel, -11, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.EAST, buttonPanel, -10, SpringLayout.EAST, p);
		formsPanel.setLayout(null);
		p.add(buttonPanel);
		SpringLayout sl_buttonPanel = new SpringLayout();
		buttonPanel.setLayout(sl_buttonPanel);
		
		lblSupplierID = new JLabel("Supplier ID");
		lblSupplierID.setFont(gallery.getFont(15f));
		lblSupplierID.setBounds(10, 11, 80, 25);
		formsPanel.add(lblSupplierID);
		
		comboSupplierID = new JComboBox();
		comboSupplierID.setFont(gallery.getFont(15f));
		comboSupplierID.setBounds(141, 11, 146, 25);
		formsPanel.add(comboSupplierID);
		
		lblName = new JLabel("Name");
		lblName.setFont(gallery.getFont(15f));
		lblName.setBounds(10, 47, 80, 25);
		formsPanel.add(lblName);
		
		lblContactNumber = new JLabel("Contact Number");
		lblContactNumber.setFont(gallery.getFont(15f));
		lblContactNumber.setBounds(10, 85, 131, 25);
		formsPanel.add(lblContactNumber);
		
		lblAddress = new JLabel("Address");
		lblAddress.setFont(gallery.getFont(15f));
		lblAddress.setBounds(10, 121, 80, 25);
		formsPanel.add(lblAddress);
		
		txtName = new JTextField();
		txtName.setFont(gallery.getFont(15f));
		txtName.setBounds(141, 47, 305, 25);
		formsPanel.add(txtName);
		txtName.setColumns(10);
		
		txtContactNumber = new JTextField();
		txtContactNumber.setFont(gallery.getFont(15f));
		txtContactNumber.setColumns(10);
		txtContactNumber.setBounds(141, 85, 305, 25);
		formsPanel.add(txtContactNumber);
		
		txtAddress = new JTextArea();
		txtAddress.setFont(gallery.getFont(15f));
		txtAddress.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtAddress.setBounds(141, 119, 305, 70);
		formsPanel.add(txtAddress);
				
		btnConfirm = new JLabel("Confirm");
		btnConfirm.setName("primary");
		gallery.styleLabelToButton(btnConfirm, 15f, 15, 10);
		btnConfirm.setHorizontalAlignment(SwingConstants.CENTER);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnConfirm, 10, SpringLayout.NORTH, buttonPanel);
		buttonPanel.add(btnConfirm);
		
		btnCancel = new JLabel("Cancel");
		btnCancel.setName("danger");
		gallery.styleLabelToButton(btnCancel, 15f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnConfirm, 0, SpringLayout.WEST, btnCancel);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnConfirm, -15, SpringLayout.NORTH, btnCancel);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnConfirm, 0, SpringLayout.EAST, btnCancel);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnCancel, -48, SpringLayout.SOUTH, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnCancel, 10, SpringLayout.WEST, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnCancel, 173, SpringLayout.WEST, buttonPanel);
		btnCancel.setHorizontalAlignment(SwingConstants.CENTER);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnCancel, -10, SpringLayout.SOUTH, buttonPanel);
		buttonPanel.add(btnCancel);
		
		manageSupplierPanel = new RoundedPanel(gallery.BLUE);
		sl_p.putConstraint(SpringLayout.NORTH, manageSupplierPanel, -15, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.WEST, manageSupplierPanel, -15, SpringLayout.WEST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, manageSupplierPanel, 50, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.EAST, manageSupplierPanel, 200, SpringLayout.WEST, p);
		p.add(manageSupplierPanel);
		SpringLayout sl_manageSupplierPanel = new SpringLayout();
		manageSupplierPanel.setLayout(sl_manageSupplierPanel);
		
		lblManageSupplier = new JLabel("Manage Supplier");
		sl_manageSupplierPanel.putConstraint(SpringLayout.WEST, lblManageSupplier, 20, SpringLayout.WEST, manageSupplierPanel);
		lblManageSupplier.setFont(gallery.getFont(20f));
		lblManageSupplier.setForeground(Color.WHITE);
		sl_manageSupplierPanel.putConstraint(SpringLayout.NORTH, lblManageSupplier, 23, SpringLayout.NORTH, manageSupplierPanel);
		manageSupplierPanel.add(lblManageSupplier);
		
		//Action Listeners
		
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { gallery.buttonHovered(btnConfirm);}
			@Override
			public void mouseExited(MouseEvent e) { gallery.buttonNormalized(btnConfirm);}
			@Override
			public void mouseClicked(MouseEvent e) { System.out.println("confirmed");	}
		});
		
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { gallery.buttonHovered(btnCancel); }
			@Override
			public void mouseExited(MouseEvent e) {	gallery.buttonNormalized(btnCancel); }
			@Override
			public void mouseClicked(MouseEvent e) { dispose();}
		});
		
	}
}
