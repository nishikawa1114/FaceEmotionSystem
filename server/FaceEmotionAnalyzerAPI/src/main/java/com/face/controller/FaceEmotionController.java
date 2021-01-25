package com.face.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.face.model.ErrorMassage;
import com.face.model.ImageInfo;
import com.face.model.ResultData;
import com.face.response.FaceApiException;
import com.face.response.FaceApiInvalidRequestException;
import com.face.response.FaceApiServerException;
import com.face.response.NotDetectedException;

@RestController
@RequestMapping("/face")
public class FaceEmotionController {

	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${SUBSCRIPTION_KEY}")
	private String subcscriptiponKey;

	@Value("${END_POINT_FACE_API}")
	private String endPoint;
	
	final String  QUERY_URL = endPoint + "?detectionModel=detection_01&returnFaceAttributes=emotion&returnFaceId=true"; // FaceAPIへの分析項目をクエリパラメータで指定

	@PostMapping(value = "/emotion")
	public ResultData[] analyze(@RequestBody ImageInfo url) throws Exception {

		// パラメータが不正な場合
		if (url.getUrl() == null || url.getUrl().isEmpty()) {
			throw new ValidationException(ErrorMassage.REQUEST_BODY_ERROR);
		}

		// ヘッダ設定
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Ocp-Apim-Subscription-Key", subcscriptiponKey);
		// ボディ設定
		// 画像URL と 分析項目を指定
		String imageQueryStr = "?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D"; // サーバー上に置かれている画像にアクセスするために必要なクエリ文字列。処理には関係なし。
		Map<String, String> map = new HashMap<>();
		map.put("url", url.getUrl() + imageQueryStr);
		HttpEntity<Object> request = new HttpEntity<Object>(map, headers);

		ResultData[] response = null;
		try {
			// FaceAPIと通信
			response = restTemplate.postForObject(QUERY_URL, request, ResultData[].class);
		} catch (HttpClientErrorException e) {
			// FaceAPIがエラーの場合
			if (e.getRawStatusCode() == 400 || e.getRawStatusCode() == 429) {
				throw new FaceApiException(e.getResponseBodyAsString());
			} else if (e.getRawStatusCode() == 503) {
				throw new FaceApiServerException(ErrorMassage.FACE_API_SERVER_UNABLABLE_ERROR);
			} else {
				throw new FaceApiInvalidRequestException(ErrorMassage.FACE_API_RESPONSE_ERROR);
			}
		} catch (Exception e) {
			throw new Exception(ErrorMassage.UNEXPECTED_ERROR);
		}

		// 顔が検出されなかった場合
		if (response == null || response.length == 0) {
			throw new NotDetectedException("face not detected.");
		}

		return response;

	}

}
