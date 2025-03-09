package uk.ac.ucl.model;

// The main model used to handle operations of the program.
// The Model object has access to the main Index object.

public class Model {
    private Index index;

    public Model(String notesDataFilePath) {
        this.index = new Index();
        this.index.readFrom(notesDataFilePath);
    }

    public Index getIndex() {
        return this.index;
    }
}
