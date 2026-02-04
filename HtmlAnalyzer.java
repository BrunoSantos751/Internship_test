import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class HtmlAnalyzer {

    public static void main(String[] args) {
        if (args.length != 1) {// If no URL is provided, run standard example
            List<String> htmlLines = fetchHtml("http://hiring.axreng.com/internship/example1.html");
            String result = analyze(htmlLines);
            System.out.println(result);
            return;
        }

        String targetUrl = args[0];
        List<String> htmlLines = fetchHtml(targetUrl);

        if (htmlLines == null) {
            System.out.println("URL connection error");
            return;
        }

        String result = analyze(htmlLines);
        System.out.println(result);
    }

    private static List<String> fetchHtml(String urlString) {
        List<String> lines = new ArrayList<>();
        try {
            URL url = new URL(urlString);
            java.net.URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000); // 5 seconds timeout
            connection.setReadTimeout(5000);

            if (connection instanceof HttpURLConnection) {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                int responseCode = httpConnection.getResponseCode();
                if (responseCode >= 400) {
                    return null;
                }
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
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

    private static String analyze(List<String> lines) {
        Stack<String> tagStack = new Stack<>();
        int maxDepth = -1;
        String deepestText = "";

        // Flag to check if we actually found any deep text.
        // If the HTML is empty or has no text, behavior isn't explicitly defined
        // but "malformed HTML" usually applies to structure errors.

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }

            if (trimmed.startsWith("</")) {
                // Closing tag
                if (tagStack.isEmpty()) {
                    return "malformed HTML";
                }

                String tagName = getTagName(trimmed);
                String expectedTagName = tagStack.pop();

                if (!tagName.equals(expectedTagName)) {
                    return "malformed HTML";
                }
            } else if (trimmed.startsWith("<")) {
                // Opening tag
                // Assuming simplified HTML: no attributes, so just <tag>
                tagStack.push(getTagName(trimmed));
            } else {
                // Text content
                int currentDepth = tagStack.size();
                // "Se dois ou mais trechos estiverem no nível máximo de profundidade do
                // documento, o primeiro deles deve ser retornado."
                // So we only update if strictly greater.
                if (currentDepth > maxDepth) {
                    maxDepth = currentDepth;
                    deepestText = line.trim(); // The requirement says "trecho de texto" (segment of text).
                                               // Premise 8 says "ignorar espaços iniciais".
                                               // Example shows "Este é o título." without quotes.
                }
            }
        }

        if (!tagStack.isEmpty()) {
            return "malformed HTML";
        }

        return deepestText;
    }

    private static String getTagName(String tagLine) {
        // Remove <, </, >
        // Example: <div> -> div
        // Example: </div> -> div
        String content = tagLine.replace("<", "").replace(">", "").replace("/", "");
        return content.trim();
    }
}
