package ru.shmaykhelduo.egehelper.backend.fetching;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shmaykhelduo.egehelper.backend.task.Task;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FetchedTaskSaveService {
    private final FetchedTaskRepository fetchedTaskRepository;

    public void save(FetchedTask fetchedTask) {
        Optional<FetchedTask> availableTask = fetchedTaskRepository
                .findBySourceNameAndSourceTaskId(fetchedTask.getSourceName(), fetchedTask.getSourceTaskId());

        if (availableTask.isEmpty()) {
            fetchedTaskRepository.save(fetchedTask);
            return;
        }

        FetchedTask task = availableTask.get();

        transferValues(task, fetchedTask);
        fetchedTaskRepository.save(task);
    }

    private void transferValues(Task target, Task source) {
        target.setText(source.getText());
        if (source.getAnswer() != null) {
            target.setAnswer(source.getAnswer());
        }
        target.setImages(source.getImages());
        target.setTags(source.getTags());
    }
}
