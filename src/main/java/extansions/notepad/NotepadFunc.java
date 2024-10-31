package extansions.notepad;

import lombok.Getter;
import lombok.Setter;

import java.io.*;

import static core.layout.BorderFunc.marginBigBorder;
import static core.layout.DisplayManager.errorAscii;
import static core.layout.DisplayManager.message;


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
            message("Title cannot be empty!", "red", 58);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(title + ".txt"))) {
            writer.write(content);
            message("Note saved successfully!", "purple", 58);
        } catch (IOException e) {
            marginBigBorder();
            errorAscii();
            message("Error saving the note: " + e.getMessage(), "red", 58);
        }
    }

    public static NotepadFunc readFromFile(String title) {
        File file = new File(title + ".txt");

        if (!file.exists()) {
            message("File not found!", "red", 58);
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
            message("Error reading the note: " + e.getMessage(), "red", 58);
            return null;
        }

        message("Note read successfully!", "purple", 58);
        return new NotepadFunc(title, content.toString());
    }

    public static boolean deleteNoteFile(String title) {
        File file = new File(title + ".txt");

        if (!file.exists()) {
            message("File not found!", "red", 58);
            return false;
        }

        if (file.delete()) {
            message("Note deleted successfully!", "purple", 58);
            return true;
        } else {
            errorAscii();
            message("Error deleting the note!", "red", 58);
            return false;
        }
    }
}
