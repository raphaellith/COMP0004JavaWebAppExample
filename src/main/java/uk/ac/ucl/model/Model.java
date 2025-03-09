package uk.ac.ucl.model;

// The main model used to handle operations of the program.
// The Model object has access to the main Index object.

import java.util.ArrayList;
import java.util.Arrays;

public class Model {
    private Index index;
    private String rootIndexName;

    public Model(String rootIndexName, String notesDataFilePath) {
        this.rootIndexName = rootIndexName;
        this.index = new Index(rootIndexName);
        this.index.readFrom(notesDataFilePath);
    }

    public Model(String notesDataFilePath) {
        this("root", notesDataFilePath);
    }

    public Index getIndex() {
        return this.index;
    }

    public String getRootIndexName() {
        return this.rootIndexName;
    }

    public IndexEntry getEntryByParsedPath(ArrayList<String> parsedPath) {
        String firstSubstring = parsedPath.removeFirst();

        if (!firstSubstring.equals(rootIndexName)) {
            return null;
        }

        return index.getEntryByParsedPath(parsedPath);
    }

    public IndexEntry getEntryByPath(String path) {
        String[] parsedPathArray = path.split("/");
        ArrayList<String> parsedPath = new ArrayList<>(Arrays.asList(parsedPathArray));
        return getEntryByParsedPath(parsedPath);
    }
}
