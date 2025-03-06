package uk.ac.ucl.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;

public class Index {
    private ArrayList<Note> notes;

    public Index() {
        this.notes = new ArrayList<>();
    }

    public void readFrom(String notesDataFilePath) {
        // Overwrites this.notes with the data from the .json file, specified in notesDataFilePath.

        ObjectMapper mapper = new ObjectMapper();

        this.notes.clear();

        try {
            List<Map<String, String>> readJSON = mapper.readValue(
                    new File(notesDataFilePath),
                    mapper.getTypeFactory().constructCollectionType(List.class, Map.class)
            );

            for (Map<String, String> dict : readJSON) {
                this.notes.add(new Note(dict.get("name"), dict.get("contents")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public Note getNote(String name) {
        for (Note n: getNotes()) {
            if (n.getTitle().equals(name)) {
                return n;
            }
        }
        return new Note("Can't find note", "Can't find note");
    }
}
