package ui.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ui.layout.BorderFunc.displayMarginBigBorder;
import static ui.layout.DisplayManager.message;

public class TimePage {
    public static void displayCurrentTime() {
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy " + "HH:mm");
        String formattedTime = localTime.format(myFormatter);
        System.out.print("\n\n");
        message("Time is: " + formattedTime, "white", 58, false);
        displayMarginBigBorder();
    }
}
