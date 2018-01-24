package com.unico.webservice.dao;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.unico.webservice.model.GCD;

public class GCDsDAO {

	@PersistenceContext(unitName = "unico.webapp.mysql")
	EntityManager entityManager;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addGcd(int gcd) {
		GCD gcdsEntity = new GCD();
		gcdsEntity.setGcd(gcd);
		entityManager.persist(gcdsEntity);
	}
	
	public List<GCD> getGCDs() {
		 Query query = entityManager.createQuery("SELECT g FROM GCD g order by id");
		 return query.getResultList();
	}
	
	public long getGCDSum() {
		 Query query = entityManager.createQuery("SELECT sum(gcd) FROM GCD");
		 Object result = query.getSingleResult();
		 if(result == null) {
			 return 0;
		 }
		 return (Long)result;
	}
}
