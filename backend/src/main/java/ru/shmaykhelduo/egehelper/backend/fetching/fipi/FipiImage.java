package ru.shmaykhelduo.egehelper.backend.fetching.fipi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import ru.shmaykhelduo.egehelper.backend.fetching.SSLHelper;
import ru.shmaykhelduo.egehelper.backend.image.Image;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@Slf4j
public class FipiImage {
    private final URI uri;

    public FipiImage(URI rootUri, String uri) {
        this.uri = rootUri.resolve(uri);
    }

    public Image download() throws IOException {
        InputStream conn = null;
        for (int i = 0; i < 5; i++) {
            try {
                HttpsURLConnection urlConnection = (HttpsURLConnection) uri.toURL().openConnection();
                urlConnection.setSSLSocketFactory(SSLHelper.getSSLSocketFactory());
                conn = urlConnection.getInputStream();
                break;
            } catch (IOException e) {
                log.warn("Downloading image uri {} failed, attempt {} of 5", uri, i + 1, e);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        if (conn == null) {
            throw new RuntimeException("failed to download image from " + uri);
        }

        Image image = new Image();
        image.setName("");
        image.setType(MediaType.IMAGE_PNG_VALUE);
        try (InputStream in = new BufferedInputStream(conn)) {
            image.setImage(in.readAllBytes());
        }

        return image;
    }
}
