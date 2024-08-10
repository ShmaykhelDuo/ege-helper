package ru.shmaykhelduo.egehelper.backend.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<TaskDto> getTasks() {
        List<Task> tasks = taskRepository.findAll();
        return taskMapper.toDtoList(tasks);
    }

    public TaskDto getTask(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow();
        return taskMapper.toDto(task);
    }

    public TaskDto createTask(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);

        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    public TaskDto updateTask(UUID id, TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        task.setId(id);

        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}
