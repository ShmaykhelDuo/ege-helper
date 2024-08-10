package ru.shmaykhelduo.egehelper.backend.task;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {
    TaskDto toDto(Task task);

    List<TaskDto> toDtoList(List<Task> tasks);

    Task toEntity(TaskDto dto);
}
