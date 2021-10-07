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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * 
 * @author Allen Glenn E. Castillo
 *
 */
public class Gallery {
	
	/**
	 *  System theme color reference:
	 *  https://coolors.co/0080ff-edede9-051923-d84a31-ffffff
	 */
	
	// Color Palette
	public static Color BLUE = new Color(0, 128, 255);
	public static Color LIGHT_BLUE = new Color(92, 173, 255); // 4 up the palette
	public static Color DARK_BLUE = new Color(0, 112, 224); // 2 down the palette
	public static Color GRAY = new Color(237, 237, 233);
	public static Color LIGHT_GRAY = new Color(218, 218, 210); // 2 down the palette
	public static Color DARK_GRAY = new Color(145, 145, 120); // 10 down the palette
	public static Color BLACK = new Color(5, 25, 35);
	public static Color RED = new Color(216, 74, 49);
	public static Color DARK_RED = new Color(188, 59, 36); // 2 down the palette
	public static Color WHITE = new Color(255, 255, 255);
	
	// External Colors
	public static Color BOOTSTRAP_SECONDARY = new Color(108, 117, 125);
	
	// System Colors
	public static Color PRIMARY_BUTTON_FOREGROUND = Gallery.WHITE;
	public static Color PRIMARY_BUTTON_BACKGROUND = Gallery.BLUE;
	public static Color PRIMARY_BUTTON_FOREGROUND_HOVER = Gallery.WHITE;
	public static Color PRIMARY_BUTTON_BACKGROUND_HOVER = Gallery.DARK_BLUE;

	public static Color SECONDARY_BUTTON_FOREGROUND = Gallery.WHITE;
	public static Color SECONDARY_BUTTON_BACKGROUND = BOOTSTRAP_SECONDARY;
	public static Color SECONDARY_BUTTON_FOREGROUND_HOVER = BOOTSTRAP_SECONDARY;
	public static Color SECONDARY_BUTTON_BACKGROUND_HOVER = Gallery.WHITE;

	public static Color DANGER_BUTTON_FOREGROUND = Gallery.WHITE;
	public static Color DANGER_BUTTON_BACKGROUND = Gallery.RED;
	public static Color DANGER_BUTTON_FOREGROUND_HOVER = Gallery.WHITE;
	public static Color DANGER_BUTTON_BACKGROUND_HOVER = Gallery.DARK_RED;
	
	private String fontName = "OpenSans-SemiBold";
	private SimpleDateFormat sdf;
	private Date date;
	
	public Font font;
	
	public Gallery() {
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
	
	public ImageIcon getImage(String name, int width, int height) {
		ImageIcon image = new ImageIcon("assets/images/" + name);
		Image img = image.getImage();
		Image scaledIcon = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon finalImage = new ImageIcon(scaledIcon);
		return finalImage;
	}
	
	public ImageIcon resizeImage(ImageIcon oldImage, int newWidth, int newHeight) {
		Image image = oldImage.getImage();
		Image newimg = image.getScaledInstance(newWidth, newHeight,  java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
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
	
	public void styleLabelToButton(JLabel label, float size, int marginWidth, int marginHeight) {
		label.setFont(getFont(size));
		buttonNormalized(label);
		
		if (label.getName() == null) {}
		else if (label.getName().equals("primary")) {
			label.setBorder(new EmptyBorder(marginHeight, marginWidth, marginHeight, marginWidth));
			
		} else if (label.getName().equals("secondary")) {
			label.setBorder(new CompoundBorder(
				new LineBorder(Gallery.SECONDARY_BUTTON_BACKGROUND), 
				new EmptyBorder(marginHeight, marginWidth, marginHeight, marginWidth)));
			
		} else if (label.getName().equals("danger")) {
			label.setBorder(new CompoundBorder(
				new LineBorder(Gallery.DANGER_BUTTON_FOREGROUND), 
				new EmptyBorder(marginHeight, marginWidth, marginHeight, marginWidth)));
		} 
		
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setOpaque(true);
	}
	
	public void styleLabelToButton(JLabel label, float size, String icon, int iconSize,  int marginWidth, int marginHeight) {
		label.setIcon(getImage(icon, iconSize, iconSize));
		styleLabelToButton(label, size, marginWidth, marginHeight);
	}
	
	public void buttonNormalized(JLabel label) {
		if (label.getName() == null) {}
		
		else if (label.getName().equals("primary")) {
			label.setBackground(Gallery.PRIMARY_BUTTON_BACKGROUND);
			label.setForeground(Gallery.PRIMARY_BUTTON_FOREGROUND);
		}

		else if (label.getName().equals("secondary")) {
			label.setBackground(Gallery.SECONDARY_BUTTON_BACKGROUND);
			label.setForeground(Gallery.SECONDARY_BUTTON_FOREGROUND);
		}
		
		else if (label.getName().equals("danger")) {
			label.setBackground(Gallery.DANGER_BUTTON_BACKGROUND);
			label.setForeground(Gallery.DANGER_BUTTON_FOREGROUND);
		}
	}
	
	public void buttonHovered(JLabel label) { 
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		if (label.getName() == null) {}
		
		else if (label.getName().equals("primary")) {
			label.setBackground(Gallery.PRIMARY_BUTTON_BACKGROUND_HOVER);
			label.setForeground(Gallery.PRIMARY_BUTTON_FOREGROUND_HOVER);
		}

		else if (label.getName().equals("secondary")) {
			label.setBackground(Gallery.SECONDARY_BUTTON_BACKGROUND_HOVER);
			label.setForeground(Gallery.SECONDARY_BUTTON_FOREGROUND_HOVER);
		}
		
		else if (label.getName().equals("danger")) {
			label.setBackground(Gallery.DANGER_BUTTON_BACKGROUND_HOVER);
			label.setForeground(Gallery.DANGER_BUTTON_FOREGROUND_HOVER);
		}
	}
	
	public void styleTextField(JTextField textField, String defaultText, float fontSize) {
		textField.setBorder(new EmptyBorder(0, 10, 0, 10));
		textField.setFont(getFont(fontSize));
		textFieldFocusLost(textField, defaultText);
	}
	
	public void textFieldFocusLost(JTextField textField, String defaultText) {
		if (textField.getText().equals("")) {
			textField.setText(defaultText);
			textField.setForeground(Gallery.LIGHT_GRAY);
		}
	}
	
	public void textFieldFocusGained(JTextField textField, String defaultText) {
		if (textField.getText().equals(defaultText)) {
			textField.setText("");
			textField.setForeground(Gallery.BLACK);
		}
	}

}
