package com.myblog.exceptions;

public class PostServiceException extends RuntimeException {
    public PostServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
