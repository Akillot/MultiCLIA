package extensions.notes;

import java.util.Scanner;

public class NotesMenu {
    private static Scanner scanner = new Scanner(System.in);

    public static void displayNotesMenu() {
        boolean running = true;
        while (running) {
            System.out.println("1. Create Note");
            System.out.println("2. Open Note");
            System.out.println("3. Delete Note");
            System.out.println("4. Sort Notes by Title");
            System.out.println("5. Sort Notes by Content");
            System.out.println("6. Exit");
            System.out.print("> ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createNote();
                    break;
                case "2":
                    openNote();
                    break;
                case "3":
                    deleteNote();
                    break;
                case "4":
                    NotesManager.sortNotesByTitle();
                    System.out.println("Notes sorted by title.");
                    break;
                case "5":
                    NotesManager.sortNotesByContent();
                    System.out.println("Notes sorted by content.");
                    break;
                case "6":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void createNote() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter content: ");
        String content = scanner.nextLine();

        NotesConfigs note = new NotesConfigs(title, content);
        NotesManager.addNote(note);
        note.saveToFile();
        System.out.println("Note created.");
    }

    private static void openNote() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        NotesConfigs note = NotesConfigs.readFromFile(title);
        if (note != null) {
            System.out.println("Content: ");
            System.out.println(note.getContent());
        } else {
            System.out.println("Note not found.");
        }
    }

    private static void deleteNote() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        boolean deleted = NotesConfigs.deleteNoteFile(title);
        if (deleted) {
            NotesManager.deleteNoteByTitle(title);
            System.out.println("Note deleted.");
        }
    }
}