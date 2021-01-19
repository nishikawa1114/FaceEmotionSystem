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
import org.springframework.web.client.RestTemplate;

import com.face.model.Param;
import com.face.model.ResultData;
import com.face.response.Response;

@RestController
public class FaceEmotionController {
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Value("${SUBSCRIPTION_KEY}")   
	private String subcscriptiponKey;
	
	@Value("${END_POINT_FACE_API}")   
	private String faceUrl;

	// FaceAPIの JSON を一度オブジェクトにしてから返却
    // 画像URLを指定する
    @PostMapping(value = "/face/emotion")
    public  Object[] emotion(	@RequestBody Param url,
    							@RequestHeader("Content-Type") String value) { // 画像URLをString型で受け取る
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Ocp-Apim-Subscription-Key", subcscriptiponKey);
        
        String queryUrl = faceUrl + "?detectionModel=detection_01&returnFaceAttributes=emotion&returnFaceId=true";
        
        Map<String, String> map= new HashMap<>();
        String imgUrl = url.getUrl();
        map.put("url", imgUrl + "?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D");
        HttpEntity<Object> request = new HttpEntity<Object>(map, headers);
        
        // メディアタイプが不正の場合
        // 
        // cmdに{"timestamp":"2021-01-19T07:26:26.788+00:00","status":500,"error":"Internal Server Error","message":"","path":"/face/emotion"}
        // のように、返却される。InvalidMediaTypeException→ValidationExceptionに変更すれば、ErrorResponseがcmdに返却される
        if (!value.equals("application/json")) {
        	throw new InvalidMediaTypeException(value, imgUrl);
        }
        // パラメータが不正な場合
        //
        // POSTパラメータを-d '{"url": "https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg"}'と指定してもエラーになっている

//        ResultData[] response = null;
//        ResponseEntity<List<ResultData>> response = restTemplate.exchange(queryUrl,HttpMethod.POST,request,new ParameterizedTypeReference<List<ResultData>>(){});
        Object[] response =  restTemplate.postForObject(queryUrl, request, Object[].class);
        return response;
        // オブジェクトに格納する部分がうまくいっていない…
        // Object型で実行すれば、正しく返却できる

    }
    
    
    


}
