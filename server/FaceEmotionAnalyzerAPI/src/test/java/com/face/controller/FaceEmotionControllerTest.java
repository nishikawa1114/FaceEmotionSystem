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

	// 成功時のテスト
	// レスポンス HTTPステータス200を期待
	@Test
	void testAnalyzSuccess() throws Exception {

		// 返却されるレスポンス
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
		// FaceAPI通信のモック
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST)).andRespond(withStatus(HttpStatus.OK).body(jsonResponseBody));

		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isOk());
//				.andExpect(content().json(jsonResponseBody));
	}

	// 画像から顔が検出されない場合のテスト
	// レスポンス HTTPステータス400を期待
	@Test
	void testAnalyzeErrorNotDetected() throws Exception {
		// 返却されるレスポンス
		String jsonResponseBody = "";
		// FaceAPI通信のモック
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST)).andRespond(withSuccess().body(jsonResponseBody));

		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isBadRequest());
	}

	// POSTパラメータが不正の場合のテスト キー "url" → "uri"
	// レスポンス HTTPステータス400を期待
	@Test
	void testAnalyzeErrorinvalidBody() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"uri\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/02.jpg\"}"))
				.andExpect(status().isBadRequest());
	}

	// POSTパラメータが未入力の場合のテスト
	// レスポンス HTTPステータス400を期待
	@Test
	void testAnalyzeNoEnterdBody() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	// メディアタイプが不正の場合のテスト
	// レスポンス HTTPステータス415を期待
	@Test
	void testAnalyzeErrorInvalidMediaType() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_PDF))
				.andExpect(status().isUnsupportedMediaType());
	}

	// FaceAPIからHTTPステータス400が返却される場合
	// レスポンス HTTPステータス400を期待
	@Test
	void testAnalyzeErrorReturn400() throws Exception {

		// 返却されるレスポンス
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// FaceAPI通信のモック
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.BAD_REQUEST).body(jsonResponseBody));
		// 実行
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isBadRequest())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		mockServer.verify();
	}

	// FaceAPIからHTTPステータス429が返却される場合
	// レスポンス HTTPステータス400を期待
	@Test
	void testAnalyzeReturn429() throws Exception {

		// 返却されるレスポンス
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// FaceAPI通信のモック
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.TOO_MANY_REQUESTS).body(jsonResponseBody));
		// 実行
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isBadRequest());

		mockServer.verify();
	}

	// FaceAPIからHTTPステータス403(400,429以外)が返却される場合
	// レスポンス HTTPステータス500を期待
	@Test
	void testAnalyzeReturn403() throws Exception {

		// 返却されるレスポンス
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// FaceAPI通信のモック
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST)).andRespond(withStatus(HttpStatus.FORBIDDEN).body(jsonResponseBody));
		// 実行
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isInternalServerError());

		mockServer.verify();
	}

	// FaceAPIとの通信でHTTPステータス503が返却される場合
	// レスポンス HTTPステータス503を期待
	@Test
	void testAnalyzeReturn503() throws Exception {

		// 返却されるレスポンス
		String jsonResponseBody = "{ \"error\": { \"code\": \"BadArgument\", \"message\": \"Request body is invalid.\"} }";
		// FaceAPI通信のモック
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE).body(jsonResponseBody));
		// 実行
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isServiceUnavailable());

		mockServer.verify();
	}

}
