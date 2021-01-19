package com.face.response;

public class FaceApiError {
	private String code;
	private String message;
	
	public FaceApiError(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	
}
