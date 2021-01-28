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
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ResponseFactoryTest {

	@Autowired
	ResponseFactory target;

	
	@Test
	void testCreateErrorResponse() {
		
		String expectedErrorMessage = "test error";
		
		Exception ex = new NotDetectedException(expectedErrorMessage);
		// ���s
		ErrorResponse response = ResponseFactory.createErrorResponse(ex);
		
		ErrorResponse expected = new ErrorResponse(expectedErrorMessage);
		// ��r
		assertThat(response.getError()).isEqualTo(expected.getError());
	}


	@Test
	void testCreateFaceApiErrorResponse() throws JsonMappingException, JsonProcessingException {
		
		String expectedErrorMessage = "test error";
		String expetedDetailCode = "BadArgument";
		String expectedDetailMessage = "Request body is invalid.";
		
		Exception ex = new FaceApiInvalidRequestException(expectedErrorMessage);
		
		ObjectMapper mapper = new ObjectMapper();
		FaceApiErrorResponse err = mapper.readValue(ex.getMessage(), FaceApiErrorResponse.class);
		err.setMessage("{ \"error\": { \"code\": \"" + expetedDetailCode + "\", \"message\": \"" + expectedDetailMessage + "\"} }");
		// ���s
		FaceApiErrorResponse response = ResponseFactory.createFaceApiErrorResponse(ex);
		
		FaceApiErrorResponse expected = new FaceApiErrorResponse();
		expected.setMessage(expectedErrorMessage);
		FaceApiErrorResponseDetail aaa = new FaceApiErrorResponseDetail();
		aaa.setCode(expetedDetailCode);
		aaa.setMessage(expectedDetailMessage);
		expected.setDetails(aaa);
		// ��r
		assertThat(response.getMessage()).isEqualTo(expected.getMessage());
		assertThat(response.getDetails()).isEqualTo(expected.getDetails());
	}

}
