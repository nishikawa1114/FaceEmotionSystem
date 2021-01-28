package com.face.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.face.model.ErrorMessage;
import com.face.model.ErrorResponse;
import com.face.model.FaceApiErrorResponse;
import com.face.response.FaceApiException;
import com.face.response.FaceApiInvalidRequestException;
import com.face.response.FaceApiServerException;
import com.face.response.NotDetectedException;
import com.face.response.ResponseFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

	// POSTパラメータが不正の場合の例外処理
	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleInvalidRequestBodyException(HttpServletRequest req, ValidationException ex) {
		return ResponseFactory.createErrorResponse(ex);
	}

	// 顔が検出されなかった場合の例外処理
	@ExceptionHandler(NotDetectedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleNotDetectedException(HttpServletRequest req, NotDetectedException ex) {
		return ResponseFactory.createErrorResponse(ex);
	}

	// FaceAPIから400,429エラーが返却された場合の処理
	@ExceptionHandler(FaceApiInvalidRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public FaceApiErrorResponse handleFaceApiInvalidRequestBodyException(HttpServletRequest req,
			FaceApiInvalidRequestException ex) throws JsonMappingException, JsonProcessingException {
		return ResponseFactory.createFaceApiErrorResponse(ex);
	}

	// FaceAPIから400,429以外のエラーが返却された場合の処理
	@ExceptionHandler(FaceApiException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public FaceApiErrorResponse handleFaceApiException(HttpServletRequest req, FaceApiException ex)
			throws JsonMappingException, JsonProcessingException {
		return ResponseFactory.createFaceApiErrorResponse(ex);
	}

	// FaceAPIからサーバーエラーが返却された場合の処理
	@ExceptionHandler(FaceApiServerException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public FaceApiErrorResponse handleFaceApiException(HttpServletRequest req, FaceApiServerException ex)
			throws JsonMappingException, JsonProcessingException {
		return ResponseFactory.createFaceApiErrorResponse(ex);
	}

	// メディアタイプが不正の場合の例外処理
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse(ErrorMessage.MEDIA_TYPE_ERROR), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	// その他のエラーの場合
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public ErrorResponse handleAllException(HttpServletRequest req, Exception ex) {
		return ResponseFactory.createErrorResponse(ex);
	}

}
