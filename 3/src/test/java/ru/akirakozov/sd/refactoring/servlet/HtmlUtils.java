package ru.akirakozov.sd.refactoring.servlet;

public class HtmlUtils {
    private static final String START_HTML_WRAPPER = "<html><body>\n";
    private static final String END_HTML_WRAPPER = "</body></html>\n";

    public static String wrapResult(String res) {
        return START_HTML_WRAPPER + res + END_HTML_WRAPPER;
    }

    public static String buildResultString(Integer n) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            result.append("product")
                    .append(i)
                    .append('\t')
                    .append(i)
                    .append("</br>")
                    .append('\n');
        }
        return result.toString();
    }
}
