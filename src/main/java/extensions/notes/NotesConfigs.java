package extensions.notes;

import lombok.Getter;
import lombok.Setter;

import java.io.*;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.DisplayManager.errorAscii;
import static core.logic.DisplayManager.message;
import static java.lang.System.out;


public class NotesConfigs {
    private String title;
    @Setter
    @Getter
    private String content;

    public NotesConfigs(String title, String content) {
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

    public static NotesConfigs readFromFile(String title) {
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
        return new NotesConfigs(title, content.toString());
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

    public static String[] notepadLogo = {
            "ooooo      ooo               .             ",
            "`888b.     `8'             .o8             ",
            " 8 `88b.    8   .ooooo.  .o888oo  .ooooo.  ",
            " 8   `88b.  8  d88' `88b   888   d88' `88b ",
            " 8     `88b.8  888   888   888   888ooo888 ",
            " 8       `888  888   888   888 . 888    .o ",
            "o8o        `8  `Y8bod8P'   \"888\" `Y8bod8P' ",
            "                                                 ",
            "                           .o8             ",
            "                          \"888             ",
            "oo.ooooo.   .oooo.    .oooo888             ",
            " 888' `88b `P  )88b  d88' `888             ",
            " 888   888  .oP\"888  888   888             ",
            " 888   888 d8(  888  888   888             ",
            " 888bod8P' `Y888\"\"8o `Y8bod88P\"            ",
            " 888                                       ",
            "o888o                                      ",
            "                                           "
    };

}
