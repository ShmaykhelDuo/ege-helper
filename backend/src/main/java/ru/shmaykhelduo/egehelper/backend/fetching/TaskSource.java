package ru.shmaykhelduo.egehelper.backend.fetching;

import java.util.stream.Stream;

public interface TaskSource {
    Stream<FetchedTask> getTasks();
}
