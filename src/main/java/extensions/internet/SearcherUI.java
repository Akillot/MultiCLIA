package extensions.internet;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static core.logic.AppearanceConfigs.*;
import static core.logic.CommandManager.*;
import static core.logic.DisplayManager.scanner;
import static core.logic.TextConfigs.*;
import static extensions.internet.SearcherConfigs.*;
import static java.lang.System.out;

public class SearcherUI {

    public static void displaySearcher() {
        modifyMessage('n', 2);
        switchLogoRandomly(searcherLogo, 32);
        marginBorder(2, 2);

        displayExample();

        while (true) {
            String domainInput = promptUserForDomain();
            modifyMessage('n',1);
            if (isExitCommand(domainInput)) {
                terminateExtension(themeColor_1, acceptanceColor, layoutColor);
                modifyMessage('n',1);
                break;
            }

            String domain = buildDomain(domainInput);

            displayConfirmation("Enter", "to open and", "to skip",
                    acceptanceColor, rejectionColor, layoutColor);

            choice("Show URL", () -> displayFullURL(domain), themeColor_1, rejectionColor, layoutColor);
            modifyMessage('n',1);
            choice(domainInput, openUri(domain), themeColor_1, rejectionColor, layoutColor);

            marginBorder(2, 1);
        }
    }

    private static void displayExample() {
        alert("Example", String.format("%s: '%sgithub.com%s'",
                        getAnsi256Color(layoutColor),
                        getAnsi256Color(themeColor_1),
                        getAnsi256Color(layoutColor)),
                58, themeColor_1, layoutColor);
    }

    private static @NotNull String promptUserForDomain() {
        modifyMessage('n', 1);
        out.print(alignment(58) + getAnsi256Color(themeColor_1) + "Enter domain"
                + getAnsi256Color(layoutColor) + " (Type '" + getAnsi256Color(rejectionColor) + "exit"
                + getAnsi256Color(layoutColor) + "' to quit): ");
        return scanner.nextLine().toLowerCase();
    }

    private static boolean isExitCommand(@NotNull String input) {
        return input.equals("exit") || input.equals("e") || input.equals("quit") || input.equals("q");
    }

    @Contract(pure = true)
    private static String buildDomain(@NotNull String input) {
        return input.startsWith("http") ? input : "https://" + input;
    }
}
