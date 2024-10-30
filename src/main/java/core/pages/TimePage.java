package core.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static core.layout.BorderFunc.displayMarginBigBorder;
import static core.layout.DisplayManager.*;
import static java.lang.System.out;

public class TimePage {
    public static void displayCurrentTime() {
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy " + "HH:mm");
        String formattedTime = localTime.format(myFormatter);
        out.print("\n\n");
        message("Time is: " + formattedTime, "white", 58);
        out.print("\n");
        displayMarginBigBorder();
    }
}
