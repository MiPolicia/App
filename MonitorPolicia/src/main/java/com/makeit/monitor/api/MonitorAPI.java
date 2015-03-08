package com.makeit.monitor.api;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.makeit.monitor.bean.MonitorResume;
import com.makeit.monitor.dao.ReportDAO;
import com.makeit.monitor.dto.ImageDTO;
import com.makeit.monitor.dto.ReportDTO;

@Service
@RequestMapping("/monitor")
public class MonitorAPI {
	@Autowired
	public ReportDAO reportDAO;
	
	@RequestMapping(method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public @ResponseBody List<ReportDTO> monitor() { 
		return reportDAO.getAll();
	}
	
	@RequestMapping(value="/resume",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public @ResponseBody List<MonitorResume> resume() { 
		return reportDAO.resume();
	}
	@RequestMapping(value="/category/{category}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)	
	public @ResponseBody List<ReportDTO> category(@PathVariable("category")String category) { 
		return reportDAO.category(category);
	}
	
	@RequestMapping(value="/image/{id}",method=RequestMethod.GET)
	public ResponseEntity<InputStreamResource> get(@PathVariable("id") Long id){
		
		ImageDTO image=reportDAO.getImage(id);
	HttpHeaders headers= new HttpHeaders();
		headers.add("content-type", image.content);
		headers.add("cache-control", "max-age=31556926");
		ResponseEntity<InputStreamResource> response=  
				new ResponseEntity<InputStreamResource>(
						new InputStreamResource(
								new ByteArrayInputStream(image.image)
								),headers,HttpStatus.OK);		
		return response;
	}
}
