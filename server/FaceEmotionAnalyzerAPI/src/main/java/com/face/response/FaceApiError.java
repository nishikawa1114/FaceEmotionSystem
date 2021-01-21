package com.face.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class FaceApiError {
	private String code;
	private String message;


	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	
}
