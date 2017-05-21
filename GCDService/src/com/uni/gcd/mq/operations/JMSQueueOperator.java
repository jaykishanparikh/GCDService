
package com.uni.gcd.mq.operations;

import static com.uni.gcd.common.GcdConstants.JMS_FACTORY;
import static com.uni.gcd.common.GcdConstants.JNDI_FACTORY;
import static com.uni.gcd.common.GcdConstants.QUEUE_NAME;
import static com.uni.gcd.common.GcdConstants.SERVER_URL;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.uni.gcd.infra.EntityLogger;

/**
 * This class performs load and retrieve operations on queue.
 * 
 * @author japarikh
 */
public class JMSQueueOperator {

	/**
	 * This attribute represents the component name.
	 */
	private static final String THIS_COMPONENT_NAME = JMSQueueOperator.class.getName();
	/**
	 * This creates a instance of logger for the component name as class name.
	 */
	private Logger logger = EntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);
	private QueueConnectionFactory qconFactory;
	private QueueConnection qcon;
	private QueueSession qsession;
	private QueueSender qsender;
	private QueueReceiver qreceiver;
	private Queue queue;
	private TextMessage msg;
	private static boolean quit = false;

	public JMSQueueOperator() throws NamingException, JMSException {

		InitialContext ctx = getInitialContext(SERVER_URL);
		qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
		qcon = qconFactory.createQueueConnection();
		qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = (Queue) ctx.lookup(QUEUE_NAME);
		qsender = qsession.createSender(queue);
		qreceiver = qsession.createReceiver(queue);
		msg = qsession.createTextMessage();
		qcon.start();
	}

	/**
	 * This method writes list of messages into the jms queue.
	 * 
	 * @param messageList
	 * @throws JMSException
	 */
	public void pushRecords(List<Integer> messageList) throws JMSException {

		if (logger.isLoggable(Level.FINE)) {
			logger.log(Level.FINE, "JMSQueueOperator.pushRecords () Begins.");
		}
		synchronized (this) {
			quit = true;
			for (Integer msg : messageList) {
				write(msg.toString());
			}
		}
		if (logger.isLoggable(Level.FINE)) {
			logger.log(Level.FINE, "JMSQueueOperator.pushRecords () Ends.");
		}
	}

	/**
	 * This method reads number of message from queue head.
	 * 
	 * @param fetchSize
	 * @return
	 * @throws JMSException
	 */
	public List<Integer> pullRecordsFromQueueHead(Integer fetchSize) throws JMSException {
		if (logger.isLoggable(Level.FINE)) {
			logger.log(Level.FINE, "JMSQueueOperator.pullRecordsFromQueueHead () begins.");
		}
		List<Integer> messageList = new ArrayList<Integer>();
		synchronized (this) {
			quit = true;
			for (int i = 0; i < fetchSize; i++) {
				messageList.add(Integer.parseInt(read()));
			}
		}
		if (logger.isLoggable(Level.FINE)) {
			logger.log(Level.FINE, "JMSQueueOperator.pullRecordsFromQueueHead () ends.");
		}
		return messageList;
	}

	/**
	 * This method closes various resources required to operate jms queue.
	 * 
	 * @throws JMSException
	 */
	public void close() throws JMSException {
		if (logger.isLoggable(Level.FINE)) {
			logger.log(Level.FINE, "JMSQueueOperator.close () begins.");
		}
		qsender.close();
		qsession.close();
		qcon.close();
		if (logger.isLoggable(Level.FINE)) {
			logger.log(Level.FINE, "JMSQueueOperator.close () ends.");
		}
	}

	/**
	 * This methods puts message into jsm queue.
	 * 
	 * @param message
	 *            String
	 * @throws JMSException
	 */
	private void write(String message) throws JMSException {

		msg.setText(message);
		qsender.send(msg);
	}

	/**
	 * This method reads a message from the queue from the head.
	 * 
	 * @return messageText String
	 * @throws JMSException
	 */
	private String read() throws JMSException {

		TextMessage msg = (TextMessage) qreceiver.receive();
		return msg.getText();
	}

	/**
	 * Initializes context.
	 * 
	 * @param url
	 * @return
	 * @throws NamingException
	 */
	private InitialContext getInitialContext(String url) throws NamingException {

		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
		env.put(Context.PROVIDER_URL, url);
		return new InitialContext(env);
	}
}