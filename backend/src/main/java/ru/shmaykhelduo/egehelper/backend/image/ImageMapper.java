package ru.shmaykhelduo.egehelper.backend.image;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageMetadata toMetadata(Image image);
}
