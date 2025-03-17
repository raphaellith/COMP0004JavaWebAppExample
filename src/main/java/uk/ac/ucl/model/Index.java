package uk.ac.ucl.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;

public class Index extends IndexEntry {
    /*
    Represents an index. An Index holds a list of notes and a list of sub-indices.
    */

    private final ArrayList<Note> noteEntries;
    private final ArrayList<Index> indexEntries;

    public Index(String title) {
        super(title);
        this.noteEntries = new ArrayList<>();
        this.indexEntries = new ArrayList<>();
    }

    public void createDefaultNotesDataFile(File file, String rootIndexName) throws IOException {  // Called when JSON data file is not found
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

        /*
        Default content:

        {
            "indexTitle" : "[root index name]",
            "noteEntries" : [ ],
            "indexEntries" : [ ]
        }
        */

        bufferedWriter.write("{");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\"indexTitle\": \"" + rootIndexName + "\",");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\"noteEntries\": [ ],");
        bufferedWriter.newLine();
        bufferedWriter.write("\t\"indexEntries\": [ ]");
        bufferedWriter.newLine();
        bufferedWriter.write("}");

        bufferedWriter.close();
    }

    public void readFrom(JsonNode rootNode) {  // For initialisation
        this.setTitle(rootNode.path("indexTitle").asText().strip());

        this.noteEntries.clear();
        this.indexEntries.clear();

//        JsonNode jsonNoteEntries = rootNode.path("noteEntries");
//        Iterator<JsonNode> jsonNoteEntriesIterator = jsonNoteEntries.elements();
//
//        while (jsonNoteEntriesIterator.hasNext()) {
//            JsonNode jsonNoteEntry = jsonNoteEntriesIterator.next();
//            this.noteEntries.add(
//                    new Note(
//                            jsonNoteEntry.path("noteTitle").asText(),
//                            jsonNoteEntry.path("contents").asText()
//                    )
//            );
//        }

        ArrayNode jsonNoteEntries = (ArrayNode) rootNode.path("noteEntries");

        for (JsonNode jsonNoteEntry: jsonNoteEntries) {
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

    public JsonNode readFrom(String notesDataFilePath, String defaultRootIndexName) {
        // Overwrites this.noteEntries and this.indexEntries with the data from the .json file,
        // which is specified using notesDataFilePath.
        // Returns the JsonNode corresponding to this index.

        try {
            File notesDataFile = new File(notesDataFilePath);

            System.out.println(notesDataFile.exists());

            if (!notesDataFile.exists()) {
                createDefaultNotesDataFile(notesDataFile, defaultRootIndexName);
            }

            System.out.println(notesDataFile.exists());

            ObjectMapper mapper = ObjectMapperFactory.getMapper();
            JsonNode rootNode = mapper.readTree(notesDataFile);
            readFrom(rootNode);
            return rootNode;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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

        for (Note n: noteEntries) {
            if (n.getTitle().equals(title)) {
                return n;
            }
        }
        return null;
    }

    public void removeNoteByTitle(String title) {
        for (int i = 0; i < noteEntries.size(); i++) {
            Note note = noteEntries.get(i);
            if (note.getTitle().equals(title)) {
                noteEntries.remove(i);
                break;
            }
        }
    }

    public Index getIndexByTitle(String title) {
        // Returns a sub-index in the index by title
        // Returns null if the note is not found

        for (Index i: indexEntries) {
            if (i.getTitle().equals(title)) {
                return i;
            }
        }
        return null;
    }

    public void removeIndexByTitle(String title) {
        for (int i = 0; i < indexEntries.size(); i++) {
            Index index = indexEntries.get(i);
            if (index.getTitle().equals(title)) {
                indexEntries.remove(i);
                break;
            }
        }
    }

    public void addIndex(String title) {
        indexEntries.add(new Index(title));
    }

    public void addNote(String title) {
        noteEntries.add(new Note(title, ""));
    }

    @Override
    public IndexEntry getEntryByPath(IndexEntryPath path) {
        if (path.isEmpty()) {
            return this;
        }

        String firstSubstring = path.removeFirst();

        if (firstSubstring.charAt(0) == '!') {  // Referring to a note
            String noteTitleToSearch = firstSubstring.substring(1);
            Note noteObtained = getNoteByTitle(noteTitleToSearch);

            if (noteObtained == null) {
                return null;
            }

            return noteObtained.getEntryByPath(path);

        } else {  // Referring to an index
            Index indexObtained = getIndexByTitle(firstSubstring);

            if (indexObtained == null) {
                return null;
            }

            return indexObtained.getEntryByPath(path);
        }
    }
}
