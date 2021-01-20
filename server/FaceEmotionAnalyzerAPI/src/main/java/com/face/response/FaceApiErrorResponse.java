package com.face.response;

public class FaceApiErrorResponse{
//		private FaceApiError details;
//		private ErrorStatus errorStatus;
//		
//		public FaceApiErrorResponse(ErrorStatus errorStatus, FaceApiError details) {
//			this.details = details;
//			this.errorStatus = errorStatus;
//		}
//
//		public FaceApiError getDetails() {
//			return details;
//		}
//
//		public void setDetails(FaceApiError details) {
//			this.details = details;
//		}
//
//		public ErrorStatus getErrorStatus() {
//			return errorStatus;
//		}
//
//		public void setErrorStatus(ErrorStatus errorStatus) {
//			this.errorStatus = errorStatus;
//		}
		
		private FaceApiError error;

		public FaceApiErrorResponse(FaceApiError error) {
			super();
			this.error = error;
		}

		public FaceApiError getError() {
			return error;
		}

		public void setError(FaceApiError error) {
			this.error = error;
		}
	
		
}
