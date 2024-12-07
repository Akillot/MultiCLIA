package extensions.time.clock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static core.logic.TextConfigs.message;
import static java.lang.System.out;

public class Clock {
    public static void displayTime(){
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy " + "HH:mm");
        String formattedTime = localTime.format(myFormatter);
        message("Current time: " + formattedTime,15,58,0,out::print);
    }
}
