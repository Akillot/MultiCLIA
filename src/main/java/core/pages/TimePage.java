package core.pages;
import static core.layout.BorderFunc.marginBigBorder;
import static core.layout.ColorFunc.getRgbText;
import static core.layout.DisplayManager.*;

public class TimePage {
    public static void displayCurrentTime() {
        messageModifier('n', 2);
        time();
        getRgbText("Hello", "3;252;57", "84;152;255");
        messageModifier('n', 1);
        marginBigBorder();
    }
}
