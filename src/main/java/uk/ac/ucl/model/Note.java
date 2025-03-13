package uk.ac.ucl.model;

// Represents a note.
// Each note has a title and contents.

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
    public IndexEntry getEntryByPath(IndexEntryPath path) {
        if (path.isEmpty()) {
            return this;
        }

        return null;
    }
}

