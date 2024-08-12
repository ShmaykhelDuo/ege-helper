package ru.shmaykhelduo.egehelper.backend.fetching.fipi;

import org.springframework.stereotype.Component;
import ru.shmaykhelduo.egehelper.backend.fetching.TaskSource;
import ru.shmaykhelduo.egehelper.backend.task.Task;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Component
public class FipiTaskSource implements TaskSource {
    private final URI uri = URI.create("https://ege.fipi.ru/bank/questions.php?proj=AC437B34557F88EA4115D2F374B0A07B&page=0&pagesize=1000");

    @Override
    public List<Task> getTasks() {
        Site site = new Site(uri);
        try {
            return site.getTasks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
