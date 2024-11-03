package core.pages;

import static core.layout.BorderFunc.marginBigBorder;
import static core.layout.DisplayManager.*;
import static java.lang.System.out;

public class TimePage {
    public static void displayCurrentTime() {
        messageModifier('n', 2);
        time();
        messageModifier('n', 1);
        out.println("First Line\fSecond Line");
        marginBigBorder();
    }
}
