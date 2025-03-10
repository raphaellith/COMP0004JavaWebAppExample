package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class IndexEntry {
    private String title;

    public IndexEntry(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public abstract IndexEntry getEntryByParsedPath(ArrayList<String> parsedPath);

    public IndexEntry getEntryByPath(String path) {
        String[] parsedPathArray = path.split("/");
        ArrayList<String> parsedPath = new ArrayList<>(Arrays.asList(parsedPathArray));
        return getEntryByParsedPath(parsedPath);
    }
}
