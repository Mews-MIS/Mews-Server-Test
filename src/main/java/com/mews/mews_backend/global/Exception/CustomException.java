package com.mews.mews_backend.global.Exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException { // runtimeException 상속

    private CustomErrorCode customErrorCode;
    private String detaliMessage;

    public CustomException(CustomErrorCode customErrorCode) {
        super(customErrorCode.getStatusMessage()); // runtimeException
        this.customErrorCode = customErrorCode;
        this.detaliMessage = customErrorCode.getStatusMessage();
    }

    public CustomException(CustomErrorCode customErrorCode, String detaliMessage) {
        super(detaliMessage); // runtimeException
        this.customErrorCode = customErrorCode;
        this.detaliMessage = detaliMessage;
    }
}
