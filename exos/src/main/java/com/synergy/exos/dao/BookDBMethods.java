/**
 * Interface guiding the methods required for managing books' data in the database
 */
package com.synergy.exos.dao;

import java.util.List;

import com.synergy.exos.entity.Author;
import com.synergy.exos.entity.Books;
import com.synergy.exos.entity.Publisher;

/**
* @author G-Kesh
*
*/

public interface BookDBMethods {
	public void save(Books book, List<Author> authors, Publisher pb);

	public List<Books> fetch(int fetchMethod, String identifier);
	

	public void update(Books book, List<Author> authors, Publisher pb);

	public void terminate(Books book);
}