package ui.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ui.layout.BorderWork.drawTripleBorder;
import static ui.layout.ColorWork.displayColorCommand;

public  class InfoPage {
    public static String version = "0.5.0.0";
    public static void displayInfo() {
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern(" dd-MM-yyyy"
                + " HH:mm");
        String formattedTime = localTime.format(myFormatter);

        displayColorCommand("Current version:", "white", (byte) 58);
        displayColorCommand(version, "purple", (byte) 58);
        displayColorCommand("Author: Nick Zozulia", "white", (byte) 58);

        displayColorCommand("Time is: " + formattedTime, "white", (byte) 58);
        drawTripleBorder();
    }
}
