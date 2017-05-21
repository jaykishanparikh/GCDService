
package com.uni.gcd.service.soap;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jws.WebService;
import javax.naming.NamingException;

import com.uni.gcd.common.ApplicationService;
import com.uni.gcd.dao.GcdQueueDAOOperations;
import com.uni.gcd.infra.EntityLogger;
import com.uni.gcd.mq.operations.JMSQueueOperator;

/**
 * This is a service implementation class of GcdSoapService. It performs below operations.
 * <ol>
 * <li>gcd : calculates the gcd (greatest common divisor) of the two integers at the head of the queue.</li>
 * <li>gcdList : returns a list of all the computed greatest common divisors from a database.</li>
 * <li>gcdSum : returns the sum of all computed greatest common divisors from a database.</li>
 * </ol>
 * 
 * @author japarikh
 */
@WebService(endpointInterface = "com.uni.gcd.service.soap.IGcdSoapService")
public class GcdSoapService extends ApplicationService implements IGcdSoapService {

	/**
	 * This attribute represents the component name.
	 */
	private static final String THIS_COMPONENT_NAME = GcdSoapService.class.getName();
	/**
	 * This creates a instance of logger for the component name as class name.
	 */
	private Logger logger = EntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);

	/**
	 * This method returns the greatest common divisor* of the two integers at the head of the queue. These two elements
	 * will subsequently be discarded from the queue and the head replaced by the next two in line.
	 * 
	 * @return gcd int
	 */
	@Override
	public int gcd() {

		int gcd = 0;
		try {
			JMSQueueOperator operator = new JMSQueueOperator();
			List<Integer> msgList = operator.pullRecordsFromQueueHead(2);
			if (msgList != null && msgList.size() == 2) {
				gcd = calculateGCD(msgList.get(0), msgList.get(1));
				// Add gcd into db list
				GcdQueueDAOOperations.getUniqueInstance().pushGcd(gcd);
			}
		} catch (NamingException e) {
			logger.log(Level.SEVERE, "GcdSoapService.gcd() : NamingException while finding gcd of the tow integers at the head of the queue.", e);
		} catch (JMSException e) {
			logger.log(Level.SEVERE, "GcdSoapService.gcd() : JMSException while finding gcd of the tow integers at the head of the queue.", e);
		}
		return gcd;
	}

	/**
	 * This method returns a list of all the computed greatest common divisors from a database.
	 * 
	 * @return gcdListList<Integer
	 */
	@Override
	public List<Integer> gcdList() {

		return GcdQueueDAOOperations.getUniqueInstance().pullGcdRecords();
	}

	/**
	 * This method returns the sum of all computed greatest common divisors from a database.
	 * 
	 * @return totalGcd int
	 */
	@Override
	public int gcdSum() {

		int totalGcd = 0;
		List<Integer> gcdList = GcdQueueDAOOperations.getUniqueInstance().pullGcdRecords();
		for (Integer gcd : gcdList) {
			totalGcd = totalGcd + gcd;
		}
		return totalGcd;
	}
}
