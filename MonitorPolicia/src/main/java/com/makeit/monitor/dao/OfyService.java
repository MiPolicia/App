package com.makeit.monitor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

@Repository
public class OfyService {

	@Autowired 
	private ObjectifyFactory objectifyFactory;
 
	/**
	 * @return our extension to Objectify
	 */
	public Objectify ofy() {
		return objectifyFactory.begin();
	}

}
