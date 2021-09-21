package utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet.ColorAttribute;


public class Utility {

	public Font font;
	private final String fontName = "OpenSans-SemiBold";
	private SimpleDateFormat sdf;
	private Date date;
	
	private final Color PRIMARY_BUTTON_BACKGROUND = Gallery.BLUE;
	private final Color PRIMARY_BUTTON_BACKGROUND_HOVER = Gallery.DARK_BLUE;
	private final Color PRIMARY_BUTTON_FOREGROUND = Gallery.WHITE;
	private final Color PRIMARY_BUTTON_FOREGROUND_HOVER = Gallery.WHITE;

	private final Color DANGER_BUTTON_BACKGROUND = Gallery.WHITE;
	private final Color DANGER_BUTTON_BACKGROUND_HOVER = Gallery.RED;
	private final Color DANGER_BUTTON_FOREGROUND = Gallery.RED;
	private final Color DANGER_BUTTON_FOREGROUND_HOVER = Gallery.WHITE;
	
	public Utility() {
		setupCustomFont();
	}
	
	public void setupCustomFont() {
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream("assets/fonts/" + fontName + ".ttf"));
			font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(font);
			font = font.deriveFont(12f);
		} catch (FontFormatException e) {
			// insert error JLabel here
			e.printStackTrace();
		} catch (IOException e) {
			// insert error JLabel here
			e.printStackTrace();
		}
	}
	
	public Font getFont(float size) {
		font = font.deriveFont(size);
		return font;
	}
	
	public void styleLabelToButton(JLabel label, float size, int marginWidth, int marginHeight) {
		label.setFont(getFont(size));
		buttonNormalized(label);
		
		if (label.getName().equals("primary")) {
			label.setBorder(new EmptyBorder(marginHeight, marginWidth, marginHeight, marginWidth));
		} else if (label.getName().equals("danger")) {
			label.setBorder(new CompoundBorder(
				new LineBorder(DANGER_BUTTON_FOREGROUND), 
				new EmptyBorder(marginHeight, marginWidth, marginHeight, marginWidth))
			);
		}
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setOpaque(true);
	}
	
	public void styleLabelToButton(JLabel label, float size, String icon, int iconSize,  int marginWidth, int marginHeight) {
		label.setIcon(getImage(icon, iconSize));
		styleLabelToButton(label, size, marginWidth, marginHeight);
	}
	
	public void buttonNormalized(JLabel label) {
		if (label.getName().equals("primary")) {
			label.setBackground(PRIMARY_BUTTON_BACKGROUND);
			label.setForeground(PRIMARY_BUTTON_FOREGROUND);
		}
		
		else if (label.getName().equals("danger")) {
			label.setBackground(DANGER_BUTTON_BACKGROUND);
			label.setForeground(DANGER_BUTTON_FOREGROUND);
		}
	}
	
	public void buttonHovered(JLabel label) { 
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		if (label.getName().equals("primary")) {
			label.setBackground(PRIMARY_BUTTON_BACKGROUND_HOVER);
			label.setForeground(PRIMARY_BUTTON_FOREGROUND_HOVER);
		}
		
		else if (label.getName().equals("danger")) {
			label.setBackground(DANGER_BUTTON_BACKGROUND_HOVER);
			label.setForeground(DANGER_BUTTON_FOREGROUND_HOVER);
		}
	}
	
	public ImageIcon getImage(String name, int size) {
		ImageIcon image = new ImageIcon("assets/images/" + name);
		Image img = image.getImage();
		Image scaledIcon = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
		ImageIcon finalImage = new ImageIcon(scaledIcon);
		return finalImage;
	}
	
	public String getTime(boolean shorten) {
		if (shorten) {
			sdf = new SimpleDateFormat("MM/dd/yyy, hh:mm:ss aa");
		} else {
			sdf = new SimpleDateFormat("EE, MMMM dd, yyyy, hh:mm:ss aa");
		}
        date = Calendar.getInstance().getTime();
        return sdf.format(date);
	}
}

