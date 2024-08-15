package ru.shmaykhelduo.egehelper.backend.error;

import org.springframework.http.HttpStatusCode;

import java.util.List;

public record ValidationErrorResponse(HttpStatusCode code, String message, List<FieldErrorRecord> fieldErrors) {
}
