package com.unico.webservice.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface GcdWebService {

	@WebMethod
	public int gcd() throws Exception;
	
	@WebMethod
	public List<Integer> gcdList();
	
	@WebMethod
	public long gcdSum();	
}
