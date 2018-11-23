/**
 * This class hold values that are to be used while extracting book data based on various parameters
 */
package com.synergy.exos.hdbc.extrafetch;

/**
 * @author G-Kesh
 *
 */
public class BooksFetch {
	/**
	 * To be used for fetching the data of a book based on its isbn number
	 */
	public final static int FETCH_BY_ISBN = 2001;
	/**
	 * To be used to fetch the book data using the title of the book
	 * It will fetch the book if the title is incomplete
	 * But if the title is misspelled or the words are in the wrong order
	 * the data will not be fetched in that  case
	 */
	public final static int FETCH_BY_TITLE = 2002;
	/**
	 * Here the books will be fetched based on the author
	 * The author class will first be extracted
	 * And from the extracted author class the books list will be taken and returned as the result
	 */
	public final static int FETCH_BY_AUTHOR = 2003;
	/**
	 * Here the relation between the book and the publisher will be used to extract the books based on publisher
	 * Rather than going to the publisher and then extracting the books like we did with the author
	 * Here we directly looked into the publisher column of the books as they have a one-to-one relation
	 */
	public final static int FETCH_BY_PUBLISHER = 2004;
	/**
	 * This is to fetch all the records of books from the database
	 * Here the identifier parameter should be null
	 */
	public final static int FETCH_ALL = 2005;
	
}
