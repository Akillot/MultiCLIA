package additional_packages.Notepad;

import java.util.Scanner;

import static ui.layout.BasicFunc.displayTip;
import static ui.layout.BasicFunc.exitApp;
import static ui.layout.BorderFunc.drawTripleBorder;
import static ui.layout.ColorFunc.*;
import static ui.layout.TextFunc.contentAlignment;
import static ui.layout.TextFunc.displaySlowMotionText;
import static ui.layout.ThemesFunc.displayErrorAscii;

public class NotepadLayout {
    private static  Scanner scanner = new Scanner(System.in);

    public static void displayNotepad() {
        boolean running = true;
        while (running) {
            System.out.print("\n");
            String[] operations = new String[]{"1 create note", "2 open note", "3 delete note"};
            for (String operation : operations) {
                displayContent(operation, "white", 58);
            }
            System.out.println(contentAlignment(58) + BOLD + WHITE + "4 " + RESET + BOLD + RED + "exit" + RESET);
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
                default:
                    System.out.println("\n");
                    drawTripleBorder();
                    System.out.println("\n");
                    displayErrorAscii();
                    displayContent("Error, try again", "red", 0);
            }
        }
    }

    // Creating a new note
    private static void createNote() {
        System.out.print(contentAlignment(58) + BOLD + WHITE + "Enter title: " + RESET);
        String title = scanner.nextLine();
        System.out.print(contentAlignment(58) + BOLD + WHITE + "Enter content: " + RESET);
        String content = scanner.nextLine();

        Notepad note = new Notepad(title, content);
        note.saveToFile();
        System.out.print("\n");
        drawTripleBorder();
        System.out.print("\n");
        displayContent("Note saved", "purple", 58);
        System.out.print("\n");
        drawTripleBorder();
    }

    // Opening and editing a note
    private static void openNote() {
        System.out.print(contentAlignment(58) + BOLD + WHITE + "Enter title to open: " + RESET);
        String title = scanner.nextLine();

        Notepad note = Notepad.readFromFile(title);
        if (note != null) {
            displayContent("Content: ", "white", 58);
            System.out.println(note.getContent());
            drawTripleBorder();
            System.out.print("\n");

            displayTip("Do you want to update this note? [+/-]", 58);
            System.out.print(contentAlignment(58) + BOLD + WHITE + "Your choice: " + RESET);
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("+")) {
                displayContent("Enter new text to this note: ", "white", 58);
                String newContent = scanner.nextLine();
                note.setContent(newContent);
                note.saveToFile();
                displayContent("Note updated", "purple", 58);
                System.out.print("\n");
                drawTripleBorder();
            }
            else if(answer.equals("-")) {
                System.out.print("\n");
                displayContent("Opening canceled", "purple", 58);
                System.out.print("\n");
                drawTripleBorder();
            }
        } else {
            System.out.println("\n");
            drawTripleBorder();
            System.out.println("\n");
            displayErrorAscii();
            displayContent("Note not found", "red", 0);
        }
    }

    // Deleting the note
    private static void deleteNote() {
        System.out.print(contentAlignment(58) + BOLD + WHITE + "Enter title to delete note: " + RESET);
        String title = scanner.nextLine();
        boolean success = Notepad.deleteNoteFile(title);
        if (success) {
            displayContent("Note deleted", "purple", 58);
            System.out.print("\n");
            drawTripleBorder();
        } else {
            System.out.println("\n");
            drawTripleBorder();
            System.out.println("\n");
            displayErrorAscii();
            displayContent("Note not found", "red", 0);
        }
    }
}
