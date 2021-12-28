package pos;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import utils.Gallery;
import utils.RoundedPanel;
import utils.Utility;

/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
@SuppressWarnings("serial")
public class ProductDisplay extends RoundedPanel {
	
	private int x, y;
	private int defaultWidth = 150;
	private int defaultHeight = 100;
	private int marginWidth = 8;
	private int marginHeight = 10;
	
	private int index;
	private int maxColumn;
	private int maxRow;
	private int maxPerPage;
	
	private boolean isSelected = false;
	
	private JLabel lblName, lblImage, lblPrice, lblUOM;
	
	private Object[] product;
	private POS pos;

	/**
	 * 
	 * @param parentPanel The size of the current parent's panel
	 * @param index The order of when the product will be shown and what page
	 * @param product The information of the product being handled by the class
	 * 		  <br> The order will be: [0]product_id, [1]category, [2]name, [3]image, [4]uom and [5]selling_price
	 * @param gallery Instance must be passed in order to style the child components
	 * 
	 * @see utils.Database#getProductsByKeyword(String)
	 */
	public ProductDisplay(Dimension parentPanel, int index, Object[] product, Gallery gallery, POS pos) {
		super(15, Gallery.GRAY, Gallery.WHITE);
		this.product = product;
		this.pos = pos;

		maxColumn = (parentPanel.width / (defaultWidth + marginWidth));
		maxRow = (parentPanel.height / (defaultHeight + marginHeight));
		maxPerPage = maxColumn * maxRow;
		
		/*
		 * The process calculates the absolute coordinates of 
		 * the panel relative to its parentPanel
		 */
		this.index = index % maxPerPage;
		int columnIndex = this.index % maxColumn;
		int rowIndex = this.index / maxColumn;
		
		x = (defaultWidth + marginWidth) * columnIndex;
		y = (defaultHeight + marginHeight) * rowIndex;
		
		setBounds(x, y, defaultWidth, defaultHeight);
		setLayout(null);
		
		lblName = new JLabel(product[2].toString());
		lblName.setFont(gallery.getFont(13f));
		lblName.setOpaque(false);
		lblName.setBounds(8, 12, 134, 16);
		add(lblName);
		
		lblImage = new JLabel();
		lblImage.setIcon((ImageIcon) product[3]);
		lblImage.setBounds(14, 38, 48, 48);
		add(lblImage);
        
		lblPrice = new JLabel(Utility.getInstance().formatCurrency((double) product[7]));
		lblPrice.setVerticalAlignment(SwingConstants.BOTTOM);
		lblPrice.setFont(gallery.getFont(16f));
		lblPrice.setOpaque(false);
		lblPrice.setBounds(70, 32, 72, 32);
		add(lblPrice);
		
		lblUOM = new JLabel("per " + product[5].toString());
		lblUOM.setVerticalAlignment(SwingConstants.TOP);
		lblUOM.setFont(gallery.getFont(10f));
		lblUOM.setBounds(70, 60, 72, 26);
		add(lblUOM);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				setBorderColor(Color.WHITE);
				if (isSelected) {
					setBackgroundColor(Gallery.BLUE);
				} else {
					setBackgroundColor(Gallery.LIGHT_BLUE);
				}
				
				labelHovered(new JLabel[] {lblName, lblPrice, lblUOM});
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setBorderColor(Color.WHITE);
				labelNormalized(new JLabel[] {lblName, lblPrice, lblUOM});
				
				if (isSelected) {
					setBackgroundColor(Gallery.BLUE);
				} else {
					setBackgroundColor(Gallery.GRAY);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				setBackgroundColor(Gallery.DARK_BLUE);
				labelHovered(new JLabel[] {lblName, lblPrice, lblUOM});
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				isSelected = !isSelected;
				
				if (!isSelected) 
					pos.setSelectedIndex(-1);
				
				panelClicked();
			}
		});
	}
	
	public void unselect() {
		this.isSelected = false;
		
		panelClicked();
	}
	
	public void panelClicked() {
		if (isSelected) {
			pos.setSelectedIndex(index);
			setBackgroundColor(Gallery.BLUE);
		} else {
			setBackgroundColor(Gallery.GRAY);
		}

		labelNormalized(new JLabel[] {lblName, lblPrice, lblUOM});
	}

	public void labelHovered(JLabel[] labels) {
		if (isSelected) {
			for (JLabel label : labels) {
				label.setForeground(Gallery.WHITE);
				label.setBackground(Gallery.BLUE);
			}
		} else {
			for (JLabel label : labels) {
				label.setForeground(Gallery.WHITE);
				label.setBackground(Gallery.LIGHT_BLUE);
			}
		}
	}
	
	public void labelNormalized(JLabel[] labels) {
		if (isSelected) {
			labelHovered(labels);
		} else {
			for (JLabel label : labels) {
				label.setForeground(Gallery.BLACK);
				label.setBackground(Gallery.GRAY);
			}
		}
	}
	
	public Object[] getProduct() {
		return product;
	}
	
	public int getMaxPerPage() {
		return maxPerPage;
	}
	
	public int getMaxRow() {
		return maxRow;
	}
	
	public int getMaxColumn() {
		return maxColumn;
	}
	
	public boolean getSelected() {
		return isSelected;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getName() {
		return product[2].toString();
	}
}
