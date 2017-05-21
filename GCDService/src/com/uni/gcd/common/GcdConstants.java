
package com.uni.gcd.common;

/**
 * This class holds constants that are accessed by various components in the application.
 * 
 * @author japarikh
 */
public class GcdConstants {

	/**
	 * Success code when operation is completed successfully.
	 */
	public static final String JMS_MESSAGE_DELIVERY_SUCCESS = "SUCCESS";
	/**
	 * Failure code when operation is failed.
	 */
	public static final String JMS_MESSAGE_DELIVERY_FAILURE = "FAILED";
	/**
	 * Generic error message when exception occures during operation.
	 */
	public static final String INTERNAL_ERROR = "Internal error. Please try again latter.";
	/**
	 * Message queue configuration
	 */
	public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	/**
	 * JNDI Name of connection factory
	 */
	public final static String JMS_FACTORY = "gcdMessaageJNDI";
	/**
	 * JNDI Name of queue
	 */
	public final static String QUEUE_NAME = "MsgQ";
	/**
	 * Weblogic server name and port
	 */
	public final static String SERVER_URL = "t3://localhost:7001";
	/**
	 * Path of push operation of Gcd Resetful service
	 */
	public final static String GCD_REST_SERVICE_PATH_PUSH = "/GcdRestService";
	/**
	 * Path of list operation of Gcd Resetful service
	 */
	public final static String GCD_REST_SERVICE_PATH_LIST = "/list";
	/**
	 * Parameters of push operation of Gcd Resetful service
	 */
	public final static String GCD_REST_PUSH_PARAs = "/{firstNumber}/{secondNumber}";
	/**
	 * First parameter of push operation of Gcd Resetful service
	 */
	public final static String GCD_REST_PUSH_PARA_1 = "firstNumber";
	/**
	 * Second parameter of push operation of Gcd Resetful service
	 */
	public final static String GCD_REST_PUSH_PARA_2 = "secondNumber";
}
