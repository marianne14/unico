package com.unico.webservice.messaging;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;

public class UnicoMessageReceiver {

	@Resource(lookup = "queue/params")
	private Queue paramQueue;

	@Resource(lookup = "java:jboss/DefaultJMSConnectionFactory")
	ConnectionFactory connectionFactory ;

	public int receiveMessage() throws JMSException {
		JMSContext context = null;
		try {
			context = connectionFactory.createContext();
			context.start();
			Integer paramStr = context.createConsumer(paramQueue).receiveBody(Integer.class, 1000);
			if(paramStr != null) {
				return paramStr;
			}
			throw new JMSException("No param in the Queue.");
		} finally {
			if (context != null) {
				context.close();
			}
		}
	}
}
