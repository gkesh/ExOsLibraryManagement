/**
 * 
 */
package com.synergy.exos.uxinterface.listeners;

import java.awt.Color;

import javax.swing.JSeparator;

/**
 * @author G-Kesh
 *
 */
public class SeperatorListener {
	public static void seperatorState(JSeparator jsep, boolean bool) {
		if (bool) {
			jsep.setBackground(new Color(144, 238, 144));
			jsep.setForeground(new Color(144, 238, 144));
		} else {
			jsep.setBackground(new Color(255, 145, 164));
			jsep.setForeground(new Color(255, 145, 164));
		}
	}
}
