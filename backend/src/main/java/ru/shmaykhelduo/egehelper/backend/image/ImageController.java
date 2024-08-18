package ru.shmaykhelduo.egehelper.backend.image;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("images/")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public ImageMetadata createImage(@RequestParam("file") @NotNull MultipartFile file) throws IOException {
        return imageService.createImage(file);
    }

    @GetMapping("{id}/download")
    @SecurityRequirements
    public ResponseEntity<Resource> downloadImage(@PathVariable @NotNull UUID id) {
        return imageService.downloadImage(id);
    }

    @PutMapping("{id}")
    public ImageMetadata updateMetadata(@PathVariable @NotNull UUID id, @RequestBody ImageMetadata metadata) {
        return imageService.updateMetadata(id, metadata);
    }

    @GetMapping("{id}")
    public ImageMetadata getMetadata(@PathVariable @NotNull UUID id) {
        return imageService.getMetadata(id);
    }

    @DeleteMapping("{id}")
    public void deleteImage(@PathVariable @NotNull UUID id) {
        imageService.deleteImage(id);
    }
}
