package utils;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class Gallery {
	
	/**
	 *  System theme color reference:
	 *  https://coolors.co/45a3d9-edede9-051923-d84a31-ffffff
	 *  
	 *  Blue = (69, 163, 217)
	 *  Gray = (237, 237, 233)
	 *  Black = (5, 25, 35)
	 *  Red = (216, 74, 49)
	 *  White = (255, 255, 255)
	 */
	
	public static Color BLUE = new Color(69, 163, 217);
	public static Color GRAY = new Color(237, 237, 233);
	public static Color BLACK = new Color(5, 25, 35);
	public static Color RED = new Color(216, 74, 49);
	public static Color WHITE = new Color(255, 255, 255);
	
	public Font font;

	
	@SuppressWarnings("serial")
	public class RoundedPanel extends JPanel
    {
        private Color backgroundColor;
        private int cornerRadius = 30;

        public RoundedPanel(Color bgColor) {
            super();
            backgroundColor = bgColor;
        }

        public RoundedPanel(int radius, Color bgColor) {
            super();
            cornerRadius = radius;
            backgroundColor = bgColor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //Draws the rounded panel with borders.
            if (backgroundColor != null) {
                graphics.setColor(backgroundColor);
            } else {
                graphics.setColor(getBackground());
            }
            graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint background
            graphics.setColor(getForeground());
            graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint border
        }
    }
	
	
	public Gallery() {
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("OpenSans-Regular.ttf"));
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(font);
			setFontSize(12f);
		} catch (FontFormatException e) {
			// insert error JLabel here
			e.printStackTrace();
		} catch (IOException e) {
			// insert error JLabel here
			e.printStackTrace();
		}
	}
	
	public void setFontSize(float size) {
		font = font.deriveFont(size);
	}

}
