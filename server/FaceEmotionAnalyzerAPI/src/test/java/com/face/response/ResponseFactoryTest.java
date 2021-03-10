package com.face.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.face.model.Emotion;
import com.face.model.ErrorResponse;
import com.face.model.FaceApiErrorResponse;
import com.face.model.FaceAttributes;
import com.face.model.FaceRectangle;
import com.face.model.MeanFaceAttributes;
import com.face.model.ResponseData;
import com.face.model.ResultData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ResponseFactoryTest {

	@Autowired
	ResponseFactory target;

	// ErrorResponse����������A����error�̒l��"test error"�ł��邱�Ƃ�����
	@Test
	void testCreateErrorResponse() {

		String expectedErrorMessage = "test error"; // ����API����Ԃ����G���[��

		Exception ex = new NotDetectedException(expectedErrorMessage);
		// ���s
		ErrorResponse response = ResponseFactory.createErrorResponse(ex);
		// ��r
		assertThat(response.getError()).isEqualTo(expectedErrorMessage);
	}

	// FaceApiErrorResponse����������Aerror�ɌŒ�̃G���[���Adetails��JSON������Ŏw�肳�ꂽ(���ۂɂ́AFaceAPI����ԋp���ꂽ)�G���[�̓��e���ݒ肳��Ă��邱�Ƃ����ҁB
	// FaceApiErrorResponse.error�͌Œ�̃G���[��"Face API response is error."
	// FaceApiErrorResponse.detail��code, message��SON������Ŏw�肳�ꂽ�G���[�̓��e���i�[
	@Test
	void testCreateFaceApiErrorResponse() throws JsonMappingException, JsonProcessingException {

		String expectedErrorMessage = "Face API response is error."; // ����API����Ԃ����G���[��
		String expetedDetailCode = "BadArgument"; // FaceAPI����̃��X�|���X
		String expectedDetailMessage = "Request body is invalid."; // FaceAPI����̃��X�|���X

		String FaceApiErrMessage = "{\"error\":{\"code\":\"" + expetedDetailCode + "\",\"message\":\""
				+ expectedDetailMessage + "\"}}"; // FaceAPI����ԋp�����G���[�̑z��
		Exception ex = new FaceApiException(FaceApiErrMessage);
		// ���s
		FaceApiErrorResponse response = ResponseFactory.createFaceApiErrorResponse(ex);
		// ��r
		assertThat(response.getMessage()).isEqualTo(expectedErrorMessage);
		assertThat(response.getDetails().getCode()).isEqualTo(expetedDetailCode);
		assertThat(response.getDetails().getMessage()).isEqualTo(expectedDetailMessage);
	}

	// ===== floorDecimal �̃e�X�g =====
	
	// ������4�ʐ؂�̂�
	@Test
	void testFloorDecimal1() {
		// ���Ғl
		double expect = 0.123;
		// ���s
		double result = ResponseFactory.floorDecimal(0.1234, 4);
		// ��r
		assertThat(result).isEqualTo(expect);
	}

	// ������S�ʐ؂�̂�(5�ȏ�̐����̏ꍇ)
	@Test
	void testFloorDecimal2() {
		// ���Ғl
		double expect = 0.123;
		// ���s
		double result = ResponseFactory.floorDecimal(0.1236, 4);
		// ��r
		assertThat(result).isEqualTo(expect);
	}

	// ���̐�����������1�ʂ܂ł̏ꍇ�A������4�ʂ��w�肵�Ă̐؂�̂�
	@Test
	void testFloorDecimal3() {
		// ���Ғl
		double expect = 0.1;
		// ���s
		double result = ResponseFactory.floorDecimal(0.1, 4);
		// ��r
		assertThat(result).isEqualTo(expect);
	}

	// ������3�ʈȉ��̐؂�̂�
	@Test
	void testFloorDecimal4() {
		// ���Ғl
		double expect = 0.12;
		// ���s
		double result = ResponseFactory.floorDecimal(0.1234, 3);
		// ��r
		assertThat(result).isEqualTo(expect);
	}

	// �����_�ȉ��̐؂�̂�
	@Test
	void testFloorDecimal5() {
		// ���Ғl
		double expect = 1;
		// ���s
		double result = ResponseFactory.floorDecimal(1.1234, 1);
		// ��r
		assertThat(result).isEqualTo(expect);
	}
	
	// ===== calcMeanEmotion �̃e�X�g =====
	
	// �����̕��͌��ʂ��܂ޔz��resultData��emotion�̕��ϒl���i�[���ꂽ
	// Emotion�I�u�W�F�N�g���ԋp�����
	@Test
	void testCalcMeanEmotion() {
		// ���Ғl
			// ���͌��ʂ��쐬
		Emotion emotion1 = new Emotion();
		emotion1.setAnger(0.1);
		emotion1.setContempt(0.1);
		emotion1.setDisgust(0.1);
		emotion1.setFear(0.1);
		emotion1.setHappiness(0.1);
		emotion1.setNeutral(0.1);
		emotion1.setSadness(0.1);
		emotion1.setSurprise(0.1);
		FaceAttributes faceAttributes1 = new FaceAttributes();
		faceAttributes1.setEmotion(emotion1);
		ResultData resultData1 = new ResultData();
		resultData1.setFaceAttributes(faceAttributes1);
		Emotion emotion2 = new Emotion();
		emotion2.setAnger(0.2);
		emotion2.setContempt(0.2);
		emotion2.setDisgust(0.2);
		emotion2.setFear(0.2);
		emotion2.setHappiness(0.2);
		emotion2.setNeutral(0.2);
		emotion2.setSadness(0.2);
		emotion2.setSurprise(0.2);
		FaceAttributes faceAttributes2 = new FaceAttributes();
		faceAttributes2.setEmotion(emotion2);
		ResultData resultData2 = new ResultData();
		resultData2.setFaceAttributes(faceAttributes2);
		ResultData[] resultDatas = {resultData1, resultData2};
			// emotion�̕��ϒl
		Emotion meanEmotion = new Emotion();
		meanEmotion.setAnger(0.15);
		meanEmotion.setContempt(0.15);
		meanEmotion.setDisgust(0.15);
		meanEmotion.setFear(0.15);
		meanEmotion.setHappiness(0.15);
		meanEmotion.setNeutral(0.15);
		meanEmotion.setSadness(0.15);
		meanEmotion.setSurprise(0.15);
		FaceAttributes meanFaceAttributes = new FaceAttributes();
		meanFaceAttributes.setEmotion(meanEmotion);
		MeanFaceAttributes mean = new MeanFaceAttributes();
		mean.setFaceAttributes(faceAttributes2);
		
		ResponseData expect = new ResponseData();
		expect.setTotal(2);
		expect.setMean(mean);
		expect.setResultData(resultDatas);
		
		// ���s
		Emotion result = ResponseFactory.calcMeanEmotion(resultDatas);
		// ��r
		assertThat(result.getAnger()).isEqualTo(meanEmotion.getAnger());
		assertThat(result.getContempt()).isEqualTo(meanEmotion.getContempt());
		assertThat(result.getDisgust()).isEqualTo(meanEmotion.getDisgust());
		assertThat(result.getFear()).isEqualTo(meanEmotion.getFear());
		assertThat(result.getHappiness()).isEqualTo(meanEmotion.getHappiness());
		assertThat(result.getNeutral()).isEqualTo(meanEmotion.getNeutral());
		assertThat(result.getSadness()).isEqualTo(meanEmotion.getSadness());
		assertThat(result.getSurprise()).isEqualTo(meanEmotion.getSurprise());

	}
	
	// ===== createSuccessResponse �̃e�X�g =====

	// �ԋp���X�|���X�����������
	@Test
	void testCreateSuccessResponse() {
		// ���Ғl�쐬
		Emotion emotion1 = new Emotion();
		emotion1.setAnger(0.1);
		emotion1.setContempt(0.1);
		emotion1.setDisgust(0.1);
		emotion1.setFear(0.1);
		emotion1.setHappiness(0.1);
		emotion1.setNeutral(0.1);
		emotion1.setSadness(0.1);
		emotion1.setSurprise(0.1);
		FaceAttributes faceAttributes1 = new FaceAttributes();
		faceAttributes1.setEmotion(emotion1);
		FaceRectangle faceRectangle1 = new FaceRectangle();
		faceRectangle1.setTop(10);
		faceRectangle1.setLeft(20);
		faceRectangle1.setWidth(150);
		faceRectangle1.setHeight(120);
		ResultData resultData1 = new ResultData();
		resultData1.setFaceId("01");
		resultData1.setFaceRectangle(faceRectangle1);
		resultData1.setFaceAttributes(faceAttributes1);
		MeanFaceAttributes mean = new MeanFaceAttributes();
		mean.setFaceAttributes(faceAttributes1);
		ResultData[] list = { resultData1 };

		ResponseData responseData = new ResponseData();
		responseData.setTotal(1);
		responseData.setResultData(list);
		responseData.setMean(mean);

		// ���s
		ResponseData result = ResponseFactory.createSuccessResponse(list);

		// ��r
		assertThat(result.getTotal()).isEqualTo(responseData.getTotal());
		assertThat(result.getMean().getFaceAttributes().getEmotion().getAnger()).isEqualTo(responseData.getMean().getFaceAttributes().getEmotion().getAnger());
		assertThat(result.getMean().getFaceAttributes().getEmotion().getContempt()).isEqualTo(responseData.getMean().getFaceAttributes().getEmotion().getAnger());
		assertThat(result.getMean().getFaceAttributes().getEmotion().getDisgust()).isEqualTo(responseData.getMean().getFaceAttributes().getEmotion().getAnger());
		assertThat(result.getMean().getFaceAttributes().getEmotion().getFear()).isEqualTo(responseData.getMean().getFaceAttributes().getEmotion().getAnger());
		assertThat(result.getMean().getFaceAttributes().getEmotion().getHappiness()).isEqualTo(responseData.getMean().getFaceAttributes().getEmotion().getAnger());
		assertThat(result.getMean().getFaceAttributes().getEmotion().getNeutral()).isEqualTo(responseData.getMean().getFaceAttributes().getEmotion().getAnger());
		assertThat(result.getMean().getFaceAttributes().getEmotion().getSadness()).isEqualTo(responseData.getMean().getFaceAttributes().getEmotion().getAnger());
		assertThat(result.getMean().getFaceAttributes().getEmotion().getSurprise()).isEqualTo(responseData.getMean().getFaceAttributes().getEmotion().getAnger());

	}

}
