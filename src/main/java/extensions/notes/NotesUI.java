package extensions.notes;

import java.util.Scanner;

import static core.configs.AppearanceConfigs.getColor;
import static core.configs.TextConfigs.*;
import static java.lang.System.out;

public class NotesUI {
    private static Scanner scanner = new Scanner(System.in);

    public static void displayNotesMenu() {
        boolean running = true;
        while (running) {

            modifyMessage('n',1);

            message("1. Create Note",15,58,0,out::print);
            message("2. Open Note",15,58,0,out::print);
            message("3. Delete Note",15,58,0,out::print);
            message("4. Sort Notes by Title",15,58,0,out::print);
            message("5. Sort Notes by Content",15,58,0,out::print);
            message("6. " + getColor(196) + "Exit",15,58,0,out::print);

            modifyMessage('n',1);

            out.print(alignment(58) + getColor(15) + "> ");
            String choice = scanner.nextLine().toLowerCase();

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
                    message("Notes sorted by title.",46,58,0,out::print);
                    break;
                case "5":
                    NotesManager.sortNotesByContent();
                    message("Notes sorted by content.",46,58,0,out::print);
                    break;
                case "6":
                    running = false;
                    break;
                default:
                    message("Invalid choice. Try again.",196,58,0,out::print);
            }
        }
    }

    private static void createNote() {
        out.print(alignment(58) + getColor(15) + "Enter title: ");
        String title = scanner.nextLine();
        out.print(alignment(58) + getColor(15) + "Enter content: ");
        String content = scanner.nextLine();

        NotesConfigs note = new NotesConfigs(title, content);
        NotesManager.addNote(note);
        note.saveToFile();
        message("Note created.",46,58,0,out::print);
    }

    private static void openNote() {
        message("Enter title: ",15,58,0,out::print);
        String title = scanner.nextLine();

        NotesConfigs note = NotesConfigs.readFromFile(title);
        if (note != null) {
            message("Content: ",15,58,0,out::print);
            out.println(note.getContent());
        } else {
            message("Note not found.",196,58,0,out::print);
        }
    }

    private static void deleteNote() {
        message("Enter title: ",15,58,0,out::print);
        String title = scanner.nextLine();

        boolean deleted = NotesConfigs.deleteNoteFile(title);
        if (deleted) {
            NotesManager.deleteNoteByTitle(title);
           message("Note deleted.",46,58,0,out::print);
        }
    }
}