package ru.shmaykhelduo.egehelper.backend.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiError(ApiException e) {
        ApiErrorResponse response = new ApiErrorResponse(e.getCode(), e.getMessage());
        return new ResponseEntity<>(response, e.getCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidationError(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        ObjectError objectError = bindingResult.getGlobalError();
        String objectErrorString = objectError != null ? objectError.getDefaultMessage() : null;
        List<FieldErrorRecord> errors = bindingResult.getFieldErrors().stream()
                .map(error -> new FieldErrorRecord(error.getField(), error.getDefaultMessage()))
                .toList();

        return new ValidationErrorResponse(HttpStatus.BAD_REQUEST, objectErrorString, errors);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error");
    }
}
