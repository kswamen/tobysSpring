package com.example.tobysspring.tobySrc.chapter7.a.exception;

public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException(Throwable cause) {
        super(cause);
    }
}
