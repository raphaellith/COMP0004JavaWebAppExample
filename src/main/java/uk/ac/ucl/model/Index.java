package uk.ac.ucl.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;

/*
Represents an index holding a collection of notes or sub-indices
*/

public class Index extends IndexEntry {
    private ArrayList<Note> noteEntries;
    private ArrayList<Index> indexEntries;

    public Index(String title) {
        super(title);
        this.noteEntries = new ArrayList<>();
        this.indexEntries = new ArrayList<>();
    }

    public void readFrom(JsonNode rootNode) {
        this.setTitle(rootNode.path("indexTitle").asText());
        this.noteEntries.clear();
        this.indexEntries.clear();

        JsonNode jsonNoteEntries = rootNode.path("noteEntries");
        Iterator<JsonNode> jsonNoteEntriesIterator = jsonNoteEntries.elements();

        while (jsonNoteEntriesIterator.hasNext()) {
            JsonNode jsonNoteEntry = jsonNoteEntriesIterator.next();
            this.noteEntries.add(
                    new Note(
                            jsonNoteEntry.path("noteTitle").asText(),
                            jsonNoteEntry.path("contents").asText()
                    )
            );
        }

        JsonNode jsonIndexEntries = rootNode.path("indexEntries");
        Iterator<JsonNode> jsonIndexEntriesIterator = jsonIndexEntries.elements();

        while (jsonIndexEntriesIterator.hasNext()) {
            JsonNode jsonIndexEntry = jsonIndexEntriesIterator.next();

            Index index = new Index("");  // placeholder title will be overwritten via recursion
            index.readFrom(jsonIndexEntry);
            this.indexEntries.add(index);
        }
    }


    public void readFrom(String notesDataFilePath) {
        // Overwrites this.notes with the data from the .json file,
        // which is specified using notesDataFilePath.

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(notesDataFilePath));
            readFrom(rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<Note> getNoteEntries() {
        return noteEntries;
    }

    public ArrayList<Index> getIndexEntries() {
        return indexEntries;
    }

    public Note getNoteByTitle(String title) {
        // Returns a note in the index by title
        // Returns null if the note is not found

        for (Note n: getNoteEntries()) {
            if (n.getTitle().equals(title)) {
                return n;
            }
        }
        return null;
    }


    public Index getIndexByTitle(String title) {
        // Returns a sub-index in the index by title
        // Returns null if the note is not found

        for (Index i: getIndexEntries()) {
            if (i.getTitle().equals(title)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public IndexEntry getEntryByParsedPath(ArrayList<String> parsedPath) {
        System.out.println("The parsed path: " + parsedPath);
        if (parsedPath.isEmpty()) {
            return this;
        }

        String firstSubstring = parsedPath.removeFirst();

        if (firstSubstring.charAt(0) == '!') {  // Referring to a note
            String noteTitleToSearch = firstSubstring.substring(1);
            Note noteObtained = getNoteByTitle(noteTitleToSearch);

            if (noteObtained == null) {
                return null;
            }

            return noteObtained.getEntryByParsedPath(parsedPath);

        } else {  // Referring to an index
            Index indexObtained = getIndexByTitle(firstSubstring);

            if (indexObtained == null) {
                return null;
            }

            return indexObtained.getEntryByParsedPath(parsedPath);
        }
    }
}
