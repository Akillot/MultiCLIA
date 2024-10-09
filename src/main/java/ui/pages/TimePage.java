package ui.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ui.layout.ColorFunc.displayContent;
import static ui.layout.BorderFunc.drawTripleBorder;

public class TimePage {
    public static void displayCurrentTime() {
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy " + "HH:mm");
        String formattedTime = localTime.format(myFormatter);
        displayContent("Time is: " + formattedTime, "white", (byte) 58);
        System.out.print("\n");
        drawTripleBorder();
        System.out.print("\n");
    }
}
