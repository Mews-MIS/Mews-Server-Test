package com.mews.mews_backend.global.error.exception;

import com.mews.mews_backend.global.error.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {
    private ErrorCode status;

    //
    public BaseException(ErrorCode status) {
        super(status.getMessage());
        this.status = status;
    }
}