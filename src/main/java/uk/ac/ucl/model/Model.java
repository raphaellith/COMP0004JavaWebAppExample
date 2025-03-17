package uk.ac.ucl.model;

// The main model used to handle operations of the program.
// The Model object has access to the main Index object.

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;
import java.util.regex.Pattern;

public class Model {
    private final Index index;
    private final String notesDataFilePath;
    private final String rootIndexName;
    private final JsonNode rootJsonNode;

    public Model(String rootIndexName, String notesDataFilePath) {
        this.rootIndexName = rootIndexName;
        this.notesDataFilePath = notesDataFilePath;

        this.index = new Index(rootIndexName);
        this.rootJsonNode = this.index.readFrom(notesDataFilePath);
    }

    public Model(String notesDataFilePath) {
        this("Root", notesDataFilePath);  // Default root index name: "Root"
    }

    public String getRootIndexName() {
        return this.rootIndexName;
    }

    public IndexEntry getEntryByPath(IndexEntryPath path) {
        path = path.copy();
        if (!path.removeFirst().equals(rootIndexName)) {
            return null;
        }

        return index.getEntryByPath(path);
    }

    public boolean pathExists(IndexEntryPath path) {
        return getEntryByPath(path) != null;
    }

    public JsonNode getJsonNodeByPath(IndexEntryPath path) {
        path = path.copy();
        String firstSubstring = path.removeFirst();

        if (!firstSubstring.equals(rootIndexName)) {
            return null;
        }

        JsonNode currIndexNode = rootJsonNode;

        while (!path.isEmpty()) {
            String head = path.removeFirst();

            if (head.charAt(0) == '!') {  // Referring to a note
                String noteTitleToSearch = head.substring(1);

                JsonNode jsonNoteEntries = currIndexNode.path("noteEntries");
                Iterator<JsonNode> jsonNoteEntriesIterator = jsonNoteEntries.elements();

                while (jsonNoteEntriesIterator.hasNext()) {
                    JsonNode jsonNoteEntry = jsonNoteEntriesIterator.next();
                    if (jsonNoteEntry.path("noteTitle").asText().equals(noteTitleToSearch)) {
                        return jsonNoteEntry;
                    }
                }

                return null;
            } else {  // Referring to an index
                JsonNode jsonIndexEntries = currIndexNode.path("indexEntries");
                Iterator<JsonNode> jsonIndexEntriesIterator = jsonIndexEntries.elements();

                currIndexNode = null;

                while (jsonIndexEntriesIterator.hasNext()) {
                    JsonNode jsonIndexEntry = jsonIndexEntriesIterator.next();
                    if (jsonIndexEntry.path("indexTitle").asText().equals(head)) {
                        currIndexNode = jsonIndexEntry;
                    }
                }

                if (currIndexNode == null) {
                    return null;
                }
            }
        }

        return currIndexNode;
    }

    public void writeChangesToJsonFile() {
        try {
            ObjectMapper mapper = ObjectMapperFactory.getMapper();
            mapper.writeValue(new File(notesDataFilePath), rootJsonNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateNoteJsonNode(IndexEntryPath path, String newTitle, String newContents) {
        JsonNode noteEntry = getJsonNodeByPath(path);

        ((ObjectNode) noteEntry).put("noteTitle", newTitle);
        ((ObjectNode) noteEntry).put("contents", newContents);

        writeChangesToJsonFile();
    }

    public void updateIndexJsonNode(IndexEntryPath path, String newTitle) {
        JsonNode indexEntry = getJsonNodeByPath(path);

        ((ObjectNode) indexEntry).put("indexTitle", newTitle);

        writeChangesToJsonFile();
    }

    public void deleteJsonNode(IndexEntryPath path) {
        IndexEntryPath parentIndexPath = path.getParentPath();

        JsonNode jsonParentIndex = getJsonNodeByPath(parentIndexPath);

        if (path.isNote()) {
            ArrayNode jsonNoteEntries = (ArrayNode) jsonParentIndex.path("noteEntries");

            for (int i = 0; i < jsonNoteEntries.size(); i++) {
                if (jsonNoteEntries.get(i).get("noteTitle").asText().equals(path.getTitle())) {
                    jsonNoteEntries.remove(i);
                    break;
                }
            }
        } else {
            ArrayNode jsonIndexEntries = (ArrayNode) jsonParentIndex.path("indexEntries");

            for (int i = 0; i < jsonIndexEntries.size(); i++) {
                if (jsonIndexEntries.get(i).get("indexTitle").asText().equals(path.getTitle())) {
                    jsonIndexEntries.remove(i);
                    break;
                }
            }
        }

        writeChangesToJsonFile();
    }

    public void addJsonNode(IndexEntryPath path) {
        ObjectMapper mapper = ObjectMapperFactory.getMapper();

        IndexEntryPath parentIndexPath = path.getParentPath();
        JsonNode jsonParentIndex = getJsonNodeByPath(parentIndexPath);

        if (path.isNote()) {
            ArrayNode jsonNoteEntries = (ArrayNode) jsonParentIndex.path("noteEntries");

            ObjectNode newNote = mapper.createObjectNode();
            newNote.put("noteTitle", path.getTitle());
            newNote.put("contents", "");

            jsonNoteEntries.add(newNote);
        } else {
            ArrayNode jsonIndexEntries = (ArrayNode) jsonParentIndex.path("indexEntries");

            ObjectNode newIndex = mapper.createObjectNode();
            newIndex.put("indexTitle", path.getTitle());
            newIndex.set("noteEntries", mapper.createArrayNode());
            newIndex.set("indexEntries", mapper.createArrayNode());

            jsonIndexEntries.add(newIndex);
        }

        writeChangesToJsonFile();
    }

    public Optional<IndexEntryPath> getPathIfNewTitleValid(String newTitle, IndexEntryPath currentPath) {
        // Given a current path, determines if it is valid to rename this Index/Note to newTitle.
        // If it is, Optional.of(the new path) is returned.
        // If not, Optional.empty() is returned

        if (Pattern.compile("[!/]").matcher(newTitle).find()) {
            // New title must not contain "/" or "!"
            return Optional.empty();
        }

        IndexEntryPath parentPath = currentPath.getParentPath();
        IndexEntryPath potentialNewPath;

        if (currentPath.isNote()) {
            potentialNewPath = parentPath.getNoteChildPath(newTitle);
        } else {
            potentialNewPath = parentPath.getIndexChildPath(newTitle);
        }

        if (!newTitle.equals(currentPath.getTitle()) && pathExists(potentialNewPath)) {
            // If the title is edited, the new title should not be identical to other index/note titles at the same level
            return Optional.empty();
        }

        return Optional.of(potentialNewPath);
    }

    public String escapeHTML(String input) {
        if (input == null) {
            return null;
        }

        return input.replace("&", "&amp;")  // Ampersand must come first as it is included in other replacements
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
