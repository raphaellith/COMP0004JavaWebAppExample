package uk.ac.ucl.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class IndexEntryPath {
    private ArrayList<String> parsed;

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

    public ArrayList<String> getParsed() {
        return parsed;
    }

    public String getUnparsed() {
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
        IndexEntryPath childPath = copy();
        childPath.getParsed().add(childTitle);
        return childPath;
    }

    public IndexEntryPath getNoteChildPath(String childTitle) {
        return getIndexChildPath("!" + childTitle);
    }

    public String getURLEncoding() {
        return URLEncoder.encode(getUnparsed(), StandardCharsets.UTF_8);
    }
}
