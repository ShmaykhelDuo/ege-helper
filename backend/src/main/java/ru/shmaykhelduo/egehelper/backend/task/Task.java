package ru.shmaykhelduo.egehelper.backend.task;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.shmaykhelduo.egehelper.backend.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String text;

    private String answer;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tasks_images",
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    private List<Image> images = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tasks_tags",
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tags = new ArrayList<>();
}
