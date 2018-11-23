/**
 * Extra Database Methods Interface
 */
package com.synergy.exos.dao;

import java.util.List;

import com.synergy.exos.entity.Author;
import com.synergy.exos.entity.Publisher;

/**
 * @author G-Kesh
 *
 */
public interface ExtraDBMethods {
	public List<Author> fetchAuthor(int fetchMethod, String identifier);

	public List<Publisher> fetchPublisher(int fetchMethod, String identifier);
}
