package ru.shmaykhelduo.egehelper.backend.error;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatusCode code;
    private final String message;

    public ApiException(HttpStatusCode code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
