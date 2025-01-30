package core.pages;

import java.util.Scanner;

import static core.configs.AppearanceConfigs.*;
import static core.configs.AppearanceConfigs.sysLayoutColor;
import static core.configs.TextConfigs.*;
import static core.configs.TextConfigs.message;
import static core.logic.CommandManager.exitPage;
import static core.logic.CommandManager.mainMenuRerun;
import static core.ui.DisplayManager.clearTerminal;
import static java.lang.System.out;

public class TerminalPage {

    private static Scanner scanner = new Scanner(System.in);

    public static void displayTerminalPage() {
        marginBorder(1, 2);
        message("Terminal:", sysLayoutColor, 58, 0, out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, searchingLineAlignment, false, getColor(sysLayoutColor) + "> ",
                    "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "rerun", "/rr" -> {
                    modifyMessage('n',1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
                case "list of commands", "/lc" -> displayListOfCommands();
                case "exit", "/e" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands(){
        modifyMessage('n',1);
        message("·  Clear Terminal [" + getColor(sysMainColor)
                + "/cl" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  List Of Commands [" + getColor(sysMainColor)
                + "/lc" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Exit [" + getColor(sysMainColor)
                + "/e" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::println);
    }
}
