
package com.uni.gcd.service.rest;

import java.util.List;

/**
 * This is an Gcd rest service. It performs below operations.
 * <ol>
 * <li>push : add the numbers to the messgage queue and database.</li>
 * <li>list : retrieves all the numbers saved to the queue from database.</li>
 * </ol>
 * 
 * @author japarikh
 */
public interface IGcdRestService {

	/**
	 * This method add the numbers to the messgage queue and database
	 * 
	 * @param i1
	 *            integer number
	 * @param i2
	 *            integer number
	 * @return String the status of the operation
	 */
	public String push(int int1, int int2);

	/**
	 * Retrieves all the numbers saved to the queue from database
	 * 
	 * @return numbers as an Integer list
	 */
	public List<Integer> list();
}
