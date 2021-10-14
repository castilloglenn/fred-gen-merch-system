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

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.event.KeyAdapter;

public class SupplierUpdate extends JFrame {

	private JPanel contentPane, p, formsPanel, buttonPanel, manageSupplierPanel;
	private JLabel lblManageSupplier, btnConfirm, btnCancel;
	private Utility utility;
	private Gallery gallery;
	private JLabel lblSupplierID;
	private JTextField txtName;
	private JLabel lblContactNumber;
	private JTextField txtContactNumber;
	private JLabel lblAddress;
	private JTextField txtAddress;
	
	
	
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
		SpringLayout sl_formsPanel = new SpringLayout();
		formsPanel.setLayout(sl_formsPanel);
		
		lblSupplierID = new JLabel("Supplier ID");
		lblSupplierID.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblSupplierID, 25, SpringLayout.NORTH, formsPanel);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblSupplierID, 25, SpringLayout.WEST, formsPanel);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblSupplierID, 145, SpringLayout.WEST, formsPanel);
		formsPanel.add(lblSupplierID);
		
		JComboBox comboSupplierID = new JComboBox();
		comboSupplierID.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, comboSupplierID, -2, SpringLayout.NORTH, lblSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.WEST, comboSupplierID, 15, SpringLayout.EAST, lblSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, comboSupplierID, 2, SpringLayout.SOUTH, lblSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, comboSupplierID, -30, SpringLayout.EAST, formsPanel);
		formsPanel.add(comboSupplierID);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblName, 25, SpringLayout.SOUTH, lblSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblName, 0, SpringLayout.WEST, lblSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblName, 0, SpringLayout.EAST, lblSupplierID);
		formsPanel.add(lblName);
		
		txtName = new JTextField();
		txtName.setFont(gallery.getFont(15f));
		txtName.setEnabled(false);
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtName, -2, SpringLayout.NORTH, lblName);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtName, 0, SpringLayout.WEST, comboSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtName, 2, SpringLayout.SOUTH, lblName);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtName, 0, SpringLayout.EAST, comboSupplierID);
		formsPanel.add(txtName);
		txtName.setColumns(10);
		
		lblContactNumber = new JLabel("Contact Number");
		lblContactNumber.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblContactNumber, 25, SpringLayout.SOUTH, lblName);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblContactNumber, 0, SpringLayout.WEST, lblSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblContactNumber, 0, SpringLayout.EAST, lblSupplierID);
		formsPanel.add(lblContactNumber);
		
		txtContactNumber = new JTextField();
		txtContactNumber.setFont(gallery.getFont(15f));
		txtContactNumber.setEnabled(false);
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtContactNumber, -2, SpringLayout.NORTH, lblContactNumber);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtContactNumber, 0, SpringLayout.WEST, comboSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtContactNumber, 2, SpringLayout.SOUTH, lblContactNumber);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtContactNumber, 0, SpringLayout.EAST, comboSupplierID);
		formsPanel.add(txtContactNumber);
		txtContactNumber.setColumns(10);
		
		lblAddress = new JLabel("Address");
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblAddress, 25, SpringLayout.SOUTH, lblContactNumber);
		lblAddress.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblAddress, 0, SpringLayout.WEST, lblSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblAddress, 0, SpringLayout.EAST, lblSupplierID);
		formsPanel.add(lblAddress);
		
		txtAddress = new JTextField();
		txtAddress.setFont(gallery.getFont(15f));
		txtAddress.setEnabled(false);
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtAddress, -2, SpringLayout.NORTH, lblAddress);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtAddress, 0, SpringLayout.WEST, comboSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtAddress, 2, SpringLayout.SOUTH, lblAddress);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtAddress, 0, SpringLayout.EAST, comboSupplierID);
		formsPanel.add(txtAddress);
		txtAddress.setColumns(10);
		
		sl_p.putConstraint(SpringLayout.WEST, buttonPanel, -193, SpringLayout.EAST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, buttonPanel, -11, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.EAST, buttonPanel, -10, SpringLayout.EAST, p);
		p.add(buttonPanel);
		SpringLayout sl_buttonPanel = new SpringLayout();
		buttonPanel.setLayout(sl_buttonPanel);
				
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
		
		txtContactNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String phoneNumber = txtContactNumber.getText();
				constraintPhoneNumber(phoneNumber, e);
			}
		});
		
	}
	
	//User Defined Methods
	private void constraintPhoneNumber(String phoneNumber, KeyEvent evt){
		int length = phoneNumber.length();
		
			if(evt.getKeyChar()>='0' && evt.getKeyChar()<='9') {
			if(length<11) {
				txtContactNumber.setEditable(true);
			}
			else {
				txtContactNumber.setEditable(false);
			}
		}
		else {
			if(evt.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || evt.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
				txtContactNumber.setEditable(true);						
			}
			else {
				txtContactNumber.setEditable(false);
			}
		}
	}
}
