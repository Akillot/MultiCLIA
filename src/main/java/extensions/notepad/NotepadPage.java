package extensions.notepad;

import java.util.Scanner;

import static core.logic.BorderFunc.border;
import static core.logic.BorderFunc.marginBorder;
import static core.logic.ColorFunc.*;
import static core.logic.CommandManager.exitExtension;
import static core.logic.DisplayManager.*;
import static core.logic.TextFunc.alignment;
import static core.logic.TextFunc.slowMotionText;
import static java.lang.System.out;

public class NotepadPage {
    private static  Scanner scanner = new Scanner(System.in);

    public static void displayNotepad() {
        boolean running = true;
        while (running) {
            messageModifier('n', 2);
            String[] operations = new String[]{"1 create note", "2 open note", "3 delete note"};
            for (String operation : operations) {
                message(operation, "white", 58,0, out::print);
            }
            message(alignment(58) + BOLD + WHITE + "4 " + RESET + BOLD + RED + "exit" + RESET,
                    "white", 58, 0, out::print);
            messageModifier('n', 1);
            slowMotionText(50, 58, true, false, "Your choice", ": ");
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
                case "exit":
                    exitExtension();
                    running = false;
                    break;
            }
        }
    }

    private static void createNote() {
        out.print(alignment(58) + BOLD + WHITE + "Enter title: " + RESET);
        String title = scanner.nextLine();
        out.print(alignment(58) + BOLD + WHITE + "Enter content: " + RESET);
        String content = scanner.nextLine();

        NotepadFunc note = new NotepadFunc(title, content);
        note.saveToFile();
        marginBorder();
        message("Note saved", "blue", 58,0, out::println);
        messageModifier('n', 1);
        border();
    }

    private static void openNote() {
        out.print(alignment(58) + BOLD + WHITE + "Enter title to open: " + RESET);
        String title = scanner.nextLine();

        NotepadFunc note = NotepadFunc.readFromFile(title);
        if (note != null) {
            message("Content: ", "white", 58,0, out::println);
            message(note.getContent(), "white", 58,0, out::println);
            border();
            messageModifier('n', 1);

            alert("i", "Do you want to update this note? [+/-]", 58);
            out.print(alignment(58) + BOLD + WHITE + "Your choice: " + RESET);
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("+")) {
                message("Enter new text to this note: ", "white", 58,0, out::println);
                String newContent = scanner.nextLine();
                note.setContent(newContent);
                note.saveToFile();

                messageModifier('n', 1);
                border();
                message("Note updated", "blue", 58,0, out::println);
                messageModifier('n', 1);
                border();
            }
            else if(answer.equals("-")) {
                messageModifier('n', 1);
                message("Opening canceled", "blue", 58,0, out::println);
                messageModifier('n', 1);
                border();
            }
        } else {
            marginBorder();
            errorAscii();
            message("Note not found", "red", 58,0, out::println);
        }
    }

    private static void deleteNote() {
        out.print(alignment(58) + BOLD + WHITE + "Enter title to delete note: " + RESET);
        String title = scanner.nextLine();
        boolean success = NotepadFunc.deleteNoteFile(title);
        if (success) {
            message("Note deleted", "blue", 58,0, out::println);
            messageModifier('n', 1);
            border();
        } else {
            marginBorder();
            errorAscii();
            message("Note not found", "red", 58,0, out::println);
        }
    }
}
