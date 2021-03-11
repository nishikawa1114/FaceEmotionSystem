package com.face.response;

import java.util.Arrays;

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
	
	static Emotion calcMeanEmotion(ResultData[] data) {
		
		Emotion meanEmotion = new Emotion();
		
		if (data.length == 1) {
			return data[0].getFaceAttributes().getEmotion();
		} else if (data.length == 0) {
			return meanEmotion;
		}

//		// 各感情値の合計値の計算
//		for (ResultData rd: data) {
//			Emotion emo = rd.getFaceAttributes().getEmotion();
//			meanEmotion.setAnger(meanEmotion.getAnger() + emo.getAnger());
//			meanEmotion.setContempt(meanEmotion.getContempt() + emo.getContempt());
//			meanEmotion.setDisgust(meanEmotion.getDisgust() + emo.getDisgust());
//			meanEmotion.setFear(meanEmotion.getFear() + emo.getFear());
//			meanEmotion.setHappiness(meanEmotion.getHappiness() + emo.getHappiness());
//			meanEmotion.setSadness(meanEmotion.getSadness() + emo.getSadness());
//			meanEmotion.setSurprise(meanEmotion.getSurprise() + emo.getSurprise()); 
//			meanEmotion.setNeutral(meanEmotion.getNeutral() + emo.getNeutral()); 
//		}
		
		double meanAnger = Arrays.stream(data).mapToDouble(rd -> rd.getFaceAttributes().getEmotion().getAnger()).average().orElse(0);
		double meanContempt = Arrays.stream(data).mapToDouble(rd -> rd.getFaceAttributes().getEmotion().getContempt()).average().orElse(0);
		double meanDisgust = Arrays.stream(data).mapToDouble(rd -> rd.getFaceAttributes().getEmotion().getDisgust()).average().orElse(0);
		double meanFear = Arrays.stream(data).mapToDouble(rd -> rd.getFaceAttributes().getEmotion().getFear()).average().orElse(0);
		double meanHappiness = Arrays.stream(data).mapToDouble(rd -> rd.getFaceAttributes().getEmotion().getHappiness()).average().orElse(0);
		double meanSadness = Arrays.stream(data).mapToDouble(rd -> rd.getFaceAttributes().getEmotion().getSadness()).average().orElse(0);
		double meanSurprise = Arrays.stream(data).mapToDouble(rd -> rd.getFaceAttributes().getEmotion().getSurprise()).average().orElse(0);
		double meanNeutral = Arrays.stream(data).mapToDouble(rd -> rd.getFaceAttributes().getEmotion().getNeutral()).average().orElse(0);

		// 各感情値の平均値の計算
		meanEmotion.setAnger(floorDecimalPlace4(meanAnger));
		meanEmotion.setContempt(floorDecimalPlace4(meanContempt));
		meanEmotion.setDisgust(floorDecimalPlace4(meanDisgust));
		meanEmotion.setFear(floorDecimalPlace4(meanFear));
		meanEmotion.setHappiness(floorDecimalPlace4(meanHappiness));
		meanEmotion.setSadness(floorDecimalPlace4(meanSadness));
		meanEmotion.setSurprise(floorDecimalPlace4(meanSurprise));
		meanEmotion.setNeutral(floorDecimalPlace4(meanNeutral));
		
		return meanEmotion;
	}
	
	/**
	 * 小数第４位以下の桁を切り捨てる
	 * @param number: 切り捨て対象の実数
	 * @return 指定桁数以下が切り捨てられた実数値
	 */
	static double floorDecimalPlace4(double number) {
		int num = (int) Math.pow(10, 3);
		int temp = (int) (number * num);
		double result = (double)temp / num;
		return result;
	}
}
