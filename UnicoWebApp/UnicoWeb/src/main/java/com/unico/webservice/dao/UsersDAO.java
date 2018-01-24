package com.unico.webservice.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.unico.webservice.model.User;

public class UsersDAO {

	@PersistenceContext(unitName = "unico.webapp.mysql")
	EntityManager entityManager;

	public User getUserInfo(String userName, String password) {
		
		Query query = entityManager.createQuery(
			"SELECT u FROM User u where user = '" + userName + "' and password = '"+ password + "'");
		Object result = null;
		try {
			result = query.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
		
		return (User) result;
		
	}
}
