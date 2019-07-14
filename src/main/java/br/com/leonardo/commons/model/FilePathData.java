package br.com.leonardo.commons.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FilePathData implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	public FilePathData(@JsonProperty("path") String path,
						@JsonProperty("name") String name) {
		this.path = path;
		this.name = name;
		
	}
	
	private String path;
	private String name;
	
}
