/**
 * 
 */
package com.synergy.exos.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "tbl_author")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
@DynamicUpdate(value=true)
@SelectBeforeUpdate(value=true)
public class Author implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String first_name, last_name;
	@ManyToMany(cascade = CascadeType.ALL ,mappedBy = "author",fetch = FetchType.EAGER)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	private List<Books> books;
	// Default Constructor
	public Author() {

	}

	// Getters And Setters
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}

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
	 * @return the books
	 */
	public List<Books> getBooks() {
		return books;
	}

	/**
	 * @param books
	 *            the books to set
	 */
	public void setBooks(List<Books> books) {
		this.books = books;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Author [id=" + id + ", first_name=" + first_name
				+ ", last_name=" + last_name + "]";
	}

}
