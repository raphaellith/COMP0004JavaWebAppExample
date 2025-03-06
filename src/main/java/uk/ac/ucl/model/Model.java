package uk.ac.ucl.model;

import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

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
