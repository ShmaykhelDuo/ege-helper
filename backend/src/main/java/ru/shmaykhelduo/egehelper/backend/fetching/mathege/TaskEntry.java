package ru.shmaykhelduo.egehelper.backend.fetching.mathege;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import ru.shmaykhelduo.egehelper.backend.fetching.FetchedTask;
import ru.shmaykhelduo.egehelper.backend.image.Image;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class TaskEntry {
    private final int number;
    private final String id;
    private final URI uri;

    public TaskEntry(int number, String id, URI rootUri, String uri) {
        this.number = number;
        this.id = id;
        this.uri = rootUri.resolve(uri);
    }

    public FetchedTask getTask() throws IOException {
        String tex = "";
        List<byte[]> images = new ArrayList<>();

        log.debug("Downloading task entry for number {}, id {}, uri {}", number, id, uri);
        InputStream conn = null;
        for (int i = 0; i < 5; i++) {
            try {
                conn = uri.toURL().openStream();
                break;
            } catch (IOException e) {
                log.warn("Downloading task entry for number {}, id {}, uri {} failed, attempt {} of 5", number, id, uri, i + 1, e);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if (conn == null) {
            throw new RuntimeException("failed to download task entry from " + uri);
        }

        try (ZipInputStream in = new ZipInputStream(new BufferedInputStream(conn))) {
            for (ZipEntry entry = in.getNextEntry(); entry != null; entry = in.getNextEntry()) {
                log.debug("Handling file {} for number {}, id {}, uri {}", entry.getName(), number, id, uri);

                if (entry.isDirectory()) {
                    continue;
                }

                if (entry.getName().endsWith(".tex")) {
                     tex = new String(in.readAllBytes());
                     tex = LatexHandler.extract(tex);
                    continue;
                }

                if (entry.getName().endsWith(".eps")) {
                    log.trace("Getting stream from converter for file {}", entry.getName());
                    try (InputStream input = EpsConverter.convertEps(in)) {
                        log.trace("Got stream from converter for file {}, reading...", entry.getName());
                        images.add(input.readAllBytes());
                        log.trace("Read all from file {}", entry.getName());
                    }
                }
            }
        }

        log.debug("Downloaded task entry for number {}, id {}, uri {}", number, id, uri);

        FetchedTask task = new FetchedTask();
        task.setText(tex);
        task.setImages(images.stream().map(i -> {
            Image image = new Image();
            image.setName("");
            image.setType(MediaType.IMAGE_PNG_VALUE);
            image.setImage(i);
            return image;
        }).collect(Collectors.toCollection(ArrayList::new)));

        task.setSourceName("mathege");
        task.setSourceTaskId(id);

        return task;
    }
}
