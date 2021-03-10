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
	void testFloorDecimal1() {
		// 期待値
		double expect = 0.123;
		// 実行
		double result = ResponseFactory.floorDecimal(0.1234, 4);
		// 比較
		assertThat(result).isEqualTo(expect);
	}

	// 小数第４位切り捨て(5以上の数字の場合)
	@Test
	void testFloorDecimal2() {
		// 期待値
		double expect = 0.123;
		// 実行
		double result = ResponseFactory.floorDecimal(0.1236, 4);
		// 比較
		assertThat(result).isEqualTo(expect);
	}

	// 元の数字が小数第1位までの場合、小数第4位を指定しての切り捨て
	@Test
	void testFloorDecimal3() {
		// 期待値
		double expect = 0.1;
		// 実行
		double result = ResponseFactory.floorDecimal(0.1, 4);
		// 比較
		assertThat(result).isEqualTo(expect);
	}

	// 小数第3位以下の切り捨て
	@Test
	void testFloorDecimal4() {
		// 期待値
		double expect = 0.12;
		// 実行
		double result = ResponseFactory.floorDecimal(0.1234, 3);
		// 比較
		assertThat(result).isEqualTo(expect);
	}

	// 小数点以下の切り捨て
	@Test
	void testFloorDecimal5() {
		// 期待値
		double expect = 1;
		// 実行
		double result = ResponseFactory.floorDecimal(1.1234, 1);
		// 比較
		assertThat(result).isEqualTo(expect);
	}
	
	// ===== calcMeanEmotion のテスト =====
	
	// 複数の分析結果を含む配列resultDataのemotionの平均値が格納された
	// Emotionオブジェクトが返却される
	@Test
	void testCalcMeanEmotion() {
		// 期待値
			// 分析結果を作成
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
			// emotionの平均値
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
		
		// 実行
		Emotion result = ResponseFactory.calcMeanEmotion(resultDatas);
		// 比較
		assertThat(result.getAnger()).isEqualTo(meanEmotion.getAnger());
		assertThat(result.getContempt()).isEqualTo(meanEmotion.getContempt());
		assertThat(result.getDisgust()).isEqualTo(meanEmotion.getDisgust());
		assertThat(result.getFear()).isEqualTo(meanEmotion.getFear());
		assertThat(result.getHappiness()).isEqualTo(meanEmotion.getHappiness());
		assertThat(result.getNeutral()).isEqualTo(meanEmotion.getNeutral());
		assertThat(result.getSadness()).isEqualTo(meanEmotion.getSadness());
		assertThat(result.getSurprise()).isEqualTo(meanEmotion.getSurprise());

	}
	
	// ===== createSuccessResponse のテスト =====

	// 返却レスポンスが生成される
	@Test
	void testCreateSuccessResponse() {
		// 期待値作成
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
