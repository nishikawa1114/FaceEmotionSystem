package com.face.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

		// FaceAPI����̃��X�|���X
		String jsonResponseBody = "[{\"faceId\":\"580a572a-c529-4333-b93f-8429ebda8b18\",\"faceRectangle\":{\"top\":128,\"left\":529,\"width\":109,\"height\":109},\"faceAttributes\":{\"emotion\":{\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"neutral\":0.0,\"sadness\":0.0,\"surprise\":0.0}}}]";
		// ���Ғl
		String expect = "{\"total\":1,\"mean\":{\"faceAttributes\":{\"emotion\":{\"neutral\":0.0,\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"sadness\":0.0,\"surprise\":0.0}}},\"resultData\":[{\"faceId\":\"580a572a-c529-4333-b93f-8429ebda8b18\",\"faceRectangle\":{\"top\":128,\"left\":529,\"width\":109,\"height\":109},\"faceAttributes\":{\"emotion\":{\"neutral\":0.0,\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"sadness\":0.0,\"surprise\":0.0}}}]}";
		// FaceAPI�ʐM�̃��b�N
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withSuccess(jsonResponseBody, MediaType.APPLICATION_JSON));
		// ���s�E��r
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(content().json(expect))
				.andExpect(status().isOk());
	}

	// �������̃e�X�g �摜�ɕ����l�ڂ��Ă���ꍇ
	// ���X�|���X HTTP�X�e�[�^�X200������
	@Test
	void testAnalyzSuccess2() throws Exception {

		// FaceAPI����̃��X�|���X
		String jsonResponseBody = "[{\"faceId\":\"580a572a-c529-4333-b93f-8429ebda8b18\",\"faceRectangle\":{\"top\":128,\"left\":529,\"width\":109,\"height\":109},\"faceAttributes\":{\"emotion\":{\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"neutral\":0.0,\"sadness\":0.0,\"surprise\":0.0}}},"
				+ "{\"faceId\":\"580a572a-c529-4333-b93f-8429ebda8b18\",\"faceRectangle\":{\"top\":128,\"left\":529,\"width\":109,\"height\":109},\"faceAttributes\":{\"emotion\":{\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"neutral\":0.0,\"sadness\":0.0,\"surprise\":0.0}}}]";
		// ���Ғl
		String expect = "{\"total\":2,\"mean\":{\"faceAttributes\":{\"emotion\":{\"neutral\":0.0,\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"sadness\":0.0,\"surprise\":0.0}}},"
				+ "\"resultData\":[{\"faceId\":\"580a572a-c529-4333-b93f-8429ebda8b18\",\"faceRectangle\":{\"top\":128,\"left\":529,\"width\":109,\"height\":109},\"faceAttributes\":{\"emotion\":{\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"neutral\":0.0,\"sadness\":0.0,\"surprise\":0.0}}},"
				+ "{\"faceId\":\"580a572a-c529-4333-b93f-8429ebda8b18\",\"faceRectangle\":{\"top\":128,\"left\":529,\"width\":109,\"height\":109},\"faceAttributes\":{\"emotion\":{\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"neutral\":0.0,\"sadness\":0.0,\"surprise\":0.0}}}]}";
		// FaceAPI�ʐM�̃��b�N
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withSuccess(jsonResponseBody, MediaType.APPLICATION_JSON));
		// ���s�E��r
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(content().json(expect))
				.andExpect(status().isOk());
	}

	// �摜����炪���o����Ȃ��ꍇ�̃e�X�g
	// ���X�|���X HTTP�X�e�[�^�X400������
	@Test
	void testAnalyzeErrorNotDetected() throws Exception {
		// FaceAPI����̃��X�|���X
		String jsonResponseBody = "";
		// ���Ғl
		String expect = "{\"error\":\"face not detected.\"}";
		// FaceAPI�ʐM�̃��b�N
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST)).andRespond(withSuccess().body(jsonResponseBody));
		// ���s�E��r
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(expect));
	}

	// POST�p�����[�^���s���̏ꍇ�̃e�X�g �L�[ "url" �� "uri"
	// ���X�|���X HTTP�X�e�[�^�X400������
	// ��FaceAPI�Ƃ̒ʐM�͍s��Ȃ�
	@Test
	void testAnalyzeErrorinvalidBody() throws Exception {
		// ���Ғl
		String expect = "{\"error\":\"request body is invalid.\"}";
		// ���s�E��r
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"uri\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/02.jpg\"}"))
				.andExpect(content().json(expect))
				.andExpect(status().isBadRequest());
	}

	// POST�p�����[�^�������͂̏ꍇ�̃e�X�g
	// ���X�|���X HTTP�X�e�[�^�X400������
	// ��FaceAPI�Ƃ̒ʐM�͍s��Ȃ�
	@Test
	void testAnalyzeNoEnterdBody() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON))
//				.andExpect(content().json(expect))
				.andExpect(status().isBadRequest());
	}

	// ���f�B�A�^�C�v���s���̏ꍇ�̃e�X�g
	// ���X�|���X HTTP�X�e�[�^�X415������
	// ��FaceAPI�Ƃ̒ʐM�͍s��Ȃ�
	@Test
	void testAnalyzeErrorInvalidMediaType() throws Exception {
		// ���Ғl
		String expect = "{\"error\":\"media type is invalid.\"}";
		// ���s�E��r
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_PDF))
				.andExpect(content().json(expect))
				.andExpect(status().isUnsupportedMediaType());
	}

	// FaceAPI����HTTP�X�e�[�^�X400���ԋp�����ꍇ
	// ���X�|���X HTTP�X�e�[�^�X400������
	@Test
	void testAnalyzeErrorReturn400() throws Exception {
		// FaceAPI����̃��X�|���X
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// ���Ғl
		String expect = "{\"error\":\"Face API response is error.\",\"details\":{\"code\":\"BadArgument\",\"message\":\"Request body is invalid.\"}}";
		// FaceAPI�ʐM�̃��b�N
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.BAD_REQUEST).body(jsonResponseBody));
		// ���s�E��r
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON).content(
						"{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(content().json(expect))
				.andExpect(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString();
		mockServer.verify();
	}

	// FaceAPI����HTTP�X�e�[�^�X429���ԋp�����ꍇ
	// ���X�|���X HTTP�X�e�[�^�X400������
	@Test
	void testAnalyzeReturn429() throws Exception {
		// FaceAPI����̃��X�|���X
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// ���Ғl
		String expect = "{\"error\":\"Face API response is error.\",\"details\":{\"code\":\"BadArgument\",\"message\":\"Request body is invalid.\"}}";
		// FaceAPI�ʐM�̃��b�N
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.TOO_MANY_REQUESTS).body(jsonResponseBody));
		// ���s�E��r
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(content().json(expect))
				.andExpect(status().isBadRequest());
	}

	// FaceAPI����HTTP�X�e�[�^�X403(400,429�ȊO)���ԋp�����ꍇ
	// ���X�|���X HTTP�X�e�[�^�X500������
	@Test
	void testAnalyzeReturn403() throws Exception {
		// FaceAPI����̃��X�|���X
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// ���Ғl
		String expect = "{\"error\":\"Face API response is error.\",\"details\":{\"code\":\"BadArgument\",\"message\":\"Request body is invalid.\"}}";
		// FaceAPI�ʐM�̃��b�N
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST)).andRespond(withStatus(HttpStatus.FORBIDDEN).body(jsonResponseBody));
		// ���s�E��r
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(content().json(expect))
				.andExpect(status().isInternalServerError());

		mockServer.verify();
	}

	// FaceAPI�Ƃ̒ʐM��HTTP�X�e�[�^�X503���ԋp�����ꍇ
	// ���X�|���X HTTP�X�e�[�^�X503������
	@Test
	void testAnalyzeReturn503() throws Exception {
		// FaceAPI����̃��X�|���X
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// ���Ғl
		String expect = "{\"error\":\"Face API server unavalable.\",\"details\":{\"code\":\"BadArgument\",\"message\":\"Request body is invalid.\"}}";
		// FaceAPI�ʐM�̃��b�N
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE).body(jsonResponseBody));
		// ���s�E��r
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(content().json(expect))
				.andExpect(status().isServiceUnavailable());

		mockServer.verify();
	}

}
