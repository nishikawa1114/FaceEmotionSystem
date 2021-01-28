package com.face.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

//import com.face.model.ErrorResponse;
//import com.face.model.ErrorMassage;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class FaceEmotionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	RestTemplate restTemplate;

//	@Mock
//	RestTemplate restTemplate;
//	@InjectMocks

	@Autowired
	FaceEmotionController target;

	@Value("${SUBSCRIPTION_KEY}")
	private String subcscriptiponKey;

	@Value("${END_POINT_FACE_API}")
	private String endPoint;

	private String url = endPoint + "?detectionModel=detection_01&returnFaceAttributes=emotion&returnFaceId=true";
	private String ImageUrl = "https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg"
			+ "?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D";

//	RestTemplate restTemplate = new RestTemplate();

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
	}

	// 成功時のテスト
	@Test
	void testAnalyzSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isOk());
	}

	// 顔が検出されない場合のテスト
	@Test
	void testAnalyzeErrorNotDetected() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/02.jpg\"}"))
				.andExpect(status().isBadRequest());
	}

	// POSTパラメータが不正の場合のテスト
	@Test
	void testAnalyzeErrorinvalidBody() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"uri\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/02.jpg\"}"))
				.andExpect(status().isBadRequest());
	}

	// POSTパラメータが未入力の場合のテスト
	@Test
	void testAnalyzeNoEnterdBody() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON).content(""))
				.andExpect(status().isBadRequest());
	}

	// メディアタイプが不正の場合のテスト
	@Test
	void testAnalyzeErrorInvalidMediaType() throws Exception {
//		ErrorResponse errorResponse = new ErrorResponse(ErrorMassage.REQUEST_BODY_ERROR);

		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_PDF))
				.andExpect(status().isUnsupportedMediaType());
	}

	// FaceAPIからHTTPステータス400が返却される場合
	@Test
	void testAnalyzeErrorReturn400() throws Exception {

		// 返却されるレスポンス
		String jsonResponseBody = "{ \"error\": { \"code\": \"request is invalid\", \"message\": \"パラメータが不正です。\"} }";
		// モック
		MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true)
				.bufferContent().build();
		mockServer.expect(method(HttpMethod.POST))
				.andRespond(withStatus(HttpStatus.BAD_REQUEST).body(jsonResponseBody));
		// 実行
		mockMvc.perform(MockMvcRequestBuilders.post("/face/emotion").contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg\"}"))
				.andExpect(status().isBadRequest());

		mockServer.verify();
	}

	// FaceAPIからHTTPステータス429が返却される場合
	@Test
	void testAnalyzeReturn429() throws Exception {

		// 返却されるレスポンス
		String jsonResponseBody = "{ \"error\": { \"code\": \"request is invalid\", \"message\": \"パラメータが不正です。\"} }";

		// ヘッダ設定
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Ocp-Apim-Subscription-Key", subcscriptiponKey);
		// モック
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

	// FaceAPIとの通信でHTTPステータス503が返却される場合
	@Test
	void testAnalyzeReturn503() throws Exception {

		// 返却されるレスポンス
		String jsonResponseBody = "{ \"error\": { \"code\": \"request is invalid\", \"message\": \"パラメータが不正です。\"} }";
		// モック
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
