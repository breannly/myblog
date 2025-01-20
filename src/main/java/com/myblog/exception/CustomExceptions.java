package com.myblog.exception;

public class CustomExceptions {

    public static class PostNotFoundException extends RuntimeException {
        public PostNotFoundException(String message) {
            super(message);
        }
    }

    public static class PostServiceException extends RuntimeException {
        public PostServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
