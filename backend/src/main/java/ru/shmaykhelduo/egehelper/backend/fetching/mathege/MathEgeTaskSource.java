package ru.shmaykhelduo.egehelper.backend.fetching.mathege;


import org.springframework.stereotype.Component;
import ru.shmaykhelduo.egehelper.backend.fetching.FetchedTask;
import ru.shmaykhelduo.egehelper.backend.fetching.TaskSource;

import java.io.IOException;
import java.net.URI;
import java.util.stream.Stream;

@Component
public class MathEgeTaskSource implements TaskSource {
    private static final URI ROOT_URI = URI.create("https://prof.mathege.ru/");

    @Override
    public Stream<FetchedTask> getTasks() {
        Site site = new Site(ROOT_URI);

        try {
            return site.getTaskGroups()
                    .parallelStream().flatMap(taskGroup -> {
                        try {
                            return taskGroup.getTaskEntries().parallelStream();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).map(taskEntry -> {
                        try {
                            return taskEntry.getTask();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
