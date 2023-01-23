package com.mews.mews_backend.global.Exception;

public class ServerException extends RuntimeException {

    private CustomErrorCode customErrorCode;
    private String detaliMessage;

    public ServerException(CustomErrorCode customErrorCode) {
        super(customErrorCode.getStatusMessage()); // runtimeException
        this.customErrorCode = customErrorCode;
        this.detaliMessage = customErrorCode.getStatusMessage();
    }

    public ServerException(CustomErrorCode customErrorCode, String detaliMessage) {
        super(detaliMessage); // runtimeException
        this.customErrorCode = customErrorCode;
        this.detaliMessage = detaliMessage;
    }
}
