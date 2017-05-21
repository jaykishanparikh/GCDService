
package com.uni.gcd.common;

/**
 * This is a super class for Application service. It contains common implementation for each type of services.
 * 
 * @author japarikh
 */
public class ApplicationService {

	/**
	 * Calculates the GCD of two numbers
	 * 
	 * @param number1
	 *            Integer number
	 * @param number2
	 *            Integer number
	 * @return int the calculated GCD
	 */
	protected int calculateGCD(int number1, int number2) {

		if (number2 == 0) {
			return number1;
		}
		return calculateGCD(number2, number1 % number2);
	}
}
