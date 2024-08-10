package ru.shmaykhelduo.egehelper.backend.image;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public UUID createImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setImage(file.getBytes());

        image = imageRepository.save(image);
        return image.getId();
    }

    public Resource getImage(UUID id) {
        Image image = imageRepository.findById(id).orElseThrow();

        return new ByteArrayResource(image.getImage());
    }
}
