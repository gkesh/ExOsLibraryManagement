package com.synergy.exos.uxinterface.filters;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

public class RoundedBorderFilter implements Border {

    private int radius;
    private Color color = Color.WHITE;


    public RoundedBorderFilter(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }
    
    public RoundedBorderFilter(int radius) {
    	this.radius = radius;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    	g.setColor(color);
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}