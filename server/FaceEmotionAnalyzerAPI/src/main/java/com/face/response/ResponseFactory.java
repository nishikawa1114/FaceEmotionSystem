package com.face.response;

import org.springframework.stereotype.Component;

import com.face.model.Emotion;
import com.face.model.ErrorMessage;
import com.face.model.ErrorResponse;
import com.face.model.FaceApiErrorResponse;
import com.face.model.FaceAttributes;
import com.face.model.MeanFaceAttributes;
import com.face.model.ResponseData;
import com.face.model.ResultData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ResponseFactory {	
	
	public static ErrorResponse createErrorResponse(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return errorResponse;
	}
	
	public static FaceApiErrorResponse createFaceApiErrorResponse(Exception ex) throws JsonMappingException, JsonProcessingException {
       
		ObjectMapper mapper = new ObjectMapper();
		FaceApiErrorResponse err = mapper.readValue(ex.getMessage(), FaceApiErrorResponse.class);
		
		err.setMessage(ErrorMessage.FACE_API_RESPONSE_ERROR);
		return err;
	}
	
	public static FaceApiErrorResponse createFaceApiServerError(Exception ex) throws JsonMappingException, JsonProcessingException {
	       
		ObjectMapper mapper = new ObjectMapper();
		FaceApiErrorResponse err = mapper.readValue(ex.getMessage(), FaceApiErrorResponse.class);
		
		err.setMessage(ErrorMessage.FACE_API_SERVER_UNABLABLE_ERROR);
		return err;
	}
	
	public static ResponseData createSuccessResponse(ResultData[] data) {
		
		Emotion meanEmotion = calcMeanEmotion(data);
		FaceAttributes faceAttributes = new FaceAttributes();
		faceAttributes.setEmotion(meanEmotion);
		MeanFaceAttributes meanFaceAttributes = new MeanFaceAttributes();
		meanFaceAttributes.setFaceAttributes(faceAttributes);
		
		ResponseData responseData = new ResponseData();
		responseData.setTotal(data.length);
		responseData.setMean(meanFaceAttributes);
		responseData.setResultData(data);
		
		return responseData;
		
	}
	
	public static Emotion calcMeanEmotion(ResultData[] data) {
		
		Emotion meanEmotion = new Emotion();
		
		if (data.length == 1) {
			return data[0].getFaceAttributes().getEmotion();
		} else if (data.length == 0) {
			return meanEmotion;
		}

		// 各感情値の合計値の計算
		for (ResultData rd: data) {
			Emotion emo = rd.getFaceAttributes().getEmotion();
			meanEmotion.setAnger(meanEmotion.getAnger() + emo.getAnger());
			meanEmotion.setContempt(meanEmotion.getContempt() + emo.getContempt());
			meanEmotion.setDisgust(meanEmotion.getDisgust() + emo.getDisgust());
			meanEmotion.setFear(meanEmotion.getFear() + emo.getFear());
			meanEmotion.setHappiness(meanEmotion.getHappiness() + emo.getHappiness());
			meanEmotion.setSadness(meanEmotion.getSadness() + emo.getSadness());
			meanEmotion.setSurprise(meanEmotion.getSurprise() + emo.getSurprise()); 
			meanEmotion.setNeutral(meanEmotion.getNeutral() + emo.getNeutral()); 
		}

		// 各感情値の平均値の計算
		int total = data.length;
		meanEmotion.setAnger(floorDecimal(meanEmotion.getAnger() / total, 4));
		meanEmotion.setContempt(floorDecimal(meanEmotion.getContempt() / total, 4));
		meanEmotion.setDisgust(floorDecimal(meanEmotion.getDisgust() / total, 4));
		meanEmotion.setFear(floorDecimal(meanEmotion.getFear() / total, 4));
		meanEmotion.setHappiness(floorDecimal(meanEmotion.getHappiness() / total, 4));
		meanEmotion.setSadness(floorDecimal(meanEmotion.getSadness() / total, 4));
		meanEmotion.setSurprise(floorDecimal(meanEmotion.getSurprise() / total, 4));
		meanEmotion.setNeutral(floorDecimal(meanEmotion.getNeutral() / total, 4));
		
		return meanEmotion;
	}
	
	/**
	 * 引数で指定された小数点以下の桁を切り捨てる
	 * @param number: 切り捨て対象の実数
	 * @param digit: 切り捨てる桁数
	 * @return 指定桁数以下が切り捨てられた実数値
	 */
	public static double floorDecimal(double number, int digit) {
		int temp = (int) (number * Math.pow(10, digit-1));
		double result = (double)temp / Math.pow(10, digit-1);
		return result;
	}
}
