package core.ui.essential.pages;

import org.jetbrains.annotations.NotNull;

import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.scanner;
import static core.ui.essential.configs.appearance.TextConfigs.*;
import static java.lang.System.exit;
import static java.lang.System.out;

public class ExitPage {

    public static void displayExitPage() {
        marginBorder(1, 2);
        message("Exit:", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayExitPrompt();
    }

    private static void displayExitPrompt() {
        insertControlChars('n',1);
        displayConfirmation(
                "Enter", "y", "+", "to exit MultiCLIA and",
                "n", "-", "to stay in",
                getAcceptanceColor(), getRejectionColor(), getLayoutColor(), getDefaultTextAlignment()
        );

        message("Are you sure?", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        out.print(alignment(48) + getColor(getLayoutColor()) + getSearchingArrow());
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
        message("Status: " + getColor(getAcceptanceColor()) + "✓", getLayoutColor(),
                getDefaultTextAlignment(), 0, out::print);
        message("Program terminated successfully" + getColor(getLayoutColor()) + ". " +
                       getColor(getMainColor()) + "You are back in Terminal" + getColor(getLayoutColor()) + ".",
                getMainColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        insertControlChars('n', 1);
        exit(0);
    }

    private static void stayInProgram() {
        marginBorder(2, 2);
        message("Status: " + getColor(getAcceptanceColor()) + "✓", getLayoutColor(),
                getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("Program is still running" + getColor(getLayoutColor()) + ". "
                        + getColor(getMainColor()) + "Returning to the main menu"
                        + getColor(getLayoutColor()) + ".",
                getMainColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);

        marginBorder(1, 1);
    }
}
