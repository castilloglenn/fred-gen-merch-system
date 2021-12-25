package pos;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import utils.Gallery;
import utils.Logger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class CartItem extends JPanel {
	
	
	private int index;
	private int quantity = 0;
	
	private Object[] product;
	
	private int width = 250;
	
	// both height values must be the same
	private int height = 34;
	public static int staticHeight = 34;
	
	private int squareImageSize = 24;
	private int textWidth = 150;
	private int textIconGapMargin = 5;
	private int oneLineTextMargin = 4;
	private int twoLineTextMargin = 1;
	private int oneLineColumnLimit = 15;
	private int twoLineColumnLimit = 30;
	
	private Logger logger;
	private Gallery gallery;
	private POS pos;
	
	private JLabel lblCartItem, lblCartAdd, lblCartQuantity, lblCartLess, lblCartRemove;
	
	/**
	 * 
	 * @param indexOrder the order in which the cart item will appear relative to the maximum available items in the panel
	 * @param product the product details contained in an object list.  <br> [0]product_id, [1] category, [2]name, [3]image, [4]uom, [5]selling_price
	 * @param quantity number of registered products
	 * @param gallery used for styling components
	 * @param pos used to communicate to its parent source 
	 * 
	 */
	public CartItem(int indexOrder, Object[] product, int quantity, POS pos) {
		logger = Logger.getInstance();
		logger.addLog(String.format("User added product '%s' on the cartlist.", product[2].toString()));
		
		index = indexOrder;
		
		this.product = product;
		this.quantity = quantity;
		this.gallery = Gallery.getInstance();
		this.pos = pos;
		
		setBounds(0, index * height, width, height);
		setBackground(Gallery.WHITE);
		setLayout(null);

		String productName = product[2].toString();
		int nameLength = productName.length();
		lblCartItem = new JLabel(gallery.resizeImage((ImageIcon) product[3], squareImageSize, squareImageSize));
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
				adjustQuantity(1);
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
				adjustQuantity(-1);
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
				pos.removeToCart(index);
			}
		});
	}
	
	/**
	 * 
	 * @param adjustment amount to be changed from its quantity.
	 */
	public void adjustQuantity(int adjustment) {
		
		if (quantity + adjustment == 0) {
			pos.removeToCart(index);
		} else if (quantity + adjustment <= 999) {
			quantity += adjustment;
		}
		
		lblCartQuantity.setText(Integer.toString(quantity));
		pos.updatePaymentStatistics();
	}
	
	/**
	 * for proper ordering after a cart item is removed from the main panel
	 * 
	 * @param newIndex adjustment value, this will replace the current index value
	 */
	public void rearrangeOrder(int newIndex) {
		index = newIndex;
		setBounds(0, index * height, width, height);
	}
	
	public String getName() {
		return product[2].toString();
	}
	
	public Object[] getProduct() {
		return product;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	/**
	 * Size 7 object list that will get from the cart item.
	 * 
	 * @return object list with the following content order:
	 * <br> 0: Product ID
	 * <br> 1: Category
	 * <br> 2: Name
	 * <br> 3: Image (ImageIcon)
	 * <br> 4: Quantity
	 * <br> 5: Unit of measurement
	 * <br> 6: Selling price
	 * <br> 7: Total price
	 * 
	 * @see javax.swing.ImageIcon
	 */
	public Object[] getTransactionDetail() {
		return new Object[] {product[0], product[1], product[2], product[3], quantity, 
				product[5], product[7], quantity * (double) product[7]};
	}
	
	@Override 
	public String toString() {
		return String.format("\nID: %s\nName: %s\nImage Class: %s\nQuantity: %d %s\nPrice: %s\nTotal: %s",
				product[0].toString(), product[2].toString(), product[3].toString(), quantity, 
				product[5].toString(), product[7].toString(), quantity * (double) product[7]);
	}
}
