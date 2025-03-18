package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.Comparator;

public class NoteSorter {
    private static void sortAtoZ(ArrayList<Note> notes) {
        notes.sort((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()));
    }

    private static void sortZtoA(ArrayList<Note> notes) {
        notes.sort((o1, o2) -> o2.getTitle().compareToIgnoreCase(o1.getTitle()));
    }

    private static void sortShortestToLongest(ArrayList<Note> notes) {
        notes.sort(
                Comparator.comparingInt(Note::charCount)
        );
    }

    private static void sortLongestToShortest(ArrayList<Note> notes) {
        notes.sort(
                (o1, o2) -> Integer.compare(o2.charCount(), o1.charCount())
        );
    }

    public static void sort(ArrayList<Note> notes, NoteOrdering ordering) {
        if (ordering == null) {
            return;
        }

        switch (ordering) {
            case NoteOrdering.A_TO_Z -> sortAtoZ(notes);
            case NoteOrdering.Z_TO_A -> sortZtoA(notes);
            case NoteOrdering.SHORTEST_TO_LONGEST -> sortShortestToLongest(notes);
            case NoteOrdering.LONGEST_TO_SHORTEST -> sortLongestToShortest(notes);
            default -> {}
        }
    }
}
