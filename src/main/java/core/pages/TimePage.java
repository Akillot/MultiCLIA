package core.pages;

import static core.layout.BorderFunc.marginBigBorder;
import static core.layout.DisplayManager.*;

public class TimePage {
    public static void displayCurrentTime() {
        messageModifier('n', 2);
        time();
        messageModifier('n', 1);
        marginBigBorder();
    }
}
