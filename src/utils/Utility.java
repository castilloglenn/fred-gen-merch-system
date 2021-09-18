package utils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JPanel;
import javax.swing.text.StyledEditorKit.FontSizeAction;

public class Utility {

	public Font font;
	private final String fontName = "OpenSans-SemiBold";
	

	@SuppressWarnings("serial")
	public class RoundedPanel extends JPanel
    {
        private Color backgroundColor;
        private int cornerRadius = 30;

        public RoundedPanel(Color bgColor) {
            super();
            backgroundColor = bgColor;
            setOpaque(false);
        }

        public RoundedPanel(int radius, Color bgColor) {
            super();
            cornerRadius = radius;
            backgroundColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (backgroundColor != null) {
                graphics.setColor(backgroundColor);
            } else {
                graphics.setColor(getBackground());
            }
            graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
            graphics.setColor(getForeground());
            graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
        }
    }
	
	
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
}
