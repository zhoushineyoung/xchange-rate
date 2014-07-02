package com.ebj.exception;

public class EBJException extends Exception {
    private static final long serialVersionUID = 1L;

    public EBJException() {
    }

    public EBJException(String message) {
        super(message);
    }

    public EBJException(Throwable cause) {
        super(cause);
    }

    public EBJException(String message, Throwable cause) {
        super(message, cause);
    }

    public EBJException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
