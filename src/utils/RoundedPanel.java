package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class RoundedPanel extends JPanel
{
    public Color backgroundColor;
    public int defaultRadius = 30;

    
    public RoundedPanel(Color bgColor) {
        super();
        backgroundColor = bgColor;
        setOpaque(false);
    }

    public RoundedPanel(int radius, Color bgColor) {
        super();
        defaultRadius = radius;
        backgroundColor = bgColor;
        setOpaque(false);
    }
    
    
    public void setBackgroundColor(Color color) {
    	backgroundColor = color;
    	repaint();
    	revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(backgroundColor);
        graphics.fillRoundRect(0, 0, width-1, height-1, defaultRadius, defaultRadius);
    }
}
