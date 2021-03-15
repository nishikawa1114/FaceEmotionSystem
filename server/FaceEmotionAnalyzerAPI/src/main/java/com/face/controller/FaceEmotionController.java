package com.face.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.face.model.ErrorMessage;
import com.face.model.ImageInfo;
import com.face.model.ResponseData;
import com.face.model.ResultData;
import com.face.response.FaceApiException;
import com.face.response.FaceApiInvalidRequestException;
import com.face.response.FaceApiServerException;
import com.face.response.NotDetectedException;
import com.face.response.ResponseFactory;

@CrossOrigin
@RestController
@RequestMapping("/face")
public class FaceEmotionController {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private RestTemplate restTemplate;

	@Value("${SUBSCRIPTION_KEY}")
	private String subcscriptiponKey;

	@Value("${END_POINT_FACE_API}")
	private String endPoint;

	@PostMapping(value = "/emotion")
	public ResponseData analyze(@RequestBody ImageInfo url) throws Exception {

		// パラメータが不正な場合
		if (url.getUrl() == null || url.getUrl().isEmpty()) {
			throw new ValidationException(ErrorMessage.REQUEST_BODY_ERROR);
		}

		// ヘッダ設定
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Ocp-Apim-Subscription-Key", subcscriptiponKey);
		// ボディ設定
		// 画像URL と 分析項目を指定
		String queryUrl = endPoint + "?detectionModel=detection_01&returnFaceAttributes=emotion&returnFaceId=true";

		Map<String, String> map = new HashMap<>();
		map.put("url", url.getUrl());
		HttpEntity<Object> request = new HttpEntity<Object>(map, headers);

		ResultData[] response = null;
		try {
			// FaceAPIと通信
			response = restTemplate.postForObject(queryUrl, request, ResultData[].class);
		} catch (HttpClientErrorException e) {
			// FaceAPIがエラーの場合
			if (e.getRawStatusCode() == 400 || e.getRawStatusCode() == 429) {
				throw new FaceApiInvalidRequestException(e.getResponseBodyAsString());
			} else {
				throw new FaceApiException(e.getResponseBodyAsString());
			}
		} catch (HttpServerErrorException e) {
			// FaceAPIがエラーの場合
			if (e.getRawStatusCode() == 503) {
				throw new FaceApiServerException(e.getResponseBodyAsString());
			} else {
				throw new Exception(ErrorMessage.UNEXPECTED_ERROR);
			}
		} catch (Exception e) {
			throw new Exception(ErrorMessage.UNEXPECTED_ERROR);
		}

		// 顔が検出されなかった場合
		if (response == null || response.length == 0) {
			throw new NotDetectedException("face not detected.");
		}
		
		ResponseData responseData = ResponseFactory.createSuccessResponse(response);

		return responseData;

	}

}
