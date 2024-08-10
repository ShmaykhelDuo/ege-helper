package ru.shmaykhelduo.egehelper.backend.image;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
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
    public CreateImageResponse createImage(@RequestParam("file") MultipartFile file) throws IOException {
        UUID id = imageService.createImage(file);

        return new CreateImageResponse(id);
    }

    @GetMapping("{id}")
    public Resource getImage(@RequestParam("id") UUID id) {
        return imageService.getImage(id);
    }
}
