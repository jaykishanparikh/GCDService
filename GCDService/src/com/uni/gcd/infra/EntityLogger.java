
package com.uni.gcd.infra;

import java.util.logging.Logger;

/**
 * This logger is created to support logging wherein separate log files can be generated for the same logger category
 * for separate entities.
 * 
 * @author japarikh
 */
public class EntityLogger {

	private static final String THIS_COMPONENT_NAME = EntityLogger.class.getName();
	private String tracingRefNumber = null;
	/**
	 * Singleton instance
	 */
	private static EntityLogger instance = null;

	/**
	 * private constructor
	 */
	private EntityLogger() {

	}

	/**
	 * Method to return the unique singleton instance.
	 * 
	 * @return
	 */
	public static EntityLogger getUniqueInstance() {

		if (instance == null) {
			synchronized (EntityLogger.class) {
				if (instance == null) {
					instance = new EntityLogger();
				}
			}
		}
		return instance;
	}

	/**
	 * Public interface of the logger class which returns the multi-entity logger
	 * 
	 * @param category
	 * @return
	 */
	public Logger getLogger(String category) {

		Logger logger = Logger.getLogger("01.gc");
		return logger;
	}

	/**
	 * This method formats message with the provided parameters. It first creates a pattern which considers all the
	 * inputs passed to it. Thereafter, it uses the java.text.MessageFormatter to format the message with all parameters
	 * included in the message before returning the message to be logged.
	 * 
	 * @param message
	 *            String
	 * @params various objects
	 * @return message String
	 */
	public String formatMessage(String message, Object... params) {

		StringBuffer messageBuffer = new StringBuffer("[< ").append(generateUniqueReferenceNumber()).append(" >] ").append("\t").append(message);
		try {
			return String.format(messageBuffer.toString(), params);
		} catch (Throwable e) {
			message = messageBuffer.toString();
			instance.getLogger(THIS_COMPONENT_NAME).warning("Error occurred while formatting message. Unformatted message being returned is : " + message);
			return message;
		}
	}

	/**
	 * Generates an unique reference number for error tracking purpose
	 */
	private String generateUniqueReferenceNumber() {

		if (tracingRefNumber == null) {
			StringBuffer buf = new StringBuffer(Thread.currentThread().getId() + String.valueOf(System.nanoTime()));
			for (int i = buf.length() - 4; i > 0; i -= 4) {
				buf.insert(i, '-');
			}
			tracingRefNumber = buf.toString();
		}
		return tracingRefNumber;
	}
}