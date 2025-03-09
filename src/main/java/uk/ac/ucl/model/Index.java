package uk.ac.ucl.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;

/*
Represents an index holding a collection of notes.
The index can be initialised by reading from a JSON file of the following structure:

    [
        NOTE_REPRESENTATION,
        NOTE_REPRESENTATION,
        NOTE_REPRESENTATION,
        ...
    ]

where each NOTE_REPRESENTATION is a dictionary as follows:

    {
        "title": String,
        "contents": String
    }
*/

public class Index {
    private ArrayList<Note> notes;

    public Index() {
        this.notes = new ArrayList<>();
    }

    public void readFrom(String notesDataFilePath) {
        // Overwrites this.notes with the data from the .json file,
        // which is specified using notesDataFilePath.

        ObjectMapper mapper = new ObjectMapper();

        this.notes.clear();

        try {
            List<Map<String, String>> readJSON = mapper.readValue(
                    new File(notesDataFilePath),
                    mapper.getTypeFactory().constructCollectionType(List.class, Map.class)
            );

            for (Map<String, String> dict : readJSON) {
                this.notes.add(new Note(dict.get("title"), dict.get("contents")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public Note getNote(String name) {
        // Returns a note in the index by name.
        // If the note is not found, a note saying "Note not found!" will be returned.

        for (Note n: getNotes()) {
            if (n.getTitle().equals(name)) {
                return n;
            }
        }
        return new Note("Note not found!", "An error has occurred - this note cannot be found.");
    }
}
