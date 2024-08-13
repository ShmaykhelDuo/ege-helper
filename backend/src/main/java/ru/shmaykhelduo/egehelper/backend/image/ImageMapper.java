package ru.shmaykhelduo.egehelper.backend.image;

import org.mapstruct.Mapper;

@Mapper
public interface ImageMapper {
    ImageMetadata toMetadata(Image image);
}
