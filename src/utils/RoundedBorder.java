package utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.border.Border;

public class RoundedBorder implements Border {

    private int radius;
    private Color backgroundColor;

    /**
     * To create a new JButton: <p>
     * 
     * JButton addBtn = new JButton("+"); <p>
     * addBtn.setBounds(x_pos, y_pos, 30, 25); <p>
     * addBtn.setBorder(new RoundedBorder(10)); //10 is the radius <p>
     * addBtn.setForeground(Color.BLUE); <p>
     */
    public RoundedBorder(int radius, Color bgColor) {
        this.radius = radius;
        backgroundColor = bgColor;
    }

    @Override
    public Insets getBorderInsets(Component c) {
    	return new Insets(radius / 2, radius, radius / 2, radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    	Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(backgroundColor);
//        g.fillRoundRect(x, y, width / 2, height / 2, radius, radius);
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}
