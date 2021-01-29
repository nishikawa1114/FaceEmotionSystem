package com.face.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.face.model.ErrorResponse;
import com.face.model.FaceApiErrorResponse;
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
		
		String FaceApiErrMessage = "{\"error\":{\"code\":\"" + expetedDetailCode + "\",\"message\":\"" + expectedDetailMessage + "\"}}"; // FaceAPIから返却されるエラーの想定
		Exception ex = new FaceApiException(FaceApiErrMessage);
		// 実行
		FaceApiErrorResponse response = ResponseFactory.createFaceApiErrorResponse(ex);
		// 比較
		assertThat(response.getMessage()).isEqualTo(expectedErrorMessage);
		assertThat(response.getDetails().getCode()).isEqualTo(expetedDetailCode);
		assertThat(response.getDetails().getMessage()).isEqualTo(expectedDetailMessage);
	}

}
