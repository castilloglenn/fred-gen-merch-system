package utils;

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


public class Utility {

	public Font font;
	private final String fontName = "OpenSans-SemiBold";
	private SimpleDateFormat sdf;
	private Date date;
	
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
	
	public ImageIcon getImage(String name, int size) {
		ImageIcon image = new ImageIcon("assets/images/" + name);
		Image img = image.getImage();
		Image scaledIcon = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
		ImageIcon finalImage = new ImageIcon(scaledIcon);
		return finalImage;
	}
	
	public String getTime() {
		sdf = new SimpleDateFormat("EE, MMM dd, yyyy, hh:mm:ss aa");
        date = Calendar.getInstance().getTime();
        return sdf.format(date);
	}
}

