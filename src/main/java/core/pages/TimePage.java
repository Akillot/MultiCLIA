package core.pages;

import static core.layout.BorderFunc.marginBigBorder;
import static core.layout.DisplayManager.*;
import static java.lang.System.out;

public class TimePage {
    public static void displayCurrentTime() {
        out.print("\n\n");
        time();
        messageModifier('n', 1);
        out.println("First Line\fSecond Line");
        marginBigBorder();
    }
}
