package ru.shmaykhelduo.egehelper.backend.fetching.mathege;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class EpsConverter {
    public static InputStream convertEps(InputStream inputStream) throws IOException {
        Process process = new ProcessBuilder("convert", "-size", "1000x", "eps:-", "png:-")
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start();
        BufferedInputStream out = new BufferedInputStream(process.getInputStream());
        try (OutputStream outputStream = process.getOutputStream()) {
            inputStream.transferTo(outputStream);
        }

        return out;
    }
}
