package inventory;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import utils.Database;
import utils.Gallery;
import utils.Logger;
import utils.RoundedPanel;
import utils.Utility;

@SuppressWarnings("serial")
public class SupplierAdd extends JFrame {
	
	private final String TITLE = "New Supplier";
	
	private Database database;
	private Utility utility;
	private Gallery gallery;
	private Logger logger;
	
	private JPanel contentPane, p, buttonPanel, newSupplierPanel;
	private JLabel btnAdd, lblNewSupplier,btnCancel,lblName,lblContactNumber,lblSupplierID,lblAddress;
	private JTextField txtSupplierID,txtName,txtContactNumber,txtAddress;

	
	public SupplierAdd(Object[] user) {
		database = Database.getInstance();
		utility = Utility.getInstance();
		gallery = Gallery.getInstance();
		logger = Logger.getInstance();

		setIconImage(gallery.getSystemIcon());
		setTitle(TITLE + Utility.TITLE_SEPARATOR + Utility.BUSINESS_TITLE);
		
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
		p.setLayout(sl_p);;
		
		buttonPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, buttonPanel, 299, SpringLayout.NORTH, p);
		sl_p.putConstraint(SpringLayout.SOUTH, buttonPanel, -11, SpringLayout.SOUTH, p);
		sl_p.putConstraint(SpringLayout.WEST, buttonPanel, -193, SpringLayout.EAST, p);
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
		
		btnCancel = new JLabel("Cancel");
		btnCancel.setName("danger");
		gallery.styleLabelToButton(btnCancel, 15f, 15, 10);
		sl_buttonPanel.putConstraint(SpringLayout.WEST, btnCancel, 0, SpringLayout.WEST, btnAdd);
		sl_buttonPanel.putConstraint(SpringLayout.EAST, btnCancel, 0, SpringLayout.EAST, btnAdd);
		sl_buttonPanel.putConstraint(SpringLayout.NORTH, btnCancel, 10, SpringLayout.SOUTH, btnAdd);
		sl_buttonPanel.putConstraint(SpringLayout.SOUTH, btnCancel, -15, SpringLayout.SOUTH, buttonPanel);
		btnCancel.setHorizontalAlignment(SwingConstants.CENTER);
		buttonPanel.add(btnCancel);
		
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
		
		JPanel formsPanel = new RoundedPanel(Gallery.WHITE);
		sl_p.putConstraint(SpringLayout.NORTH, formsPanel, 10, SpringLayout.SOUTH, newSupplierPanel);
		sl_p.putConstraint(SpringLayout.WEST, formsPanel, 10, SpringLayout.WEST, p);
		sl_p.putConstraint(SpringLayout.SOUTH, formsPanel, -10, SpringLayout.NORTH, buttonPanel);
		sl_p.putConstraint(SpringLayout.EAST, formsPanel, -10, SpringLayout.EAST, p);
		p.add(formsPanel);
		SpringLayout sl_formsPanel = new SpringLayout();
		formsPanel.setLayout(sl_formsPanel);
		
		lblSupplierID = new JLabel("Supplier ID");
		lblSupplierID.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblSupplierID, 25, SpringLayout.WEST, formsPanel);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblSupplierID, 145, SpringLayout.WEST, formsPanel);
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblSupplierID, 25, SpringLayout.NORTH, formsPanel);
		formsPanel.add(lblSupplierID);
		
		txtSupplierID = new JTextField(Long.toString(utility.generateSupplierID(database.fetchLastID("supplier", "supplier_id"))));
		txtSupplierID.setFont(gallery.getFont(15f));
		txtSupplierID.setEditable(false);
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtSupplierID, -2, SpringLayout.NORTH, lblSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtSupplierID, 15, SpringLayout.EAST, lblSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtSupplierID, 2, SpringLayout.SOUTH, lblSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtSupplierID, -30, SpringLayout.EAST, formsPanel);
		formsPanel.add(txtSupplierID);
		txtSupplierID.setColumns(10);
		
		lblName = new JLabel("Name");
		lblName.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblName, 25, SpringLayout.SOUTH, lblSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblName, 0, SpringLayout.WEST, lblSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblName, 0, SpringLayout.EAST, lblSupplierID);
		formsPanel.add(lblName);
		
		txtName = new JTextField();
		txtName.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtName, -2, SpringLayout.NORTH, lblName);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtName, 0, SpringLayout.WEST, txtSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtName, 2, SpringLayout.SOUTH, lblName);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtName, 0, SpringLayout.EAST, txtSupplierID);
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
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtContactNumber, -2, SpringLayout.NORTH, lblContactNumber);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtContactNumber, 0, SpringLayout.WEST, txtSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtContactNumber, 2, SpringLayout.SOUTH, lblContactNumber);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtContactNumber, 0, SpringLayout.EAST, txtSupplierID);
		formsPanel.add(txtContactNumber);
		txtContactNumber.setColumns(10);
		
		lblAddress = new JLabel("Address");
		lblAddress.setFont(gallery.getFont(14f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, lblAddress, 25, SpringLayout.SOUTH, lblContactNumber);
		sl_formsPanel.putConstraint(SpringLayout.WEST, lblAddress, 0, SpringLayout.WEST, lblSupplierID);
		sl_formsPanel.putConstraint(SpringLayout.EAST, lblAddress, 0, SpringLayout.EAST, lblSupplierID);
		formsPanel.add(lblAddress);
		
		txtAddress = new JTextField();
		txtAddress.setFont(gallery.getFont(15f));
		sl_formsPanel.putConstraint(SpringLayout.NORTH, txtAddress, -2, SpringLayout.NORTH, lblAddress);
		sl_formsPanel.putConstraint(SpringLayout.WEST, txtAddress, 15, SpringLayout.EAST, lblAddress);
		sl_formsPanel.putConstraint(SpringLayout.SOUTH, txtAddress, 2, SpringLayout.SOUTH, lblAddress);
		sl_formsPanel.putConstraint(SpringLayout.EAST, txtAddress, 0, SpringLayout.EAST, txtSupplierID);
		formsPanel.add(txtAddress);
		txtAddress.setColumns(10);
		
		
		
		//	ActionListeners
		
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { gallery.buttonHovered(btnAdd);}
			@Override
			public void mouseExited(MouseEvent e) { gallery.buttonNormalized(btnAdd);}
			@Override
			public void mouseClicked(MouseEvent e) {
				 if (checkFields()) {
					 long id = Long.parseLong(txtSupplierID.getText());
					 String name = txtName.getText();
					 String contactNum = txtContactNumber.getText();
					 String address = txtAddress.getText();
					 
					 if (database.addSupplier(id, name, contactNum, address)) {
						 logger.addLog(String.format("User %s added a new supplier with the ID:%s", user[0].toString(), id));
						 
						 JOptionPane.showMessageDialog(
							null, "Successfully register the new supplier with the ID of " + id + ".", 
							Utility.BUSINESS_TITLE, 
							JOptionPane.INFORMATION_MESSAGE);
						 
						 clearFields();
					 }
				 }
			}
		});
		
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { gallery.buttonHovered(btnCancel);}
			@Override
			public void mouseExited(MouseEvent e) { gallery.buttonNormalized(btnCancel);}
			@Override
			public void mouseClicked(MouseEvent e) {dispose();}
		});
		
		txtContactNumber.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent evt) {	
				String phoneNumber = txtContactNumber.getText();
				constraintPhoneNumber(phoneNumber, evt);
			}
		});
		
		setLocationRelativeTo(null);
		setVisible(true);
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
	
	private boolean checkFields() {
		ArrayList<String> errorMessages = new ArrayList<>();
		
		String name = txtName.getText();
		String contactNum = txtContactNumber.getText();
		String address = txtAddress.getText();
		
		if(name.isBlank() || contactNum.isBlank() || address.isBlank()) {
			errorMessages.add(" - Please fill out the missing fields!");
		}
		
		if (errorMessages.size() > 0) { 
			gallery.showMessage(errorMessages.toArray(new String[0]));
			return false;
		}
		return true;
	}
	
	private void clearFields() {
		txtSupplierID.setText(Long.toString(utility.generateSupplierID(database.fetchLastID("supplier", "supplier_id"))));
		txtName.setText("");
		txtContactNumber.setText("");
		txtContactNumber.setEditable(true);
		txtAddress.setText("");
	}

}
