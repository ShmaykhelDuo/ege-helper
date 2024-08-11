package ru.shmaykhelduo.egehelper.backend.fetching.mathege;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class EpsConverter {
    public static InputStream convertEps(InputStream inputStream) throws IOException {
        Process process = new ProcessBuilder("convert", "eps:-", "png:-")
                .start();
        BufferedInputStream out = new BufferedInputStream(process.getInputStream());
        inputStream.transferTo(process.getOutputStream());

        return out;
    }
}
