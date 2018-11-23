/**
 * 
 */
package com.synergy.exos.uxinterface.filters;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * @author G-Kesh
 *
 */
public class NumberFilter extends DocumentFilter {

	@Override
	public void replace(FilterBypass fb, int i, int i1, String string, AttributeSet as) throws BadLocationException {
		for (int n = string.length(); n > 0; n--) {//Simple change allows the above written character filter to turn into Number Filter
			char c = string.charAt(n - 1);// get a single digit from the string
			if (Character.isDigit(c)) {// if its a digit
				super.replace(fb, i, i1, String.valueOf(c), as);// allow update to take place for the given character
			}
		}
	}

	@Override
	public void remove(FilterBypass fb, int i, int i1) throws BadLocationException {
		super.remove(fb, i, i1);
	}

	@Override
	public void insertString(FilterBypass fb, int i, String string, AttributeSet as) throws BadLocationException {
		super.insertString(fb, i, string, as);

	}
}