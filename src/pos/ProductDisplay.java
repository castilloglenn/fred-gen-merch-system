package pos;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import utils.Gallery;
import utils.RoundedPanel;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
public class ProductDisplay extends RoundedPanel {
	
	public int x, y;
	public int defaultWidth = 150;
	public int defaultHeight = 100;
	public int margin = 5;
	
	public int index;
	public int maxColumn;
	public int maxRow;
	public int maxPerPage;
	
	public Object[] product;
	public boolean isSelected = false;
	
	private JLabel lblName, lblImage, lblPrice;
	private JLabel lblUOM;

	/**
	 * 
	 * @param parentPanel The size of the current parent's panel
	 * @param index The order of when the product will be shown and what page
	 * @param product The information of the product being handled by the class
	 * 		  <br> The order will be: [0]product_id, [1]name, [2]image, [3]uom and [4]selling_price
	 * @param gallery Instance must be passed in order to style the child components
	 * 
	 * @see utils.Database#getProductsByKeyword(String)
	 */
	public ProductDisplay(Dimension parentPanel, int index, Object[] product, Gallery gallery) {
		super(15, Gallery.GRAY, Gallery.WHITE);

		this.product = product;
		maxColumn = (parentPanel.width / (defaultWidth + margin));
		maxRow = (parentPanel.height / (defaultHeight + margin));
		maxPerPage = maxColumn + maxRow;
		
		/*
		 * The process calculates the absolute coordinates of 
		 * the panel relative to its parentPanel
		 */
		this.index = index % maxPerPage;
		int columnIndex = this.index % maxColumn;
		int rowIndex = this.index % maxRow;
		
		x = (defaultWidth + margin) * columnIndex;
		y = (defaultHeight + margin) * rowIndex;
		
		setBounds(x, y, defaultWidth, defaultHeight);
		setLayout(null);
		
		lblName = new JLabel(product[1].toString());
		lblName.setFont(gallery.getFont(13f));
		lblName.setOpaque(false);
		lblName.setBounds(8, 12, 134, 14);
		add(lblName);
		
		lblImage = new JLabel();
		lblImage.setIcon((ImageIcon) product[2]);
		lblImage.setBounds(8, 34, 54, 54);
		add(lblImage);
		
		DecimalFormat formatter = new DecimalFormat("#,###.00");
        String currency = formatter.format((((double) product[4]) * 100.0) / 100.0 );
        
		lblPrice = new JLabel("P" + currency);
		lblPrice.setVerticalAlignment(SwingConstants.BOTTOM);
		lblPrice.setFont(gallery.getFont(16f));
		lblPrice.setOpaque(false);
		lblPrice.setBounds(70, 34, 72, 32);
		add(lblPrice);
		
		lblUOM = new JLabel("per " + product[3].toString());
		lblUOM.setVerticalAlignment(SwingConstants.TOP);
		lblUOM.setFont(gallery.getFont(10f));
		lblUOM.setBounds(70, 62, 72, 26);
		add(lblUOM);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				setBorderColor(Color.WHITE);
				setBackgroundColor(Gallery.BLUE);
				labelHovered(new JLabel[] {lblName, lblPrice, lblUOM});
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setBorderColor(Color.WHITE);
				setBackgroundColor(Gallery.GRAY);
				labelNormalized(new JLabel[] {lblName, lblPrice, lblUOM});
			}
		});
	}
	
	public void labelHovered(JLabel[] labels) {
		for (JLabel label : labels) {
			label.setForeground(Gallery.WHITE);
			label.setBackground(Gallery.BLUE);
		}
	}
	
	public void labelNormalized(JLabel[] labels) {
		for (JLabel label : labels) {
			label.setForeground(Gallery.BLACK);
			label.setBackground(Gallery.GRAY);
		}
	}

}
