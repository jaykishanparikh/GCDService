
package com.uni.gcd.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * This javabean acts as a entity object for gcd. It holds two list
 * <ol>
 * <li>msgList : List that holds all messages that are ever added into the queue.</li>
 * <li>gcdList : List of ever calculated gcd of queue messages.</li>
 * </ol>
 * 
 * @author japarikh
 */
public class GcdQueueDataStructure {

	/** The unique instance. */
	private static GcdQueueDataStructure uniqueInstance;

	/**
	 * Private Constructor of the class
	 */
	private GcdQueueDataStructure() {

	}

	/**
	 * This method returns unique instance of GcdQueueDataStructure
	 * 
	 * @return GcdQueueDataStructure
	 */
	public static GcdQueueDataStructure getInstance() {

		if (uniqueInstance == null) {
			synchronized (GcdQueueDataStructure.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new GcdQueueDataStructure();
				}
			}
		}
		return uniqueInstance;
	}

	private List<Integer> msgList = new ArrayList<Integer>();
	private List<Integer> gcdList = new ArrayList<Integer>();

	public List<Integer> getMsgList() {

		return msgList;
	}

	public void setMsgList(List<Integer> msgList) {

		this.msgList = msgList;
	}

	public List<Integer> getGcdList() {

		return gcdList;
	}

	public void setGcdList(List<Integer> gcdList) {

		this.gcdList = gcdList;
	}
}
