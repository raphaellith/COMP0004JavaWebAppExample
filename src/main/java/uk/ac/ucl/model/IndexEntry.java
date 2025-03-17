package uk.ac.ucl.model;

public abstract class IndexEntry {
    // Represents an entry of an index. Both the Index and Note classes inherit from IndexEntry.
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

    // Given a path, returns the IndexEntry it points to
    public abstract IndexEntry getEntryByPath(IndexEntryPath path);
}
