package com.brichev.exception;

public class ErrorCodes {
    public static final String INVALID_PARAMETER = "error.invalid_parameter";

    public static final String NOT_ENOUGH_MONEY = "error.not_enough_money";

    public static final String NOT_ENOUGH_STOCKS = "error.not_enough_stocks";

    public static final String REQUEST_ERROR = "error.request_error";

    public static final String EXCHANGE_FAILED = "error.exchange_failed";

    public static Error getError(String errorCode) {
        Error error = new Error();
        error.setError(errorCode);
        return error;
    }

    private ErrorCodes() {
    }
}
