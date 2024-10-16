package additional_packages.notepad;

import java.util.Scanner;

import static ui.layout.BasicFunc.*;
import static ui.layout.BorderFunc.displayBigBorder;
import static ui.layout.BorderFunc.displayMarginBigBorder;
import static ui.layout.ColorFunc.*;
import static ui.layout.TextFunc.alignmentLogic;
import static ui.layout.TextFunc.displaySlowMotionText;
import static ui.layout.ThemesFunc.displayErrorAscii;

public class NotepadLayout {
    private static  Scanner scanner = new Scanner(System.in);

    public static void displayNotepad() {
        boolean running = true;
        while (running) {
            System.out.print("\n");
            System.out.print("\n");
            String[] operations = new String[]{"1 create note", "2 open note", "3 delete note"};
            for (String operation : operations) {
                displayContent(operation, "white", 58);
            }
            System.out.println(alignmentLogic(58) + BOLD + WHITE + "4 " + RESET + BOLD + RED + "exit" + RESET);
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
                    exitApp();
                    running = false;
                    break;
            }
        }
    }

    // Creating a new note
    private static void createNote() {
        System.out.print(alignmentLogic(58) + BOLD + WHITE + "Enter title: " + RESET);
        String title = scanner.nextLine();
        System.out.print(alignmentLogic(58) + BOLD + WHITE + "Enter content: " + RESET);
        String content = scanner.nextLine();

        Notepad note = new Notepad(title, content);
        note.saveToFile();
        displayMarginBigBorder();
        displayContent("Note saved", "purple", 58);
        System.out.print("\n");
        displayBigBorder();
    }

    private static void openNote() {
        System.out.print(alignmentLogic(58) + BOLD + WHITE + "Enter title to open: " + RESET);
        String title = scanner.nextLine();

        Notepad note = Notepad.readFromFile(title);
        if (note != null) {
            displayContent("Content: ", "white", 58);
            System.out.println(note.getContent());
            displayBigBorder();
            System.out.print("\n");

            displayTip("Do you want to update this note? [+/-]", 58);
            System.out.print(alignmentLogic(58) + BOLD + WHITE + "Your choice: " + RESET);
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("+")) {
                displayContent("Enter new text to this note: ", "white", 58);
                String newContent = scanner.nextLine();
                note.setContent(newContent);
                note.saveToFile();

                System.out.print("\n");
                displayBigBorder();
                displayContent("Note updated", "purple", 58);
                System.out.print("\n");
                displayBigBorder();
            }
            else if(answer.equals("-")) {
                System.out.print("\n");
                displayContent("Opening canceled", "purple", 58);
                System.out.print("\n");
                displayBigBorder();
            }
        } else {
            displayMarginBigBorder();
            displayErrorAscii();
            displayContent("Note not found", "red", 0);
        }
    }

    // Deleting the note
    private static void deleteNote() {
        System.out.print(alignmentLogic(58) + BOLD + WHITE + "Enter title to delete note: " + RESET);
        String title = scanner.nextLine();
        boolean success = Notepad.deleteNoteFile(title);
        if (success) {
            displayContent("Note deleted", "purple", 58);
            System.out.print("\n");
            displayBigBorder();
        } else {
            displayMarginBigBorder();
            displayErrorAscii();
            displayContent("Note not found", "red", 0);
        }
    }
}
