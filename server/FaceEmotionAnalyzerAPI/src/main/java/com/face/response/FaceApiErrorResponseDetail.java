package com.face.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class FaceApiErrorResponseDetail {
	private String code;
	private String message;


	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	
}
