package core.pages;

import static core.logic.BorderFunc.marginBigBorder;
import static core.logic.CommandManager.*;
import static core.logic.DisplayManager.*;

public  class InfoPage {
    public static String version = "A-0.7.0";

    public static void displayInfo() throws InterruptedException {
        messageModifier('n', 2);
        message("Current version:", "white", 58,0);
        message(version, "purple", 58,0);
        message("Author: Nick Zozulia", "white", 58,0);

        messageModifier('n', 2);
        choice("Description", appDescription());
        messageModifier('n', 1);

        choice("Github", getOpenUriAction("https://github.com/Akillot/MultiCLIA"));
        marginBigBorder();
    }
}