package com.makeit.monitor.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.makeit.monitor.dto.CategoryDTO;
@Repository
public class CategoryDAO {
	@Autowired
	OfyService ofyService;
	
	public List<CategoryDTO> all() {
		return ofyService.ofy().load().type(CategoryDTO.class).list();
	} 

	public List<CategoryDTO> save(CategoryDTO categoryDTO) {
		ofyService.ofy().save().entity(categoryDTO);
		return all();
	}
}
