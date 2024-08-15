package ru.shmaykhelduo.egehelper.backend.fetching.fipi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.shmaykhelduo.egehelper.backend.task.Task;

@Entity
@Table(name = "fipi_tasks")
@PrimaryKeyJoinColumn(name = "task_id")
@Getter
@Setter
public class FipiTask extends Task {
    @Column(unique = true)
    @NotNull
    @Size(min = 6, max = 6)
    private String fipiId;
}
