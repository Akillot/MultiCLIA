package additional_packages.notepad;

import java.io.*;

import static ui.layout.BasicFunc.displayContent;
import static ui.layout.BorderFunc.displayMarginBigBorder;
import static ui.layout.ThemesFunc.displayErrorAscii;

public class Notepad {
    private String title;
    private String content;

    public Notepad(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void saveToFile() {
        if (title == null || title.isEmpty()) {
            displayContent("Title cannot be empty!", "red", 0);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(title + ".txt"))) {
            writer.write(content);
            displayContent("Note saved successfully!", "green", 0);
        } catch (IOException e) {
            displayMarginBigBorder();
            displayErrorAscii();
            displayContent("Error saving the note: " + e.getMessage(), "red", 0);
        }
    }

    public static Notepad readFromFile(String title) {
        File file = new File(title + ".txt");

        if (!file.exists()) {
            displayContent("File not found!", "red", 0);
            return null;
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            displayErrorAscii();
            displayContent("Error reading the note: " + e.getMessage(), "red", 0);
            return null;
        }

        displayContent("Note read successfully!", "green", 0);
        return new Notepad(title, content.toString());
    }

    public static boolean deleteNoteFile(String title) {
        File file = new File(title + ".txt");

        if (!file.exists()) {
            displayContent("File not found!", "red", 0);
            return false;
        }

        if (file.delete()) {
            displayContent("Note deleted successfully!", "green", 0);
            return true;
        } else {
            displayErrorAscii();
            displayContent("Error deleting the note!", "red", 0);
            return false;
        }
    }
}
