package com.face.response;

import javax.validation.ValidationException;

public class FaceApiInvalidRequestException extends ValidationException {

	public FaceApiInvalidRequestException(String message) {
        super(message);
    }
}
