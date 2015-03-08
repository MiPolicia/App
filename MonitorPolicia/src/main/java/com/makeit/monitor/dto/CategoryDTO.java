package com.makeit.monitor.dto;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class CategoryDTO {	 

	public @Id String name;
	
	public CategoryDTO() {
	}
	 public CategoryDTO(String name) {
		this.name = name;
	}

}
