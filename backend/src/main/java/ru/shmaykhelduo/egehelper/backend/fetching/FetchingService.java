package ru.shmaykhelduo.egehelper.backend.fetching;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.shmaykhelduo.egehelper.backend.task.Task;
import ru.shmaykhelduo.egehelper.backend.task.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FetchingService {
    private final List<TaskSource> taskSources;
    private final TaskRepository taskRepository;

    @Scheduled(cron = "${fetching.schedule}")
    public void fetchTasks() {
        for (TaskSource taskSource : taskSources) {
            List<Task> tasks = taskSource.getTasks();
            taskRepository.saveAll(tasks);
        }
    }
}
