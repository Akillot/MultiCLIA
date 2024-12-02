package core.pages;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.DisplayManager.*;

public class TimePage {
    public static void displayCurrentTime() {
        messageModifier('n', 2);
        displayTime();
        messageModifier('n', 1);
        marginBorder();
    }
}
