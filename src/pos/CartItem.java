package pos;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import utils.Gallery;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CartItem extends JPanel {
	
	// [0]product_id, [1]name, [2]image, [3]uom, [4]selling_price and [5] quantity
	private Object[] product;
	private int quantity = 0;
	
	private int width = 250;
	private int height = 34;
	
	private int squareImageSize = 24;
	private int textWidth = 150;
	private int textIconGapMargin = 5;
	private int oneLineTextMargin = 4;
	private int twoLineTextMargin = 1;
	private int oneLineColumnLimit = 15;
	private int twoLineColumnLimit = 30;
	
	private Gallery gallery;
	
	private JLabel lblCartItem, lblCartAdd, lblCartQuantity, lblCartLess, lblCartRemove;
	
	
	public CartItem(int indexOrder, Object[] product, int quantity, Gallery gallery, POS pos) {
		this.product = product;
		this.gallery = gallery;
		this.quantity = quantity;
		
		setBounds(0, indexOrder * height, width, height);
		setBackground(Gallery.WHITE);
		setLayout(null);

		String productName = product[1].toString();
		int nameLength = productName.length();
		lblCartItem = new JLabel(gallery.resizeImage((ImageIcon) product[2], squareImageSize, squareImageSize));
		if (nameLength <= oneLineColumnLimit) {
			lblCartItem.setText("<html>" + productName + "</html>");
			lblCartItem.setFont(gallery.getFont(14f));
			lblCartItem.setBounds(0, oneLineTextMargin, textWidth, height - (oneLineTextMargin * 2));
		} else if (nameLength > twoLineColumnLimit) {
			lblCartItem.setText("<html>" + productName.substring(0, twoLineColumnLimit - 1) + "...</html>");
			lblCartItem.setFont(gallery.getFont(10f));
			lblCartItem.setBounds(0, twoLineTextMargin, textWidth, height - (twoLineTextMargin * 2));
		} else {
			lblCartItem.setText("<html>" + productName + "</html>");
			lblCartItem.setFont(gallery.getFont(10f));
			lblCartItem.setBounds(0, twoLineTextMargin, textWidth, height - (twoLineTextMargin * 2));
		}
		lblCartItem.setIconTextGap(textIconGapMargin);
		lblCartItem.setHorizontalAlignment(SwingConstants.LEADING);
		add(lblCartItem);
		
		lblCartAdd = new JLabel(gallery.getImage("add.png", squareImageSize, squareImageSize));
		lblCartAdd.setBounds(151, 5, 24, 24);
		add(lblCartAdd);
		
		lblCartQuantity = new JLabel(Integer.toString(this.quantity));
		lblCartQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		lblCartQuantity.setFont(gallery.getFont(14f));
		lblCartQuantity.setBounds(176, 5, 24 ,24);
		add(lblCartQuantity);
		
		lblCartLess = new JLabel(gallery.getImage("minus.png", squareImageSize, squareImageSize));
		lblCartLess.setBounds(201, 5, 24, 24);
		add(lblCartLess);
		
		lblCartRemove = new JLabel(gallery.getImage("remove.png", squareImageSize, squareImageSize));
		lblCartRemove.setBounds(226, 5, 24, 24);
		add(lblCartRemove);
		
		

		lblCartAdd.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				lblCartAdd.setIcon(gallery.getImage("add-hovered.png", 24, 24));
			}
			@Override public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				lblCartAdd.setIcon(gallery.getImage("add.png", 24, 24));
			}
			@Override public void mouseClicked(MouseEvent e) {
				System.out.println("Increment product quantity on product: " + product[1].toString());
				System.out.println("Product quantity is now on: " + quantity);
				System.out.println(lblCartQuantity.getText());
			}
		});
		lblCartLess.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				lblCartLess.setIcon(gallery.getImage("minus-hovered.png", 24, 24));
			}
			@Override public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				lblCartLess.setIcon(gallery.getImage("minus.png", 24, 24));
			}
			@Override public void mouseClicked(MouseEvent e) {
				System.out.println("Decrement product quantity on product: " + product[1].toString());
				System.out.println("Product quantity is now on: " + quantity);
			}
		});
		lblCartRemove.addMouseListener(new MouseAdapter() {
			@Override public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				lblCartRemove.setIcon(gallery.getImage("remove-hovered.png", 24, 24));
			}
			@Override public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				lblCartRemove.setIcon(gallery.getImage("remove.png", 24, 24));
			}
			@Override public void mouseClicked(MouseEvent e) {
				// TODO: Do this after coding the cart list
				System.out.println("Remove product: " + product[1].toString());
			}
		});
	}
}
