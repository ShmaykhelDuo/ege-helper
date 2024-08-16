package ru.shmaykhelduo.egehelper.backend.fetching.mathege;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class TaskGroup {
    private static final Pattern TEX_DOWNLOAD_LINK_PATTERN = Pattern.compile(".*'(.*)'.*");
    private final int number;
    private final URI rootUri;
    private final URI uri;

    public TaskGroup(int number, URI rootUri, String uri) {
        this.number = number;
        this.rootUri = rootUri;
        log.trace("saving root uri {}", rootUri);
        uri += "&filter=&limit=1000";
        this.uri = rootUri.resolve(uri);
        log.trace("appending uri {} to root uri {}, result is {}", uri, rootUri, this.uri);
    }

    public List<TaskEntry> getTaskEntries() throws IOException {
        log.trace("fetching task entries from {}", uri);

        Document document = null;
        for (int i = 0; i < 5; i++) {
            try {
                document = Jsoup.connect(uri.toURL().toString())
                        .followRedirects(true).get();
                break;
            } catch (SocketTimeoutException e) {
                log.warn("task entries from {} timed out, try {} of 5", uri, i + 1, e);
            }
        }
        if (document == null) {
            throw new RuntimeException("failed to fetch task entries from " + uri);
        }

        Elements tasks = document.select(".task");

        return tasks.stream().map(task -> {
            Element l = task.select(".downloadTex").first();
            String id = l.id().substring(12);

            String texLinkText = l.attr("onclick");
            Matcher matcher = TEX_DOWNLOAD_LINK_PATTERN.matcher(texLinkText);

            matcher.find();
            String link = matcher.group(1);

            return new TaskEntry(number, id, rootUri, link);
        }).toList();
    }
}
