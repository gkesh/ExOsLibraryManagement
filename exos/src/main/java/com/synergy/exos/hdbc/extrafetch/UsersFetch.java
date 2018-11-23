/**
 * 
 */
package com.synergy.exos.hdbc.extrafetch;

/**
 * @author G-Kesh
 *
 */
public class UsersFetch {
	/**
	 * To fetch using the users Id we use the fetch by id variable
	 */
	public final static int FETCH_BY_ID = 1001;
	/**
	 * To fetch data using the user name of the record you can use this final static variable
	 * This fetch technique will get the user the data even if the user name matches the given value exactly
	 * The search function will be implemented with a different function
	 */
	public final static int FETCH_BY_USERNAME = 1002;
	/**
	 * This will be used while fetching the user data using the full name of the user
	 * This will perform the same searching technique as the username variable 
	 * But separately for the first and last name
	 */
	public final static int FETCH_BY_FULL_NAME = 1003;
	/**
	 * This will fetch all the data of the users available in the database
	 *  Here the identifier parameter should be null
	 */
	public final static int FETCH_ALL = 1004;
	/**
	 * This will be used while fetching the user data using the email of the user
	 * This will perform the same searching technique as the other variables 
	 */
	public final static int FETCH_BY_EMAIL = 1005;
}
