package core.ui.pages;

import org.jetbrains.annotations.NotNull;

import static core.configs.AppearanceConfigs.*;
import static core.configs.TextConfigs.*;
import static core.ui.DisplayManager.scanner;
import static java.lang.System.exit;
import static java.lang.System.out;

public class ExitPage {

    public static void displayExitPage() {
        marginBorder(1, 2);
        message("Exit:", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayExitPrompt();
    }

    private static void displayExitPrompt() {
        insertControlChars('n',1);
        displayConfirmation(
                "Enter", "y", "+", "to exit MultiCLIA and",
                "n", "-", "to stay in",
                sysAcceptanceColor, sysRejectionColor, sysLayoutColor, getDefaultTextAlignment()
        );

        message("Are you sure?", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
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
                getDefaultTextAlignment(), 0, out::print);
        message("Program terminated successfully" + getColor(sysLayoutColor) + ". " +
                       getColor(sysMainColor) + "You are back in Terminal" + getColor(sysLayoutColor) + ".",
                sysMainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        insertControlChars('n', 1);
        exit(0);
    }

    private static void stayInProgram() {
        marginBorder(2, 2);
        message("Status: " + getColor(sysAcceptanceColor) + "✓", sysLayoutColor,
                getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("Program is still running" + getColor(sysLayoutColor) + ". "
                        + getColor(sysMainColor) + "Returning to the main menu"
                        + getColor(sysLayoutColor) + ".",
                sysMainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

        marginBorder(1, 1);
    }
}
