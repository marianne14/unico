package com.unico.webservice.dao;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.unico.webservice.model.Param;

public class ParamsDAO {

	@PersistenceContext(unitName = "unico.webapp.mysql")
	EntityManager entityManager;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addParam(int param) {
		Param paramsEntity = new Param();
		paramsEntity.setParam(param);
		entityManager.persist(paramsEntity);
	}
	
	public List<Param> getParams() {
		 Query query = entityManager.createQuery("SELECT p FROM Param p order by id");
		 return query.getResultList();
	}
}
