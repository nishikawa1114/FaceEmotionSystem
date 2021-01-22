package com.face.model;

public class ResultData {
	private String faceId;
	private FaceRectangle faceRectangle;
	private FaceAttributes faceAttributes;

	public String getFaceId() {
		return faceId;
	}

	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	public FaceRectangle getFaceRectangle() {
		return faceRectangle;
	}

	public void setFaceRectangle(FaceRectangle faceRectangle) {
		this.faceRectangle = faceRectangle;
	}

	public FaceAttributes getFaceAttributes() {
		return faceAttributes;
	}

	public void setFaceAttributes(FaceAttributes faceAttributes) {
		this.faceAttributes = faceAttributes;
	}
	
	
}
