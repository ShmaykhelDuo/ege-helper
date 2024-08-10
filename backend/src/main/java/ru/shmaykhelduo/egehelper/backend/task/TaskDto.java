package ru.shmaykhelduo.egehelper.backend.task;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record TaskDto(UUID id, @NotNull String text, String answer, List<UUID> images, List<String> tags) {
}
