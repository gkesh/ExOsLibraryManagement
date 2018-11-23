/**
 * Interface guiding the methods required for managing users' data in the database
 */
package com.synergy.exos.dao;

import java.util.List;

import com.synergy.exos.entity.Users;

/**
 * @author G-Kesh
 * 
 */
public interface UserDBMethods {
	
	public void save(Users usr);

	public List<Users> fetch(int fetchMethod, String identifier);


	public void update(Users usr);

	public void terminate(Users usr);
}
