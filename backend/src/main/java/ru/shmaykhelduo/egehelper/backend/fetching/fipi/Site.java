package ru.shmaykhelduo.egehelper.backend.fetching.fipi;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import ru.shmaykhelduo.egehelper.backend.fetching.FetchedTask;
import ru.shmaykhelduo.egehelper.backend.fetching.SSLHelper;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
public class Site {
    private final URI uri;

    public List<FetchedTask> getTasks() throws IOException {
        Document document = Jsoup.connect(uri.toURL().toString())
                .sslSocketFactory(SSLHelper.getSSLSocketFactory())
                .get();
        Elements elements = document.select(".qblock");

        return elements.stream().map(element -> {
            String text = element.select(".cell_0").text();
            String fipiId = element.id();
            if (fipiId.length() > 1) {
                fipiId = fipiId.substring(1);
            }

            FetchedTask task = new FetchedTask();
            task.setText(text);
            task.setSourceName("fipi");
            task.setSourceTaskId(fipiId);
            return task;
        }).toList();
    }
}
