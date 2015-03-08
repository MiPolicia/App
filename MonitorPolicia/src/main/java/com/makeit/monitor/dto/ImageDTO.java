package com.makeit.monitor.dto;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
@Entity
public class ImageDTO {
	@Id Long id;
	public @Index Key<ReportDTO> report;
	public byte[] image;
	@Index
	public String content;	
}
