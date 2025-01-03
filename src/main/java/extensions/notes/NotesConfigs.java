package extensions.notes;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.io.*;

@Getter
@Setter
public class NotesConfigs {
    private String title;
    private String content;

    public NotesConfigs(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void saveToFile() {
        if (title == null || title.isEmpty()) {
            System.out.println("Title cannot be empty!");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(title + ".txt"))) {
            writer.write(content);
            System.out.println("Note saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving the note: " + e.getMessage());
        }
    }

    public static @Nullable NotesConfigs readFromFile(String title) {
        File file = new File(title + ".txt");
        if (!file.exists()) {
            System.out.println("Note not found!");
            return null;
        }
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading the note: " + e.getMessage());
            return null;
        }
        return new NotesConfigs(title, content.toString());
    }

    public static boolean deleteNoteFile(String title) {
        File file = new File(title + ".txt");
        if (!file.exists()) {
            System.out.println("Note not found!");
            return false;
        }
        if (file.delete()) {
            System.out.println("Note deleted successfully!");
            return true;
        } else {
            System.out.println("Error deleting the note!");
            return false;
        }
    }

    static String[] notesLogoAscii = {
            "ooooo      ooo               .                      ",
            "`888b.     `8'             .o8                      ",
            " 8 `88b.    8   .ooooo.  .o888oo  .ooooo.   .oooo.o ",
            " 8   `88b.  8  d88' `88b   888   d88' `88b d88(  \"8 ",
            " 8     `88b.8  888   888   888   888ooo888 `\"Y88b.  ",
            " 8       `888  888   888   888 . 888    .o o.  )88b ",
            "o8o        `8  `Y8bod8P'   \"888\" `Y8bod8P' 8\"\"888P' "
    };
}
