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

	// ErrorResponseが生成され、そのerrorの値が"test error"であることを期待
	@Test
	void testCreateErrorResponse() {

		String expectedErrorMessage = "test error"; // このAPIから返されるエラー文

		Exception ex = new NotDetectedException(expectedErrorMessage);
		// 実行
		ErrorResponse response = ResponseFactory.createErrorResponse(ex);
		// 比較
		assertThat(response.getError()).isEqualTo(expectedErrorMessage);
	}

	// FaceApiErrorResponseが生成され、errorに固定のエラー文、detailsにJSON文字列で指定された(実際には、FaceAPIから返却された)エラーの内容が設定されていることを期待。
	// FaceApiErrorResponse.errorは固定のエラー文"Face API response is error."
	// FaceApiErrorResponse.detailのcode, messageにSON文字列で指定されたエラーの内容が格納
	@Test
	void testCreateFaceApiErrorResponse() throws JsonMappingException, JsonProcessingException {

		String expectedErrorMessage = "Face API response is error."; // このAPIから返されるエラー文
		String expetedDetailCode = "BadArgument"; // FaceAPIからのレスポンス
		String expectedDetailMessage = "Request body is invalid."; // FaceAPIからのレスポンス

		String FaceApiErrMessage = "{\"error\":{\"code\":\"" + expetedDetailCode + "\",\"message\":\""
				+ expectedDetailMessage + "\"}}"; // FaceAPIから返却されるエラーの想定
		Exception ex = new FaceApiException(FaceApiErrMessage);
		// 実行
		FaceApiErrorResponse response = ResponseFactory.createFaceApiErrorResponse(ex);
		// 比較
		assertThat(response.getMessage()).isEqualTo(expectedErrorMessage);
		assertThat(response.getDetails().getCode()).isEqualTo(expetedDetailCode);
		assertThat(response.getDetails().getMessage()).isEqualTo(expectedDetailMessage);
	}

	// ===== floorDecimal のテスト =====
	
	// 小数第4位切り捨て
	@Test
	void testFloorDecimal_lessThan5() {
		// 期待値
		double expect = 0.123;
		// 実行
		double result = ResponseFactory.floorDecimalPlace4(0.1234);
		// 比較
		assertThat(result).isEqualTo(expect);
	}

	// 小数第４位切り捨て(5以上の数字の場合)
	@Test
	void testFloorDecimal_5OrHigher() {
		// 期待値
		double expect = 0.123;
		// 実行
		double result = ResponseFactory.floorDecimalPlace4(0.1236);
		// 比較
		assertThat(result).isEqualTo(expect);
	}

	// 元の数字が小数第1位までの場合、小数第4位を指定しての切り捨て
	@Test
	void testFloorDecimal_oneDecimalPlace() {
		// 期待値
		double expect = 0.1;
		// 実行
		double result = ResponseFactory.floorDecimalPlace4(0.1);
		// 比較
		assertThat(result).isEqualTo(expect);
	}
	
	// ===== calcMeanEmotion のテスト =====
	
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
	
	// 複数の分析結果を含む配列resultDataのemotionの平均値が格納された
	// Emotionオブジェクトが返却される
	@Test
	void testCalcMeanEmotion() {
			// 分析結果を作成
		FaceAttributes faceAttributes1 = createFaceAttributes(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1);
		ResultData resultData1 = new ResultData();
		resultData1.setFaceAttributes(faceAttributes1);

		FaceAttributes faceAttributes2 = createFaceAttributes(0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2, 0.2);
		ResultData resultData2 = new ResultData();
		resultData2.setFaceAttributes(faceAttributes2);
		
		ResultData[] resultDatas = {resultData1, resultData2};
		// 期待値
			// emotionの平均値
		FaceAttributes meanFaceAttributes = createFaceAttributes(0.15, 0.15, 0.15, 0.15, 0.15, 0.15, 0.15, 0.15);
		MeanFaceAttributes mean = new MeanFaceAttributes();
		mean.setFaceAttributes(meanFaceAttributes);
		
		ResponseData expect = new ResponseData();
		expect.setTotal(2);
		expect.setMean(mean);
		expect.setResultData(resultDatas);
		// 実行
		Emotion result = ResponseFactory.calcMeanEmotion(resultDatas);
		// 比較
		assertThat(result.getAnger()).isEqualTo(mean.getFaceAttributes().getEmotion().getAnger());
		assertThat(result.getContempt()).isEqualTo(mean.getFaceAttributes().getEmotion().getContempt());
		assertThat(result.getDisgust()).isEqualTo(mean.getFaceAttributes().getEmotion().getDisgust());
		assertThat(result.getFear()).isEqualTo(mean.getFaceAttributes().getEmotion().getFear());
		assertThat(result.getHappiness()).isEqualTo(mean.getFaceAttributes().getEmotion().getHappiness());
		assertThat(result.getNeutral()).isEqualTo(mean.getFaceAttributes().getEmotion().getNeutral());
		assertThat(result.getSadness()).isEqualTo(mean.getFaceAttributes().getEmotion().getSadness());
		assertThat(result.getSurprise()).isEqualTo(mean.getFaceAttributes().getEmotion().getSurprise());

	}
	
	// ===== createSuccessResponse のテスト =====
	
	FaceRectangle createFaceRectangle(int top, int left, int width, int height) {
		FaceRectangle rectangle = new FaceRectangle();
		rectangle.setTop(top);
		rectangle.setLeft(left);
		rectangle.setWidth(width);
		rectangle.setHeight(height);
		
		return rectangle;
	}

	// 返却レスポンスが生成される
	@Test
	void testCreateSuccessResponse() {
		// 期待値作成
		FaceAttributes faceAttributes1 = createFaceAttributes(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1);
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
		// 実行
		ResponseData result = ResponseFactory.createSuccessResponse(list);
		// 比較
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
