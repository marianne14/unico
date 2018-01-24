package com.unico.webservice.processor.gcd;

import java.util.List;

public interface GCDProcessor {

	public int getGCD() throws Exception;
	public List<Integer> getGCDList();
	public long getGCDSum();
	
}
