package com.unico.webservice.messaging;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

public class UnicoMessageSender {

	@Resource(lookup = "queue/params")
	private Queue paramQueue;

	@Resource(lookup = "java:jboss/DefaultJMSConnectionFactory")
	ConnectionFactory connectionFactory ;

	public void send(int param1, int param2) {

		JMSContext context = null;
		try {
			context = connectionFactory.createContext();
			context.start();
			JMSProducer producer = context.createProducer();
			producer.send(paramQueue, param1);
			producer.send(paramQueue, param2);
		} finally {
			if(context != null) {
			    context.close();
			}
		}

	}

}
