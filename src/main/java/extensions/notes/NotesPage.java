package extensions.notes;

import java.util.Scanner;

import static core.logic.AppearanceConfigs.*;
import static core.logic.CommandManager.*;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.*;
import static java.lang.System.out;

public class NotesPage {
    private static Scanner scanner = new Scanner(System.in);

    public static void displayNotesMenu(){
        modifyMessage('n',2);
        switchLogoRandomly(notesLogo,-2);
        marginBorder();
        modifyMessage('n', 1);
        choice("Operations",NotesPage::displayOperations,
                systemFirstColor, systemLayoutColor, systemRejectionColor);
        displayNotepad();
    }

    private static String[] operations = new String[]{
            "1 create note",
            "2 open note",
            "3 delete note",
            "4 sort notes by title",
            "5 sort notes by content",
            "6 exit"
    };

    private static int themeColor_1 = 165;
    private static int themeColor_2 = 207;
    private static int layoutColor = 15;

    private static int acceptanceColor = 46;
    private static int rejectionColor = 196;

    private static void displayOperations(){
        for (String operation : operations) {
            message(operation, systemLayoutColor,58,0,out::print);
        }
    }

    public static void displayNotepad() {

        boolean running = true;
        while (running) {
            modifyMessage('n', 1);
            slowMotionText(50, 58, false, true,
                    getAnsi256Color(systemLayoutColor) + "> ", "");
            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "1":
                case "create":
                    createNote();
                    break;
                case "2":
                case "open":
                    openNote();
                    break;
                case "3":
                case "delete":
                    deleteNote();
                    break;
                case "4":
                    sortNotesByTitle();
                    break;
                case "5":
                    sortNotesByContent();
                    break;
                case "6":
                case "exit":
                    terminateExtension();
                    modifyMessage('n', 1);
                    running = false;
                    break;
            }
        }
    }

    private static void createNote() {
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Enter title: " + RESET);
        String title = scanner.nextLine();
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Enter content: " + RESET);
        String content = scanner.nextLine();

        NotesConfigs note = new NotesConfigs(title, content);
        note.saveToFile();
        marginBorder();
        message("Note saved", systemFirstColor,58,0,out::println);
        border();
    }

    private static void openNote() {
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Enter title to open: " + RESET);
        String title = scanner.nextLine();

        NotesConfigs note = NotesConfigs.readFromFile(title);
        if (note != null) {
            message("Content: ", systemLayoutColor,58,0,out::println);
            message(note.getContent(), systemLayoutColor,58,0,out::println);
            border();
            modifyMessage('n', 1);

            displayConfirmation("Enter","to open and","to skip",
                    themeColor_1,layoutColor,rejectionColor);
            out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Your choice: " + RESET);
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("+")) {
                message("Enter new text to this note: ", systemLayoutColor,58,0,out::println);
                String newContent = scanner.nextLine();
                note.setContent(newContent);
                note.saveToFile();

                modifyMessage('n', 1);
                border();
                message("Note updated", systemFirstColor,58,0,out::println);
                modifyMessage('n', 1);
                border();
            } else if (answer.equals("-")) {
                modifyMessage('n', 1);
                message("Opening canceled", systemFirstColor,58,0,out::println);
                modifyMessage('n', 1);
                border();
            }
        } else {
            marginBorder();
            errorAscii();
            message("Note not found", systemRejectionColor,58,0,out::println);
        }
    }

    private static void deleteNote() {
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Enter title to delete note: " + RESET);
        String title = scanner.nextLine();
        boolean success = NotesConfigs.deleteNoteFile(title);
        if (success) {
            message("Note deleted", systemFirstColor,58,0,out::println);
            modifyMessage('n', 1);
            border();
        } else {
            marginBorder();
            errorAscii();
            message("Note not found", systemRejectionColor,58,0,out::println);
        }
    }

    private static void sortNotesByTitle() {
        NotesConfigs.sortNotesByTitle();
        marginBorder();
        message("Notes sorted by title", systemFirstColor,58,0,out::println);
        border();
    }

    private static void sortNotesByContent() {
        NotesConfigs.sortNotesByContent();
        marginBorder();
        message("Notes sorted by content", systemFirstColor,58,0,out::println);
        border();
    }

    public static String[] notesLogo = {
            "ooooo      ooo               .                      ",
            "`888b.     `8'             .o8                      ",
            " 8 `88b.    8   .ooooo.  .o888oo  .ooooo.   .oooo.o ",
            " 8   `88b.  8  d88' `88b   888   d88' `88b d88(  \"8 ",
            " 8     `88b.8  888   888   888   888ooo888 `\"Y88b.  ",
            " 8       `888  888   888   888 . 888    .o o.  )88b ",
            "o8o        `8  `Y8bod8P'   \"888\" `Y8bod8P' 8\"\"888P' ",
            " "
    };
}
