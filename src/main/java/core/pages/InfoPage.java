package core.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static core.layout.CommandManager.*;
import static core.layout.DisplayManager.*;

import static java.lang.System.out;

public  class InfoPage {
    public static String version = "A-0.6.5";

    public static void displayInfo() throws InterruptedException {
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern(" dd-MM-yyyy" + " HH:mm");
        String formattedTime = localTime.format(myFormatter);
        out.println("\n");
        message("Current version:", "white", 58);
        message(version, "purple", 58);
        message("Author: Nick Zozulia", "white", 58);
        message("Current time: " + formattedTime, "white", 58);
        message("Memory used: " +
                ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                / (1000 * 1000) + "M"), "white", 58);

        out.print("\n");
        choice("Github: ", getOpenUriAction("https://github.com/Akillot/MultiCLIA"));
    }
}