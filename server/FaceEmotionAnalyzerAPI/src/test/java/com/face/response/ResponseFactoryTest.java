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
	void testFloorDecimal_lessThan5() {
		// ���Ғl
		double expect = 0.123;
		// ���s
		double result = ResponseFactory.floorDecimalPlace4(0.1234);
		// ��r
		assertThat(result).isEqualTo(expect);
	}

	// ������S�ʐ؂�̂�(5�ȏ�̐����̏ꍇ)
	@Test
	void testFloorDecimal_5OrHigher() {
		// ���Ғl
		double expect = 0.123;
		// ���s
		double result = ResponseFactory.floorDecimalPlace4(0.1236);
		// ��r
		assertThat(result).isEqualTo(expect);
	}

	// ���̐�����������1�ʂ܂ł̏ꍇ�A������4�ʂ��w�肵�Ă̐؂�̂�
	@Test
	void testFloorDecimal_oneDecimalPlace() {
		// ���Ғl
		double expect = 0.1;
		// ���s
		double result = ResponseFactory.floorDecimalPlace4(0.1);
		// ��r
		assertThat(result).isEqualTo(expect);
	}
	
	// ===== calcMeanEmotion �̃e�X�g =====
	
	// FaceAttributes������������
	FaceAttributes createFaceAttributes(double anger, double contempt,
										double disgust, double fear,
										double happiness, double neutral,
										double sadness, double surprise) {
		
		Emotion emotion = new Emotion();
		emotion.setAnger(anger);
		emotion.setContempt(contempt);
		emotion.setDisgust(disgust);
		emotion.setFear(fear);
		emotion.setHappiness(happiness);
		emotion.setNeutral(neutral);
		emotion.setSadness(sadness);
		emotion.setSurprise(surprise);
		FaceAttributes faceAttributes = new FaceAttributes();
		faceAttributes.setEmotion(emotion);
		
		return faceAttributes;
	}
	
	// �����̕��͌��ʂ��܂ޔz��resultData��emotion�̕��ϒl���i�[���ꂽ
	// Emotion�I�u�W�F�N�g���ԋp�����
	@Test
	void testCalcMeanEmotion() {
			// ���͌��ʂ��쐬
		FaceAttributes faceAttributes1 = createFaceAttributes(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8);
		ResultData resultData1 = new ResultData();
		resultData1.setFaceAttributes(faceAttributes1);

		FaceAttributes faceAttributes2 = createFaceAttributes(0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0);
		ResultData resultData2 = new ResultData();
		resultData2.setFaceAttributes(faceAttributes2);
		
		ResultData[] resultDatas = {resultData1, resultData2};
		// ���Ғl
			// emotion�̕��ϒl
		FaceAttributes meanFaceAttributes = createFaceAttributes(0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9);
		MeanFaceAttributes mean = new MeanFaceAttributes();
		mean.setFaceAttributes(meanFaceAttributes);
		
		ResponseData expect = new ResponseData();
		expect.setTotal(2);
		expect.setMean(mean);
		expect.setResultData(resultDatas);
		// ���s
		Emotion result = ResponseFactory.calcMeanEmotion(resultDatas);
		// ��r
		Emotion expectEmotion = mean.getFaceAttributes().getEmotion(); // ���҂��銴��̕��ϒl
		assertThat(result.getAnger()).isEqualTo(expectEmotion.getAnger());
		assertThat(result.getContempt()).isEqualTo(expectEmotion.getContempt());
		assertThat(result.getDisgust()).isEqualTo(expectEmotion.getDisgust());
		assertThat(result.getFear()).isEqualTo(expectEmotion.getFear());
		assertThat(result.getHappiness()).isEqualTo(expectEmotion.getHappiness());
		assertThat(result.getNeutral()).isEqualTo(expectEmotion.getNeutral());
		assertThat(result.getSadness()).isEqualTo(expectEmotion.getSadness());
		assertThat(result.getSurprise()).isEqualTo(expectEmotion.getSurprise());

	}
	
	// ===== createSuccessResponse �̃e�X�g =====
	
	FaceRectangle createFaceRectangle(int top, int left, int width, int height) {
		FaceRectangle rectangle = new FaceRectangle();
		rectangle.setTop(top);
		rectangle.setLeft(left);
		rectangle.setWidth(width);
		rectangle.setHeight(height);
		
		return rectangle;
	}

	// �ԋp���X�|���X�����������
	@Test
	void testCreateSuccessResponse() {
		// ���Ғl�쐬
		FaceAttributes faceAttributes1 = createFaceAttributes(0.1, 0.12, 0.13, 0.14, 0.15, 0.16, 0.17, 0.18);
		FaceRectangle faceRectangle1 = createFaceRectangle(10, 20, 150, 200);
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
		Emotion resultEmotion = result.getMean().getFaceAttributes().getEmotion(); // ���s���ʂ��瓾���銴��̕��ϒl
		Emotion expectEmotion = responseData.getMean().getFaceAttributes().getEmotion(); // ���҂��銴��̕��ϒl
		assertThat(result.getTotal()).isEqualTo(responseData.getTotal());
		assertThat(resultEmotion.getAnger()).isEqualTo(expectEmotion.getAnger());
		assertThat(resultEmotion.getContempt()).isEqualTo(expectEmotion.getContempt());
		assertThat(resultEmotion.getDisgust()).isEqualTo(expectEmotion.getDisgust());
		assertThat(resultEmotion.getFear()).isEqualTo(expectEmotion.getFear());
		assertThat(resultEmotion.getHappiness()).isEqualTo(expectEmotion.getHappiness());
		assertThat(resultEmotion.getNeutral()).isEqualTo(expectEmotion.getNeutral());
		assertThat(resultEmotion.getSadness()).isEqualTo(expectEmotion.getSadness());
		assertThat(resultEmotion.getSurprise()).isEqualTo(expectEmotion.getSurprise());

	}

}
