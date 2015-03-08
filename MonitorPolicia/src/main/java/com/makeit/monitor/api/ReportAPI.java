package com.makeit.monitor.api;

import java.io.IOException;



import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.makeit.monitor.bean.Response;
import com.makeit.monitor.dao.ReportDAO;
import org.apache.commons.codec.binary.Base64;
@Controller
@RequestMapping("/report")
public class ReportAPI {

	@Autowired
	public ReportDAO reportDAO;

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public @ResponseBody String submit(@RequestParam("description") String description,
			@RequestParam("lat") double lat, @RequestParam("lon") double lon,
			@RequestParam("category") String category,
			@RequestParam("image") String image) {
			Base64 base64 = new Base64();			
			reportDAO.saveReport(base64.decode(image), 
					description, 
					lat,
					lon,
					category,
					"image/jpeg");
			
		
		return "ok";
	}
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String report(@RequestParam("description") String description,
			@RequestParam("lat") double lat, @RequestParam("lon") double lon,
			@RequestParam("category") String category,
			@RequestParam("file") MultipartFile file) {
  
		try {
			
			reportDAO.saveReport(file.getInputStream(), 
					description, 
					lat,
					lon,
					category,
					file.getContentType());
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return "redirect:/upload.html";
	}
}
