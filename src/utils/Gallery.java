package utils;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.AttributeSet.ColorAttribute;

public class Gallery {
	
	/**
	 *  System theme color reference:
	 *  https://coolors.co/0080ff-edede9-051923-d84a31-ffffff
	 */
	
	// Color Palette
	public static Color BLUE = new Color(0, 128, 255);
	public static Color DARK_BLUE = new Color(0, 112, 224); // 2 down the palette
	public static Color GRAY = new Color(237, 237, 233);
	public static Color LIGHT_GRAY = new Color(218, 218, 210); // 2 down the palette
	public static Color BLACK = new Color(5, 25, 35);
	public static Color RED = new Color(216, 74, 49);
	public static Color DARK_RED = new Color(188, 59, 36); // 2 down the palette
	public static Color WHITE = new Color(255, 255, 255);
	
	// System Colors
	public static Color PRIMARY_BUTTON_BACKGROUND = Gallery.BLUE;
	public static Color PRIMARY_BUTTON_BACKGROUND_HOVER = Gallery.DARK_BLUE;
	public static Color PRIMARY_BUTTON_FOREGROUND = Gallery.WHITE;
	public static Color PRIMARY_BUTTON_FOREGROUND_HOVER = Gallery.WHITE;

	public static Color DANGER_BUTTON_BACKGROUND = Gallery.WHITE;
	public static Color DANGER_BUTTON_BACKGROUND_HOVER = Gallery.RED;
	public static Color DANGER_BUTTON_FOREGROUND = Gallery.RED;
	public static Color DANGER_BUTTON_FOREGROUND_HOVER = Gallery.WHITE;

}
