package com.face.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.face.response.ErrorResponse;
import com.face.response.FaceApiException;
import com.face.response.NotDetectedException;
import com.face.response.Response;

@RestControllerAdvice
public class ErrorController {

	 /**
    *
    * @param req
    * @param ex
    * @return
    */
   @ExceptionHandler(InvalidMediaTypeException.class)
   @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
   public ErrorResponse handleInvalidMediaTypeException(HttpServletRequest req, InvalidMediaTypeException ex){
       return Response.createErrorResponse(ex);
   }
   
   /**
   *
   * @param req
   * @param ex
   * @return
   */
  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleInvalidRequestBodyException(HttpServletRequest req, ValidationException ex){
      return Response.createErrorResponse(ex);
  }
  
  /**
  *
  * @param req
  * @param ex
  * @return
  */
 @ExceptionHandler(NotDetectedException.class)
 @ResponseStatus(HttpStatus.BAD_REQUEST)
 public ErrorResponse handleNotDetectedException(HttpServletRequest req, NotDetectedException ex){
     return Response.createErrorResponse(ex);
 }
 
 /**
 *
 * @param req
 * @param ex
 * @return
 */
@ExceptionHandler(FaceApiException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public ErrorResponse handleFaceApiException(HttpServletRequest req, FaceApiException ex){
    return Response.createErrorResponse(ex);
}
   
      
}
