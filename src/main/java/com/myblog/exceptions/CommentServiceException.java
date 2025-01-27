package com.myblog.exceptions;

public class CommentServiceException extends RuntimeException {
    public CommentServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
