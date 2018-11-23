package com.synergy.exos.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity
@Table(name = "table_user")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Users implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String first_name, last_name, username, password, email_add, phone_no, type;
	@Column(name = "date_joined")
	private Timestamp added_date;
	@Column(name = "d_o_b")
	private Date dob;
	private boolean isAdmin;
	// @transient will specify any data not to be stored in the database
	// Setting Id as the primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	/*
	 * @ManyToMany(fetch = FetchType.EAGER)
	 * 
	 * @Cache(usage = CacheConcurrencyStrategy.READ_WRITE) private List<Books>
	 * books;
	 */

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<UserBooks> book = new HashSet<>();

	// Constructor
	public Users() {

	}
	// Getters And Setters

	public void addBook(Books book) {
		UserBooks userBook = new UserBooks(this, book, new Timestamp(System.currentTimeMillis()));
		this.book.add(userBook);
		book.getUsers().add(userBook);
	}

	/**
	 * @return the book
	 */
	public Set<UserBooks> getBook() {
		return book;
	}

	/**
	 * @param book
	 *            the book to set
	 */
	public void setBook(Set<UserBooks> book) {
		this.book = book;
	}

	public void removeTag(Books book) {
		for (Iterator<UserBooks> iterator = this.book.iterator(); iterator.hasNext();) {
			UserBooks userBook = iterator.next();
			if (userBook.getUser().equals(this) && userBook.getBook().equals(book)) {
				iterator.remove();
				userBook.getBook().getUsers().remove(userBook);
				userBook.setUser(null);
				userBook.setBook(null);
			}
		}
	}

	public Set<Books> getWithdrawls() {
		Set<Books> withdrawls = new HashSet<>();
		this.book.forEach((uB) -> {
			withdrawls.add(uB.getBook());
		});
		return withdrawls;
	}

	/**
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}

	/**
	 * @return the books
	 */
	/*
	 * public List<Books> getBooks() { return books; }
	 * 
	 *//**
		 * @param books
		 *            the books to set
		 *//*
			 * public void setBooks(List<Books> books) { this.books = books; }
			 * 
			 * public void setBook(Books book) { this.books.add(book); }
			 */

	/**
	 * @param first_name
	 *            the first_name to set
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	/**
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}

	/**
	 * @param last_name
	 *            the last_name to set
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email_add
	 */
	public String getEmail_add() {
		return email_add;
	}

	/**
	 * @param email_add
	 *            the email_add to set
	 */
	public void setEmail_add(String email_add) {
		this.email_add = email_add;
	}

	/**
	 * @return the phone_no
	 */
	public String getPhone_no() {
		return phone_no;
	}

	/**
	 * @param phone_no
	 *            the phone_no to set
	 */
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the added_date
	 */
	public Timestamp getAdded_date() {
		return added_date;
	}

	/**
	 * @param added_date
	 *            the added_date to set
	 */
	public void setAdded_date(Timestamp added_date) {
		this.added_date = added_date;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob
	 *            the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the isAdmin
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin
	 *            the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Users [first_name=" + first_name + ", last_name=" + last_name + ", username=" + username + ", password="
				+ password + ", email_add=" + email_add + ", phone_no=" + phone_no + ", type=" + type + ", added_date="
				+ added_date + ", dob=" + dob + ", isAdmin=" + isAdmin + ", id=" + id + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Users user = (Users) o;
		return Objects.equals(email_add, user.email_add);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email_add);
	}
}
