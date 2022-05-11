package com.brichev.exception;

public class ErrorCodes {
    public static final String INVALID_PARAMETER = "error.invalid_parameter";

    public static final String STOCK_NOT_FOUND = "error.stock_not_found";

    public static Error getError(String errorCode) {
        Error error = new Error();
        error.setError(errorCode);
        return error;
    }

    private ErrorCodes() {
    }
}
