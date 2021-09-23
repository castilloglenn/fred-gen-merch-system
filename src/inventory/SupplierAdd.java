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
	
	private JPanel contentPane, p, formsPanel, buttonPanel;
	private JLabel lblNewSupplier, btnCancel, btnAdd, lblSupplierID, lblName, lblContactNumber, lblAddress;
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
		
		lblNewSupplier = new JLabel("New Supplier");
		sl_p.putConstraint(SpringLayout.SOUTH, lblNewSupplier, -6, SpringLayout.NORTH, formsPanel);
		sl_p.putConstraint(SpringLayout.EAST, lblNewSupplier, 139, SpringLayout.WEST, formsPanel);
		sl_p.putConstraint(SpringLayout.NORTH, lblNewSupplier, 10, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.WEST, lblNewSupplier, 0, SpringLayout.WEST, formsPanel);
		lblNewSupplier.setFont(utility.getFont(20f));
		p.add(lblNewSupplier);
		
		buttonPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, buttonPanel, 6, SpringLayout.SOUTH, formsPanel);
		formsPanel.setLayout(null);
		
		lblSupplierID = new JLabel("Supplier ID");
		lblSupplierID.setFont(utility.getFont(15f));
		lblSupplierID.setBounds(10, 22, 80, 25);
		formsPanel.add(lblSupplierID);
		
		lblName = new JLabel("Name");
		lblName.setFont(utility.getFont(15f));
		lblName.setBounds(10, 65, 80, 25);
		formsPanel.add(lblName);
		
		txtName = new JTextField();
		txtName.setFont(utility.getFont(15f));
		txtName.setColumns(10);
		txtName.setBounds(153, 59, 276, 27);
		formsPanel.add(txtName);
		
		txtSupplierID = new JTextField();
		txtSupplierID.setFont(utility.getFont(15f));
		txtSupplierID.setEditable(false);
		txtSupplierID.setColumns(10);
		txtSupplierID.setBounds(153, 21, 151, 27);
		formsPanel.add(txtSupplierID);
		
		lblContactNumber = new JLabel("Contact Number");
		lblContactNumber.setFont(utility.getFont(15f));
		lblContactNumber.setBounds(10, 99, 133, 25);
		formsPanel.add(lblContactNumber);
		
		txtContactNumber = new JTextField();
		txtContactNumber.setFont(utility.getFont(15f));
		txtContactNumber.setColumns(10);
		txtContactNumber.setBounds(153, 101, 276, 27);
		formsPanel.add(txtContactNumber);
		
		lblAddress = new JLabel("Address");
		lblAddress.setFont(utility.getFont(15f));
		lblAddress.setBounds(10, 140, 133, 25);
		formsPanel.add(lblAddress);
		
		txtAddress = new JTextField();
		txtAddress.setFont(utility.getFont(15f));
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
		utility.styleLabelToButton(btnCancel, 15f, 15, 10);
		buttonPanel.add(btnCancel);
		
		btnAdd = new JLabel("Add");
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnAdd, 10, SpringLayout.NORTH, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnAdd, 0, SpringLayout.WEST, btnCancel);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnAdd, -15, SpringLayout.NORTH, btnCancel);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnAdd, 0, SpringLayout.EAST, btnCancel);
		btnAdd.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnAdd.setFont(utility.getFont(15f));
		btnAdd.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.add(btnAdd);
		
		
		
		//	ActionListeners
		
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { mouseEnter2(btnAdd);}
			@Override
			public void mouseExited(MouseEvent e) { mouseExit2(btnAdd);}
			@Override
			public void mouseClicked(MouseEvent e) { System.out.println("Added");}
		});
		
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { utility.buttonHovered(btnCancel);}
			@Override
			public void mouseExited(MouseEvent e) { utility.buttonNormalized(btnCancel);}
			@Override
			public void mouseClicked(MouseEvent e) { dispose();}
		});
		
		
	}
		
	//Methods
	public void mouseEnter2(JLabel label) {
		label.setBackground(Gallery.BLUE);
		label.setForeground(Gallery.WHITE);
		label.setOpaque(true);
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public void mouseExit2(JLabel label) {
		label.setBackground(Gallery.WHITE);
		label.setForeground(Gallery.BLACK);
	}
}