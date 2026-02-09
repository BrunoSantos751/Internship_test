EASTER_EGG_URLS

# HtmlAnalyzer

Solution for the "Internship Test" technical challenge.

The program analyzes the HTML source code from a given URL and returns the first text snippet found at the deepest level of the HTML structure.

## Requirements

- JDK 17 or higher (compatible with Java 17)

## How to Compile

Navigate to the directory containing the `HtmlAnalyzer.java` file and run:

```bash
javac HtmlAnalyzer.java
```

## How to Run

To analyze a specific URL, run the following command:

```bash
java HtmlAnalyzer <URL>
```

**Example:**
```bash
java HtmlAnalyzer http://hiring.axreng.com/internship/example1.html
```

## Program Output

The program generates only the following types of output to standard console:

1.  **Identified text snippet:** The text at the deepest level of the HTML structure.
2.  **malformed HTML:** If the HTML structure is malformed (unbalanced tags, closing tag without opening, incomplete structure).
3.  **URL connection error:** If the HTML content cannot be obtained due to a connection failure or if the URL is not provided correctly.

## Assumptions and Limitations

The solution follows the simplified assumptions described in the challenge:

-   The HTML is analyzed line by line.
-   Each line contains only one type of content (opening tag, closing tag, or text).
-   Ignores whitespace and empty lines.
-   Opening tags do not have attributes.
-   Does not use external libraries or Java XML/DOM manipulation packages.
