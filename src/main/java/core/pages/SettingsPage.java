package core.pages;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.choice;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.alignment;
import static core.logic.TextConfigs.modifyMessage;
import static java.lang.System.out;

public class SettingsPage {
    public static void displaySettings() {
        modifyMessage('n', 2);
        out.print(alignment(60) + BOLD + "-" + BLUE + ITALIC + "Memory\n" + RESET);
        displayUsingMemory();

        modifyMessage('n', 2);
        out.print(alignment(60) + BOLD + "-" + BLUE + ITALIC + "Text\n" + RESET);
        choice("Text", textModification());
        marginBorder();
    }
}
