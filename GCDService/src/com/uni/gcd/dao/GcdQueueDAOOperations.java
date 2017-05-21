
package com.uni.gcd.dao;

import java.util.List;

import javax.jms.JMSException;

/**
 * This class performs following operations related to JMS queue.
 * <ol>
 * <li>pushRecords - writes list of messages into the jms queue.</li>
 * <li>pullRecords - returns all messages ever stored into the db</li>
 * <li>pushGcd - writes gcd into the db list.</li>
 * <li>pullGcdRecords - returns all calculated gcd that are stored in the db list.</li>
 * </ol>
 * 
 * @author japarikh
 */
public class GcdQueueDAOOperations {

	/**
	 * Singleton instance
	 */
	private static GcdQueueDAOOperations instance = null;

	/**
	 * private constructor
	 */
	private GcdQueueDAOOperations() {

	}

	/**
	 * Method to return the unique singleton instance.
	 * 
	 * @return
	 */
	public static GcdQueueDAOOperations getUniqueInstance() {

		if (instance == null) {
			synchronized (GcdQueueDAOOperations.class) {
				if (instance == null) {
					instance = new GcdQueueDAOOperations();
				}
			}
		}
		return instance;
	}

	/**
	 * GcdQueueDataStructure instance
	 */
	private GcdQueueDataStructure daoObject = GcdQueueDataStructure.getInstance();

	/**
	 * This method writes list of messages into the jms queue.
	 * 
	 * @param messageList
	 * @throws JMSException
	 */
	public void pushRecords(List<Integer> messageList) {

		synchronized (this) {
			for (Integer msg : messageList) {
				// Store message into db
				daoObject.getMsgList().add(msg);
			}
		}
	}

	/**
	 * This method returns all messages ever stored into the db. In other worlds, it returns messages that are ever
	 * placed in the queue.
	 * 
	 * @return
	 * @throws JMSException
	 */
	public List<Integer> pullRecords() {

		synchronized (this) {
			return daoObject.getMsgList();
		}
	}

	/**
	 * This method writes gcd into the db list.
	 * 
	 * @param gcd
	 *            Integer
	 */
	public void pushGcd(Integer gcd) {

		synchronized (this) {
			// Store gcd into db list
			daoObject.getGcdList().add(gcd);
		}
	}

	/**
	 * This method returns all calculated gcd that are stored in the db list.
	 * 
	 * @return List<Integer>
	 */
	public List<Integer> pullGcdRecords() {

		synchronized (this) {
			return daoObject.getGcdList();
		}
	}
}
