package com.makeit.monitor.dto;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class ReportDTO {
    public ReportDTO(Long id) {
    	this.id = id;
	}
	@Id 
    public Long id;
	@Index 
	public String description;
	public double lat;
	public double lon;
	@Index
	public Date timestamp;
	@Index
	public int quadrant;
    @Index
	public String category;

    public ReportDTO() {
	
    }
}
