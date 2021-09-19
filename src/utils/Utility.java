package utils;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.text.StyledEditorKit.FontSizeAction;

public class Utility {

	public Font font;
	private final String fontName = "OpenSans-SemiBold";
	
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
	
	public ImageIcon getImage(String name) {
		ImageIcon image = new ImageIcon("assets/images/" + name);
		Image img = image.getImage();
		Image scaledIcon = img.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		ImageIcon finalImage = new ImageIcon(scaledIcon);
		return finalImage;
	}
}

