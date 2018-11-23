/**
 * 
 */
package com.synergy.exos.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 * @author G-Kesh
 *
 */

@Entity(name = "UserBooks")
@Table(name = "tbl_user_books")
public class UserBooks implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2502318539494492303L;
	@EmbeddedId
	private UserBooksId ub;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	private Users user;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("bookId")
	private Books book;

	@Column(name = "issued_date")
	private Timestamp issuedDate;

	public UserBooks() {

	}

	public UserBooks(Users user, Books book, Timestamp issuedDate) {
		this.user = user;
		this.book = book;
		this.issuedDate = issuedDate;
		this.ub = new UserBooksId(user.getId(), book.getIsbn());
	}

	/**
	 * @return the ub
	 */
	public UserBooksId getUb() {
		return ub;
	}

	/**
	 * @param ub
	 *            the ub to set
	 */
	public void setUb(UserBooksId ub) {
		this.ub = ub;
	}

	/**
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * @return the book
	 */
	public Books getBook() {
		return book;
	}

	/**
	 * @param book
	 *            the book to set
	 */
	public void setBook(Books book) {
		this.book = book;
	}

	/**
	 * @return the issuedDate
	 */
	public Timestamp getIssuedDate() {
		return issuedDate;
	}

	/**
	 * @param issuedDate
	 *            the issuedDate to set
	 */
	public void setIssuedDate(Timestamp issuedDate) {
		this.issuedDate = issuedDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || getClass() != obj.getClass())
			return false;

		UserBooks that = (UserBooks) obj;
		return Objects.equals(user, that.user) && Objects.equals(book, that.book);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, book);
	}
}
