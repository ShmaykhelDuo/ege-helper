package ru.shmaykhelduo.egehelper.backend.auth;

import jakarta.validation.constraints.NotEmpty;

public record TokenResponse(@NotEmpty String token) {
}
