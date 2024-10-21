package extansions.notepad;

import java.util.Scanner;

import static ui.layout.BorderFunc.displayBigBorder;
import static ui.layout.BorderFunc.displayMarginBigBorder;
import static ui.layout.ColorFunc.*;
import static ui.layout.CommandManager.exitExtension;
import static ui.layout.DisplayManager.*;
import static ui.layout.TextFunc.alignment;
import static ui.layout.TextFunc.displaySlowMotionText;

public class NotepadPage {
    private static  Scanner scanner = new Scanner(System.in);

    public static void displayNotepad() {
        boolean running = true;
        while (running) {
            System.out.print("\n\n");
            String[] operations = new String[]{"1 create note", "2 open note", "3 delete note"};
            for (String operation : operations) {
                message(operation, "white", 58, false);
            }
            System.out.println(alignment(58) + BOLD + WHITE + "4 " + RESET + BOLD + RED + "exit" + RESET);
            System.out.print("\n");
            displaySlowMotionText(50, 58, true, "Your choice", ": ");
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
        System.out.print(alignment(58) + BOLD + WHITE + "Enter title: " + RESET);
        String title = scanner.nextLine();
        System.out.print(alignment(58) + BOLD + WHITE + "Enter content: " + RESET);
        String content = scanner.nextLine();

        NotepadFunc note = new NotepadFunc(title, content);
        note.saveToFile();
        displayMarginBigBorder();
        message("Note saved", "purple", 58, false);
        System.out.print("\n");
        displayBigBorder();
    }

    private static void openNote() {
        System.out.print(alignment(58) + BOLD + WHITE + "Enter title to open: " + RESET);
        String title = scanner.nextLine();

        NotepadFunc note = NotepadFunc.readFromFile(title);
        if (note != null) {
            message("Content: ", "white", 58, false);
            System.out.println(note.getContent());
            displayBigBorder();
            System.out.print("\n");

            tip("Do you want to update this note? [+/-]", 58);
            System.out.print(alignment(58) + BOLD + WHITE + "Your choice: " + RESET);
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("+")) {
                message("Enter new text to this note: ", "white", 58, false);
                String newContent = scanner.nextLine();
                note.setContent(newContent);
                note.saveToFile();

                System.out.print("\n");
                displayBigBorder();
                message("Note updated", "purple", 58, false);
                System.out.print("\n");
                displayBigBorder();
            }
            else if(answer.equals("-")) {
                System.out.print("\n");
                message("Opening canceled", "purple", 58, false);
                System.out.print("\n");
                displayBigBorder();
            }
        } else {
            displayMarginBigBorder();
            errorAscii();
            message("Note not found", "red", 58, false);
        }
    }

    private static void deleteNote() {
        System.out.print(alignment(58) + BOLD + WHITE + "Enter title to delete note: " + RESET);
        String title = scanner.nextLine();
        boolean success = NotepadFunc.deleteNoteFile(title);
        if (success) {
            message("Note deleted", "purple", 58, false);
            System.out.print("\n");
            displayBigBorder();
        } else {
            displayMarginBigBorder();
            errorAscii();
            message("Note not found", "red", 58, false);
        }
    }
}
