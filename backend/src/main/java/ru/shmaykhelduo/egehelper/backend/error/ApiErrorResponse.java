package ru.shmaykhelduo.egehelper.backend.error;

import org.springframework.http.HttpStatusCode;

public record ApiErrorResponse(HttpStatusCode code, String message) {
}
