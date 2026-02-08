import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class HtmlAnalyzer {
    public String analyze(List<String> lines) {
        Deque<String> tagStack = new ArrayDeque<>();
        int maxDepth = -1;
        String deepestText = "";

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }

            if (trimmed.startsWith("</")) {
                if (tagStack.isEmpty()) {
                    return "malformed HTML";
                }

                String tagName = getTagName(trimmed);
                if (tagName.isEmpty()) {
                    return "malformed HTML";
                }

                String expectedTagName = tagStack.pop();

                if (!tagName.equals(expectedTagName)) {
                    return "malformed HTML";
                }
            } else if (trimmed.startsWith("<")) {
                if (!trimmed.endsWith(">")) {
                    return "malformed HTML";
                }
                String tagName = getTagName(trimmed);
                if (tagName.isEmpty()) {
                    return "malformed HTML";
                }
                tagStack.push(tagName);
            } else {
                int currentDepth = tagStack.size();
                if (currentDepth > maxDepth) {
                    maxDepth = currentDepth;
                    deepestText = line.trim();
                }
            }
        }

        if (!tagStack.isEmpty()) {
            return "malformed HTML";
        }

        if (maxDepth == -1) {
            return "malformed HTML";
        }

        return deepestText;
    }

    private String getTagName(String tagLine) {
        String content = tagLine.trim();
        if (content.startsWith("</")) {
            content = content.substring(2, content.length() - 1);
        } else {
            content = content.substring(1, content.length() - 1);
        }
        return content.trim();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("URL connection error");
            return;
        }

        HtmlFetcher fetcher = new HtmlFetcher();
        List<String> htmlLines = fetcher.fetch(args[0]);

        if (htmlLines == null) {
            System.out.println("URL connection error");
            return;
        }

        HtmlAnalyzer analyzer = new HtmlAnalyzer();
        System.out.println(analyzer.analyze(htmlLines));
    }
}

class HtmlFetcher {
    public List<String> fetch(String urlString) {
        List<String> lines = new ArrayList<>();
        try {
            URL url = new URL(urlString);
            java.net.URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection instanceof HttpURLConnection) {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                int responseCode = httpConnection.getResponseCode();
                if (responseCode >= 400) {
                    return null;
                }
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            return null;
        }
        return lines;
    }
}
