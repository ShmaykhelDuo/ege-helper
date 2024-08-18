package ru.shmaykhelduo.egehelper.backend.fetching.fipi;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;
import ru.shmaykhelduo.egehelper.backend.fetching.FetchedTask;
import ru.shmaykhelduo.egehelper.backend.fetching.SSLHelper;
import ru.shmaykhelduo.egehelper.backend.image.Image;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class Site {
    private final URI rootUri;
    private final URI uri;
    private static final Safelist safelist = Safelist.simpleText()
            .addTags("math", "mfenced", "mfrac", "mi", "mmultiscripts", "mn", "mo", "mover", "mpadded", "mphantom", "mprescripts", "mroot", "mrow", "ms", "semantics", "mspace", "msqrt", "mstyle", "msub", "msup", "msubsup", "mtable", "mtd", "mtext", "mtr", "munder", "munderover");
    private static final Pattern imagePattern = Pattern.compile("ShowPictureQ\\('(.*)'\\);");

    public List<FetchedTask> getTasks() throws IOException {
        Document document = Jsoup.connect(uri.toURL().toString())
                .sslSocketFactory(SSLHelper.getSSLSocketFactory())
                .get();
        Elements elements = document.select(".qblock");

        return elements.stream().map(element -> {
            String text = element.select(".cell_0").html();

            List<Image> images = new ArrayList<>();
            Matcher matcher = imagePattern.matcher(text);
            while (matcher.find()) {
                String imageUrl = matcher.group(1);
                FipiImage fipiImage = new FipiImage(rootUri, imageUrl);
                try {
                    images.add(fipiImage.download());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            text = text.replace("<m:", "<").replace("</m:", "</");
            text = Jsoup.clean(text, safelist);

            String fipiId = element.id();
            if (fipiId.length() > 1) {
                fipiId = fipiId.substring(1);
            }

            FetchedTask task = new FetchedTask();
            task.setText(text);
            task.setSourceName("fipi");
            task.setSourceTaskId(fipiId);
            task.setImages(images);
            return task;
        }).toList();
    }
}
