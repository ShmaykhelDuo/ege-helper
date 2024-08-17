package ru.shmaykhelduo.egehelper.backend.fetching.mathege;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LatexConverter {
    public static InputStream convertLatex(InputStream inputStream) throws IOException {
        Process process = new ProcessBuilder("pandoc", "-f", "latex", "-t", "html", "--mathjax")
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start();
        BufferedInputStream out = new BufferedInputStream(process.getInputStream());
        try (OutputStream outputStream = process.getOutputStream()) {
            inputStream.transferTo(outputStream);
        }

        return out;
    }
}
