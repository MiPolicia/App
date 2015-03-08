package com.makeit.monitor.dao;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.googlecode.objectify.Key;
import com.makeit.monitor.bean.MonitorResume;
import com.makeit.monitor.dto.CategoryDTO;
import com.makeit.monitor.dto.ImageDTO;
import com.makeit.monitor.dto.ReportDTO;
@Repository
public class ReportDAO {
	
	@Autowired
	OfyService ofyService;
	
	public void saveReport(InputStream in,
			String description,
			double lat,
			double lon,
			String category,
			String content){
		 try {
			saveReport(IOUtils.toByteArray(in),
					description,
					lat,
					lon,
					category
					, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveReport(byte[] bs,
			String description,
			double lat,
			double lon,
			String category,
			String content){
		ReportDTO dto = new ReportDTO();
		dto.description=description;
		dto.lat = lat;
		dto.lon = lon;
		dto.timestamp = new Date();
		dto.quadrant=0;		
		dto.category =  category;			
		Key<ReportDTO> key= ofyService.ofy().save().entity(dto).now();
		ImageDTO image = new ImageDTO();
		image.report = key;
		image.content=content;
		
		image.image = bs;		
		ofyService.ofy().save().entity(image);
	}
	
	public int cuadrante(double lat,double lon){
		try {
		    URL url = new URL("http://201.144.220.174/pid/cuadrantesService/index.php/cuadrante_ajax/json_datos_cuadrante");
		    HTTPRequest request = new HTTPRequest(url,HTTPMethod.POST);
		   request.setPayload(("latitude_post="+lat+"&longitude_post="+lon).getBytes());		   
		   HTTPResponse response = URLFetchServiceFactory.getURLFetchService().fetch(request);		  
		   JsonFactory factory = new JsonFactory();
		   JsonParser parser = factory.createParser(response.getContent());
		   JsonNode node=  parser.readValueAsTree();
		   return node.get(0).get("idcd").intValue();		   
		} catch (MalformedURLException e) {
		    return 0;
		} catch (IOException e) {
		    return 0;
		}
	}
	
	public List<ReportDTO> getAll() {
		return ofyService.ofy().load().type(ReportDTO.class).list();
	}

	public ImageDTO getImage(Long id) {
		return ofyService.ofy().load().type(ImageDTO.class).filter("report ==",new ReportDTO(id)).first().now();		
	}

	public List<MonitorResume> resume() {
		List<MonitorResume> monitor = new ArrayList<MonitorResume>();
		List<CategoryDTO> categories = ofyService.ofy().load().type(CategoryDTO.class).list();
		for (CategoryDTO category : categories) {
			
			int count = ofyService.ofy().load().type(ReportDTO.class).filter("category =", category.name).count();			
			monitor.add(new MonitorResume(category.name,count));
		}
		return monitor;
		
	}

	public List<ReportDTO> category(String category) {
		return ofyService.ofy().load().type(ReportDTO.class).filter("category =", category).list();
	}
}
