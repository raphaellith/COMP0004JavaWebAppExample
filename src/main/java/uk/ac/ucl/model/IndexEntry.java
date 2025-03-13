package uk.ac.ucl.model;

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

    public abstract IndexEntry getEntryByPath(IndexEntryPath path);
}
