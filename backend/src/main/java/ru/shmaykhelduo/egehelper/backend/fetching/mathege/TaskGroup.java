package ru.shmaykhelduo.egehelper.backend.fetching.mathege;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskGroup {
    private static final Pattern TEX_DOWNLOAD_LINK_PATTERN = Pattern.compile(".*'(.*)'.*");
    private final int number;
    private final URI rootUri;
    private final URI uri;

    public TaskGroup(int number, URI rootUri, String uri) {
        this.number = number;
        this.rootUri = rootUri;
        uri += "&filter=&limit=1000";
        this.uri = rootUri.relativize(URI.create(uri));
    }

    public List<TaskEntry> getTaskEntries() throws IOException {
        Document document = Jsoup.connect(uri.toURL().toString()).get();
        Elements tasks = document.select(".task");

        return tasks.stream().map(task -> {
            String texLinkText = task.select(".downloadTex").first().attr("onclick");
            Matcher matcher = TEX_DOWNLOAD_LINK_PATTERN.matcher(texLinkText);

            matcher.find();
            String link = matcher.group(1);

            return new TaskEntry(number, rootUri, link);
        }).toList();
    }
}
