package com.face.model;

public class ResponseData {
	
	private int total;
	private MeanFaceAttributes mean;
	private ResultData resultData[];
	
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public ResultData[] getResultData() {
		return resultData;
	}
	
	public void setResultData(ResultData[] resultData) {
		this.resultData = resultData;
	}
	
	public MeanFaceAttributes getMean() {
		return mean;
	}
	
	public void setMean(MeanFaceAttributes mean) {
		this.mean = mean;
	}
	
	

}
