package ru.shmaykhelduo.egehelper.backend.task;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.shmaykhelduo.egehelper.backend.paging.PageDto;
import ru.shmaykhelduo.egehelper.backend.user.User;

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
    public TaskResponse createTask(@Valid TaskRequest request, @AuthenticationPrincipal User user) {
        return taskService.createTask(request, user);
    }

    @PutMapping("{id}")
    public TaskResponse updateTask(@PathVariable @NotNull UUID id, @Valid TaskRequest request, @AuthenticationPrincipal User user) {
        return taskService.updateTask(id, request, user);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable @NotNull UUID id, @AuthenticationPrincipal User user) {
        taskService.deleteTask(id, user);
    }
}
