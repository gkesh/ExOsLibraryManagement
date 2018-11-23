/**
 * 
 */
package com.synergy.exos.uxinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.synergy.exos.uxinterface.listeners.CursorListener;
import com.synergy.exos.uxinterface.listeners.DragListener;

/**
 * @author G-Kesh
 *
 */
public class NotificationDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel messageContents, panelTitle, panelBody;
	private Dimension rootSize = new Dimension(500, 200);

	public void createNotification(String message) {
		messageContents = new JPanel();
		messageContents.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(messageContents);
		messageContents.setLayout(null);
		setUndecorated(true);
		DragListener dragListener = new DragListener(this);
		this.addMouseListener(dragListener);
		this.addMouseMotionListener(dragListener);
		this.setVisible(true);
		this.setLayout(null);
		this.setSize(rootSize);
		this.setLocation(900, 400);
		this.setLocationRelativeTo(null);
		messageContents.setSize(rootSize);

		panelTitle = new JPanel();
		panelTitle.setLayout(null);
		panelTitle.setBounds(0, 0, 500, 50);
		panelTitle.setBackground(SystemColor.inactiveCaptionBorder);
		panelTitle.setForeground(new Color(0, 102, 153));
		messageContents.add(panelTitle);

		JLabel lblX = new JLabel("X");
		lblX.setForeground(new Color(0, 102, 153));
		lblX.setBounds(450, 12, 25, 25);
		lblX.setHorizontalAlignment(SwingConstants.RIGHT);
		lblX.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 25));
		panelTitle.add(lblX);
		lblX.addMouseListener(new CursorListener(lblX) {
			@Override
			public void mouseClicked(MouseEvent evt) {
				dispose();
			}
		});

		JLabel lblTitle = new JLabel("Alert");
		lblTitle.setForeground(new Color(0, 102, 153));
		lblTitle.setBounds(20, 12, 250, 25);
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
		panelTitle.add(lblTitle);

		panelBody = new JPanel();
		panelBody.setLayout(null);
		panelBody.setBounds(0, 0, 500, 200);
		panelBody.setBackground(new Color(0, 102, 153));
		panelBody.setForeground(SystemColor.inactiveCaptionBorder);
		messageContents.add(panelBody);

		JLabel lblMessage = new JLabel(message);
		lblMessage.setForeground(SystemColor.inactiveCaptionBorder);
		lblMessage.setBounds(20, 50, 460, 150);
		lblMessage.setHorizontalAlignment(SwingConstants.LEFT);
		lblMessage.setFont(new Font("Tahoma", Font.PLAIN, 30));
		panelBody.add(lblMessage);

	}

}
