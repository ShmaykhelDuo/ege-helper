package ru.shmaykhelduo.egehelper.backend.fetching.mathege;

import ru.shmaykhelduo.egehelper.backend.image.Image;
import ru.shmaykhelduo.egehelper.backend.task.Task;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TaskEntry {
    private final int number;
    private final URI uri;

    public TaskEntry(int number, URI rootUri, String uri) {
        this.number = number;
        this.uri = rootUri.relativize(URI.create(uri));
    }

    public Task getTask() throws IOException {
        String tex = "";
        List<byte[]> images = new ArrayList<>();

        try (ZipInputStream in = new ZipInputStream(new BufferedInputStream(uri.toURL().openStream()))) {
            for (ZipEntry entry = in.getNextEntry(); entry != null; entry = in.getNextEntry()) {
                if (entry.isDirectory()) {
                    continue;
                }

                if (entry.getName().endsWith(".tex")) {
                    tex = new String(in.readAllBytes());
                    continue;
                }

                if (entry.getName().endsWith(".eps")) {
                    try (InputStream input = EpsConverter.convertEps(in)) {
                        images.add(input.readAllBytes());
                    }
                }
            }
        }

        Task task = new Task();
        task.setText(tex);
        task.setImages(images.stream().map(i -> {
            Image image = new Image();
            image.setName("");
            image.setImage(i);
            return image;
        }).toList());

        return task;
    }
}
