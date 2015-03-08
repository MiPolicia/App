package com.makeit.monitor.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.makeit.monitor.dao.CategoryDAO;
import com.makeit.monitor.dto.CategoryDTO;

@Controller
@RequestMapping("/admin")
public class AdminAPI {
	
	@Autowired
	public CategoryDAO categoryDAO;
	
	@RequestMapping(value = "/categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<CategoryDTO> getAllCategories() { 
		return categoryDAO.all();
	}
	
	@RequestMapping(value = "/categories", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<CategoryDTO> putCategory(@RequestParam String name) {
		return categoryDAO.save(new CategoryDTO(name));
	}
	
}
