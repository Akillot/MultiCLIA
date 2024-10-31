package core.pages;

import static core.layout.BorderFunc.marginBigBorder;
import static core.layout.DisplayManager.*;
import static java.lang.System.out;

public class TimePage {
    public static void displayCurrentTime() {
        out.print("\n\n");
        time();
        out.print("\n");
        marginBigBorder();
    }
}
