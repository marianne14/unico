package com.unico.webservice.soap;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.unico.webservice.processor.gcd.GCDProcessor;

@WebService(name = "UnicoSOAPWebService", serviceName = "UnicoSOAPWebService")
@HandlerChain(file="/handler/handlers.xml")
public class UnicoGcdWebService implements GcdWebService {

	@Inject
	@Named("gcdProcessor")
	private GCDProcessor gcdProcessor;
	
	@WebMethod
	public int gcd() throws Exception {
		return gcdProcessor.getGCD();    
	}

	@WebMethod
	public List<Integer> gcdList() {
		return gcdProcessor.getGCDList();
	}

	@WebMethod
	public long gcdSum() {
		return gcdProcessor.getGCDSum();
	}
}
