package com.face.model;

public class FaceRectangle {
	private int top;
	private int left;
	private int width;
	private int height;
	
	public FaceRectangle(int top, int left, int width, int height) {
		super();
		this.top = top;
		this.left = left;
		this.width = width;
		this.height = height;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
