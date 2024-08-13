package ru.shmaykhelduo.egehelper.backend.task;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.shmaykhelduo.egehelper.backend.image.Image;

import java.util.*;

@Entity
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String text;

    private String answer;

    @OneToMany
    private List<Image> images = new ArrayList<>();

    @ManyToMany
    private List<Tag> tags = new ArrayList<>();
}
