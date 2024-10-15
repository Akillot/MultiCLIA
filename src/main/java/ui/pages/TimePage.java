package ui.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ui.layout.BorderFunc.displayMarginBigBorder;
import static ui.layout.ColorFunc.displayContent;

public class TimePage {
    public static void displayCurrentTime() {
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy " + "HH:mm");
        String formattedTime = localTime.format(myFormatter);
        displayContent("Time is: " + formattedTime, "white", (byte) 58);
        displayMarginBigBorder();
    }
}
