/**
 * 
 */
package com.synergy.exos.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author G-Kesh
 *
 */
@Embeddable
public class UserBooksId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4831492434476764597L;

	@Column(name = "id")
	private int userId;
	@Column(name = "isbn")
	private String bookId;

	/**
	 * @param userId
	 * @param bookId
	 */

	public UserBooksId() {

	}

	public UserBooksId(int userId, String bookId) {
		this.userId = userId;
		this.bookId = bookId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || getClass() != obj.getClass())
			return false;

		UserBooksId that = (UserBooksId) obj;
		return Objects.equals(userId, that.userId) && Objects.equals(bookId, that.bookId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, bookId);
	}
}
