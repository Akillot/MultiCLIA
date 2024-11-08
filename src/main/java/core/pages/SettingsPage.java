package core.pages;

import static core.layout.BorderFunc.marginBigBorder;
import static core.layout.ColorFunc.*;
import static core.layout.CommandManager.choice;
import static core.layout.DisplayManager.*;
import static core.layout.TextFunc.alignment;
import static java.lang.System.out;

public class SettingsPage {
    public static void displaySettings() {
        messageModifier('n', 2);
        out.print(alignment(60) + BOLD + "-" + PURPLE + ITALIC + "Memory\n" + RESET);
        usingMemory();
        messageModifier('n', 2);
        out.print(alignment(60) + BOLD + "-" + PURPLE + ITALIC + "Text\n" + RESET);
        choice("Text", textModification());
        marginBigBorder();
    }
}
