package ru.shmaykhelduo.egehelper.backend.fetching;

import ru.shmaykhelduo.egehelper.backend.task.Task;

import java.util.List;

public interface TaskSource {
    List<Task> getTasks();
}
