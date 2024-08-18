package ru.shmaykhelduo.egehelper.backend.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import ru.shmaykhelduo.egehelper.backend.error.ApiException;
import ru.shmaykhelduo.egehelper.backend.image.ImageRepository;
import ru.shmaykhelduo.egehelper.backend.paging.PageDto;
import ru.shmaykhelduo.egehelper.backend.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ImageRepository imageRepository;

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    private TaskService taskService;

    private User user;
    private UUID taskId;
    private UserTask task;
    private TaskRequest taskRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(); // Initialize user as needed
        taskId = UUID.randomUUID();
        task = new UserTask(); // Initialize task as needed
        task.setUser(user);
        taskRequest = new TaskRequest("Sample text", "Sample answer", List.of(), List.of());

        taskService = new TaskService(this.taskRepository, this.tagRepository, this.imageRepository, this.taskMapper);
    }

    @Test
    void testGetTasks() {
        List<Task> tasks = List.of(task);
        Page<Task> taskPage = new PageImpl<>(tasks);
        when(taskRepository.findAll(any(Pageable.class))).thenReturn(taskPage);

        PageDto<TaskResponse> result = taskService.getTasks(0, 10);

        assertNotNull(result);
        assertEquals(1, result.totalElements());
        assertEquals(1, result.totalPages());
        assertEquals(0, result.number());
    }

    @Test
    void testGetTask_Success() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        TaskResponse result = taskService.getTask(taskId);

        assertNotNull(result);
        verify(taskRepository).findById(taskId);
    }

    @Test
    void testGetTask_NotFound() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> taskService.getTask(taskId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getCode());
        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    void testCreateTask() {
        when(taskRepository.save(any())).thenReturn(task);

        TaskResponse result = taskService.createTask(taskRequest, user);

        assertNotNull(result);
        verify(taskRepository).save(any());
    }

    @Test
    void testUpdateTask_Success() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any())).thenReturn(task);

        TaskResponse result = taskService.updateTask(taskId, taskRequest, user);

        assertNotNull(result);
        verify(taskRepository).save(any());
    }

    @Test
    void testUpdateTask_NotFound() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> taskService.updateTask(taskId, taskRequest, user));
        assertEquals(HttpStatus.NOT_FOUND, exception.getCode());
        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    void testDeleteTask_Success() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.deleteTask(taskId, user);

        verify(taskRepository).deleteById(taskId);
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> taskService.deleteTask(taskId, user));
        assertEquals(HttpStatus.NOT_FOUND, exception.getCode());
        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    void testDeleteTask_Forbidden() {
        User anotherUser = new User(); // Initialize another user
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        ApiException exception = assertThrows(ApiException.class, () -> taskService.deleteTask(taskId, anotherUser));
        assertEquals(HttpStatus.FORBIDDEN, exception.getCode());
        assertEquals("Task is owned by another user", exception.getMessage());
    }
}
