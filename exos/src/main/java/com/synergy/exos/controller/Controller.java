/**
 * @author G-Kesh
 */
package com.synergy.exos.controller;

import com.synergy.exos.hdbc.SessionManufacturer;
import com.synergy.exos.uxinterface.LoginPanel;

/**
 * @author G-Kesh
 *
 */
public class Controller {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SessionManufacturer();
		LoginPanel.loginPanel();
	}
}
