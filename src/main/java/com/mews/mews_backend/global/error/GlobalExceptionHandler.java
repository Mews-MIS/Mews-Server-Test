package com.mews.mews_backend.global.error;

import com.mews.mews_backend.global.error.exception.BaseException;
import com.mews.mews_backend.global.error.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * More detailed exception comes first
     * 더 자세한 exception 순으로 정렬
     */


    /**
     *  모델 바인딩 및 @Valid 실패 시 발생하는 에러 처리
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error("handleBindException", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), e.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    /**
     * ErrorCode 를 담고 있는 BaseException 처리
     */
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        log.error("handleBaseException", e);
        ErrorResponse errorResponse = ErrorResponse.of(e.getStatus().getCode(), e.getMessage());
        return ResponseEntity.status(e.getStatus().getHttpStatus())
                .body(errorResponse);
    }


    /**
     * 나머지 예외 처리 (Exception 클래스를 부모로 두는 모든 자식 예외들)
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("handleException", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

}