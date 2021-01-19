package com.face.response;

public class FaceApiErrorResponse extends ErrorStatus {
		private FaceApiError faceApiError;

		public FaceApiErrorResponse(String error, FaceApiError faceApiError) {
			super(error);
			this.faceApiError = faceApiError;
		}

		public FaceApiError getFaceApiError() {
			return faceApiError;
		}

		public void setFaceApiError(FaceApiError faceApiError) {
			this.faceApiError = faceApiError;
		}
	
		
}
