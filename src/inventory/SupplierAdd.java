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
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class SupplierAdd extends JFrame {
	
	private Utility utility;
	private Gallery gallery;
	
	private JPanel contentPane, p, formsPanel, buttonPanel, newSupplierPanel;
	private JLabel btnAdd, lblSupplierID, lblName, lblContactNumber, lblAddress, lblNewSupplier;
	private JTextField txtName, txtSupplierID, txtContactNumber;
	private JTextArea textArea;
	
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
		setLocationRelativeTo(null);
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
		lblSupplierID.setBounds(10, 11, 80, 25);
		formsPanel.add(lblSupplierID);
		
		lblName = new JLabel("Name");
		lblName.setFont(gallery.getFont(15f));
		lblName.setBounds(10, 54, 80, 25);
		formsPanel.add(lblName);
		
		txtName = new JTextField();
		txtName.setFont(gallery.getFont(15f));
		txtName.setColumns(10);
		txtName.setBounds(160, 49, 276, 27);
		formsPanel.add(txtName);
		
		txtSupplierID = new JTextField();
		txtSupplierID.setFont(gallery.getFont(15f));
		txtSupplierID.setEditable(false);
		txtSupplierID.setColumns(10);
		txtSupplierID.setBounds(163, 10, 151, 27);
		formsPanel.add(txtSupplierID);
		
		lblContactNumber = new JLabel("Contact Number");
		lblContactNumber.setFont(gallery.getFont(15f));
		lblContactNumber.setBounds(10, 88, 133, 25);
		formsPanel.add(lblContactNumber);
		
		txtContactNumber = new JTextField();
		txtContactNumber.setBorder(new LineBorder(new Color(171, 173, 179)));
		txtContactNumber.setFont(gallery.getFont(15f));
		txtContactNumber.setColumns(10);
		txtContactNumber.setBounds(160, 91, 276, 27);
		formsPanel.add(txtContactNumber);
		
		lblAddress = new JLabel("Address");
		lblAddress.setFont(gallery.getFont(15f));
		lblAddress.setBounds(10, 129, 126, 25);
		formsPanel.add(lblAddress);
		
		textArea = new JTextArea();
		textArea.setBounds(153, 140, 5, 22);
		formsPanel.add(textArea);
		txtContactNumber.setFont(gallery.getFont(15f));
		
		sl_p.putConstraint(SpringLayout.WEST, buttonPanel, -193, SpringLayout.EAST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, buttonPanel, -11, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.EAST, buttonPanel, -10, SpringLayout.EAST, p);
		p.add(buttonPanel);
		SpringLayout sl_buttonPanel = new SpringLayout();
		buttonPanel.setLayout(sl_buttonPanel);
		
		btnAdd = new JLabel("Add");
		btnAdd.setName("primary");
		gallery.styleLabelToButton(btnAdd, 15f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnAdd, 15, SpringLayout.NORTH, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnAdd, 15, SpringLayout.WEST, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnAdd, -60, SpringLayout.SOUTH, buttonPanel);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnAdd, -15, SpringLayout.EAST, buttonPanel);
		btnAdd.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.add(btnAdd);
		
		JLabel btnDelete = new JLabel("Delete");
		btnDelete.setName("danger");
		gallery.styleLabelToButton(btnDelete, 15f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnDelete, 0, SpringLayout.WEST, btnAdd);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnDelete, 0, SpringLayout.EAST, btnAdd);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnDelete, 10, SpringLayout.SOUTH, btnAdd);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnDelete, -15, SpringLayout.SOUTH, buttonPanel);
		btnDelete.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.add(btnDelete);
		
		newSupplierPanel = new RoundedPanel(Gallery.BLUE);
		sl_p.putConstraint(SpringLayout.NORTH, newSupplierPanel, -15, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.WEST, newSupplierPanel, -15, SpringLayout.WEST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, newSupplierPanel, 50, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.EAST, newSupplierPanel, 200, SpringLayout.WEST, p);
		SpringLayout sl_newSupplierPanel = new SpringLayout();
		newSupplierPanel.setLayout(sl_newSupplierPanel);
		p.add(newSupplierPanel);
		
		lblNewSupplier = new JLabel("New Supplier");
		lblNewSupplier.setFont(gallery.getFont(20f));
		lblNewSupplier.setForeground(Color.WHITE);
		sl_newSupplierPanel.putConstraint(SpringLayout.NORTH, lblNewSupplier, 23, SpringLayout.NORTH, newSupplierPanel);
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
		
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { gallery.buttonHovered(btnDelete);}
			@Override
			public void mouseExited(MouseEvent e) { gallery.buttonNormalized(btnDelete);}
			@Override
			public void mouseClicked(MouseEvent e) {dispose();}
		});
	}
}
