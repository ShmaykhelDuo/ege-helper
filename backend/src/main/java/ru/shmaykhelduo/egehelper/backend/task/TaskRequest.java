package ru.shmaykhelduo.egehelper.backend.task;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record TaskRequest(@NotNull String text, String answer, List<UUID> imageIds, List<UUID> tagIds) {
}
