package core.pages;

import static core.logic.BorderFunc.marginBorder;
import static core.logic.ColorFunc.*;
import static core.logic.CommandManager.choice;
import static core.logic.DisplayManager.*;
import static core.logic.TextFunc.alignment;
import static java.lang.System.out;

public class SettingsPage {
    public static void displaySettings() {
        messageModifier('n', 2);
        out.print(alignment(60) + BOLD + "-" + BLUE + ITALIC + "Memory\n" + RESET);
        usingMemory();

        messageModifier('n', 2);
        out.print(alignment(60) + BOLD + "-" + BLUE + ITALIC + "Text\n" + RESET);
        choice("Text", textModification());
        marginBorder();
    }
}
