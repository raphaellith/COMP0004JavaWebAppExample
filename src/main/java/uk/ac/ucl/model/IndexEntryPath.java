package uk.ac.ucl.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class IndexEntryPath {
    /*
    Represents a path that points to an IndexEntry. A path can be represented in two forms: parsed or unparsed.

    An unparsed path is a string that takes the following form:

    - For an index: (e.g.) [Root index name]/[Sub-index title]/[Sub-sub-index title]
    - For a note: (e.g.) [Root index name]/[Sub-index title]/![Note title]

    Note the exclamation mark used to indicate that a path points to a note, not an index.

    A parsed path is an ArrayList of strings, each of which is the title of an index or a note:

    - For an index: (e.g.)
        0. [Root index name],
        1. [Sub-index title],
        2. [Sub-sub-index title]

    - For a note: (e.g.)
        0. [Root index name],
        1. [Sub-index title],
        2. ![Note title]

    Once again note the exclamation mark.

    A path that does not begin with [Root index name] can also be represented as an IndexEntryPath. This is useful for
    evaluating the path, during which one element of the parsed form is read at a time.
    */

    private final ArrayList<String> parsed;

    public IndexEntryPath(ArrayList<String> parsed) {
        this.parsed = parsed;
    }

    public IndexEntryPath(String unparsed) {
        this.parsed = parse(unparsed);
    }

    private ArrayList<String> parse(String unparsed) {
        String[] parsedPathArray = unparsed.split("/");
        return new ArrayList<>(Arrays.asList(parsedPathArray));
    }

    private ArrayList<String> getParsed() {
        return parsed;
    }

    private String getUnparsed() {
        return String.join("/", parsed);
    }

    @Override
    public String toString() {
        return getUnparsed();
    }

    public boolean isEmpty() {
        return parsed.isEmpty();
    }

    public String removeFirst() {
        return parsed.removeFirst();
    }

    public String getTitle() {
        String title = parsed.getLast();
        if (title.startsWith("!")) {
            return title.substring(1);
        } else {
            return title;
        }
    }

    public boolean isNote() {
        return parsed.getLast().startsWith("!");
    }

    public IndexEntryPath copy() {  // Deep copy
        return new IndexEntryPath(new ArrayList<>(parsed));
    }

    public IndexEntryPath getParentPath() {
        IndexEntryPath parentPath = copy();
        parentPath.getParsed().removeLast();
        return parentPath;
    }

    public IndexEntryPath getIndexChildPath(String childTitle) {
        // "If this index had a sub-index with the title childTitle, what would its path be?"
        IndexEntryPath childPath = copy();
        childPath.getParsed().add(childTitle);
        return childPath;
    }

    public IndexEntryPath getNoteChildPath(String childTitle) {
        // "If this index contained a note with the title childTitle, what would its path be?"
        return getIndexChildPath("!" + childTitle);
    }

    public String getURLEncoding() {
        // Special characters in paths have to be encoded before they are used in URLs
        return URLEncoder.encode(getUnparsed(), StandardCharsets.UTF_8);
    }
}
