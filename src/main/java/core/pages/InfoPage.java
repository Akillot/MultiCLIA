package core.pages;

import static core.layout.BorderFunc.bigBorder;
import static core.layout.CommandManager.*;
import static core.layout.DisplayManager.*;

public  class InfoPage {
    public static String version = "A-0.7.0";

    public static void displayInfo() throws InterruptedException {
        messageModifier('n', 2);
        message("Current version:", "white", 58);
        message(version, "purple", 58);
        message("Author: Nick Zozulia", "white", 58);

        messageModifier('n', 2);
        choice("Description", appDescription());
        choice("Github", getOpenUriAction("https://github.com/Akillot/MultiCLIA"));
        bigBorder();
        messageModifier('n', 1);
    }
}