package ru.shmaykhelduo.egehelper.backend.auth;

import jakarta.validation.constraints.NotEmpty;

public record SignUpRequest(@NotEmpty String username, @NotEmpty String password) {
}
