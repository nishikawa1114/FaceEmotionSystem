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

	// ���X�|���X {"error":"test error"} ������
	@Test
	void testCreateErrorResponse() {
		
		String expectedErrorMessage = "test error"; // ����API����Ԃ����G���[��
		
		Exception ex = new NotDetectedException(expectedErrorMessage);
		// ���s
		ErrorResponse response = ResponseFactory.createErrorResponse(ex);
		// ��r
		assertThat(response.getError()).isEqualTo(expectedErrorMessage);
	}



	// ���X�|���X {"error":"Face API response is error.",
	//          "detail":{"code":"BadArgument",
	//                    "message":"Request body is invalid."}} ������
	@Test
	void testCreateFaceApiErrorResponse() throws JsonMappingException, JsonProcessingException {
		
		String expectedErrorMessage = "Face API response is error."; // ����API����Ԃ����G���[��
		String expetedDetailCode = "BadArgument"; // FaceAPI����̃��X�|���X
		String expectedDetailMessage = "Request body is invalid."; // FaceAPI����̃��X�|���X
		
		String FaceApiErrMessage = "{\"error\":{\"code\":\"" + expetedDetailCode + "\",\"message\":\"" + expectedDetailMessage + "\"}}"; // FaceAPI����ԋp�����G���[�̑z��
		Exception ex = new FaceApiException(FaceApiErrMessage);
		// ���s
		FaceApiErrorResponse response = ResponseFactory.createFaceApiErrorResponse(ex);
		// ��r
		assertThat(response.getMessage()).isEqualTo(expectedErrorMessage);
		assertThat(response.getDetails().getCode()).isEqualTo(expetedDetailCode);
		assertThat(response.getDetails().getMessage()).isEqualTo(expectedDetailMessage);
	}

}
