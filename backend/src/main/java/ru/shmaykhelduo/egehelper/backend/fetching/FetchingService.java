package ru.shmaykhelduo.egehelper.backend.fetching;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FetchingService {
    private final List<TaskSource> taskSources;
    private final FetchedTaskSaveService fetchedTaskSaveService;

    @Scheduled(cron = "${fetching.schedule}")
    public void fetchTasks() {
        for (TaskSource taskSource : taskSources) {
            Stream<FetchedTask> tasks = taskSource.getTasks();
            tasks.forEach(fetchedTaskSaveService::save);
        }
    }
}
