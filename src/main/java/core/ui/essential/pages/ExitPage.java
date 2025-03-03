package core.ui.essential.pages;

import org.jetbrains.annotations.NotNull;

import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.scanner;
import static core.ui.essential.configs.TextConfigs.*;
import static java.lang.System.exit;
import static java.lang.System.out;

public class ExitPage {

    public static void displayExitPage() {
        marginBorder(1, 2);
        message("Exit:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayExitPrompt();
    }

    private static void displayExitPrompt() {
        insertControlChars('n',1);
        displayConfirmation(
                "Enter", "y", "+", "to exit MultiCLIA and",
                "n", "-", "to stay in",
                acceptanceColor, rejectionColor, layoutColor, getDefaultTextAlignment()
        );

        message("Are you sure?", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        out.print(alignment(48) + getColor(layoutColor) + searchingArrow);
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
        message("Status: " + getColor(acceptanceColor) + "✓", layoutColor,
                getDefaultTextAlignment(), 0, out::print);
        message("Program terminated successfully" + getColor(layoutColor) + ". " +
                       getColor(mainColor) + "You are back in Terminal" + getColor(layoutColor) + ".",
                mainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        insertControlChars('n', 1);
        exit(0);
    }

    private static void stayInProgram() {
        marginBorder(2, 2);
        message("Status: " + getColor(acceptanceColor) + "✓", layoutColor,
                getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("Program is still running" + getColor(layoutColor) + ". "
                        + getColor(mainColor) + "Returning to the main menu"
                        + getColor(layoutColor) + ".",
                mainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

        marginBorder(1, 1);
    }
}
