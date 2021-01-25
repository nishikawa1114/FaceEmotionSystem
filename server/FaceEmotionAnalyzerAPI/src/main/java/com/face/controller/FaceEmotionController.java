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

	@PostMapping(value = "/emotion")
	public ResultData[] analyze(@RequestBody ImageInfo url) {

		ResultData[] response = null;

		try {
			// �p�����[�^���s���ȏꍇ
			if (url.url == null) {
				throw new ValidationException("request body is invalid.");
			}

			// �w�b�_�ݒ�
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Ocp-Apim-Subscription-Key", subcscriptiponKey);
			// �{�f�B�ݒ�
			// �摜URL �� ���͍��ڂ��w��
			String queryUrl = endPoint + "?detectionModel=detection_01&returnFaceAttributes=emotion&returnFaceId=true";
			Map<String, String> map = new HashMap<>();
			map.put("url", url.getUrl()
					+ "?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D");
			HttpEntity<Object> request = new HttpEntity<Object>(map, headers);
			// FaceAPI�ƒʐM
			response = restTemplate.postForObject(queryUrl, request, ResultData[].class);
		} catch (HttpClientErrorException e) {
			// FaceAPI���G���[�̏ꍇ
			if (e.getRawStatusCode() == 400 || e.getRawStatusCode() == 429) {
				throw new FaceApiException(e.getResponseBodyAsString());
			} else if (e.getRawStatusCode() == 503) {
				throw new FaceApiServerException("Face API server unavalable.");
			} else {
				throw new FaceApiInvalidRequestException("Face API response is error.");
			}
		}

		// �炪���o����Ȃ������ꍇ
		if (response.length == 0) {
			throw new NotDetectedException("face not detected.");
		}

		return response;

	}

}
