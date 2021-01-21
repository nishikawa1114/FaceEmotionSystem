package com.face.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Response {	
	
	public static ErrorResponse createErrorResponse(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse(new ErrorStatus(ex.getMessage()));
        return errorResponse;
	}
	
	public static FaceApiErrorResponse createFaceApiErrorResponse(Exception ex) throws JsonMappingException, JsonProcessingException {
       
		ObjectMapper mapper = new ObjectMapper();
		FaceApiErrorResponse err = mapper.readValue(ex.getMessage(), FaceApiErrorResponse.class);
		err.setMessage("Face API response is error.");
		return err;
	}
}
