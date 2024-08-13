package ru.shmaykhelduo.egehelper.backend.task;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shmaykhelduo.egehelper.backend.image.Image;
import ru.shmaykhelduo.egehelper.backend.image.ImageRepository;
import ru.shmaykhelduo.egehelper.backend.paging.PageDto;

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
        Task task = taskRepository.findById(id).orElseThrow();
        return taskMapper.toResponse(task);
    }

    public TaskResponse createTask(TaskRequest request) {
        Task task = new Task();
        populateTaskFromResponse(request, task);

        task = taskRepository.save(task);
        return taskMapper.toResponse(task);
    }

    public TaskResponse updateTask(UUID id, TaskRequest request) {
        Task task = new Task();
        task.setId(id);
        populateTaskFromResponse(request, task);

        task = taskRepository.save(task);
        return taskMapper.toResponse(task);
    }

    private void populateTaskFromResponse(TaskRequest request, Task task) {
        task.setText(request.text());
        task.setAnswer(request.answer());

        List<Tag> tags = tagRepository.findAllById(request.tagIds());
        task.setTags(tags);

        List<Image> images = imageRepository.findAllById(request.imageIds());
        task.setImages(images);
    }

    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}
