package ru.shmaykhelduo.egehelper.backend.task;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.shmaykhelduo.egehelper.backend.paging.PageDto;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public PageDto<TaskResponse> getTasks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return taskService.getTasks(page, size);
    }

    @GetMapping("{id}")
    public TaskResponse getTask(@PathVariable @NotNull UUID id) {
        return taskService.getTask(id);
    }

    @PostMapping
    public TaskResponse createTask(@Valid TaskRequest request) {
        return taskService.createTask(request);
    }

    @PutMapping("{id}")
    public TaskResponse updateTask(@PathVariable @NotNull UUID id, @Valid TaskRequest request) {
        return taskService.updateTask(id, request);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable @NotNull UUID id) {
        taskService.deleteTask(id);
    }
}
