package ru.shmaykhelduo.egehelper.backend.fetching.mathege;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
public class Site {
    private final URI rootUri;

    public List<TaskGroup> getTaskGroups() throws IOException {
        Document document = Jsoup.connect(rootUri.toURL().toString()).get();
        Elements taskGroupLinks = document.select(".catalogbuttons > a");

        return taskGroupLinks.stream().map(link -> {
            String caption = link.getElementsByTag("p").first().text();
            int number = Integer.parseInt(caption);
            String uri = link.attr("href");

            return new TaskGroup(number, rootUri, uri);
        }).toList();
    }
}
