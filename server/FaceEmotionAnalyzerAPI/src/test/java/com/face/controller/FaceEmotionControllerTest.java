package com.face.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
	}

	// �������̃e�X�g
	// ���X�|���X�@HTTP�X�e�[�^�X200������
	@Test
	void testAnalyzSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isOk());
	}
	
	// �摜����炪���o����Ȃ��ꍇ�̃e�X�g
	// ���X�|���X�@HTTP�X�e�[�^�X400������
	@Test
	void testAnalyzeErrorNotDetected() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/02.jpg\"}"))
				.andExpect(status().isBadRequest());
	}

	// POST�p�����[�^���s���̏ꍇ�̃e�X�g�@�L�[�@"url" ���@"uri"
	// ���X�|���X�@HTTP�X�e�[�^�X400������
	@Test
	void testAnalyzeErrorinvalidBody() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"uri\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/02.jpg\"}"))
				.andExpect(status().isBadRequest());
	}

	// POST�p�����[�^�������͂̏ꍇ�̃e�X�g
	// ���X�|���X�@HTTP�X�e�[�^�X400������
	@Test
	void testAnalyzeNoEnterdBody() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	// ���f�B�A�^�C�v���s���̏ꍇ�̃e�X�g
	// ���X�|���X�@HTTP�X�e�[�^�X415������
	@Test
	void testAnalyzeErrorInvalidMediaType() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_PDF))
				.andExpect(status().isUnsupportedMediaType());
	}

	// FaceAPI����HTTP�X�e�[�^�X400���ԋp�����ꍇ
	// ���X�|���X�@HTTP�X�e�[�^�X400������
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
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isBadRequest());

		mockServer.verify();
	}

	// FaceAPI����HTTP�X�e�[�^�X429���ԋp�����ꍇ
	// ���X�|���X�@HTTP�X�e�[�^�X400������
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

	// FaceAPI�Ƃ̒ʐM��HTTP�X�e�[�^�X503���ԋp�����ꍇ
	// ���X�|���X�@HTTP�X�e�[�^�X503������
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
