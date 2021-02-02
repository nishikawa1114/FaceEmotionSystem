package com.face.model;

import com.face.response.FaceApiErrorResponseDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class FaceApiErrorResponse{
		
		@JsonProperty("error")
		private String message;

		private FaceApiErrorResponseDetail details;

		@JsonProperty("details")
		public FaceApiErrorResponseDetail getDetails() {
			return details;
		}

		@JsonProperty("error")
		public void setDetails(FaceApiErrorResponseDetail error) {
			this.details = error;
		}
		
		public String getMessage() {
			return message;
		}
		
		public void setMessage(String error) {
			this.message = error;
		}
		
}
