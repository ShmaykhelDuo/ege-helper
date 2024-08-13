package ru.shmaykhelduo.egehelper.backend.image;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.shmaykhelduo.egehelper.backend.error.ApiException;

import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public ImageMetadata createImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setImage(file.getBytes());

        image = imageRepository.save(image);
        return imageMapper.toMetadata(image);
    }

    public Resource downloadImage(UUID id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Image not found"));

        return new ByteArrayResource(image.getImage());
    }

    public ImageMetadata updateMetadata(UUID id, ImageMetadata metadata) {
        if (!metadata.id().equals(id)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "ID mismatch");
        }

        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Image not found"));
        image.setName(metadata.name());

        image = imageRepository.save(image);
        return imageMapper.toMetadata(image);
    }

    public ImageMetadata getMetadata(UUID id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Image not found"));

        return imageMapper.toMetadata(image);
    }

    public void deleteImage(UUID id) {
        if (!imageRepository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Image not found");
        }

        imageRepository.deleteById(id);
    }
}
