/**
 * Book Entity 
 * @author G-Kesh
 */
package com.synergy.exos.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

/**
 * @author G-Kesh
 * 
 */
@Entity
@Table(name = "tbl_books")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Books implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String isbn;
	private String title;
	private String genre, edition;
	private int published_year;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private List<Author> author;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "publisher_pid")
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Publisher publisher;
	/*
	 * @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
	 * 
	 * @Cache(usage = CacheConcurrencyStrategy.READ_ONLY) private List<Users> user;
	 */

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<UserBooks> users = new HashSet<>();
	private int copies_available;

	// Constructor
	public Books() {

	}

	// Getters and setters

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @return the users
	 */
	public Set<UserBooks> getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(Set<UserBooks> users) {
		this.users = users;
	}

	/**
	 * @return the user
	 */
	/*
	 * public List<Users> getUser() { return user; }
	 * 
	 *//**
		 * @param user
		 *            the user to set
		 *//*
			 * public void setUser(List<Users> user) { this.user = user; }
			 */

	/**
	 * @param isbn
	 *            the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @param genre
	 *            the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * @return the edition
	 */
	public String getEdition() {
		return edition;
	}

	/**
	 * @param edition
	 *            the edition to set
	 */
	public void setEdition(String edition) {
		this.edition = edition;
	}

	/**
	 * @return the published_year
	 */
	public int getPublished_year() {
		return published_year;
	}

	/**
	 * @param published_year
	 *            the published_year to set
	 */
	public void setPublished_year(int published_year) {
		this.published_year = published_year;
	}

	/**
	 * @return the publisher
	 */
	public Publisher getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher
	 *            the publisher to set
	 */
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return the author
	 */
	public List<Author> getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(List<Author> author) {
		this.author = author;
	}

	/**
	 * @return the copies_available
	 */
	public int getCopies_available() {
		return copies_available;
	}

	/**
	 * @param copies_available
	 *            the copies_available to set
	 */
	public void setCopies_available(int copies_available) {
		this.copies_available = copies_available;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Books [isbn=" + isbn + ", title=" + title + ", genre=" + genre + ", edition=" + edition
				+ ", copies_available=" + copies_available + ", published_year=" + published_year + ", publisher="
				+ publisher + ", authors=" + author + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Books book = (Books) o;
		return Objects.equals(title, book.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title);
	}
}
