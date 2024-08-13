package ru.shmaykhelduo.egehelper.backend.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiError(ApiException e) {
        ApiErrorResponse response = new ApiErrorResponse(e.getCode(), e.getMessage());
        return new ResponseEntity<>(response, e.getCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error");
    }
}
