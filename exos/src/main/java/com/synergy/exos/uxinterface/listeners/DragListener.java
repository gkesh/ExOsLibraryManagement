/**
 * @author G-kesh
 * code-credit to Erik Pragt - Stack Overflow
 */
package com.synergy.exos.uxinterface.listeners;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class DragListener extends MouseAdapter {

        private JFrame frame;
        private JDialog dialog;
        private Point mouseDownCompCoords = null;

        public DragListener(JFrame frame) {
            this.frame = frame;
        }
        
        public DragListener(JDialog dialog) {
        	this.dialog = dialog;
        }

        public void mouseReleased(MouseEvent e) {
            mouseDownCompCoords = null;
        }

        public void mousePressed(MouseEvent e) {
            mouseDownCompCoords = e.getPoint();
        }

        public void mouseDragged(MouseEvent e) {
            Point currCoords = e.getLocationOnScreen();
            if(frame != null) {
            	frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }else {
            	dialog.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        }
    }