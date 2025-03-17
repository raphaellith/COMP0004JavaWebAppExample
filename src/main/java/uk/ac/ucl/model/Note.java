package uk.ac.ucl.model;

public class Note extends IndexEntry {
    // Represents a note, which has a title and contains textual contents
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

