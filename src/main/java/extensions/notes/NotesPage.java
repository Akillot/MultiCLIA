package extensions.notes;

import java.util.Scanner;

import static core.logic.BorderConfigs.border;
import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.terminateExtension;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.alignment;
import static core.logic.TextConfigs.slowMotionText;
import static extensions.notes.NotesConfigs.notepadLogo;
import static java.lang.System.out;

public class NotesPage {
    private static  Scanner scanner = new Scanner(System.in);

    public static void displayNotepad() {
        boolean running = true;
        while (running) {
            messageModifier('n', 2);
            switchLogoAscii(notepadLogo,48);
            border();
            messageModifier('n',1);

            String[] operations = new String[]{"1 create note", "2 open note", "3 delete note", "4 exit"};
            for (String operation : operations) {
                message(operation,"white",58,0, out::print);
            }
            messageModifier('n', 1);
            slowMotionText(50,58,false,true,"Choice",": ");
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
                    terminateExtension();
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

        NotesConfigs note = new NotesConfigs(title, content);
        note.saveToFile();
        marginBorder();
        message("Note saved", "blue", 58,0, out::println);
        messageModifier('n', 1);
        border();
    }

    private static void openNote() {
        out.print(alignment(58) + BOLD + WHITE + "Enter title to open: " + RESET);
        String title = scanner.nextLine();

        NotesConfigs note = NotesConfigs.readFromFile(title);
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
        boolean success = NotesConfigs.deleteNoteFile(title);
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