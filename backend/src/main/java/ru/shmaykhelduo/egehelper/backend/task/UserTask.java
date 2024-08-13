package ru.shmaykhelduo.egehelper.backend.task;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import ru.shmaykhelduo.egehelper.backend.user.User;

@Entity
@Getter
@Setter
public class UserTask extends Task {
    @ManyToOne
    private User user;
}
