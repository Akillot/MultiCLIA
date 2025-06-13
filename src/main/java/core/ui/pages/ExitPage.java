package core.ui.pages;

import org.jetbrains.annotations.NotNull;

import static core.CommandManager.*;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.DisplayManager.scanner;
import static core.ui.configs.TextConfigs.*;
import static java.lang.System.out;

public class ExitPage {

    public static void displayExitPage() {
        marginBorder(1, 2);
        message("Exit:",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        displayExitPrompt();
    }

    private static void displayExitPrompt() {
        insertControlChars('n',1);
        displayConfirmation(
                "Enter", "y", "+", "to exit MultiCLIA and",
                "n", "-", "to stay in",
                getAcceptanceColor(),
                getRejectionColor(),
                getLayoutColor(),
                getDefaultTextAlignment()
        );

        message("Are you sure?", getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);

        out.print(alignment(getDefaultLogoAlignment()) + getColor(getLayoutColor()) + getSearchingArrow());
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
            case "n", "-" -> {
                exitPageFormatting();
                clearAndRestartApp();
            }
            default -> displayExitPrompt();
        }
    }
}
