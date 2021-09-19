package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class RoundedLabel extends JLabel {

    private int radius;
    private Color backgroundColor;
    
    public RoundedLabel(String message, int radius, Color backgroundColor) {
    	super(message);
    	this.radius = radius;
    	this.backgroundColor = backgroundColor;
    	setOpaque(true);
    }
    
    @Override
    public void print(Graphics g) {
        Color orig = getBackground();
        setBackground(Color.RED);

        // wrap in try/finally so that we always restore the state
        try {
            super.print(g);
        } finally {
            setBackground(orig);
        }
    }
}
