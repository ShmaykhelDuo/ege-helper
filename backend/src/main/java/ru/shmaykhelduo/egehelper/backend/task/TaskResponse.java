package ru.shmaykhelduo.egehelper.backend.task;

import jakarta.validation.constraints.NotNull;
import ru.shmaykhelduo.egehelper.backend.image.ImageMetadata;

import java.util.List;
import java.util.UUID;

public record TaskResponse(UUID id, @NotNull String text, String answer, List<ImageMetadata> images, List<TagDto> tags) {
}
