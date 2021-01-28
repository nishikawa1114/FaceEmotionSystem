package com.face.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.face.model.Emotion;
import com.face.model.FaceAttributes;
import com.face.model.FaceRectangle;
import com.face.model.ResultData;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class FaceEmotionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	FaceEmotionController target;

	// �������̃e�X�g
	// ���X�|���X HTTP�X�e�[�^�X200������
	@Test
	void testAnalyzSuccess() throws Exception {

		// �ԋp����郌�X�|���X
		Emotion emotion = new Emotion();
		emotion.setAnger(0);
		emotion.setContempt(0);
		emotion.setDisgust(0);
		emotion.setFear(0);
		emotion.setHappiness(0);
		emotion.setNeutral(0);
		emotion.setSadness(0);
		emotion.setSurprise(0);
		FaceAttributes faceAttributes = new FaceAttributes();
		faceAttributes.setEmotion(emotion);
		FaceRectangle faceRectangle = new FaceRectangle();
		faceRectangle.setTop(1);
		faceRectangle.setLeft(2);
		faceRectangle.setWidth(100);
		faceRectangle.setHeight(200);
		ResultData resultData = new ResultData();
		resultData.setFaceId("580a572a-c529-4333-b93f-8429ebda8b18");
		resultData.setFaceRectangle(faceRectangle);
		resultData.setFaceAttributes(faceAttributes);
		ResultData[] resultDatas = {resultData}; 
		String str = resultDatas.toString();
		
		String jsonResponseBody = "[{\"faceId\":\"580a572a-c529-4333-b93f-8429ebda8b18\",\"faceRectangle\":{\"top\":128,\"left\":529,\"width\":109,\"height\":109},\"faceAttributes\":{\"emotion\":{\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"neutral\":0.0,\"sadness\":0.0,\"surprise\":0.0}}}]";
		// FaceAPI�ʐM�̃��b�N
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST)).andRespond(withStatus(HttpStatus.OK).body(jsonResponseBody));

		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isOk());
//				.andExpect(content().json(jsonResponseBody));
	}

	// �摜����炪���o����Ȃ��ꍇ�̃e�X�g
	// ���X�|���X HTTP�X�e�[�^�X400������
	@Test
	void testAnalyzeErrorNotDetected() throws Exception {
		// �ԋp����郌�X�|���X
		String jsonResponseBody = "";
		// FaceAPI�ʐM�̃��b�N
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST)).andRespond(withSuccess().body(jsonResponseBody));

		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isBadRequest());
	}

	// POST�p�����[�^���s���̏ꍇ�̃e�X�g �L�[ "url" �� "uri"
	// ���X�|���X HTTP�X�e�[�^�X400������
	@Test
	void testAnalyzeErrorinvalidBody() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"uri\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/02.jpg\"}"))
				.andExpect(status().isBadRequest());
	}

	// POST�p�����[�^�������͂̏ꍇ�̃e�X�g
	// ���X�|���X HTTP�X�e�[�^�X400������
	@Test
	void testAnalyzeNoEnterdBody() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	// ���f�B�A�^�C�v���s���̏ꍇ�̃e�X�g
	// ���X�|���X HTTP�X�e�[�^�X415������
	@Test
	void testAnalyzeErrorInvalidMediaType() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_PDF))
				.andExpect(status().isUnsupportedMediaType());
	}

	// FaceAPI����HTTP�X�e�[�^�X400���ԋp�����ꍇ
	// ���X�|���X HTTP�X�e�[�^�X400������
	@Test
	void testAnalyzeErrorReturn400() throws Exception {

		// �ԋp����郌�X�|���X
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// FaceAPI�ʐM�̃��b�N
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.BAD_REQUEST).body(jsonResponseBody));
		// ���s
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isBadRequest())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		mockServer.verify();
	}

	// FaceAPI����HTTP�X�e�[�^�X429���ԋp�����ꍇ
	// ���X�|���X HTTP�X�e�[�^�X400������
	@Test
	void testAnalyzeReturn429() throws Exception {

		// �ԋp����郌�X�|���X
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// FaceAPI�ʐM�̃��b�N
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.TOO_MANY_REQUESTS).body(jsonResponseBody));
		// ���s
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isBadRequest());

		mockServer.verify();
	}

	// FaceAPI����HTTP�X�e�[�^�X403(400,429�ȊO)���ԋp�����ꍇ
	// ���X�|���X HTTP�X�e�[�^�X500������
	@Test
	void testAnalyzeReturn403() throws Exception {

		// �ԋp����郌�X�|���X
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// FaceAPI�ʐM�̃��b�N
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST)).andRespond(withStatus(HttpStatus.FORBIDDEN).body(jsonResponseBody));
		// ���s
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isInternalServerError());

		mockServer.verify();
	}

	// FaceAPI�Ƃ̒ʐM��HTTP�X�e�[�^�X503���ԋp�����ꍇ
	// ���X�|���X HTTP�X�e�[�^�X503������
	@Test
	void testAnalyzeReturn503() throws Exception {

		// �ԋp����郌�X�|���X
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// FaceAPI�ʐM�̃��b�N
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE).body(jsonResponseBody));
		// ���s
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isServiceUnavailable());

		mockServer.verify();
	}

}
