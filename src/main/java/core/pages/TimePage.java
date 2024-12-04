package core.pages;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.messageModifier;

public class TimePage {
    public static void displayCurrentTime() {
        messageModifier('n', 2);
        displayTime();
        messageModifier('n', 1);
        marginBorder();
    }
}
