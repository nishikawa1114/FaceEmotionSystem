package com.face.response;

public class ErrorResponse {

	private String error;
	
	public ErrorResponse(String error) {
		super();
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
}
