/**
 * Scalr Dependency used From Maven Repository
 * For image manipulation
 */
package com.synergy.exos.uxinterface.filters;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.imgscalr.Scalr;



/**
 * StackOverflow Code Credit to
 * 
 * @author Andrew Thompson (Update)
 * @author MadProgrammer (Base)
 * @author G-Kesh (Modifications)
 * 
 */
public class RoundImageFilter {

	public static JLabel getRoundLabel(String fileLoc, int d) {
		try {
			BufferedImage master = Scalr.resize(ImageIO.read(new File(fileLoc)), d+30);
			
			int diameter = d;
			BufferedImage mask = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = mask.createGraphics();
			applyQualityRenderingHints(g2d);
			g2d.fillOval(0, 0, diameter - 1, diameter - 1);
			g2d.dispose();

			BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
			g2d = masked.createGraphics();
			applyQualityRenderingHints(g2d);
			int x = (diameter - master.getWidth()) / 2;
			int y = (diameter - master.getHeight()) / 2;
			g2d.drawImage(master, x, y, null);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
			g2d.drawImage(mask, 0, 0, null);
			g2d.dispose();
			return new JLabel(new ImageIcon(masked));
		} catch (IOException ex) {
			return null;
		}
	}
	
	public static BufferedImage getRoundImage(String fileLoc, int d) {
		try {
			BufferedImage master = Scalr.resize(ImageIO.read(new File(fileLoc)), d+30);
			
			int diameter = d;
			BufferedImage mask = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = mask.createGraphics();
			applyQualityRenderingHints(g2d);
			g2d.fillOval(0, 0, diameter - 1, diameter - 1);
			g2d.dispose();

			BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
			g2d = masked.createGraphics();
			applyQualityRenderingHints(g2d);
			int x = (diameter - master.getWidth()) / 2;
			int y = (diameter - master.getHeight()) / 2;
			g2d.drawImage(master, x, y, null);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
			g2d.drawImage(mask, 0, 0, null);
			g2d.dispose();
			return masked;
		} catch (IOException ex) {
			return null;
		}
	}

	public static void applyQualityRenderingHints(Graphics2D g2d) {

		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

	}
}
