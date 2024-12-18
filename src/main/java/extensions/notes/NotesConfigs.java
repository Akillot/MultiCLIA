package extensions.notes;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.message;
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
            message("Title cannot be empty!",systemDefaultRed,58,0,out::print);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(title + ".txt"))) {
            writer.write(content);
            message("Note saved successfully!",systemDefaultColor,58,0,out::print);
            notesList.add(this);
        } catch (IOException e) {
            marginBorder();
            errorAscii();
            message("Error saving the note: " + e.getMessage(),systemDefaultRed,58,0,out::print);
        }
    }

    public static @Nullable NotesConfigs readFromFile(String title) {
        File file = new File(title + ".txt");

        if (!file.exists()) {
            message("File not found!",systemDefaultRed,58,0,out::print);
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
            message("Error reading the note: " + e.getMessage(),systemDefaultRed,58,0,out::print);
            return null;
        }

        message("Note read successfully!",systemDefaultColor,58,0,out::print);
        return new NotesConfigs(title, content.toString());
    }

    public static boolean deleteNoteFile(String title) {
        File file = new File(title + ".txt");

        if (!file.exists()) {
            message("File not found!",systemDefaultRed,58,0,out::print);
            return false;
        }

        if (file.delete()) {
            message("Note deleted successfully!",systemDefaultColor,58,0,out::print);
            notesList.removeIf(note -> note.getTitle().equals(title));
            return true;
        } else {
            errorAscii();
            message("Error deleting the note!",systemDefaultRed,58,0,out::print);
            return false;
        }
    }

    public static void sortNotesByTitle() {
        notesList.sort(Comparator.comparing(NotesConfigs::getTitle));
        message("Notes sorted by title.",systemDefaultColor,58,0,out::print);
        displayNotesList();
    }

    public static void sortNotesByContent() {
        notesList.sort(Comparator.comparing(NotesConfigs::getContent));
        message("Notes sorted by content.",systemDefaultColor,58,0,out::print);
        displayNotesList();
    }

    public static void displayNotesList() {
        if (notesList.isEmpty()) {
            message("No notes available.",systemDefaultRed,58,0,out::print);
            return;
        }
        message("List of Notes:",systemDefaultGreen,58,0,out::print);
        for (NotesConfigs note : notesList) {
            message("Title: " + note.getTitle(), systemDefaultWhite, 58, 0,out::print);
            message("Content: " + note.getContent(),systemDefaultWhite,58,0,out::print);
        }
    }
/*
    public static String[] notepadLogo = {
            "ooooo      ooo               .                      ",
            "`888b.     `8'             .o8                      ",
            " 8 `88b.    8   .ooooo.  .o888oo  .ooooo.   .oooo.o ",
            " 8   `88b.  8  d88' `88b   888   d88' `88b d88(  \"8 ",
            " 8     `88b.8  888   888   888   888ooo888 `\"Y88b.  ",
            " 8       `888  888   888   888 . 888    .o o.  )88b ",
            "o8o        `8  `Y8bod8P'   \"888\" `Y8bod8P' 8\"\"888P' "
    };
 */
}
