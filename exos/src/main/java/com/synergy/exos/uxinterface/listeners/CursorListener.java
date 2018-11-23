/**
 * 
 */
package com.synergy.exos.uxinterface.listeners;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author G-Kesh
 *
 */
public class CursorListener extends MouseAdapter{
	private Component comp;
	
	public CursorListener(Component comp) {
		// TODO Auto-generated constructor stub
		this.comp = comp;
	}
	
	@Override
	public void mouseEntered(MouseEvent evt) {
		comp.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	@Override
	public void mouseExited(MouseEvent evt) {
		comp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
}
