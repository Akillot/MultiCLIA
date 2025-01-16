package extensions.internet;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static core.configs.AppearanceConfigs.*;
import static core.logic.CommandManager.*;
import static core.ui.DisplayManager.scanner;
import static core.configs.TextConfigs.*;
import static extensions.internet.SearcherConfigs.*;
import static java.lang.System.out;

public class SearcherUI {

    public static void displaySearcherMenu() {
        marginBorder(1,2);
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

            displayConfirmation("Enter","y","+",
                    "to open and","n","-","to skip",
                    systemAcceptanceColor, systemRejectionColor, systemLayoutColor);

            choice("Show URL", () -> displayFullURL(domain), themeColor_1, layoutColor, rejectionColor);
            modifyMessage('n',2);

            choice(domainInput, openUri(domain), themeColor_1, layoutColor, rejectionColor);
            marginBorder(2, 1);
        }
        modifyMessage('n',1);
        choice("Info", SearcherUI::displayInfo, themeColor_1, layoutColor, rejectionColor);
        marginBorder(2, 1);
    }

    private static void displayInfo(){
        modifyMessage('n', 1);
        message("Name: " + getAnsi256Color(themeColor_2) + "Searcher", layoutColor, 58, 0, out::print);
        message("Type: " + getAnsi256Color(themeColor_2) + "Default extension", layoutColor, 58, 0, out::print);
        message("Version: " +  getAnsi256Color(themeColor_2) + getVersion(), layoutColor, 58, 0, out::print);
        message("Author: " + getAnsi256Color(themeColor_2) + "Nick Zozulia", layoutColor, 58, 0, out::print);
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

    public static void terminateExtension(int themeColor_1, int acceptanceColor, int layoutColor) {
        message("\r   Status: " + getAnsi256Color(acceptanceColor) + "✓", layoutColor,58,0,out::print);
        message("Terminated correctly", themeColor_1,
                58,0,out::print);
        modifyMessage('n',2);
        border();
    }
}
