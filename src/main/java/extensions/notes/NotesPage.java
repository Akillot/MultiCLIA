package extensions.notes;

import java.util.Scanner;

import static core.logic.BorderConfigs.border;
import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.terminateExtension;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.*;
import static java.lang.System.out;

public class NotesPage {
    private static Scanner scanner = new Scanner(System.in);

    public static void displayNotepad() {
        boolean running = true;
        modifyMessage('n', 2);

        String[] operations = new String[]{
                "1 create note",
                "2 open note",
                "3 delete note",
                "4 sort notes by title",
                "5 sort notes by content",
                "6 exit"
        };

        while (running) {
            for (String operation : operations) {
                message(operation,systemDefaultWhite,58,0,out::print);
            }
            modifyMessage('n', 1);
            slowMotionText(50, 58, false, true, "Choice", ": ");
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
        out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + "Enter title: " + RESET);
        String title = scanner.nextLine();
        out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + "Enter content: " + RESET);
        String content = scanner.nextLine();

        NotesConfigs note = new NotesConfigs(title, content);
        note.saveToFile();
        marginBorder();
        message("Note saved",systemDefaultColor,58,0,out::println);
        border();
    }

    private static void openNote() {
        out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + "Enter title to open: " + RESET);
        String title = scanner.nextLine();

        NotesConfigs note = NotesConfigs.readFromFile(title);
        if (note != null) {
            message("Content: ",systemDefaultWhite,58,0,out::println);
            message(note.getContent(),systemDefaultWhite,58,0,out::println);
            border();
            modifyMessage('n', 1);

            alert("i", "Do you want to update this note? [+/-]", 58);
            out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + "Your choice: " + RESET);
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("+")) {
                message("Enter new text to this note: ",systemDefaultWhite,58,0,out::println);
                String newContent = scanner.nextLine();
                note.setContent(newContent);
                note.saveToFile();

                modifyMessage('n', 1);
                border();
                message("Note updated",systemDefaultColor,58,0,out::println);
                modifyMessage('n', 1);
                border();
            } else if (answer.equals("-")) {
                modifyMessage('n', 1);
                message("Opening canceled",systemDefaultColor,58,0,out::println);
                modifyMessage('n', 1);
                border();
            }
        } else {
            marginBorder();
            errorAscii();
            message("Note not found",systemDefaultRed,58,0,out::println);
        }
    }

    private static void deleteNote() {
        out.print(alignment(58) + getAnsi256Color(systemDefaultWhite) + "Enter title to delete note: " + RESET);
        String title = scanner.nextLine();
        boolean success = NotesConfigs.deleteNoteFile(title);
        if (success) {
            message("Note deleted",systemDefaultColor,58,0,out::println);
            modifyMessage('n', 1);
            border();
        } else {
            marginBorder();
            errorAscii();
            message("Note not found",systemDefaultRed,58,0,out::println);
        }
    }

    private static void sortNotesByTitle() {
        NotesConfigs.sortNotesByTitle();
        marginBorder();
        message("Notes sorted by title",systemDefaultColor,58,0,out::println);
        border();
    }

    private static void sortNotesByContent() {
        NotesConfigs.sortNotesByContent();
        marginBorder();
        message("Notes sorted by content",systemDefaultColor,58,0,out::println);
        border();
    }
}
