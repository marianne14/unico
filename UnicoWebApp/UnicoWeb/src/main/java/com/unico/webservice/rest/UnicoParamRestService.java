package com.unico.webservice.rest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.unico.webservice.processor.param.ParamProcessor;

@Path("/service")
public class UnicoParamRestService implements ParamRestService {

	@Inject
	@Named("paramProcessor")
	private ParamProcessor paramProcessor;

	@GET
	@Path("/push/{param1}/{param2}")
	@Produces("text/plain")
	public String push(@PathParam("param1") int param1, @PathParam("param2") int param2) {
		return paramProcessor.pushParams(param1, param2);
	}

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Integer> listParams() {
		return paramProcessor.listParams();
	}
}
