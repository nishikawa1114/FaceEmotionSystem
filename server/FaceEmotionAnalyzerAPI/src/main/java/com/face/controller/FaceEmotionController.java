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
	
	final String QUERY_URL = endPoint + "?detectionModel=detection_01&returnFaceAttributes=emotion&returnFaceId=true"; // FaceAPI�ւ̕��͍��ڂ��N�G���p�����[�^�Ŏw��
	final String IMAGE_QUERY_STR = "?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D"; // �T�[�o�[��ɒu����Ă���摜�ɃA�N�Z�X���邽�߂ɕK�v�ȃN�G��������B�����ɂ͊֌W�Ȃ��B
	@PostMapping(value = "/emotion")
	public ResultData[] analyze(@RequestBody ImageInfo url) throws Exception {

		// �p�����[�^���s���ȏꍇ
		if (url.getUrl() == null || url.getUrl().isEmpty()) {
			throw new ValidationException(ErrorMassage.REQUEST_BODY_ERROR);
		}

		// �w�b�_�ݒ�
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Ocp-Apim-Subscription-Key", subcscriptiponKey);
		// �{�f�B�ݒ�
		// �摜URL �� ���͍��ڂ��w��
		
		Map<String, String> map = new HashMap<>();
		map.put("url", url.getUrl() + IMAGE_QUERY_STR);
		HttpEntity<Object> request = new HttpEntity<Object>(map, headers);

		ResultData[] response = null;
		try {
			// FaceAPI�ƒʐM
			response = restTemplate.postForObject(QUERY_URL, request, ResultData[].class);
		} catch (HttpClientErrorException e) {
			// FaceAPI���G���[�̏ꍇ
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

		// �炪���o����Ȃ������ꍇ
		if (response == null || response.length == 0) {
			throw new NotDetectedException("face not detected.");
		}

		return response;

	}

}