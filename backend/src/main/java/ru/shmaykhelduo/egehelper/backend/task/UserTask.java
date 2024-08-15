package ru.shmaykhelduo.egehelper.backend.task;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.shmaykhelduo.egehelper.backend.user.User;

@Entity
@Table(name = "user_tasks")
@PrimaryKeyJoinColumn(name = "task_id")
@Getter
@Setter
public class UserTask extends Task {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
