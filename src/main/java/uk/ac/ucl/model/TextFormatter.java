package uk.ac.ucl.model;

public class TextFormatter {
    // A class with static methods for text formatting

    public static String escapeHTML(String input) {
        // Escape special characters for use in HTML to avoid injection
        if (input == null) {
            return null;
        }

        return input.replace("&", "&amp;")  // Ampersand must come first as it is included in other replacements
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private static String inlineMarkdownToHTML(String input) {
        /*
        Parses a subset of Markdown syntax to HTML, including:

        (1): ***text*** ==> BOLD and ITALIC
        (2): **text**   ==> BOLD
        (3): *text*     ==> ITALIC
        (4): ```text``` ==> PRE-FORMATTED; MONOSPACE

        (1)-(3) also work with underscores.
        */

        input = input.replaceAll("\\*{3}(.*?)\\*{3}", "<b><i>$1</i></b>");  // ***text***
        input = input.replaceAll("\\*{2}(.*?)\\*{2}", "<b>$1</b>");  // **text**
        input = input.replaceAll("\\*(.*?)\\*", "<i>$1</i>");  // *text*

        input = input.replaceAll("_{3}(.*?)_{3}", "<b><i>$1</i></b>");  // ___text___
        input = input.replaceAll("_{2}(.*?)_{2}", "<b>$1</b>");  // __text__
        input = input.replaceAll("_(.*?)_", "<i>$1</i>");  // _text_

        input = input.replaceAll("`{3}(.*?)`{3}", "<span class=\"codeBlock\">$1</span>");  // ```text```
        return input;
    }

    private static String listParagraphMarkdownToHTML(String paragraph, boolean numbered) {
        StringBuilder snippet = new StringBuilder(numbered ? "<ol>" : "<ul>");
        for (String line: paragraph.split("\\r?\\n")) {
            if (numbered) {
                line = line.substring(line.indexOf('.') + 2);  // Remove "Num. "
            } else {
                line = line.substring(2);  // Remove "- "
            }

            line = inlineMarkdownToHTML(line);
            snippet.append("<li>").append(line).append("</li>");
        }

        snippet.append(numbered ? "</ol>" : "</ul>");

        return snippet.toString();
    }

    private static String preformattedMarkdownToHTML(String paragraph) {
        return paragraph.replaceAll("`{3}\\r?\\n((.*\\r?\\n)*)`{3}", "<pre>$1</pre>");
    }

    private static String blockquoteParagraphMarkdownToHTML(String paragraph) {
        String snippet = "<div class=\"blockquotes\">";

        StringBuilder blockquotesContents = new StringBuilder();

        for (String line: paragraph.split("\\r?\\n")) {
            line = line.replaceAll("^&gt;", " ");
            blockquotesContents.append(line);
        }

        snippet += inlineMarkdownToHTML(blockquotesContents.toString());
        snippet += "</div>";

        return snippet;
    }

    private static String paragraphMarkdownToHTML(String paragraph) {
        if (paragraph.matches("^(-\\s.+(\\r?\\n)?)+$")) {
            // The paragraph consists of one or more lines starting with "- " ==> BULLETED LIST
            return listParagraphMarkdownToHTML(paragraph, false);
        }

        if (paragraph.matches("^([0-9]+\\.\\s.+(\\r?\\n)?)+$")) {
            // The paragraph consists of one or more lines starting with "Num. " ==> NUMBERED LIST
            return listParagraphMarkdownToHTML(paragraph, true);
        }

        if (paragraph.matches("^(&gt;\\s*?.*(\\r?\\n)?)+$")) {
            // The paragraph consists of one or more lines starting with ">" ==> BLOCKQUOTES
            return blockquoteParagraphMarkdownToHTML(paragraph);
        }

        if (paragraph.matches("`{3}\\r?\\n((.*\\r?\\n)*)`{3}")) {
            // The paragraph starts and ends with the line "```" ==> PRE-FORMATTED; MONOSPACE
            return preformattedMarkdownToHTML(paragraph);
        }

        return inlineMarkdownToHTML(paragraph);
    }

    public static String markdownToHTML(String input) {
        /*
        Parses a subset of Markdown syntax to HTML, including:

        (1): ***text*** ==> BOLD and ITALIC
        (2): **text**   ==> BOLD
        (3): *text*     ==> ITALIC
        (4): ```text``` ==> CODE BLOCK (in-line or multi-line)
        (5):
             - item
             - item     ==> BULLETED LIST
             - item

        (6):
             1. item
             1. item    ==> NUMBERED LIST
             1. item

        (7):
            > Some text
            > in a      ==> QUOTE BLOCK
            > quote

        (1)-(3) work with underscores too.
        */

        StringBuilder resultHTML = new StringBuilder();

        for (String paragraph : input.split("(\\r?\\n){2,}")) {
            resultHTML.append("<p>");
            resultHTML.append(paragraphMarkdownToHTML(paragraph));
            resultHTML.append("</p>");
        }

        return resultHTML.toString();
    }
}
