package core.pages;

import static core.logic.BorderFunc.marginBorder;
import static core.logic.DisplayManager.*;

public class TimePage {
    public static void displayCurrentTime() {
        messageModifier('n', 2);
        time();
        messageModifier('n', 1);
        marginBorder();
    }
}
