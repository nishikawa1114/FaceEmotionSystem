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

	// 成功時のテスト
	// レスポンス HTTPステータス200を期待
	@Test
	void testAnalyzSuccess() throws Exception {

		// FaceAPIからのレスポンス
		String jsonResponseBody = "[{\"faceId\":\"580a572a-c529-4333-b93f-8429ebda8b18\",\"faceRectangle\":{\"top\":128,\"left\":529,\"width\":109,\"height\":109},\"faceAttributes\":{\"emotion\":{\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"neutral\":0.0,\"sadness\":0.0,\"surprise\":0.0}}}]";
		// 期待値
		String expect = "{\"total\":1,\"mean\":{\"faceAttributes\":{\"emotion\":{\"neutral\":0.0,\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"sadness\":0.0,\"surprise\":0.0}}},\"resultData\":[{\"faceId\":\"580a572a-c529-4333-b93f-8429ebda8b18\",\"faceRectangle\":{\"top\":128,\"left\":529,\"width\":109,\"height\":109},\"faceAttributes\":{\"emotion\":{\"neutral\":0.0,\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"sadness\":0.0,\"surprise\":0.0}}}]}";
		// FaceAPI通信のモック
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withSuccess(jsonResponseBody, MediaType.APPLICATION_JSON));
		// 実行・比較
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(content().json(expect))
				.andExpect(status().isOk());
	}

	// 成功時のテスト 画像に複数人移っている場合
	// レスポンス HTTPステータス200を期待
	@Test
	void testAnalyzSuccess2() throws Exception {

		// FaceAPIからのレスポンス
		String jsonResponseBody = "[{\"faceId\":\"580a572a-c529-4333-b93f-8429ebda8b18\",\"faceRectangle\":{\"top\":128,\"left\":529,\"width\":109,\"height\":109},\"faceAttributes\":{\"emotion\":{\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"neutral\":0.0,\"sadness\":0.0,\"surprise\":0.0}}},"
				+ "{\"faceId\":\"580a572a-c529-4333-b93f-8429ebda8b18\",\"faceRectangle\":{\"top\":128,\"left\":529,\"width\":109,\"height\":109},\"faceAttributes\":{\"emotion\":{\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"neutral\":0.0,\"sadness\":0.0,\"surprise\":0.0}}}]";
		// 期待値
		String expect = "{\"total\":2,\"mean\":{\"faceAttributes\":{\"emotion\":{\"neutral\":0.0,\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"sadness\":0.0,\"surprise\":0.0}}},"
				+ "\"resultData\":[{\"faceId\":\"580a572a-c529-4333-b93f-8429ebda8b18\",\"faceRectangle\":{\"top\":128,\"left\":529,\"width\":109,\"height\":109},\"faceAttributes\":{\"emotion\":{\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"neutral\":0.0,\"sadness\":0.0,\"surprise\":0.0}}},"
				+ "{\"faceId\":\"580a572a-c529-4333-b93f-8429ebda8b18\",\"faceRectangle\":{\"top\":128,\"left\":529,\"width\":109,\"height\":109},\"faceAttributes\":{\"emotion\":{\"anger\":0.0,\"contempt\":0.0,\"disgust\":0.0,\"fear\":0.0,\"happiness\":1.0,\"neutral\":0.0,\"sadness\":0.0,\"surprise\":0.0}}}]}";
		// FaceAPI通信のモック
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withSuccess(jsonResponseBody, MediaType.APPLICATION_JSON));
		// 実行・比較
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(content().json(expect))
				.andExpect(status().isOk());
	}

	// 画像から顔が検出されない場合のテスト
	// レスポンス HTTPステータス400を期待
	@Test
	void testAnalyzeErrorNotDetected() throws Exception {
		// FaceAPIからのレスポンス
		String jsonResponseBody = "";
		// 期待値
		String expect = "{\"error\":\"face not detected.\"}";
		// FaceAPI通信のモック
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST)).andRespond(withSuccess().body(jsonResponseBody));
		// 実行・比較
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(expect));
	}

	// POSTパラメータが不正の場合のテスト キー "url" → "uri"
	// レスポンス HTTPステータス400を期待
	// ※FaceAPIとの通信は行わない
	@Test
	void testAnalyzeErrorinvalidBody() throws Exception {
		// 期待値
		String expect = "{\"error\":\"request body is invalid.\"}";
		// 実行・比較
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"uri\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/02.jpg\"}"))
				.andExpect(content().json(expect))
				.andExpect(status().isBadRequest());
	}

	// POSTパラメータが未入力の場合のテスト
	// レスポンス HTTPステータス400を期待
	// ※FaceAPIとの通信は行わない
	@Test
	void testAnalyzeNoEnterdBody() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON))
//				.andExpect(content().json(expect))
				.andExpect(status().isBadRequest());
	}

	// メディアタイプが不正の場合のテスト
	// レスポンス HTTPステータス415を期待
	// ※FaceAPIとの通信は行わない
	@Test
	void testAnalyzeErrorInvalidMediaType() throws Exception {
		// 期待値
		String expect = "{\"error\":\"media type is invalid.\"}";
		// 実行・比較
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_PDF))
				.andExpect(content().json(expect))
				.andExpect(status().isUnsupportedMediaType());
	}

	// FaceAPIからHTTPステータス400が返却される場合
	// レスポンス HTTPステータス400を期待
	@Test
	void testAnalyzeErrorReturn400() throws Exception {
		// FaceAPIからのレスポンス
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// 期待値
		String expect = "{\"error\":\"Face API response is error.\",\"details\":{\"code\":\"BadArgument\",\"message\":\"Request body is invalid.\"}}";
		// FaceAPI通信のモック
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.BAD_REQUEST).body(jsonResponseBody));
		// 実行・比較
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON).content(
						"{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(content().json(expect))
				.andExpect(status().isBadRequest()).andReturn();

		String content = result.getResponse().getContentAsString();
		mockServer.verify();
	}

	// FaceAPIからHTTPステータス429が返却される場合
	// レスポンス HTTPステータス400を期待
	@Test
	void testAnalyzeReturn429() throws Exception {
		// FaceAPIからのレスポンス
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// 期待値
		String expect = "{\"error\":\"Face API response is error.\",\"details\":{\"code\":\"BadArgument\",\"message\":\"Request body is invalid.\"}}";
		// FaceAPI通信のモック
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.TOO_MANY_REQUESTS).body(jsonResponseBody));
		// 実行・比較
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(content().json(expect))
				.andExpect(status().isBadRequest());
	}

	// FaceAPIからHTTPステータス403(400,429以外)が返却される場合
	// レスポンス HTTPステータス500を期待
	@Test
	void testAnalyzeReturn403() throws Exception {
		// FaceAPIからのレスポンス
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// 期待値
		String expect = "{\"error\":\"Face API response is error.\",\"details\":{\"code\":\"BadArgument\",\"message\":\"Request body is invalid.\"}}";
		// FaceAPI通信のモック
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST)).andRespond(withStatus(HttpStatus.FORBIDDEN).body(jsonResponseBody));
		// 実行・比較
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(content().json(expect))
				.andExpect(status().isInternalServerError());

		mockServer.verify();
	}

	// FaceAPIとの通信でHTTPステータス503が返却される場合
	// レスポンス HTTPステータス503を期待
	@Test
	void testAnalyzeReturn503() throws Exception {
		// FaceAPIからのレスポンス
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// 期待値
		String expect = "{\"error\":\"Face API server unavalable.\",\"details\":{\"code\":\"BadArgument\",\"message\":\"Request body is invalid.\"}}";
		// FaceAPI通信のモック
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE).body(jsonResponseBody));
		// 実行・比較
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(content().json(expect))
				.andExpect(status().isServiceUnavailable());

		mockServer.verify();
	}

}
