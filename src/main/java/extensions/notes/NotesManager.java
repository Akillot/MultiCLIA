package extensions.notes;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NotesManager {
    @Getter
    private static List<NotesConfigs> notesList = new ArrayList<>();

    public static void addNote(NotesConfigs note) {
        notesList.add(note);
    }

    public static NotesConfigs findNoteByTitle(String title) {
        return notesList.stream()
                .filter(note -> note.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    public static void deleteNoteByTitle(String title) {
        notesList.removeIf(note -> note.getTitle().equalsIgnoreCase(title));
    }

    public static void sortNotesByTitle() {
        notesList.sort(Comparator.comparing(NotesConfigs::getTitle));
    }

    public static void sortNotesByContent() {
        notesList.sort(Comparator.comparing(NotesConfigs::getContent));
    }
}
