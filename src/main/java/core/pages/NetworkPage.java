package core.pages;

import static core.configs.AppearanceConfigs.*;
import static core.configs.AppearanceConfigs.sysLayoutColor;
import static core.configs.TextConfigs.*;
import static core.configs.TextConfigs.message;
import static core.logic.CommandManager.exitPage;
import static core.pages.StartPage.mainMenuRerun;
import static core.ui.DisplayManager.clearTerminal;
import static core.ui.DisplayManager.scanner;
import static java.lang.System.out;

public class NetworkPage {
    public static void displayNetworkPage() {
        marginBorder(1, 2);
        message("Network:", sysLayoutColor, 58, 0, out::println);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, searchingLineAlignment, false, getAnsi256Color(sysLayoutColor) + "> ",
                    "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "internet speed test", "speed test", "/ist" -> passwordCreatorMenu();
                case "scan ports", "/sp" -> passwordCreatorMenu();
                case "trace rout", "/tr" -> passwordCreatorMenu();
                case "rerun", "/rr" -> mainMenuRerun();
                case "clear terminal", "/cl" -> clearTerminal();
                case "list of commands", "/lc" -> displayListOfCommands();
                case "exit", "/e" -> {
                    exitPage();
                    return;
                }
                default -> modifyMessage('n', 1);
            }
        }
    }

    private static void displayListOfCommands() {
        message("·  Internet Speed Test [" + getAnsi256Color(sysMainColor) + "/ist"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Scan Ports [" + getAnsi256Color(sysMainColor) + "/sp"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Trace Rout [" + getAnsi256Color(sysMainColor) + "/tr"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  List Of Commands [" + getAnsi256Color(sysMainColor) + "/lc"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Exit [" + getAnsi256Color(sysMainColor) + "/e"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::println);
    }
}
