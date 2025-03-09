package uk.ac.ucl.model;

// Represents a note.
// Each note has a title and contents.

import java.util.ArrayList;

public class Note extends IndexEntry {
    private String contents;

    public Note(String title, String contents) {
        super(title);
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public IndexEntry getEntryByParsedPath(ArrayList<String> parsedPath) {
        if (parsedPath.isEmpty()) {
            return this;
        }

        return null;
    }
}

