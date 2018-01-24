package com.unico.webservice.processor.param;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import com.unico.webservice.dao.ParamsDAO;
import com.unico.webservice.messaging.UnicoMessageSender;
import com.unico.webservice.model.Param;

@Stateless
@Named("paramProcessor")
public class ParamProcessorImpl implements ParamProcessor {

	@Inject
	private UnicoMessageSender messageSender;

	@Inject 
	private ParamsDAO paramsDAO;
	
	@Override
	public String pushParams(int param1, int param2) {
		messageSender.send(param1, param2);
		paramsDAO.addParam(param1);
		paramsDAO.addParam(param2);

		return "SUCCESS";
	}

	@Override
	public List<Integer> listParams() {

		List<Integer> list = new ArrayList<Integer>();
		Collection<Param> paramsList = paramsDAO.getParams();

		for(Param param : paramsList) {
			list.add(param.getParam());
		}

		return list;
	}

}
