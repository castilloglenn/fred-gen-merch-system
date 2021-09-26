package pos;

import java.awt.Color;
import java.awt.Dimension;

import utils.Gallery;
import utils.RoundedPanel;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class ProductDisplay extends RoundedPanel {
	
	public int x, y;
	public int defaultWidth = 150;
	public int defaultHeight = 100;
	public int margin = 5;
	public int index;
	public Object[] product;
	
	JLabel lblName, lblImage, lblSoldAs;

	
	public ProductDisplay(Dimension parentPanel, int index, Object[] product, Gallery gallery) {
		super(15, Gallery.GRAY, Gallery.WHITE);
		
		x = (parentPanel.width / (defaultWidth + margin));
		y = (parentPanel.height / (defaultHeight + margin));
		
		this.index = index;
		this.product = product;
		
		setBounds(x, y, defaultWidth, defaultHeight);
		setLayout(null);
		
		lblName = new JLabel("Sample Name");
		lblName.setFont(gallery.getFont(13f));
		lblName.setOpaque(false);
		lblName.setBounds(8, 8, 134, 14);
		add(lblName);
		
		lblImage = new JLabel();
		lblImage.setIcon(new ImageIcon("D:\\Administrator\\Pictures\\Assets\\blue-white-and-red-abstract-painting.jpg"));
		lblImage.setBounds(8, 28, 64, 64);
		add(lblImage);
		
		lblSoldAs = new JLabel("Sold as:");
		lblSoldAs.setFont(gallery.getFont(13f));
		lblSoldAs.setOpaque(false);
		lblSoldAs.setBounds(78, 28, 54, 14);
		add(lblSoldAs);
		
		
		
		
		
		
		
		
		
		

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBorderColor(Color.WHITE);
				setBackgroundColor(Gallery.BLUE);
				lblName.setForeground(Gallery.WHITE);
				lblName.setBackground(Gallery.BLUE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setBorderColor(Color.WHITE);
				setBackgroundColor(Gallery.GRAY);
				lblName.setForeground(Gallery.BLACK);
				lblName.setBackground(Gallery.GRAY);
			}
		});
	}
	
	

}
