package com.face.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.face.model.Param;
import com.face.model.ResultData;
import com.face.response.FaceApiError;
import com.face.response.FaceApiErrorResponse;
import com.face.response.FaceApiException;
import com.face.response.NotDetectedException;
import com.face.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/face")
public class FaceEmotionController {

	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${SUBSCRIPTION_KEY}")
	private String subcscriptiponKey;

	@Value("${END_POINT_FACE_API}")
	private String faceUrl;

	// FaceAPI�� JSON ����x�I�u�W�F�N�g�ɂ��Ă���ԋp
	// �摜URL���w�肷��
	@PostMapping(value = "/emotion")
	public ResultData[] emotion(@RequestBody Param url, @RequestHeader("Content-Type") String type) throws JsonProcessingException { // �摜URL��String�^�Ŏ󂯎��

		ResultData[] response = null;

		try {

			// ���f�B�A�^�C�v���s���̏ꍇ
			if (!type.equals("application/json")) {
				throw new InvalidMediaTypeException("media type is invalid.", type);
			}
			// �p�����[�^���s���ȏꍇ
			if (url.url == null) {
				throw new ValidationException("request body is invalid.");
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Ocp-Apim-Subscription-Key", subcscriptiponKey);

			String queryUrl = faceUrl + "?detectionModel=detection_01&returnFaceAttributes=emotion&returnFaceId=true";

			Map<String, String> map = new HashMap<>();
			String imgUrl = url.getUrl();
			map.put("url", imgUrl
					+ "?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D");
			HttpEntity<Object> request = new HttpEntity<Object>(map, headers);

			response = restTemplate.postForObject(queryUrl, request, ResultData[].class);
		} catch (HttpClientErrorException e) {
			// Face API���G���[�̏ꍇ			
			if (e.getRawStatusCode() == 400 || e.getRawStatusCode() == 429) {
				throw new FaceApiException(e.getResponseBodyAsString());
			} else if (e.getRawStatusCode() == 503) {
				throw new FaceApiException("Face API server unavalable.");
			}
		}

		// �炪���o����Ȃ������ꍇ
		if (response.length == 0) {
			throw new NotDetectedException("face not detected.");
		}

		return response;

	}


}
