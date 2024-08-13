package ru.shmaykhelduo.egehelper.backend.fetching.fipi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.shmaykhelduo.egehelper.backend.task.Task;

@Entity
@Getter
@Setter
public class FipiTask extends Task {
    @Column(unique = true)
    @NotNull
    private String fipiId;
}
