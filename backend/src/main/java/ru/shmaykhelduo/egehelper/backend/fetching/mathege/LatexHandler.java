package ru.shmaykhelduo.egehelper.backend.fetching.mathege;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LatexHandler {
    private static final Pattern documentPattern = Pattern.compile("\\\\begin\\{document}([\\s\\S]*)\\\\end\\{document}", Pattern.MULTILINE);
    private static final Pattern figurePattern = Pattern.compile("\\\\begin\\{wrapfigure}([\\s\\S]*)\\\\end\\{wrapfigure}", Pattern.MULTILINE);

    public static String extract(String latex) {
        Matcher matcher = documentPattern.matcher(latex);
        matcher.find();
        latex = matcher.group(1).strip();
        latex = latex.replace("\\noindent", "");
        final Matcher figureMatcher = figurePattern.matcher(latex);
        return figureMatcher.replaceAll("").strip();
    }
}
