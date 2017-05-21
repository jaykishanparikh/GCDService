
package com.uni.gcd.service.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * This is a soap webservice of Gcd. It performs below operations.
 * <ol>
 * <li>gcd : calculates the gcd (greatest common divisor) of the two integers at the head of the queue.</li>
 * <li>gcdList : returns a list of all the computed greatest common divisors from a database.</li>
 * <li>gcdSum : returns the sum of all computed greatest common divisors from a database.</li>
 * </ol>
 * 
 * @author japarikh
 */
@WebService
public interface IGcdSoapService {

	/**
	 * This method returns the greatest common divisor* of the two integers at the head of the queue. These two elements
	 * will subsequently be discarded from the queue and the head replaced by the next two in line.
	 * 
	 * @return
	 */
	@WebMethod
	public int gcd();

	/**
	 * This method returns a list of all the computed greatest common divisors from a database.
	 * 
	 * @return
	 */
	@WebMethod
	public List<Integer> gcdList();

	/**
	 * This method returns the sum of all computed greatest common divisors from a database.
	 * 
	 * @return
	 */
	@WebMethod
	public int gcdSum();
}
