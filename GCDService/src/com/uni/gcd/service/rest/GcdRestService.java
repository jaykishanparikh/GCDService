
package com.uni.gcd.service.rest;

import static com.uni.gcd.common.GcdConstants.GCD_REST_PUSH_PARA_1;
import static com.uni.gcd.common.GcdConstants.GCD_REST_PUSH_PARA_2;
import static com.uni.gcd.common.GcdConstants.GCD_REST_PUSH_PARAs;
import static com.uni.gcd.common.GcdConstants.GCD_REST_SERVICE_PATH_LIST;
import static com.uni.gcd.common.GcdConstants.GCD_REST_SERVICE_PATH_PUSH;
import static com.uni.gcd.common.GcdConstants.JMS_MESSAGE_DELIVERY_FAILURE;
import static com.uni.gcd.common.GcdConstants.JMS_MESSAGE_DELIVERY_SUCCESS;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.uni.gcd.common.ApplicationService;
import com.uni.gcd.dao.GcdQueueDAOOperations;
import com.uni.gcd.mq.operations.JMSQueueOperator;

/**
 * This is an implementation class of RESTful service of gcd functionality. It performs following operations.
 * <ol>
 * <li>push : add the numbers to the message queue and database.</li>
 * <li>list : retrieves all the numbers saved to the queue from database.</li>
 * </ol>
 * 
 * @author japarikh
 */
@Path(GCD_REST_SERVICE_PATH_PUSH)
public class GcdRestService extends ApplicationService implements IGcdRestService {

	/**
	 * This method adds two integer numbers to the message queue and database
	 * 
	 * @param i1
	 *            integer number
	 * @param i2
	 *            integer number
	 * @return String the status of the operation
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path(GCD_REST_PUSH_PARAs)
	@Override
	public String push(@javax.ws.rs.PathParam(GCD_REST_PUSH_PARA_1) int int1, @javax.ws.rs.PathParam(GCD_REST_PUSH_PARA_2) int int2) {

		String retValue = JMS_MESSAGE_DELIVERY_SUCCESS;
		try {
			JMSQueueOperator operator = new JMSQueueOperator();
			// 1. Write messages into queue
			List<Integer> messageList = new ArrayList<Integer>();
			messageList.add(int1);
			messageList.add(int2);
			operator.pushRecords(messageList);
			// 2. Add records into db list
			GcdQueueDAOOperations.getUniqueInstance().pushRecords(messageList);
			// 3. Calculate gcd and add in db gcd list
			GcdQueueDAOOperations.getUniqueInstance().pushGcd(calculateGCD(int1, int2));
		} catch (NamingException ne) {
			retValue = JMS_MESSAGE_DELIVERY_FAILURE;
		} catch (JMSException je) {
			retValue = JMS_MESSAGE_DELIVERY_FAILURE;
		}
		return retValue;
	}

	/**
	 * This method returns all the numbers saved to the queue from database
	 * 
	 * @return numbers as a list
	 */
	@GET
	@Path(GCD_REST_SERVICE_PATH_LIST)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public List<Integer> list() {

		return GcdQueueDAOOperations.getUniqueInstance().pullRecords();
	}
}
