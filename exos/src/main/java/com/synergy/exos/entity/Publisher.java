/**
 * 
 */
package com.synergy.exos.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "tbl_publisher")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(value=true)
@SelectBeforeUpdate(value=true)
public class Publisher implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int pid;
	@Column(name = "publisher_name")
	private String name;
	@Column(name = "publisher_location")
	private String location;
	private String website, contact_no;

	// FetchType.EAGER will fetch the connected data even if it is not used but
	// FetchType.LAZY will only fetch the data if it is used
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	@OneToMany( cascade = CascadeType.ALL , mappedBy = "publisher", fetch = FetchType.EAGER)
	private List<Books> books;

	public Publisher() {

	}

	// Getters and setters

	/**
	 * @return the pid
	 */
	public int getPid() {
		return pid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website
	 *            the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @return the contact_no
	 */
	public String getContact_no() {
		return contact_no;
	}

	/**
	 * @param contact_no
	 *            the contact_no to set
	 */
	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
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
		return "Publisher [pid=" + pid + ", name=" + name + ", location="
				+ location + ", website=" + website + ", contact_no="
				+ contact_no + "]";
	}

}
