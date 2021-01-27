package com.face.response;

import com.face.model.ErrorResponse;
import com.face.model.FaceApiErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseFactory {	
	
	public static ErrorResponse createErrorResponse(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return errorResponse;
	}
	
	public static FaceApiErrorResponse createFaceApiErrorResponse(Exception ex) throws JsonMappingException, JsonProcessingException {
       
		ObjectMapper mapper = new ObjectMapper();
		FaceApiErrorResponse err = mapper.readValue(ex.getMessage(), FaceApiErrorResponse.class);
		err.setMessage("Face API response is error.");
		return err;
	}
}
