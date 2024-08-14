package ru.shmaykhelduo.egehelper.backend.task;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shmaykhelduo.egehelper.backend.error.ApiException;
import ru.shmaykhelduo.egehelper.backend.image.Image;
import ru.shmaykhelduo.egehelper.backend.image.ImageRepository;
import ru.shmaykhelduo.egehelper.backend.paging.PageDto;
import ru.shmaykhelduo.egehelper.backend.user.User;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final ImageRepository imageRepository;
    private final TaskMapper taskMapper;

    public PageDto<TaskResponse> getTasks(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<Task> page = taskRepository.findAll(pageable);

        return new PageDto<>(
                taskMapper.toResponseList(page.getContent()),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber()
        );
    }

    public TaskResponse getTask(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Task not found"));
        return taskMapper.toResponse(task);
    }

    public TaskResponse createTask(TaskRequest request, User user) {
        UserTask task = new UserTask();
        setTaskFromResponse(request, task);
        task.setUser(user);

        task = taskRepository.save(task);
        return taskMapper.toResponse(task);
    }

    public TaskResponse updateTask(UUID id, TaskRequest request, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Task not found"));

        if (!(task instanceof UserTask userTask) || !userTask.getUser().equals(user)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Task is owned by another user");
        }

        setTaskFromResponse(request, task);

        task = taskRepository.save(task);
        return taskMapper.toResponse(task);
    }

    private void setTaskFromResponse(TaskRequest request, Task task) {
        task.setText(request.text());
        task.setAnswer(request.answer());

        List<Tag> tags = tagRepository.findAllById(request.tagIds());
        task.setTags(tags);

        List<Image> images = imageRepository.findAllById(request.imageIds());
        task.setImages(images);
    }

    public void deleteTask(UUID id, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Task not found"));

        if (!(task instanceof UserTask userTask) || !userTask.getUser().equals(user)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Task is owned by another user");
        }

        taskRepository.deleteById(id);
    }
}
