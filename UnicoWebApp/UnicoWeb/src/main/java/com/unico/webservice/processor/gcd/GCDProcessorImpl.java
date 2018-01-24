package com.unico.webservice.processor.gcd;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;

import com.unico.webservice.dao.GCDsDAO;
import com.unico.webservice.messaging.UnicoMessageReceiver;
import com.unico.webservice.model.GCD;

@Stateless
@Named("gcdProcessor")
public class GCDProcessorImpl implements GCDProcessor {

	@Inject
	private UnicoMessageReceiver messageReceiver;

	@Inject
	private GCDsDAO gcdsDAO;
	
	@Override
	public int getGCD() throws JMSException {

		int integer1 = messageReceiver.receiveMessage();
		int integer2 = messageReceiver.receiveMessage();

		BigInteger b1 = BigInteger.valueOf(integer1);
		BigInteger b2 = BigInteger.valueOf(integer2);
		BigInteger gcd = b1.gcd(b2);
		gcdsDAO.addGcd(gcd.intValue());
		return gcd.intValue();
	}

	@Override
	public List<Integer> getGCDList() {
		List<Integer> list = new ArrayList<Integer>();

		Collection<GCD> gcdsList = gcdsDAO.getGCDs();

		for(GCD gcd : gcdsList) {
			list.add(gcd.getGcd());
		}
		
		return list;

	}

	@Override
	public long getGCDSum() {
		return gcdsDAO.getGCDSum();
	}

}
