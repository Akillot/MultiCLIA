package core.pages;

import org.jetbrains.annotations.NotNull;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;
import static core.ui.DisplayManager.scanner;
import static java.lang.System.exit;
import static java.lang.System.out;

public class ExitPage {

    public static void displayExitPage() {
        marginBorder(1, 2);
        message("Exit:", sysLayoutColor, 58, 0, out::print);
        displayExitPrompt();
    }

    private static void displayExitPrompt() {
        insertControlCharacters('n',1);
        displayConfirmation(
                "Enter", "y", "+", "to exit MultiCLIA and",
                "n", "-", "to stay in",
                sysAcceptanceColor, sysRejectionColor, sysLayoutColor, 58
        );

        message("Are you sure?", sysLayoutColor, 58, 0, out::println);
        out.print(alignment(48) + getColor(sysLayoutColor) + searchingArrow);
        String answer = scanner.nextLine().toLowerCase();

        processUserResponse(answer);
    }

    private static void processUserResponse(@NotNull String answer) {
        if (answer.isEmpty()) {
            displayExitPrompt();
            return;
        }

        switch (answer) {
            case "y", "+" -> terminateProgram();
            case "n", "-" -> stayInProgram();
            default -> displayExitPrompt();
        }
    }

    private static void terminateProgram() {
        marginBorder(2, 2);
        message("Status: " + getColor(sysAcceptanceColor) + "✓", sysLayoutColor,
                58, 0, out::print);
        message(
                "Program terminated successfully" + getColor(sysLayoutColor) + ".",
                sysMainColor, 58, 0, out::println);
        insertControlCharacters('n', 1);
        exit(0);
    }

    private static void stayInProgram() {
        marginBorder(2, 2);
        message("Status: " + getColor(sysAcceptanceColor) + "✓", sysLayoutColor,
                58, 0, out::print);

        message("Program is still running" + getColor(sysLayoutColor) + ". "
                        + getColor(sysMainColor) + "Returning to the main menu"
                        + getColor(sysLayoutColor) + ".",
                sysMainColor, 58, 0, out::println);

        marginBorder(1, 1);
    }
}
