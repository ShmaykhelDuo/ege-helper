package ru.shmaykhelduo.egehelper.backend.image;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ImageMetadata(UUID id, @NotNull String name) {
}
