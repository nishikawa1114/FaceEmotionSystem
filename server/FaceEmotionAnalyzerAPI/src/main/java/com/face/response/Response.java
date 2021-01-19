package com.face.response;

import com.face.model.ResultData;

public class Response {	
	
	public static ErrorResponse createErrorResponse(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse(new ErrorStatus(ex.getMessage()));
        return errorResponse;
	}
}
