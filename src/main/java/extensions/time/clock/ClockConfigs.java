package extensions.time.clock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.systemLayoutColor;
import static core.logic.TextConfigs.message;
import static core.logic.TextConfigs.modifyMessage;
import static java.lang.System.out;

public class ClockConfigs {
    public static void displayTime(){
        LocalDateTime localTime = LocalDateTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy " + "HH:mm");
        String formattedTime = localTime.format(myFormatter);

        modifyMessage('n', 2);
        message("Current time: " + formattedTime, systemLayoutColor,58,0,out::print);
        modifyMessage('n', 1);
        marginBorder();
    }
}
