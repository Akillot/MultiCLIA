package extensions.time;

import java.time.format.DateTimeFormatter;

import static core.logic.AppearanceConfigs.*;
import static core.logic.TextConfigs.alignment;
import static core.logic.TextConfigs.modifyMessage;
import static java.lang.System.out;

public class Reminder {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static void reminderStarted(){

        modifyMessage('n',2);
        out.print(alignment(58) + getAnsi256Color(systemLayoutColor) + "Set date and time to remind: " + RESET);
    }
}