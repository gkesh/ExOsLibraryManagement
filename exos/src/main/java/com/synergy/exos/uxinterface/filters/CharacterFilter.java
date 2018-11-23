/**
 * 
 */
package com.synergy.exos.uxinterface.filters;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Credit to StackOverflow
 * 
 * @author David Kroukamp Thanks mate!!
 *
 */
public class CharacterFilter extends DocumentFilter {
	
	boolean isProfile = false;
	
	public CharacterFilter() {
		//Default Constructor
	}
	public CharacterFilter(boolean isProfile) {
		this.isProfile = isProfile;
	}

	@Override
	public void replace(FilterBypass fb, int i, int i1, String string, AttributeSet as) throws BadLocationException {
		for (int n = string.length(); n > 0; n--) {// an inserted string may be more than a single character i.e a copy
													// and paste of 'aaa123d', also we iterate from the back as super.XX
													// implementation will put last inserted string first and so on
													// thus 'aa123d' would be 'daa', but because we iterate from the
													// back its 'aad' like we want
			char c = string.charAt(n - 1);// get a single character of the string
			boolean condition;
			if(isProfile) {
				condition = (Character.isAlphabetic(c) || c == ' ');
			}else {
				condition = (Character.isAlphabetic(c));
			}
			if(condition) {
				super.replace(fb, i, i1, String.valueOf(c), as);
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
