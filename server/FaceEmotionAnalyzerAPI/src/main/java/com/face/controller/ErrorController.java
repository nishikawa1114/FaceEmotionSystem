package com.face.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.face.response.ErrorResponse;
import com.face.response.FaceApiErrorResponse;
import com.face.response.FaceApiException;
import com.face.response.FaceApiInvalidRequestException;
import com.face.response.FaceApiServerException;
import com.face.response.NotDetectedException;
import com.face.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

	// POSTパラメータが不正の場合の例外処理
	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleInvalidRequestBodyException(HttpServletRequest req, ValidationException ex) {
		return Response.createErrorResponse(ex);
	}

	// 顔が検出されなかった場合の例外処理
	@ExceptionHandler(NotDetectedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleNotDetectedException(HttpServletRequest req, NotDetectedException ex) {
		return Response.createErrorResponse(ex);
	}

	// FaceAPIから400,429エラーが返却された場合の処理
	@ExceptionHandler(FaceApiInvalidRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public FaceApiErrorResponse handleFaceApiInvalidRequestBodyException(HttpServletRequest req,
			FaceApiInvalidRequestException ex) throws JsonMappingException, JsonProcessingException {
		return Response.createFaceApiErrorResponse(ex);
	}

	// FaceAPIから400,429以外のエラーが返却された場合の処理
	@ExceptionHandler(FaceApiException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public FaceApiErrorResponse handleFaceApiException(HttpServletRequest req, FaceApiException ex)
			throws JsonMappingException, JsonProcessingException {
		return Response.createFaceApiErrorResponse(ex);
	}

	// FaceAPIからサーバーエラーが返却された場合の処理
	@ExceptionHandler(FaceApiServerException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public FaceApiErrorResponse handleFaceApiException(HttpServletRequest req, FaceApiServerException ex)
			throws JsonMappingException, JsonProcessingException {
		return Response.createFaceApiErrorResponse(ex);
	}

	// メディアタイプが不正の場合の例外処理
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse("media type is invalid."), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

}
