package extensions.notepad;

import lombok.Getter;
import lombok.Setter;

import java.io.*;

import static core.logic.BorderFunc.marginBorder;
import static core.logic.DisplayManager.errorAscii;
import static core.logic.DisplayManager.message;
import static java.lang.System.out;


public class NotepadFunc {
    private String title;
    @Setter
    @Getter
    private String content;

    public NotepadFunc(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void saveToFile() {
        if (title == null || title.isEmpty()) {
            message("Title cannot be empty!", "red", 58,0, out::println);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(title + ".txt"))) {
            writer.write(content);
            message("Note saved successfully!", "blue", 58,0, out::println);
        } catch (IOException e) {
            marginBorder();
            errorAscii();
            message("Error saving the note: " + e.getMessage(), "red", 58,0, out::println);
        }
    }

    public static NotepadFunc readFromFile(String title) {
        File file = new File(title + ".txt");

        if (!file.exists()) {
            message("File not found!", "red", 58,0, out::println);
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
            message("Error reading the note: " + e.getMessage(), "red", 58,0, out::println);
            return null;
        }

        message("Note read successfully!", "blue", 58,0, out::println);
        return new NotepadFunc(title, content.toString());
    }

    public static boolean deleteNoteFile(String title) {
        File file = new File(title + ".txt");

        if (!file.exists()) {
            message("File not found!", "red", 58,0, out::println);
            return false;
        }

        if (file.delete()) {
            message("Note deleted successfully!", "blue", 58,0, out::println);
            return true;
        } else {
            errorAscii();
            message("Error deleting the note!", "red", 58,0, out::println);
            return false;
        }
    }

}
