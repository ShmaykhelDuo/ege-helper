package ru.shmaykhelduo.egehelper.backend.user;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserResponse(@NotNull UUID id, @NotNull String username) {
}
