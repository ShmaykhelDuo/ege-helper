package ru.shmaykhelduo.egehelper.backend.auth;

import jakarta.validation.constraints.NotEmpty;

public record SignInRequest(@NotEmpty String username, @NotEmpty String password) {
}
