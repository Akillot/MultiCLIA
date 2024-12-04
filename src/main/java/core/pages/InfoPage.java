package core.pages;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.CommandManager.*;
import static core.logic.DisplayManager.*;
import static core.logic.TextConfigs.message;
import static core.logic.TextConfigs.messageModifier;
import static java.lang.System.out;

public  class InfoPage {
    public static String version = "A-0.7.1";

    public static void displayInfo() throws InterruptedException {
        messageModifier('n', 2);
        message("Current version:", "white", 58,0, out::print);
        message(version, "blue", 58,0, out::print);
        message("Author: Nick Zozulia", "white", 58,0, out::print);
        messageModifier('n', 1);

        message("Enter '+' to open and '-' to skip", "white", 58,0, System.out::print);
        choice("Description", displayAppDescription());
        messageModifier('n', 1);

        message("Enter '+' to open and '-' to skip", "white", 58,0, System.out::print);
        choice("Github", openUri("https://github.com/Akillot/MultiCLIA"));
        marginBorder();
    }
}