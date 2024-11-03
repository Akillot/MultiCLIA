package core.pages;

import static core.layout.BorderFunc.bigBorder;
import static core.layout.CommandManager.*;
import static core.layout.DisplayManager.*;

import static java.lang.System.out;

public  class InfoPage {
    public static String version = "A-0.7.0";

    public static void displayInfo() throws InterruptedException {
        out.print("\n\n");
        message("Current version:", "white", 58);
        message(version, "purple", 58);
        message("Author: Nick Zozulia", "white", 58);

        out.print("\n\n");
        choice("Description", appDescription());
        choice("Github", getOpenUriAction("https://github.com/Akillot/MultiCLIA"));
        bigBorder();
        messageModifier('n', 1);
    }
}