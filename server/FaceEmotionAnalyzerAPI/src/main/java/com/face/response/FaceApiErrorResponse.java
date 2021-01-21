package com.face.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class FaceApiErrorResponse{
		
		private String message;
		private FaceApiError details;

		@JsonProperty("details")
		public FaceApiError getDetails() {
			return details;
		}

		@JsonProperty("error")
		public void setDetails(FaceApiError error) {
			this.details = error;
		}
		
		@JsonProperty("error")
		public String getMessage() {
			return message;
		}
		
		public void setMessage(String error) {
			this.message = error;
		}
		
}
