package ru.shmaykhelduo.egehelper.backend.task;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskDto> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("{id}")
    public TaskDto getTask(@PathVariable @NotNull UUID id) {
        return taskService.getTask(id);
    }

    @PostMapping
    public TaskDto createTask(@Valid TaskDto request) {
        return taskService.createTask(request);
    }

    @PutMapping("{id}")
    public TaskDto updateTask(@PathVariable @NotNull UUID id, @Valid TaskDto request) {
        return taskService.updateTask(id, request);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable @NotNull UUID id) {
        taskService.deleteTask(id);
    }
}
