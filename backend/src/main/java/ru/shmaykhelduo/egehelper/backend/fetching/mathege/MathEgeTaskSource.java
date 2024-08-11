package ru.shmaykhelduo.egehelper.backend.fetching.mathege;


import org.springframework.stereotype.Component;
import ru.shmaykhelduo.egehelper.backend.fetching.TaskSource;
import ru.shmaykhelduo.egehelper.backend.task.Task;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Component
public class MathEgeTaskSource implements TaskSource {
    private static final URI ROOT_URI = URI.create("https://prof.mathege.ru/");

    @Override
    public List<Task> getTasks() {
        Site site = new Site(ROOT_URI);

        try {
            return site.getTaskGroups()
                    .stream().flatMap(taskGroup -> {
                        try {
                            return taskGroup.getTaskEntries().stream();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).map(taskEntry -> {
                        try {
                            return taskEntry.getTask();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
