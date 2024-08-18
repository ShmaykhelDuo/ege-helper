package ru.shmaykhelduo.egehelper.backend.fetching;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.shmaykhelduo.egehelper.backend.task.Task;

@Entity
@Table(name = "fetched_tasks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"source_name", "source_task_id"})})
@PrimaryKeyJoinColumn(name = "task_id")
@Getter
@Setter
public class FetchedTask extends Task {
    @Column(name = "source_name")
    @NotNull
    private String sourceName;

    @Column(name = "source_task_id")
    @NotNull
    private String sourceTaskId;
}
