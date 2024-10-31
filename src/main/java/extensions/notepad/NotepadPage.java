package extensions.notepad;

import java.util.Scanner;

import static core.layout.BorderFunc.bigBorder;
import static core.layout.BorderFunc.marginBigBorder;
import static core.layout.ColorFunc.*;
import static core.layout.CommandManager.exitExtension;
import static core.layout.DisplayManager.*;
import static core.layout.TextFunc.alignment;
import static core.layout.TextFunc.slowMotionText;
import static java.lang.System.out;

public class NotepadPage {
    private static  Scanner scanner = new Scanner(System.in);

    public static void displayNotepad() {
        boolean running = true;
        while (running) {
            out.print("\n\n");
            String[] operations = new String[]{"1 create note", "2 open note", "3 delete note"};
            for (String operation : operations) {
                message(operation, "white", 58);
            }
            out.println(alignment(58) + BOLD + WHITE + "4 " + RESET + BOLD + RED + "exit" + RESET);
            out.print("\n");
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

    // Creating a new note
    private static void createNote() {
        out.print(alignment(58) + BOLD + WHITE + "Enter title: " + RESET);
        String title = scanner.nextLine();
        out.print(alignment(58) + BOLD + WHITE + "Enter content: " + RESET);
        String content = scanner.nextLine();

        NotepadFunc note = new NotepadFunc(title, content);
        note.saveToFile();
        marginBigBorder();
        message("Note saved", "purple", 58);
        out.print("\n");
        bigBorder();
    }

    private static void openNote() {
        out.print(alignment(58) + BOLD + WHITE + "Enter title to open: " + RESET);
        String title = scanner.nextLine();

        NotepadFunc note = NotepadFunc.readFromFile(title);
        if (note != null) {
            message("Content: ", "white", 58);
            out.println(note.getContent());
            bigBorder();
            out.print("\n");

            tip("Do you want to update this note? [+/-]", 58);
            out.print(alignment(58) + BOLD + WHITE + "Your choice: " + RESET);
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("+")) {
                message("Enter new text to this note: ", "white", 58);
                String newContent = scanner.nextLine();
                note.setContent(newContent);
                note.saveToFile();

                out.print("\n");
                bigBorder();
                message("Note updated", "purple", 58);
                out.print("\n");
                bigBorder();
            }
            else if(answer.equals("-")) {
                out.print("\n");
                message("Opening canceled", "purple", 58);
                out.print("\n");
                bigBorder();
            }
        } else {
            marginBigBorder();
            errorAscii();
            message("Note not found", "red", 58);
        }
    }

    private static void deleteNote() {
        out.print(alignment(58) + BOLD + WHITE + "Enter title to delete note: " + RESET);
        String title = scanner.nextLine();
        boolean success = NotepadFunc.deleteNoteFile(title);
        if (success) {
            message("Note deleted", "purple", 58);
            out.print("\n");
            bigBorder();
        } else {
            marginBigBorder();
            errorAscii();
            message("Note not found", "red", 58);
        }
    }
}
