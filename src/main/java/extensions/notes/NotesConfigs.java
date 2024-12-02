package extensions.notes;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static core.logic.BorderConfigs.border;
import static core.logic.BorderConfigs.marginBorder;
import static core.logic.DisplayManager.*;
import static java.lang.System.out;

public class NotesConfigs {
    @Getter
    private String title;
    @Setter
    @Getter
    private String content;

    @Getter
    private static List<NotesConfigs> notesList = new ArrayList<>();

    public NotesConfigs(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void saveToFile() {
        if (title == null || title.isEmpty()) {
            message("Title cannot be empty!", "red", 58, 0, out::print);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(title + ".txt"))) {
            writer.write(content);
            message("Note saved successfully!", "blue", 58, 0, out::print);
            notesList.add(this);
        } catch (IOException e) {
            marginBorder();
            errorAscii();
            message("Error saving the note: " + e.getMessage(), "red", 58, 0, out::print);
        }
    }

    public static @Nullable NotesConfigs readFromFile(String title) {
        File file = new File(title + ".txt");

        if (!file.exists()) {
            message("File not found!", "red", 58, 0, out::print);
            return null;
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            errorAscii();
            message("Error reading the note: " + e.getMessage(), "red", 58, 0, out::print);
            return null;
        }

        message("Note read successfully!", "blue", 58, 0, out::print);
        return new NotesConfigs(title, content.toString());
    }

    public static boolean deleteNoteFile(String title) {
        File file = new File(title + ".txt");

        if (!file.exists()) {
            message("File not found!", "red", 58, 0, out::print);
            return false;
        }

        if (file.delete()) {
            message("Note deleted successfully!", "blue", 58, 0, out::print);
            notesList.removeIf(note -> note.getTitle().equals(title));
            return true;
        } else {
            errorAscii();
            message("Error deleting the note!", "red", 58, 0, out::print);
            return false;
        }
    }

    public static void sortNotesByTitle() {
        Collections.sort(notesList, Comparator.comparing(NotesConfigs::getTitle));
        message("Notes sorted by title.", "blue", 58, 0, out::print);
        displayNotesList();
    }

    public static void sortNotesByContent() {
        Collections.sort(notesList, Comparator.comparing(NotesConfigs::getContent));
        message("Notes sorted by content.", "blue", 58, 0, out::print);
        displayNotesList();
    }

    public static void displayNotesList() {
        if (notesList.isEmpty()) {
            message("No notes available.", "red", 58, 0, out::print);
            return;
        }
        message("List of Notes:", "green", 58, 0, out::print);
        for (NotesConfigs note : notesList) {
            message("Title: " + note.getTitle(), "white", 58, 0, out::print);
            message("Content: " + note.getContent(), "white", 58, 0, out::print);
        }
    }

    public static String[] notepadLogo = {
            "ooooo      ooo               .                      ",
            "`888b.     `8'             .o8                      ",
            " 8 `88b.    8   .ooooo.  .o888oo  .ooooo.   .oooo.o ",
            " 8   `88b.  8  d88' `88b   888   d88' `88b d88(  \"8 ",
            " 8     `88b.8  888   888   888   888ooo888 `\"Y88b.  ",
            " 8       `888  888   888   888 . 888    .o o.  )88b ",
            "o8o        `8  `Y8bod8P'   \"888\" `Y8bod8P' 8\"\"888P' "
    };

}
